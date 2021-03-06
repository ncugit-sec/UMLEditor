package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.item.shape.ItemShapeBase;

import java.awt.event.MouseEvent;

public class EditorModeSelectItem extends EditorModeBaseItem {
    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase)((ItemShapeBase) e.getSource()).getClientProperty("parent")).getItemParent();
        dragStartX = e.getXOnScreen();
        dragStartY = e.getYOnScreen();
        editor.clearSelect(b);
        b.setDragStart();
        e.consume();
    }

    @Override
    public void handleMouseDragging(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase)((ItemShapeBase) e.getSource()).getClientProperty("parent")).getItemParent();
        b.updatePos(e.getXOnScreen() - dragStartX, e.getYOnScreen() - dragStartY);
        editor.canvasRepaint();
        e.consume();
    }

    private int dragStartX, dragStartY;
}
