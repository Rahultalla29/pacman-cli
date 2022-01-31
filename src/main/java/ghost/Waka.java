package ghost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Waka extends Cells {
    /**
    * Determines whether fruit is available based on next move 
    * and if true update fruit count. Checks for superfruit and sodaCan to set boolean states
    * @param map Map instance
    */
    public void isFruit(Map map) {

        List<String> possibleCells = new ArrayList<String>(Arrays.asList("7","8","s"));
        if (map.move.equals("down")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY+1).get(map.wakaX))) {
                map.fruitAvailable += 1;
                if (map.mapLines.get(map.wakaY+1).get(map.wakaX).equals("8")) {
                    map.frightenMode = true;
                }
                else if (map.mapLines.get(map.wakaY+1).get(map.wakaX).equals("s")) {
                    map.ghostInvisible = true;
                }
            }
        }

        else if (map.move.equals("right")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY).get(map.wakaX+1))) {
                map.fruitAvailable += 1;
                if (map.mapLines.get(map.wakaY).get(map.wakaX+1).equals("8")) {
                    map.frightenMode = true;
                }
                else if (map.mapLines.get(map.wakaY).get(map.wakaX+1).equals("s")) {
                    map.ghostInvisible = true;
                }
            }

        } else if (map.move.equals("left")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY).get(map.wakaX-1))) {
                map.fruitAvailable += 1;
                if (map.mapLines.get(map.wakaY).get(map.wakaX-1).equals("8")) {
                    map.frightenMode = true;
                }
                else if (map.mapLines.get(map.wakaY).get(map.wakaX-1).equals("s")) {
                    map.ghostInvisible = true;
                }
            }
        }

        else if (map.move.equals("up")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY-1).get(map.wakaX))) {
                map.fruitAvailable += 1;
                if (map.mapLines.get(map.wakaY-1).get(map.wakaX).equals("8")) {
                    map.frightenMode = true;
                }
                else if (map.mapLines.get(map.wakaY-1).get(map.wakaX).equals("s")) {
                    map.ghostInvisible = true;
                }
            }
        }   
    }
    /**
    * Determines waka move based on possible move and updates waka X and Y coordinates
    * @param map Map instance
    */
   
    public void wakaMoves(Map map) {
        
        List<String> possibleCells = new ArrayList<String>(Arrays.asList("7","8","0","s"));
        
        if (map.move != null) {
            map.restoreGame = false;
        }
        

        if (map.move == null) {}

        else if (map.move.equals("down")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY+1).get(map.wakaX)) ) {
                isFruit(map);
                map.mapLines.get(map.wakaY).set(map.wakaX, "0");
                map.wakaY +=1;
            }
        }
        
        else if (map.move.equals("right")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY).get(map.wakaX+1))) {
                isFruit(map);
                map.mapLines.get(map.wakaY).set(map.wakaX, "0");
                map.wakaX +=1;
            }
        }
        else if (map.move.equals("left")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY).get(map.wakaX-1))) {
                isFruit(map);
                map.mapLines.get(map.wakaY).set(map.wakaX, "0");
                map.wakaX -=1;
            }
        }

        else if (map.move.equals("up")) {
            if (possibleCells.contains(map.mapLines.get(map.wakaY-1).get(map.wakaX))) {
                isFruit(map);
                map.mapLines.get(map.wakaY).set(map.wakaX, "0");
                map.wakaY -=1;
            }
        }   
    }

    class WakaLeft extends Waka {

        PImage sprite;
    
        public WakaLeft(PImage sprite) {
            this.sprite = sprite;
        }

        public void draw(int x, int y,PApplet app) { 
            app.image(this.sprite, x, y);
        }
    }

    class WakaClosed extends Waka {

        PImage sprite;
    
        public WakaClosed(PImage sprite) {
            this.sprite = sprite;
        }

        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }

    class WakaRight extends Waka {

        PImage sprite;
    
        public WakaRight(PImage sprite) {
             this.sprite = sprite;
        }

        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }

    class WakaUp extends Waka {
        PImage sprite;
    
        public WakaUp(PImage sprite) {
            this.sprite = sprite;
        }
   
        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }
    class WakaDown extends Waka {

        PImage sprite;
    
        public WakaDown(PImage sprite) {
            this.sprite = sprite;
        }
    
        public void draw(int x, int y,PApplet app) {
            app.image(this.sprite, x, y);
        }
    }
}