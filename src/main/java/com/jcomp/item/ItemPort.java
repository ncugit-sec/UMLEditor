package com.jcomp.item;

import javax.swing.JPanel;

public class ItemPort extends JPanel {

    public int getPortStartX() {
        return getX() + getWidth() / 2;
    }

    public int getPortStartY() {
        return getY() + getHeight() / 2;
    }
}
