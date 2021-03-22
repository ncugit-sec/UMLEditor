package com.jcomp.button;

import java.util.ArrayList;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;


import com.jcomp.UMLEditor;
import com.jcomp.mode.EditorModeBase;

public class EditorButton extends JButton {

    /**
     *
     */
    private static final long serialVersionUID = 4712240117996111750L;

    /**
     * Constructor for Button
     * 
     * @param editor
     * @param iconType
     */
    public EditorButton(ImageIcon icon, UMLEditor editor, EditorModeBase... modes) {
        super("", icon);

        addActionListener((e) -> {
            editor.setEditorButton(EditorButton.this);
            setBackground(Color.GRAY);
        });
        for (EditorModeBase e : modes) {
            if (e != null) {
                while (mode.size() < e.getTargetType() + 1)
                    mode.add(null);
                mode.set(e.getTargetType(), e);
            }
        }
    }

    public void unclick() {
        setBackground(null);
    }

    /**
     * Retreive mode holder of the button
     * 
     * @return EditorMode
     */
    public EditorModeBase getMode(int type) {
        if (type >= mode.size())
            return null;
        return mode.get(type);
    }

    protected ArrayList<EditorModeBase> mode = new ArrayList<>();
    final static protected int ICON_WIDTH = 50;
}
