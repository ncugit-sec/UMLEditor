package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;

import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public class EditorMode {
    public void setType(int type) {
        this.type = type;
    }

    /* Item handler start */
    public void handleItemMousePressed(MouseEvent e, ItemBase b, UMLEditor editor) {
        return;
    }

    public void handleItemDragStart(MouseEvent e, ItemBase b, UMLEditor editor) {
        return;
    }

    public void handleItemDragOver(DragEvent e, ItemBase b, UMLEditor editor) {
        return;
    }

    public void handleItemDragEnd(DragEvent e, ItemBase b, UMLEditor editor) {
        return;
    }

    public void handleItemMouseDrag(MouseEvent e, ItemBase b, UMLEditor editor) {
        return;
    }

    /* Item handler end */
    /* canvas handler start */

    public void handleCanvasMousePressed(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleCanvasClick(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleCanvasDragStart(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleCanvasMouseDrag(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleCanvasMouseReleased(MouseEvent e, UMLEditor editor) {
        return;
    }

    /* canvas handler end */
    protected int type;
}
