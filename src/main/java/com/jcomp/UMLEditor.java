package com.jcomp;

import java.util.ArrayList;
import java.util.Vector;

import com.jcomp.button.EditorButton;
import com.jcomp.button.EditorButtonType;
import com.jcomp.item.ItemBase;
import com.jcomp.item.ItemClass;
import com.jcomp.item.ItemGroup;
import com.jcomp.item.ItemUseCase;
import com.jcomp.line.head.EditorLineHeadAssoiciation;
import com.jcomp.line.head.EditorLineHeadComposition;
import com.jcomp.line.head.EditorLineHeadGeneralization;
import com.jcomp.mode.EditorModeBase;
import com.jcomp.mode.EditorModeAddObjectCanvas;
import com.jcomp.mode.EditorModeDrawLineItem;
import com.jcomp.mode.EditorModeSelectCanvas;
import com.jcomp.mode.EditorModeSelectItem;
import com.jcomp.mode.EditorModeBase.EditorModeTargetType;

import javafx.geometry.BoundingBox;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * UMLEditor!
 */
public class UMLEditor {

    /* Initialzation start */

    /**
     * Initialze UML scene
     * 
     * @return Scene
     */
    public Scene init() {
        BorderPane root = new BorderPane();
        initMenu(root);
        initBtn(root);
        initCanvas(root);
        root.setCenter(canvas);
        return new Scene(root, 400, 400);
    }

    /**
     * Initialize menu and set to Top
     * 
     * @param root UML root pane
     */
    private void initMenu(BorderPane root) {
        MenuBar menuBar = new MenuBar();
        // outer
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");

        // Edit inner
        MenuItem groupMenu = new MenuItem("group");
        MenuItem ungroupMenu = new MenuItem("ungroup");
        MenuItem ediNameMenu = new MenuItem("change object name");

        groupMenu.setOnAction((e) -> {
            this.groupMenu();
        });
        ungroupMenu.setOnAction((e) -> {
            this.ungroupMenu();
        });
        ediNameMenu.setOnAction((e) -> {
            this.editNameMenu();
        });

        menuEdit.getItems().addAll(groupMenu, ungroupMenu, ediNameMenu);
        menuBar.getMenus().addAll(menuFile, menuEdit);
        root.setTop(menuBar);
    }

    /**
     * Initialzie Function Button and set to Left
     * 
     * @param root UML root pane
     */
    private void initBtn(BorderPane root) {
        VBox btnBox = new VBox();
        Vector<Button> btns = new Vector<Button>();
        // select
        btns.add(new EditorButton(getEditorButtonIcon(EditorButtonType.SELECT), UMLEditor.this,
                new EditorModeSelectItem(),
                new EditorModeSelectCanvas()));
        // draw line
        double halfWidth = LINE_HEAD_WIDTH / 2;
        btns.add(new EditorButton(getEditorButtonIcon(EditorButtonType.ASSOCIATION), UMLEditor.this,
                new EditorModeDrawLineItem(halfWidth, EditorLineHeadAssoiciation.class)));
        btns.add(new EditorButton(getEditorButtonIcon(EditorButtonType.GENERALIZATION), UMLEditor.this,
                new EditorModeDrawLineItem(halfWidth, EditorLineHeadGeneralization.class)));
        btns.add(new EditorButton(getEditorButtonIcon(EditorButtonType.COMPOSITION), UMLEditor.this,
                new EditorModeDrawLineItem(halfWidth, EditorLineHeadComposition.class)));
        // add
        btns.add(new EditorButton(getEditorButtonIcon(EditorButtonType.CLASS), UMLEditor.this,
                new EditorModeAddObjectCanvas(ItemClass.class)));
        btns.add(new EditorButton(getEditorButtonIcon(EditorButtonType.USE_CASE), UMLEditor.this,
                new EditorModeAddObjectCanvas(ItemUseCase.class)));
        btnBox.getChildren().addAll(btns);
        root.setLeft(btnBox);
    }

