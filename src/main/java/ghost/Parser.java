package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Parser {

    private String filename;
    private Long speed;
    private Long lives;
    private Long frightenedLength;
    private List<Long> modeLengths;

    public Parser() {}
    /**
    * Returns filename
    * @return filename 
    */
    public String getFilename() {return this.filename;}
    /**
    * Returns speed
    * @return speed
    */
    public Long getSpeed() {return this.speed;}
    /**
    * Returns lives
    * @return lives
    */
    public Long getLives() {return this.lives;}
    /**
    * Returns frightenedLength
    * @return frightenedLength
    */
    public Long getfrightenedLength() {return this.frightenedLength;}
    /**
    * Returns modeLengths
    * @return modeLengths
    */
    public List<Long> getModelengths() {return this.modeLengths;}
    /**
    * Read Configuration file and sets parser instance variables
    * @param filename name of file
    */
    public void jsonReader(String filename) {

        JSONParser parseJson = new JSONParser();

        try {

            FileReader readFile = new FileReader(filename);
            Object fileObj = parseJson.parse(readFile);
            JSONObject jsonObj = (JSONObject) fileObj;

            String fname = (String) jsonObj.get("map");
            this.filename = fname;

            Long speed = (Long) jsonObj.get("speed");
            this.speed = speed;

            Long lives = (Long) jsonObj.get("lives");
            this.lives = lives;

            Long frightenedLength = (Long) jsonObj.get("frightenedLength");
            this.frightenedLength = frightenedLength;

            JSONArray modeLengthsJson = (JSONArray) jsonObj.get("modeLengths");

            List<Long> modelengths = new ArrayList<Long>();

            for(int i = 0; i < modeLengthsJson.size(); i++){
                modelengths.add((Long) modeLengthsJson.get(i));
            }
            this.modeLengths = modelengths;

            if (this.speed != 1 && this.speed != 2) {
                System.out.println("Invalid Speed (Need to be 1 or 2)");
                System.exit(1);
            }

        } catch (FileNotFoundException fileNotFound) {
            System.out.println("File was not found");
        }

        catch (NullPointerException nullFound) {
            System.out.println("Input was null");

        } catch (IOException cannotRead) {
            System.out.println("File cannot be read");
            
        } catch (ParseException cannotParse) {
            System.out.println("File was not able to be parsed");    
        }
    }
    /**
    * Read lines in configuration and convert to list of list of strings
    * @param filename name of file
    * @return list of list of strings
    */
    public List<List<String>> mapReadLines(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            List<String> lines = new ArrayList<String> ();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();

            return mapParse(lines);


        }catch (IOException e) {
            System.out.println("File cannot be read");  
        }
        return null;
    }

    /**
    * Parses lines in configuration and does error-checking
    * @param lines list of strings
    * @return list of strings
    */

    public List<List<String>> mapParse(List<String> lines) {

        int startCount = 0;
        int fruitCount = 0;
        List<List<String>> map = new ArrayList<List<String>>(); // List of List of Strings
        ArrayList<String> validCells = new ArrayList<>(Arrays.asList("0","1","2","3","4","5","6","7","p","g","a","c","i","w","8","s"));
        for (int i=0; i<lines.size(); i++) { 
            List<String> mapLine = new ArrayList<String>(Arrays.asList(lines.get(i).split("")));

            for (int k=0; k <mapLine.size(); k++) {
                String cell = mapLine.get(k);
                if (!validCells.contains(cell)) {
                    System.out.println("Invalid char");
                    System.exit(1);
                }

                if (cell.equals("p")) {
                    startCount += 1;
                }
                
                if (cell.equals("7")) {
                    fruitCount += 1;
                }
                
            }
            map.add(mapLine);          
        }

        if (startCount != 1) {
            System.out.println("Invalid Number of Starting Location for Waka");
            System.exit(1);
        }

        if (fruitCount < 1) {
            System.out.println("Invalid Number of Fruit Cells (Need minimum 1)");
            System.exit(1);
        }
        return map;
    }
}
