package com.veda.hrentgelt.dlstools.util;

import java.util.List;

public class Spaltenbeschreibungen {
    private static final String COLUMN_SEPARATOR = ";";

    private Spaltenbeschreibungen() {
    }

    public static final Spaltenbeschreibung[] ERGAENZUNGSBESCHREIBUNG = {
            new Spaltenbeschreibung('A', 1, 64, 0, "PRNR_ALT", "", true),
            new Spaltenbeschreibung('N', 2, 6, 0, "PRNR_NEU", "", true),
            new Spaltenbeschreibung('A', 3, 3, 0, "FIRMA", "", true),

            new Spaltenbeschreibung('N', 4, 1, 0, "ABRECHNUNGSGRUPPE", "", false, 1, 2, 3, 4, 5, 6),
            new Spaltenbeschreibung('A', 5, 6, 0, "ABTEILUNG", "", false),
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


            new Spaltenbeschreibung('A', 32, 8, 0, "NAME_GRUNDVERGÜTUNGSTABELLE", "", false),
            new Spaltenbeschreibung('A', 32, 8, 0, "TARIF_VERGÜTUNGSGRUPPE", "", false),
            new Spaltenbeschreibung('N', 32, 3, 0, "VERGÜTUNGSSTUFE", "", false),
            new Spaltenbeschreibung('A', 32, 17, 0, "BERUFSST_V-WERK_MITGLIEDSNUMMER", "", false),
            new Spaltenbeschreibung('A', 32, 2, 0, "ECHTE_KK_BEI_PGS_109/110", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "STUNDENLOHN", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "STUNDENLOHNEMPFÄNGER", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "KV_VORG_GES", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "KV_VORG_AG", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "PV_VORG_GES", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "PV_VORG_AG", "", false),


            new Spaltenbeschreibung('A', 32, 8, 0, "BASISTARIF_ANTEIL_AN", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "ÜBERGANGSBEREICH_ABGERECHNET", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "AKADEMISCHER_TITEL", "", false),

            new Spaltenbeschreibung('A', 32, 8, 0, "TÄTIGKEITSSCHLÜSSEL", "", false),


    };

