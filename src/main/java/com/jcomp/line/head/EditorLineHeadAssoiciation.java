package com.jcomp.line.head;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class EditorLineHeadAssoiciation extends Polyline {
    public EditorLineHeadAssoiciation(final double LINE_HEAD_WIDTH) {
        final double LINE_HEAD_HALFWIDTH = LINE_HEAD_WIDTH / 2;
        Double d[] = new Double[] { 0.0, LINE_HEAD_WIDTH, LINE_HEAD_HALFWIDTH, 0.0, LINE_HEAD_WIDTH, LINE_HEAD_WIDTH };
        getPoints().addAll(d);
        setFill(Color.TRANSPARENT);
        setStroke(Color.BLACK);
    }
}
