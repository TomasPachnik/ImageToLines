package sk.tomas.iti.gui;

import sk.tomas.iti.config.Configuration;
import sk.tomas.iti.bo.Line;
import sk.tomas.iti.core.Core;
import sk.tomas.iti.core.IndividualImpl;
import sk.tomas.iti.ga.Individual;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;

import javax.swing.*;
import java.awt.*;

import static sk.tomas.iti.config.Configuration.*;
import static sk.tomas.iti.config.Configuration.OFFSET_X;

@Bean("imagePanel")
public class ImagePanel extends JPanel {

    private IndividualImpl best;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (best != null) {
            drawBoard(g);
            drawLines(g);
        }
        g.drawRect(OFFSET_X, OFFSET_Y, Configuration.WIDTH, Configuration.HEIGHT);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        if (best.getBoard() != null) {
            for (int i = 0; i < best.getBoard().length; i++) {
                for (int j = 0; j < best.getBoard()[j].length; j++) {
                    if (best.getBoard()[i][j].getFill() > 0) {
                        g.drawLine(OFFSET_X + i, OFFSET_Y + j, OFFSET_X + i, OFFSET_Y + j);
                    }
                }
            }
        }
    }

    private void drawLines(Graphics g) {
        g.setColor(Color.BLACK);
        if (best.getLines() != null)
            for (Line line : best.getLines()) {
                g.drawLine((int) line.getFromX(), (int) line.getFromY(), (int) line.getToX(), (int) line.getToY());
            }
    }

    public void setBest(IndividualImpl best) {
        this.best = best;
    }
}
