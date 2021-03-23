package com.jcomp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jcomp.button.EditorButton;
import com.jcomp.button.EditorButtonType;
import com.jcomp.item.ItemBase;
import com.jcomp.item.ItemGroup;
import com.jcomp.item.shape.ItemShapeClass;
import com.jcomp.item.shape.ItemShapeUseCase;
import com.jcomp.line.head.EditorLineHeadAssoiciation;
import com.jcomp.line.head.EditorLineHeadBase;
import com.jcomp.line.head.EditorLineHeadComposition;
import com.jcomp.line.head.EditorLineHeadGeneralization;
import com.jcomp.mode.EditorModeAddObjectCanvas;
import com.jcomp.mode.EditorModeBase;
import com.jcomp.mode.EditorModeBase.EditorModeTargetType;
import com.jcomp.mode.EditorModeDrawLineItem;
import com.jcomp.mode.EditorModeSelectCanvas;
import com.jcomp.mode.EditorModeSelectItem;

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
        JMenuBar menuBar = new JMenuBar();
        // outer
        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");

        // Edit inner
        JMenuItem groupMenu = new JMenuItem("group");
        JMenuItem ungroupMenu = new JMenuItem("ungroup");
        JMenuItem ediNameMenu = new JMenuItem("change object name");

        groupMenu.addActionListener((e) -> {
            this.groupMenu();
        });
        ungroupMenu.addActionListener((e) -> {
            this.ungroupMenu();
        });
        ediNameMenu.addActionListener((e) -> {
            this.editNameMenu();
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
        btnBox.add(new EditorButton(getEditorButtonIcon(EditorButtonType.SELECT), UMLEditor.this,
                new EditorModeSelectItem(), new EditorModeSelectCanvas()));
        // draw line
        int halfWidth = LINE_HEAD_WIDTH / 2;
        btnBox.add(new EditorButton(getEditorButtonIcon(EditorButtonType.ASSOCIATION), UMLEditor.this,
                new EditorModeDrawLineItem(halfWidth, EditorLineHeadAssoiciation.class)));
        btnBox.add(new EditorButton(getEditorButtonIcon(EditorButtonType.GENERALIZATION), UMLEditor.this,
                new EditorModeDrawLineItem(halfWidth, EditorLineHeadGeneralization.class)));
        btnBox.add(new EditorButton(getEditorButtonIcon(EditorButtonType.COMPOSITION), UMLEditor.this,
                new EditorModeDrawLineItem(halfWidth, EditorLineHeadComposition.class)));
        // add
        btnBox.add(new EditorButton(getEditorButtonIcon(EditorButtonType.CLASS), UMLEditor.this,
                new EditorModeAddObjectCanvas(ItemShapeClass.class)));
        btnBox.add(new EditorButton(getEditorButtonIcon(EditorButtonType.USE_CASE), UMLEditor.this,
                new EditorModeAddObjectCanvas(ItemShapeUseCase.class)));
        add(btnBox);
    }

    /**
     * Get editor button icon by type id
     * 
     * @param iconType type ID
     */
    private ImageIcon getEditorButtonIcon(int iconType) {
        ImageIcon icon = null;
        int ICON_WIDTH = 50;
        JPanel canvas = drawButtonIcon(iconType, ICON_WIDTH);
        canvas.setOpaque(false);
        canvas.setSize(ICON_WIDTH, ICON_WIDTH);
        BufferedImage bi = new BufferedImage(ICON_WIDTH, ICON_WIDTH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        canvas.paint(g2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", baos);
            icon = new ImageIcon(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;

    }

    private JPanel drawButtonIcon(int type, int ICON_WIDTH) {
        switch (type) {
        case EditorButtonType.ASSOCIATION:
        case EditorButtonType.GENERALIZATION:
        case EditorButtonType.COMPOSITION:
            return drawLineButtonIcon(type);
        case EditorButtonType.USE_CASE:
        case EditorButtonType.CLASS:
            return drawObjectButtonIcon(type, ICON_WIDTH);
        case EditorButtonType.SELECT:
            return new JPanel() {
                private static final long serialVersionUID = 1L;

                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.fillPolygon(new Polygon(new int[] { 8, 19, 29, }, new int[] { 8, 28, 14 }, 3));
                    g.drawLine(23, 22, 40, 40);
                }
            };
        }

        return null;
    }

    /**
     * Draw add Line icon by button type id
     * 
     * @param type type ID
     * @param gc   gc from canvas
     * @param sp   snapshot parameter
     * @see UMLEditor#getEditorButtonIcon(int)
     */
    private JPanel drawLineButtonIcon(int type) {
        final EditorLineHeadBase head;
        if (type == EditorButtonType.ASSOCIATION)
            head = new EditorLineHeadAssoiciation();
        else if (type == EditorButtonType.GENERALIZATION)
            head = new EditorLineHeadGeneralization();
        else if (type == EditorButtonType.COMPOSITION)
            head = new EditorLineHeadComposition();
        else
            head = null;
        return new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawLine(10, 25, 40, 25);
                head.drawHead((Graphics2D) g, 40, 25, 10, 25);
            }
        };
    }

    /**
     * Draw add object by button type id
     * 
     * @param type type ID
     * @param gc   gc from canvas
     * @param sp   snapshot parameter
     * @see UMLEditor#getEditorButtonIcon(int)
     */
    private JPanel drawObjectButtonIcon(int type, int ICON_WIDTH) {
        switch (type) {
        case EditorButtonType.USE_CASE: {
            int widthHeightDiff = 20;
            return new ItemShapeUseCase(this, 0, widthHeightDiff / 2, ICON_WIDTH, ICON_WIDTH - widthHeightDiff);
        }
        case EditorButtonType.CLASS:
            return new ItemShapeClass(this, 0, 0, ICON_WIDTH, ICON_WIDTH);
        }
        return null;
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
                getEditorMode(EditorModeTargetType.Item).drawOnCanvas(g);
                getEditorMode(EditorModeTargetType.CANVAS).drawOnCanvas(g);
                for (ItemBase b : itemList)
                    b.drawOnCanvas(g);
            }
        };
        canvas.setBackground(Color.white);
        // add item
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getEditorMode(EditorModeTargetType.CANVAS).handleMouseClick(e, UMLEditor.this);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                getEditorMode(EditorModeTargetType.CANVAS).handleMouseReleased(e, UMLEditor.this);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getEditorMode(EditorModeTargetType.CANVAS).handleMousePressed(e, UMLEditor.this);
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                getEditorMode(EditorModeTargetType.CANVAS).handleMouseDragging(e, UMLEditor.this);
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
            b.addToCanvas(canvas);
        }
        canvas.repaint();
    }

    /**
     * Add All Item only to Canvas
     * 
     * @param b BaseItem
     */
    public void addItemToCanvaTop(ArrayList<JPanel> b) {
        for (JPanel i : b) {
            canvas.add(i);
            canvas.setComponentZOrder(i, 0);
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
            selectedList.get(0).updateText(this);
            selectedList.get(0).repaint();
            canvas.repaint();
        }
    }

    /* Menu Function end */

    public int getStringWidth(String str, int maxer, int padding) {
        return Math.max(maxer, canvas.getGraphics().getFontMetrics().stringWidth(str) + padding);
    }

    public void canvasRepaint() {
        canvas.repaint();
    }

    public JPanel getCanvas() {
        return canvas;
    }

    /* member start */
    private EditorButton selectedButton;
    private EditorModeBase emptyMode = new EditorModeBase();
    private ArrayList<ItemBase> itemList = new ArrayList<>();
    private ArrayList<ItemBase> selectedList = new ArrayList<>();
    private JPanel canvas;
    final private int LINE_HEAD_WIDTH = 12;
    /* member end */
}
