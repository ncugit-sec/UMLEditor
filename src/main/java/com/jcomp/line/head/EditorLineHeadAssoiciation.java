package com.jcomp.line.head;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class EditorLineHeadAssoiciation extends EditorLineHeadBase {

    @Override
    protected void _drawHead(Graphics2D g2, double startX, double startY, double endX, double endY, double radianTop,
            double radianDown, double topSin, double topCos, double downSin, double downCos, double dist,
            double arrowUnit, double topX, double topY, double downX, double downY) {

        GeneralPath arrow = new GeneralPath();
        arrow.moveTo(endX, endY);
        arrow.lineTo(topX, topY);
        arrow.lineTo(endX, endY);
        arrow.lineTo(downX, downY);
        arrow.closePath();

        g2.draw(arrow);
    }
}
