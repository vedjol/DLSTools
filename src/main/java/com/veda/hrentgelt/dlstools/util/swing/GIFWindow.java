package com.veda.hrentgelt.dlstools.util.swing;

import com.veda.hrentgelt.dlstools.DLSTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.MalformedURLException;
import java.net.URL;

public class GIFWindow extends JDialog {

    private JLabel gifLabel;

    public GIFWindow(DLSTools parent) {
        super(parent);
        initGUI();
    }


    private void initGUI() {
        Rectangle r = getParent().getBounds();
        this.setBounds(r.x, r.y, r.width, r.height);
        getParent().addComponentListener(getComponentListener());
        try {
            Icon ico = new ImageIcon(new URL("https://z3.hs-offenburg.de/fileadmin/default_upload/giphy.gif"));
            gifLabel = new JLabel(ico);
        } catch (MalformedURLException e) {
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(new Color(255, 255, 255, 0));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
//        mainPanel.add(gifLabel, BorderLayout.CENTER);

        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.add(mainPanel);
    }

    private ComponentListener getComponentListener() {

        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Rectangle r = getParent().getBounds();
                System.out.println(r);
                setBounds(r.x, r.y, r.width, r.height);
                System.out.println("Bounds: " + getBounds());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                Rectangle r = getParent().getBounds();
                System.out.println(r);
                setBounds(r.x, r.y, r.width, r.height);
                System.out.println("Bounds: " + getBounds());
            }

            @Override
            public void componentShown(ComponentEvent e) {
                setVisible(true);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                setVisible(false);
            }
        };
    }
}
