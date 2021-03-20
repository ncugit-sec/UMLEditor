package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.button.EditorButtonType;
import com.jcomp.item.ItemBase;
import com.jcomp.item.ItemClass;
import com.jcomp.item.ItemUseCase;

import javafx.scene.input.MouseEvent;

public class EditorModeAddObject extends EditorMode {
    /* canvas handler start */
    @Override
    public void handleCanvasClick(MouseEvent e, UMLEditor editor) {
        ItemBase item = null;
        if (type == EditorButtonType.CLASS)
            item = new ItemClass(editor, e.getX(), e.getY());
        else if (type == EditorButtonType.USE_CASE)
            item = new ItemUseCase(editor, e.getX(), e.getY());
        editor.addItemToBoth(item);
        e.consume();
    }

    /* canvas handler end */
}
