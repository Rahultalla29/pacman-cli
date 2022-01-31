package ghost;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;

public class WakaTest {

    @Test
    public void updateVariables() { //getter methods check
        
        Waka testWaka = new Waka();
        
        App app = new App();
        Map map = new Map(app);
        PApplet.runSketch(new String[] {"hi"}, app);
        app.delay(1000); // Wait for assets to be loaded in
        assertNotNull(app);
        map.move = "left";
        Parser parse = new Parser();

        map.mapLines = parse.mapReadLines("map.txt");
 
        
        assertSame(map.fruitAvailable,0);
        
        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testWaka.isFruit(map);
        });

        Exception exception2 = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testWaka.wakaMoves(map);
        });
        app.noLoop();
            
    



    }
}