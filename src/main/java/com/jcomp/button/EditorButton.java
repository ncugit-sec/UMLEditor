package com.jcomp.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

import com.jcomp.UMLEditor;
import com.jcomp.line.head.EditorLineHeadBase;
import com.jcomp.mode.EditorModeBase;

public abstract class EditorButton extends JButton implements ActionListener {
    UMLEditor umlEditor;
    EditorModeBase canvasMode, objectMode;
    protected EditorButton(){}
    /**
     * Constructor for Button
     * 
     * @param editor
     * @param iconType
     */
    public EditorButton(UMLEditor editor) {
        super("");
        this.umlEditor = editor;
        setIcon(getEditorButtonIcon());
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        umlEditor.setEditorButton(EditorButton.this);
        setBackground(Color.GRAY);
    }

    public void unclick() {
        setBackground(null);
    }

    private ImageIcon getEditorButtonIcon() {
        ImageIcon icon = null;
        JPanel canvas = drawButtonIcon();
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

    protected abstract JPanel drawButtonIcon();

    protected JPanel drawLineButtonIcon(EditorLineHeadBase head) {
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

    public EditorModeBase getCanvasMode() {
        return canvasMode;
    }
    public EditorModeBase getObjectMode() {
        return objectMode;
    }

    final static protected int ICON_WIDTH = 50;
}
