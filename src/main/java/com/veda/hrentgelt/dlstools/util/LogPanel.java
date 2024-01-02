package com.veda.hrentgelt.dlstools.util;

import com.veda.hrentgelt.dlstools.DLSTools;
import com.veda.hrentgelt.dlstools.util.swing.StyledScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class LogPanel extends JPanel implements System.Logger {
    private static final int STANDARD_PADDING = 10;
    private static final String HEADER = "Log:\n";
    private final String name;
    private static LogPanel INSTANCE;
    private JButton clearButton;
    private JButton exportButton;
    private StyledScrollPane sp;
    private JTextArea textArea;
    private Color currentColor;

    public static LogPanel getInstance() {
        if (INSTANCE == null) INSTANCE = new LogPanel("LogPanel");
        return INSTANCE;
    }

    private LogPanel(String name) {
        this.name = name;
        initGUI();
    }

    private void initGUI() {
        textArea = new JTextArea();
        textArea.setText(HEADER);
        textArea.setEditable(false);
        sp = new StyledScrollPane(textArea, currentColor, STANDARD_PADDING);

        clearButton = new JButton("clear");
        clearButton.addActionListener(a -> {
            textArea.setText(HEADER);
        });
        exportButton = new JButton("export");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        buttonPanel.add(clearButton);
        buttonPanel.add(exportButton);

        this.setLayout(new BorderLayout());
        this.add(sp, BorderLayout.CENTER);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setFocusable(false);
        clearButton.setFocusable(false);
        exportButton.setFocusable(false);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLoggable(Level level) {
        return false;
    }

    public void log(Level level, String text) {
        log(level, (ResourceBundle) null, text);
    }

    public void log(Exception e) {
        log(Level.ERROR, (ResourceBundle) null, e.getMessage());
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        log(level, bundle, "Message: {}; Exception Message:{}", msg, thrown);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        String start = getMaxLengthText(Level.values(), level.toString()) + "   |    Message: ";

        switch (level) {
            case INFO:
            case WARNING:
            case ERROR:
                String sb = start + String.format(format, params);
                textArea.setText(textArea.getText() + "\n" + sb);
                break;
            default:
                System.out.println(start + String.format(format, params));
        }
    }

    private String getMaxLengthText(Object[] values, String text) {
        int maxLength = -1;
        for (Object o : values) {
            String s = o.toString();
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }
        return String.format("%-" + maxLength + "s", text);
    }

    public void setColor(Color c) {
        textArea.setForeground(c);
        exportButton.setForeground(c);
        clearButton.setForeground(c);
        sp.setThumbColor(c);
    }

    public void setLogFont(Font font) {
        if (textArea != null)
            textArea.setFont(font);
    }

    public void setButtonFont(Font font) {
        if (exportButton != null)
            exportButton.setFont(font);
        if (clearButton != null)
            clearButton.setFont(font);
    }

    public void setBreakLinesPolicy(boolean breakLines) {
        textArea.setLineWrap(breakLines);
        textArea.setWrapStyleWord(breakLines);
        revalidate();
    }

    public boolean getBreakLinesPolicy() {
        return textArea.getLineWrap();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
