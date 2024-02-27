package com.veda.hrentgelt.dlstools.util;

import java.util.List;

public class Spaltenbeschreibungen {
	private static final String COLUMN_SEPARATOR = ";";

	private Spaltenbeschreibungen() {
	}

	public static final Spaltenbeschreibung[] ERGAENZUNG_SPALTENBESCHREIBUNG = {
			new Spaltenbeschreibung('A', 1, 64, 0, "Alte Personalnummer", "", true),
			new Spaltenbeschreibung('N', 2, 6, 0, "Neue Personalnummer", "", true),
			new Spaltenbeschreibung('A', 3, 3, 0, "Firma", "", true),

			new Spaltenbeschreibung('N', 4, 1, 0, "Abrechnungsgruppe", "", false, 1, 2, 3, 4, 5, 6),
			new Spaltenbeschreibung('A', 5, 6, 0, "Abteilung", "", false),
			new Spaltenbeschreibung('A', 6, 10, 0, "Kostenstelle", "", false),
			new Spaltenbeschreibung('A', 7, 3, 0, "Taetigkeit", "", false),
			new Spaltenbeschreibung('A', 8, 3, 0, "Sachbereich", "", false),
			new Spaltenbeschreibung('A', 9, 33, 0, "Beschaeftigungstext", "", false),
			new Spaltenbeschreibung('N', 10, 9, 2, "Versorgungsbezug im Erstmonat", "", false),
			new Spaltenbeschreibung('A', 11, 5, 0, "Betriebsstaettennummer", "", false),
			new Spaltenbeschreibung('A', 12, 2, 0, "Rentenart", "", false),
			new Spaltenbeschreibung('A', 13, 3, 0, "Geburtsland", "", false),
			new Spaltenbeschreibung('A', 14, 3, 0, "Land Doppelbesteuerungsabkommen", "", false),
			new Spaltenbeschreibung('A', 15, 1, 0, "Kennzeichen Hauptarbeitgeber", "", false),
			new Spaltenbeschreibung('A', 16, 1, 0, "Familienstand", "", false),
			new Spaltenbeschreibung('A', 17, 1, 0, "Zahlungsart", "", false),
			new Spaltenbeschreibung('A', 18, 33, 0, "Geburtsort", "", false),
			new Spaltenbeschreibung('A', 19, 3, 0, "Firma Zeitwirtschaft", "", false),
			new Spaltenbeschreibung('A', 20, 2, 0, "Einzugsstelle Zusatzversorgung", "", false),
			new Spaltenbeschreibung('N', 21, 5, 2, "Arbeitszeit monatlich", "", false),
			new Spaltenbeschreibung('N', 22, 4, 2, "Arbeitszeit taeglich", "", false),
			new Spaltenbeschreibung('A', 23, 2, 0, "Berufsgenossenschaft 1", "", false),
			new Spaltenbeschreibung('N', 24, 5, 2, "Berufsgenossenschaft 1 (%-Satz)", "", false),
			new Spaltenbeschreibung('A', 25, 8, 0, "Gefahrtarifstelle 1", "", false),
			new Spaltenbeschreibung('A', 26, 2, 0, "Berufsgenossenschaft 2", "", false),
			new Spaltenbeschreibung('N', 27, 5, 2, "Berufsgenossenschaft 2 (%-Satz)", "", false),
			new Spaltenbeschreibung('A', 28, 8, 0, "Gefahrtarifstelle 2", "", false),
			new Spaltenbeschreibung('A', 29, 2, 0, "Berufsgenossenschaft 3", "", false),
			new Spaltenbeschreibung('N', 30, 5, 2, "Berufsgenossenschaft 3 (%-Satz)", "", false),
			new Spaltenbeschreibung('A', 31, 8, 0, "Gefahrtarifstelle 3", "", false),
			new Spaltenbeschreibung('A', 34, 8, 0, "Name der Grundvergütungstabelle", "", false),
			new Spaltenbeschreibung('A', 35, 8, 0, "Tarif der Grundvergütungstabelle", "", false),
			new Spaltenbeschreibung('N', 36, 3, 0, "Vergütungsstufe", "", false),

			new Spaltenbeschreibung('A', 32, Integer.MAX_VALUE, 0, "EMPTY_FIELD", "", false),
			new Spaltenbeschreibung('A', 33, Integer.MAX_VALUE, 0, "EMPTY_FIELD", "", false),

			new Spaltenbeschreibung('A', 37, 17, 0, "Berufsst Versorgungswerk Mitgliedsnummer", "", false),
			new Spaltenbeschreibung('A', 38, 15, 0, "Echte Krankenkasse (bei PGS109/110) (Achtung: Vor dem AutoUpdate 05.03.2024 ist der zweistellige Schlüssel korrekt)", "", false),
			new Spaltenbeschreibung('A', 39, 8, 0, "Stundenlohn", "", false),
			new Spaltenbeschreibung('A', 40, 8, 0, "Stundenlohnempfänger", "", false),
			new Spaltenbeschreibung('A', 41, 8, 0, "KV-Vorgabe Gesamt", "", false),
			new Spaltenbeschreibung('A', 42, 8, 0, "KV-Vorgabe AG", "", false),
			new Spaltenbeschreibung('A', 43, 8, 0, "PV-Vorgabe Gesamt", "", false),
			new Spaltenbeschreibung('A', 44, 8, 0, "PV-Vorgabe AG", "", false),
			new Spaltenbeschreibung('A', 45, 8, 0, "Basistarif anteil Arbeitnehmer", "", false),
			new Spaltenbeschreibung('A', 46, 8, 0, "Übergangsbereich abgerechnet", "", false),

			new Spaltenbeschreibung('A', 32, Integer.MAX_VALUE, 0, "EMPTY_FIELD", "", false),
			new Spaltenbeschreibung('A', 33, Integer.MAX_VALUE, 0, "EMPTY_FIELD", "", false),

			new Spaltenbeschreibung('A', 47, 8, 0, "Akademischer Titel", "", false),
			new Spaltenbeschreibung('A', 48, 8, 0, "Tätigkeitsschlüssel", "", false),
	};

