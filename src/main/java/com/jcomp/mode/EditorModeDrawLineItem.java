package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.button.EditorButtonLine;
import com.jcomp.item.ItemBase;
import com.jcomp.item.ItemPort;
import com.jcomp.item.shape.ItemShapeBase;
import com.jcomp.line.EditorLine;

import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EditorModeDrawLineItem extends EditorModeBase {
    private EditorButtonLine button;
    private ItemBase src;
    private ItemShapeBase dst;
    private ItemPort dragStartPort;
    private int dragX, dragY;
    private EditorLine preview;

    public EditorModeDrawLineItem(EditorButtonLine button) {
        this.button = button;
    }

    @Override
    public void handleMousePressed(MouseEvent e, UMLEditor editor) {
        reset();
        ItemBase selected = ((ItemShapeBase) e.getSource()).getRoot();
        if (selected.isGroup())
            return;
        
        src = selected;
        dragStartPort = selected.getPort(e.getX(), e.getY());
        preview = new EditorLine(button.getAddLineShape(), dragStartPort);
    }

    private void reset(){
        src = null;
        dst = null;
        dragStartPort = null;
        preview = null;
    }

    @Override
    public void handleMouseEntered(MouseEvent e, UMLEditor editor) {
        ItemShapeBase selected = (ItemShapeBase) e.getSource();
        if (src == null || selected.getRoot().isGroup() || src == selected.getRoot())
            return;
        dst = selected;
    }

    public void handleMouseExit(MouseEvent e, UMLEditor editor) {
        if(dst != null)
            dst.getRoot().removeCorner(editor);
        dst = null;
    }

    @Override
    public void handleMouseDragging(MouseEvent e, UMLEditor editor) {
        ItemShapeBase b = (ItemShapeBase) e.getSource();
        dragX = e.getX() + b.getX();
        dragY = e.getY() + b.getY();
        editor.repaint();

        if (dst != null) {
            if (!dst.getRoot().isGroup())
                dst.getRoot().showCorner(editor, dragX - dst.getX(),
                        dragY - dst.getY());
        }
    }

    @Override
    public void handleMouseReleased(MouseEvent e, UMLEditor editor) {
        if(dst != null && dst.getRoot() != src) {
            dst.getRoot().removeCorner(editor);
            ItemPort dstPort = dst.getRoot().getPort(dragX - dst.getX(),
                        dragY - dst.getY());
            
            dst.getRoot().addLine(
                src.addLine(
                    new EditorLine(button.getAddLineShape(), dragStartPort, dstPort)
                ));

        }
        reset();
        editor.canvasRepaint();
    }

    @Override
    public void drawOnCanvas(Graphics g) {
        if (preview != null)
            preview.draw((Graphics2D) g, dragX, dragY);
    }
}
