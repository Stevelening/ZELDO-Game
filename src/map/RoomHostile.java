package map;

import java.awt.*;
import other.*;
import tuiles.Murs;
import tuiles.Sols;
import entite.*;
import java.util.ArrayList;
import java.util.Random;

import entite.Entite;
import main.GraphiqueInterface;
import objects.Items;


public class RoomHostile extends Room{

    private ArrayList<Entite> mechants = new ArrayList<Entite>();
    private ArrayList<Items> obj = new ArrayList<Items>();

    public RoomHostile(Map map){
        super(map);
    }


    private enum TypeMechant{
        MONSTRE, VOLEUR
    }

    public void generate(){
        generate_doors();

        //génération objects
        Random rand = new Random();
        generate_obj(obj, rand.nextInt(1,2), rand);

        //génération des méchants
        int nb_mch = rand.nextInt(1,3);
        Position pos_piece1 = new Position(rand.nextInt(2, WIDTH/2-3), rand.nextInt(2, HEIGHT/2-3));
        Position pos_piece2 = new Position(rand.nextInt(2, WIDTH/2-3), rand.nextInt(HEIGHT/2+3, HEIGHT-3));
        Position pos_piece3 = new Position(rand.nextInt(WIDTH/2+3, WIDTH-3), rand.nextInt(2, HEIGHT/2-3));
        Position pos_piece4 = new Position(rand.nextInt(WIDTH/2+3, WIDTH-3), rand.nextInt(HEIGHT/2+3, HEIGHT-3));
        ArrayList<Position> list_pos_pieces = new ArrayList<Position>();
        list_pos_pieces.add(pos_piece1);
        list_pos_pieces.add(pos_piece2);
        list_pos_pieces.add(pos_piece3);
        list_pos_pieces.add(pos_piece4);
        TypeMechant[] type_mch = {TypeMechant.MONSTRE, TypeMechant.VOLEUR};

        for(int i = 0; i<nb_mch; i++){
            int j = rand.nextInt(0,1);
            int k = rand.nextInt(0,3);
            switch (type_mch[j]) {
                case MONSTRE:
                    mechants.add(new Monstre(list_pos_pieces.get(k)));
                    list_pos_pieces.remove(j);
                    break;
                case VOLEUR:
                    mechants.add(new Voleur(list_pos_pieces.get(k)));
                    list_pos_pieces.remove(j);
                    break;
                default:
                    break;
            }
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
                Boolean is_obj = false;
                Items o = null;
                for(int l = 0; l<2; l++){
                    if((this.obj.size()>l) && this.obj.get(l).getPosition().getX()==i && this.obj.get(l).getPosition().getY()==j){
                        is_obj = true;  
                        o = this.obj.get(l);
                    }
                }
                Boolean is_mechant = false;
                Entite m = null;
                for(int l = 0; l<3; l++){
                    if((this.mechants.size()>l) && this.mechants.get(l).getPosition().getX()==i && this.mechants.get(l).getPosition().getY()==j){
                        is_mechant = true;  
                        m = this.mechants.get(l);
                    }
                }
                if((i == 0 || j == 0 || i == WIDTH-1 || j == HEIGHT-1) && not_porte){
                    this.carte[i][j] = new Murs();
                    this.carte[i][j].affichage(g, pos, ig);
                }else if(is_obj){
                    this.carte[i][j] = o;
                    o.affichage(g, pos, ig);
                }else if(is_mechant){
                    this.carte[i][j] = m;
                    m.affichage(g, pos, ig);
                }else{
                    this.carte[i][j] = new Sols();
                    this.carte[i][j].affichage(g, pos, ig);
                }
            }
        }
    }

    public ArrayList<Entite> getMechants(){
        return mechants;
    }

    public ArrayList<Items> getItems(){
        return obj;
    }
}
