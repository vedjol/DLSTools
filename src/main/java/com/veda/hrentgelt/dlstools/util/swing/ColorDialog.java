package com.veda.hrentgelt.dlstools.util.swing;

import com.veda.hrentgelt.dlstools.DLSTools;
import com.veda.hrentgelt.dlstools.util.LogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;

public class ColorDialog extends JDialog {

    private JTextField colorTextField;
    private JLabel colorInfoLabel;
    private Color colorBevore;
    private JButton cancelButton;
    private JButton okButton;

    public ColorDialog(DLSTools parent) {
        super(parent);
        initGUI();
    }

    public void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        colorInfoLabel = new JLabel("Farbe:    ");
        colorTextField = new JTextField();
        mainPanel.add(colorInfoLabel, BorderLayout.WEST);
        mainPanel.add(colorTextField, BorderLayout.CENTER);

        colorTextField.addKeyListener(getKeyListener());


        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        cancelButton = new JButton("cancel");
        cancelButton.addActionListener(a -> dispose());
        cancelButton.setFocusable(false);
        okButton = new JButton("ok");
        okButton.addActionListener(a -> {
            DLSTools.getInstance().setLAF(DLSTools.getInstance().getCurrentLAF(), getColor());
            dispose();
        });
        okButton.setFocusable(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
        this.add(buttonPanel);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        pack();
    }

    public KeyListener getKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE -> cancelButton.doClick();
                    case KeyEvent.VK_ENTER -> okButton.doClick();
                }
            }
        };
    }

    public void setColor(Color currentColor) {
//        chooseSourceButton.setIcon(ImageHandler.getImageIcon(ImageHandler.CHOOSE_FILE, currentColor));
    }


    public void open() {
        colorBevore = DLSTools.getInstance().getCurrentColor();
        setIconImage(DLSTools.getInstance().getIconImage());
        colorTextField.setText(getTextFromColor(colorBevore));
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
    }


    private Color getColor() {
        String text = colorTextField.getText();
        try {
            String[] vals = text.split(";");
            int r = Integer.parseInt(vals[0]);
            int g = Integer.parseInt(vals[1]);
            int b = Integer.parseInt(vals[2]);
            return new Color(r, g, b);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            //Kein Format "r;g;b"
            //Color from value
            try {
                return Color.decode(text.trim());
            } catch (Exception e2) {
                //Decode nicht möglich
                // -> from name
                //        try {
                Field field = null;
                try {
                    field = Class.forName("java.awt.Color").getField(text.trim());
                    return (Color) field.get(null);
                } catch (NoSuchFieldException | ClassNotFoundException |
                         IllegalAccessException ex) {
                    LogPanel.getInstance().log(System.Logger.Level.ERROR, "Neue Farbe \"" + text + "\" konnte nicht ermittelt werden. Behalte alte Farbe bei.");
                }
            }
        }
        return colorBevore;
    }

    private String getTextFromColor(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
}
