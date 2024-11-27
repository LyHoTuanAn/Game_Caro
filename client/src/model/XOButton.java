package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class XOButton extends JButton {
    public static boolean isXMove = true;
    public Point point;
    public int value = 0;
    private final ImageIcon xImageIcon;
    private final ImageIcon oImageIcon;
    private ImageIcon hintIcon;
    private boolean showHintIcon = false;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If hint icon is set, draw it over the button
        if (showHintIcon && hintIcon != null) {
            // Calculate position to center the hint icon
            int x = (getWidth() - hintIcon.getIconWidth()) / 2;
            int y = (getHeight() - hintIcon.getIconHeight()) / 2;

            hintIcon.paintIcon(this, g, x, y);
        }
    }

    public void setHintIcon(ImageIcon icon) {
        this.hintIcon = icon;
        this.showHintIcon = true;
        repaint();
    }

    public void clearHintIcon() {
        this.showHintIcon = false;
        repaint();
    }
    public XOButton(int xImageIcon, int y) {
        this.xImageIcon = new ImageIcon("assets/image/x3.jpg");
        oImageIcon = new ImageIcon("assets/image/o3.jpg");
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        this.setIcon(new ImageIcon("assets/image/blank.jpg"));
        this.point = new Point(xImageIcon, y);
        XOButton _this = this;
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (_this.isEnabled()) {
                    _this.setBackground(null);
                    _this.setIcon(new ImageIcon("assets/image/blank.jpg"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (_this.isEnabled()) {
                    _this.setBackground(Color.GREEN);
                    _this.setIcon(new ImageIcon("assets/image/x3.jpg"));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
    }

    public void setState(Boolean isXMove) {
        if (isXMove) {
            setIcon(xImageIcon);
            value = 2;
            XOButton.isXMove = false;
            this.setDisabledIcon(xImageIcon);
        } else {
            setIcon(oImageIcon);
            value = 1;
            this.setDisabledIcon(oImageIcon);
            XOButton.isXMove = true;
        }
    }

    public void resetState() {
        value = 0;
        this.setEnabled(true);
        this.setIcon(new ImageIcon("assets/image/blank.jpg"));
        this.setDisabledIcon(new ImageIcon("assets/image/blank.jpg"));
    }


}