package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.item.shape.ItemShapeBase;

import java.awt.event.MouseEvent;

public class EditorModeSelectItem extends EditorModeBase {
    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemShapeBase) e.getSource()).getRoot().getParent();
        dragStartX = e.getXOnScreen();
        dragStartY = e.getYOnScreen();
        editor.clearSelect(b);
        b.setDragStart();
        e.consume();
    }

    @Override
    public void handleMouseDragging(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemShapeBase) e.getSource()).getRoot().getParent();
        b.updatePos(e.getXOnScreen() - dragStartX, e.getYOnScreen() - dragStartY);
        editor.canvasRepaint();
        e.consume();
    }

    private int dragStartX, dragStartY;
}
