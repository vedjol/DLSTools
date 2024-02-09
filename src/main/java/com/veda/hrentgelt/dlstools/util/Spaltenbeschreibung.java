package com.veda.hrentgelt.dlstools.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Beschreibt eine Spalte fuer eine CSV-Datei (Bildet Rahmenbedingungen fuer
 * eine Pruefung von CSV-Werten)
 *
 * @author JOL
 */
public class Spaltenbeschreibung {

    private final static String DECIMALSYMBOL = ",";
    private final static String DIGITGROUPINGSYMBOL = ".";

    public final String NAME;
    public final char TYPE;
    public final int MAXLENGTH;
    public final int ACCURACY;
    public final String DATETYPE;
    public final boolean ISNECESSARY;
    private int[] validNums = null;
    private final int COLUMN;

//	public Spaltenbeschreibung(char type, int maxLength) {
//		this(type, maxLength, "");
//	}
//
//	public Spaltenbeschreibung(char type, int maxLength, String name) {
//		this(type, maxLength, 0, name, "");
//	}
//
//	public Spaltenbeschreibung(char type, int maxLength, int accuracy, String name) {
//		this(type, maxLength, accuracy, name, "");
//	}
//
//	public Spaltenbeschreibung(char type, int maxLength, String name, String dateType) {
//		this(type, maxLength, 0, name, dateType);
//	}

    public Spaltenbeschreibung(char type, int col, int maxLength, int accuracy, String name, String dateType,
                               boolean isNecessary, int... validNums) {
        this.ACCURACY = accuracy;
        this.NAME = name;
        this.TYPE = type;
        this.MAXLENGTH = maxLength;
        this.DATETYPE = dateType;
        this.ISNECESSARY = isNecessary;
        this.COLUMN = col;
        if (validNums != null) {
            for (int num : validNums) {
                if (num != -1) {
                    this.validNums = validNums;
                    break;
                }
            }
        }
    }

    public Spaltenbeschreibung(char type, int col, int maxLength, int accuracy, String name, String dateType,
                               boolean isNecessary) {
        this(type, col, maxLength, accuracy, name, dateType, isNecessary, -1, -1);
    }


    /**
     * Prueft den String val gegen alle relevanten Kriterien und dokumentiert eine
     * Meldung des typs meldungsTyp in der meldungsListe
     *
     * @param val der zu pruefende Wert
     * @param row Reihe des Wertes val in der csv-Datei
     */
    public void logLineErrors(String val, int row) {
        row = row + 1; // Wir wollen nicht den Index der Reihe sondern die Reihe selbst
        LogPanel logger = LogPanel.getInstance();

        if (val == null || val.isEmpty()) {
            if (ISNECESSARY)
                logger.log(System.Logger.Level.ERROR, "Das Feld " + NAME + " (col: " + this.COLUMN + " | row " + row
                        + ") ist ein notwendig zu fuellendes Feld");
            return;
        }
        // Typ:
        switch (TYPE) {
            // Alphanumerisch
            case 'A':
                if (val.length() > MAXLENGTH)
                    logger.log(System.Logger.Level.ERROR,
                            "Das Feld " + NAME + " (col: " + this.COLUMN + " | row " + row + ") mit dem Inhalt \"" + val
                                    + "\" hat eine zu großen Länge. Die erlaubte Maximallänge ist " + MAXLENGTH + ".");
                break;
            // Numerisch
            case 'N':
                val = val.replace(DIGITGROUPINGSYMBOL, "");
                double d = -1;
                try {
                    d = Double.parseDouble(val.replace(DECIMALSYMBOL, "."));
                } catch (NumberFormatException e) {
                    logger.log(System.Logger.Level.ERROR, "Numerisches Feld " + NAME + " (col: " + this.COLUMN + " | row " + row + ") mit dem Wert \"" + val
                            + "\" beinhaltet nicht numerische Werte.");
                }

                int vk = MAXLENGTH - ACCURACY;
                int nk = ACCURACY;
                double maxNum = getMaxNum(vk, nk);
                if (d > maxNum)
                    logger.log(System.Logger.Level.ERROR,
                            "Das Feld " + NAME + " (col: " + this.COLUMN + " | row " + row + ") mit dem Inhalt \"" + val
                                    + "\" hat eine zu großen Länge. Die erlaubte Maximallänge ist " + MAXLENGTH + ".");

                if (getAccuracy(val) > ACCURACY)
                    logger.log(System.Logger.Level.ERROR, "Numerisches Feld " + NAME + " (col: " + this.COLUMN + " | row " + row + ") mit dem Inhalt \""
                            + val + "\" beinhaltet zu viele relevante Nachkommastellen. Zugelassen sind " + ACCURACY
                            + ", vorhanden sind " + getAccuracy(val));

                if (d != -1 && validNums != null) {
                    boolean isValid = false;
                    for (int i : validNums) {
                        if (i == d) {
                            isValid = true;
                        }
                    }
                    if (!isValid) {
                        String validNumsString = "[";
                        for (int i = 0; i < validNums.length; i++) {
                            validNumsString += validNums[i];
                            if (i != validNums.length - 1)
                                validNumsString += ",";
                        }
                        validNumsString += "]";
                        logger.log(System.Logger.Level.ERROR, "Wert des Feldes " + NAME + " (col: " + this.COLUMN + " | row " + row + ") mit dem Inhalt \""
                                + val + "\" entspricht keinem der validen Werte: " + validNumsString);
                    }
                }

                break;
            // Date
            case 'T':
                checkDateOrTime('T', val);
                break;
            // Date
            case 'D':
                checkDateOrTime('D', val);
                break;
            default:
                logger.log(System.Logger.Level.ERROR, "Unbekannter Datentyp " + TYPE + " für " + NAME + " (col: " + this.COLUMN + " | row " + row + ").");
                break;
        }
    }

