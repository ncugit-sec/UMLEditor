package com.jcomp.line;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.line.head.EditorLineHeadBase;

import java.awt.geom.Line2D;
import java.awt.Graphics2D;

public class EditorLine {
    protected ItemBase src, dst;
    protected int srcDir, dstDir;
    protected EditorLineHeadBase head;
    protected double halfWidth;

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

    public EditorLine(EditorLineHeadBase head, ItemBase src, int srcDir, UMLEditor editor, int halfWidth, double x,
            double y) {
        this.src = src;
        this.srcDir = srcDir;
        this.head = head;
        this.halfWidth = halfWidth;
    }

    public EditorLine(EditorLineHeadBase head, ItemBase src, int srcDir, ItemBase dst, int dstDir, UMLEditor editor,
            int halfWidth) {
        this(head, src, srcDir, editor, halfWidth, dst.getPortX(dstDir), dst.getPortY(dstDir));
        this.dst = dst;
        this.dstDir = dstDir;
    }

    /**
     * update for destination as a point
     * 
     * @param x
     * @param y
     */
    public void draw(Graphics2D g2, int x, int y) {
        g2.draw(new Line2D.Double(src.getPortX(srcDir), src.getPortY(srcDir), x, y));
        head.drawHead(g2, src.getPortX(srcDir), src.getPortY(srcDir), x, y);
    }

    public void draw(Graphics2D g2) {
        g2.draw(new Line2D.Double(src.getPortX(srcDir), src.getPortY(srcDir), dst.getPortX(dstDir),
                dst.getPortY(dstDir)));
        head.drawHead(g2, src.getPortX(srcDir), src.getPortY(srcDir), dst.getPortX(dstDir), dst.getPortY(dstDir));
    }
}
