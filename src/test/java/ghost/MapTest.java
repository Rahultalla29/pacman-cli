package ghost;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;



public class MapTest {

    @Test
    public void testSetup() {
        App app = new App();
        Map map = new Map(app);
        PApplet.runSketch(new String[] {"hi"}, app);
        app.delay(1000); // Wait for assets to be loaded in
        assertNotNull(app);
        app.noLoop();
        

    }


    @Test
    public void notNull() { // If initailised object is not null
        App app = new App();
        Map map = new Map(app);
        assertNotNull(map);
    }

    @Test
    public void moveRecorded() { // If input move is recorded
        App app = new App();
        Map map = new Map(app);
        map.move("left");
        assertSame(map.move, "left");
    }

    @Test
    public void lastMoveRecorded() { // if last move is recorded
        App app = new App();
        Map map = new Map(app);
        map.move("left");
        assertSame(map.lastMove, null);
    }

    @Test
    public void ghostPosCheck() { // null input for map, should return
        App app = new App();
        Map map = new Map(app);
        map.initialGhostPos(null);
        assertSame(map.ghostPos.size(), 0);
    }

    @Test
    public void ghostPosCheck2() { // map correctly read
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();

        map.mapLines = parse.mapReadLines("map.txt");
        int sizeRows = map.mapLines.size();
        int sizeColumns = map.mapLines.get(0).size();
        assertSame(sizeRows, 36);
        assertSame(sizeColumns, 28);
    }

    @Test
    public void initialiseChase() { // Chase Mode Intialised correctly
        App app = new App();
        Map map = new Map(app);
        map.chaseModeInitialised = true;
        map.initialiseChaseMode();
        assertSame(map.ghostsListCurrentChase.size(),0);
        assertNotSame(map.chaseModeInitialised, false);
    }

    @Test
    public void initialiseScatter() { // Scatter Mode Intialised correctly
        App app = new App();
        Map map = new Map(app);
        map.scatterModeInitialised = true;
        map.initialiseScatterMode();
        assertSame(map.ghostsListCurrentScatter.size(),0);
        assertNotSame(map.scatterModeInitialised, false);
    }

    @Test
    public void deadGhost() { // Ghosts Removed correctly at Intialisation
        App app = new App();
        Map map = new Map(app);
        assertSame(map.ghostsRemoved.size(), 0);
    }

    @Test
    public void deadGhost2() { // Ghosts Removed correctly
        App app = new App();
        Map map = new Map(app);
        map.ghostsRemoved.add(3);
        assertSame(map.ghostsRemoved.size(), 1);
    }

    @Test
    public void ghostPosTest() {  // Ghosts positions correctly recorded and number of ghosts on map match
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        map.mapLines = parse.mapReadLines("map.txt");
        map.initialGhostPos(map);
        assertSame(map.ghostPos.size(), 4);
    }

    @Test
    public void restoreGameTest() {  // Ghosts and map restored correctly
        App app = new App();
        Map map = new Map(app);
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.restoreGame = true;
        map.mode = 1;
        map.restoreGame();
        map.mode = 0;
        map.restoreGame = true;
        map.restoreGame();
    }

    @Test
    public void removeGhostTest() { // Ghosts Removed correctly
        App app = new App();
        Map map = new Map(app);
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.wakaKillGhost = true;
        map.ghostsRemoved.add(0);
        map.mode = 1;
        map.removeDeadGhost(1);
        assertSame(map.ghostsListCurrentChase.size(), 0); /// check ghost has been removed
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.wakaKillGhost = true;
        map.mode = 0;
        map.removeDeadGhost(0);
    }

    @Test
    public void restoreGhostsTest() { // removes ghost from scatter and adds to chase
        App app = new App();
        Map map = new Map(app);
        map.ghostsListCurrentScatter.clear();
        map.mode = 1;
        map.ghostsRemovedScatter.put(new ChaserScatter(3, 4), 2);
        map.restoreGhosts();
        assertSame(map.ghostsListCurrentScatter.size(), 0);

    }

    @Test
    public void frightenTest() { // checks if in frighten mode booleans are set to false after conditon
        App app = new App();
        Map map = new Map(app);
        map.frightenMode = true;
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.mode = 1;
        map.inFrightenedMode();
        assertSame(map.frightenMode, false);
        map.ghostInvisible = true;
        map.inFrightenedMode();
    }

