package player_interraction;

import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Random;

import entite.Entite;
import other.Position;
import tuiles.Murs;
import map.*;

public class MechantInterraction {
    public void move(ArrayList<Entite> mechants, Room actual){
        Random rand = new Random();
        Position.Direction[] dir = {Position.Direction.LEFT, Position.Direction.RIGHT, Position.Direction.UP, Position.Direction.DOWN};

        for (Entite e : mechants) {
            Position ePos = e.getPosition();
            switch (dir[rand.nextInt(0, 3)]) {
                case LEFT:
                    if (ePos.getX()-1 > 0)
                        ePos.setPosition(ePos.getX()-1, ePos.getY());
                    break;
                case RIGHT:
                    if (ePos.getX()+1 < Room.WIDTH-1)
                        ePos.setPosition(ePos.getX()+1, ePos.getY());
                    break;
                case UP:
                    if (ePos.getY()-1 > 0)
                        ePos.setPosition(ePos.getX(), ePos.getY()-1);
                    break;
                case DOWN:
                    if (ePos.getY()+1 < Room.HEIGHT-1)
                        ePos.setPosition(ePos.getX(), ePos.getY()+1);
                    break;
                default:
                    break;
            }
        }
    }
}
