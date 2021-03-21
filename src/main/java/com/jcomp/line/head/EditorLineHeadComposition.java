package com.jcomp.line.head;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class EditorLineHeadComposition extends Polygon {
    public EditorLineHeadComposition(final double LINE_HEAD_WIDTH) {
        final double LINE_HEAD_HALFWIDTH = LINE_HEAD_WIDTH / 2;
        Double d[] = new Double[] { LINE_HEAD_HALFWIDTH, 0.0, 0.0, LINE_HEAD_HALFWIDTH, LINE_HEAD_HALFWIDTH,
                LINE_HEAD_WIDTH, LINE_HEAD_WIDTH, LINE_HEAD_HALFWIDTH };
        getPoints().addAll(d);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
