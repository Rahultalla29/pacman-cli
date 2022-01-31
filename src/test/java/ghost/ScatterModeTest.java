package ghost;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScatterModeTest {
    
    @Test
    public void initialiseScatter() { // Test normal case at begining
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        map.scatter = new ScatterMode();
        
        map.mapLines = parse.mapReadLines("map.txt");
        map.scatter.ScatterModeInitialise(map);
        assertEquals(map.ghostsListCurrentScatter.size(), 4);
    }

    @Test
    public void ReinitialiseScatter() {// Test objects added to list
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new IgnorantChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new WhimChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new AmbusherChase(3, 4, "left"));
        map.scatter.ScatterModeReinitialise(map);
        assertEquals(map.ghostsListCurrentScatter.size(), 4);
    }


    @Test
    public void ScatterActivate() { // Test scatter not  work if null move
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        map.scatter = new ScatterMode();
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new IgnorantChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new WhimChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new AmbusherChase(3, 4, "left"));
        map.mapLines = parse.mapReadLines("map.txt");
        map.scatter.ScatterModeReinitialise(map);
        map.ghostInvisible = false;
        map.debugMode = false;
        int value = map.ghostsListCurrentScatter.get(0).getX();
        map.scatter.ScatterModeActivate(map);
        int value2 = map.ghostsListCurrentScatter.get(0).getX();
        assertSame(value, value2);
    }
    @Test
    public void ScatterModeActivate() { // Test null as param
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.chase = new ChaseMode();
        map.scatter.ScatterModeActivate(null);
        map.ghostsListCurrentScatter.clear();
        assertEquals(map.ghostsListCurrentScatter.size(), 0);
    }

    @Test
    public void ScatterModeActivate2() { // Test objects added to list
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.chase = new ChaseMode();
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostInvisible = false;
        map.frightenMode = true;
        map.alternateGhosts  = true;
        map.scatter.ScatterModeActivate(map);
        assertEquals(map.alternateGhosts, true);
    }

    @Test
    public void ScatterModeActivate3() { // Test null as param
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.chase = new ChaseMode();
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3, 4, "left"));
        map.ghostInvisible = false;
        map.frightenMode = false;
        map.alternateGhosts  = false;
        map.scatter.ScatterModeActivate(map);
        assertEquals(map.alternateGhosts,false);
    }

}