package ghost;

import java.util.ArrayList;
import java.util.List;

public class ChaseMode extends Ghost {
    
    boolean chasers;

    /** 
     * Reads current ghosts in Scatter mode 
     * and creates ChaseBehaviour objects with last move and (x,y) of previous mode
     * @param map Map instance
     */    
    public void ChaseModeInitialise(Map map) {
   
        if ((map == null) || (map.ghostsListCurrentScatter.size() == 0)) {
            return;
        }
        map.ghostsListCurrentChase.clear();
        try {
            for (ScatterBehaviour ghost : map.ghostsListCurrentScatter) {
                if (ghost.getClass().getName().equals("ghost.ChaserScatter")) {
                    map.ghostsListCurrentChase.add(new ChaserChase(ghost.getX(),ghost.getY(),ghost.getLastMove()));
                }
                else if (ghost.getClass().getName().equals("ghost.AmbusherScatter")) {
                    map.ghostsListCurrentChase.add(new AmbusherChase(ghost.getX(),ghost.getY(),ghost.getLastMove()));

                }
                else if (ghost.getClass().getName().equals("ghost.IgnorantScatter")) {
                    map.ghostsListCurrentChase.add(new IgnorantChase(ghost.getX(),ghost.getY(),ghost.getLastMove()));
                }
                else if (ghost.getClass().getName().equals("ghost.WhimScatter")) {
                    map.ghostsListCurrentChase.add(new WhimChase(ghost.getX(),ghost.getY(),ghost.getLastMove()));
                }
            } 
        }catch (NullPointerException e) {
            return;
        } 
    }

    /** 
     * Draws all chase ghosts with the respective mode, game is currently in
     * Also, holds current chasers in game for determining Whim ghost target cell
     * If no chasers exist uses waka as target
     * @param map Map instance
     */    

    public void ChaseModeActivate(Map map) { 
        if ((map == null) || (map.ghostsListCurrentChase.size() == 0)) {
            return;
        }
        try {
            
            List<ChaseBehaviour> chasers = new ArrayList<ChaseBehaviour>();


            for (ChaseBehaviour ghost : map.ghostsListCurrentChase) {

                if (ghost == null) {
                    return;
                }

                else if (ghost.getClass().getName().equals("ghost.ChaserChase")) {
                    chasers.add(ghost);
                    this.chasers = true;
                    ghost.chase(map); 
                }
                else if (ghost.getClass().getName().equals("ghost.WhimChase")) {
                    if (chasers.size() > 0) {
                        this.chasers = true;
                        ghost.chase(map, chasers.get(0));
                    }
                    else {
                        ghost.chase(map);
                    }
                }
                else {
                    ghost.chase(map);
                }
            }

            if (!map.ghostInvisible) {
                if (map.frightenMode) {
                    if (map.alternateGhosts) {
                        for (ChaseBehaviour ghost : map.ghostsListCurrentChase) {
                            ghost.frightenDraw(map);
                        }
                        map.alternateGhosts = false;
                    }
                    else {
                        for (ChaseBehaviour ghost : map.ghostsListCurrentChase) {
                            ghost.draw(map);
                        }
                        map.alternateGhosts = true;
                    }
                }
                else {
                    for (ChaseBehaviour ghost : map.ghostsListCurrentChase) {
                        ghost.draw(map);
                    }
                }
            }
            else {
                
                for (ChaseBehaviour ghost : map.ghostsListCurrentChase) {
                    ghost.debugMode(map);
        
                }
            }
        }catch(NullPointerException e) {
            return;
        }
    }
}