    public static final Spaltenbeschreibung[] BEZUEGE_ABZUEGE_SPALTENBESCHREIBUNG = {
            new Spaltenbeschreibung('A', 1, 3, 0, "FIRMA", "", true),
            new Spaltenbeschreibung('N', 2, 6, 0, "PRNR", "", true),

            new Spaltenbeschreibung('A', 3, 3, 0, "LOHNART", "", false),
            new Spaltenbeschreibung('A', 4, 20, 0, "LOHNARTENTEXT", "", false),
            new Spaltenbeschreibung('N', 5, 9, 2, "BETRAG", "", true),
            new Spaltenbeschreibung('A', 6, 3, 0, "WÄHRUNG", "", false),
            new Spaltenbeschreibung('N', 7, 9, 2, "STUNDEN", "", false),
            new Spaltenbeschreibung('N', 8, 2, 0, "MONAT_VON", "", false),
            new Spaltenbeschreibung('N', 9, 4, 0, "JAHR_VON", "", false),
            new Spaltenbeschreibung('N', 10, 2, 0, "MONAT_BIS", "", false),
            new Spaltenbeschreibung('N', 11, 4, 0, "JAHR_BIS", "", false),
            new Spaltenbeschreibung('A', 12, 2, 0, "ZAHLUNGSWEISE", "", false),
            new Spaltenbeschreibung('A', 13, 1, 0, "ZAHLWEG", "", false),
            new Spaltenbeschreibung('A', 14, 1, 0, "ZAHLUNGSART", "", false),
            new Spaltenbeschreibung('N', 15, 5, 2, "AG-ANTEIL_VL", "", false),
            new Spaltenbeschreibung('N', 16, 2, 0, "AG-ANTEIL_VL_AB_MONAT", "", false),
            new Spaltenbeschreibung('N', 17, 4, 0, "AG-ANTEIL_VL_AB_JAHR", "", false),
            new Spaltenbeschreibung('A', 18, 27, 0, "ZAHLUNGSEMPFÄNGER", "", false),
            new Spaltenbeschreibung('A', 19, 11, 0, "BIC", "", false),
            new Spaltenbeschreibung('A', 20, 34, 0, "IBAN", "", false),
            new Spaltenbeschreibung('T', 21, 8, 0, "WIRKSAM_AB_UHRZEIT", "hh:mm:ss", false), // TODO
            new Spaltenbeschreibung('A', 22, 35, 0, "AKTENZEICHEN_PFÄNDUNGEN", "", false),
            new Spaltenbeschreibung('A', 23, 1, 0, "ANZAHL_UNTERHALTSBERECHTIGTE", "", false),
            new Spaltenbeschreibung('N', 24, 9, 2, "AKTUELLER_RÜCKSTAND", "", false),
            new Spaltenbeschreibung('N', 25, 9, 2, "URSPRÜNGLICHER_RÜCKSTAND", "", false),
            new Spaltenbeschreibung('N', 26, 9, 2, "LAUFENDE_FORDERUNG", "", false),
            new Spaltenbeschreibung('N', 27, 5, 2, "PERSÖNLICHER_FREIBETRAG", "", false),
            new Spaltenbeschreibung('N', 28, 2, 0, "ANTEIL_MEHRBETRAG_ZÄHLER", "", false),
            new Spaltenbeschreibung('N', 29, 2, 0, "ANTEIL_MEHRBETRAG_NENNER", "", false),
            new Spaltenbeschreibung('A', 30, 1, 0, "ZEITGLEICHE_PFÄNDUNGEN", "", false),
            new Spaltenbeschreibung('A', 31, 2, 0, "RANGFOLGE_PFÄNDUNGEN", "", false),
            new Spaltenbeschreibung('A', 32, 20, 0, "VERTRAGSNUMMER", "", false),
            new Spaltenbeschreibung('A', 33, 27, 0, "VERWENDUNGSZWECK", "", false),
            new Spaltenbeschreibung('A', 34, 27, 0, "BEGÜNSTIGTER", "", false)};

    public static final Spaltenbeschreibung[] BBG_SALDEN_SPALTENBESCHREIBUNG = {
            new Spaltenbeschreibung('A', 1, 3, 0, "FIRMA", "", true),
            new Spaltenbeschreibung('N', 2, 4, 0, "ABRECHNUNGSJAHR", "", true),
            new Spaltenbeschreibung('N', 3, 6, 0, "PERSONALNUMMER", "", true),

            new Spaltenbeschreibung('N', 4, 7, 2, "KV_LUFT", "", false),
            new Spaltenbeschreibung('N', 5, 7, 2, "RV_LUFT", "", false),
            new Spaltenbeschreibung('N', 6, 7, 2, "BA_LUFT", "", false),
            new Spaltenbeschreibung('N', 7, 7, 2, "PV_LUFT", "", false),
            new Spaltenbeschreibung('N', 8, 7, 2, "UMLAGEBASIS", "", false),
            new Spaltenbeschreibung('N', 9, 7, 2, "UMLAND_LUFT", "", false),
            new Spaltenbeschreibung('N', 10, 7, 2, "BEMESSUNSG_EGA", "", false)};

