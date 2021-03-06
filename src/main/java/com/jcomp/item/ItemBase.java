package com.jcomp.item;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

import com.jcomp.UMLEditor;
import com.jcomp.item.shape.ItemShapeBase;
import com.jcomp.line.EditorLine;
import com.jcomp.mode.EditorModeBase.EditorModeTargetType;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ItemBase {
    protected static int CORNER_SIZE = 10;

    private ItemBase parent;
    private ItemShapeBase item;
    // private boolean drawingLine = false;
    // private int drawingPort;
    protected ArrayList<JPanel> corners = new ArrayList<>();
    protected ArrayList<EditorLine> connectedItems = new ArrayList<>();

    private int startX, startY;

    protected ItemBase() {
    }

    public ItemBase(UMLEditor editor, ItemShapeBase item) {
        // select
        parent = this;
        this.item = item;
        item.putClientProperty("parent", this);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editor.getEditorMode(EditorModeTargetType.Item).handleMousePressed(e, editor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                editor.getEditorMode(EditorModeTargetType.Item).handleMouseEntered(e, editor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editor.getEditorMode(EditorModeTargetType.Item).handleMouseExit(e, editor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                editor.getEditorMode(EditorModeTargetType.Item).handleMouseReleased(e, editor);
            }
        });
        item.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.getEditorMode(EditorModeTargetType.Item).handleMouseDragging(e, editor);
            }
        });
    }

    public void addToCanvas(JPanel canvas) {
        canvas.add(item);
    }

    public void repaint() {
        item.repaint();
        updateCorner();
    }

    public void drawOnCanvas(Graphics g) {
        for (EditorLine l : connectedItems) {
            l.draw((Graphics2D) g);
        }
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
     * get Object parent
     * 
     * @return ItemBase
     */
    public ItemBase getItemParent() {
        return parent;
    }

    public void updateText(UMLEditor editor) {
        if (isGroup())
            return;
        String s = (String) JOptionPane.showInputDialog(editor, "Please enter the name:", "Edit Name",
                JOptionPane.PLAIN_MESSAGE, null, null, item.getName());
        if ((s != null))
            item.updateText(editor, s);
    }

    // port calculation

    /**
     * Calculate Port number by clicked position
     *
     * @param x
     * @param y
     * @return int
     */
    public int getDragItemDirection(int x, int y) {
        double a = (double) item.getHeight() / item.getWidth();
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
     * get port X position by oprt number
     *
     * @param port
     * @return int
     */
    public double getPortX(int port) {
        switch (port) {
        case 0:
            return item.getX();
        case 1:
            return (int) (item.getX() + item.getWidth() / 2.0);
        case 2:
            return item.getX() + item.getWidth();
        case 3:
            return (int) (item.getX() + item.getWidth() / 2.0);
        }
        return 0;
    }

    /**
     * get port Y position by oprt number
     *
     * @param port
     * @return int
     */
    public int getPortY(int port) {
        switch (port) {
        case 0:
            return (int) (item.getY() + item.getHeight() / 2.0);
        case 1:
            return item.getY() + item.getHeight();
        case 2:
            return (int) (item.getY() + item.getHeight() / 2);
        case 3:
            return item.getY();
        }
        return 0;
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
     *
     * @param box bounding box
     * @return boolean
     */
    public boolean boundSelectContain(Rectangle box) {
        return box.contains(item.getX(), item.getY(), item.getWidth(), item.getHeight());
    }

    /**
     * setUp corner layout
     *
     * @param id
     * @param x
     * @param y
     */
    private void setCorner(int id, int x, int y) {
        if (id < corners.size()) {
            corners.get(id).setBounds(x, y, CORNER_SIZE, CORNER_SIZE);
        }
    }

    private void updateCorner() {
        if (corners.size() < 4)
            return;
        int layoutX = item.getX(), layoutY = item.getY();
        setCorner(0, layoutX + (item.getWidth() - CORNER_SIZE) / 2, layoutY - CORNER_SIZE);
        setCorner(1, layoutX + (item.getWidth() - CORNER_SIZE) / 2, layoutY + item.getHeight());
        setCorner(2, layoutX - CORNER_SIZE, layoutY + (item.getHeight() - CORNER_SIZE) / 2);
        setCorner(3, layoutX + item.getWidth(), layoutY + (item.getHeight() - CORNER_SIZE) / 2);
    }

    /**
     * add Corner to canvas
     * 
     * @param editor
     */
    public void addCorner(UMLEditor editor) {
        if (corners.isEmpty()) {
            removeCorner(editor);
            for (int x = 0; x < 4; x++) {
                JPanel p = new JPanel();
                p.setBackground(Color.black);
                corners.add(p);
            }
            editor.addItemToCanvaTop(corners);
            updateCorner();
        }
    }

    public void addCorner(UMLEditor editor, int x, int y) {
        removeCorner(editor);
        int port = getDragItemDirection(x, y);
        JPanel p = new JPanel();
        p.setBackground(Color.black);
        int layoutX = item.getX(), layoutY = item.getY();
        if (port == 2)
            p.setBounds(layoutX + item.getWidth(), layoutY + (item.getHeight() - CORNER_SIZE) / 2, CORNER_SIZE,
                    CORNER_SIZE);
        else if (port == 1)
            p.setBounds(layoutX + (item.getWidth() - CORNER_SIZE) / 2, layoutY + item.getHeight(), CORNER_SIZE,
                    CORNER_SIZE);
        else if (port == 3)
            p.setBounds(layoutX + (item.getWidth() - CORNER_SIZE) / 2, layoutY - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);
        else
            p.setBounds(layoutX - CORNER_SIZE, layoutY + (item.getHeight() - CORNER_SIZE) / 2, CORNER_SIZE,
                    CORNER_SIZE);
        corners.add(p);
        editor.addItemToCanvaTop(corners);
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
     *
     * @param line
     * @return EditorLine
     */

    public EditorLine addLine(EditorLine line) {
        connectedItems.add(line);
        return line;
    }

    public boolean isGroup() {
        if (parent == this)
            return false;
        return parent.isGroup();
    }

    /**
     * Un do group For {@link ItemGroup}
     *
     * @param editor
     */
    public void unGroup(UMLEditor editor) {
    }

    // handle drawing udpate
    public void setDragStart() {
        startX = item.getX();
        startY = item.getY();
    }

    /**
     * Update by setting new position
     * 
     * @param x
     * @param y
     */
    public void updatePos(int x, int y) {
        item.setBounds(startX + x, startY + y, item.getWidth(), item.getHeight());
        item.repaint();
        updateCorner();
    }
}