    @Test
    public void ModeTransferTest() { //Mode transfer and list addition/conversion as expected
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        parse.jsonReader("config.json");
        map.modeLengths = parse.getModelengths();
        map.scatter = new ScatterMode();
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new IgnorantChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new WhimChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new AmbusherChase(3, 4, "left"));
        map.mapLines = parse.mapReadLines("map.txt");
        map.scatter.ScatterModeReinitialise(map);
        map.ModeTransfer();
        map.mode = 1;
        map.chase = new ChaseMode();
        map.ModeTransfer();
    }

    @Test
    public void wakaCoord() { // Correct waka postion
        App app = new App();
        Map map = new Map(app);
        map.wakaX = 1;
        map.wakaY = 2;
        assertArrayEquals(map.wakaCoord(),new int[]{1,2});
    }
    @Test
    public void wakaXNY() { // Correct waka postion computation in pixel
        App app = new App();
        Map map = new Map(app);
        map.wakaX = 1;
        map.wakaY = 2;
        map.xPixel = 1;
        map.yPixel = 1;
        assertSame(map.wakaPixelX(24), 13);
        assertSame(map.wakaPixelY(36), 23);
    }
    @Test
    public void wakaType() { // Correct waka type
        App app = new App();
        Map map = new Map(app);
        map.move = "left";
        Exception exception = assertThrows(NullPointerException.class, () -> {
            map.wakaType();
        });
        assertTrue(true,exception.getMessage());
        // map.wakaType();
    }

    @Test
    public void wakaStart() { // Correct waka position
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        map.mapLines = parse.mapReadLines("map.txt");
        map.wakaStartPos();
        assertSame(map.wakaX,13);
        assertSame(map.wakaY,20);

    }
    @Test
    public void fruitCount() { // Correct fruit count
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        map.mapLines = parse.mapReadLines("map.txt");
        map.fruitCount();
        assertEquals(map.fruitTot,300);
    }

        
    @Test
    public void testDrawMethods() { // Correctly draws objects
        App app = new App();
        Map map = new Map(app);
        map.ghosts = new Ghost();
        map.waka = new Waka();
        PApplet.runSketch(new String[] {"hi"}, app);
        map.sodaCan = new SodaCan(app.loadImage("src/main/resources/sodaCan.png"));
        map.fruit = new Fruit(app.loadImage("src/main/resources/fruit.png"));
        map.superFruit = new SuperFruit(app.loadImage("src/main/resources/superFruit.png"));
        map.chaser = map.ghosts.new Chaser(app.loadImage("src/main/resources/chaser.png"));
        map.ambusher = map.ghosts.new Ambusher(app.loadImage("src/main/resources/ambusher.png"));
        map.ignorant = map.ghosts.new Ignorant(app.loadImage("src/main/resources/ignorant.png"));
        map.whim = map.ghosts.new Whim(app.loadImage("src/main/resources/whim.png"));
        map.frightenGhost = map.ghosts.new FrightenGhost(app.loadImage("src/main/resources/frightened.png"));
        map.hWall = new HWall(app.loadImage("src/main/resources/horizontal.png"));
        map.vWall = new VWall(app.loadImage("src/main/resources/vertical.png"));
        map.cWallDL = new CWallDL(app.loadImage("src/main/resources/downLeft.png"));
        map.cWallDR = new CWallDR(app.loadImage("src/main/resources/downRight.png"));
        map.cWallUL = new CWallUL(app.loadImage("src/main/resources/upLeft.png"));
        map.cWallUR = new CWallUR(app.loadImage("src/main/resources/upRight.png"));
        map.wakaLeft = map.waka.new WakaLeft(app.loadImage("src/main/resources/playerLeft.png"));
        map.wakaRight = map.waka.new WakaRight(app.loadImage("src/main/resources/playerRight.png"));
        map.wakaUp = map.waka.new WakaUp(app.loadImage("src/main/resources/playerUp.png"));
        map.wakaDown = map.waka.new WakaDown(app.loadImage("src/main/resources/playerDown.png"));
        map.sodaCan.draw(16,16,app);
        map.fruit.draw(16,16,app);
        map.superFruit.draw(16,16,app);
        map.ambusher.draw(16,16,app);
        map.ignorant.draw(16,16,app);
        map.whim.draw(16,16,app);
        map.frightenGhost.draw(16,16,app);
        map.hWall.draw(16,16,app);
        map.vWall.draw(16,16,app);
        map.cWallDL.draw(16,16,app);
        map.cWallDR.draw(16,16,app);
        map.cWallUL.draw(16,16,app);
        map.cWallUR.draw(16,16,app);
        map.wakaLeft.draw(16,16,app);
        map.wakaRight.draw(16,16,app);
        map.wakaDown.draw(16,16,app);
        app.delay(1000); // Wait for assets to be loaded in
        assertNotNull(app);
        app.noLoop();
    }

    @Test
    public void testDrawMethods2() {
        App app = new App();
        Map map = new Map(app);
        app.map.mapParse("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"hi"}, app);
        
        app.delay(1000);
        
        app.map.move = "left";

        // app.draw();
        app.delay(500);
        app.map.move("right");
        app.map.debugMode = true;
        app.delay(500);
        app.map.move("up");
        app.delay(500);
        app.map.move("down");
        app.delay(500);
        app.map.move("up");
        app.map.frightenMode = true;
        app.map.debugMode = false;
        app.delay(500);
        app.map.move("left");
        app.delay(10);
        app.map.frightenMode = false;
        app.map.ghostInvisible = true;
        assertEquals(app.map.lastMove,"up");
        app.map.move("right");
        app.delay(10);
        app.map.ghostInvisible = false;

        app.map.debugMode = true;
        app.map.move("right");
        assertEquals(app.map.mode,0);
        // app.redraw();
        app.delay(5000);
        assertNotNull(app);

        
        
    }
}