package com.jcomp.item.shape;

import com.jcomp.UMLEditor;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ItemShapeClass extends ItemShapeBase {
    private static final long serialVersionUID = 5985654579081143671L;
    private int stackHeight = 20;

    public ItemShapeClass(UMLEditor editor, int x, int y) {
        super(editor, x, y, 40, 60); // stackHeight * 2
    }

    public ItemShapeClass(UMLEditor editor, int x, int y, int width, int height) {
        super(editor, x, y, width, height); // stackHeight * 2
        stackHeight = height / 3;
    }

    @Override
    public void paintComponent(Graphics g) {
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(name);
        int width = Math.max(minWidth, stringWidth + padding * 2);

        g.setColor(Color.GRAY);
        g.fillRect(0, diffY, width, stackHeight);
        g.fillRect(0, diffY + stackHeight, width, stackHeight);
        g.fillRect(0, diffY + stackHeight * 2, width, stackHeight);

        g.setColor(Color.BLACK);
        g.drawRect(0, diffY, width, stackHeight);
        g.drawRect(0, diffY + stackHeight, width, stackHeight);
        g.drawRect(0, diffY + stackHeight * 2, width, stackHeight);
        g.drawString(name, (width - stringWidth) / 2, (stackHeight - metrics.getHeight()) / 2 + metrics.getAscent());
    }
}
