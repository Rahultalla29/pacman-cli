package ghost;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ChaseModeTest {
    

    @Test
    public void initialiseChase() { // Test normal case at begining
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.chase.ChaseModeInitialise(map);
        assertEquals(map.ghostsListCurrentChase.size(), 0);
    }

    
    @Test
    public void initialiseChase2() { // Test null in list
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.chase = new ChaseMode();
        map.ghostsListCurrentScatter.add(null);
        map.chase.ChaseModeInitialise(map);
        assertEquals(map.ghostsListCurrentChase.size(), 0);
    }
    @Test
    public void initialiseChase3() { // Test null as param
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.chase = new ChaseMode();
        map.chase.ChaseModeInitialise(null);
        assertEquals(map.ghostsListCurrentChase.size(), 0);
    }
    @Test
    public void initialiseChase4() {// Test objects added to list
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.ghostsListCurrentScatter.add(new AmbusherScatter(3,4,"left"));
        map.ghostsListCurrentScatter.add(new WhimScatter(3,4,"left"));
        map.ghostsListCurrentScatter.add(new IgnorantScatter(3,4,"left"));
        map.ghostsListCurrentScatter.add(new ChaserScatter(3,4,"left"));
        map.chase = new ChaseMode();
        map.chase.ChaseModeInitialise(map);
        assertEquals(map.ghostsListCurrentChase.size(), 4);
    }
    @Test
    public void ChaseModeActivate() { // Test null as param
        App app = new App();
        Map map = new Map(app);
        map.scatter = new ScatterMode();
        map.chase = new ChaseMode();
        map.chase.ChaseModeActivate(null);
        assertEquals(map.ghostsListCurrentChase.size(), 0);
    }
    @Test
    public void ChaseModeActivate2() { // Test empty list
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.ghostsListCurrentChase.clear();
        map.chase.ChaseModeActivate(map);
        assertEquals(map.ghostsListCurrentChase.size(), 0);
    }
    @Test
    public void ChaseModeActivate3() {  // Test null element
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.ghostsListCurrentChase.add(null);
        map.chase.ChaseModeActivate(map);
        assertEquals(map.ghostsListCurrentChase.size(), 1);
    }
    @Test
    public void ChaseModeActivate4() { // Test Chaser detected
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.chase.ChaseModeActivate(map);
        assertEquals(map.chase.chasers, true);
    }
    @Test
    public void ChaseModeActivate5() { // Test Whim and chaser detected
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.ghostsListCurrentChase.add(new ChaserChase(3, 4, "left"));
        map.ghostsListCurrentChase.add(new WhimChase(3, 4, "left"));
        map.chase.ChaseModeActivate(map);
        assertEquals(map.chase.chasers, true);
    }

    @Test
    public void ChaseModeActivate6() { // frigghten mode set
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.ghostInvisible = false;
        map.frightenMode = true;
        map.chase.ChaseModeActivate(map);
        assertEquals(map.alternateGhosts, true);
    }

    @Test
    public void ChaseModeActivate7() {  // frighten mode removed
        App app = new App();
        Map map = new Map(app);
        map.chase = new ChaseMode();
        map.ghostInvisible = false;
        map.frightenMode = false;
        map.alternateGhosts = true;
        map.chase.ChaseModeActivate(map);
        assertEquals(map.alternateGhosts, true);
    }


}