	public static final Spaltenbeschreibung[] BEZUEGE_ABZUEGE_SPALTENBESCHREIBUNG = {
			new Spaltenbeschreibung('A', 1, 3, 0, "Firma", "", true),
			new Spaltenbeschreibung('N', 2, 6, 0, "Personalnummer", "", true),

			new Spaltenbeschreibung('A', 3, 3, 0, "Lohnart", "", false),
			new Spaltenbeschreibung('A', 4, 20, 0, "Lohnartentext", "", false),
			new Spaltenbeschreibung('N', 5, 9, 2, "Betrag", "", true),
			new Spaltenbeschreibung('A', 6, 3, 0, "Währung", "", false),
			new Spaltenbeschreibung('N', 7, 9, 2, "Stunden", "", false),
			new Spaltenbeschreibung('N', 8, 2, 0, "Von-Monat", "", false),
			new Spaltenbeschreibung('N', 9, 4, 0, "Von-Jahr", "", false),
			new Spaltenbeschreibung('N', 10, 2, 0, "Bis-Monat", "", false),
			new Spaltenbeschreibung('N', 11, 4, 0, "Bis-Jahr", "", false),
			new Spaltenbeschreibung('A', 12, 2, 0, "Zahlungsweise", "", false),
			new Spaltenbeschreibung('A', 13, 1, 0, "Zahlweg", "", false),
			new Spaltenbeschreibung('A', 14, 1, 0, "Zahlungsart", "", false),
			new Spaltenbeschreibung('N', 15, 5, 2, "Anteil VL des Arbeitgebers", "", false),
			new Spaltenbeschreibung('N', 16, 2, 0, "Von-Monat des VL-Anteils des Arbeitgebers", "", false),
			new Spaltenbeschreibung('N', 17, 4, 0, "Von-Jahr des VL-Anteils des Arbeitgebers", "", false),
			new Spaltenbeschreibung('A', 18, 27, 0, "Zahlungsempfänger", "", false),
			new Spaltenbeschreibung('A', 19, 11, 0, "BIC", "", false),
			new Spaltenbeschreibung('A', 20, 34, 0, "IBAN", "", false),
			new Spaltenbeschreibung('T', 21, 8, 0, "Wirksam ab Uhrzeit", "hh:mm:ss", false), // TODO
			new Spaltenbeschreibung('A', 22, 35, 0, "Aktenzeichen Pfändungen", "", false),
			new Spaltenbeschreibung('A', 23, 1, 0, "Anzahl Unterhaltsberechtigte", "", false),
			new Spaltenbeschreibung('N', 24, 9, 2, "Aktueller Rückstand", "", false),
			new Spaltenbeschreibung('N', 25, 9, 2, "Ursprünglicher Rückstand", "", false),
			new Spaltenbeschreibung('N', 26, 9, 2, "Laufende Forderung", "", false),
			new Spaltenbeschreibung('N', 27, 5, 2, "Persönlicher Freibetrag", "", false),
			new Spaltenbeschreibung('N', 28, 2, 0, "Anteil Mehrbetrag (Zähler)", "", false),
			new Spaltenbeschreibung('N', 29, 2, 0, "Anteil Mehrbetrag (Nenner)", "", false),
			new Spaltenbeschreibung('A', 30, 1, 0, "Zeitgleiche Pfändungen", "", false),
			new Spaltenbeschreibung('A', 31, 2, 0, "Rangfolge Pfändungen", "", false),
			new Spaltenbeschreibung('A', 32, 20, 0, "Vertragsnummer", "", false),
			new Spaltenbeschreibung('A', 33, 27, 0, "Verwendungszweck", "", false),
			new Spaltenbeschreibung('A', 34, 27, 0, "Begünstigter", "", false)};

