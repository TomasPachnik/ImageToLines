package sk.tomas.iti.gui;

import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.PostInit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Bean("mainWindow")
public class MainWindow extends JFrame {

    @Inject
    private ImagePanel imagePanel;
    @Inject
    private Core core;

    @PostInit
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
        System.out.println(core.fitness());
    }

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("Menu");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem newGame = new JMenuItem("New");
        newGame.setMnemonic(KeyEvent.VK_E);
        newGame.setToolTipText("Generate new lines");
        newGame.addActionListener((ActionEvent event) -> randomizeLines());

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setToolTipText("Exit application");
        exit.addActionListener((ActionEvent event) -> System.exit(0));

        file.add(newGame);
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
    }


}
