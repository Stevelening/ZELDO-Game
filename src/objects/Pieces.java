package objects;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;



public class Pieces extends Items{
    
    public Pieces(Position pos){
        super(pos);
    }

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/piece.png");
        ig.draw(g, pos, img);
    }

}
