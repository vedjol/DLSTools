package com.veda.hrentgelt.dlstools.util.swing;

import com.veda.hrentgelt.dlstools.DLSTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class LoginDialog extends JDialog {
	private static final long serialVersionUID = -4433192700299467630L;

	private DLSTools parent;
	private JButton cancelButton;
	private JButton okButton;
	private JPasswordField passwordField;
	private JPanel buttonPanel;
	private JPanel mainPanel;

	public LoginDialog(DLSTools parent) {
		super(parent);
		this.setTitle("Passwort");
		this.parent = parent;
		initGui(parent.getCurrentColor());
		pack();
		setLocationRelativeTo(parent);
	}

	private void initGui(Color c) {
		passwordField = new JPasswordField();
		passwordField.addKeyListener(getKeyListener());

		cancelButton = new JButton("Abbrechen");
		cancelButton.setFocusable(false);
		cancelButton.addActionListener(a -> {
			dispose();
		});
		okButton = new JButton("OK");
		okButton.setFocusable(false);
		okButton.addActionListener(a -> {
			unlockAdminMode();
		});

		buttonPanel = new JPanel(new GridLayout(1, 0));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(passwordField, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.add(mainPanel);
	}

	private void unlockAdminMode() {
		parent.unlockAdminMode(passwordField.getPassword());
		dispose();
	}

	private KeyListener getKeyListener() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					unlockAdminMode();
					break;
				case KeyEvent.VK_ESCAPE:
					dispose();
					break;
				}
			}
		};
	}
}
