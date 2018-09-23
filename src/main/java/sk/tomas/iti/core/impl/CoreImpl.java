package sk.tomas.iti.core.impl;

import sk.tomas.iti.Configuration;
import sk.tomas.iti.Line;
import sk.tomas.iti.Node;
import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;

@Bean("core")
public class CoreImpl implements Core {

    private Node[][] board;
    private Line[] lines;

    public CoreImpl() {
        init();
    }

    private void init() {
        board = new Node[Configuration.width][Configuration.height];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[j].length; j++) {
                board[i][j] = new Node(Math.random() < 0.5);
            }
        }
    }

    public Node[][] getBoard() {
        return board;
    }
}
