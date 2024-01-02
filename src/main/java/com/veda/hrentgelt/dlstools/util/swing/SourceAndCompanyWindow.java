package com.veda.hrentgelt.dlstools.util.swing;

import com.veda.hrentgelt.dlstools.DLSTools;
import com.veda.hrentgelt.dlstools.util.images.ImageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class SourceAndCompanyWindow extends JDialog {
    private JTextField sourcePathTextField;
    private JTextField companyTextField;
    private JButton chooseSourceButton;
    private JButton cancelButton;
    private JButton okButton;
    private JFileChooser chooser;


    public SourceAndCompanyWindow(DLSTools parent) {
        super(parent);
        initGUI(parent.getCurrentColor());
    }

    public void setColor(Color currentColor) {
        chooseSourceButton.setIcon(ImageHandler.getImageIcon(ImageHandler.CHOOSE_FILE, currentColor));
    }

    private void initGUI(Color c) {
        sourcePathTextField = new JTextField();
        chooseSourceButton = new JButton(ImageHandler.getImageIcon(ImageHandler.CHOOSE_FILE, c));
        chooseSourceButton.addActionListener(a -> {
            String newPath = chooseFile(new File(DLSTools.getInstance().getSourcePath()), "Root-Verzeichnis auswählen");
            if (newPath != null && !newPath.isEmpty())
                sourcePathTextField.setText(newPath);
        });
        chooseSourceButton.setFocusable(false);

        companyTextField = new JTextField();
        companyTextField.addKeyListener(getKeyListener());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        cancelButton = new JButton("cancel");
        cancelButton.addActionListener(a -> dispose());
        cancelButton.setFocusable(false);
        okButton = new JButton("ok");
        okButton.addActionListener(a -> {
            DLSTools.getInstance().setSourcePath(new File(sourcePathTextField.getText()), companyTextField.getText());
            dispose();
        });
        okButton.setFocusable(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);


        JPanel labelPanel = new JPanel(new GridLayout(0, 1));
        labelPanel.add(new JLabel("Quellpfad: "));
        labelPanel.add(new JLabel("Firma: "));

        JPanel textFieldPanel = new JPanel(new GridLayout(0, 1));
        JPanel sourceHelpPanel = new JPanel(new BorderLayout());
        sourceHelpPanel.add(sourcePathTextField, BorderLayout.CENTER);
        sourceHelpPanel.add(chooseSourceButton, BorderLayout.EAST);
        textFieldPanel.add(sourceHelpPanel);
        textFieldPanel.add(companyTextField);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(textFieldPanel, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
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

    private String chooseFile(File root, String title) {
        if (chooser == null) {
            chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);
        }

        chooser.setCurrentDirectory(root);
        chooser.setDialogTitle(title);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public void open(String currentPath, String currentCompany) {
        setIconImage(DLSTools.getInstance().getIconImage());
        sourcePathTextField.setText(currentPath);
        companyTextField.setText(currentCompany);
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
}
