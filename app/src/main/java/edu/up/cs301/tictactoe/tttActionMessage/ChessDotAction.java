package edu.up.cs301.tictactoe.tttActionMessage;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class ChessDotAction extends GameAction {

    private ArrayList<Integer> row;
    private ArrayList<Integer> col;
    private int num;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessDotAction(GamePlayer player, ArrayList<Integer> row, ArrayList<Integer> col, int num) {
        super(player);

        this.row = row;
        this.col = col;
        this.num = num;
    }

    public ArrayList<Integer> getRow() {
        return row;
    }

    public ArrayList<Integer> getCol() {
        return col;
    }

    public int getNum() {
        return num;
    }
}
