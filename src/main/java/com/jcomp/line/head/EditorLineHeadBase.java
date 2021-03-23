package com.jcomp.line.head;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class EditorLineHeadBase {
    public void drawHead(Graphics2D g2, double startX, double startY, double endX, double endY) {
        int angle = 30;
        double radianTop = Math.toRadians(angle);
        double radianDown = Math.toRadians(-1 * angle);
        double topSin = Math.sin(radianTop);
        double topCos = Math.cos(radianTop);
        double downSin = Math.sin(radianDown);
        double downCos = Math.cos(radianDown);

        double dist = Point.distance(startX, startY, endX, endY);
        double arrowUnit = 10 / dist;
        double topX = ((startX - endX) * topCos - (startY - endY) * topSin) * arrowUnit + endX;
        double topY = ((startX - endX) * topSin + (startY - endY) * topCos) * arrowUnit + endY;

        double downX = ((startX - endX) * downCos - (startY - endY) * downSin) * arrowUnit + endX;
        double downY = ((startX - endX) * downSin + (startY - endY) * downCos) * arrowUnit + endY;
        _drawHead(g2, startX, startY, endX, endY, radianTop, radianDown, topSin, topCos, downSin, downCos, dist,
                arrowUnit, topX, topY, downX, downY);
    }

    protected abstract void _drawHead(Graphics2D g2, double startX, double startY, double endX, double endY, double radianTop,
            double radianDown, double topSin, double topCos, double downSin, double downCos, double dist,
            double arrowUnit, double topX, double topY, double downX, double downY);
}
