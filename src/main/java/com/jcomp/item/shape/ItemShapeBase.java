package com.jcomp.item.shape;

import javax.swing.JPanel;

import com.jcomp.UMLEditor;

public abstract class ItemShapeBase extends JPanel {
    private static final long serialVersionUID = -5944192777275476410L;
    protected String name = "";
    protected int minWidth;
    protected int minHeight;
    protected int padding = 10;
    protected int diffY = 0;

    protected ItemShapeBase(UMLEditor editor, int x, int y, int minWidth, int minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        setBounds(x, y, minWidth, minHeight);
        setOpaque(false);
    }

    public void updateText(UMLEditor editor, String s) {
        name = s;
        setBounds(getX(), getY(), editor.getStringWidth(name, minWidth, padding * 2), getHeight());
        repaint();
        editor.repaint();
    }

    public String getName() {
        return name;
    }
}
