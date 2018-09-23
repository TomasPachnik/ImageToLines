package sk.tomas.iti.gui;

import sk.tomas.iti.Configuration;
import sk.tomas.iti.Line;
import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;

import javax.swing.*;
import java.awt.*;

import static sk.tomas.iti.Configuration.*;
import static sk.tomas.iti.Configuration.OFFSET_X;

@Bean("imagePanel")
public class ImagePanel extends JPanel {

    @Inject
    private Core core;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawLines(g);
        g.drawRect(OFFSET_X, OFFSET_Y, Configuration.WIDTH, Configuration.HEIGHT);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < core.getBoard().length; i++) {
            for (int j = 0; j < core.getBoard()[j].length; j++) {
                if (core.getBoard()[i][j].isFill()) {
                    g.drawLine(OFFSET_X + i, OFFSET_Y + j, OFFSET_X + i, OFFSET_Y + j);
                }
            }
        }
    }

    private void drawLines(Graphics g) {
        g.setColor(Color.BLACK);
        for (Line line : core.getLines()) {
            g.drawLine(line.getFromX(), line.getFromY(), line.getToX(), line.getToY());
        }
    }

}
