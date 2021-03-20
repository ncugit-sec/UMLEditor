package com.jcomp;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

public class Tools {
    
    /** 
     * Calculate text width when showing on screen
     * @param text Text Object with text string
     * @return double
     */
    public static double getTextWidth(Text text) {
        Text t = new Text(text.getText());
        new Scene(new Group(t));
        t.applyCss();
        return t.getLayoutBounds().getWidth();
    }
}
