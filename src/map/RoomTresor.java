package map;

import java.util.ArrayList;
import java.util.Random;

import main.GraphiqueInterface;
import objects.Items;
import objects.Pieces;
import other.Position;
import special_entities.Chest;
import tuiles.*;

import java.awt.*;


public class RoomTresor extends Room{
    private ArrayList<Pieces> pieces = new ArrayList<Pieces>();
    private Chest chest;
    
    public RoomTresor(Map map){
        super(map);
    }

    public void generate(){
        //System.out.println("Appel a generate tresor");
        //génération des portes
        generate_doors();
        
        //Positions du coffre
        Position pos = new Position(WIDTH/2, HEIGHT/2);
        this.chest = new Chest(pos);

        //création des pieces
        Random rand = new Random();
        int nb_pieces = rand.nextInt(1,3);
        Position pos_piece1 = new Position(rand.nextInt(2, WIDTH/2-3), rand.nextInt(2, HEIGHT/2-3));
        Position pos_piece2 = new Position(rand.nextInt(2, WIDTH/2-3), rand.nextInt(HEIGHT/2+3, HEIGHT-3));
        Position pos_piece3 = new Position(rand.nextInt(WIDTH/2+3, WIDTH-3), rand.nextInt(2, HEIGHT/2-3));
        ArrayList<Position> list_pos_pieces = new ArrayList<Position>();
        list_pos_pieces.add(pos_piece1);
        list_pos_pieces.add(pos_piece2);
        list_pos_pieces.add(pos_piece3);
        for(int i = 0; i<nb_pieces; i++){
            int j = rand.nextInt(0, 3-i);
            pieces.add(new Pieces(list_pos_pieces.get(j)));
            list_pos_pieces.remove(j);
        }

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
                Boolean is_piece = false;
                Pieces p = null;
                for(int l = 0; l<3; l++){
                    if((this.pieces.size() > l) && this.pieces.get(l).getPosition().getX()==i && this.pieces.get(l).getPosition().getY()==j){
                        is_piece = true;  
                        p = this.pieces.get(l);
                    }
                }
                Boolean is_chest = false;
                for (Position position : this.chest.getPosition()) {
                    if(position.getX() == i && position.getY() == j){
                        is_chest = true;
                    }
                }
                if((i == 0 || j == 0 || i == WIDTH-1 || j == HEIGHT-1) && not_porte){
                    this.carte[i][j] = new Murs();
                    this.carte[i][j].affichage(g, pos, ig);
                }else if(is_chest){
                    this.carte[i][j] = this.chest;
                    this.chest.affichage(g, pos, ig);
                }else if(is_piece){
                    this.carte[i][j] =  p;
                    p.affichage(g, pos, ig);
                }else{
                    this.carte[i][j] = new Sols();
                    this.carte[i][j].affichage(g, pos, ig);
                }
            }
        }
    }
    public ArrayList<Pieces> getItems(){
        return this.pieces;
    }

    public Chest getChest(){
        return this.chest;
    }
}
