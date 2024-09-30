package other;

public class Position {
    private int x;
    private int y;

    public enum Direction{
        UP, DOWN, LEFT, RIGHT
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void goUp(){
        this.y = y-1;
    }

    public void goDown(){
        this.y = y+1;
    }

    public void goLeft(){
        this.x = x-1;
    }

    public void goRight(){
        this.x = x+1;
    }
}
