package sk.tomas.iti.bo;

public class Node {

    private boolean fill;
    private boolean line;

    public Node() {

    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isLine() {
        return line;
    }

    public void setLine(boolean line) {
        this.line = line;
    }
}
