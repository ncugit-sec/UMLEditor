package com.jcomp.mode;

import com.jcomp.UMLEditor;

import javafx.scene.input.MouseEvent;

public class EditorMode {
    public EditorMode() {
        this.targetType = -1;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTargetType() {
        return targetType;
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

    protected int type;
    protected int targetType;

    public final static class EditorModeTargetType {
        public static final int CANVAS = 0;
        public static final int Item = 1;
    }
}
