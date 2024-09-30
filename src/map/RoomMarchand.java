package map;

import main.GraphiqueInterface;
import objects.Items;
import other.Position;
import special_entities.Marchand;
import tuiles.Murs;
import tuiles.Sols;

import java.awt.*;

public class RoomMarchand extends Room{
    private Marchand marchand;
    
    public RoomMarchand(Map map){
        super(map);
    }

    public void generate(){
        //génération des portes
        generate_doors();

        //génération de la position du marchand
        Position pos_marchand = new Position(WIDTH/2, HEIGHT/2);
        marchand = new Marchand(pos_marchand);

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
                Boolean is_marchand = false;
                for (Position position : this.marchand.getPosition()) {
                    if(position.getX() == i && position.getY() == j){
                        is_marchand = true;
                    }
                }
                if((i == 0 || j == 0 || i == WIDTH-1 || j == HEIGHT-1) && not_porte){
                    this.carte[i][j] = new Murs();
                    this.carte[i][j].affichage(g, pos, ig);
                }else if(is_marchand){
                    this.carte[i][j] = this.marchand;
                    this.marchand.affichage(g, pos, ig);
                }else{
                    this.carte[i][j] = new Sols();
                    this.carte[i][j].affichage(g, pos, ig);
                }
            }
        }
    }

    public Marchand getMarchand(){
        return this.marchand;
    }
}
