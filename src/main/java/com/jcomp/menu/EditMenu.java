package com.jcomp.menu;

import java.util.ArrayList;

import com.jcomp.UMLEditor;
import com.jcomp.item.ItemBase;
import com.jcomp.item.ItemGroup;

public class EditMenu {
     /**
     * Implementatiob of group function
     */
    public void group(ArrayList<ItemBase> selectedList, UMLEditor umlEditor) {
        if (selectedList.size() < 2)
            return;
        ArrayList<ItemBase> item = new ArrayList<>();
        for (ItemBase b : selectedList) {
            item.add(b);
            umlEditor.removeItemFromList(b);
        }
        ItemBase b = new ItemGroup(umlEditor, item);
        // handle item list
        umlEditor.addItemToList(b);
        // handle selected list
        umlEditor.clearSelect(b);
    }

    /**
     * Implementatiob of ungroup function
     */
    public void ungroup(ArrayList<ItemBase> selectedList, UMLEditor umlEditor) {
        if (selectedList.size() == 1 && selectedList.get(0).isGroup()) {
            ItemGroup b = (ItemGroup) selectedList.get(0);
            umlEditor.removeItemFromList(b);
            umlEditor.clearSelect();
            b.unGroup(umlEditor);
        }
    }

    /**
     * Implementatiob of edit name function
     */
    public void editName(ArrayList<ItemBase> selectedList, UMLEditor umlEditor) {
        if (selectedList.size() == 1) {
            selectedList.get(0).updateText(umlEditor);
            selectedList.get(0).repaint();
            umlEditor.canvasRepaint();
        }
    }
}
