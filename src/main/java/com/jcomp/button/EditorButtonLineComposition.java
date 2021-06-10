package com.jcomp.button;

import javax.swing.JPanel;

import com.jcomp.line.head.EditorLineHeadBase;
import com.jcomp.line.head.EditorLineHeadComposition;
import com.jcomp.UMLEditor;

public class EditorButtonLineComposition extends EditorButtonLine {

    public EditorButtonLineComposition(UMLEditor editor) {
        super(editor);
    }
    @Override
    protected JPanel drawButtonIcon() {
        return drawLineButtonIcon(new EditorLineHeadComposition());
    }
    @Override
    public EditorLineHeadBase getAddLineShape() {
        return new EditorLineHeadComposition();
    }

}
