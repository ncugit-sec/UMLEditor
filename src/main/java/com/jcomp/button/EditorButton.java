package com.jcomp.button;

import java.util.ArrayList;

import com.jcomp.UMLEditor;
import com.jcomp.mode.EditorMode;

import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EditorButton extends Button {

    /**
     * Constructor for Button
     * 
     * @param editor
     * @param iconType
     */
    public EditorButton(int type, UMLEditor editor, EditorMode... modes) {
        super("");
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                editor.setEditorButton(EditorButton.this);
                setStyle("-fx-background-color: gray;");
            }
        });
        setGraphic(getIcon(type));
        for (EditorMode e : modes) {
            if (e != null) {
                while (mode.size() < e.getTargetType() + 1)
                    mode.add(null);
                e.setType(type);
                mode.set(e.getTargetType(), e);
            }
        }
    }

    public void unclick() {
        setStyle("");
    }

    /**
     * Draw icon by type id
     * 
     * @param type type ID
     * @param gc   gc from canvas
     * @param sp   snapshot parameter
     */
    void drawIcon(int type, GraphicsContext gc, SnapshotParameters sp) {
        switch (type) {
        case EditorButtonType.SELECT:
            gc.fillPolygon(new double[] { 8.0, 19.0, 29.0, }, new double[] { 8.0, 28.0, 14.0 }, 3);
            gc.strokeLine(23, 22, 40, 40);
            break;
        case EditorButtonType.ASSOCIATION:
            gc.strokeLine(7, 25, 15, 11);
            gc.strokeLine(7, 25, 15, 39);
            gc.strokeLine(7, 25, 40, 25);
            break;
        case EditorButtonType.GENERALIZATION:
            gc.strokePolygon(new double[] { 7.0, 23.0, 23.0, }, new double[] { 25.0, 11.0, 39.0 }, 3);
            gc.strokeLine(23, 25, 40, 25);
            break;
        case EditorButtonType.COMPOSITION:
            gc.strokePolygon(new double[] { 7.0, 15.0, 23.0, 15.0 }, new double[] { 25.0, 11.0, 25.0, 39.0 }, 4);
            gc.strokeLine(23, 25, 40, 25);
            break;
        case EditorButtonType.CLASS:
            gc.setFill(Color.GRAY);
            gc.fillRect(10.0, 10.0, 30.0, 10.0);
            gc.fillRect(10.0, 20.0, 30.0, 10.0);
            gc.fillRect(10.0, 30.0, 30.0, 10.0);
            gc.strokeRect(10.0, 10.0, 30.0, 10.0);
            gc.strokeRect(10.0, 20.0, 30.0, 10.0);
            gc.strokeRect(10.0, 30.0, 30.0, 10.0);
            break;
        case EditorButtonType.USE_CASE:
            gc.setFill(Color.GRAY);
            gc.fillOval(10, 10, 35, 25);
            gc.strokeOval(10, 10, 35, 25);
            break;
        }
        sp.setFill(Color.TRANSPARENT);
    }

    /**
     * generate imageview for button
     * 
     * @param id
     * @return ImageView
     */
    private ImageView getIcon(int iconType) {
        Canvas canvas = new Canvas(ICON_WIDTH, ICON_WIDTH);
        WritableImage im = new WritableImage(ICON_WIDTH, ICON_WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, ICON_WIDTH, ICON_WIDTH);
        gc.setLineWidth(5);
        SnapshotParameters sp = new SnapshotParameters();

        drawIcon(iconType, gc, sp);
        canvas.snapshot(sp, im);
        return new ImageView(im);
    }

    /**
     * Retreive mode holder of the button
     * 
     * @return EditorMode
     */
    public EditorMode getMode(int type) {
        if (type >= mode.size())
            return null;
        return mode.get(type);
    }

    protected ArrayList<EditorMode> mode = new ArrayList<>();
    final static protected int ICON_WIDTH = 50;
}
