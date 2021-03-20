package com.jcomp.item;

import java.util.ArrayList;

import com.jcomp.UMLEditor;
import com.jcomp.line.EditorLine;

import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Pair;

public abstract class ItemBase {
    protected static int CORNER_SIZE = 10;

    private ItemBase parent;
    protected Pane item;
    protected Text name = new Text();
    protected ArrayList<Shape> corners = new ArrayList<>();
    private ArrayList<EditorLine> connectedItems = new ArrayList<>();
    private double initX, initY, initWidth;

    private double startX, startY;

    protected ItemBase() {
    }

    public ItemBase(Pane item, UMLEditor editor, double x, double y) {
        this.item = item;
        this.parent = this;
        item.setOnMousePressed((e) -> editor.getEditorMode().handleItemMousePressed(e, parent, editor));
        item.setOnMouseDragged((e) -> editor.getEditorMode().handleItemMouseDrag(e, parent, editor));
        item.setOnDragDetected((e) -> editor.getEditorMode().handleItemDragStart(e, parent, editor));
        item.setOnDragOver((e) -> editor.getEditorMode().handleItemDragOver(e, parent, editor));
        item.setOnDragDropped((e) -> editor.getEditorMode().handleItemDragEnd(e, parent, editor));
        item.setLayoutX(x);
        item.setLayoutY(y);
    }

    public ItemBase(Pane item, UMLEditor editor, String s, double x, double y) {
        this(item, editor, x, y);
        setText(s);
    }

    /**
     * set Object parent
     * 
     * @param parent
     */
    public void setParent(ItemBase parent) {
        this.parent = parent;
    }

    /**
     * Get Pane hold by BaseItem
     * 
     * @return Pane
     */
    public Pane getItem() {
        return item;
    }

    protected abstract void _setText();

    /**
     * Implementation of updating width of object
     * 
     * @param s
     */
    public void setText(String s) {
        name.setText(s);
        _setText();
        Platform.runLater(() -> update());
    };

    public void updateText() {
        TextInputDialog dialog = new TextInputDialog(name.getText());
        dialog.setHeaderText("");
        dialog.setTitle("Edit Name");
        dialog.setContentText("Please enter the name:");
        dialog.showAndWait().ifPresent(name -> {
            setText(name);
        });
    }

    // port calculation

    /**
     * Calculate Port number by clicked position
     * 
     * @param x
     * @param y
     * @return int
     */
    public int getDragItemDirection(double x, double y) {
        double a = item.getHeight() / item.getWidth();
        boolean down = (y > (a * x));
        boolean left = (y < (-a * x + item.getHeight()));
        if (down && left)
            return 0; // left
        else if (down)
            return 1; // down
        else if (left)
            return 3; // up
        else
            return 2; // right
    }

    /**
     * get port position by oprt number
     * 
     * @param port
     * @return Pair<Double, Double>
     */
    public Pair<Double, Double> getPointbyPort(int port) {
        switch (port) {
        case 0:
            return new Pair<Double, Double>(item.getLayoutX(), item.getLayoutY() + item.getHeight() / 2);
        case 1:
            return new Pair<Double, Double>(item.getLayoutX() + item.getWidth() / 2,
                    item.getLayoutY() + item.getHeight());
        case 2:
            return new Pair<Double, Double>(item.getLayoutX() + item.getWidth(),
                    item.getLayoutY() + item.getHeight() / 2);
        case 3:
            return new Pair<Double, Double>(item.getLayoutX() + item.getWidth() / 2, item.getLayoutY());
        }
        return null;
    }

    // handle corner visibility

    /**
     * Check if group is select with first item
     * 
     * @return boolean
     */
    public boolean isSelected() {
        return !corners.isEmpty();
    }

    /**
     * Check if item is contained in the bounded selection
     * @param box bounding box
     * @return boolean
     */
    public boolean boundSelectContain(BoundingBox box) {
        return box.contains(new BoundingBox(item.getLayoutX(), item.getLayoutY(), item.getWidth(), item.getHeight()));
    }

    /**
     * get rectangle with default width and height
     * @param x
     * @param y
     * @return Rectangle
     */
    private Rectangle getRectangleWithSize(double x, double y) {
        return new Rectangle(x, y, CORNER_SIZE, CORNER_SIZE);
    }

    /**
     * setUp corner layout
     * 
     * @param id
     * @param x
     * @param y
     */
    private void setCorner(int id, double x, double y) {
        corners.get(id).setLayoutX(x);
        corners.get(id).setLayoutY(y);
    }

    private void updateCorner() {
        double layoutX = item.getLayoutX() - initX, layoutY = item.getLayoutY() - initY;
        double w = (item.getWidth() - initWidth) / 2;
        setCorner(0, layoutX + w, layoutY);
        setCorner(1, layoutX + w, layoutY);
        setCorner(2, layoutX, layoutY);
        setCorner(3, layoutX + w * 2, layoutY);
    }

    /**
     * add Corner to canvas
     * 
     * @param editor
     */
    public void addCorner(UMLEditor editor) {
        if (corners.isEmpty()) {
            initX = item.getLayoutX();
            initY = item.getLayoutY();
            initWidth = item.getWidth();
            double width = item.getWidth(), height = item.getHeight();
            corners.add(getRectangleWithSize(initX + (width - CORNER_SIZE) / 2, initY - CORNER_SIZE));
            corners.add(getRectangleWithSize(initX + (width - CORNER_SIZE) / 2, initY + height));
            corners.add(getRectangleWithSize(initX - CORNER_SIZE, initY + (height - CORNER_SIZE) / 2));
            corners.add(getRectangleWithSize(initX + width, initY + (height - CORNER_SIZE) / 2));
            editor.addItemToCanvas(corners);
        }
    }

    /**
     * remove Corner from canvas
     * 
     * @param editor
     */
    public void removeCorner(UMLEditor editor) {
        if (!corners.isEmpty()) {
            editor.removeItemFromCanvas(corners);
        }
        corners.clear();
    }

    // handle neighbor

    /**
     * add Drawn line to Canvas
     * @param line
     * @return EditorLine
     */

    public EditorLine addLine(EditorLine line) {
        connectedItems.add(line);
        return line;
    }

    public boolean isGroup() {
        return false;
    }

    /**
     * Un do group For {@link ItemGroup}
     * @param editor
     */
    public void unGroup(UMLEditor editor) {
    }

    // handle drawing udpate
    public void setDragStart() {
        startX = item.getLayoutX();
        startY = item.getLayoutY();
    }

    public void update() {
        updateCorner();
        for (EditorLine line : connectedItems) {
            line.updatePos();
        }
    }

    /**
     * Update by setting new position
     * @param x
     * @param y
     */
    public void updatePos(double x, double y) {
        item.setLayoutX(startX + x);
        item.setLayoutY(startY + y);
        Platform.runLater(() -> update());
    }

}
