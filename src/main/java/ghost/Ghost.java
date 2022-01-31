package ghost;

import processing.core.PApplet;
import processing.core.PImage;

public class Ghost extends Cells {

    class Ambusher extends Ghost {

        PImage sprite;
    
        public Ambusher(PImage sprite) {
            this.sprite = sprite;
        }
        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }
    
    class Chaser extends Ghost {

        PImage sprite;
    
        public Chaser(PImage sprite) {
            this.sprite = sprite;
        }
        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }

    class Ignorant extends Ghost {

        PImage sprite;
    
        public Ignorant(PImage sprite) {
            this.sprite = sprite;
        }
    
        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }

    class Whim extends Ghost {

        PImage sprite;
    
        public Whim(PImage sprite) {
            this.sprite = sprite;
        }

        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }

    class FrightenGhost extends Ghost {

        PImage sprite;
    
        public FrightenGhost(PImage sprite) {
             this.sprite = sprite;
        }

        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }
}






