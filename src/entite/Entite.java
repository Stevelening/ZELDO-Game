package entite;

import main.GraphiqueInterface;
import other.Position;
import java.awt.*;
import other.*;

public abstract class Entite extends Components{

    protected Position pos;
    public Entite(Position pos){
        this.pos = pos;
    }

    public void move(Position.Direction dir){
        switch (dir) {
            case UP:
                pos.goUp();
                break;
            case DOWN:
                pos.goDown();
                break;
            case LEFT:
                pos.goLeft();
                break;
            case RIGHT:
                pos.goRight();
                break;
        
            default:
                break;
        }
    }
    
    public Position getPosition(){
        return this.pos;
    }

    public void setPosition(Position pos){
        this.pos = pos;
    }

}
