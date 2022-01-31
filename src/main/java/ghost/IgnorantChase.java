package ghost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class IgnorantChase extends ChaseMethods implements ChaseBehaviour {
    int targetX;
    int targetY;
    String lastMove;
    int ghostX;
    int ghostY;

    public IgnorantChase(int x, int y,String lastMove) {
        this.ghostX = x;
        this.ghostY = y;
        this.lastMove = lastMove;   
    }
    // All getter and setter methods
    @Override
    public void setX(int x) {this.ghostX = x;}
    @Override
    public void setY(int y) {this.ghostY = y;}
    @Override
    public int getX() {return this.ghostX;}
    @Override
    public int getY() {return this.ghostY;}

    @Override
    public String getLastMove() {return this.lastMove;}

    @Override
    public void bestMove(Map map) {
        
        LinkedHashMap<String, Double> possibleMoves = ChaseMethods.PossibleMoves(map, this.ghostX, this.ghostY, this.targetX, this.targetY);
        List<Double> mapValuesFixed = new ArrayList<Double>(possibleMoves.values());
        List<Double> mapValuesSorted = new ArrayList<Double>(possibleMoves.values());
        List<String> mapKeys = new ArrayList<String>(possibleMoves.keySet());
        Collections.sort(mapValuesSorted);
        String move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(0)));
        
        if (this.lastMove != null) {
            
            if(this.lastMove.equals("down") && move.equals("up")) {
                
                if (mapValuesSorted.size() == 1) {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(0)));
                }
                else {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(1)));
                }
            }
            else if (this.lastMove.equals("up") && move.equals("down")) {
                if (mapValuesSorted.size() == 1) {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(0)));
                }
                else {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(1)));
                }
            }
            else if (this.lastMove.equals("left") && move.equals("right")) {
                if (mapValuesSorted.size() == 1) {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(0)));
                }
                else {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(1)));
                }
            }
            else if (this.lastMove.equals("right") && move.equals("left")) {
                
                if (mapValuesSorted.size() == 1) {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(0)));
                }
                else {
                    move = mapKeys.get(mapValuesFixed.indexOf(mapValuesSorted.get(1)));
                }
            }
        }

        if (this.lastMove == null) {
            map.mapLines.get(this.ghostY).set(this.ghostX, "0");
        }
        if (move.equals("down")) {
            this.ghostY +=1;
            this.lastMove = move;
        }

        else if (move.equals("right")) {   
            this.ghostX += 1;
            this.lastMove = move;
        }
        else if (move.equals("left")) {
            this.ghostX -= 1;
            this.lastMove = move;
        }

        else if (move.equals("up")) {
            this.ghostY -=1;
            this.lastMove = move;
        } 
    }

    @Override
    public void chase(Map map) {
        
        findTarget(map);
        if (map.move != null) {
            ChaseMethods.GhostHitsWaka(map, this.ghostX, this.ghostY, this);
            if (map.frightenMode || map.ghostInvisible) {
                ChaseMethods.FrightenMove(map, this.lastMove, this);
            }
            else {
                bestMove(map);
            }
            ChaseMethods.GhostHitsWaka(map, this.ghostX, this.ghostY,this);
        }
    }

    @Override
    public void draw(Map map) {
        map.app.image(map.ignorant.sprite,((this.ghostX * 16)-(28-16)/2), ((this.ghostY * 16)-(28-16)/2));
        debugMode(map);
    }

    @Override
    public void findTarget(Map map) {
        Double dist = Math.sqrt(Math.pow((map.wakaX-ghostX),2)+ Math.pow((map.wakaY-ghostY),2));
        if (dist > 8) {
            this.targetX = map.wakaX;
            this.targetY = map.wakaY;
        }

        else {
            for (int i = map.mapLines.size(); i-- > 0;) { // entire list
                List<String> lsInMapLines = map.mapLines.get(i);    
                for (int k = 0; k < lsInMapLines.size(); k++) { // list within list (k,i)
                    if (lsInMapLines.get(k).equals("4")) {
                        this.targetX = k+1;
                        this.targetY = i;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void debugMode(Map map) {
        if (map.debugMode) {
            map.app.line(this.ghostX*16, this.ghostY*16, this.targetX*16, this.targetY*16);
            map.app.stroke(255,255,255);
        }

    }

    @Override
    public void chase(Map map, ChaseBehaviour chaser) {}

    @Override
    public void setLastMove(String lastMove) {this.lastMove = lastMove;}

    @Override
    public void frightenDraw(Map map) {
        map.app.image(map.frightenGhost.sprite,((this.ghostX * 16)-(28-16)/2), ((this.ghostY * 16)-(28-16)/2));

    }
}
