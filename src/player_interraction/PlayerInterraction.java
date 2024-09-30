package player_interraction;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Random;

import entite.*;
import main.*;
import map.*;
import other.Position;
import special_entities.*;
import tuiles.Murs;

import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.event.TreeSelectionEvent;

import objects.*;

public class PlayerInterraction {
    private Joueur joueur;
    private Position posPlayer;
    private MechantInterraction mechantInterraction;
    private ArrayList<Items> objAEchanger = new ArrayList<Items>();
    private Boolean movePlayer = true;
    private int indexObj = 0;


    public PlayerInterraction(Joueur joueur){
        this.joueur = joueur;
        this.posPlayer = joueur.getPosition();
        this.mechantInterraction = new MechantInterraction();
    }
    
    public void checkChangeSalle(Position.Direction dir, Map map, Position roomPosition, GraphiqueInterface ig){
        if(posPlayer.getX() == 0 && dir == Position.Direction.LEFT){
            map.getMap()[roomPosition.getX()][roomPosition.getY()].setWhereIAm(false);
            map.getMap()[roomPosition.getX()-1][roomPosition.getY()].setWhereIAm(true);
            posPlayer.setPosition(Room.WIDTH-1, posPlayer.getY());
        }
        if(posPlayer.getX() == Room.WIDTH-1 && dir == Position.Direction.RIGHT){
            map.getMap()[roomPosition.getX()][roomPosition.getY()].setWhereIAm(false);
            map.getMap()[roomPosition.getX()+1][roomPosition.getY()].setWhereIAm(true);
            posPlayer.setPosition(0, posPlayer.getY());
        }
        if(posPlayer.getY() == 0 && dir == Position.Direction.UP){
            map.getMap()[roomPosition.getX()][roomPosition.getY()].setWhereIAm(false);
            map.getMap()[roomPosition.getX()][roomPosition.getY()-1].setWhereIAm(true);
            posPlayer.setPosition(posPlayer.getX(), Room.HEIGHT-1);
        }
        if(posPlayer.getY() == Room.HEIGHT-1 && dir == Position.Direction.DOWN){
            map.getMap()[roomPosition.getX()][roomPosition.getY()].setWhereIAm(false);
            map.getMap()[roomPosition.getX()][roomPosition.getY()+1].setWhereIAm(true);
            posPlayer.setPosition(posPlayer.getX(), 0);
        }
        map.setActual();
        roomPosition = map.getActual().getPosition();
    }


