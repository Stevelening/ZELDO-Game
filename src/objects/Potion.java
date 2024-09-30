package objects;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;


public class Potion extends Items{
    

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/potion.png");
        ig.draw(g, pos, img);
    }

    public Potion(Position pos){
        super(pos);
    }
}
