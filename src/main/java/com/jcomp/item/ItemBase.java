package com.jcomp.item;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

import com.jcomp.UMLEditor;
import com.jcomp.item.shape.ItemShapeBase;
import com.jcomp.line.EditorLine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ItemBase {
    protected static int CORNER_SIZE = 10;

    protected ItemBase parent;
    private ItemShapeBase item;
    // private boolean drawingLine = false;
    // private int drawingPort;
    protected ItemPort [] ports = new ItemPort[4];
    protected ArrayList<EditorLine> connectedItems = new ArrayList<>();

    private int startX, startY;

    protected ItemBase() {
    }

    public ItemBase(UMLEditor editor, ItemShapeBase item) {
        // select
        parent = this;
        this.item = item;
        item.setRoot(this);;
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editor.getObjectEditorMode().handleMousePressed(e, editor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                editor.getObjectEditorMode().handleMouseEntered(e, editor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editor.getObjectEditorMode().handleMouseExit(e, editor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                editor.getObjectEditorMode().handleMouseReleased(e, editor);
            }
        });
        item.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.getObjectEditorMode().handleMouseDragging(e, editor);
            }
        });

        for (int x = 0; x < 4; x++) {
            ItemPort p = new ItemPort();
            p.setBackground(Color.black);
            ports[x] = p;
        }
        updateCorner();
    }


    // port
    public ItemPort getPort(int x, int y) {
        return ports[getDragItemDirection(x,y)];
    }

    /**
     * setUp corner layout
     *
     * @param id
     * @param x
     * @param y
     */
    private void setCorner(int id, int x, int y) {
        ports[id].setBounds(x, y, CORNER_SIZE, CORNER_SIZE);
    }
    
    private void updateCorner() {
        int layoutX = item.getX(), layoutY = item.getY();
        setCorner(0, layoutX + (item.getWidth() - CORNER_SIZE) / 2, layoutY - CORNER_SIZE / 2);
        setCorner(1, layoutX + (item.getWidth() - CORNER_SIZE) / 2, layoutY + item.getHeight() - CORNER_SIZE / 2);
        setCorner(2, layoutX - CORNER_SIZE / 2, layoutY + (item.getHeight() - CORNER_SIZE) / 2);
        setCorner(3, layoutX + item.getWidth() - CORNER_SIZE / 2 , layoutY + (item.getHeight() - CORNER_SIZE) / 2);
    }


    /**
     * Calculate Port number by clicked position
     *
     * @param x
     * @param y
     * @return int
     */
    private int getDragItemDirection(int x, int y) {
        double a = (double) item.getHeight() / item.getWidth();
        boolean down = (y > (a * x));
        boolean left = (y < (-a * x + item.getHeight()));
        if (down && left)
            return 2; // left
        else if (down)
            return 1; // down
        else if (left)
            return 0; // up
        else
            return 3; // right
    }
    
    /**
     * remove Corner from canvas
     *
     * @param editor
     */
    public void removeCorner(UMLEditor editor) {
        for (ItemPort p : ports) {
            editor.removeItemFromCanvas(p);
        }
    }

    public void showCorner(UMLEditor editor, int x, int y) {
        removeCorner(editor);
        ItemPort p = ports[getDragItemDirection(x, y)];
        editor.addItemToCanvas(p);
    }

    public void showCorner(UMLEditor editor) {
        removeCorner(editor);
        for (ItemPort p : ports) {
            editor.addItemToCanvas(p);
        }
    }

    
    // group
    
    /**
     * set Object parent
     *
     * @param parent
     */
    public void setParent(ItemBase parent) {
        this.parent = parent;
    }

    public ItemBase getParent() {
        return parent;
    }

    /**
     * Un do group For {@link ItemGroup}
     *
     * @param editor
     */
    public void unGroup(UMLEditor editor) {

    }

    public boolean isGroup() {
        if (parent == this)
            return false;
        return true;
    }

    // selection

    protected boolean isSelect = false;
    /**
     * Check if group is select with first item
     * 
     * @return boolean
     */
    public boolean isSelected() {
        return isSelect;
    }
  
    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
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

    // handle drawing udpate

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

    public void drawOnCanvas(Graphics g) {
        for (EditorLine l : connectedItems) {
            l.draw((Graphics2D) g);
        }
    }
  
    public void addToCanvas(UMLEditor umlEditor) {
        umlEditor.addItemToCanvas(item);
    }
   
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
  
    public void repaint() {
        item.repaint();
        updateCorner();
    }

    
    public void updateText(UMLEditor editor) {
        if (isGroup())
            return;
        String s = (String) JOptionPane.showInputDialog(editor, "Please enter the name:", "Edit Name",
                JOptionPane.PLAIN_MESSAGE, null, null, item.getName());
        if ((s != null))
            item.updateText(editor, s);
    }
  
}
