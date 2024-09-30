package objects;

import main.GraphiqueInterface;
import java.awt.*;
import other.*;

public abstract class Items extends Components{

    protected Position pos;
    private Boolean selected = false;
    public enum Type{
        CLE, EPEE, POTION
    }

    public abstract void affichage(Graphics g, Position pos, GraphiqueInterface ig);

    public Position getPosition(){
        return this.pos;
    }

    public void setPosition(Position p){
        this.pos = p;
    }

    public Items(Position pos){
        this.pos = pos;
    }

    public void selectThisObj(){
        this.selected = true;
    }

    public void dontSelectThisObj(){
        this.selected = false;
    }

    public Boolean isSelected(){
        return this.selected;
    }
}
