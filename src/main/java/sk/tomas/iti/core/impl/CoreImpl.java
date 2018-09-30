package sk.tomas.iti.core.impl;

import sk.tomas.iti.bo.Line;
import sk.tomas.iti.bo.Node;
import sk.tomas.iti.config.ImageLoader;
import sk.tomas.iti.config.Randomizer;
import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;

import java.awt.image.BufferedImage;

import static sk.tomas.iti.config.Configuration.*;

@Bean("core")
public class CoreImpl implements Core {

    private Node[][] board;
    private Line[] lines;

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
            lines[i] = Randomizer.generateLines();
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
                    board[i][j].setFill(1);
                }
            }
        }
    }

    @Override
    public int fitness() {
        int count = 0;
        cleanLinesInBoard();
        linesToBoard();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (board[i][j].getFill()>0 && board[i][j].isLine()) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void changePosition() {
        for (Line line : lines) {
            move(line, Randomizer.generateShiftX(), Randomizer.generateShiftY());
            rotate(line, Randomizer.generateAngle());
        }
        cleanLinesInBoard();
        linesToBoard();
    }

    private void cleanLinesInBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j].setLine(false);
            }
        }
    }

    private void linesToBoard() {
        for (Line line : lines) {
            calculateLinePoints(line);
        }
    }

    private void calculateLinePoints(Line line) {
        double dx = line.getToX() - line.getFromX();
        double dy = line.getToY() - line.getFromY();

        for (int x = (int) line.getFromX(); x < line.getToX(); x++) {
            int y = (int) (line.getFromY() + dy * (x - line.getFromX()) / dx);
            board[x - OFFSET_X][y - OFFSET_Y].setLine(true);
        }
    }

    private void move(Line input, int x, int y) {
        if (input.getFromX() + x >= OFFSET_X && input.getFromX() + x < WIDTH + OFFSET_X
                && input.getToX() + x >= OFFSET_X && input.getToX() + x < WIDTH + OFFSET_X
                && input.getFromY() + y >= OFFSET_Y && input.getFromY() + y < HEIGHT + OFFSET_Y
                && input.getToY() + y >= OFFSET_Y && input.getToY() + y < HEIGHT + OFFSET_Y) {
            input.setFromX(input.getFromX() + x);
            input.setToX(input.getToX() + x);
            input.setFromY(input.getFromY() + y);
            input.setToY(input.getToY() + y);
        }
    }

    private void rotate(Line input, int angle) {
        double x1 = input.getToX() - input.getFromX();
        double y1 = input.getFromY() - input.getToY();
        double x2 = input.getFromX() + (((x1 * Math.cos(angle * Math.PI / 180))) - ((y1 * Math.sin(angle * Math.PI / 180))));
        double y2 = input.getFromY() - (((x1 * Math.sin(angle * Math.PI / 180))) + ((y1 * Math.cos(angle * Math.PI / 180))));

        if (x2 >= OFFSET_X && x2 < WIDTH + OFFSET_X && y2 >= OFFSET_Y && y2 < HEIGHT + OFFSET_Y) {
            input.setToX(x2);
            input.setToY(y2);
        }
    }
}
