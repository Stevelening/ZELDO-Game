package special_screens;

import main.GraphiqueInterface;
import java.awt.*;

public class Winner {
    public void affichage(Graphics g, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/winner.png");
        g.drawImage(img, 0, 0, 910, 1000, null, ig);
    }
}