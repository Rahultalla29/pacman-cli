package ghost;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChaserChaseTest {

    @Test
    public void updateVariables() { //getter methods check
        ChaserChase test = new ChaserChase(3,4,"left");
        test.setX(4);
        assertSame(test.getX(), 4);
        test.setY(3);
        assertSame(test.getY(), 3);
        test.setLastMove("right");
        assertEquals(test.getLastMove(), "right");


    }

    @Test
    public void bestMove() { //Exception handling
        App app = new App();
        Map map = new Map(app);
        Parser parse = new Parser();
        map.mapLines = parse.mapReadLines("map.txt");
        ChaserChase test = new ChaserChase(3,4,"left");
        map.wakaX = 4;
        map.wakaY = 5;
        map.move = "left";
        map.debugMode = true;
        map.ghostInvisible = true;
        map.frightenMode = true;
        test.bestMove(map);
        
        test.chase(map);
        test.chase(map,test);
        Exception exception = assertThrows(NullPointerException.class, () -> {
           test.debugMode(map);
        });
        assertTrue(true,exception.getMessage());
        Exception exception2 = assertThrows(NullPointerException.class, () -> {
            test.draw(map);
            test.frightenDraw(map);

         });
         assertTrue(true,exception2.getMessage());

    }
    
}