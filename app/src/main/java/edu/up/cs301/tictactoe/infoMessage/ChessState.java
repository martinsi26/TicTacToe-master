package edu.up.cs301.tictactoe.infoMessage;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;

public class ChessState extends GameState {

    private int[][] pieces;
    private int[][] board;

    private int playerToMove;

    public ChessState() {
        pieces = new int[8][8];
        board = new int[9][9];

        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                if(j == 0) {
                    pieces[0][j] = -4;
                    pieces[1][j] = -3;
                    pieces[2][j] = -2;
                    pieces[3][j] = -5;
                    pieces[4][j] = -6;
                    pieces[5][j] = -2;
                    pieces[6][j] = -3;
                    pieces[7][j] = -4;
                } else if(j == 1) {
                    pieces[i][j] = -1;
                } else if(j == 6) {
                    pieces[i][j] = 1;
                } else if(j == 7) {
                    pieces[0][j] = 4;
                    pieces[1][j] = 3;
                    pieces[2][j] = 2;
                    pieces[3][j] = 5;
                    pieces[4][j] = 6;
                    pieces[5][j] = 2;
                    pieces[6][j] = 3;
                    pieces[7][j] = 4;
                } else {
                    pieces[i][j] = 0;
                }
            }
        }

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }

        playerToMove = 0;
    }

    public ChessState(ChessState other) {
        pieces = new int[8][8];
        board = new int[9][9];

        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                pieces[i][j] = other.pieces[i][j];
            }
        }

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = other.board[i][j];
            }
        }

        playerToMove = other.playerToMove;
        super.numSetupTurns = other.numSetupTurns;
        super.currentSetupTurn = other.currentSetupTurn;
    }

    public void setBoard(int row, int col, int num) {
        if (board == null || row < 0 || col < 0) return;
        if (row >= board.length || col >= board[row].length) return;

        board[row][col] = num;
    }

    public int getBoard(int row, int col) {
        if (board == null || row < 0 || col < 0) return '?';
        if (row >= board.length || col >= board[row].length) return '?';

        return board[row][col];
    }

    public int getPiece(int row, int col) {
        if (pieces == null || row < 0 || col < 0) return '?';
        if (row >= pieces.length || col >= pieces[row].length) return '?';

        return pieces[row][col];
    }

    public void setPiece(int row, int col, int piece) {
        if (pieces == null || row < 0 || col < 0) return;
        if (row >= pieces.length || col >= pieces[row].length) return;

        pieces[row][col] = piece;
    }

    public int getWhoseMove() {
        return playerToMove;
    }

    public void setWhoseMove(int id) {
        playerToMove = id;
    }

    public boolean equals(Object object){
        if(! (object instanceof ChessState)) return false;
        ChessState chessState = (ChessState) object;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(this.board[i][j] != chessState.board[i][j]){
                    return false;
                }
            }
        }

        if (this.playerToMove != chessState.playerToMove || this.numSetupTurns != chessState.numSetupTurns || this.currentSetupTurn != chessState.currentSetupTurn){
            return false;
        }
        return true;
    }
}
