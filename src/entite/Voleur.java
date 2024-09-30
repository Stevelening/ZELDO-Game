package entite;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;

public class Voleur extends Entite{
    
    public Voleur(Position pos){
        super(pos);
    }

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/voleur.png");
        ig.draw(g, pos, img);
    }
}
