package sk.tomas.iti.gui;

import sk.tomas.iti.Node;
import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;

import javax.swing.*;
import java.awt.*;

import static sk.tomas.iti.Configuration.height;
import static sk.tomas.iti.Configuration.width;

@Bean("imagePanel")
public class ImagePanel extends JPanel {

    private int offsetX = 140;
    private int offsetY = 60;

    @Inject
    private Core core;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        g.drawRect(offsetX, offsetY, width, height);

        for (int j = 0; j < core.getBoard()[j].length; j++) {
            for (int i = 0; i < core.getBoard().length; i++) {
                if (core.getBoard()[i][j].isFill()) {
                    g.drawLine(offsetX + i, offsetY + j, offsetX + i, offsetY + j);
                }
            }
        }
    }


}
