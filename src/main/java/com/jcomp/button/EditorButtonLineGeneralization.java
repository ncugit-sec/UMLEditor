package com.jcomp.button;

import javax.swing.JPanel;

import com.jcomp.line.head.EditorLineHeadBase;
import com.jcomp.line.head.EditorLineHeadGeneralization;
import com.jcomp.UMLEditor;

public class EditorButtonLineGeneralization extends EditorButtonLine {

    public EditorButtonLineGeneralization(UMLEditor editor) {
        super(editor);
    }
    @Override
    protected JPanel drawButtonIcon() {
        return drawLineButtonIcon(new EditorLineHeadGeneralization());
    }
    @Override
    public EditorLineHeadBase getAddLineShape() {
        return new EditorLineHeadGeneralization();
    }

}
