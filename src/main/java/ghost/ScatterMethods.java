package ghost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public abstract class ScatterMethods {

    public static void restoreGame(Map map) {
        if (!map.frightenMode) {
            map.wakaLivesLeft -=1;
        }
        map.mapLines.get(map.wakaY).set(map.wakaX, "0");
        map.mapLines.get(map.wakaStartingY).set(map.wakaStartingX, "p");
        map.wakaX = map.wakaStartingX;
        map.wakaY = map.wakaStartingY;
        map.move = null;
        map.restoreGame = true;
    }

    public static void GhostHitsWaka(Map map, int ghostX, int ghostY,ScatterBehaviour ghost) {
        if ((map.wakaX == ghostX) && (map.wakaY == ghostY) && (map.frightenMode || map.ghostInvisible)) {
            map.ghostsRemoved.add(map.ghostsListCurrentScatter.indexOf(ghost));
            map.wakaKillGhost = true;
        }
        else if ((map.wakaX == ghostX) && (map.wakaY == ghostY)) {
            ScatterMethods.restoreGame(map);
        }
    }


    public static LinkedHashMap<String, Double>  PossibleMoves( Map map, int ghostX, int ghostY, int targetX, int targetY) {
        
        LinkedHashMap<String, Double> possibleMoves = new LinkedHashMap<String, Double>();
        List<String> ghosts = new ArrayList<String>(Arrays.asList("c","a","i","w","g","p","7","0","8","s"));

        // Up Case 
        if ( ghosts.contains(map.mapLines.get(ghostY-1).get(ghostX))) {
            double tempDist =  Math.pow((targetX-ghostX),2)+ Math.pow((targetY-(ghostY-1)),2);
            possibleMoves.put("up", tempDist);
        }
        // Left Case 
        if ( ghosts.contains(map.mapLines.get(ghostY).get(ghostX-1))) {
            double tempDist = Math.pow((targetX-(ghostX-1)),2) +  Math.pow((targetY-ghostY),2);
            possibleMoves.put("left", tempDist);
        }
        // Right Case 
        if (ghosts.contains(map.mapLines.get(ghostY).get(ghostX+1))) {
            double tempDist =  Math.pow((targetX-(ghostX+1)),2) + Math.pow((targetY-ghostY),2);
            possibleMoves.put("right", tempDist);
        }
        // Down Case
        if (ghosts.contains(map.mapLines.get(ghostY+1).get(ghostX))) {
            double tempDist =  Math.pow((targetX-ghostX),2) + Math.pow((targetY-(ghostY+1)),2);
            possibleMoves.put("down", tempDist);
        }
        return possibleMoves;
    }

    public static List<String>  PossibleMovesFrighten( Map map, ScatterBehaviour ghost) {
        
        List<String> allMoves = new ArrayList<String>();
        List<String> ghosts = new ArrayList<String>(Arrays.asList("c","a","i","w","g","p","7","0","8","s"));

        // Up Case 
        if (ghosts.contains(map.mapLines.get(ghost.getY()-1).get(ghost.getX()))) {
            allMoves.add("up");
        }
        // Left Case 
        if (ghosts.contains(map.mapLines.get(ghost.getY()).get(ghost.getX()-1))) {
            allMoves.add("left");
        }
        // Right Case 
        if (ghosts.contains(map.mapLines.get(ghost.getY()).get(ghost.getX()+1))) {
            allMoves.add("right");
        }
        // Down Case
        if (ghosts.contains(map.mapLines.get(ghost.getY()+1).get(ghost.getX()))) {
            allMoves.add("down");
        }
        return allMoves;
    }


    public static void FrightenMove(Map map, String lastMove, ScatterBehaviour ghost) {
        
        List<String> allMoves = ScatterMethods.PossibleMovesFrighten(map, ghost);
        Random randomiser = new Random();
        String move = allMoves.get(randomiser.nextInt(allMoves.size()));
        
        if (lastMove != null) {
            
            if(lastMove.equals("down") && move.equals("up")) {
                allMoves.remove("up");
                move = allMoves.get(randomiser.nextInt(allMoves.size()));
            }
            else if (lastMove.equals("up") && move.equals("down")) {
                allMoves.remove("down");
                move = allMoves.get(randomiser.nextInt(allMoves.size()));
            }
            else if (lastMove.equals("left") && move.equals("right")) {
                allMoves.remove("right");
                move = allMoves.get(randomiser.nextInt(allMoves.size()));
                
            }
            else if (lastMove.equals("right") && move.equals("left")) {
                allMoves.remove("left");
                move = allMoves.get(randomiser.nextInt(allMoves.size()));
            }
        }
        
        if (lastMove == null) {
            map.mapLines.get(ghost.getY()).set(ghost.getX(), "0");
        }
        if (move.equals("down")) {
            ghost.setY(ghost.getY() + 1);
            ghost.setLastMove(move);
        }

        else if (move.equals("right")) {   
            ghost.setX(ghost.getX() + 1);
            ghost.setLastMove(move);
        }
        else if (move.equals("left")) {
            ghost.setX(ghost.getX() - 1);
            ghost.setLastMove(move);
        }

        else if (move.equals("up")) {
            ghost.setY(ghost.getY() - 1);
            ghost.setLastMove(move);
        } 
    }
}