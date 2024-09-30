package objects;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;


public class Cle extends Items{
    

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/key.png");
        ig.draw(g, pos, img);
    }

    public Cle(Position pos){
        super(pos);
    }
}
