
/**
* This the main App class from which Waka game is run
*
* @author  Rahul Talla
* @version 1.0
* @since   2020-11-20 
*/

package ghost;

import processing.core.PApplet;
import processing.core.PImage;


public class App extends PApplet {

    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;
    protected Map map;
    private Waka waka = new Waka();
    private Waka.WakaClosed wakaClosed;
    private PImage lose;
    private PImage win;
    private int frameCount;
    private boolean restart;
    
     public App() {
        this.map = new Map(this);
    }
    
    /**
     * Called once loads all resuroces for app functioning 
    */
    public void setup() {
        frameRate(60);
        this.map.mapParse("config.json");
        this.map.setup();
        this.wakaClosed =   this.waka.new WakaClosed(this.loadImage("src/main/resources/playerClosed.png"));
        this.lose =   this.loadImage("src/main/resources/gameover.png");
        this.win = this.loadImage("src/main/resources/win.png");
        this.map.fruitCount();
        this.map.wakaStartPos();
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Calls map's draw method and handles game state
    */
    public void draw() {

        background(0, 0, 0);
        restartGame();

        if (map.fruitAvailable == map.fruitTot && this.map.move != null) {
            this.win.resize(112*2, 16*2);
            this.image(this.win, 110, 230);
            this.restart = true;
        }

        else if (map.wakaLivesLeft == 0) {
            this.lose.resize(112*2, 16*2);
            this.image(this.lose, 110, 230);
            this.restart = true;        
        }

        else if (this.frameCount == 8) {
            this.map.draw();
            int[] wakaCoord = this.map.wakaCoord();
            this.wakaClosed.draw(wakaCoord[0]*16-(24-16)/2, wakaCoord[1]*16-((26-16)/2), this);
            frameCount = 0;
        }

        else {
            this.map.draw();
            this.frameCount +=1;
        }

    }
        
    /**
     * Checks restart flag and resets Map variable
     * with new Map instance and calls setup method
     */
    public void restartGame() {
        if (this.restart) {
            this.delay(10000);
            this.map = new Map(this);
            this.setup();
            this.restart = false;
        }
    }

    /**
     * This method is used to collect move input and pass
     * it to map's move method
     */
    public void keyPressed() {

        if (key == ' ') {
            if (this.map.debugMode) {
                this.map.debugMode = false;
            }
            else {
                this.map.debugMode = true;
            }
            
        }
        else if (key == CODED) {
            if (keyCode == UP) {
                this.map.move("up");
                
            }
            else if (keyCode == DOWN) {
                this.map.move("down");
                
            }
            else if (keyCode == RIGHT) {
                this.map.move("right");
                
            }
            else if (keyCode == LEFT) {
                this.map.move("left");
                
            }

        }
    }
    
    
    public static void main(String[] args) {
        PApplet.main("ghost.App");

    }
    
}