	public static final Spaltenbeschreibung[] BBG_SALDEN_SPALTENBESCHREIBUNG = {
			new Spaltenbeschreibung('A', 1, 3, 0, "Firma", "", true),
			new Spaltenbeschreibung('N', 2, 4, 0, "Abrechnungsjahr", "", true),
			new Spaltenbeschreibung('N', 3, 6, 0, "Personalnummer", "", true),

			new Spaltenbeschreibung('N', 4, 7, 2, "KV-Luft", "", false),
			new Spaltenbeschreibung('N', 5, 7, 2, "RV-Luft", "", false),
			new Spaltenbeschreibung('N', 6, 7, 2, "BA-Luft", "", false),
			new Spaltenbeschreibung('N', 7, 7, 2, "PV-Luft", "", false),
			new Spaltenbeschreibung('N', 8, 7, 2, "Umlagebasis", "", false),
			new Spaltenbeschreibung('N', 9, 7, 2, "Beitragsbemessungsgrenze Insolvenzumlage", "", false),
			new Spaltenbeschreibung('N', 10, 7, 2, "Bemessungsgrenze für EGA bei Berufsständischen", "", false)};

	public static final Spaltenbeschreibung[] VAG_SPALTENBESCHREIBUNG = {
			new Spaltenbeschreibung('A', 1, 3, 0, "Firma", "", true),
			new Spaltenbeschreibung('N', 2, 4, 0, "Abrechnungsjahr", "", true),
			new Spaltenbeschreibung('N', 3, 6, 0, "Personalnummer", "", true),

			new Spaltenbeschreibung('D', 4, 10, 0, "Beschäftigt von Datum", "dd.mm.yyyy", false),
			new Spaltenbeschreibung('D', 5, 10, 0, "Beschäftigt bis Datum", "dd.mm.yyyy", false),
			new Spaltenbeschreibung('T', 6, 8, 0, "Beginn der Fehlzeit", "hh:mm:ss", false),
			new Spaltenbeschreibung('T', 7, 8, 0, "Ende der Fehlzeit", "hh:mm:ss", false),
			new Spaltenbeschreibung('A', 8, 1, 0, "Grund der Fehlzeit", "", false),
			new Spaltenbeschreibung('N', 9, 9, 2, "Steuerbrutto", "", false),
			new Spaltenbeschreibung('N', 10, 9, 2, "Lohnsteuer", "", false),
			new Spaltenbeschreibung('N', 11, 9, 2, "Solidaritätszuschlag", "", false),
			new Spaltenbeschreibung('N', 12, 9, 2, "Kirchensteuer Arbeitnehmer", "", false),
			new Spaltenbeschreibung('N', 13, 9, 2, "Kirchensteuer Ehegatte", "", false),
			new Spaltenbeschreibung('N', 14, 11, 2, "Abfindungen", "", false),
			new Spaltenbeschreibung('N', 15, 11, 2, "Vorbezüge: Entgelt mehrere Jahre", "", false),
			new Spaltenbeschreibung('N', 16, 7, 2, "Mutterschaftsgeld", "", false),
			new Spaltenbeschreibung('N', 17, 9, 2, "Kurzarbeitergeld", "", false),
			new Spaltenbeschreibung('N', 18, 2, 0, "Bezugsmonate Kurzarbeitergeld", "", false),
			new Spaltenbeschreibung('N', 19, 2, 0, "Anzahl Unterbrechnungen", "", false),
			new Spaltenbeschreibung('N', 20, 7, 2, "Verbeitragstes Entgelt KV", "", false),
			new Spaltenbeschreibung('N', 21, 7, 2, "Verbeitragstes Entgelt RV", "", false),
			new Spaltenbeschreibung('N', 22, 7, 2, "Verbeitragstes Entgelt BA", "", false),
			new Spaltenbeschreibung('N', 23, 7, 2, "Verbeitragstes Entgelt PV", "", false),
			new Spaltenbeschreibung('N', 24, 7, 2, "Verbeitragstes Entgelt Insolvenzumlage", "", false),
			new Spaltenbeschreibung('N', 25, 7, 2, "Verbeitragstes Entgelt Umlage", "", false),
			new Spaltenbeschreibung('N', 26, 7, 2, "Unverbeitragtes Entgelt KV", "", false),
			new Spaltenbeschreibung('N', 27, 7, 2, "Unverbeitragtes Entgelt RV", "", false),
			new Spaltenbeschreibung('N', 28, 7, 2, "Unverbeitragtes Entgelt BA", "", false),
			new Spaltenbeschreibung('N', 29, 7, 2, "Unverbeitragtes Entgelt PV", "", false),
			new Spaltenbeschreibung('N', 30, 7, 2, "Unverbeitragtes Entgelt Insolvenzumlage", "", false),
			new Spaltenbeschreibung('N', 31, 7, 2, "Unverbeitragtes Entgelt Umlage", "", false),
			new Spaltenbeschreibung('N', 32, 3, 0, "SV-Tage KV", "", false),
			new Spaltenbeschreibung('N', 33, 3, 0, "SV-Tage RV", "", false),
			new Spaltenbeschreibung('N', 34, 3, 0, "SV-Tage BA", "", false),
			new Spaltenbeschreibung('N', 35, 3, 0, "SV-Tage PV", "", false),
			new Spaltenbeschreibung('A', 36, 1, 0, "BBG_RV_UND_RV_OST", "", false),
			new Spaltenbeschreibung('A', 37, 1, 0, "KZ_BBG_RV/BA_KNAPPSCHAFT", "", false),
			new Spaltenbeschreibung('N', 38, 9, 2, "SV-Beitrag Arbeitgeber", "", false),
			new Spaltenbeschreibung('N', 39, 9, 2, "SV-Beitrag Arbeitnehmer", "", false),
			new Spaltenbeschreibung('N', 40, 9, 2, "Bereits Pauschalierte ZV-Umlage", "", false),
			new Spaltenbeschreibung('N', 41, 9, 2, "Bereits Pauschalierte ZV-Beil.", "", false),
			new Spaltenbeschreibung('N', 42, 9, 2, "Bereits Steuerfrei in ZV", "", false),
			new Spaltenbeschreibung('N', 43, 7, 2, "Bereits Steuerfrei Arbeitgeber", "", false),
			new Spaltenbeschreibung('N', 44, 7, 2, "Bereits Steuerfrei Arbeitnehmer", "", false),
			new Spaltenbeschreibung('N', 45, 7, 2, "Bereits SV-Frei Arbeitgeber", "", false),
			new Spaltenbeschreibung('N', 46, 7, 2, "Bereits SV-Frei Arbeitnehmer", "", false),
			new Spaltenbeschreibung('N', 47, 7, 2, "Bereits pauschaliert Arbeitgeber", "", false),
			new Spaltenbeschreibung('N', 48, 7, 2, "Bereits pauschaliert Arbeitnehmer", "", false),
			new Spaltenbeschreibung('N', 49, 7, 2, "Bereits SV-Frei abgerechnet", "", false),
			new Spaltenbeschreibung('N', 50, 7, 2, "Bereits SV-Frei arbeitnehmer", "", false),
			new Spaltenbeschreibung('N', 51, 11, 2, "Wertguthaben West SV-Frei (Wert)", "", false),
			new Spaltenbeschreibung('N', 52, 7, 2, "Wertguthaben West SV-Frei (Stunden)", "", false),
			new Spaltenbeschreibung('N', 53, 11, 2, "Wertguthaben SV-Pflichtig (West)", "", false),
			new Spaltenbeschreibung('N', 54, 7, 2, "Stunden SV-Pflichtig (West)", "", false),
			new Spaltenbeschreibung('N', 55, 9, 2, "WERTGUTHABEN_WEST_SV_PFL_AG_SV_ANTEILE", "", false),
			new Spaltenbeschreibung('A', 56, 15, 0, "Betriebsnummer (West)", "", false),
			new Spaltenbeschreibung('N', 57, 11, 2, "SV-Luft KV (West)", "", false),
			new Spaltenbeschreibung('N', 58, 11, 2, "SV-Luft RV (West)", "", false),
			new Spaltenbeschreibung('N', 59, 11, 2, "SV-Luft BA (West)", "", false),
			new Spaltenbeschreibung('N', 60, 11, 2, "SV-Luft PV (West)", "", false),
			new Spaltenbeschreibung('N', 61, 11, 2, "Wertguthaben Ost SV-Frei (Wert)", "", false),
			new Spaltenbeschreibung('N', 62, 7, 2, "Wertguthaben Ost SV-Frei (Stunden)", "", false),
			new Spaltenbeschreibung('N', 63, 11, 2, "Wertguthaben SV-Pflichtig (Ost)", "", false),
			new Spaltenbeschreibung('N', 64, 7, 2, "Stunden SV-Pflichtig (Ost)", "", false),
			new Spaltenbeschreibung('N', 65, 9, 2, "WERTGUTHABEN_OST_SV_PFL_AG_SV_ANTEILE", "", false),
			new Spaltenbeschreibung('A', 66, 15, 0, "Betriebsnummer (Ost)", "", false),
			new Spaltenbeschreibung('N', 67, 11, 2, "SV-Luft KV (Ost)", "", false),
			new Spaltenbeschreibung('N', 68, 11, 2, "SV-Luft RV (Ost)", "", false),
			new Spaltenbeschreibung('N', 69, 11, 2, "SV-Luft BA (Ost)", "", false),
			new Spaltenbeschreibung('N', 70, 11, 2, "SV-Luft PV (Ost)", "", false),
			new Spaltenbeschreibung('N', 71, 11, 2, "GB_BRUTTO", "", false)};

