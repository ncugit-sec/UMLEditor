package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;

import javafx.geometry.BoundingBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EditorModeSelect extends EditorMode {
    private Rectangle dragRect = new Rectangle(10, 10);

    public EditorModeSelect() {
        dragRect.setFill(Color.TRANSPARENT);
        dragRect.setStroke(Color.BLACK);
        dragRect.setStrokeWidth(2);
        dragRect.getStrokeDashArray().addAll(40.0, 30.0, 20.0, 15.0);
        dragRect.setStrokeDashOffset(2);
    }

    /* Item handler start */
    @Override
    public void handleItemMousePressed(MouseEvent e, ItemBase b, UMLEditor editor) {
        dragStartX = e.getScreenX();
        dragStartY = e.getScreenY();
        editor.clearSelect(b);
        b.setDragStart();
        e.consume();
    }

    @Override
    public void handleItemMouseDrag(MouseEvent e, ItemBase b, UMLEditor editor) {
        b.updatePos(e.getScreenX() - dragStartX, e.getScreenY() - dragStartY);
        e.consume();
    }

    /* Item handler end */
    /* canvas handler start */

    @Override
    public void handleCanvasMousePressed(MouseEvent e, UMLEditor editor) {
        editor.clearSelect();
        dragStartX = e.getX();
        dragStartY = e.getY();
        e.consume();
    }

    @Override
    public void handleCanvasDragStart(MouseEvent e, UMLEditor editor) {
        dragRect.setWidth(0);
        dragRect.setHeight(0);
        editor.addItemToCanvas(dragRect);
        return;
    }

    @Override
    public void handleCanvasMouseDrag(MouseEvent e, UMLEditor editor) {
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
    public void handleCanvasMouseReleased(MouseEvent e, UMLEditor editor) {
        editor.removeItemFromCanvas(dragRect);
    }

    /* canvas handler end */
    private double dragStartX, dragStartY;
}
