package com.jcomp.mode;

import com.jcomp.UMLEditor;

import java.awt.event.MouseEvent;
import java.awt.Graphics;

public class EditorModeBase {
    public int getTargetType() {
        return -1;
    }

    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseEntered(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseDragging(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseMoving(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseExit(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseClick(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void drawOnCanvas(Graphics g) {
        
    }

    public final static class EditorModeTargetType {
        public static final int CANVAS = 0;
        public static final int Item = 1;
    }
}
