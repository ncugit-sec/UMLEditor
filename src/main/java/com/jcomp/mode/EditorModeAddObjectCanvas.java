package com.jcomp.mode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.item.shape.ItemShapeBase;

import java.awt.event.MouseEvent;

public class EditorModeAddObjectCanvas extends EditorModeBaseCanvas {
    Constructor<?> itemShapeConstructor = null;
    public EditorModeAddObjectCanvas(Class<?> itemShapeClass) {
        try {
            this.itemShapeConstructor = itemShapeClass.getConstructor(UMLEditor.class, int.class, int.class);
        } catch (NoSuchMethodException | SecurityException e) {
            // handle error
        }
    }

    @Override
    public int getTargetType() {
        return EditorModeTargetType.CANVAS;
    }

    @Override
    public void handleMouseClick(MouseEvent e, UMLEditor editor) {
        try {
            ItemShapeBase shape = (ItemShapeBase) itemShapeConstructor.newInstance(editor, e.getX(), e.getY());
            ItemBase item = new ItemBase(editor, shape);
            editor.addItemToBoth(item);
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
            // handle error
        }
        e.consume();
    }
}
