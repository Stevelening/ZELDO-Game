package map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import entite.Joueur;

import other.Position;

public class Map {
    private static int MAPSIZE = 5;
    private Room[][] map = new Room[MAPSIZE][MAPSIZE];
    private Joueur joueur; 
    private Room actual;
    //private KeyHandler kh = new KeyHandler();

    public Map(){
        this.joueur = new Joueur(new Position(Room.WIDTH/2, Room.HEIGHT/2));
        
        Random rand = new Random();
        Position[][] map_positions = new Position[MAPSIZE][MAPSIZE];

        for(int i = 0; i<MAPSIZE; i++){
            for(int j = 0; j<MAPSIZE; j++){
                map_positions[i][j] = new Position(i, j);
            }
        }

        ArrayList<Position> pos_placees = new ArrayList<Position>();
        ArrayList<Position> pos_dispos = new ArrayList<Position>();

        // on établit combien de salles de quel type on doit placer (toujours 8)
        ArrayList<Room> salles_a_placer = new ArrayList<Room>();
        salles_a_placer.add(new RoomTresor(this));
        salles_a_placer.add(new RoomMarchand(this));
        int nb_salles_hostiles = rand.nextInt(3,7);
        int nb_salles_neutres = rand.nextInt(2,6);
        for(int i = 0; i< nb_salles_neutres; i++){
            salles_a_placer.add(new RoomNeutre(this));
        }
        for(int i = 0; i< nb_salles_hostiles; i++){
            salles_a_placer.add(new RoomHostile(this));
        }
        int nb_salles_total = 2 + nb_salles_neutres + nb_salles_hostiles;
        // génération des 8 salles les une à côté des autres
        Position first_salle = map_positions[rand.nextInt(0, MAPSIZE-1)][rand.nextInt(0, MAPSIZE-1)];
        pos_placees.add(first_salle);
        if(first_salle.getX() != MAPSIZE-1){
            pos_dispos.add(map_positions[first_salle.getX() + 1][first_salle.getY()]);
        }
        if(first_salle.getX() != 0){
            pos_dispos.add(map_positions[first_salle.getX() - 1][first_salle.getY()]);
        }
        if(first_salle.getY() != MAPSIZE-1){
            pos_dispos.add(map_positions[first_salle.getX()][first_salle.getY() + 1]);
        }
        if(first_salle.getY() != 0){
            pos_dispos.add(map_positions[first_salle.getX()][first_salle.getY() - 1]);
        }
        
        this.map[first_salle.getX()][first_salle.getY()] = new RoomNeutre(this);
        this.map[first_salle.getX()][first_salle.getY()].setPosition(first_salle);
        //System.err.println("position salle : " + this.map[first_salle.getX()][first_salle.getY()].getPosition().getX() + " , " + this.map[first_salle.getX()][first_salle.getY()].getPosition().getY());
        this.map[first_salle.getX()][first_salle.getY()].setWhereIAm(true);
        setActual();

        Position new_pos;
        Position new_salle;
        int type_salle_index;
        for(int i = 0; i < nb_salles_total; i++){
            new_salle = pos_dispos.get(rand.nextInt(pos_dispos.size()));
            pos_dispos.remove(new_salle);
            pos_placees.add(new_salle);
            type_salle_index = rand.nextInt(0,salles_a_placer.size());
            this.map[new_salle.getX()][new_salle.getY()] = salles_a_placer.get(type_salle_index);
            this.map[new_salle.getX()][new_salle.getY()].setPosition(new_salle);
            salles_a_placer.remove(type_salle_index);

            if(new_salle.getX() < MAPSIZE-1){
                new_pos = map_positions[new_salle.getX() + 1][new_salle.getY()];
                if(!pos_placees.contains(new_pos) && !pos_dispos.contains(new_pos)){
                    pos_dispos.add(new_pos);
                }
            }
            if(new_salle.getX() > 0){
                new_pos = map_positions[new_salle.getX() - 1][new_salle.getY()];
                if(!pos_placees.contains(new_pos) && !pos_dispos.contains(new_pos)){
                    pos_dispos.add(new_pos);
                }
            }
            if(new_salle.getY() < MAPSIZE-1){
                new_pos = map_positions[new_salle.getX()][new_salle.getY() + 1];
                if(!pos_placees.contains(new_pos) && !pos_dispos.contains(new_pos)){
                    pos_dispos.add(new_pos);
                }
            }
            if(new_salle.getY() > 0){
                new_pos = map_positions[new_salle.getX()][new_salle.getY() - 1];
                if(!pos_placees.contains(new_pos) && !pos_dispos.contains(new_pos)){
                    pos_dispos.add(new_pos);
                }
            }
        }

        for(int a = 0; a < MAPSIZE; a++){
            for(int b = 0; b < MAPSIZE; b++){
                if(this.map[a][b] != null){
                    this.map[a][b].generate();
                }
            }
        }
    }

    @Override
    public String toString(){
        String res = new String();
        for(int i = 0; i<MAPSIZE; i++){
            for(int j = 0; j<MAPSIZE; j++){
                if(map[i][j]==null){
                    res += " 0 ";
                }
                else if(map[i][j] instanceof RoomMarchand){
                    res += " 1 ";
                }
                else if(map[i][j] instanceof RoomHostile){
                    res += " 2 ";
                }
                else if(map[i][j] instanceof RoomNeutre){
                    res += " 3 ";
                }
                else if(map[i][j] instanceof RoomTresor){
                    res += " 4 ";
                }
            }
            res += "\n";
        }
        return res;
    }

    public LinkedList<Position.Direction> getAdjacent(Position pos){
        LinkedList<Position.Direction> list_pos = new LinkedList<Position.Direction>();
        if(pos.getX() > 0 && map[pos.getX()-1][pos.getY()] != null){
            list_pos.add(Position.Direction.LEFT);
        }
        if(pos.getX() < MAPSIZE-1 && map[pos.getX()+1][pos.getY()] != null){
            list_pos.add(Position.Direction.RIGHT);
        }
        if(pos.getY() > 0 && map[pos.getX()][pos.getY()-1] != null){
            list_pos.add(Position.Direction.DOWN);
        }
        if(pos.getY() < MAPSIZE-1 && map[pos.getX()][pos.getY()+1] != null){
            list_pos.add(Position.Direction.UP);
        }
        return list_pos;
    }

    public static int getMAPSIZE(){
        return MAPSIZE;
    }

    public Room[][] getMap(){
        return this.map;
    }

    public Joueur getJoueur(){
        return this.joueur;
    }

    public void setJoueurPosition(Position pos){
        this.joueur.setPosition(pos);
    }

    public Room getActual(){
        return this.actual;
    }

    public void setActual(){
        for(int i = 0; i<Map.getMAPSIZE(); i++) {
            for(int j = 0; j<Map.getMAPSIZE(); j++){
                if(map[i][j] != null && map[i][j].getWhereIAm()){
                    this.actual = map[i][j];
                }
            }
        }
    }

}
