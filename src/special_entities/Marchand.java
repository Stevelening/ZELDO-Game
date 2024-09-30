package special_entities;

import java.util.ArrayList;

import main.GraphiqueInterface;
import other.*;
import java.awt.*;

public class Marchand extends Components{
    private ArrayList<Position> bureau = new ArrayList<Position>();
    private Position marchand;

    public Marchand(Position pos){
        Position pos2 = new Position(pos.getX()-1, pos.getY());
        Position pos3 = new Position(pos.getX()+1, pos.getY());
        this.bureau.add(pos);
        this.bureau.add(pos2);
        this.bureau.add(pos3);
        this.marchand = new Position(pos.getX(), pos.getY()+1);
    }

    public ArrayList<Position> getPosition(){
        ArrayList<Position> list = new ArrayList<>();
        list.addAll(bureau);
        list.add(marchand);
        return list;
    }

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/marchand.png");
        for (Position p : this.bureau) {
            ig.draw(g, p, img);
        }
        ig.draw(g, marchand, img);
    }
}
