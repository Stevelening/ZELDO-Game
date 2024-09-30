package map;

import java.util.ArrayList;
import java.util.Random;

import main.GraphiqueInterface;
import objects.*;
import other.*;
import tuiles.Murs;
import tuiles.Sols;

import java.awt.*;
import javax.swing.*;


public class RoomNeutre extends Room{

    private ArrayList<Items> obj = new ArrayList<Items>();
    
    public RoomNeutre(Map map){
        super(map);
    }
    
    public void generate(){
        //System.out.println("Appel a generate neutre");
        //génération des portes
        generate_doors();


        //génération objects
        Random rand = new Random();
        generate_obj(obj, rand.nextInt(1,4), rand);
        //System.out.println(obj.toString());
    }

    @Override
    public void affichage(Graphics g, GraphiqueInterface ig){
        for(int i = 0; i<WIDTH; i++){
            for(int j = 0; j<HEIGHT; j++){
                Position pos = new Position(i, j);
                Boolean not_porte = true;
                for(int k = 0; k<4; k++){
                    if((this.portes.size() > k) && this.portes.get(k).getX()==i && this.portes.get(k).getY()==j){
                        not_porte = false;
                    }
                }
                Boolean is_obj = false;
                Items o = null;
                for(int l = 0; l<4; l++){
                    if((this.obj.size()>l) && this.obj.get(l).getPosition().getX()==i && this.obj.get(l).getPosition().getY()==j){
                        is_obj = true;  
                        o = this.obj.get(l);
                    }
                }
                if((i == 0 || j == 0 || i == WIDTH-1 || j == HEIGHT-1) && not_porte){
                    this.carte[i][j] = new Murs();
                    this.carte[i][j].affichage(g, pos, ig);
                }else if(is_obj){
                    this.carte[i][j] = o;
                    o.affichage(g, pos, ig);
                }else{
                    this.carte[i][j] = new Sols();
                    this.carte[i][j].affichage(g, pos, ig);
                }
            }
        }
    }

    public ArrayList<Items> getItems(){
        return obj;
    }
}
