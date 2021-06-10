package com.jcomp.button;

import javax.swing.JPanel;

import com.jcomp.item.shape.ItemShapeBase;
import com.jcomp.item.shape.ItemShapeClass;
import com.jcomp.UMLEditor;

public class EditorButtonObjectClass extends EditorButtonObject {

    public EditorButtonObjectClass(UMLEditor editor) {
        super(editor);
    }
    @Override
    protected JPanel drawButtonIcon() {
        return new ItemShapeClass(0, 0, ICON_WIDTH, ICON_WIDTH);
    }

    @Override
    public ItemShapeBase getObjectShape(int x, int y) {
        return new ItemShapeClass(umlEditor, x, y);
    }

}
