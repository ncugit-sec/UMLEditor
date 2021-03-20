package com.jcomp.mode;

import com.jcomp.UMLEditor;
import com.jcomp.button.EditorButtonType;
import com.jcomp.item.ItemBase;
import com.jcomp.line.EditorLine;

import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class EditorModeDrawLineItem extends EditorMode {

    public EditorModeDrawLineItem() {
        this.targetType = EditorModeTargetType.Item;
    }

    @Override
    public void handleDragStart(MouseEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData()).getParent();
        if (b.isGroup())
            return;
        Dragboard db = b.getItem().startDragAndDrop(TransferMode.ANY);
        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(b.getDragItemDirection(e.getX(), e.getY()) + "," + editor.getItemIndex(b));
        db.setContent(content);
    }

    @Override
    public void handleDragOver(DragEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData()).getParent();
        if (b.isGroup())
            return;
        if (e.getGestureSource() != b.getItem()) {
            e.acceptTransferModes(TransferMode.ANY);
        }
    }

    @Override
    public void handleDragEnd(DragEvent e, UMLEditor editor) {
        ItemBase b = ((ItemBase) ((Node) e.getSource()).getUserData()).getParent();
        if (b.isGroup())
            return;
        String content[] = e.getDragboard().getString().split(",");

        ItemBase src = editor.getItem(Integer.valueOf(content[1]));
        int srcDir = Integer.valueOf(content[0]);
        ItemBase dst = b;
        int dstDir = b.getDragItemDirection(e.getX(), e.getY());

        double width = 12.0;
        double halfWidth = width / 2;
        Double d[] = null;
        Shape shape = null;
        if (type == EditorButtonType.GENERALIZATION) {
            Polygon p = new Polygon();
            d = new Double[] { halfWidth, 0.0, 0.0, width, width, width };
            p.getPoints().addAll(d);
            shape = p;
            shape.setFill(Color.WHITE);
        } else if (type == EditorButtonType.COMPOSITION) {
            Polygon p = new Polygon();
            d = new Double[] { halfWidth, 0.0, 0.0, halfWidth, halfWidth, width, width, halfWidth };
            p.getPoints().addAll(d);
            shape = p;
            shape.setFill(Color.WHITE);
        } else if (type == EditorButtonType.ASSOCIATION) {
            Polyline p = new Polyline();
            d = new Double[] { 0.0, width, halfWidth, 0.0, width, width };
            p.getPoints().addAll(d);
            shape = p;
            shape.setFill(Color.TRANSPARENT);
        }
        shape.setStroke(Color.BLACK);
        dst.addLine(src.addLine(new EditorLine(src, srcDir, dst, dstDir, shape, editor, halfWidth)));
        e.consume();
    }
}
