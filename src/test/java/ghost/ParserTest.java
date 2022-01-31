package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ParserTest {

    @Test
    public void jsonRead() { //getter methods check
        Parser parse = new Parser();
        parse.jsonReader("config.json");
        assertEquals(parse.getFilename(),"map.txt");
        assertEquals(parse.getSpeed(),1);
        assertEquals(parse.getLives(),3);
        assertEquals(parse.getModelengths().size(),4);
        assertEquals(parse.getfrightenedLength(),2);

    }

    @Test
    public void jsonRead2() { // Variables would not be set as error in config occured during parsing
        Parser parse = new Parser();
        parse.jsonReader("configTwo.json");
        assertEquals(parse.getFilename(),null);
        assertEquals(parse.getSpeed(),null);
    }

    
    @Test
    public void jsonRead3Exception() { // Variables would not be set as error in config occured during parsing
        Parser parse = new Parser();
        parse.jsonReader(null);
        parse.jsonReader("badConfig.json");

    }


    @Test
    public void readMap() { // Invalid Cell
        Parser parse = new Parser();
        List<List<String>> mapLines = parse.mapReadLines("mapTwo.txt");
        assertSame(mapLines,null);



    }
}