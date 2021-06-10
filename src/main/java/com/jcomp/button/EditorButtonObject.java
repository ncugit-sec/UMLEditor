package com.jcomp.button;

import com.jcomp.UMLEditor;
import com.jcomp.item.shape.ItemShapeBase;
import com.jcomp.mode.EditorModeAddObjectCanvas;

public abstract class EditorButtonObject extends EditorButton {

    public EditorButtonObject(UMLEditor editor) {
        super(editor);
        canvasMode = new EditorModeAddObjectCanvas(this);
    }

    
    public abstract ItemShapeBase getObjectShape(int x, int y);
}