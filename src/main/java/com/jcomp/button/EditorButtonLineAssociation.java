package com.jcomp.button;

import javax.swing.JPanel;

import com.jcomp.line.head.EditorLineHeadAssoiciation;
import com.jcomp.line.head.EditorLineHeadBase;
import com.jcomp.UMLEditor;


public class EditorButtonLineAssociation extends EditorButtonLine {

    public EditorButtonLineAssociation(UMLEditor editor) {
        super(editor);
        
    }
    @Override
    protected JPanel drawButtonIcon() {
        return drawLineButtonIcon(new EditorLineHeadAssoiciation());
    }
    @Override
    public EditorLineHeadBase getAddLineShape() {
        return new EditorLineHeadAssoiciation();
    }

}
