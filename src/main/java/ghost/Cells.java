package ghost;


import processing.core.PApplet;
import processing.core.PImage;

/**
* Cells class holds all general cells types and their
* draw methods 
*/
public class Cells {
    PImage sprite;

    /**
    * Draws sprite to app screen
    * @param x cell x-coordinate
    * @param y cell y-coordinate
    * @param app App class instance
    */
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
    
}
class SodaCan extends Cells {

    PImage sprite;

    public SodaCan(PImage sprite) {
        this.sprite = sprite;
    }
    
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
    
}

class SuperFruit extends Cells {
    PImage sprite;

    public SuperFruit(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
    
}
class Fruit extends Cells {
    PImage sprite;

    public Fruit(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
    
}

class HWall extends Cells {
    PImage sprite;

    public HWall(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
} 

class VWall extends Cells {
    PImage sprite;

    public VWall(PImage sprite) {
        this.sprite = sprite;
    }

    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
} 

class CWallUR extends Cells {
    PImage sprite;

    public CWallUR(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
} 

class CWallUL extends Cells {
    PImage sprite;

    public CWallUL(PImage sprite) {
        this.sprite = sprite;
    }

    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
} 

class CWallDR extends Cells {
    PImage sprite;

    public CWallDR(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
} 

class CWallDL extends Cells {
    PImage sprite;

    public CWallDL(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(int x, int y,PApplet app) {
        app.image(this.sprite, x, y);
    }
} 

