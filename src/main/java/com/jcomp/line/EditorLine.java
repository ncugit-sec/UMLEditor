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

    protected EditorLine(ItemBase src, int srcDir, UMLEditor editor, double halfWidth) {
        this.src = src;
        this.srcDir =  srcDir;
        this.halfWidth = halfWidth;
        rotate.setPivotX(halfWidth);
        rotate.setPivotY(0);
        
    }
    
    public EditorLine(ItemBase src, int srcDir, ItemBase dst, int dstDir, Shape head, UMLEditor editor, double halfWidth) {
        this(src, srcDir, editor, halfWidth);
        this.dst = dst;
        this.dstDir = dstDir;
        this.head = head;
        head.getTransforms().add(rotate);
        
        updatePos();

        editor.addItemToCanvas(line);
        editor.addItemToCanvas(head);
    }

    public void updatePos() {
        Pair<Double, Double> from = src.getPointbyPort(srcDir);
        Pair<Double, Double> to = dst.getPointbyPort(dstDir);
        line.setStartX(from.getKey());
        line.setStartY(from.getValue());
        line.setEndX(to.getKey());
        line.setEndY(to.getValue());
        head.setLayoutX(to.getKey() - halfWidth);
        head.setLayoutY(to.getValue());
        rotate.setAngle(Math.atan2(to.getValue() - from.getValue(), to.getKey() - from.getKey()) / Math.PI * 180 + 90);
    }
}
