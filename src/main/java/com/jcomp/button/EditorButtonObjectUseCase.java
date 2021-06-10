package com.jcomp.button;

import javax.swing.JPanel;

import com.jcomp.item.shape.ItemShapeBase;
import com.jcomp.item.shape.ItemShapeUseCase;

import com.jcomp.UMLEditor;

public class EditorButtonObjectUseCase extends EditorButtonObject {

    public EditorButtonObjectUseCase(UMLEditor editor) {
        super(editor);
    }
    @Override
    protected JPanel drawButtonIcon() {
        int widthHeightDiff = 20;
        return new ItemShapeUseCase(0, widthHeightDiff / 2, ICON_WIDTH, ICON_WIDTH - widthHeightDiff);
    }
    @Override
    public ItemShapeBase getObjectShape(int x, int y) {
        return new ItemShapeUseCase(umlEditor, x, y);
    }
}