    private void checkDateOrTime(char timeOrDate, String val) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETYPE);
        Date zeitstempel;
        try {
            zeitstempel = simpleDateFormat.parse(val);
            System.out.println(zeitstempel);
        } catch (ParseException e) {
            String t = "";
            switch (timeOrDate) {
                case 'T':
                    t = "Datumsfeldes";
                    break;
                case 'D':
                    t = "Zeitfeldes";
                    break;
            }
            LogPanel.getInstance().log(System.Logger.Level.ERROR, "Fehler beim Ermitteln des " + t + " " + val + " mit Maske " + DATETYPE);
        }
    }

    /**
     * Gibt die Genauigkeit der als String uebergebenen Zahl zurueck (Anzahl der
     * relevanten Nachkommastellen)
     *
     * @param s Die zu pruefende Zahl in Form eines Strings
     * @return
     */
    private int getAccuracy(String s) {
        if (!s.contains(DECIMALSYMBOL)) {
            return 0;
        } else {
            s.replace(".", "");
            String nk = s.substring(s.indexOf(DECIMALSYMBOL) + 1);
            if (nk.replace("0", "").isEmpty()) {
                return 0;
            }

            char c;
            for (int i = nk.length(); i >= 0; i--) {
                c = nk.charAt(i - 1);
                if (c == '0')
                    nk = nk.substring(0, i);
                else
                    return i;
            }

        }
        return 0;
    }

    /**
     * Gibt die maximal moegliche Zahl zurueck, die mit dieser Anzahl von Stellen
     * erstellt werden koennte. Im Falle von VK=2 und NK= 3 waere das bsp. 99,999
     *
     * @param vk Anzahl der Vorkommastellen
     * @param nk Anzahl der Nachkommastellen
     * @return groeßtmoegliche Zahl mit dieser Anzahl von Stellen
     */
    private double getMaxNum(int vk, int nk) {
        double d = 0.0;

        // vorkommateil fuellen
        for (int i = 0; i < vk; i++) {
            d += 9 * (Math.pow(10, i));
        }
        // nachkommateil fuellen
        for (int i = 0; i < nk; i++) {
            d += 9 * (Math.pow(10, -(i + 1)));
        }

        return d;
    }
}