    public static final Spaltenbeschreibung[] VAG_SPALTENBESCHREIBUNG = {
            new Spaltenbeschreibung('A', 1, 3, 0, "FIRMA", "", true),
            new Spaltenbeschreibung('N', 2, 4, 0, "ABRECHNUNGSJAHR", "", true),
            new Spaltenbeschreibung('N', 3, 6, 0, "PERSONALNUMMER", "", true),

            new Spaltenbeschreibung('D', 4, 10, 0, "BESCHÄFTIGT_VON", "dd.mm.yyyy", false),
            new Spaltenbeschreibung('D', 5, 10, 0, "BESCHÄFTIGT_BIS", "dd.mm.yyyy", false),
            new Spaltenbeschreibung('T', 6, 8, 0, "FEHLZEIT_BEGINN", "hh:mm:ss", false),
            new Spaltenbeschreibung('T', 7, 8, 0, "FEHLZEIT_ENDE", "hh:mm:ss", false),
            new Spaltenbeschreibung('A', 8, 1, 0, "FEHLZEIT_GRUND", "", false),
            new Spaltenbeschreibung('N', 9, 9, 2, "STEUERBRUTTO", "", false),
            new Spaltenbeschreibung('N', 10, 9, 2, "LOHNSTEUER", "", false),
            new Spaltenbeschreibung('N', 11, 9, 2, "SOLIDARITÄTSZUSCHLAG", "", false),
            new Spaltenbeschreibung('N', 12, 9, 2, "KIRCHENSTEUER_AN", "", false),
            new Spaltenbeschreibung('N', 13, 9, 2, "KIRCHENSTEUER_EG", "", false),
            new Spaltenbeschreibung('N', 14, 11, 2, "ABFINDUNGEN", "", false),
            new Spaltenbeschreibung('N', 15, 11, 2, "ENTGELT_MEHRERE_JAHRE", "", false),
            new Spaltenbeschreibung('N', 16, 7, 2, "MUTTERSCHAFTSGELD", "", false),
            new Spaltenbeschreibung('N', 17, 9, 2, "KURZARBEITERGELD", "", false),
            new Spaltenbeschreibung('N', 18, 2, 0, "BEZUGSMONATE_KUG", "", false),
            new Spaltenbeschreibung('N', 19, 2, 0, "ANZAHL_UNTERBRECHUNGEN", "", false),
            new Spaltenbeschreibung('N', 20, 7, 2, "VERBEITRAGTES_ENTGELT_KV", "", false),
            new Spaltenbeschreibung('N', 21, 7, 2, "VERBEITRAGTES_ENTGELT_RV", "", false),
            new Spaltenbeschreibung('N', 22, 7, 2, "VERBEITRAGTES_ENTGELT_BA", "", false),
            new Spaltenbeschreibung('N', 23, 7, 2, "VERBEITRAGTES_ENTGELT_PV", "", false),
            new Spaltenbeschreibung('N', 24, 7, 2, "VERBEITRAGTES_ENTGELT_INSOLVENZUMLAGE", "", false),
            new Spaltenbeschreibung('N', 25, 7, 2, "VERBEITRAGTES_ENTGELT_UMLAGE", "", false),
            new Spaltenbeschreibung('N', 26, 7, 2, "UNVERBEITRAGTES_ENTGELT_KV", "", false),
            new Spaltenbeschreibung('N', 27, 7, 2, "UNVERBEITRAGTES_ENTGELT_RV", "", false),
            new Spaltenbeschreibung('N', 28, 7, 2, "UNVERBEITRAGTES_ENTGELT_BA", "", false),
            new Spaltenbeschreibung('N', 29, 7, 2, "UNVERBEITRAGTES_ENTGELT_PV", "", false),
            new Spaltenbeschreibung('N', 30, 7, 2, "UNVERBEITRAGTES_ENTGELT_INSOLVENZUMLAGE", "", false),
            new Spaltenbeschreibung('N', 31, 7, 2, "UNVERBEITRAGTES_ENTGELT_UMLAGE", "", false),
            new Spaltenbeschreibung('N', 32, 3, 0, "SV_TAGE_KV", "", false),
            new Spaltenbeschreibung('N', 33, 3, 0, "SV_TAGE_RV", "", false),
            new Spaltenbeschreibung('N', 34, 3, 0, "SV_TAGE_BA", "", false),
            new Spaltenbeschreibung('N', 35, 3, 0, "SV_TAGE_PV", "", false),
            new Spaltenbeschreibung('A', 36, 1, 0, "BBG_RV_UND_RV_OST", "", false),
            new Spaltenbeschreibung('A', 37, 1, 0, "KZ_BBG_RV/BA_KNAPPSCHAFT", "", false),
            new Spaltenbeschreibung('N', 38, 9, 2, "SV_BEITRAG_AG", "", false),
            new Spaltenbeschreibung('N', 39, 9, 2, "SV_BEITRAG_AN", "", false),
            new Spaltenbeschreibung('N', 40, 9, 2, "BEREITS_PAUSCHALIERT_ZV_UML", "", false),
            new Spaltenbeschreibung('N', 41, 9, 2, "BEREITS_PAUSCHALIERT_ZV_BEIL", "", false),
            new Spaltenbeschreibung('N', 42, 9, 2, "BEREITS_STEUERFREI_IN_ZV", "", false),
            new Spaltenbeschreibung('N', 43, 7, 2, "BEREITS_STEUERFREI_AG", "", false),
            new Spaltenbeschreibung('N', 44, 7, 2, "BEREITS_STEUERFREI_AN", "", false),
            new Spaltenbeschreibung('N', 45, 7, 2, "BEREITS_SV_FREI_AG", "", false),
            new Spaltenbeschreibung('N', 46, 7, 2, "BEREITS_SV_FREI_AN", "", false),
            new Spaltenbeschreibung('N', 47, 7, 2, "BEREITS_PAUSCHALIERT_AG", "", false),
            new Spaltenbeschreibung('N', 48, 7, 2, "BEREITS_PAUSCHALIERT_AN", "", false),
            new Spaltenbeschreibung('N', 49, 7, 2, "BEREITS_SV_FREI_ABGERECHNET", "", false),
            new Spaltenbeschreibung('N', 50, 7, 2, "BEREITS_SV_FREI_AN", "", false),
            new Spaltenbeschreibung('N', 51, 11, 2, "WERTGUTHABEN_WEST_SV_FREI_WERT", "", false),
            new Spaltenbeschreibung('N', 52, 7, 2, "WERTGUTHABEN_WEST_SV_FREI_STUNDEN", "", false),
            new Spaltenbeschreibung('N', 53, 11, 2, "WERTGUTHABEN_SV_PFL_WEST", "", false),
            new Spaltenbeschreibung('N', 54, 7, 2, "STUNDEN_SV_PFL_WEST", "", false),
            new Spaltenbeschreibung('N', 55, 9, 2, "WERTGUTHABEN_WEST_SV_PFL_AG_SV_ANTEILE", "", false),
            new Spaltenbeschreibung('A', 56, 15, 0, "BETRIEBSNUMMER_WEST", "", false),
            new Spaltenbeschreibung('N', 57, 11, 2, "SV_LUFT_WEST_KV", "", false),
            new Spaltenbeschreibung('N', 58, 11, 2, "SV_LUFT_WEST_RV", "", false),
            new Spaltenbeschreibung('N', 59, 11, 2, "SV_LUFT_WEST_BA", "", false),
            new Spaltenbeschreibung('N', 60, 11, 2, "SV_LUFT_WEST_PV", "", false),
            new Spaltenbeschreibung('N', 61, 11, 2, "WERTGUTHABEN_OST_SV_FREI_WERT", "", false),
            new Spaltenbeschreibung('N', 62, 7, 2, "WERTGUTHABEN_OST_SV_FREI_STUNDEN", "", false),
            new Spaltenbeschreibung('N', 63, 11, 2, "WERTGUTHABEN_SV_PFL_OST", "", false),
            new Spaltenbeschreibung('N', 64, 7, 2, "STUNDEN_SV_PFL_OST", "", false),
            new Spaltenbeschreibung('N', 65, 9, 2, "WERTGUTHABEN_OST_SV_PFL_AG_SV_ANTEILE", "", false),
            new Spaltenbeschreibung('A', 66, 15, 0, "BETRIEBSNUMMER_OST", "", false),
            new Spaltenbeschreibung('N', 67, 11, 2, "SV_LUFT_OST_KV", "", false),
            new Spaltenbeschreibung('N', 68, 11, 2, "SV_LUFT_OST_RV", "", false),
            new Spaltenbeschreibung('N', 69, 11, 2, "SV_LUFT_OST_BA", "", false),
            new Spaltenbeschreibung('N', 70, 11, 2, "SV_LUFT_OST_PV", "", false),
            new Spaltenbeschreibung('N', 71, 11, 2, "GB_BRUTTO", "", false)};

