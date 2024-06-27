package com.veda.hrentgelt.dlstools;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.veda.hrentgelt.dlstools.util.DLSFunctions;
import com.veda.hrentgelt.dlstools.util.IndexFileData;
import com.veda.hrentgelt.dlstools.util.LogPanel;
import com.veda.hrentgelt.dlstools.util.Table;
import com.veda.hrentgelt.dlstools.util.images.ImageHandler;
import com.veda.hrentgelt.dlstools.util.swing.*;
import com.veda.hrentgelt.dlstools.util.swing.styles.SpecialStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class DLSTools extends JFrame {
	private static DLSTools INSTANCE;

	//constant values
	private static final BasicLookAndFeel DARK_LAF = new SpecialStyle();
	private static final BasicLookAndFeel LIGHT_LAF = new FlatLightLaf();
	private static final String STAND = "27.06.2024";
	private static final String TITLE = "DLSTools   |  " + STAND;
	private static final String ADMIN_TITLE = TITLE + "  |  ADMIN";

	private static final String USER_NAME = System.getProperty("user.name");
	private static final String USER_HOME = System.getProperty("user.home");
	private static final String DLSTOOLS_HOME = USER_HOME + File.separator + ".dlstools";
	private static final File PROPERTIES_FILE = new File(DLSTOOLS_HOME + File.separator + "app.properties");
	private static final String[] ADMIN_USERS = getAdminUsers();
	private static final String ADMIN_PASSWORD = "jump"; // Supergeheimes Passwort...
	private static final String INDEX_FILE_NAME = "index.xml";
	public static final String AN_TABLE_NAME = "Arbeitnehmerstammdaten";
	public static final String SV_TABLE_NAME = "Sozialversicherungsdaten";
	public static final String BEAB_FILE_NAME = "be_abzuege";
	public static final String BBG_FILE_NAME = "BBG_Salden";
	public static final String VAG_FILE_NAME = "VAG_Daten";
	public static final String DURS_FILE_NAME = "Durchschnitte";
	public static final String SWBH_FILE_NAME = "SB-Daten";
	public static final String CSV_ENDING = ".csv";
	public static final String EXCEL_ENDING = ".xlsx";
	public static final String VALID_CHARSET_NAME = "ISO-8859-1";
	public static final String ANER_FILE_NAME = "Arbeitnehmerstammdatenergänzung";
	public static final String KK_FILE_NAME = "Krankenkassenliste" + EXCEL_ENDING;
	private static final Color GREEN_CONSOLE_COLOR = Color.GREEN;
	private static final int STANDARD_X = 50;
	private static final int STANDARD_Y = 50;
	private static final int STANDARD_WIDTH = 800;
	private static final int STANDARD_HEIGHT = 500;
	private static final String STANDARD_COMPANY_STRING = "001";
	private static final String STANDARD_ADMIN_SOURCE_PATH = "K:\\DATEN\\XYZ\\dta01\\migration";
	private static final String STANDARD_SOURCE_PATH = "C:\\Users\\" + USER_NAME;
	private static final String COLUMN_SEPARATOR = ";";
	private static final Font CONSOLE_FONT = new Font("Monospaced", Font.BOLD, 18);
	private static final Font BUTTON_FONT = new Font("ZapfDingbats", Font.PLAIN, 15);
	private static final DocumentBuilderFactory DOC_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();


	/*
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 */

	//variable values
	/*
	 * ---Property-Values-----------------------------------------------------------------------------------------------------------------
	 */
	private static final String COLOR_KEY = "COLOR";
	private Color currentColor;
	private static final String LAF_KEY = "LAF";
	private BasicLookAndFeel currentLAF;
	private static final String SOURCE_KEY = "SOURCEPATH";

	private JButton switchDesignButton;
	private JLabel currentSourcePathLabel;
	private static final String OLD_COMPANY_KEY = "OLD_COMPANY";
	private JLabel currentCompanyLabel;
	private static final String X_KEY = "X";
	private int x;
	private static final String Y_KEY = "Y";
	private int y;
	private static final String WIDTH_KEY = "WIDTH";
	private int width;
	private static final String HEIGHT_KEY = "HEIGHT";
	private int height;
	private static final String FULLSCREEN_KEY = "FULLSCREEN";
	private boolean isFullscreen;
	private static final String PENGUIN_KEY = "SHOW_PENGUIN";
	private boolean isPenguinVisible;

	/*
	 * ---NON-GUI-------------------------------------------------------------------------------------------------------------------------
	 */
	private Properties props = new Properties();
	private boolean isAdmin = false;
	private IndexFileData indexFileData;
	private static boolean isInitialized = false;


	/*
	 * ---GUI-----------------------------------------------------------------------------------------------------------------------------
	 */
	private JMenuBar menuBar;
	private final ArrayList<JButton> functionButtons = new ArrayList<>();
	private JButton deleteUnwantedLinesButton;
	private JButton deleteUnwantedAndOutdatedLinesButton;
	private JPopupMenu deleteUnwantedLinesMenu;
	private JPopupMenu deleteUnwantedAndOutdatedLinesMenu;
	private JPopupMenu mergeAdditionsMenu;
	private JPopupMenu validateFileMenu;
	private JButton createAdditionButton;
	private JButton mergeAdditionsButton;
	private JButton validateFilesButton;
	private JButton createKKListButton;
	private JButton prettyPrintButton;
	private JPanel mainPanel;
	private SourceAndCompanyWindow sourceAndCompanyWindow;
	private ColorDialog colorDialog;

	public static DLSTools getInstance() {
		if (INSTANCE == null) INSTANCE = new DLSTools();
		return INSTANCE;
	}

	private DLSTools() {
		initDocumentBuilderFactory();
		initAdminflag();
		initGUI();

		//Test:
//        GIFWindow gifW = new GIFWindow(this);
//        gifW.setVisible(true);
	}

	private void initDocumentBuilderFactory() {
		try {
			DOC_BUILDER_FACTORY.setValidating(false);
			DOC_BUILDER_FACTORY.setNamespaceAware(true);
			DOC_BUILDER_FACTORY.setFeature("http://xml.org/sax/features/namespaces", false);
			DOC_BUILDER_FACTORY.setFeature("http://xml.org/sax/features/validation", false);
			DOC_BUILDER_FACTORY.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			DOC_BUILDER_FACTORY.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (Exception e) {
			return;
		}
	}

	private void initGUI() {
		loadProperties();
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(getButtonsPanel(), BorderLayout.WEST);
		LogPanel l = LogPanel.getInstance();
		l.setLogFont(CONSOLE_FONT);
		l.setButtonFont(BUTTON_FONT);
		l.getTextArea().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				LogPanel l = LogPanel.getInstance();
				switch (e.getKeyCode()) {
					case KeyEvent.VK_F1:
						openHelp();
						break;
					case KeyEvent.VK_R:
						if (currentLAF instanceof FlatLaf) {
							setLAF(currentLAF, ColorUtils.getFittingColor((FlatLaf) currentLAF));
						}
						break;
					case KeyEvent.VK_L:
						l.setBreakLinesPolicy(!l.getBreakLinesPolicy());
						break;
					case KeyEvent.VK_ENTER:
						if (e.isAltDown() && e.isShiftDown() && e.isControlDown()) {
							if (isAdmin) {
								l.log(System.Logger.Level.INFO, "Sie sind bereits als ein Admin angemeldet.");
								return;
							}
							showLoginDialog();
						}
						break;
				}
			}
		});
		mainPanel.add(l, BorderLayout.CENTER);

		Icon ico = (currentLAF.equals(DARK_LAF)) ? ImageHandler.getImageIcon(ImageHandler.MOON_ICON, currentColor, 40, 40) : ImageHandler.getImageIcon(ImageHandler.SUN_ICON, currentColor, 40, 40);
		switchDesignButton = new JButton(ico);
		switchDesignButton.setFocusable(false);
		switchDesignButton.addActionListener(a -> {
			if (currentLAF.equals(DARK_LAF)) {
				setLAF(LIGHT_LAF, currentColor);
			} else {
				setLAF(DARK_LAF, currentColor);
			}
			Icon i = (currentLAF.equals(DARK_LAF)) ? ImageHandler.getImageIcon(ImageHandler.MOON_ICON, currentColor, 40, 40) : ImageHandler.getImageIcon(ImageHandler.SUN_ICON, currentColor, 40, 40);
			switchDesignButton.setIcon(i);
		});

		JPanel helpPanel = new JPanel(new BorderLayout());
		helpPanel.add(getSourcePathInfoPanel(), BorderLayout.CENTER);
		helpPanel.add(switchDesignButton, BorderLayout.EAST);
		mainPanel.add(helpPanel, BorderLayout.NORTH);

		initMenu();
		this.setJMenuBar(menuBar);


		if (isAdmin)
			this.setTitle(ADMIN_TITLE);
		else
			this.setTitle(TITLE);
		this.add(mainPanel);


		this.setBounds(x, y, width, height);
		if (isFullscreen) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}

		setLAF(currentLAF, currentColor);
		addWindowListener(
				new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						storeProperties();
					}
				}
		);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		sourceAndCompanyWindow = new SourceAndCompanyWindow(this);
		colorDialog = new ColorDialog(this);

		isInitialized = true;
	}


	private JPanel getSourcePathInfoPanel() {
		JPanel hp = new JPanel(new GridLayout(0, 1));
		JLabel sourceLabel = new JLabel("Quellpfad:    ");
		JLabel companyLabel = new JLabel("Firma:    ");
		String tooltip =
				"<html>" +
						"Der Quellordner aus dem die Dateien ausgelesen werden. Das beinhaltet folgende Struktur: " +
						"<p style=color:green>[Ausgewählter Root-Ordner] </p> " +
						"<p style=color:yellow>---> [FirmenOrdner1] </p> " +
						"<p style=color:red>--->---> [arbeitgeberstammdaten.csv] </p> " +
						"<p style=color:red>--->---> [index.xml] </p> " +
						"<p style=color:red>--->---> [...] </p> " +
						"<p style=color:yellow>---> [FirmenOrdner2] </p> " +
						"<p style=color:red>--->---> [...] </p> " +
						"<p style=color:yellow>---> [FirmenOrdnerX] </p> " +
						"<p style=color:red>--->---> [...] </p> " +
						"<p style=color:yellow>---> [Arbeitnehmerstammdatenergänzung]</h5> <small>(falls benötigt)</small> </p> " +
						"<p style=color:yellow>---> [weitere dateien (Vorarbeitgeberdaten, Durchschnitte, ...)] <small>(falls benötigt)</small> </p> " +
						"</html>";
		currentSourcePathLabel.setToolTipText(tooltip);
		sourceLabel.setToolTipText(tooltip);
		currentCompanyLabel.setToolTipText(tooltip);
		companyLabel.setToolTipText(tooltip);

		JPanel sourcePanel = new JPanel(new BorderLayout());
		sourcePanel.add(sourceLabel, BorderLayout.WEST);
		sourcePanel.add(currentSourcePathLabel, BorderLayout.CENTER);

		JPanel companyPanel = new JPanel(new BorderLayout());
		companyPanel.add(companyLabel, BorderLayout.WEST);
		companyPanel.add(currentCompanyLabel, BorderLayout.CENTER);

		hp.add(sourcePanel);
		hp.add(companyPanel);
		return hp;
	}

	private void initMenu() {
		menuBar = new JMenuBar();
		JMenu windowMenu = new JMenu("Fenster");
		JMenuItem documentationItem = new JMenuItem("Dokumentation/Hilfe");
		documentationItem.addActionListener(a -> openHelp());
		windowMenu.add(documentationItem);

		JMenu settingsMenu = new JMenu("Einstellungen");

		JMenuItem changeSourceAndCompanyItem = new JMenuItem("Firma / Quelle ändern");
		changeSourceAndCompanyItem.addActionListener(a -> changeSource());


		JMenuItem changeColorItem = new JMenuItem("Farbe anpassen");
		changeColorItem.addActionListener(a -> changeColor());

		settingsMenu.add(changeSourceAndCompanyItem);
		settingsMenu.add(changeColorItem);

		menuBar.add(windowMenu);
		menuBar.add(settingsMenu);
	}

	private void loadProperties() {
		LogPanel l = LogPanel.getInstance();
		if (!PROPERTIES_FILE.exists()) {
			storeProperties();
		}

		FileInputStream inStream;
		try {
			inStream = new FileInputStream(PROPERTIES_FILE);
			props.load(inStream);
		} catch (Exception e) {
			setStandardProperties();
			return;
		}

		String widthString = props.getProperty(WIDTH_KEY);
		String heightString = props.getProperty(HEIGHT_KEY);
		String xString = props.getProperty(X_KEY);
		String yString = props.getProperty(Y_KEY);
		String isFullscreenString = props.getProperty(FULLSCREEN_KEY);
		String currentColorString = props.getProperty(COLOR_KEY);
		String currentLAFString = props.getProperty(LAF_KEY);
		String currentSourcePathString = props.getProperty(SOURCE_KEY);
		String currentCompanyString = props.getProperty(OLD_COMPANY_KEY);

		try {
			width = Integer.parseInt(widthString);
		} catch (Exception e) {
			width = STANDARD_WIDTH;
		}
		try {
			height = Integer.parseInt(heightString);
		} catch (Exception e) {
			height = STANDARD_HEIGHT;
		}
		try {
			x = Integer.parseInt(xString);
		} catch (Exception e) {
			x = STANDARD_WIDTH;
		}
		try {
			y = Integer.parseInt(yString);
		} catch (Exception e) {
			y = STANDARD_Y;
		}
		isFullscreen = "true".equalsIgnoreCase(isFullscreenString.trim());

		try {
			String[] values = currentColorString.split(COLUMN_SEPARATOR);
			currentColor = new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		} catch (Exception e) {
			currentColor = GREEN_CONSOLE_COLOR;
		}

		if (DARK_LAF.getName().equals(currentLAFString)) {
			currentLAF = DARK_LAF;
		} else {
			currentLAF = LIGHT_LAF;
		}

		File f = new File(currentSourcePathString.trim());
		if (!f.exists()) {
			currentSourcePathLabel = isAdmin ? new JLabel(STANDARD_ADMIN_SOURCE_PATH) : new JLabel(STANDARD_SOURCE_PATH);
		} else {
			currentSourcePathLabel = new JLabel(f.getAbsolutePath());
		}

		if (currentCompanyString == null || currentCompanyString.isEmpty()) {
			currentCompanyString = STANDARD_COMPANY_STRING;
		}
		if ((currentCompanyString = currentCompanyString.trim()).length() > 3) {
			l.log(System.Logger.Level.ERROR, (ResourceBundle) null, "Firma " + currentCompanyString + " ist unzulässig. Verkürze auf 3 Stellen.");
			currentCompanyString = currentCompanyString.substring(0, 3);
		}
		currentCompanyLabel = new JLabel(currentCompanyString);
		setSourcePath(new File(getSourcePath()), getCompany());
	}

	private void storeProperties() {
		LogPanel l = LogPanel.getInstance();
		if (!PROPERTIES_FILE.exists()) {
			setStandardProperties();
			l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "Properties Datei nicht vorhanden. Lege Datei an...");
			if (!PROPERTIES_FILE.getParentFile().exists()) {
				if (PROPERTIES_FILE.getParentFile().mkdirs()) {
					l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "DLSTools-Home-Ordner angelegt");
				} else {
					l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "DLSTools-Home-Ordner konnte nicht angelegt werden");
				}
			}
			try {
				if (PROPERTIES_FILE.createNewFile()) {
					l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "Properties Datei angelegt");
				} else {
					l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "Properties Datei konnte nicht angelegt werden.");
				}
			} catch (IOException e) {
				l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "Properties Datei konnte nicht angelegt werden.");
			}
		}

		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(PROPERTIES_FILE);
		} catch (FileNotFoundException e) {
			l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "properties konnten nicht gespeichert werden: " + e.getMessage());
			return;
		}

		//TODO: sichern
		props.put(FULLSCREEN_KEY, String.valueOf(getExtendedState() >= 2));
		if (getExtendedState() >= 2) {
			//Fulscreen
			setExtendedState(NORMAL);
		}
		props.put(WIDTH_KEY, String.valueOf(getWidth()));
		props.put(HEIGHT_KEY, String.valueOf(getHeight()));
		Point p = getLocation();
		props.put(X_KEY, String.valueOf(p.x));
		props.put(Y_KEY, String.valueOf(p.y));
		props.put(COLOR_KEY, currentColor.getRed() + COLUMN_SEPARATOR + currentColor.getGreen() + COLUMN_SEPARATOR
				+ currentColor.getBlue());
		props.put(LAF_KEY, currentLAF.getName());
		props.put(SOURCE_KEY, getSourcePath());
		props.put(OLD_COMPANY_KEY, getCompany());
		try {
			props.store(outStream, "Properties");
		} catch (IOException e) {
			l.log(System.Logger.Level.DEBUG, (ResourceBundle) null, "properties konnten nicht gespeichert werden: " + e.getMessage());
		}
	}

	private void setStandardProperties() {
		width = STANDARD_WIDTH;
		height = STANDARD_HEIGHT;
		x = STANDARD_X;
		y = STANDARD_Y;
		isFullscreen = false;
		currentColor = GREEN_CONSOLE_COLOR;
		currentLAF = DARK_LAF;
		if (isAdmin)
			currentSourcePathLabel = new JLabel(STANDARD_ADMIN_SOURCE_PATH);
		else
			currentSourcePathLabel = new JLabel(STANDARD_SOURCE_PATH);
		isPenguinVisible = false;

		this.setBounds(x, y, width, height);
		if (isFullscreen) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

	private static String[] getAdminUsers() {
		InputStream stream = DLSTools.class.getResourceAsStream("files/users.txt");
		try {
			if (stream == null) return new String[0];
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			ArrayList<String> list = new ArrayList<>();
			reader.lines().forEach(s -> {
				if (s.contains("//")) {
					s = s.substring(0, s.indexOf("//")).trim();
				}

				if (!s.isEmpty())
					list.add(s);
			});

			String[] ret = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ret[i] = list.get(i);
			}
			return ret;
		} catch (Exception e) {
			return new String[0];
		}
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	private void initAdminflag() {
		for (String usr : ADMIN_USERS) {
			if (usr.equalsIgnoreCase(USER_NAME)) {
				isAdmin = true;
				return;
			}
		}
	}

	public void unlockAdminMode(char[] password) {
		StringBuilder s = new StringBuilder();
		for (char c : password) {
			s.append(c);
		}
		if (s.toString().equals(ADMIN_PASSWORD)) {
			setAdminState(true);
		}
	}

	private void showLoginDialog() {
		LoginDialog dialog = new LoginDialog(this);
		dialog.setVisible(true);
	}

	private void setAdminState(boolean b) {
		isAdmin = b;
		if (isAdmin)
			this.setTitle(ADMIN_TITLE);
		else
			this.setTitle(TITLE);
		// Funktionsbuttons enablen / disablen
		for (JButton button : functionButtons) {
			if (button.equals(createAdditionButton)) {
				continue;
			}
			button.setEnabled(isAdmin);
		}
	}

	private JPanel getButtonsPanel() {
		//Buttons
		deleteUnwantedLinesButton = new JButton("Überflüssige Zeilen entfernen");
		JMenuItem deleteUnwantedANDataItem = new JMenuItem("AN-Daten");
		deleteUnwantedANDataItem.addActionListener(a -> DLSFunctions.removeUnusableANLines(indexFileData, new File(getPath(AN_TABLE_NAME)), false));
		JMenuItem deleteUnwantedSVDataItem = new JMenuItem("SV-Daten");
		deleteUnwantedSVDataItem.addActionListener(a -> DLSFunctions.removeUnusableSVLines(indexFileData, new File(getPath(SV_TABLE_NAME)), false));
		JMenuItem deleteAllUnwantedDataItem = new JMenuItem("Beide");
		deleteAllUnwantedDataItem.addActionListener(a -> {
			deleteUnwantedANDataItem.doClick();
			deleteUnwantedSVDataItem.doClick();
		});

		deleteUnwantedLinesMenu = new JPopupMenu();
		deleteUnwantedLinesMenu.add(deleteUnwantedANDataItem);
		deleteUnwantedLinesMenu.add(deleteUnwantedSVDataItem);
		deleteUnwantedLinesMenu.add(deleteAllUnwantedDataItem);
		deleteUnwantedLinesButton.addActionListener(a -> {
			deleteUnwantedLinesMenu.show(deleteUnwantedLinesButton, deleteUnwantedLinesButton.getWidth(), 0);
		});
		deleteUnwantedLinesButton.setToolTipText("Entfernt die überflüssigen Zeilen der SV-/AN-Daten.");


		deleteUnwantedAndOutdatedLinesButton = new JButton("Überflüssige und veraltete Zeilen entfernen");
		JMenuItem deleteUnwantedAndOutdatedANDataItem = new JMenuItem("AN-Daten");
		deleteUnwantedAndOutdatedANDataItem.addActionListener(a -> DLSFunctions.removeUnusableANLines(indexFileData, new File(getPath(AN_TABLE_NAME)), true));
		JMenuItem deleteUnwantedAndOutdatedSVDataItem = new JMenuItem("SV-Daten");
		deleteUnwantedAndOutdatedSVDataItem.addActionListener(a -> DLSFunctions.removeUnusableSVLines(indexFileData, new File(getPath(SV_TABLE_NAME)), true));
		JMenuItem deleteAllUnwantedAndOutdatedDataItem = new JMenuItem("Beide");
		deleteAllUnwantedAndOutdatedDataItem.addActionListener(a -> {
			deleteUnwantedAndOutdatedANDataItem.doClick();
			deleteUnwantedAndOutdatedSVDataItem.doClick();
		});
		deleteUnwantedAndOutdatedLinesMenu = new JPopupMenu();
		deleteUnwantedAndOutdatedLinesMenu.add(deleteUnwantedAndOutdatedANDataItem);
		deleteUnwantedAndOutdatedLinesMenu.add(deleteUnwantedAndOutdatedSVDataItem);
		deleteUnwantedAndOutdatedLinesMenu.add(deleteAllUnwantedAndOutdatedDataItem);
		deleteUnwantedAndOutdatedLinesButton.addActionListener(a -> {
			deleteUnwantedAndOutdatedLinesMenu.show(deleteUnwantedAndOutdatedLinesButton, deleteUnwantedAndOutdatedLinesButton.getWidth(), 0);
		});
		deleteUnwantedAndOutdatedLinesButton.setToolTipText("Entfernt die überflüssigen Zeilen der SV-/AN-Daten sowie die Datensätze die nicht im Jahr " + DLSFunctions.getCurrentYear() + " liegen.");


		createAdditionButton = new JButton("Ergänzungsdatei erstellen");
		createAdditionButton.addActionListener(a -> DLSFunctions.createAddition(indexFileData, new File(getPath(AN_TABLE_NAME)), getCompany()));
		createAdditionButton.setToolTipText("Erstellt eine Arbeitnehmerstammdatenergänzung auf Basis der vorliegenden Arbeitnehmerstammdaten.");


		mergeAdditionsButton = new JButton("Ergänzungen zusammensetzen");
		JMenuItem mergeWithHeaderItem = new JMenuItem("Mit Überschrift");
		mergeWithHeaderItem.addActionListener(a -> DLSFunctions.mergeAdditions(new File(getSourcePath()), true));
		JMenuItem mergeWithoutHeaderItem = new JMenuItem("Ohne Überschrift");
		mergeWithoutHeaderItem.addActionListener(a -> DLSFunctions.mergeAdditions(new File(getSourcePath()), false));
		mergeAdditionsMenu = new JPopupMenu();
		mergeAdditionsMenu.add(mergeWithHeaderItem);
		mergeAdditionsMenu.add(mergeWithoutHeaderItem);
		mergeAdditionsButton.addActionListener(a -> mergeAdditionsMenu.show(mergeAdditionsButton, mergeAdditionsButton.getWidth(), 0));
		mergeAdditionsButton.setToolTipText("Setzt die Ergänzungen im Quellordner zu einer großen Datei zusammen.");


		validateFilesButton = new JButton("Dateien prüfen");
		JMenuItem allDLSFileItem = new JMenuItem("DLS-Dateien");
		allDLSFileItem.addActionListener(a -> DLSFunctions.checkDLSFiles(indexFileData));
		JMenuItem BEABFileItem = new JMenuItem("Be-/Abzüge");
		BEABFileItem.addActionListener(a -> DLSFunctions.checkBEAB(new File(getPath(BEAB_FILE_NAME))));
		JMenuItem BBGFileItem = new JMenuItem("BBG-Salden");
		BBGFileItem.addActionListener(a -> DLSFunctions.checkBBG(new File(getPath(BBG_FILE_NAME))));
		JMenuItem VAGFileItem = new JMenuItem("VAG-Daten");
		VAGFileItem.addActionListener(a -> DLSFunctions.checkVAG(new File(getPath(VAG_FILE_NAME))));
		JMenuItem DURSFileItem = new JMenuItem("Durchschnitte");
		DURSFileItem.addActionListener(a -> DLSFunctions.checkDURS(new File(getPath(DURS_FILE_NAME))));
		JMenuItem SWBHFileItem = new JMenuItem("Schwerbehindertendaten");
		SWBHFileItem.addActionListener(a -> DLSFunctions.checkSWBH(new File(getPath(SWBH_FILE_NAME))));
		validateFileMenu = new JPopupMenu();
		validateFileMenu.add(allDLSFileItem);
		validateFileMenu.add(BEABFileItem);
		validateFileMenu.add(BBGFileItem);
		validateFileMenu.add(VAGFileItem);
		validateFileMenu.add(DURSFileItem);
		validateFileMenu.add(SWBHFileItem);
		validateFilesButton.addActionListener(a -> validateFileMenu.show(validateFilesButton, validateFilesButton.getWidth(), 0));
		validateFilesButton.setToolTipText("Prüft die ausgewählten Dateien.");


		createKKListButton = new JButton("Liste Krankenkassen erstellen");
		createKKListButton.addActionListener(a -> DLSFunctions.createKrankenKassenListe(indexFileData, new File(getPath(SV_TABLE_NAME))));
		createKKListButton.setToolTipText("Erstellt eine Liste aller Krankenkassen, welche in den SV-Daten vorkommen.\n(KEYS:" + DLSFunctions.BETR_NR_KK + "," + DLSFunctions.KASSENBEZ + ")");


		prettyPrintButton = new JButton("Pretty Print");
		prettyPrintButton.addActionListener(a -> DLSFunctions.prettyPrint(indexFileData.getFile()));
		prettyPrintButton.setToolTipText("Erstellt eine Kopie der ausgewählten index.xml und wendet die \"Pretty-Print\"-Funktion auf diese an.");

		functionButtons.add(deleteUnwantedLinesButton);
		functionButtons.add(deleteUnwantedAndOutdatedLinesButton);
		functionButtons.add(createAdditionButton);
		functionButtons.add(mergeAdditionsButton);
		functionButtons.add(validateFilesButton);
		functionButtons.add(createKKListButton);
		functionButtons.add(prettyPrintButton);

		JPanel buttonsPanel = new JPanel(new GridLayout(0, 1));

		functionButtons.forEach(b -> {
			b.setFont(BUTTON_FONT);
			b.setFocusable(false);
			buttonsPanel.add(b);
			b.setEnabled(isAdmin || b.equals(createAdditionButton));
		});

		return buttonsPanel;
	}

	public String getPath(String tableName) {
		if (indexFileData == null) return "File:" + tableName;
		String companyPath = getSourcePath() + File.separator + getCompany();
		String url;
		switch (tableName) {
			case AN_TABLE_NAME:
				url = indexFileData.getTable(AN_TABLE_NAME).URL;
				return (url == null || url.isEmpty()) ? companyPath + File.separator + ANER_FILE_NAME + CSV_ENDING : url;
			case SV_TABLE_NAME:
				url = indexFileData.getTable(SV_TABLE_NAME).URL;
				return (url == null || url.isEmpty()) ? companyPath + File.separator + ANER_FILE_NAME + CSV_ENDING : url;
			case ANER_FILE_NAME:
				return getSourcePath() + File.separator + ANER_FILE_NAME + CSV_ENDING;
			case BEAB_FILE_NAME:
				return getSourcePath() + File.separator + BEAB_FILE_NAME + CSV_ENDING;
			case BBG_FILE_NAME:
				return getSourcePath() + File.separator + BBG_FILE_NAME + CSV_ENDING;
			case VAG_FILE_NAME:
				return getSourcePath() + File.separator + VAG_FILE_NAME + CSV_ENDING;
			case DURS_FILE_NAME:
				return getSourcePath() + File.separator + DURS_FILE_NAME + CSV_ENDING;
			case SWBH_FILE_NAME:
				return getSourcePath() + File.separator + SWBH_FILE_NAME + CSV_ENDING;
			default:
				LogPanel.getInstance().log(System.Logger.Level.DEBUG, "Dateipfad konnte nicht gebaut werden, bitte beheben: " + tableName);
				return getSourcePath() + File.separator + tableName;
		}
	}

	public void setLAF(BasicLookAndFeel lookAndFeel, Color c) {
		LogPanel l = LogPanel.getInstance();

		if (!currentLAF.equals(lookAndFeel)) {
			currentLAF = lookAndFeel;
			l.log(System.Logger.Level.INFO, "Design angepasst, neues Design: " + lookAndFeel.getName());
		}

		if (c != null) {
			if (!ColorUtils.isFittingColor((FlatLaf) currentLAF, c)) {
				c = ColorUtils.getFittingColor((FlatLaf) currentLAF);
			}
			if (!currentColor.equals(c)) {
				l.log(System.Logger.Level.INFO, "Farbe angepasst, neue Farbe: " + String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
			}
			currentColor = c;
		}


		SwingUtils.updateLAF(lookAndFeel, currentColor);
		updatePopupMenuColors();
		updateButtonColors();
		functionButtons.forEach(b -> b.setForeground(currentColor));

		//weitere Komponenten, die die Farbe übernehmen müssen
		this.setIconImage(ImageHandler.getImageIcon(ImageHandler.DLSTOOLS_ICON, currentColor, 64, 64).getImage());
		LogPanel.getInstance().setColor(currentColor);
		if (sourceAndCompanyWindow != null)
			sourceAndCompanyWindow.setColor(currentColor);
		if (colorDialog != null)
			colorDialog.setColor(currentColor);


		revalidate();
	}


	public void openHelp() {
		if (isAdmin)
			openDocumentation();
		else
			openDocumentationForCustomer();
	}

	private void openDocumentationForCustomer() {
		URL url = DLSTools.class.getResource("files/documentation.pdf");
		try {
			Desktop.getDesktop().open(new File(url.getFile()));
		} catch (Exception e) {
			LogPanel.getInstance().log(System.Logger.Level.ERROR, "Dokumentation " + url + " konnte nicht geöffnet werden");
		}
	}

	/**
	 * opens the applications documentation in the users standardbrowser
	 */
	private void openDocumentation() {

		try {
			URL documentationURL = new URL(
					"https://veda-group.atlassian.net/wiki/spaces/BUHRE/pages/1174175758/Java-Tool+zur+Migration+der+DLS-Schnittstelle");
			URI uri = documentationURL.toURI();
			Desktop.getDesktop().browse(uri);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			LogPanel.getInstance().log(System.Logger.Level.ERROR, (ResourceBundle) null, "Dokumententation konnte nicht geöffnet werden. Folgen Sie Diesem Link: "
					+ "https://veda-group.atlassian.net/wiki/spaces/BUHRE/pages/1174175758/Java-Tool+zur+Migration+der+DLS-Schnittstelle");
		}
	}

	private void changeSource() {
		sourceAndCompanyWindow.open(getSourcePath(), getCompany());
	}

	private void changeColor() {
		colorDialog.open();
	}

	private void updatePopupMenuColors() {
		SwingUtilities.updateComponentTreeUI(deleteUnwantedLinesMenu);
		SwingUtilities.updateComponentTreeUI(deleteUnwantedAndOutdatedLinesMenu);
		SwingUtilities.updateComponentTreeUI(mergeAdditionsMenu);
		SwingUtilities.updateComponentTreeUI(validateFileMenu);
	}

	private void updateButtonColors() {
		Icon switchButtonIcon = (currentLAF.equals(DARK_LAF)) ? ImageHandler.getImageIcon(ImageHandler.MOON_ICON, currentColor, 40, 40) : ImageHandler.getImageIcon(ImageHandler.SUN_ICON, currentColor, 40, 40);
		switchDesignButton.setIcon(switchButtonIcon);
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public BasicLookAndFeel getCurrentLAF() {
		return currentLAF;
	}

	public static void main(String[] args) {
		DLSTools.getInstance().setVisible(true);
	}

	public void setSourcePath(File path, String oldCompany) {
		if (path == null || !path.isDirectory()) {
			LogPanel.getInstance().log(System.Logger.Level.ERROR, (ResourceBundle) null, "Der angegebene Pfad ist leer, existiert nicht oder ist kein zulässiger Ordner: " + path);
			return;
		}
		if (oldCompany == null || (oldCompany = oldCompany.trim()).length() > 3) {
			LogPanel.getInstance().log(System.Logger.Level.ERROR, "Die angegebene Firma " + oldCompany + " ist unzulässig");
			return;
		}

		File indexFile = new File(path.getAbsolutePath() + File.separator + oldCompany + File.separator + INDEX_FILE_NAME);
		if (!indexFile.exists()) {
			LogPanel.getInstance().log(System.Logger.Level.ERROR, "Die indexDatei " + indexFile.getAbsolutePath() + " konnte im ausgewählten Ordner nicht gefunden werden.");
			return;
		}
		try {
			saveData(indexFile, oldCompany);
		} catch (Exception e) {
			LogPanel logger = LogPanel.getInstance();
			logger.log(System.Logger.Level.ERROR, "Die Daten der Index-Datei konnten nicht gespeichert werden. Fehler: " + e.getMessage());
			logger.log(System.Logger.Level.INFO, "Dieser Fehler ist ggf. aufgrund einer falschen Codierung aufgetreten. Richtig ist nur die Codierung UTF-8!");
			return;
		}
		currentSourcePathLabel.setText(path.getAbsolutePath());
		currentCompanyLabel.setText(oldCompany);
		LogPanel.getInstance().log(System.Logger.Level.INFO, "Quellpfad und Firma wurden übernommen.");
	}

	private void saveData(File indexFile, String oldCompany) throws
			IOException, SAXException, ParserConfigurationException {
		indexFileData = new IndexFileData(indexFile);
		DocumentBuilder db = DOC_BUILDER_FACTORY.newDocumentBuilder();

		InputStream src = DLSFunctions.getFileInputStreamFromFile(indexFile);
		Document doc = db.parse(src);

		//https://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("Table");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				String name = e.getElementsByTagName("Name").item(0).getTextContent();
				String url = e.getElementsByTagName("URL").item(0).getTextContent();
				url = indexFile.getParent() + File.separator + url;
				Element variableLength = (Element) (e.getElementsByTagName("VariableLength").item(0));
				if (variableLength == null) {
					LogPanel.getInstance().log(System.Logger.Level.ERROR, "Spalten der " + name + " konnten nicht ermittelt werden.");
					return;
				}

				NodeList allNodes = variableLength.getChildNodes();
				ArrayList<Node> variableColumns = new ArrayList<>();
				for (int idx = 0; idx < allNodes.getLength(); idx++) {
					Node current = allNodes.item(idx);
					if ("VariablePrimaryKey".equalsIgnoreCase(current.getNodeName())
							|| "VariableColumn".equalsIgnoreCase(current.getNodeName()))
						variableColumns.add(current);
				}

				if (variableColumns.isEmpty()) {
					LogPanel.getInstance().log(System.Logger.Level.ERROR, "Spalten der " + name + " konnten nicht ermittelt werden.");
					return;
				}
				String[] keys = new String[variableColumns.size()];
				for (int col = 0; col < variableColumns.size(); col++) {
					keys[col] = ((Element) variableColumns.get(col)).getElementsByTagName("Name").item(0).getTextContent();
				}
				indexFileData.add(new Table(name, url, keys));
			}
		}
		if (indexFileData.getTables().size() == 0) {
			throw new RuntimeException("Tabellen der Index.xml konnten nicht ermittelt werden.");
		}
	}


	public String getCompany() {
		if (currentCompanyLabel == null) return STANDARD_COMPANY_STRING;
		return currentCompanyLabel.getText();
	}

	public String getSourcePath() {
		return currentSourcePathLabel.getText();
	}

	public static boolean isInitialized() {
		return isInitialized;
	}
}
