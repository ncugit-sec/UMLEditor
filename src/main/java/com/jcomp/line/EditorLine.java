package com.jcomp.line;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;

public class EditorLine {
    protected ItemBase src, dst;
    protected int srcDir, dstDir;
    protected double halfWidth;
    protected Shape head;
    protected Line line = new Line();
    Rotate rotate = new Rotate();

    /**
     * constructor for destination as a point
     * @param src
     * @param srcDir
     * @param head shape of head
     * @param editor
     * @param halfWidth half of width of head
     * @param x
     * @param y
     */
    
     public EditorLine(ItemBase src, int srcDir, Shape head, UMLEditor editor, double halfWidth, double x, double y) {
        this.src = src;
        this.srcDir = srcDir;
        this.halfWidth = halfWidth;
        this.head = head;
        head.getTransforms().add(rotate);
        rotate.setPivotX(halfWidth);
        rotate.setPivotY(0);
        head.setMouseTransparent(true);
        line.setMouseTransparent(true);
        draw(x, y, editor);
    }

    /** 
     * constructor for destination as a baseitem
     * */ 
    private EditorLine(ItemBase src, int srcDir, Shape head, UMLEditor editor, double halfWidth,
            Pair<Double, Double> to) {
        this(src, srcDir, head, editor, halfWidth, to.getKey(), to.getValue());
    }

    public EditorLine(ItemBase src, int srcDir, ItemBase dst, int dstDir, Shape head, UMLEditor editor,
            double halfWidth) {
        this(src, srcDir, head, editor, halfWidth, dst.getPointbyPort(dstDir));
        this.dst = dst;
        this.dstDir = dstDir;
    }

    /**
     * draw for constructor
     * @param x
     * @param y
     * @param editor
     */
    private void draw(double x, double y, UMLEditor editor) {
        editor.addItemToCanvas(line);
        editor.addItemToCanvas(head);
        updatePos(x, y);
    }

    /**
     * update for destination as a node
     */
    public void updatePos() {
        Pair<Double, Double> to = dst.getPointbyPort(dstDir);
        updatePos(to.getKey(), to.getValue());
    }

    /**
     * update for destination as a point
     * @param x
     * @param y
     */
    public void updatePos(double x, double y) {
        Pair<Double, Double> from = src.getPointbyPort(srcDir);
        line.setStartX(from.getKey());
        line.setStartY(from.getValue());
        line.setEndX(x);
        line.setEndY(y);
        head.setLayoutX(x - halfWidth);
        head.setLayoutY(y);
        rotate.setAngle(Math.atan2(y - from.getValue(), x - from.getKey()) / Math.PI * 180 + 90);
    }

    /**
     * remove from canvas
     * @param editor
     */
    public void remove(UMLEditor editor) {
        editor.removeItemFromCanvas(line);
        editor.removeItemFromCanvas(head);
    }
}
