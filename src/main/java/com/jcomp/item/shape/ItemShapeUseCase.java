package com.jcomp.item.shape;

import com.jcomp.UMLEditor;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ItemShapeUseCase extends ItemShapeBase {
    private static final long serialVersionUID = 1009464545721069900L;

    public ItemShapeUseCase(UMLEditor editor, int x, int y) {
        super(editor, x, y, 80, 40);
    }

    public ItemShapeUseCase(UMLEditor editor, int x, int y, int width, int height) {
        super(editor, x, y, width, height); // stackHeight * 2
        diffY = y;
    }

    @Override
    public void paintComponent(Graphics g) {
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(name);
        int width = Math.max(minWidth, stringWidth + padding * 2);
        g.setColor(Color.GRAY);
        g.fillOval(0, diffY, width, minHeight);
        g.setColor(Color.BLACK);
        g.drawOval(0, diffY, width, minHeight);
        g.drawString(name, (width - stringWidth) / 2, (minHeight - metrics.getHeight()) / 2 + metrics.getAscent());
    }
}