    public void movePlayer(int keyCode, Map map, GraphiqueInterface ig) {
        if(movePlayer){
            Room actual = map.getActual();
            Position roomPosition = actual.getPosition();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    if (!(actual.getCarte()[posPlayer.getX()-1][posPlayer.getY()] instanceof Murs) && !(actual.getCarte()[posPlayer.getX()-1][posPlayer.getY()] instanceof Chest) && !(actual.getCarte()[posPlayer.getX()-1][posPlayer.getY()] instanceof Marchand))
                        posPlayer.setPosition(posPlayer.getX()-1, posPlayer.getY());
                        checkChangeSalle(Position.Direction.LEFT, map, roomPosition, ig);
                        if(actual instanceof RoomTresor){
                            interractionTresor((RoomTresor) actual, Position.Direction.LEFT);
                        }
                        if(actual instanceof RoomMarchand){
                            if(interractionMarchand((RoomMarchand) actual, Position.Direction.LEFT)){
                                this.movePlayer = false;
                                this.indexObj = 0;
                                this.objAEchanger.removeAll(objAEchanger);

                            }
                        }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!(actual.getCarte()[posPlayer.getX()+1][posPlayer.getY()] instanceof Murs) && !(actual.getCarte()[posPlayer.getX()+1][posPlayer.getY()] instanceof Chest) && !(actual.getCarte()[posPlayer.getX()+1][posPlayer.getY()] instanceof Marchand))
                        posPlayer.setPosition(posPlayer.getX()+1, posPlayer.getY());
                        checkChangeSalle(Position.Direction.RIGHT, map, roomPosition, ig);
                        if(actual instanceof RoomTresor){
                            interractionTresor((RoomTresor) actual, Position.Direction.RIGHT);
                        }
                        if(actual instanceof RoomMarchand){
                            if(interractionMarchand((RoomMarchand) actual, Position.Direction.RIGHT)){
                                this.movePlayer = false;
                                this.indexObj = 0;
                                this.objAEchanger.removeAll(objAEchanger);
                            }                        }
                    break;
                case KeyEvent.VK_UP:
                    if (!(actual.getCarte()[posPlayer.getX()][posPlayer.getY()-1] instanceof Murs) && !(actual.getCarte()[posPlayer.getX()][posPlayer.getY()-1] instanceof Chest) && !(actual.getCarte()[posPlayer.getX()][posPlayer.getY()-1] instanceof Marchand))
                        posPlayer.setPosition(posPlayer.getX(), posPlayer.getY()-1);
                        checkChangeSalle(Position.Direction.UP, map, roomPosition, ig);
                        if(actual instanceof RoomTresor){
                            interractionTresor((RoomTresor) actual, Position.Direction.UP);
                        }
                        if(actual instanceof RoomMarchand){
                            if(interractionMarchand((RoomMarchand) actual, Position.Direction.UP)){
                                this.movePlayer = false;
                                this.indexObj = 0;
                                this.objAEchanger.removeAll(objAEchanger);

                            }                        }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!(actual.getCarte()[posPlayer.getX()][posPlayer.getY()+1] instanceof Murs) && !(actual.getCarte()[posPlayer.getX()][posPlayer.getY()+1] instanceof Chest) && !(actual.getCarte()[posPlayer.getX()][posPlayer.getY()+1] instanceof Marchand))
                        posPlayer.setPosition(posPlayer.getX(), posPlayer.getY()+1);
                        checkChangeSalle(Position.Direction.DOWN, map, roomPosition, ig);
                        if(actual instanceof RoomTresor){
                            interractionTresor((RoomTresor) actual, Position.Direction.DOWN);
                        }
                        if(actual instanceof RoomMarchand){
                            if(interractionMarchand((RoomMarchand) actual, Position.Direction.DOWN)){
                                this.movePlayer = false;
                                this.indexObj = 0;
                                this.objAEchanger.removeAll(objAEchanger);

                            }                        }
                    break;
            }
            if(actual instanceof RoomHostile){
                checkCombattre((RoomHostile) actual);
                mechantInterraction.move(((RoomHostile)actual).getMechants(), actual);
                ramasserObjHostile((RoomHostile) actual);
            }
            if(actual instanceof RoomTresor){
                ramasserObjTresor((RoomTresor) actual);
            }
            if(actual instanceof RoomNeutre){
                ramasserObjNeutre((RoomNeutre) actual);
            }
        }
    }

    public void selectObj(int keyCode, Map map, GraphiqueInterface ig){
        if(!(movePlayer) && joueur.getBackPack().getSize() > 0){
            joueur.getBackPack().objSelected(this.indexObj);

            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    if(this.indexObj>0){
                        this.indexObj--;
                    }
                    joueur.getBackPack().objSelected(this.indexObj);
                    break;
                case KeyEvent.VK_RIGHT:
                    if(this.indexObj<joueur.getBackPack().getSize()-1){
                        this.indexObj++;
                    }
                    joueur.getBackPack().objSelected(this.indexObj);
                    break;
                case KeyEvent.VK_SPACE:
                    this.movePlayer = true;
                    break;
                case KeyEvent.VK_ENTER:
                    this.objAEchanger.add(joueur.getBackPack().getSelected());
                default:
                    break;
            }
        }
        while(objAEchangerContains3P()){
            int i = 0;
            int j = 0;
            while(i<3){
                if(objAEchanger.get(j) instanceof Pieces){
                    Items it = objAEchanger.remove(j);
                    joueur.getBackPack().getContains().remove(it);
                    i++;
                }else{
                    j++;
                }
            }
            joueur.getBackPack().add(new Cle(null));
            this.indexObj = 0;
        }
        while(objAEchangerContains2P()){
            int i = 0;
            int j = 0;
            while(i<2){
                System.out.println(objAEchanger.toString());
                if(objAEchanger.get(j) instanceof Pieces){
                    Items it = objAEchanger.remove(j);
                    joueur.getBackPack().getContains().remove(it);
                    i++;
                }else{
                    j++;
                }
            }
            this.indexObj = 0;

            Random rand = new Random();
            int index = rand.nextInt(0, 1);
            switch (index) {
                case 0:
                    joueur.getBackPack().add(new Potion(null));
                    break;
                case 1:
                    joueur.getBackPack().add(new Epee(null));
                    break;
                default:
                    break;
            }
        }
        while(objAEchangerContains1Obj()){
            int i = 0;
            int j = 0;
            while(i<1){
                if(!(objAEchanger.get(j) instanceof Pieces)){
                    Items it = objAEchanger.remove(j);
                    joueur.getBackPack().getContains().remove(it);
                    i++;
                }
                j++;
            }
            joueur.getBackPack().add(new Pieces(null));
            this.indexObj = 0;
        }
    }

    private Boolean objAEchangerContains1Obj(){
        if(!(objAEchanger.isEmpty())){
            for (Items items : objAEchanger) {
                if(!(items instanceof Pieces)){
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean objAEchangerContains2P(){
        if(!(objAEchanger.isEmpty())){
            int i = 0;
            for (Items it : objAEchanger) {
                if(it instanceof Pieces){
                    i++;
                }
            }
            if(i>=2){
                return true;
            }
        }
        return false;
    }

    private Boolean objAEchangerContains3P(){
        if(!(objAEchanger.isEmpty())){
            int i = 0;
            for (Items it : objAEchanger) {
                if(it instanceof Pieces){
                    i++;
                }
            }
            if(i>=3){
                return true;
            }
        }
        return false;
    }

    public void checkCombattre(RoomHostile r){
        Random rand = new Random();
        Boolean jeSuisSurMechant = false;
        Boolean monstre = false;
        Entite mechant = null;
        for (Entite e : r.getMechants()) {
            if(e.getPosition().getX() == joueur.getPosition().getX() && e.getPosition().getY() == joueur.getPosition().getY()){
                jeSuisSurMechant = true;
                mechant = e;
                if(e instanceof Monstre){
                    monstre = true;
                }
            }
        }
        if(jeSuisSurMechant){
            if(!(joueur.getBackPack().containsEpee())){
                if(monstre){
                    if(joueur.getBackPack().containsPotion()){
                        joueur.getBackPack().removeObj("P");
                    }else{
                        joueur.getLifeManager().removeLife();
                        if(joueur.getLifeManager().getNbVies()==0){
                            Main.DEFAITE = true;
                        }
                    }
                }else{
                    if(joueur.getBackPack().containsPotion()){
                        joueur.getBackPack().removeObj("P");
                    }else{
                        if(joueur.getBackPack().getSize() > 1){
                            joueur.getBackPack().remove(rand.nextInt(0, joueur.getBackPack().getSize()-1));
                        }else if(joueur.getBackPack().getSize() == 1){
                            joueur.getBackPack().remove(0);
                        }
                        
                    }
    
                }
            }else{
                System.out.println(joueur.getBackPack().getContains().toString());
                joueur.getBackPack().removeObj("E");
            }
            r.getMechants().remove(mechant);
        }
    }

    public void ramasserObjNeutre(RoomNeutre r){
        Boolean onObj = false;
        Items item = null;
        for (Items it : r.getItems()) {
            if(it.getPosition().getX() == joueur.getPosition().getX() && it.getPosition().getY() == joueur.getPosition().getY()){
                onObj = true;
                item = it;
                break;
            }
        }
        if(onObj){
            if(item instanceof Potion && joueur.getLifeManager().getNbVies() < 3){
                joueur.getLifeManager().restoreLife();
            }else{
                joueur.getBackPack().add(item);
            }
            r.getItems().remove(item);
        }
    }

    public void ramasserObjHostile(RoomHostile r){
        Boolean onObj = false;
        Items item = null;
        for (Items it : r.getItems()) {
            if(it.getPosition().getX() == joueur.getPosition().getX() && it.getPosition().getY() == joueur.getPosition().getY()){
                onObj = true;
                item = it;
                break;
            }
        }
        if(onObj){
            if(item instanceof Potion && joueur.getLifeManager().getNbVies() < 3){
                joueur.getLifeManager().restoreLife();
            }else{
                joueur.getBackPack().add(item);
            }
            r.getItems().remove(item);
        }
    }

    public void ramasserObjTresor(RoomTresor r){
        Boolean onObj = false;
        Items item = null;
        for (Items it : r.getItems()) {
            if(it.getPosition().getX() == joueur.getPosition().getX() && it.getPosition().getY() == joueur.getPosition().getY()){
                onObj = true;
                item = it;
                break;
            }
        }
        if(onObj){
            joueur.getBackPack().add(item);
            r.getItems().remove(item);
        }
    }

    public Boolean interractionMarchand(RoomMarchand r, Position.Direction dir){
        //System.out.println("Bonjour Marchand");
        Boolean aCoteMarchand = false;
        for (Position p : r.getMarchand().getPosition()) {
            switch (dir) {
                case LEFT:
                    if(p.getX() == joueur.getPosition().getX()-1 && p.getY() == joueur.getPosition().getY()){
                        aCoteMarchand = true;
                    }
                    break;
                case RIGHT:
                    if(p.getX() == joueur.getPosition().getX()+1 && p.getY() == joueur.getPosition().getY()){
                        aCoteMarchand = true;
                    }
                case UP:
                    if(p.getX() == joueur.getPosition().getX() && p.getY() == joueur.getPosition().getY()-1){
                        aCoteMarchand = true;
                    }
                case DOWN:
                    if(p.getX() == joueur.getPosition().getX() && p.getY() == joueur.getPosition().getY()+1){
                        aCoteMarchand = true;
                    }
                default:
                    break;
            }
        }
        //System.out.println("Bonjour Marchand : "+ aCoteMarchand);

        return aCoteMarchand;
    }

    public void interractionTresor(RoomTresor r, Position.Direction dir){
        Boolean aCoteTresor = false;
        for (Position p : r.getChest().getPosition()) {
            switch (dir) {
                case LEFT:
                    if(p.getX() == joueur.getPosition().getX()-1 && p.getY() == joueur.getPosition().getY()){
                        aCoteTresor = true;
                    }
                    break;
                case RIGHT:
                    if(p.getX() == joueur.getPosition().getX()+1 && p.getY() == joueur.getPosition().getY()){
                        aCoteTresor = true;
                    }
                case UP:
                    if(p.getX() == joueur.getPosition().getX() && p.getY() == joueur.getPosition().getY()-1){
                        aCoteTresor = true;
                    }
                case DOWN:
                    if(p.getX() == joueur.getPosition().getX() && p.getY() == joueur.getPosition().getY()+1){
                        aCoteTresor = true;
                    }
                default:
                    break;
            }
        }
        if(aCoteTresor){
            if(joueur.getBackPack().contains3Key()){
                Main.VICTOIRE = true;
            }else{
                joueur.getLifeManager().removeLife();
                if(joueur.getLifeManager().getNbVies()==0){
                    Main.DEFAITE = true;
                }
            }
        }
    }
}
