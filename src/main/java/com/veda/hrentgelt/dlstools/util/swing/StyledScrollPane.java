package com.veda.hrentgelt.dlstools.util.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class StyledScrollPane extends JScrollPane {

    private static final long serialVersionUID = 8607734981506765935L;

    private static final int SCROLL_BAR_ALPHA_ROLLOVER = 200;
    private static final int SCROLL_BAR_ALPHA = 150;
    private static final int SB_SIZE = 12;
    private static int thumbSize;
    private static Color tColor = Color.RED;

    public StyledScrollPane(Component view, Color thumbColor, int thumbSize) {
        this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED, thumbColor, thumbSize);
    }

    public StyledScrollPane(int vsbPolicy, int hsbPolicy, Color thumbColor, int thumbSize) {
        this(null, vsbPolicy, hsbPolicy, thumbColor, thumbSize);
    }

    public StyledScrollPane(Component view, int vsbPolicy, int hsbPolicy, Color thumbColor, int tSize) {
        if (thumbColor != null) {
            tColor = thumbColor;
        }
        if (tSize > 0) {
            thumbSize = tSize;
        }

        setBorder(null);

        // Set ScrollBar UI
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        verticalScrollBar.setUI(new StyledScrollPaneUI(this));

        JScrollBar horizontalScrollBar = getHorizontalScrollBar();
        horizontalScrollBar.setUI(new StyledScrollPaneUI(this));

        setLayout(new ScrollPaneLayout() {
            private static final long serialVersionUID = 5740408979909014146L;

            @Override
            public void layoutContainer(Container parent) {
                Rectangle availR = ((JScrollPane) parent).getBounds();
                availR.x = availR.y = 0;

                // viewport
                Insets insets = parent.getInsets();
                availR.x = insets.left;
                availR.y = insets.top;
                availR.width -= insets.left + insets.right;
                availR.height -= insets.top + insets.bottom;
                if (viewport != null) {
                    viewport.setBounds(availR);
                }

                boolean vsbNeeded = isVerticalScrollBarfNecessary();
                boolean hsbNeeded = isHorizontalScrollBarNecessary();

                // vertical scroll bar
                Rectangle vsbR = new Rectangle();
                vsbR.width = SB_SIZE;
                vsbR.height = availR.height - (hsbNeeded ? vsbR.width : 0);
                vsbR.x = availR.x + availR.width - vsbR.width;
                vsbR.y = availR.y;
                if (vsb != null) {
                    vsb.setBounds(vsbR);
                }

                // horizontal scroll bar
                Rectangle hsbR = new Rectangle();
                hsbR.height = SB_SIZE;
                hsbR.width = availR.width - (vsbNeeded ? hsbR.height : 0);
                hsbR.x = availR.x;
                hsbR.y = availR.y + availR.height - hsbR.height;
                if (hsb != null) {
                    hsb.setBounds(hsbR);
                }
            }
        });

        // Layering
        setComponentZOrder(getVerticalScrollBar(), 0);
        setComponentZOrder(getHorizontalScrollBar(), 1);
        setComponentZOrder(getViewport(), 2);

        viewport.setView(view);
    }

    private boolean isVerticalScrollBarfNecessary() {
        Rectangle viewRect = viewport.getViewRect();
        Dimension viewSize = viewport.getViewSize();
        return viewSize.getHeight() > viewRect.getHeight();
    }

    private boolean isHorizontalScrollBarNecessary() {
        Rectangle viewRect = viewport.getViewRect();
        Dimension viewSize = viewport.getViewSize();
        return viewSize.getWidth() > viewRect.getWidth();
    }

    public void setThumbColor(Color c) {
        tColor = c;
        verticalScrollBar.setUI(new StyledScrollPaneUI(this));
        horizontalScrollBar.setUI(new StyledScrollPaneUI(this));
        revalidate();
    }

    /**
     * Class extending the BasicScrollBarUI and overrides all necessary methods
     */
    private static class StyledScrollPaneUI extends BasicScrollBarUI {

        private JScrollPane sp;

        public StyledScrollPaneUI(StyledScrollPane sp) {
            this.sp = sp;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return new InvisibleScrollBarButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return new InvisibleScrollBarButton();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
            int orientation = scrollbar.getOrientation();
            int x = thumbBounds.x;
            int y = thumbBounds.y;

            int width = orientation == JScrollBar.VERTICAL ? thumbSize : thumbBounds.width;
            width = Math.max(width, thumbSize);

            int height = orientation == JScrollBar.VERTICAL ? thumbBounds.height : thumbSize;
            height = Math.max(height, thumbSize);

            Graphics2D graphics2D = (Graphics2D) g.create();
            graphics2D.setColor(new Color(tColor.getRed(), tColor.getGreen(), tColor.getBlue(), alpha));
            graphics2D.fillRect(x, y, width, height);
            graphics2D.dispose();
        }

        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, width, height);
            sp.repaint();
        }

        /**
         * Invisible Buttons, to hide scroll bar buttons
         */
        private static class InvisibleScrollBarButton extends JButton {

            private static final long serialVersionUID = 1552427919226628689L;

            private InvisibleScrollBarButton() {
                setOpaque(false);
                setFocusable(false);
                setFocusPainted(false);
                setBorderPainted(false);
                setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }
}