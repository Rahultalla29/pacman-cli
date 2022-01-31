package ghost;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import processing.core.PApplet;

public class Map {

    protected Ghost ghosts = new Ghost();
    protected Ghost.Ambusher ambusher;
    protected Ghost.Chaser chaser;
    protected Ghost.Ignorant ignorant;
    protected Ghost.Whim whim;
    protected Ghost.FrightenGhost frightenGhost;

    protected Waka waka = new Waka();
    protected Waka.WakaLeft wakaLeft;
    protected Waka.WakaRight wakaRight;
    protected Waka.WakaUp wakaUp;
    protected Waka.WakaDown wakaDown;
    protected SodaCan sodaCan;

    protected HWall hWall;
    protected VWall vWall;
    protected CWallDL cWallDL;
    protected CWallDR cWallDR;
    protected CWallUL cWallUL;
    protected CWallUR cWallUR;

    protected Fruit fruit;
    protected SuperFruit superFruit;

    protected PApplet app;
    protected String move;
    protected int wakaX;
    protected int wakaY;
    protected int wakaStartingX;
    protected int wakaStartingY;
    protected int xPixel;
    protected int yPixel;
    protected String lastMove;
    protected ChaseMode chase;
    protected ScatterMode scatter;
    protected boolean restoreGame;
    protected boolean debugMode;
    protected List<ScatterBehaviour> ghostsListCurrentScatter = new ArrayList<ScatterBehaviour>();
    protected List<ChaseBehaviour> ghostsListCurrentChase = new ArrayList<ChaseBehaviour>();
    protected List<ScatterBehaviour> ghostsListInitial = new ArrayList<ScatterBehaviour>();
    protected LinkedHashMap<ChaseBehaviour, Integer> ghostsRemovedChase = new LinkedHashMap<ChaseBehaviour, Integer>();
    protected LinkedHashMap<ScatterBehaviour, Integer> ghostsRemovedScatter = new LinkedHashMap<ScatterBehaviour, Integer>();
    protected List<Integer> ghostsRemoved = new ArrayList<Integer>();

    protected int fruitTot;
    protected int fruitAvailable;
    protected long wakaLivesLeft;
    protected long frightenedLength;
    protected List<Long> modeLengths;
    protected Long speed;

    private Parser parse = new Parser();
    protected List<List<String>> mapLines;
    private int framePerSecond = 0;
    private int time = 0;
    private int frightenTime = 0;
    private Long currentModeLength = (long) 0;
    protected LinkedHashMap<int[], String> ghostPos = new LinkedHashMap<int[], String>();
    protected boolean frightenMode;
    protected boolean alternateGhosts = true;
    protected boolean wakaKillGhost;
    protected boolean ghostInvisible;

    public Map(PApplet app) {
        this.app = app;

    }

    /**
    * Parses map config from App
    * @param filename - name of config file
    */ 
    public void mapParse(String filename){
        this.parse.jsonReader(filename);
    }

