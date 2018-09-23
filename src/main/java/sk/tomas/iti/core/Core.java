package sk.tomas.iti.core;

import sk.tomas.iti.Line;
import sk.tomas.iti.Node;

public interface Core {

    Node[][] getBoard();

    Line[] getLines();

    void initLines();

    void imageToBoard();

    int fitness();

}