	public static final Spaltenbeschreibung[] DURCHSCHNITTE_SPALTENBESCHREIBUNG = {
			new Spaltenbeschreibung('A', 1, 3, 0, "Firma", "", true),
			new Spaltenbeschreibung('N', 2, 4, 0, "Abrechnungsjahr", "", true),
			new Spaltenbeschreibung('N', 3, 2, 0, "Abrechnungsmonat", "", true),
			new Spaltenbeschreibung('N', 4, 6, 0, "Personalnummer", "", true),

			new Spaltenbeschreibung('N', 5, 5, 2, "Personalstamm-Faktor 1", "", false),
			new Spaltenbeschreibung('N', 6, 5, 2, "Personalstamm-Faktor 2", "", false),
			new Spaltenbeschreibung('N', 7, 5, 2, "Personalstamm-Faktor 3", "", false),
			new Spaltenbeschreibung('N', 8, 5, 2, "Personalstamm-Faktor 4", "", false),
			new Spaltenbeschreibung('N', 9, 5, 2, "Personalstamm-Faktor 5", "", false),
			new Spaltenbeschreibung('N', 10, 5, 2, "Personalstamm-Durchschnittsfaktor 1", "", false),
			new Spaltenbeschreibung('N', 11, 5, 2, "Personalstamm-Durchschnittsfaktor 2", "", false),
			new Spaltenbeschreibung('N', 12, 5, 2, "Personalstamm-Durchschnittsfaktor 3", "", false),
			new Spaltenbeschreibung('N', 13, 5, 2, "Personalstamm-Durchschnittsfaktor 4", "", false),
			new Spaltenbeschreibung('N', 14, 5, 2, "Personalstamm-Durchschnittsfaktor 5", "", false),
			new Spaltenbeschreibung('N', 15, 5, 2, "Faktor laufender Monat 1", "", false),
			new Spaltenbeschreibung('N', 16, 5, 2, "Faktor laufender Monat 2", "", false),
			new Spaltenbeschreibung('N', 17, 5, 2, "Faktor laufender Monat 3", "", false),
			new Spaltenbeschreibung('N', 18, 5, 2, "Faktor laufender Monat 4", "", false),
			new Spaltenbeschreibung('N', 19, 5, 2, "Faktor laufender Monat 5", "", false),
			new Spaltenbeschreibung('N', 20, 5, 2, "Stunden laufender Monat 1", "", false),
			new Spaltenbeschreibung('N', 21, 5, 2, "Stunden laufender Monat 2", "", false),
			new Spaltenbeschreibung('N', 22, 5, 2, "Stunden laufender Monat 3", "", false),
			new Spaltenbeschreibung('N', 23, 5, 2, "Stunden laufender Monat 4", "", false),
			new Spaltenbeschreibung('N', 24, 5, 2, "Stunden laufender Monat 5", "", false),
			new Spaltenbeschreibung('N', 25, 9, 2, "Betrag laufender Monat 1", "", false),
			new Spaltenbeschreibung('N', 26, 9, 2, "Betrag laufender Monat 2", "", false),
			new Spaltenbeschreibung('N', 27, 9, 2, "Betrag laufender Monat 3", "", false),
			new Spaltenbeschreibung('N', 28, 9, 2, "Betrag laufender Monat 4", "", false),
			new Spaltenbeschreibung('N', 29, 9, 2, "Betrag laufender Monat 5", "", false),
			new Spaltenbeschreibung('N', 30, 5, 2, "Tage laufender Monat", "", false)};

