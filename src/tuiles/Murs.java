package tuiles;

import main.GraphiqueInterface;
import other.Components;
import other.Position;

import java.awt.*;

public class Murs extends Components{
    

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/walls.png");
        ig.draw(g, pos, img);
    }
}