    /**
     * Get editor button icon by type id
     * 
     * @param iconType type ID
     */
    private ImageView getEditorButtonIcon(int iconType) {
        int ICON_WIDTH = 50;
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
     * Draw icon by type id
     * 
     * @param type type ID
     * @param gc   gc from canvas
     * @param sp   snapshot parameter
     * @see UMLEditor#getEditorButtonIcon(int)
     */
    private void drawIcon(int type, GraphicsContext gc, SnapshotParameters sp) {
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
     * Initialize Canvas and set To Center
     * 
     * @param root
     */
    private void initCanvas(BorderPane root) {
        canvas = new Pane();
        Rectangle r = new Rectangle(200, 200);
        r.widthProperty().bind(root.widthProperty());
        r.heightProperty().bind(root.heightProperty());
        canvas.setClip(r);
        canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        // add item
        canvas.setOnMouseClicked((e) -> getEditorMode(EditorModeTargetType.CANVAS).handleMouseClick(e, UMLEditor.this));
        canvas.setOnDragDetected((e) -> getEditorMode(EditorModeTargetType.CANVAS).handleDragStart(e, UMLEditor.this));
        canvas.setOnMouseReleased(
                (e) -> getEditorMode(EditorModeTargetType.CANVAS).handleMouseReleased(e, UMLEditor.this));
        // select
        canvas.setOnMousePressed(
                (e) -> getEditorMode(EditorModeTargetType.CANVAS).handleMousePressed(e, UMLEditor.this));
        canvas.setOnMouseDragged(
                (e) -> getEditorMode(EditorModeTargetType.CANVAS).handleMouseDraging(e, UMLEditor.this));
    }

    /* Initialzation end */
    /* Item Management start */

    /**
     * Add Item to Canvas and itemList
     * 
     * @param b BaseItem
     */
    public void addItemToBoth(ItemBase b) {
        itemList.add(b);
        if (b.getItem() != null)
            canvas.getChildren().add(b.getItem());
    }

    /**
     * Add exist Item to itemList
     * 
     * @param b BaseItem
     */
    public void addItemToList(ItemBase b) {
        itemList.add(b);
    }

    /**
     * Add Item only to Canvas
     * 
     * @param c Shape
     */
    public void addItemToCanvas(Shape c) {
        canvas.getChildren().add(c);
    }

    /**
     * Add All Item only to Canvas
     * 
     * @param c ArrayList of Shape
     */
    public void addItemToCanvas(ArrayList<Shape> c) {
        canvas.getChildren().addAll(c);
    }

    /**
     * Remove Item only from List
     * 
     * @param b BaseItem
     */
    public void removeItemFromList(ItemBase b) {
        itemList.remove(b);
    }

    /**
     * Remove Item only from Canvas
     * 
     * @param c Shape
     */
    public void removeItemFromCanvas(Shape c) {
        canvas.getChildren().remove(c);
    }

    /**
     * Remove All Item only from Canvas
     * 
     * @param c ArrayList of Shape
     */
    public void removeItemFromCanvas(ArrayList<Shape> c) {
        canvas.getChildren().removeAll(c);
    }

    /* Item Management end */
    /* Button function start */

    /**
     * set Current Canvas Mode
     * 
     * @param b
     */
    public void setEditorButton(EditorButton selectedButton) {
        if (this.selectedButton != null)
            this.selectedButton.unclick();
        this.selectedButton = selectedButton;
        clearSelect();
    }

    /**
     * get Current Canvas Mode
     * 
     * @return EditorButtonBase
     */
    public EditorModeBase getEditorMode(int target) {
        EditorModeBase mode;
        if (selectedButton == null || (mode = selectedButton.getMode(target)) == null)
            return emptyMode;
        return mode;
    }

    /* Button function end */
    /* Item Retreive start */

    /**
     * get Item in List by index
     * 
     * @param id index in list
     * @return BaseItem
     */
    public ItemBase getItem(int id) {
        return itemList.get(id);
    }

    /**
     * get Item index in List by Item
     * 
     * @param b BaseItem
     * @return int
     */
    public int getItemIndex(ItemBase b) {
        return itemList.indexOf(b);
    }

    /* Item Retreive end */
    /* Item Selection start */

    /**
     * Select items by bound
     * 
     * @param box bounding of target area
     */
    public void boundSelect(BoundingBox box) {
        for (ItemBase b : itemList) {
            if (b.boundSelectContain(box))
                addSelected(b);
            else
                removeSelect(b);
        }
    }

    /**
     * Add selected item to List
     * 
     * @param b BaseItem
     */
    public void addSelected(ItemBase b) {
        if (b.isSelected())
            return;
        selectedList.add(b);
        b.addCorner(this);
    }

    /**
     * Remove item from selection
     * 
     * @param b BaseItem
     */
    public void removeSelect(ItemBase b) {
        selectedList.remove(b);
        b.removeCorner(this);
    }

    /**
     * Remove all selected items
     */
    public void clearSelect() {
        for (ItemBase b : selectedList) {
            b.removeCorner(this);
        }
        selectedList.clear();
    }

    /**
     * Remove all selected item and set to assigned item
     * 
     * @param b BaseItem
     */
    public void clearSelect(ItemBase b) {
        clearSelect();
        addSelected(b);
    }

    /* Item Selection end */
    /* Menu Function start */

    /**
     * Implementatiob of group function
     */
    private void groupMenu() {
        if (selectedList.size() < 2)
            return;
        ArrayList<ItemBase> item = new ArrayList<>();
        for (ItemBase b : selectedList) {
            item.add(b);
            removeItemFromList(b);
        }
        ItemBase b = new ItemGroup(this, item);
        // handle item list
        addItemToList(b);
        // handle selected list
        selectedList.clear();
        selectedList.add(b);
    }

    /**
     * Implementatiob of ungroup function
     */
    private void ungroupMenu() {
        if (selectedList.size() == 1 && selectedList.get(0).isGroup()) {
            ItemGroup b = (ItemGroup) selectedList.get(0);
            removeItemFromList(b);
            clearSelect();
            b.unGroup(this);
        }
    }

    /**
     * Implementatiob of edit name function
     */
    private void editNameMenu() {
        if (selectedList.size() == 1) {
            selectedList.get(0).updateText();
        }
    }

    /* Menu Function end */

    /* member start */
    private EditorButton selectedButton;
    private EditorModeBase emptyMode = new EditorModeBase();
    private ArrayList<ItemBase> itemList = new ArrayList<>();
    private ArrayList<ItemBase> selectedList = new ArrayList<>();
    private Pane canvas;
    final private double LINE_HEAD_WIDTH = 12.0;
    /* member end */
}