	public static final Spaltenbeschreibung[] SCHWERBEHINDERTENDATEN_SPALTENBESCHREIBUNG = {
			new Spaltenbeschreibung('A', 1, 3, 0, "Firma", "", true),
			new Spaltenbeschreibung('N', 2, 4, 0, "Abrechnungsjahr", "", true),
			new Spaltenbeschreibung('N', 3, 6, 0, "Personalnummer", "", true),

			new Spaltenbeschreibung('A', 4, 1, 0, "Kennzeichen Geschäftsführer", "", false),
			new Spaltenbeschreibung('A', 5, 6, 0, "Schwerbehinderten gleichgestellt", "", false),
			new Spaltenbeschreibung('N', 6, 3, 0, "Grad der Behinderung (in %)", "", false),
			new Spaltenbeschreibung('A', 7, 20, 0, "Ausstellender des Schwerbehindertenbescheids", "", false),
			new Spaltenbeschreibung('A', 8, 30, 0, "Ort des Ausstellenden", "", false),
			new Spaltenbeschreibung('A', 9, 10, 0, "Tag der Ausstellung", "", false),
			new Spaltenbeschreibung('A', 10, 50, 0, "Aktenzeichen schwerbehindert", "", false),
			new Spaltenbeschreibung('A', 11, 10, 0, "Behinderung anerkannt von", "", false),
			new Spaltenbeschreibung('A', 12, 10, 0, "Behinderung anerkannt bis", "", false),
	};


	public static void logErrors(List<String> lines, Spaltenbeschreibung[] fileDescriptions) {
		for (int row = 0; row < lines.size(); row++) {
			String[] values = lines.get(row).split(COLUMN_SEPARATOR);
			String[] cols = new String[values.length];
			System.arraycopy(values, 0, cols, 0, values.length);
			for (int col = 0; col < cols.length; col++) {
				if (fileDescriptions.length > col)
					fileDescriptions[col].logLineErrors(cols[col], row);
			}
		}
	}
}
