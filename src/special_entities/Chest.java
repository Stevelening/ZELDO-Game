package special_entities;

import java.awt.*;
import java.util.ArrayList;
import other.*;
import main.GraphiqueInterface;

public class Chest extends Components{
    private ArrayList<Position> chest = new ArrayList<Position>();

    public Chest(Position pos){
        Position pos2 = new Position(pos.getX()-1, pos.getY());
        Position pos3 = new Position(pos.getX()-1, pos.getY()-1);
        Position pos4 = new Position(pos.getX(), pos.getY()-1);
        this.chest.add(pos);
        this.chest.add(pos2);
        this.chest.add(pos3);
        this.chest.add(pos4);
    }

    public ArrayList<Position> getPosition(){
        return this.chest;
    }

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/chest.png");
        for (Position p : chest) {
            ig.draw(g, p, img);
        }
    }
}
