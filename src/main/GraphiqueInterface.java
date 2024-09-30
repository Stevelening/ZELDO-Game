package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import map.Map;
import other.*;
import player_interraction.PlayerInterraction;
import special_screens.Loser;
import special_screens.Winner;
import map.Room;

public class GraphiqueInterface extends JFrame{

    private JPanel gridPanel;
    private Position posPlayer;
    private static Map map = new Map();
    private static int CELLSIZE = 30;
    private GraphiqueInterface IG;
    private PlayerInterraction playIn;



    public GraphiqueInterface(){
        setTitle("GAME");
        setSize(910, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.posPlayer = map.getJoueur().getPosition();
        this.playIn = new PlayerInterraction(map.getJoueur());
    
        this.IG = this;

        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if(Main.VICTOIRE == false && Main.DEFAITE == false){
                    map.getActual().affichage(g, IG);
                    map.getJoueur().affichage(g, posPlayer, IG);
                    map.getJoueur().getLifeManager().affichage(g, IG);
                    map.getJoueur().getBackPack().affichage(g, IG);
                }else if(Main.VICTOIRE == true){
                    Winner win = new Winner();
                    win.affichage(g, IG);
                }else{
                    Loser loser = new Loser();
                    loser.affichage(g, IG);
                }
            }
        };
        add(gridPanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playIn.movePlayer(e.getKeyCode(), map, IG);
                playIn.selectObj(e.getKeyCode(), map, IG);
                gridPanel.repaint();
            }
        });

        setFocusable(true);
        setVisible(true);
    }    

    public void draw(Graphics g, Position pos, Image img){
        g.drawImage(img, pos.getX()*CELLSIZE, pos.getY()*CELLSIZE, CELLSIZE, CELLSIZE, null, this);
    }
}
