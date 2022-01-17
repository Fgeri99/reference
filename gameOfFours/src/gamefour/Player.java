package gamefour;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. november 10.
 * 
 * Eltárolja egy játékos nevét és pontszámát
 */
public class Player {
    protected String name;
    protected int score;
    
    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
