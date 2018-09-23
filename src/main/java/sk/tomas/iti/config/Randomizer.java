package sk.tomas.iti.config;

import sk.tomas.iti.bo.Line;
import sk.tomas.servant.annotation.Bean;

import java.util.Random;

import static sk.tomas.iti.config.Configuration.*;

@Bean
public class Randomizer {

    private Random fromX;
    private Random fromY;
    private Random length;
    private Random shiftX;
    private Random shiftY;
    private Random angle;

    public Randomizer() {
        fromX = new Random();
        fromY = new Random();
        length = new Random();
        shiftX = new Random();
        shiftY = new Random();
        angle = new Random();
    }

    public Line generateLines() {
        int x = fromX.nextInt(WIDTH) + OFFSET_X;
        int y = fromY.nextInt(HEIGHT) + OFFSET_Y;
        int lengthX;
        int lengthY;
        do {
            lengthX = this.length.nextInt(LINE_MAX_LENGTH) - LINE_MAX_LENGTH / 2;
            lengthY = this.length.nextInt(LINE_MAX_LENGTH) - LINE_MAX_LENGTH / 2;
        } while (x + lengthX > WIDTH + OFFSET_X || x + lengthX < OFFSET_X ||
                y + lengthY > HEIGHT + OFFSET_Y || y + lengthY < OFFSET_Y);

        return new Line(x, y, x + lengthX, y + lengthY);
    }

    public int generateShiftX() {
        return shiftX.nextInt(100) - 50;
    }

    public int generateShiftY() {
        return shiftY.nextInt(100) - 50;
    }

    public int generateAngle() {
        return angle.nextInt(50) - 25;
    }
}
