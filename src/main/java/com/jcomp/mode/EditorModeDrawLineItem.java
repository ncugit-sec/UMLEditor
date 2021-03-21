package com.jcomp.mode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.line.EditorLine;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class EditorModeDrawLineItem extends EditorModeBaseItem {

    private ItemBase dragSrc;
    private int dragStartPort;
    private EditorLine drawingLine;
    final private double HEAD_HALF_WIDTH;

    private Constructor<?> shapeClassConstructor;

    public EditorModeDrawLineItem(double halfWidth, Class<?> shapeClass) {
        this.HEAD_HALF_WIDTH = halfWidth;
        try {
            shapeClassConstructor = shapeClass.getConstructor(double.class);
        } catch (NoSuchMethodException | SecurityException e) {
            shapeClassConstructor = null;
        }
    }

    @Override
    public void handleDragStart(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData());
        if (b.isGroup())
            return;
        dragSrc = b;
        b.getItem().startFullDrag();
        dragStartPort = b.getDragItemDirection(e.getX(), e.getY());
        drawingLine = new EditorLine(b, dragStartPort, getAddLineShape(), editor, HEAD_HALF_WIDTH,
                e.getX() + b.getItem().getLayoutX(), e.getY() + b.getItem().getLayoutY());
    }

    @Override
    public void handleMouseDraging(MouseEvent e, UMLEditor editor) {
        if (drawingLine == null)
            return;
        Node n = (Node) e.getSource();
        ItemBase b = (ItemBase) n.getUserData();
        if (b != dragSrc && !b.isGroup())
            ((ItemBase) n.getUserData()).addCorner(editor, e.getX(), e.getY());
        drawingLine.updatePos(e.getX() + n.getLayoutX(), e.getY() + n.getLayoutY());
    }

    @Override
    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        if (drawingLine == null)
            return;
        drawingLine.remove(editor);
        drawingLine = null;
    }

    public void handleMouseDragExit(MouseEvent e, UMLEditor editor) {
        ((ItemBase) ((Node) e.getSource()).getUserData()).removeCorner(editor);
    }

    @Override
    public void handleDragEnd(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData());
        if (b.isGroup() || dragSrc == b)
            return;
        ItemBase src = dragSrc;
        int srcDir = dragStartPort;
        ItemBase dst = b;
        int dstDir = b.getDragItemDirection(e.getX(), e.getY());

        dst.addLine(src.addLine(new EditorLine(src, srcDir, dst, dstDir, getAddLineShape(), editor, HEAD_HALF_WIDTH)));
        e.consume();
    }

    /**
     * Get head shape of assigned line type
     * @return Shape
     */
    private Shape getAddLineShape() {
        Shape shape = new Rectangle();
        if (shapeClassConstructor != null)
            try {
                shape = (Shape) shapeClassConstructor.newInstance(HEAD_HALF_WIDTH * 2);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // handle error
            }
        return shape;
    }
}
