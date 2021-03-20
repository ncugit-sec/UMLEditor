package com.jcomp;

import java.util.ArrayList;
import java.util.Vector;

import com.jcomp.button.EditorButton;
import com.jcomp.button.EditorButtonType;
import com.jcomp.item.ItemBase;
import com.jcomp.item.ItemGroup;
import com.jcomp.mode.EditorMode;
import com.jcomp.mode.EditorModeAddObjectCanvas;
import com.jcomp.mode.EditorModeDrawLineItem;
import com.jcomp.mode.EditorModeSelectCanvas;
import com.jcomp.mode.EditorModeSelectItem;
import com.jcomp.mode.EditorMode.EditorModeTargetType;

import javafx.geometry.BoundingBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        btns.add(new EditorButton(EditorButtonType.SELECT, UMLEditor.this, new EditorModeSelectItem(),
                new EditorModeSelectCanvas()));
        btns.add(new EditorButton(EditorButtonType.ASSOCIATION, UMLEditor.this, new EditorModeDrawLineItem()));
        btns.add(new EditorButton(EditorButtonType.GENERALIZATION, UMLEditor.this, new EditorModeDrawLineItem()));
        btns.add(new EditorButton(EditorButtonType.COMPOSITION, UMLEditor.this, new EditorModeDrawLineItem()));
        btns.add(new EditorButton(EditorButtonType.CLASS, UMLEditor.this, new EditorModeAddObjectCanvas()));
        btns.add(new EditorButton(EditorButtonType.USE_CASE, UMLEditor.this, new EditorModeAddObjectCanvas()));
        btnBox.getChildren().addAll(btns);
        root.setLeft(btnBox);
    }

    /**
     * Initialize Canvas and set To Center
     * 
     * @param root
     */
    private void initCanvas(BorderPane root) {
        canvas = new Pane();
        Rectangle r = new Rectangle(200,200);
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
    public EditorMode getEditorMode(int target) {
        EditorMode mode;
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
    private EditorMode emptyMode = new EditorMode();
    private ArrayList<ItemBase> itemList = new ArrayList<>();
    private ArrayList<ItemBase> selectedList = new ArrayList<>();
    private Pane canvas;
    /* member end */
}
