package objects;

import java.awt.*;

import main.GraphiqueInterface;

public class LifeManager {
    private int nb_vies;

    // choisir 3 pour correspondre au cahier des charges
    public LifeManager(int nb_vies){
        this.nb_vies = nb_vies;
    }

    public int getNbVies(){
        return this.nb_vies;
    }

    public void removeLife(){
        this.nb_vies --;
    }

    public void restoreLife(){
        this.nb_vies ++;
    }

    public void affichage(Graphics g, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/coeur.png");
        for(int i = 0; i<this.nb_vies; i++){
            g.drawImage(img, i * 100, 600, 100, 100, null, ig);
        }
        
    }

    

}
