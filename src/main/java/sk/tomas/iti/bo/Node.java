package sk.tomas.iti.bo;

import java.io.Serializable;

public class Node implements Serializable {

    private int fill;
    private boolean line;

    public Node() {

    }

    public int getFill() {
        return fill;
    }

    public void setFill(int fill) {
        this.fill = fill;
    }

    public boolean isLine() {
        return line;
    }

    public void setLine(boolean line) {
        this.line = line;
    }
}
