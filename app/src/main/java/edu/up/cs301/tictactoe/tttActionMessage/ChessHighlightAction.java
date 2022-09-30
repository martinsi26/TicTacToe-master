package edu.up.cs301.tictactoe.tttActionMessage;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class ChessHighlightAction extends GameAction {

    private int row;
    private int col;
    private int num;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessHighlightAction(GamePlayer player, int row, int col, int num) {
        super(player);

        this.row = Math.max(0, Math.min(9, row));
        this.col = Math.max(0, Math.min(9, col));
        this.num = num;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNum() {
        return num;
    }
}
