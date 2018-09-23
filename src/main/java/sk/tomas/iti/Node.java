package sk.tomas.iti;

public class Node {

    private boolean fill;

    public Node(boolean fill) {
        this.fill = fill;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
