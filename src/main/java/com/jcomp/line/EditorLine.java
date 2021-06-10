package com.jcomp.line;

import com.jcomp.item.ItemPort;
import com.jcomp.line.head.EditorLineHeadBase;

import java.awt.geom.Line2D;

import java.awt.Graphics2D;

public class EditorLine {
    protected EditorLineHeadBase head;
    protected ItemPort srcPort, dstPort;
    protected double halfWidth = 10;

    /**
     * constructor for destination as a point
     * 
     * @param src
     * @param srcDir
     * @param editor
     * @param halfWidth
     * @param x
     * @param y
     */

    public EditorLine(EditorLineHeadBase head, ItemPort srcPort) {
        this.head = head;
        this.srcPort = srcPort;
    }

    public EditorLine(EditorLineHeadBase head, ItemPort srcPort, ItemPort dstPort) {
        this(head, srcPort);
        this.dstPort = dstPort;
    }

    /**
     * update for destination as a point
     * 
     * @param x
     * @param y
     */
    public void draw(Graphics2D g2, int x, int y) {
        g2.draw(new Line2D.Double(srcPort.getPortStartX(), srcPort.getPortStartY(), x, y));
        head.drawHead(g2, srcPort.getPortStartX(), srcPort.getPortStartY(), x, y);
    }

    public void draw(Graphics2D g2) {
        g2.draw(new Line2D.Double(srcPort.getPortStartX(), srcPort.getPortStartY(), dstPort.getPortStartX(), dstPort.getPortStartY()));
        head.drawHead(g2, srcPort.getPortStartX(), srcPort.getPortStartY(),  dstPort.getPortStartX(), dstPort.getPortStartY());
    }
}
