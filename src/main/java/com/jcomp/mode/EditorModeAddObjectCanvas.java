package com.jcomp.mode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;

import javafx.scene.input.MouseEvent;

public class EditorModeAddObjectCanvas extends EditorModeBaseCanvas {
    Class<?> itemBaseClass;
    public EditorModeAddObjectCanvas(Class<?> itemBaseClass) {
        this.itemBaseClass = itemBaseClass;
    }

    @Override
    public int getTargetType() {
        return EditorModeTargetType.CANVAS;
    }

    @Override
    public void handleMouseClick(MouseEvent e, UMLEditor editor) {
        try {
            Constructor<?> constructor = itemBaseClass.getConstructor(UMLEditor.class, double.class, double.class);
            ItemBase item = (ItemBase) constructor.newInstance(editor, e.getX(), e.getY());
            editor.addItemToBoth(item);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
            // handle error
        }
        e.consume();
    }
}
