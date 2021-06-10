package com.jcomp.mode;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.jcomp.UMLEditor;

public class EditorModeSelectCanvas extends EditorModeBase {
    private JPanel dragRect = new JPanel();

    public EditorModeSelectCanvas() {
        dragRect.setBorder(BorderFactory.createDashedBorder(Color.black));        
    }

    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        editor.clearSelect();
        dragStartX = e.getX();
        dragStartY = e.getY();
        editor.addItemToCanvas(dragRect);
        dragRect.setBounds(e.getX(), e.getY(), 5, 5);
        dragRect.setOpaque(false);
        e.consume();
    }

    @Override
    public void handleMouseDragging(MouseEvent e, UMLEditor editor) {
        int startX = dragStartX;
        int startY = dragStartY;
        int endX = e.getX();
        int endY = e.getY();
        if (dragStartX > endX) {
            startX = endX;
            endX = dragStartX;
        }
        if (dragStartY > endY) {
            startY = endY;
            endY = dragStartY;
        }
        dragRect.setBounds(startX, startY, endX - startX, endY - startY);
        editor.boundSelect(new Rectangle(startX, startY, endX - startX, endY - startY));
        editor.canvasRepaint();
        return;
    }

    @Override
    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        editor.removeItemFromCanvas(dragRect);
    }

    private int dragStartX, dragStartY;
}
