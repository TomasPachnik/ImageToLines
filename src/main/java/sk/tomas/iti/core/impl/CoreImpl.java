package sk.tomas.iti.core.impl;

import sk.tomas.iti.ImageLoader;
import sk.tomas.iti.Line;
import sk.tomas.iti.Node;
import sk.tomas.iti.Randomizer;
import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;

import java.awt.image.BufferedImage;

import static sk.tomas.iti.Configuration.*;

@Bean("core")
public class CoreImpl implements Core {

    private Node[][] board;
    private Line[] lines;

    @Inject
    private Randomizer randomizer;
    @Inject
    private ImageLoader imageLoader;

    public CoreImpl() {
        initNodes();
    }

    private void initNodes() {
        board = new Node[WIDTH][HEIGHT];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[j].length; j++) {
                board[i][j] = new Node();
            }
        }
    }

    @Override
    public void initLines() {
        lines = new Line[LINE_COUNT];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = randomizer.generateLines();
        }
    }

    @Override
    public Node[][] getBoard() {
        return board;
    }

    @Override
    public Line[] getLines() {
        return lines;
    }

    @Override
    public void imageToBoard() {
        BufferedImage image = imageLoader.getImage();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (image.getRGB(i, j) > 1_000_000 || image.getRGB(i, j) < -1_000_000) {
                    board[i][j].setFill(true);
                }
            }
        }
    }

    @Override
    public int fitness() {
        linesToBoard();
        int count = 0;
        linesToBoard();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (board[i][j].isFill()) {
                    count++;
                }
                if (board[i][j].isLine()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void linesToBoard() {
        for (Line line : lines) {
            calculateLinePoints(line);
        }
    }

    private void calculateLinePoints(Line line) {
        int dx = line.getToX() - line.getFromX();
        int dy = line.getToY() - line.getFromY();

        for (int x = line.getFromX(); x < line.getToX(); x++) {
            int y = line.getFromY() + dy * (x - line.getFromX()) / dx;
            board[x - OFFSET_X][y - OFFSET_Y].setLine(true);
        }
    }

}
