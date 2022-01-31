package ghost;

public interface ScatterBehaviour {
    /**
    * Moves scatter ghost in respective pattern
    * @param map Map
    */
    public void scatter(Map map);
    /**
    * Draw method for ghosts
    * @param map Map class instance
    */
    public void draw(Map map);
    /**
    * Draws ghost on app screen with frighten sprite
    * @param map Map class instance
    */
    public void frightenDraw(Map map);
    /**
    * Calculates x and y of required target cell 
    * and sets targetX and targetY local variables
    * @param map Map class instance
    * @param target target cell 
    */
    public void findTarget(Map map, String target);
    /**
    * Draws line from ghost instance x and y coordinate
    * to target x and y.
    * @param map Map class instance
    */ 
    public void debugMode(Map map);
    /**
    * Gets ghost X-coordinate
    * @return x-coordinate
    */
    public int getX();
    /**
    * Gets ghost Y-coordinate
    * @return y-coordinate
    */
    public int getY();
    /**
    * Gets ghost lastMove
    * @return lastMove
    */
    public String getLastMove();
    /**
    * Set ghost X-coordinate
    * @param x X-coordinate
    */
    public void setX(int x);
    /**
    * Set ghost Y-coordinate
    * @param y Y-coordinate
    */
    public void setY(int y);
    /**
    * Sets ghost lastMove
    * @param lastMove lastMove
    */
    public void setLastMove(String lastMove);
    /**
    * Determines best move based on lastmove and cell type
    * @param map Map class instance
    */ 
    public void bestMove(Map map);   
}






