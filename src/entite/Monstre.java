package entite;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;

public class Monstre extends Entite{
    
    public Monstre(Position pos){
        super(pos);
    }

    @Override
    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/monstre.png");
        ig.draw(g, pos, img);
    }
}
