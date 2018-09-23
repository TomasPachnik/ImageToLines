package sk.tomas.iti.core;

import sk.tomas.iti.bo.Line;
import sk.tomas.iti.bo.Node;

public interface Core {

    Node[][] getBoard();

    Line[] getLines();

    void initLines();

    void imageToBoard();

    int fitness();

    void changePosition();

}
