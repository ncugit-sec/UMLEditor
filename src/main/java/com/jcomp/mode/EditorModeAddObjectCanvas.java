package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.button.EditorButtonObject;
import com.jcomp.item.ItemBase;
import com.jcomp.item.shape.ItemShapeBase;

import java.awt.event.MouseEvent;

public class EditorModeAddObjectCanvas extends EditorModeBase {
    EditorButtonObject button;
    public EditorModeAddObjectCanvas(EditorButtonObject button) {
        this.button = button;
    }

    @Override
    public void handleMouseClick(MouseEvent e, UMLEditor editor) {
        ItemShapeBase shape = button.getObjectShape(e.getX(), e.getY());
        ItemBase item = new ItemBase(editor, shape);
        editor.addItemToBoth(item);
        e.consume();
    }
}
