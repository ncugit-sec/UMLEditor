package com.jcomp.mode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.line.EditorLine;
import com.jcomp.line.head.EditorLineHeadAssoiciation;
import com.jcomp.line.head.EditorLineHeadBase;

import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EditorModeDrawLineItem extends EditorModeBaseItem {

    private ItemBase dragSrc, dragDst;
    private int dragStartPort;
    private int dragX, dragY;
    private EditorLine preview;
    final private int HEAD_HALF_WIDTH;

    private Constructor<?> shapeClassConstructor;

    public EditorModeDrawLineItem(int halfWidth, Class<?> shapeClass) {
        this.HEAD_HALF_WIDTH = halfWidth;
        try {
            shapeClassConstructor = shapeClass.getConstructor();
        } catch (NoSuchMethodException | SecurityException e) {
            shapeClassConstructor = null;
        }
    }

    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        ItemBase b = (ItemBase) e.getSource();
        dragDst = null;
        dragSrc = null;
        preview = null;
        if (b.isGroup())
            return;
        dragSrc = b;
        dragStartPort = b.getDragItemDirection(e.getX(), e.getY());
        preview = new EditorLine(getAddLineShape(), dragSrc, dragStartPort, editor, HEAD_HALF_WIDTH, e.getX(), e.getY());
    }

    @Override
    public void handleMouseEntered(MouseEvent e, UMLEditor editor) {
        if (dragSrc != null && dragSrc != e.getSource()) 
            dragDst = (ItemBase) e.getSource();
    }

    @Override
    public void handleMouseDragging(MouseEvent e, UMLEditor editor) {
        if (dragDst != null && !dragDst.isGroup()) {
            dragDst.addCorner(editor, e.getX() + dragSrc.getX() - dragDst.getX(),
                    e.getY() + dragSrc.getY() - dragDst.getY());
        }
        ItemBase b = (ItemBase) e.getSource();
        dragX = e.getX() + b.getX();
        dragY = e.getY() + b.getY();
        editor.repaint();
    }

    public void handleMouseExit(MouseEvent e, UMLEditor editor) {
        dragDst = null;
        ((ItemBase) e.getSource()).removeCorner(editor);
    }

    @Override
    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        if (dragDst != null && !dragDst.isGroup()) {
            dragDst.removeCorner(editor);
            int dragEndPort = dragDst.getDragItemDirection(e.getX() + dragSrc.getX() - dragDst.getX(),
                    e.getY() + dragSrc.getY() - dragDst.getY());
            dragDst.addLine(dragSrc.addLine(new EditorLine(getAddLineShape(), dragSrc, dragStartPort, dragDst,
                    dragEndPort, editor, HEAD_HALF_WIDTH)));
        }
        dragSrc = null;
        dragDst = null;
        preview = null;
        editor.canvasRepaint();
    }

    @Override
    public void drawOnCanvas(Graphics g) {
        if (preview != null) 
            preview.draw((Graphics2D)g, dragX, dragY);
    }

    /**
     * Get head shape Constructor of assigned line type
     * 
     * @return EditorLineHeadBase
     */
    private EditorLineHeadBase getAddLineShape() {
        EditorLineHeadBase shape = new EditorLineHeadAssoiciation();
        if (shapeClassConstructor != null)
            try {
                shape = (EditorLineHeadBase) shapeClassConstructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // handle error
            }
        return shape;
    }
}
