package sk.tomas.iti.gui;

import sk.tomas.iti.core.Core;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.PostInit;

import javax.swing.*;
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
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().add(imagePanel);
        createListeners();
        setVisible(true);
        start();
    }

    //TODO add to button
    private void start() {
        core.initLines();
        core.imageToBoard();
        System.out.println(core.fitness());
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
