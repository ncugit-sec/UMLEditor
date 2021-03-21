package com.jcomp.mode;

import com.jcomp.UMLEditor;

import javafx.geometry.BoundingBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EditorModeSelectCanvas extends EditorModeBaseCanvas {
    private Rectangle dragRect = new Rectangle(10, 10);

    public EditorModeSelectCanvas() {
        dragRect.setFill(Color.TRANSPARENT);
        dragRect.setStroke(Color.BLACK);
        dragRect.setStrokeWidth(2);
        dragRect.getStrokeDashArray().addAll(40.0, 30.0, 20.0, 15.0);
        dragRect.setStrokeDashOffset(2);
    }

    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        editor.clearSelect();
        dragStartX = e.getX();
        dragStartY = e.getY();
        e.consume();
    }

    @Override
    public void handleDragStart(MouseEvent e, UMLEditor editor) {
        dragRect.setWidth(0);
        dragRect.setHeight(0);
        editor.addItemToCanvas(dragRect);
        return;
    }

    @Override
    public void handleMouseDraging(MouseEvent e, UMLEditor editor) {
        double startX = dragStartX;
        double startY = dragStartY;
        double endX = e.getX();
        double endY = e.getY();
        if (dragStartX > endX) {
            startX = endX;
            endX = dragStartX;
        }
        if (dragStartY > endY) {
            startY = endY;
            endY = dragStartY;
        }
        dragRect.setLayoutX(startX);
        dragRect.setLayoutY(startY);
        dragRect.setWidth(endX - startX);
        dragRect.setHeight(endY - startY);
        editor.boundSelect(new BoundingBox(startX, startY, endX - startX, endY - startY));
        return;
    }

    @Override
    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        editor.removeItemFromCanvas(dragRect);
    }

    private double dragStartX, dragStartY;
}
