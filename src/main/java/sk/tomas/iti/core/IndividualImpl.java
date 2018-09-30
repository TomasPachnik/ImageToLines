package sk.tomas.iti.core;

import sk.tomas.iti.bo.Line;
import sk.tomas.iti.bo.Node;
import sk.tomas.iti.config.Randomizer;
import sk.tomas.iti.ga.Individual;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static sk.tomas.iti.config.Configuration.*;

public class IndividualImpl extends Individual {

    private Node[][] board;
    private Line[] lines;

    @Override
    public void mutate() {
        changePosition();
    }

    @Override
    public void init() {
        initNodes();
        initLines();
        fitness = fitness(1);
    }

    @Override
    public void run() {
        fitness = fitness(1);
    }

    @Override
    public Individual[] cross(Individual parent2) {

        IndividualImpl par2 = (IndividualImpl) parent2;
        Individual[] result = new IndividualImpl[2];
        result[0] = this;
        result[1] = par2;
        return result;
    }


    private void changePosition() {
        for (Line line : lines) {
            move(line, Randomizer.generateShiftX(), Randomizer.generateShiftY());
            rotate(line, Randomizer.generateAngle());
        }
        cleanLinesInBoard();
        linesToBoard();
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

    private int fitness(int add) {
        int count = 0;
        cleanLinesInBoard();
        linesToBoard();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (board[i][j].getFill() > 0 && board[i][j].isLine()) {
                    count += board[i][j].getFill();
                }
            }
        }
        return count + add;
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

    private void initNodes() {
        if (board == null) {
            board = new Node[WIDTH][HEIGHT];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[j].length; j++) {
                    board[i][j] = new Node();
                }
            }
        }
        try {
            imageToBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLines() {
        if (lines == null) {
            lines = new Line[LINE_COUNT];
            for (int i = 0; i < lines.length; i++) {
                lines[i] = Randomizer.generateLines();
            }
        }
    }

    private void imageToBoard() throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH + "image.png"));
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (image.getRGB(i, j) > 1_000_000 || image.getRGB(i, j) < -1_000_000) {
                    setValues(i, j);
                }
            }
        }
    }

    private void setValues(int i, int j) {
        board[i][j].setFill(16);
        for (int k = i - 1; k <= i + 1; k += 2) {
            for (int l = j - 1; l <= j + 1; l += 2) {
                if (k >= 0 && l >= 0 && k < WIDTH && l < HEIGHT) {
                    board[k][l].setFill(board[k][l].getFill() + 8);
                }
            }
        }
        for (int k = i - 2; k <= i + 2; k += 4) {
            for (int l = j - 2; l <= j + 2; l += 4) {
                if (k >= 0 && l >= 0 && k < WIDTH && l < HEIGHT) {
                    board[k][l].setFill(board[k][l].getFill() + 4);
                }
            }
        }
        for (int k = i - 3; k <= i + 3; k += 6) {
            for (int l = j - 3; l <= j + 3; l += 6) {
                if (k >= 0 && l >= 0 && k < WIDTH && l < HEIGHT) {
                    board[k][l].setFill(board[k][l].getFill() + 2);
                }
            }
        }
    }

}