    /**
    * Loads all resources
    */ 
    public void setup() {
    
        this.sodaCan = new SodaCan(this.app.loadImage("src/main/resources/sodaCan.png"));
        this.fruit = new Fruit(this.app.loadImage("src/main/resources/fruit.png"));
        this.superFruit = new SuperFruit(this.app.loadImage("src/main/resources/superFruit.png"));
        this.superFruit.sprite.resize(32, 32);
        this.chaser = this.ghosts.new Chaser(this.app.loadImage("src/main/resources/chaser.png"));
        this.ambusher = this.ghosts.new Ambusher(this.app.loadImage("src/main/resources/ambusher.png"));
        this.ignorant = this.ghosts.new Ignorant(this.app.loadImage("src/main/resources/ignorant.png"));
        this.whim = this.ghosts.new Whim(this.app.loadImage("src/main/resources/whim.png"));
        this.frightenGhost = this.ghosts.new FrightenGhost(this.app.loadImage("src/main/resources/frightened.png"));
        this.hWall = new HWall(this.app.loadImage("src/main/resources/horizontal.png"));
        this.vWall = new VWall(this.app.loadImage("src/main/resources/vertical.png"));
        this.cWallDL = new CWallDL(this.app.loadImage("src/main/resources/downLeft.png"));
        this.cWallDR = new CWallDR(this.app.loadImage("src/main/resources/downRight.png"));
        this.cWallUL = new CWallUL(this.app.loadImage("src/main/resources/upLeft.png"));
        this.cWallUR = new CWallUR(this.app.loadImage("src/main/resources/upRight.png"));
        this.wakaLeft = this.waka.new WakaLeft(this.app.loadImage("src/main/resources/playerLeft.png"));
        this.wakaRight = this.waka.new WakaRight(this.app.loadImage("src/main/resources/playerRight.png"));
        this.wakaUp = this.waka.new WakaUp(this.app.loadImage("src/main/resources/playerUp.png"));
        this.wakaDown = this.waka.new WakaDown(this.app.loadImage("src/main/resources/playerDown.png"));
        this.mapLines = this.parse.mapReadLines(this.parse.getFilename());
        this.speed = parse.getSpeed();
        this.wakaLivesLeft = this.parse.getLives();
        this.modeLengths = this.parse.getModelengths();
        this.frightenedLength = this.parse.getfrightenedLength();
        this.chase = new ChaseMode();
        this.scatter = new ScatterMode();
        this.scatter.ScatterModeInitialise(this);
        initialGhostPos(this);
    }
    /**
    * Main draw method - Updates WakaLives based on gameState, draws all cells and updates/controls what it will draw.
    */ 
    public void draw() {

        wakaLives();
        this.app.text("Fruit: " + Integer.toString(this.fruitAvailable) + " / " + Integer.toString(this.fruitTot), 160,
                30);
        this.app.textSize(15);
        this.waka.wakaMoves(this);
        wakaType();
        restoreGame();
        inFrightenedMode();
        initialiseChaseMode();
        initialiseScatterMode();

        for (int i = 0; i < this.mapLines.size(); i++) { 
            List<String> lsInMapLines = this.mapLines.get(i);
            for (int k = 0; k < lsInMapLines.size(); k++) { 

                if (lsInMapLines.get(k).equals("1")) {
                    this.app.image(this.hWall.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("2")) {
                    this.app.image(this.vWall.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("3")) {
                    this.app.image(this.cWallUL.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("4")) {
                    this.app.image(this.cWallUR.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("5")) {
                    this.app.image(this.cWallDL.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("6")) {
                    this.app.image(this.cWallDR.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("g") || lsInMapLines.get(k).equals("c")) {
                    this.app.image(this.chaser.sprite, ((k * 16) - (28 - 16) / 2), ((i * 16) - (28 - 16) / 2));
                } else if (lsInMapLines.get(k).equals("a")) {
                    this.app.image(this.ambusher.sprite, ((k * 16) - (28 - 16) / 2), ((i * 16) - (28 - 16) / 2));
                } else if (lsInMapLines.get(k).equals("i")) {
                    this.app.image(this.ignorant.sprite, ((k * 16) - (28 - 16) / 2), ((i * 16) - (28 - 16) / 2));
                } else if (lsInMapLines.get(k).equals("w")) {
                    this.app.image(this.whim.sprite, ((k * 16) - (28 - 16) / 2), ((i * 16) - (28 - 16) / 2));
                } else if (lsInMapLines.get(k).equals("p")) {
                    this.wakaX = k;
                    this.wakaY = i;
                } else if (lsInMapLines.get(k).equals("7")) {
                    this.app.image(this.fruit.sprite, k * 16, i * 16);
                } else if (lsInMapLines.get(k).equals("8")) {
                    this.app.image(this.superFruit.sprite, ((k * 16) - (32 - 16) / 2), ((i * 16) - (32 - 16) / 2));
                }
                else if (lsInMapLines.get(k).equals("s")) {
                    this.app.image(this.sodaCan.sprite, k*16,i*16);
                }
            }
        }
        this.framePerSecond += 1;
        this.app.delay((int) (120 / this.speed));
    }

    /**
    * Records move input and updates the lastMove and move of the Map instance
    * @param move input move
    */ 
    public void move(String move) {     
        this.lastMove = this.move;
        this.move = move;
    }

    public int i = 0;
    public int mode = 0;
    public boolean scatterModeInitialised;
    public boolean chaseModeInitialised;

    /**
    * Called once at intialistion of Map instance to hold ghost starting positions
    * @param map Map instance
    */ 
    public void initialGhostPos(Map map) {
        if (map == null) {
            return;
        }
        for (int i = 0; i < map.mapLines.size(); i++) {
            List<String> lsInMapLines = map.mapLines.get(i);
            for (int k = 0; k < lsInMapLines.size(); k++) { 

                if (lsInMapLines.get(k).equals("g") || lsInMapLines.get(k).equals("c")) {
                    ghostPos.put(new int[] { k, i }, "chaser");
                } else if (lsInMapLines.get(k).equals("a")) {
                    ghostPos.put(new int[] { k, i }, "ambusher");
                } else if (lsInMapLines.get(k).equals("i")) {
                    ghostPos.put(new int[] { k, i }, "ignorant");
                } else if (lsInMapLines.get(k).equals("w")) {
                    ghostPos.put(new int[] { k, i }, "whim");
                }
            }
        }
    }

    int ghostIndex = 0;

    /**
    * Check Boolean state of restoreGame and mode to 
    * reset ghost positions to coordinates stored in ghostPos of Map instance
    */ 
    public void restoreGame() {

        if (this.restoreGame && this.mode == 0) {
            
            if (ghostsListCurrentScatter.size() >= ghostIndex) {
                ghostIndex = 0;
            }
            
            for (int[] elem : ghostPos.keySet()) {
                ghostsListCurrentScatter.get(ghostIndex).setX(elem[0]);
                ghostsListCurrentScatter.get(ghostIndex).setY(elem[1]);
                ghostIndex += 1;
            }

        } else if (this.restoreGame && this.mode == 1) {
            
            if (ghostsListCurrentChase.size() >= ghostIndex) {
                ghostIndex = 0;
            }
            
            for (int[] elem : this.ghostPos.keySet()) {
                this.ghostsListCurrentChase.get(ghostIndex).setX(elem[0]);
                this.ghostsListCurrentChase.get(ghostIndex).setY(elem[1]);
                ghostIndex += 1;
            }
        }
    }
    /**
    * Check Boolean state of chaseModeInitialised
    * calls chaseModeIntialise to add Chase Ghosts for Chase Mode
    */ 
    public void initialiseChaseMode() {
        try {
            if (this.chaseModeInitialised) {
                this.chase.ChaseModeInitialise(this);
                this.chaseModeInitialised = false;
            }
        }catch (NullPointerException e) {
            return;
        }


    }
    /**
    * Check Boolean state of scatterModeInitialised
    * calls chaseModeIntialise to add Chase Ghosts for Chase Mode
    */ 
    public void initialiseScatterMode() {
        try {
            if (this.scatterModeInitialised) {
                this.scatter.ScatterModeReinitialise(this);
                this.scatterModeInitialised = false;
            }
        }catch (NullPointerException e) {
            return;
        }

    }

    /**
    * Check Boolean state of wakaKillGhost and size of ghostsRemoved 
    * to remove ghosts form the Scatter or Chase ghosts list when waka hits ghost in frighten mode
    * @param mode mode currently in (Scatter or Chase)
    */ 
    public void removeDeadGhost(int mode) {
        if (this.ghostsRemoved.size() == 0) {
            return;

        }
        else if (mode == 0) {
            if (this.wakaKillGhost) {
                for (int index : this.ghostsRemoved) {
                    this.ghostsRemovedScatter.put(this.ghostsListCurrentScatter.get(index), index);
                    this.ghostsListCurrentScatter.remove(index);
                }
                this.ghostsRemoved.clear();
                this.wakaKillGhost = false;
            }
        }

        else if (mode == 1) {
            if (this.wakaKillGhost) {
                for (int index : this.ghostsRemoved) {
                    this.ghostsRemovedChase.put(this.ghostsListCurrentChase.get(index), index);
                    this.ghostsListCurrentChase.remove(index);
                }
                this.ghostsRemoved.clear();
                this.wakaKillGhost = false;
            }
        }
        else {
            return;
        }
    }

    /**
    * Check mode and restore ghosts form removed ghosts list 
    * back to the Scatter or Chase lists of the Map instance 
    */ 
    public void restoreGhosts() {

        if (this.mode == 0) {
            if ( this.ghostsListCurrentScatter.size() < this.ghostPos.size()) {
                for (Entry<ScatterBehaviour, Integer> elem : this.ghostsRemovedScatter.entrySet()) {
                    if (elem.getValue() > this.ghostsListCurrentScatter.size()) {
                        this.ghostsListCurrentScatter.add(elem.getKey());
                    }
                    else {
                        this.ghostsListCurrentScatter.add(elem.getValue(), elem.getKey());
                    }
                }
            }
        }

        else {
            if ( this.ghostsListCurrentChase.size() < this.ghostPos.size()) {
                for (Entry<ChaseBehaviour, Integer> elem : this.ghostsRemovedChase.entrySet()) {
                    if (elem.getValue() > this.ghostsListCurrentChase.size()) {
                        this.ghostsListCurrentChase.add(elem.getKey());
                    }
                    else {
                        this.ghostsListCurrentChase.add(elem.getValue(), elem.getKey());
                    }
                }
            }
        }
    }
    /**
    * Seperate timer method controls setting ghost drawing 
    * state to frighten for some period of time
    */ 
    public void inFrightenedMode() {


        if (this.frightenMode || this.ghostInvisible ) {

            if (this.framePerSecond == 60) {
                this.framePerSecond = 0;
                this.frightenTime += 1;
            }

            if (this.frightenTime < this.frightenedLength) {

                if (this.mode == 0) {                   
                    removeDeadGhost(0);
                    this.scatter.ScatterModeActivate(this);

                } else if (this.mode == 1) {
                    removeDeadGhost(1);
                    this.chase.ChaseModeActivate(this);
                }

            } else {
                if (this.frightenMode) {
                    this.frightenMode = false;
                }

                else if (this.ghostInvisible) {
                    this.ghostInvisible = false;
                }
              
                this.frightenTime = 0;
                restoreGhosts();
            } 
        }
        else {
            ModeTransfer();
        }
    }
    
    /**
    * Timer method controls ghost state
    * for mode lengths specified in configuration
    */
    public void ModeTransfer() {
        if (this.i >= this.modeLengths.size()) {
            this.i = 0;
        }

        this.currentModeLength = this.modeLengths.get(this.i);
        if (this.framePerSecond == 60) {
            this.framePerSecond = 0;
            this.time += 1;
        }
        
        while (this.i< this.modeLengths.size()) {
     
            if (this.time < this.currentModeLength) {

                if (this.mode == 0) {
                    this.scatter.ScatterModeActivate(this);
                    break;
                }

                else if (this.mode == 1) {
                    this.chase.ChaseModeActivate(this);
                    break;
                }
            }

            else {
                if (this.mode == 0) {
                    this.mode = 1;
                    this.chaseModeInitialised = true;
                }

                else {
                    this.mode = 0;
                    this.scatterModeInitialised = true;
                }
                this.i += 1;
                this.time = 0;
                break;
            }
        }
    }
    /**
    * Draw Method for UI element indicating waka lives
    */
    public void wakaLives() { 
        int i = 0;
        while (i < this.wakaLivesLeft) {
            this.app.image(this.wakaRight.sprite, 5 + i * 30, 545);
            i += 1;
        }
    }
    /**
    * Returns Current Waka X and Y Coordinates
    * @return wakaCoord waka coordinates
    */
    public int[] wakaCoord() {
        int[] wakaCoord = new int[2];
        wakaCoord[0] = this.wakaX;
        wakaCoord[1] = this.wakaY;
        return wakaCoord;
    }

    /**
    * Returns Waka X adjusted Pixel Coordinate with speed adjusted
    * @param dimension width
    * @return wakaX waka pixel coordinate
    */
    public int wakaPixelX(int dimension) {
        return ((this.wakaX * 16 + this.xPixel)-(dimension-16)/2);
    }

    /**
    * Returns Waka Y adjusted Pixel Coordinate with speed adjusted
    * @param dimension height
    * @return wakaY waka pixel coordinate
    */
    public int wakaPixelY(int dimension) {
        return ((this.wakaY * 16 + this.yPixel)-(dimension-16)/2);
    }
    /**
    * Draw Waka based on move of Map instance
    */
    public void wakaType() {
        
        if (this.move == null) {
            this.app.image(this.wakaLeft.sprite, wakaPixelX(24), wakaPixelY(26));
        } else if (this.move.equals("up")) {
            this.app.image(this.wakaUp.sprite, wakaPixelX(26), wakaPixelY(24));
        } else if (this.move.equals("down")) {
            this.app.image(this.wakaDown.sprite, wakaPixelX(26), wakaPixelY(24));
        } else if (this.move.equals("right")) {
            this.app.image(this.wakaRight.sprite, wakaPixelX(24), wakaPixelY(26));
        } else if (this.move.equals("left")) {
            this.app.image(this.wakaLeft.sprite, wakaPixelX(24), wakaPixelY(26));
        }
    }
    /**
    * Called once in App instance, sets Waka starting X and Y coordinates
    */
    public void wakaStartPos() {

        for (int i = 0; i < this.mapLines.size(); i++) { 
            List<String> lsInMapLines = this.mapLines.get(i);
            for (int k = 0; k < lsInMapLines.size(); k++) { 
                if (lsInMapLines.get(k).equals("p")) {
                    this.wakaX = k;
                    this.wakaY = i;
                    this.wakaStartingX = k;
                    this.wakaStartingY = i;
                }
            }
        } 
    }
    /**
    * Called once in App instance, sets total fruit amount
    */
    public void fruitCount() {

        for (int i=0; i<this.mapLines.size(); i++) { 
            List<String> lsInMapLines = this.mapLines.get(i);
            for (int k=0; k <lsInMapLines.size(); k++) { 
                if (lsInMapLines.get(k).equals("7") || lsInMapLines.get(k).equals("8") ) {
                    this.fruitTot += 1;
                }
            }
        } 
             
    }
}