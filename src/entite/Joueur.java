package entite;

import java.awt.*;

import map.Room;
import main.GraphiqueInterface;
import objects.BackPack;
import objects.LifeManager;
import other.Position;

public class Joueur extends Entite {

    private BackPack backpack;
    private LifeManager lifeManager;

    public Joueur(Position pos){
        super(pos);
        this.backpack = new BackPack();
        this.lifeManager = new LifeManager(3);
    }

    public Boolean isInDoor(Room r){
        for(int i = 0; i<r.getPortes().size(); i++){
            if(r.getPortes().get(i).getX() == pos.getX() && r.getPortes().get(i).getY() == pos.getY()){
                return true;
            }
        }
        return false;
    }

    public void affichage(Graphics g, Position pos, GraphiqueInterface ig) {
        Image img = Toolkit.getDefaultToolkit().getImage("images/character.png");
        ig.draw(g, pos, img);
    }

    public LifeManager getLifeManager(){
        return this.lifeManager;
    }

    public BackPack getBackPack(){
        return this.backpack;
    }
}