package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.button.EditorButtonType;
import com.jcomp.item.ItemBase;
import com.jcomp.line.EditorLine;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class EditorModeDrawLineItem extends EditorMode {

    public EditorModeDrawLineItem() {
        this.targetType = EditorModeTargetType.Item;
    }

    private ItemBase dragSrc;
    private int dragStartPort;
    private EditorLine drawingLine;

    final private double width = 12.0;
    final private double halfWidth = width / 2;

    @Override
    public void handleDragStart(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData());
        if (b.isGroup())
            return;
        dragSrc = b;
        b.getItem().startFullDrag();
        dragStartPort = b.getDragItemDirection(e.getX(), e.getY());
        drawingLine = new EditorLine(b, dragStartPort, getShape(), editor, halfWidth,
                e.getX() + b.getItem().getLayoutX(), e.getY() + b.getItem().getLayoutY());
    }

    @Override
    public void handleMouseDraging(MouseEvent e, UMLEditor editor) {
        if (drawingLine == null)
            return;
        Node n = (Node) e.getSource();
        ItemBase b = (ItemBase) n.getUserData();
        if(b != dragSrc && !b.isGroup())
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
        ((ItemBase)((Node) e.getSource()).getUserData()).removeCorner(editor);
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

        dst.addLine(src.addLine(new EditorLine(src, srcDir, dst, dstDir, getShape(), editor, halfWidth)));
        e.consume();
    }

    private Shape getShape() {
        Double d[];
        Shape shape = null;
        if (type == EditorButtonType.GENERALIZATION) {
            Polygon p = new Polygon();
            d = new Double[] { halfWidth, 0.0, 0.0, width, width, width };
            p.getPoints().addAll(d);
            shape = p;
            shape.setFill(Color.WHITE);
        } else if (type == EditorButtonType.COMPOSITION) {
            Polygon p = new Polygon();
            d = new Double[] { halfWidth, 0.0, 0.0, halfWidth, halfWidth, width, width, halfWidth };
            p.getPoints().addAll(d);
            shape = p;
            shape.setFill(Color.WHITE);
        } else if (type == EditorButtonType.ASSOCIATION) {
            Polyline p = new Polyline();
            d = new Double[] { 0.0, width, halfWidth, 0.0, width, width };
            p.getPoints().addAll(d);
            shape = p;
            shape.setFill(Color.TRANSPARENT);
        }
        shape.setStroke(Color.BLACK);
        return shape;
    }
}
