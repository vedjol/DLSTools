package com.veda.hrentgelt.dlstools.util;

import com.veda.hrentgelt.dlstools.DLSTools;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.parser.txt.CharsetDetector;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class DLSFunctions {
	public static final String KASSENBEZ = "KASSENBEZ";
	public static final String BETR_NR_KK = "BETR_NR_KK";
	public static final char COLUMN_SEPARATOR = ';';
	private static final String STANDARD_ENCODING_APPENDER = "_richtiges_encoding";
	private static final char TEXT_ENCAPSULATOR = '"';

	private DLSFunctions() {
	}

	public static int getCurrentYear() {
		return Year.now().getValue();
	}

	public static void removeUnusableANLines(IndexFileData indexFileData, File anFile, boolean onlyKeepCurrentYearsData) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Entfernen der überflüssigen AN-Zeilen");
		if (indexFileData == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}
		if (anFile == null || !anFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Arbeitnehmerstammdaten-Datei konnte nicht gefunden werden");
			return;
		}
		if (!isUTF8Encoded(anFile)) {
			if (JOptionPane.showConfirmDialog(DLSTools.getInstance(), "Das Encoding der Datei " + anFile + " ist wsh. nicht UTF-8. Dennoch fortfahren?", "Falsches Encoding der " + anFile, JOptionPane.OK_CANCEL_OPTION) == 2) {
				logger.log(System.Logger.Level.ERROR, "Verarbeitung abgebrochen");
				return;
			}
		}

		//Richtige Dateidefinition öffnen
		Table anTable = indexFileData.getTable(DLSTools.AN_TABLE_NAME);
		if (anTable == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}

		//
		List<String> lines = null;
		try {
			lines = getLines(anFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + DLSTools.AN_TABLE_NAME + " konnten nicht ermittelt werden");
			return;
		}

		//Dateibeschreibung und Zeilen vorhanden
		//Datei auslesen; neuste Zeilen ermitteln
		HashMap<ArbeitnehmerKey, String> newestRecords = new HashMap<>();
		int YEAR = getCurrentYear();
		lines.forEach(line -> {
			//Keyfelder finden
			String[] columns = line.split(String.valueOf(COLUMN_SEPARATOR));
			try {
				String prnr = columns[anTable.getIndexOf("PERS_NR")];
				int abrj = Integer.parseInt(columns[anTable.getIndexOf("ABR_JAHR")].replaceAll("\"", "").trim());
				int abrm = Integer.parseInt(columns[anTable.getIndexOf("ABR_MON")].replaceAll("\"", "").trim());
				ArbeitnehmerKey key = new ArbeitnehmerKey(prnr, abrj, abrm);

				List<ArbeitnehmerKey> keys = newestRecords.keySet().stream().toList();
				if (!onlyKeepCurrentYearsData || YEAR == abrj) {
					if (keys.contains(key)) {
						ArbeitnehmerKey k2 = keys.get(keys.indexOf(key));
						if (key.isNewerThan(k2)) {
							newestRecords.remove(k2);
							newestRecords.put(key, line);
						}
					} else newestRecords.put(key, line);
				}

			} catch (Exception e) {
				logger.log(System.Logger.Level.ERROR, "Zeile konnte nicht verarbeitet werden, da die " +
						"Schlüsselfelder nicht gefunden werden konnten: " + line);
			}
		});

		File newFile = getLowestFileName(anFile.getParentFile(), anFile.getName());
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + newFile.getAbsolutePath() + " konnte nicht erstellt werden.");
			return;
		}
		try {
			FileWriter writer = new FileWriter(newFile);
			for (Map.Entry<ArbeitnehmerKey, String> entry : newestRecords.entrySet()) {
				String val = entry.getValue();
				writer.write(val + "\n");
			}
			writer.close();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + newFile.getAbsolutePath() + " konnte nicht gefüllt werden.");
			return;
		}
		logger.log(System.Logger.Level.INFO, "Datei " + newFile.getAbsolutePath() + " wurde erstellt und gefüllt.");
		printEncodingWarning();
	}

	public static void removeUnusableSVLines(IndexFileData indexFileData, File svFile, boolean onlyKeepCurrentYearsData) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Entfernen der überflüssigen SV-Zeilen");
		if (indexFileData == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}
		if (svFile == null || !svFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Sozialversicherungsdaten-Datei konnte nicht gefunden werden");
			return;
		}
		if (!isUTF8Encoded(svFile)) {
			if (JOptionPane.showConfirmDialog(DLSTools.getInstance(), "Das Encoding der Datei " + svFile + " ist wsh. nicht UTF-8. Dennoch fortfahren?", "Falsches Encoding der " + svFile, JOptionPane.OK_CANCEL_OPTION) == 2) {
				logger.log(System.Logger.Level.ERROR, "Verarbeitung abgebrochen");
				return;
			}
		}

		//Richtige Dateidefinition öffnen
		Table svTable = indexFileData.getTable(DLSTools.SV_TABLE_NAME);
		if (svTable == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}

		//
		List<String> lines = null;
		try {
			lines = getLines(svFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + DLSTools.SV_TABLE_NAME + " konnten nicht ermittelt werden");
			return;
		}

		//Dateibeschreibung und Zeilen vorhanden
		//Datei auslesen; neuste Zeilen ermitteln
		HashMap<SozialversicherungsKey, String> newestRecords = new HashMap<>();
		int YEAR = getCurrentYear();
		lines.forEach(line -> {
			//Keyfelder finden
			String[] columns = line.split(String.valueOf(COLUMN_SEPARATOR));
			try {
				String prnr = columns[svTable.getIndexOf("PERS_NR")];
				int abrj = Integer.parseInt(columns[svTable.getIndexOf("ABR_JAHR")].replaceAll("\"", "").trim());
				int abrm = Integer.parseInt(columns[svTable.getIndexOf("ABR_MON")].replaceAll("\"", "").trim());
				int lfdn;
				try {
					lfdn = Integer.parseInt(columns[svTable.getIndexOf("AEND_ZAHL")].replaceAll("\"", "").trim());
				} catch (Exception e) {
					lfdn = 1;
				}
				SozialversicherungsKey key = new SozialversicherungsKey(prnr, abrj, abrm, lfdn);

				List<SozialversicherungsKey> keys = newestRecords.keySet().stream().toList();
				if (!onlyKeepCurrentYearsData || YEAR == abrj) {
					if (keys.contains(key)) {
						SozialversicherungsKey k2 = keys.get(keys.indexOf(key));
						if (key.isNewerThan(k2)) {
							newestRecords.remove(k2);
							newestRecords.put(key, line);
						}
					} else newestRecords.put(key, line);
				}

			} catch (Exception e) {
				logger.log(System.Logger.Level.ERROR, "Zeile konnte nicht verarbeitet werden, da die Schlüsselfelder nicht gefunden werden konnten: " + line);
			}
		});

		File newFile = getLowestFileName(svFile.getParentFile(), svFile.getName());
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + newFile.getAbsolutePath() + " konnte nicht erstellt werden.");
			return;
		}
		try {
			FileWriter writer = new FileWriter(newFile);
			for (Map.Entry<SozialversicherungsKey, String> entry : newestRecords.entrySet()) {
				String val = entry.getValue();
				writer.write(val + "\n");
			}
			writer.close();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + newFile.getAbsolutePath() + " konnte nicht gefüllt werden.");
			return;
		}
		logger.log(System.Logger.Level.INFO, "Datei " + newFile.getAbsolutePath() + " wurde erstellt und gefüllt.");
		printEncodingWarning();
	}

	private static void printEncodingWarning() {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.WARNING, "Achtung! Die erstellte Datei bitte genau überprüfen!");
		logger.log(System.Logger.Level.WARNING, "Sollte die eingegebene Datei nicht in UTF-8 formatiert gewesen sein, so sind die Sonderzeichen in der neu erstellten Datei sehr wahrscheinlich nicht mehr lesbar!");
	}


	public static void createAddition(IndexFileData indexFileData, File anFile, String company) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Erstellen der Arbeitnehmerstammdatenergänzung");

		if (indexFileData == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}
		if (anFile == null || !anFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Arbeitnehmerstammdaten-Datei konnte nicht gefunden werden");
			return;
		}

		if (!isUTF8Encoded(anFile)) {
			if (JOptionPane.showConfirmDialog(DLSTools.getInstance(), "Das Encoding der Datei " + anFile + " ist wsh. nicht UTF-8. Dennoch fortfahren?", "Falsches Encoding der " + anFile, JOptionPane.OK_CANCEL_OPTION) == 2) {
				logger.log(System.Logger.Level.ERROR, "Verarbeitung abgebrochen");
				return;
			}
		}

		//Richtige Dateidefinition öffnen
		Table anTable = indexFileData.getTable(DLSTools.AN_TABLE_NAME);
		if (anTable == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}

		//
		List<String> lines = null;
		try {
			lines = getLines(anFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + DLSTools.AN_TABLE_NAME + " konnten nicht ermittelt werden");
			return;
		}

		int prnrIndex = anTable.getIndexOf("PERS_NR");

		File anerFilePath = new File(DLSTools.getInstance().getPath(DLSTools.ANER_FILE_NAME));
		File parentPath = anerFilePath.getParentFile();
		File anerFile = getLowestFileName(parentPath, removeFileEnding(anerFilePath.getName()) + "_" + company + getFileEnding(anerFilePath.getName()));
