package edu.up.cs301.tictactoe;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;
import edu.up.cs301.tictactoe.infoMessage.ChessState;
import edu.up.cs301.tictactoe.tttActionMessage.ChessDotAction;
import edu.up.cs301.tictactoe.tttActionMessage.ChessHighlightAction;
import edu.up.cs301.tictactoe.tttActionMessage.ChessMoveAction;

public class ChessLocalGame extends LocalGame {

    protected int moveCount;

    public ChessLocalGame() {
        super();
        super.state = new ChessState();
    }

    public ChessLocalGame(ChessState chessState) {
        super();
        super.state = new ChessState(chessState);
    }

    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new ChessState(((ChessState) state)));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((ChessState)state).getWhoseMove();
    }

    @Override
    protected boolean makeMove(GameAction action) {
        ChessMoveAction move = (ChessMoveAction) action;
        ChessHighlightAction highlight = (ChessHighlightAction) action;
        ChessDotAction dot = (ChessDotAction) action;
        ChessState state = (ChessState) super.state;

        int row = move.getRow();
        int col = move.getCol();
        int num = move.getNum();
        ArrayList<Integer> x = dot.getCol();
        ArrayList<Integer> y = dot.getRow();

        int playerId = getPlayerIdx(move.getPlayer());

        if (state.getPiece(row, col) > 0) {
            return false;
        }

        int whoseMove = state.getWhoseMove();

        state.setPiece(row, col, num);

        row = highlight.getRow();
        col = highlight.getCol();
        num = highlight.getNum();

        state.setBoard(row, col, num);

        num = dot.getNum();
        for(int i = 0; i < dot.getCol().size(); i++) {
            row = x.get(i);
            col = y.get(i);
            state.setBoard(row, col, num);
        }

        state.setWhoseMove(1 - whoseMove);

        moveCount++;

        return true;
    }
}
