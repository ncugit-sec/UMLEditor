package com.jcomp.button;

import com.jcomp.UMLEditor;
import com.jcomp.line.head.EditorLineHeadBase;
import com.jcomp.mode.EditorModeDrawLineItem;

public abstract class EditorButtonLine extends EditorButton {

    public EditorButtonLine(UMLEditor editor) {
        super(editor);
        objectMode = new EditorModeDrawLineItem(this);
    }

    public abstract EditorLineHeadBase getAddLineShape();
}