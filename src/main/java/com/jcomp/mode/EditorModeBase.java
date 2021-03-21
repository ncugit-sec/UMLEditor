package com.jcomp.mode;

import com.jcomp.UMLEditor;

import javafx.scene.input.MouseEvent;

public class EditorModeBase {
    public int getTargetType() {
        return -1;
    }

    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseClick(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleDragStart(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseDraging(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleDragEnd(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseDragExit(MouseEvent e, UMLEditor editor) {
        return;
    }

    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        return;
    }

    public final static class EditorModeTargetType {
        public static final int CANVAS = 0;
        public static final int Item = 1;
    }
}
