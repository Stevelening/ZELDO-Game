package objects;

import java.util.ArrayList;

import main.GraphiqueInterface;
import other.Position;

import java.awt.*;

public class BackPack {
    
    private ArrayList<Items> contains;

    public BackPack(){
        this.contains = new ArrayList<Items>();
    }

    public void remove(int index){
        this.contains.remove(index);
    }

    public void removeObj(String s){
        int index = 0;
        switch (s) {
            case "C":
                boolean key = false;
                for (Items items : contains) {
                    if(items instanceof Cle){
                        key = true;
                        break;
                    }
                    index ++;
                }
                if(key == false){
                    System.err.println("On ne peut pas enlever de clé au sac à dos.");
                }else{
                    this.contains.remove(index);
                }
                break;
            case "E":
                boolean epee = false;
                for (Items items : contains) {
                    if(items instanceof Epee){
                        epee = true;
                        break;
                    }
                    index ++;
                }
                if(epee == false){
                    System.err.println("On ne peut pas enlever d'épée au sac à dos.");
                }else{
                    this.contains.remove(index);
                }
                break;
            case "P":
                boolean potion = false;
                for (Items items : contains) {
                    if(items instanceof Potion){
                        potion = true;
                        break;
                    }
                    index ++;
                }
                if(potion == false){
                    System.err.println("On ne peut pas enlever de potion au sac à dos.");
                }else{
                    this.contains.remove(index);
                }
                break;
            case "Pi":
                boolean piece = false;
                for (Items items : contains) {
                    if(items instanceof Pieces){
                        piece = true;
                        break;
                    }
                    index ++;
                }
                if(piece == false){
                    System.err.println("On ne peut pas enlever de piece au sac à dos.");
                }else{
                    this.contains.remove(index);
                }
                break;
            default:
                break;
        }
    }

    public void add(Items item){
        if(this.contains.size() < 10){
            this.contains.add(item);
        }
    }

    public boolean contains3Key(){
        int i = 0;
        for (Items items : contains) {
            if(items instanceof Cle){
                i += 1;
            }
        }
        if(i >= 3){
            return true;
        }
        return false;
    }

    public boolean containsEpee(){
        for(Items items : contains){
            if(items instanceof Epee){
                return true;
            }
        }
        return false;
    }

    public boolean containsPotion(){
        for(Items items : contains){
            if(items instanceof Potion){
                return true;
            }
        }
        return false;
    }

    public int getSize(){
        return contains.size();
    }

    public ArrayList<Items> getContains(){
        return this.contains;
    } 

    public void affichage(Graphics g, GraphiqueInterface ig){
        Image img = Toolkit.getDefaultToolkit().getImage("images/floor.png");
        Image key = Toolkit.getDefaultToolkit().getImage("images/key.png");
        Image piece = Toolkit.getDefaultToolkit().getImage("images/piece.png");
        Image epee = Toolkit.getDefaultToolkit().getImage("images/epee.png");
        Image potion = Toolkit.getDefaultToolkit().getImage("images/potion.png");

        for(int i = 0; i<5; i++){
            g.drawImage(img, i*100, 750, 100, 100, null, ig);
        }
        for(int i = 0; i<5; i++){
            g.drawImage(img, i*100, 850, 100, 100, null, ig);
        }
        for(int i = 0; i<contains.size(); i++){
            Image image = null;
            if(contains.get(i) instanceof Cle)
                image = key;
            if(contains.get(i) instanceof Pieces)
                image = piece;
            if(contains.get(i) instanceof Epee)
                image = epee;
            if(contains.get(i) instanceof Potion)
                image = potion;
            if(i < 5){
                if(contains.get(i).isSelected()){
                    g.drawImage(image, i*100, 750, 100, 100, null, ig);
                }else{
                    g.drawImage(image, 25 + i*100, 775, 50, 50, null, ig);
                }
            }else{
                if(contains.get(i).isSelected()){
                    g.drawImage(image, (i-5)*100, 850, 100, 100, null, ig);
                }else{
                    g.drawImage(image, 25 + (i-5)*100, 875, 50, 50, null, ig);
                }
            }
        }
    }

    public void objSelected(int index){
        for (Items it : contains) {
            it.dontSelectThisObj();
        }
        contains.get(index).selectThisObj();
    }

    public Items getSelected(){
        for (Items items : contains) {
            if(items.isSelected()){
                return items;
            }
        }
        return null;
    }

}
