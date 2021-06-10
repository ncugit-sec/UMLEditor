package com.jcomp.button;

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import com.jcomp.UMLEditor;
import com.jcomp.mode.EditorModeSelectCanvas;
import com.jcomp.mode.EditorModeSelectItem;

public class EditorButtonActionSelect extends EditorButton {
    public EditorButtonActionSelect(UMLEditor editor) {
        super(editor);
        canvasMode = new EditorModeSelectCanvas();
        objectMode = new EditorModeSelectItem();
    }
    
    @Override
    protected JPanel drawButtonIcon() {
        return new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.fillPolygon(new Polygon(new int[] { 8, 19, 29, }, new int[] { 8, 28, 14 }, 3));
                g.drawLine(23, 22, 40, 40);
            }
        };
    }
}