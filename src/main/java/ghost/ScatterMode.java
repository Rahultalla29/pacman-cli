package ghost;

import java.util.List;

class ScatterMode extends Ghost {

    /** 
     * Reads ghosts in mapLines variable in via Map instance
     * and creates ScatterBehaviour objects
     * @param map
     */   
    public void ScatterModeInitialise(Map map) {
        
        for (int i = 0; i < map.mapLines.size(); i++) { 
            List<String> lsInMapLines = map.mapLines.get(i);
            for (int k = 0; k < lsInMapLines.size(); k++) {
                
                if (lsInMapLines.get(k).equals("g") || lsInMapLines.get(k).equals("c")) {
                    ScatterBehaviour chaser = new ChaserScatter(k,i);
                    map.ghostsListCurrentScatter.add(chaser);
                }
                else if (lsInMapLines.get(k).equals("a") ) {
                    ScatterBehaviour ambusher = new AmbusherScatter(k,i);
                    map.ghostsListCurrentScatter.add(ambusher);
                }
                else if (lsInMapLines.get(k).equals("i") ) {
                    ScatterBehaviour ignorant = new IgnorantScatter(k,i);
                    map.ghostsListCurrentScatter.add(ignorant);
                }
                else if (lsInMapLines.get(k).equals("w") ) {
                    ScatterBehaviour whim = new WhimScatter(k,i);
                    map.ghostsListCurrentScatter.add(whim);
                } 

            }
        }       
    }

    /** 
     * Reads current ghosts in Chase mode 
     * and creates ScatterBehaviour objects with last move and (x,y) of previous mode
     * @param map
     */   

    public void ScatterModeReinitialise(Map map) {

        map.ghostsListCurrentScatter.clear();
                
        for (ChaseBehaviour ghost : map.ghostsListCurrentChase) {
        
            if (ghost.getClass().getName().equals("ghost.ChaserChase")) {
                map.ghostsListCurrentScatter.add(new ChaserScatter(ghost.getX(),ghost.getY(),ghost.getLastMove()));
            }
            else if (ghost.getClass().getName().equals("ghost.AmbusherChase")) {
                map.ghostsListCurrentScatter.add(new AmbusherScatter(ghost.getX(),ghost.getY(),ghost.getLastMove()));
            }
            else if (ghost.getClass().getName().equals("ghost.IgnorantChase")) {
                map.ghostsListCurrentScatter.add(new IgnorantScatter(ghost.getX(),ghost.getY(),ghost.getLastMove()));
            }
            else if (ghost.getClass().getName().equals("ghost.WhimChase")) {
                map.ghostsListCurrentScatter.add(new WhimScatter(ghost.getX(),ghost.getY(),ghost.getLastMove()));
            }
        }
    }

    /** 
     * Draws all scatter ghosts with the respective mode, game is currently in
     * @param map
     */ 
    public void ScatterModeActivate(Map map) {
        if ((map == null) || (map.ghostsListCurrentScatter.size() == 0)) {
            return;
        }
        
        try {
            for (ScatterBehaviour ghost : map.ghostsListCurrentScatter) {
                ghost.scatter(map); 
            }

            if (!map.ghostInvisible) {
                if (map.frightenMode) {

                    if (map.alternateGhosts) {
                        for (ScatterBehaviour ghost : map.ghostsListCurrentScatter) {
                            ghost.frightenDraw(map);
                        }
                        map.alternateGhosts = false;
                    }
                    else {
                        for (ScatterBehaviour ghost : map.ghostsListCurrentScatter) {
                            ghost.draw(map);
                        }
                        map.alternateGhosts = true;
                    }
                }
                else {
                    for (ScatterBehaviour ghost : map.ghostsListCurrentScatter) {
                        ghost.draw(map);
                    }
                }
            }
            else {
                
                for (ScatterBehaviour ghost : map.ghostsListCurrentScatter) {
                    ghost.debugMode(map);
                }
            }       
        }
        catch (NullPointerException e) {
            return;
        }
    }
}
