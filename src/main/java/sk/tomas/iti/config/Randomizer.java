package sk.tomas.iti.config;

import sk.tomas.iti.bo.Line;
import sk.tomas.servant.annotation.Bean;

import java.util.Random;

import static sk.tomas.iti.config.Configuration.*;

public final class Randomizer {

    private static Random fromX = new Random();
    private static Random fromY = new Random();
    private static Random length = new Random();
    private static Random shiftX = new Random();
    private static Random shiftY = new Random();
    private static Random angle = new Random();

    private Randomizer() {
    }

    public static Line generateLines() {
        int x = fromX.nextInt(WIDTH) + OFFSET_X;
        int y = fromY.nextInt(HEIGHT) + OFFSET_Y;
        int lengthX;
        int lengthY;
        do {
            lengthX = length.nextInt(LINE_MAX_LENGTH) - LINE_MAX_LENGTH / 2;
            lengthY = length.nextInt(LINE_MAX_LENGTH) - LINE_MAX_LENGTH / 2;
        } while (x + lengthX > WIDTH + OFFSET_X || x + lengthX < OFFSET_X ||
                y + lengthY > HEIGHT + OFFSET_Y || y + lengthY < OFFSET_Y);

        return new Line(x, y, x + lengthX, y + lengthY);
    }

    public static int generateShiftX() {
        return shiftX.nextInt(2) - 1;
    }

    public static int generateShiftY() {
        return shiftY.nextInt(2) - 1;
    }

    public static int generateAngle() {
        return angle.nextInt(2) - 1;
    }
}
