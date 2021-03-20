package com.jcomp.item;

import com.jcomp.Tools;
import com.jcomp.UMLEditor;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class ItemUseCase extends ItemBase {
    static final double MIN_WIDTH = 40;
    static final double HEIGHT = 20;
    static final double PADDING = 5;
    Ellipse circle = new Ellipse();
    public ItemUseCase(UMLEditor editor, double x, double y) {
        super(new StackPane(), editor, x, y);
        circle.setFill(Color.GRAY);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(5);
        circle.setRadiusX(MIN_WIDTH);
        circle.setRadiusY(HEIGHT);
        item.getChildren().addAll(circle, name);
    }

    @Override
    protected void _setText() {
        double r = Tools.getTextWidth(name) / 2 + PADDING;
        circle.setRadiusX(Math.max(r, MIN_WIDTH));
    }
}
