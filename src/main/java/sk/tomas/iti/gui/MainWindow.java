package sk.tomas.iti.gui;

import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.PostInit;

import javax.swing.*;
import java.awt.event.*;

@Bean("mainWindow")
public class MainWindow extends JFrame {

    @Inject
    private ImagePanel imagePanel;
    @Inject
    private Core core;

    //@PostInit
    public void init() {
        setTitle("Image to lines");
        setSize(800, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().add(imagePanel);
        createListeners();
        createMenuBar();
        setVisible(true);

    }

    private void randomizeLines() {
        core.initLines();
        core.imageToBoard();
        imagePanel.repaint();
    }

    private void changePosition() {
        core.changePosition();
        imagePanel.repaint();
    }

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("Menu");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem newGame = new JMenuItem("New");
        newGame.setMnemonic(KeyEvent.VK_E);
        newGame.setToolTipText("Generate new lines");
        newGame.addActionListener((ActionEvent event) -> randomizeLines());

        JMenuItem rotate = new JMenuItem("Rotate");
        rotate.setMnemonic(KeyEvent.VK_E);
        rotate.setToolTipText("Rotate");
        rotate.addActionListener((ActionEvent event) -> changePosition());

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setToolTipText("Exit application");
        exit.addActionListener((ActionEvent event) -> System.exit(0));

        file.add(newGame);
        file.add(rotate);
        file.add(exit);

        menubar.add(file);

        setJMenuBar(menubar);
    }

    private void createListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                changePosition();
                System.out.println(core.fitness());
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }


}
