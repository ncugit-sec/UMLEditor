package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EditorModeSelectItem extends EditorModeBaseItem {
    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData()).getParent();
        dragStartX = e.getScreenX();
        dragStartY = e.getScreenY();
        editor.clearSelect(b);
        b.setDragStart();
        e.consume();
    }

    @Override
    public void handleMouseDraging(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData()).getParent();
        b.updatePos(e.getScreenX() - dragStartX, e.getScreenY() - dragStartY);
        e.consume();
    }

    private double dragStartX, dragStartY;
}
