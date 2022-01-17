package gamefour;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. november 10.
 * 
 * A Model osztály tartalmazza a játék logikáját
 * 
 * A konstruktorban létrehozzuk az adott méretű mátrixot,
 * ami a játéktáblát reprezentálja
 * A step függvény növeli a mátrix adott mezőit,
 * és megváltoztatja a jelenlegi játékost
 * A findWinner függvény megnézi nyert-e valaki,
 * és ha igen visszatér a győztes nevével
 */
public class Model {

    private int size;
    private int[][] table;
    protected Player actualPlayer;
    protected final Player redPlayer = new Player("Red", 0);
    protected final Player bluePlayer = new Player("Blue", 0);

    Model(int size) {
        this.size = size;
        actualPlayer = redPlayer;

        table = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = 0;
            }
        }
    }

    protected int[] step(int row, int column) {
        int[] tempArray = {0, 0, 0, 0, 0};
        
        if(table[row][column] == 4) {
            return tempArray;
        }
        
        if(table[row][column] < 4) {
            riseActualPlayerScore(row, column);
            tempArray[0] = table[row][column];
        }
        if (row - 1 >= 0 && table[row - 1][column] < 4) {
            riseActualPlayerScore(row-1, column);
            tempArray[1] = table[row - 1][column];
        }
        if (row + 1 < size && table[row + 1][column] < 4) {
            riseActualPlayerScore(row+1, column);
            tempArray[2] = table[row + 1][column];
        }
        if (column - 1 >= 0 && table[row][column - 1] < 4) {
            riseActualPlayerScore(row, column-1);
            tempArray[3] = table[row][column - 1];
        }
        if (column + 1 < size && table[row][column + 1] < 4) {
            riseActualPlayerScore(row, column+1);
            tempArray[4] = table[row][column + 1];
        }

        if (actualPlayer == redPlayer) {
            actualPlayer = bluePlayer;
        } else {
            actualPlayer = redPlayer;
        }

        return tempArray;
    }
    
    private void riseActualPlayerScore(int row, int column) {
        if(++table[row][column] == 4) {
            actualPlayer.score++;
        }
    }
    
    protected String findWinner() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(table[i][j] != 4) {
                    return "Nobody";
                }
            }
        }
        if(redPlayer.score > bluePlayer.score) {
            return "Red";
        }
        return "Blue";
    }
}
