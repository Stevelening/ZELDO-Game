package map;
import other.*;

import java.awt.*;
import javax.swing.*;

import main.GraphiqueInterface;
import objects.Cle;
import objects.Epee;
import objects.Items;
import objects.Pieces;
import objects.Potion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public abstract class Room {
    public static int HEIGHT = 20;
    public static int WIDTH = 30;
    protected ArrayList<Position> portes = new ArrayList<Position>();
    protected Position pos_dans_la_map;
    private Map map;
    protected Boolean whereIAm = false;
    protected Components[][] carte = new Components[WIDTH][HEIGHT];

    public Room(Map map){
        this.map = map;
    }

    public void generate_doors(){
        LinkedList<Position.Direction> adj = map.getAdjacent(pos_dans_la_map);
        if(adj.contains(Position.Direction.UP)){
            int x = WIDTH/2;
            int y = HEIGHT-1;
            portes.add(new Position(x,y));
        }
        if(adj.contains(Position.Direction.DOWN)){
            int x = WIDTH/2;
            int y = 0;
            portes.add(new Position(x,y));
        }
        if(adj.contains(Position.Direction.LEFT)){
            int x = 0;
            int y = HEIGHT/2;
            portes.add(new Position(x,y));
        }
        if(adj.contains(Position.Direction.RIGHT)){
            int x = WIDTH-1;
            int y = HEIGHT/2;
            portes.add(new Position(x,y));
        }
    }

    public abstract void generate();

    public void setPosition(Position pos){
        this.pos_dans_la_map = pos;
    }

    public Position getPosition(){
        return this.pos_dans_la_map;
    }

    public abstract void affichage(Graphics g, GraphiqueInterface ig);


    public void setWhereIAm(Boolean b){
        this.whereIAm = b;
    }

    public Boolean getWhereIAm(){
        return this.whereIAm;
    }

    public ArrayList<Position> getPortes(){
        return this.portes;
    }

    public Components[][] getCarte(){
        return this.carte;
    }

    private enum TypeObj{
        CLE, EPEE, PIECE, POTION
    }
    protected void generate_obj(ArrayList<Items> obj, int nb_obj, Random rand){
        Position pos_piece1 = new Position(rand.nextInt(2, WIDTH/2-3), rand.nextInt(2, HEIGHT/2-3));
        Position pos_piece2 = new Position(rand.nextInt(2, WIDTH/2-3), rand.nextInt(HEIGHT/2+3, HEIGHT-3));
        Position pos_piece3 = new Position(rand.nextInt(WIDTH/2+3, WIDTH-3), rand.nextInt(2, HEIGHT/2-3));
        Position pos_piece4 = new Position(rand.nextInt(WIDTH/2+3, WIDTH-3), rand.nextInt(HEIGHT/2+3, HEIGHT-3));
        ArrayList<Position> list_pos_pieces = new ArrayList<Position>();
        list_pos_pieces.add(pos_piece1);
        list_pos_pieces.add(pos_piece2);
        list_pos_pieces.add(pos_piece3);
        list_pos_pieces.add(pos_piece4);
        TypeObj[] type_obj = {TypeObj.CLE, TypeObj.EPEE, TypeObj.PIECE, TypeObj.POTION};

        for(int i = 0; i<nb_obj; i++){
            int k = rand.nextInt(0, 3);
            int j = rand.nextInt(0,3-i);
            switch (type_obj[k]) {
                case CLE:
                    obj.add(new Cle(list_pos_pieces.get(j)));
                    list_pos_pieces.remove(j);
                    break;
                case EPEE:
                    obj.add(new Epee(list_pos_pieces.get(j)));
                    list_pos_pieces.remove(j);
                    break;
                case PIECE:
                    obj.add(new Pieces(list_pos_pieces.get(j)));
                    list_pos_pieces.remove(j);
                    break;
                case POTION:
                    obj.add(new Potion(list_pos_pieces.get(j)));
                    list_pos_pieces.remove(j);
                    break;
                default:
                    break;
            }
        }
    }
}
