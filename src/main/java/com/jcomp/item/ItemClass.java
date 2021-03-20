package com.jcomp.item;

import com.jcomp.Tools;
import com.jcomp.UMLEditor;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ItemClass extends ItemBase {
    static final double MIN_WIDTH = 40;
    static final double HEIGHT = 20;
    static final double PADDING = 10;

    
    StackPane namePane = new StackPane();
    Rectangle nameBox = new Rectangle();

    Rectangle sec = new Rectangle();
    Rectangle trd = new Rectangle();

    public ItemClass(UMLEditor editor, double x, double y) {
        super(new VBox(), editor, x, y);
        nameBox.setFill(Color.GRAY);
        nameBox.setStroke(Color.BLACK);
        nameBox.setStrokeWidth(5);

        sec.setFill(Color.GRAY);
        sec.setStroke(Color.BLACK);
        sec.setStrokeWidth(5);

        trd.setFill(Color.GRAY);
        trd.setStroke(Color.BLACK);
        trd.setStrokeWidth(5);

        namePane.getChildren().addAll(nameBox, name);
        
        setAllWidth(MIN_WIDTH);
        setAllHeight(HEIGHT);
        item.getChildren().addAll(namePane, sec, trd);
    }

    private void setAllWidth(double r) {
        nameBox.setWidth(r);
        sec.setWidth(r);
        trd.setWidth(r);
    }

    private void setAllHeight(double r) {
        nameBox.setHeight(r);
        sec.setHeight(r);
        trd.setHeight(r);
    }

    @Override
    protected void _setText() {
        double r = Tools.getTextWidth(name) + PADDING;
        setAllWidth(Math.max(r, MIN_WIDTH));
    }    
    
}
