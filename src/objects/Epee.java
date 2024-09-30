package objects;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;


public class Epee extends Items{
    

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/epee.png");
        ig.draw(g, pos, img);
    }

    public Epee(Position pos){
        super(pos);
    }
}
