package com.jcomp.item;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.jcomp.UMLEditor;

public class ItemGroup extends ItemBase {
    protected ArrayList<ItemBase> item;

    public ItemGroup(UMLEditor editor, ArrayList<ItemBase> item) {
        this.item = item;
        for (ItemBase p : item) {
            p.setParent(this);
        }
    }

    @Override
    public void setParent(ItemBase parent) {
        for (ItemBase p : item) {
            p.setParent(parent);
        }
    }

    @Override
    public boolean isSelected() {
        return item.get(0).isSelected();
    }

    @Override
    public void repaint() {
        for (ItemBase p : item) {
            p.repaint();
        }
    }

    @Override
    public boolean boundSelectContain(Rectangle box) {
        for (ItemBase b : item) {
            if (!b.boundSelectContain(box))
                return false;
        }
        return true;
    }

    @Override
    public void addCorner(UMLEditor editor) {
        for (ItemBase b : item)
            b.addCorner(editor);
    }

    @Override
    public void removeCorner(UMLEditor editor) {
        for (ItemBase b : item)
            b.removeCorner(editor);
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    @Override
    public void unGroup(UMLEditor editor) {
        for (ItemBase p : item) {
            editor.addItemToList(p);
            editor.addSelected(p);
            p.setParent(p);
        }
    }

    @Override
    public void setDragStart() {
        for (ItemBase b : item) {
            b.setDragStart();
        }
    }

    @Override
    public void updatePos(int x, int y) {
        for (ItemBase b : item) {
            b.updatePos(x, y);
        }
    }

    @Override
    public void drawOnCanvas(Graphics g) {
        for (ItemBase b : item) {
            b.drawOnCanvas(g);
        }
    }

    @Override
    public void updateText(UMLEditor editor) {
        // function prohibit
    }
}