//        File anerFile = getLowestFileName(new File(DLSTools.getInstance().getSourcePath()), removeFileEnding(anerFileName) + "_" + company + getFileEnding(anerFileName));
		try {
			anerFile.createNewFile();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + anerFile.getAbsolutePath() + " konnte nicht erstellt werden.");
			return;
		}
		try {
			FileWriter writer = new FileWriter(anerFile);
			lines.stream().map(line -> {
				String[] content = line.split(String.valueOf(COLUMN_SEPARATOR));
				if (content.length <= prnrIndex) {
					logger.log(System.Logger.Level.ERROR, "Zeile konnte nicht in die Arbeitnehmerstammdatenergänzung übersetzt werden. Kann alte Personalnummer nicht ermitteln:" + line);
					return null;
				}
				return content[prnrIndex] + ";;" + DLSTools.getInstance().getCompany();
			}).forEach(text -> {
				try {
					writer.write(text + "\n");
				} catch (Exception e) {
					logger.log(System.Logger.Level.ERROR, "Zeile " + text + "konnte nicht in die Datei " + anerFile.getAbsolutePath() + " übernommen werden.");
				}
			});
			writer.close();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + anerFile.getAbsolutePath() + " konnte nicht gefüllt werden.");
			return;
		}
		logger.log(System.Logger.Level.INFO, "Datei " + anerFile + " wurde erstellt und gefüllt.");
	}

	public static void createKrankenKassenListe(IndexFileData indexFileData, File svFile) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Erstelle eine Auflistung der Krankenkassen");

		if (indexFileData == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}
		if (svFile == null || !svFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Sozialversicherungsdaten-Datei konnte nicht gefunden werden");
			return;
		}

		//Richtige Dateidefinition öffnen
		Table svTable = indexFileData.getTable(DLSTools.SV_TABLE_NAME);
		if (svTable == null) {
			logger.log(System.Logger.Level.ERROR, "Informationen der Index.xml konnten nicht aufbereitet werden");
			return;
		}
		if (!isUTF8Encoded(svFile)) {
			if (JOptionPane.showConfirmDialog(DLSTools.getInstance(), "Das Encoding der Datei " + svFile + " ist wsh. nicht UTF-8. Dennoch fortfahren?", "Falsches Encoding der " + svFile, JOptionPane.OK_CANCEL_OPTION) == 2) {
				logger.log(System.Logger.Level.ERROR, "Verarbeitung abgebrochen");
				return;
			}
		}

		try {
			//indizees ermitteln
			int kkIdx = svTable.getIndexOf(BETR_NR_KK);
			int kassenbezIdx = svTable.getIndexOf(KASSENBEZ);
			//Daten in Liste speichern
			List<List<String>> values = new ArrayList<>();
			getLines(svFile).forEach(line -> {
				String[] cols = line.split(String.valueOf(COLUMN_SEPARATOR));
				ArrayList<String> relevantCols = new ArrayList<>();
				int i;
				String s = "";
				s = cols.length < (i = kkIdx) ? "" : cols[i];
				relevantCols.add(s);
				s = cols.length < (i = kassenbezIdx) ? "" : cols[i];
				relevantCols.add(s);
				if (!values.contains(relevantCols)) {
					values.add(relevantCols);
				}
			});
			//Daten in Excel speichern
			File f = new File(svFile.getParent() + File.separator + DLSTools.KK_FILE_NAME);
			writeToExcelFile(f, values);
			logger.log(System.Logger.Level.INFO, "Auflistung der Krankenkassen erstellt. Datei: " + f.getAbsolutePath());

		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Auflistung der Krankenkassen konnte nicht erstellt werden. Fehler: " + e.getMessage());
		}
	}


	public static void mergeAdditions(File additionRoot, boolean withHeaders) {
		LogPanel logger = LogPanel.getInstance();
		String header = withHeaders ? "mit" : "ohne";
		logger.log(System.Logger.Level.INFO, "Zusammensetzen der Ergänzungs-Dateien " + header + " Überschriften");
		if (additionRoot == null || !additionRoot.isDirectory()) {
			logger.log(System.Logger.Level.ERROR, "Ergänzungsdateien konnten nicht ermittelt werden. Root-Ordner: " + additionRoot);
			return;
		}

		//Dateien ermitteln
		File[] anerFiles = additionRoot.listFiles((dir, name) -> name.contains(DLSTools.ANER_FILE_NAME));
		if (anerFiles == null || anerFiles.length == 0) {
			logger.log(System.Logger.Level.ERROR, "Keine Ergänzungsdateien zum zusammensetzen gefunden.");
			return;
		}
		File f = new File(DLSTools.getInstance().getPath(DLSTools.ANER_FILE_NAME));
		File mergedFile = getLowestFileName(f.getParentFile(), f.getName());
		try {
			mergedFile.createNewFile();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + mergedFile + " konnte nicht erstellt werden.");
			return;
		}

		try {
			FileWriter writer = new FileWriter(mergedFile);
			for (File anerFile : anerFiles) {
				List<String> lines = getLines(anerFile);
				for (int i = 0; i < lines.size(); i++) {
					if (i == 0 && withHeaders) {
						continue;
					}
					writer.write(lines.get(i) + "\n");
				}
			}
			writer.close();


		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Datei " + mergedFile + " konnte nicht befüllt werden.");
			return;
		}

		String filesString = "[";
		for (int i = 0; i < anerFiles.length; i++) {
			filesString += anerFiles[i].getAbsolutePath();
			if (i == anerFiles.length - 1)
				filesString += "]";
			else filesString += ", ";

		}

		logger.log(System.Logger.Level.INFO, "Datei " + mergedFile + " erfolgreich gefüllt");
		printEncodingWarning();
	}

	public static void checkDLSFiles(IndexFileData indexData) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfung der DLS-Dateien...");

		//Generelles:
		//Existenz der Dateien
		if (indexData == null) {
			logger.log(System.Logger.Level.ERROR, "Daten der Index.xml konnte nicht aufbereitet werden.");
		}
		File anFile = new File(DLSTools.getInstance().getPath(DLSTools.AN_TABLE_NAME));
		if (!anFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + anFile + " existiert nicht.");
		}
		File svFile = new File(DLSTools.getInstance().getPath(DLSTools.SV_TABLE_NAME));
		if (!svFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + svFile + " existiert nicht.");
		}
		File anerFile = new File(DLSTools.getInstance().getPath(DLSTools.ANER_FILE_NAME));
		if (!anerFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + anerFile + " existiert nicht.");
		}

		File[] files = {anFile, svFile, anerFile};
		Arrays.stream(files).forEach(f -> {
			//Encoding checken
			checkEncoding(f);
			//Auf Überschrift checken
			checkForHeader(f);
		});

		//Spezifisches:
		//Arbeitnehmerstammdaten:
		checkANData(indexData, anFile, anerFile);
		checkANERData(anerFile);
	}

	private static void checkANData(IndexFileData idxData, File anFile, File anerFile) {
		LogPanel logger = LogPanel.getInstance();
		String company = DLSTools.getInstance().getCompany();

		logger.log(System.Logger.Level.INFO, "Prüfen der " + DLSTools.AN_TABLE_NAME + " für die Firma " + company + "...");

		//Check if an-File has Employees that are irrelevant; Check if aner-File has Employees that are irrelevant;
		Set<String> anerPRNRs;
		Set<String> anPRNRs;
		int anerPrnrIdx = 0;
		int anerCompanyIdx = 2;
		int prnrIdx = idxData.getTable(DLSTools.AN_TABLE_NAME).getIndexOf("PERS_NR");

		//Ermitteln der Personalnummern (Arbeitnehmerstammdaten)
		try {
			anPRNRs = getLines(anFile).stream().map(line -> {
				String[] cols = line.split(String.valueOf(COLUMN_SEPARATOR));
				return (cols.length > prnrIdx) ? removeEncapsulator(cols[prnrIdx].trim()) : "";
			}).collect(Collectors.toSet());
		} catch (Exception e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + anFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}
		//Ermitteln der Personalnummern (Ergänzung)
		try {
			anerPRNRs = getLines(anerFile).stream().map(l -> l.split(String.valueOf(COLUMN_SEPARATOR)))
					.filter(cols -> cols.length > anerPrnrIdx && cols[anerCompanyIdx].trim().equals(company))
					.map(cols -> cols.length > anerPrnrIdx ? removeEncapsulator(cols[anerPrnrIdx].trim()) : "").collect(Collectors.toSet());
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + anerFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}

		//Vergleich (Auf Basis der AN-Zeilen)
		List<String> onlyInANFile = new ArrayList<>(anPRNRs.stream()
				.filter(anPRNR -> !anerPRNRs.contains(anPRNR)).toList());
		Collections.sort(onlyInANFile);
		if (!onlyInANFile.isEmpty()) {
			logger.log(System.Logger.Level.ERROR, "Folgende Personalnummern sind in der Arbeitnehmerstammdaten-Datei vorhanden, nicht aber in der Ergänzungsdatei:");
			onlyInANFile.forEach(l -> logger.log(System.Logger.Level.INFO, l));
		}

		//Vergleich (Auf Basis der ANER-Zeilen)
		List<String> onlyInANERFile = new ArrayList<>(anerPRNRs.stream()
				.filter(anerPRNR -> !anPRNRs.contains(anerPRNR)).toList());
		Collections.sort(onlyInANERFile);
		if (!onlyInANERFile.isEmpty()) {
			logger.log(System.Logger.Level.ERROR, "Folgende Personalnummern sind in der Ergänzungsdatei vorhanden, nicht aber in der Arbeitnehmerstammdaten-Datei:");
			onlyInANERFile.forEach(l -> logger.log(System.Logger.Level.INFO, l));
		}

		if (onlyInANERFile.isEmpty() && onlyInANFile.isEmpty())
			logger.log(System.Logger.Level.INFO, "Keine Fehler beim Prüfen der " + DLSTools.AN_TABLE_NAME + " mit Firma " + company + " aufgefallen.");
	}

	private static String removeEncapsulator(String text) {
		text = text.trim();
		if (text.startsWith(String.valueOf(TEXT_ENCAPSULATOR)) && text.endsWith(String.valueOf(TEXT_ENCAPSULATOR))) {
			text = text.substring(1, text.length() - 1);
		}
		return text;
	}

	private static void checkANERData(File anerFile) {
		//- Jede PRNR einmalig
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfen der Arbeitnehmerstammdatenergänzung...");

		//PRNRs ermitteln
		List<String> lines;
		List<String> anerPRNRs;
		int prnrIdx = 0;
		try {
			lines = getLines(anerFile);
			anerPRNRs = lines.stream().map(line -> {
				String[] cols = line.split(String.valueOf(COLUMN_SEPARATOR));
				return (cols.length > prnrIdx) ? cols[prnrIdx].trim() : "";
			}).collect(Collectors.toList());
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + anerFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}

		Set<String> set = new HashSet<>();
		// Create a new ArrayList to store duplicate elements
		Set<String> duplicates = new HashSet<>();

		for (String element : anerPRNRs) {
			if (!set.add(element)) {
				// Add element to duplicates list if it's already in the set
				duplicates.add(element);
			}
		}
		if (!duplicates.isEmpty()) {
			logger.log(System.Logger.Level.WARNING, "Folgende Personalnummern sind mehr als ein Mal in der Arbeitnehmerstammdatenergänzung: " + duplicates.toString());
			return;
		}

		//Textencapsultor gefunden?
		checkForTextEncapsulator(anerFile);

		//- Dateiinhalt valid (gegen Dateibeschreibung prüfen)
		Spaltenbeschreibungen.logErrors(lines, Spaltenbeschreibungen.ERGAENZUNGSBESCHREIBUNG);

		logger.log(System.Logger.Level.INFO, "Prüfung der " + anerFile + " abgeschlossen.");
	}


	public static void checkBEAB(File beabFile) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfung der Be-/Abzüge...");

		//Generelles:
		//Existenz der Dateien
		if (beabFile == null || !beabFile.exists() || beabFile.isDirectory()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + beabFile + " existiert nicht.");
			return;
		}

		checkCostumeFile(beabFile, 1, 2);

		List<String> lines;
		try {
			lines = getLines(beabFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + beabFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}
		Spaltenbeschreibungen.logErrors(lines, Spaltenbeschreibungen.BEZUEGE_ABZUEGE_SPALTENBESCHREIBUNG);


		logger.log(System.Logger.Level.INFO, "Prüfung der Be-/Abzüge abgeschlossen.");
	}


	public static void checkBBG(File bbgFile) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfung der BBG-Salden...");

		//Generelles:
		//Existenz der Dateien
		if (bbgFile == null || !bbgFile.exists() || bbgFile.isDirectory()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + bbgFile + " existiert nicht.");
			return;
		}

		checkCostumeFile(bbgFile, 0, 1, 2);

		List<String> lines;
		try {
			lines = getLines(bbgFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + bbgFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}
		Spaltenbeschreibungen.logErrors(lines, Spaltenbeschreibungen.BBG_SALDEN_SPALTENBESCHREIBUNG);


		logger.log(System.Logger.Level.INFO, "Prüfung der BBG-Salden abgeschlossen.");
	}

	public static void checkVAG(File vagFile) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfung der Vorarbeitgeberdaten...");

		//Generelles:
		//Existenz der Dateien
		if (vagFile == null || !vagFile.exists() || vagFile.isDirectory()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + vagFile + " existiert nicht.");
			return;
		}

		checkCostumeFile(vagFile, 0, 1, 2);

		List<String> lines;
		try {
			lines = getLines(vagFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + vagFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}
		Spaltenbeschreibungen.logErrors(lines, Spaltenbeschreibungen.VAG_SPALTENBESCHREIBUNG);


		logger.log(System.Logger.Level.INFO, "Prüfung der Vorarbeitgeberdaten abgeschlossen.");
	}

	public static void checkDURS(File dursFile) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfung der Durchschnitte...");

		//Generelles:
		//Existenz der Dateien
		if (dursFile == null || !dursFile.exists() || dursFile.isDirectory()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + dursFile + " existiert nicht.");
			return;
		}

		checkCostumeFile(dursFile, 0, 1, 2, 3, 4);

		List<String> lines;
		try {
			lines = getLines(dursFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + dursFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}
		Spaltenbeschreibungen.logErrors(lines, Spaltenbeschreibungen.DURCHSCHNITTE_SPALTENBESCHREIBUNG);


		logger.log(System.Logger.Level.INFO, "Prüfung der Durchschnitte abgeschlossen.");
	}

	public static void checkSWBH(File swbhFile) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfung der Schwerbehindertendaten...");

		//Generelles:
		//Existenz der Dateien
		if (swbhFile == null || !swbhFile.exists() || swbhFile.isDirectory()) {
			logger.log(System.Logger.Level.ERROR, "Datei " + swbhFile + " existiert nicht.");
			return;
		}

		checkCostumeFile(swbhFile, 0, 1, 2);

		List<String> lines;
		try {
			lines = getLines(swbhFile);
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Zeilen der " + swbhFile.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}
		Spaltenbeschreibungen.logErrors(lines, Spaltenbeschreibungen.SCHWERBEHINDERTENDATEN_SPALTENBESCHREIBUNG);


		logger.log(System.Logger.Level.INFO, "Prüfung der Durchschnitte abgeschlossen.");
	}



	/*
	 *
	 * UTILITIES
	 *
	 * */

	private static void checkCostumeFile(File costumeFile, int... keyindexes) {
		checkEncoding(costumeFile);
		checkForHeader(costumeFile);
		checkForTextEncapsulator(costumeFile);
		checkForDuplicateKeys(costumeFile, keyindexes);
	}

	/**
	 * Searches the whole file for fields that are separated by '"'.
	 *
	 * @param file
	 */
	private static void checkForTextEncapsulator(File file) {
		List<String> lines = null;
		try {
			lines = getLines(file);
		} catch (IOException e) {
			LogPanel.getInstance().log(System.Logger.Level.ERROR, "Zeilen der Datei " + file + " konnten nicht ermittelt werden");
		}
		for (int i = 0; i < lines.size(); i++) {
			checkLineFieldsForTextEncapsultor(lines.get(i), i + 1);
		}
	}

	private static void checkForDuplicateKeys(File file, int... keyindexes) {
		LogPanel logger = LogPanel.getInstance();

		int highestIndex = -1;
		for (int i : keyindexes)
			if (highestIndex < i) highestIndex = i;


		List<String[]> keyValues = null;
		try {
			keyValues = getLines(file).stream().map(line -> {
				String[] arr = new String[keyindexes.length];
				String[] content = line.split(String.valueOf(COLUMN_SEPARATOR));

				for (int i = 0; i < keyindexes.length; i++) {
					arr[i] = content[keyindexes[i]];
				}
				return arr;
			}).collect(Collectors.toList());
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Schlüsselfelder der Datei " + file + " konnten nicht ermittelt werden.");
		}

		//Keine doppelten
		if (keyValues == null) return;


		Set<String[]> set = new HashSet<>();
		// Create a new ArrayList to store duplicate elements
		Set<String[]> duplicates = new HashSet<>();

		for (String[] key : keyValues) {
			if (!set.add(key)) {
				// Add element to duplicates list if it's already in the set
				duplicates.add(key);
			}
		}
		if (!duplicates.isEmpty()) {
			logger.log(System.Logger.Level.WARNING, "Folgende Personalnummern sind mehr als ein Mal in der Arbeitnehmerstammdatenergänzung: " + duplicates);
			return;
		}
	}

	/**
	 * checks the given line for fields that are separated by '"'.
	 *
	 * @param line the given line
	 */
	private static void checkLineFieldsForTextEncapsultor(String line, int currentRow) {
		String[] fields = line.split(String.valueOf(COLUMN_SEPARATOR));
		for (int col = 0; col < fields.length; col++) {
			String trimmed = fields[col].trim();
			if (trimmed.startsWith(String.valueOf(TEXT_ENCAPSULATOR))
					|| trimmed.endsWith(String.valueOf(TEXT_ENCAPSULATOR))) {
				// Fehler: Das Feld ist mit Textencapsulatoren angegeben. Entfernen!
				LogPanel.getInstance().log(System.Logger.Level.WARNING, "Das Feld an Position Row: " + (currentRow) + ", Col: " + col + " ist durch Textencapsulatoren ("
						+ TEXT_ENCAPSULATOR + ") abgegrenzt. In dieser Datei ist das nicht zulässig.");
			}
		}
	}

	private static void checkForHeader(File f) {
		if (f == null || f.isDirectory() || !f.getName().toUpperCase().endsWith(DLSTools.CSV_ENDING.toUpperCase())) {
			return;
		}

		LogPanel logger = LogPanel.getInstance();

		List<String> lines;
		try {
			lines = getLines(f);
		} catch (IOException e) {
			return;
		}

		if (lines.size() < 5) {
			logger.log(System.Logger.Level.WARNING, "Datei " + f + " hat zu wenig Zeilen, um auf Überschrift zu prüfen.");
			return;
		}

		//length der ersten Zeile ermitteln
		int firstLineLength = lines.get(0).length();
		//avg-length ermitteln
		int avgLength = -1;
		for (int i = 1; i < lines.size(); i++) {
			avgLength += lines.get(i).length();
		}
		avgLength /= lines.size() - 1;

		//Wenn die erste Zeile min. 1,5x so Lang ist wie der Durchschnitt der Zeilen, dann wird von einer Überschrift ausgegangen
		if (firstLineLength >= 1.5d * avgLength) {
			logger.log(System.Logger.Level.WARNING, "Datei " + f + " hat mit hoher Wahrscheinlichkeit eine Überschrift. Bitte entfernen!");
		}

	}

	private static void checkEncoding(File file) {
		LogPanel logger = LogPanel.getInstance();
		logger.log(System.Logger.Level.INFO, "Prüfe Datei " + file + " auf das richtige Encoding");
		String validCharset = DLSTools.VALID_CHARSET_NAME;

		CharsetDetector cd = new CharsetDetector();
		String encoding;
		try {
			cd.setText(Files.readAllBytes(file.toPath()));
			encoding = cd.detect().getName();
		} catch (IOException e) {
			logger.log(System.Logger.Level.ERROR, "Charset der Datei " + file.getAbsolutePath() + " konnte nicht ermittelt werden. Bitte per Hand prüfen");
			return;
		}

		if (encoding == null) {
			logger.log(System.Logger.Level.ERROR, "Das Charset der Datei " + file.getAbsolutePath() + " konnte nicht ermittelt werden.");
			return;
		}

		if (!validCharset.equals(encoding.trim().toUpperCase())) {
			logger.log(System.Logger.Level.INFO, "Datei " + file + " hat das Falsche Encoding: " + encoding);
			logger.log(System.Logger.Level.INFO, "Kopie der Datei wird erstellt...");
			File copiedFile = new File(removeFileEnding(file.getPath()) + STANDARD_ENCODING_APPENDER + getFileEnding(file.getPath()));
			try {
				copiedFile.createNewFile();
			} catch (IOException e) {
				logger.log(System.Logger.Level.ERROR, "Kopie der Datei " + file.getAbsolutePath() + " konnte nicht erstellt werden.");
				return;
			}
			String content;
			try {
				content = FileUtils.readFileToString(file, encoding);
				FileUtils.write(copiedFile, content, validCharset);
			} catch (Exception e) {
				logger.log(System.Logger.Level.ERROR, "Kopie der Datei " + file.getAbsolutePath() + " konnte nicht gefüllt werden. Fehler: " + e.getMessage());
				return;
			}
			logger.log(System.Logger.Level.INFO, "Richtig codierte Datei " + copiedFile + " erstellt.");
			return;
		}
		logger.log(System.Logger.Level.INFO, "Datei " + file + " hat das richtige Encoding");
	}

	public static boolean isUTF8Encoded(File f) {
		CharsetDetector cd = new CharsetDetector();
		String encoding;
		try {
			cd.setText(Files.readAllBytes(f.toPath()));
			encoding = cd.detect().getName();
		} catch (IOException e) {
			return false; //wsh. nicht UTF-8
		}

		if (encoding == null) return false;
		return encoding.equalsIgnoreCase("UTF-8");

	}

	private static List<String> getLines(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		List<String> ret = reader.lines().collect(Collectors.toList());
		reader.close();
		return ret;
	}


	private static File getLowestFileName(File f) {
		return getLowestFileName(f.getParentFile(), f.getName());
	}

	private static File getLowestFileName(File parent, String baseChildName) {
		if (!parent.isDirectory())
			throw new IllegalArgumentException("Parent-File " + parent.getAbsolutePath() + " ist kein Ordner");
		File[] childern = parent.listFiles();

		for (int i = 0; ; i++) {
			String name = (i == 0) ? baseChildName : removeFileEnding(baseChildName) + "_" + i + getFileEnding(baseChildName);
			File child = new File(parent + File.separator + name);
			if (!child.exists()) {
				return child;
			}
		}
	}

	public static String removeFileEnding(String filename) {
		if (filename.contains(".")) return filename.substring(0, filename.lastIndexOf("."));
		return filename;
	}

	public static String getFileEnding(String filename) {
		if (filename.contains(".")) return filename.substring(filename.lastIndexOf("."));
		LogPanel.getInstance().log(System.Logger.Level.ERROR, "Dateiendung der Datei " + filename + " konnte nicht ermittelt werden.");
		return ".txt";
	}

	public static void writeToExcelFile(File outFile, List<List<String>> content) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet worksheet = wb.createSheet();

		for (int row = 0; row < content.size(); row++) {
			List<String> rowContent = content.get(row);
			XSSFRow excelRow = worksheet.createRow(row);
			for (int col = 0; col < rowContent.size(); col++) {
				excelRow.createCell(col).setCellValue(rowContent.get(col));
			}
		}
		FileOutputStream fos = new FileOutputStream(outFile);
		wb.write(fos);
		fos.close();
	}

	public static void prettyPrint(File xmlFile) {
		LogPanel logger = LogPanel.getInstance();
		if (!isUTF8Encoded(xmlFile)) {
			if (JOptionPane.showConfirmDialog(DLSTools.getInstance(), "Das Encoding der Datei " + xmlFile + " ist wsh. nicht UTF-8. Dennoch fortfahren?", "Falsches Encoding der " + xmlFile, JOptionPane.OK_CANCEL_OPTION) == 2) {
				logger.log(System.Logger.Level.ERROR, "Verarbeitung abgebrochen");
				return;
			}
		}
		if (xmlFile == null || !xmlFile.exists()) {
			logger.log(System.Logger.Level.ERROR, "Mitgegebene Datei \"" + xmlFile + "\" für Pretty Print existiert nicht!");
			return;
		}
		File xslt = new File(DLSFunctions.class.getResource("xml/description.xslt").getFile());

		File outputfile;
		try {
			InputStream src = getFileInputStreamFromFile(xmlFile);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setNamespaceAware(true);
			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
			dbf.setFeature("http://xml.org/sax/features/validation", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			Document document = dbf.newDocumentBuilder().parse(src);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = xslt.exists() ? transformerFactory.newTransformer(new StreamSource(new BufferedReader(new FileReader(xslt)))) : transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, true ? "yes" : "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			//Create File
			File outputfileWithoutNumber = new File(removeFileEnding(xmlFile.getAbsolutePath()) + "_pretty" + getFileEnding(xmlFile.getAbsolutePath()));
			outputfile = getLowestFileName(outputfileWithoutNumber);

			if (!outputfile.getParentFile().exists())
				outputfile.mkdirs();
			outputfile.createNewFile();

			//Write into file
			try (FileWriter writer = new FileWriter(outputfile)) {
				transformer.transform(new DOMSource(document), new StreamResult(writer));
			} catch (Exception e) {
				logger.log(System.Logger.Level.ERROR, "Schreiben des Pretty-Prints in die Datei \"" + outputfile + "\" hat nicht funktioniert. Fehler:" + e.getMessage());
				logger.log(System.Logger.Level.INFO, "Dieser Fehler ist ggf. aufgrund einer falschen Codierung aufgetreten. Richtig ist nur die Codierung UTF-8!");
				if (outputfile.exists()) outputfile.delete();
				return;
			}
			logger.log(System.Logger.Level.INFO, "Pretty-Print für Datei \"" + outputfile + "\" wurde angelegt.");
		} catch (Exception e) {
			logger.log(System.Logger.Level.ERROR, "Pretty-Print für Datei \"" + xmlFile + "\" hat nicht funktioniert. Fehler:" + e.getMessage());
			logger.log(System.Logger.Level.WARNING, "Dieser Fehler ist ggf. aufgrund einer falschen Codierung aufgetreten. Richtig ist nur die Codierung UTF-8!");
		}
	}

	public static InputStream getFileInputStreamFromFile(File xmlFile) throws IOException {
		FileInputStream input = new FileInputStream(xmlFile);
		byte[] bytes = input.readAllBytes();
		char[] characters = new String(bytes).toCharArray();
		if (characters[0] == '\uFEFF') {
			bytes = Arrays.copyOfRange(bytes, 3, bytes.length);
		}
		return new ByteArrayInputStream(bytes);
	}
}
