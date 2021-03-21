package com.jcomp.button;

import java.util.ArrayList;

import com.jcomp.UMLEditor;
import com.jcomp.mode.EditorModeBase;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EditorButton extends Button {

    /**
     * Constructor for Button
     * 
     * @param editor
     * @param iconType
     */
    public EditorButton(ImageView icon, UMLEditor editor, EditorModeBase... modes) {
        super("");
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                editor.setEditorButton(EditorButton.this);
                setStyle("-fx-background-color: gray;");
            }
        });
        setGraphic(icon);
        for (EditorModeBase e : modes) {
            if (e != null) {
                while (mode.size() < e.getTargetType() + 1)
                    mode.add(null);
                mode.set(e.getTargetType(), e);
            }
        }
    }

    public void unclick() {
        setStyle("");
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