    public static final Spaltenbeschreibung[] DURCHSCHNITTE_SPALTENBESCHREIBUNG = {
            new Spaltenbeschreibung('A', 1, 3, 0, "FIRMA", "", true),
            new Spaltenbeschreibung('N', 2, 4, 0, "ABRECHNUNGSJAHR", "", true),
            new Spaltenbeschreibung('N', 3, 2, 0, "ABRECHNUNGSMONAT", "", true),
            new Spaltenbeschreibung('N', 4, 6, 0, "PERSONALNUMMER", "", true),

            new Spaltenbeschreibung('N', 5, 5, 2, "FAKTOR_1_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 6, 5, 2, "FAKTOR_2_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 7, 5, 2, "FAKTOR_3_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 8, 5, 2, "FAKTOR_4_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 9, 5, 2, "FAKTOR_5_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 10, 5, 2, "DURCHSCHNITTSFAKTOR_1_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 11, 5, 2, "DURCHSCHNITTSFAKTOR_2_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 12, 5, 2, "DURCHSCHNITTSFAKTOR_3_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 13, 5, 2, "DURCHSCHNITTSFAKTOR_4_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 14, 5, 2, "DURCHSCHNITTSFAKTOR_5_PERSONALSTAMM", "", false),
            new Spaltenbeschreibung('N', 15, 5, 2, "FAKTOR_1_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 16, 5, 2, "FAKTOR_2_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 17, 5, 2, "FAKTOR_3_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 18, 5, 2, "FAKTOR_4_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 19, 5, 2, "FAKTOR_5_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 20, 5, 2, "STUNDEN_1_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 21, 5, 2, "STUNDEN_2_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 22, 5, 2, "STUNDEN_3_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 23, 5, 2, "STUNDEN_4_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 24, 5, 2, "STUNDEN_5_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 25, 9, 2, "BETRAG_1_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 26, 9, 2, "BETRAG_2_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 27, 9, 2, "BETRAG_3_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 28, 9, 2, "BETRAG_4_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 29, 9, 2, "BETRAG_5_LFD_MONAT", "", false),
            new Spaltenbeschreibung('N', 30, 5, 2, "TAGE_LFD_MONAT", "", false)};

    public static final Spaltenbeschreibung[] SCHWERBEHINDERTENDATEN_SPALTENBESCHREIBUNG = {
            new Spaltenbeschreibung('A', 1, 3, 0, "FIRMA", "", true),
            new Spaltenbeschreibung('N', 2, 4, 0, "ABRECHNUNGSJAHR", "", true),
            new Spaltenbeschreibung('N', 3, 6, 0, "PERSONALNUMMER", "", true),

            new Spaltenbeschreibung('A', 4, 1, 0, "KENNZ_GESCHÄFTSF.", "", false),
            new Spaltenbeschreibung('A', 5, 6, 0, "SCHWERBEHINDERTEN_GLEICHGESTELLT", "", false),
            new Spaltenbeschreibung('N', 6, 3, 0, "GRAD_DER_BEHINDERUNG_%", "", false),
            new Spaltenbeschreibung('A', 7, 20, 0, "AUSSTELLENDER_DES_SB-BESCHEIDS", "", false),
            new Spaltenbeschreibung('A', 8, 30, 0, "ORT_DES_AUSSTELLENDEN", "", false),
            new Spaltenbeschreibung('A', 9, 10, 0, "BESCHEID_GESTELLT_AM", "", false),
            new Spaltenbeschreibung('A', 10, 50, 0, "AKTENZ_SCHWBH", "", false),
            new Spaltenbeschreibung('A', 11, 10, 0, "BEHINDERUNG_ANERKANNT_VON", "", false),
            new Spaltenbeschreibung('A', 12, 10, 0, "BEHINDERUNG_ANERKANNT_BIS", "", false),
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
