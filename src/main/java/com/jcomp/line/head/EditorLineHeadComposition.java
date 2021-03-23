package com.jcomp.line.head;

import java.awt.Polygon;
import java.awt.Color;
import java.awt.Graphics2D;

public class EditorLineHeadComposition extends EditorLineHeadBase {
    @Override
    protected void _drawHead(Graphics2D g2, double startX, double startY, double endX, double endY, double radianTop,
            double radianDown, double topSin, double topCos, double downSin, double downCos, double dist,
            double arrowUnit, double topX, double topY, double downX, double downY) {
        Polygon arrow = new Polygon();

        arrow.addPoint((int) endX, (int) endY);
        arrow.addPoint((int) topX, (int) topY);
        arrow.addPoint((int) (topX + downX - endX), (int) (topY + downY - endY));
        arrow.addPoint((int) downX, (int) downY);

        g2.setPaint(Color.white);
        g2.fill(arrow);
        g2.setPaint(Color.black);
        g2.draw(arrow);
    }
}
