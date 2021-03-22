package com.jcomp.item;

import com.jcomp.UMLEditor;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ItemUseCase extends ItemBase {
    private static final long serialVersionUID = 1009464545721069900L;
    static final int MIN_WIDTH = 80;
    static final int HEIGHT = 40;
    static final int PADDING = 5;

    public ItemUseCase(UMLEditor editor, int x, int y) {
        super(editor);
        width = editor.getStringWidth(name, MIN_WIDTH, PADDING * 2);
        height = HEIGHT;
        setBounds(x, y, width, HEIGHT);
        setOpaque(false);
    }

    @Override
    protected void drawItem(Graphics g) {
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(name);
        width = Math.max(width, stringWidth + PADDING * 2);
        g.setColor(Color.GRAY);
        g.fillOval(0, 0, width, HEIGHT);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, width, HEIGHT);
        g.drawString(name, (width - stringWidth) / 2, (HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent());
    }

    @Override
    public void _setText(UMLEditor editor) {
        width = editor.getStringWidth(name, MIN_WIDTH, PADDING * 2);
    };
}
