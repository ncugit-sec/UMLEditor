package com.jcomp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jcomp.button.EditorButton;
import com.jcomp.button.EditorButtonLineAssociation;
import com.jcomp.button.EditorButtonObjectClass;
import com.jcomp.button.EditorButtonLineComposition;
import com.jcomp.button.EditorButtonLineGeneralization;
import com.jcomp.button.EditorButtonActionSelect;
import com.jcomp.button.EditorButtonObjectUseCase;
import com.jcomp.item.ItemBase;
import com.jcomp.menu.EditMenu;
import com.jcomp.mode.EditorModeBase;
/**
 * UMLEditor!
 */
public class UMLEditor extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /* Initialzation start */
    UMLEditor() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setTitle("XYZ UMLEditor");
        init();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    /**
     * Initialze UML scene
     */
    public void init() {
        initMenu();
        initBtn();
        initCanvas();
    }

    /**
     * Initialize menu and set to Top
     * 
     * @param root UML root pane
     */
    private void initMenu() {
        EditMenu editMenu = new EditMenu();
        JMenuBar menuBar = new JMenuBar();
        // outer
        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");

        // Edit inner
        JMenuItem groupMenu = new JMenuItem("group");
        JMenuItem ungroupMenu = new JMenuItem("ungroup");
        JMenuItem ediNameMenu = new JMenuItem("change object name");

        groupMenu.addActionListener((e) -> {
            editMenu.group(selectedList, this);
        });
        ungroupMenu.addActionListener((e) -> {
            if (selectedList.size() == 1 && selectedList.get(0).isGroup()) 
                editMenu.ungroup(selectedList, this);
        });
        ediNameMenu.addActionListener((e) -> {
            editMenu.editName(selectedList, this);
        });

        menuEdit.add(groupMenu);
        menuEdit.add(ungroupMenu);
        menuEdit.add(ediNameMenu);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        setJMenuBar(menuBar);
    }

    /**
     * Initialzie Function Button and set to Left
     * 
     * @param root UML root pane
     */
    private void initBtn() {
        JPanel btnBox = new JPanel();
        btnBox.setLayout(new BoxLayout(btnBox, BoxLayout.Y_AXIS));
        // select
        btnBox.add(new EditorButtonActionSelect(UMLEditor.this));
        // draw line
        btnBox.add(new EditorButtonLineAssociation(UMLEditor.this));
        btnBox.add(new EditorButtonLineGeneralization(UMLEditor.this));
        btnBox.add(new EditorButtonLineComposition(UMLEditor.this));
        // add
        btnBox.add(new EditorButtonObjectClass(UMLEditor.this));
        btnBox.add(new EditorButtonObjectUseCase(UMLEditor.this));
        add(btnBox);
    }

    /**
     * Initialize Canvas and set To Center
     * 
     * @param root
     */
    private void initCanvas() {
        canvas = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintChildren(Graphics g) {
                super.paintChildren(g);
                getObjectEditorMode().drawOnCanvas(g);
                for (ItemBase b : itemList)
                    b.drawOnCanvas(g);
            }
        };
        canvas.setBackground(Color.white);
        // add item
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getCanvasEditorMode().handleMouseClick(e, UMLEditor.this);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                getCanvasEditorMode().handleMouseReleased(e, UMLEditor.this);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getCanvasEditorMode().handleMousePressed(e, UMLEditor.this);
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                getCanvasEditorMode().handleMouseDragging(e, UMLEditor.this);
            }
        });
        canvas.setLayout(null);
        add(canvas);
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
        if (b != null) {
            b.addToCanvas(this);
        }
        canvas.repaint();
    }

    /**
     * Add Item only to Canvas
     * 
     * @param b BaseItem
     */
    public void addItemToCanvas(JPanel b) {
        canvas.add(b);
        canvas.setComponentZOrder(b, 0);
        canvas.repaint();
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
     * Remove Item only from Canvas
     * 
     * @param b BaseItem
     */
    public void removeItemFromCanvas(ArrayList<JPanel> b) {
        for (JPanel p : b)
            canvas.remove(p);
        canvas.repaint();
    }

    /**
     * Remove Item only from Canvas
     * 
     * @param b BaseItem
     */
    public void removeItemFromCanvas(JPanel b) {
        canvas.remove(b);
        canvas.repaint();
    }

    /**
     * Remove Item only from List
     * 
     * @param b BaseItem
     */
    public void removeItemFromList(ItemBase b) {
        itemList.remove(b);
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
    public EditorModeBase getCanvasEditorMode() {
        if (selectedButton == null || selectedButton.getCanvasMode() == null)
            return new EditorModeBase();
        return selectedButton.getCanvasMode();
    }

    public EditorModeBase getObjectEditorMode() {
        if (selectedButton == null || selectedButton.getObjectMode() == null)
            return new EditorModeBase();
        return selectedButton.getObjectMode();
    }


    /* Button function end */
    /* Item Selection start */

    /**
     * Select items by bound
     * 
     * @param box bounding of target area
     */
    public void boundSelect(Rectangle box) {
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
        b.setSelect(true);
        selectedList.add(b);
        b.showCorner(this);
    }

    /**
     * Remove item from selection
     * 
     * @param b BaseItem
     */
    public void removeSelect(ItemBase b) {
        selectedList.remove(b);
        b.setSelect(false);
        b.removeCorner(this);
    }

    /**
     * Remove all selected items
     */
    public void clearSelect() {
        for (ItemBase b : selectedList) {
            b.setSelect(false);
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
    public int getStringWidth(String str, int maxer, int padding) {
        return Math.max(maxer, canvas.getGraphics().getFontMetrics().stringWidth(str) + padding);
    }
    public void canvasRepaint() {
        canvas.repaint();
    }

    /* member start */
    private EditorButton selectedButton = null;
    private ArrayList<ItemBase> itemList = new ArrayList<>();
    private ArrayList<ItemBase> selectedList = new ArrayList<>();
    private JPanel canvas;
    /* member end */
}
