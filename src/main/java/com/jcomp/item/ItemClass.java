package com.jcomp.item;

import com.jcomp.UMLEditor;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ItemClass extends ItemBase {
    private static final long serialVersionUID = 5985654579081143671L;
    static final int MIN_WIDTH = 40;
    static final int HEIGHT = 20;
    static final int PADDING = 10;

    public ItemClass(UMLEditor editor, int x, int y) {
        super(editor);
        width = editor.getStringWidth(name, MIN_WIDTH, PADDING * 2);
        height = HEIGHT * 3;
        setBounds(x, y, width, height);
    }

    @Override
    protected void drawItem(Graphics g) {
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(name);
        width = Math.max(width, stringWidth + PADDING * 2);

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, HEIGHT);
        g.fillRect(0, HEIGHT, width, HEIGHT);
        g.fillRect(0, HEIGHT * 2, width, HEIGHT);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width, HEIGHT);
        g.drawRect(0, HEIGHT, width, HEIGHT);
        g.drawRect(0, HEIGHT * 2, width, HEIGHT);
        g.drawString(name, (width - stringWidth) / 2, (HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent());
    }

    @Override
    public void _setText(UMLEditor editor) {
        width = editor.getStringWidth(name, MIN_WIDTH, PADDING * 2);
    };
}
