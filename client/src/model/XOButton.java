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