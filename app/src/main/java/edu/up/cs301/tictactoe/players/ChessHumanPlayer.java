package edu.up.cs301.tictactoe.players;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.game.R;
import edu.up.cs301.tictactoe.infoMessage.ChessState;
import edu.up.cs301.tictactoe.tttActionMessage.ChessDotAction;
import edu.up.cs301.tictactoe.tttActionMessage.ChessHighlightAction;
import edu.up.cs301.tictactoe.tttActionMessage.ChessMoveAction;
import edu.up.cs301.tictactoe.views.ChessSurfaceView;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    private ChessSurfaceView surfaceView;
    private ChessState state;

    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private int x;
    private int y;

    private int layoutId;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (surfaceView == null) {
            return;
        }

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        } else if (!(info instanceof ChessState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((ChessState) info);
            surfaceView.invalidate();
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);

        surfaceView = (ChessSurfaceView) myActivity.findViewById(R.id.surfaceView);
        surfaceView.setOnTouchListener(this);
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    protected void initAfterReady() {
        myActivity.setTitle("Chess: " + allPlayerNames[0] + " vs. " + allPlayerNames[1]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            int xValue = (int) event.getX();
            int yValue = (int) event.getY();

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (xValue > 20 + (row * 115) && yValue < 175 + (row * 115)) {
                        if (xValue > 20 + (col * 115) && yValue < 175 + (col * 115)) {
                            for(int index = 0; index < xMovement.size(); index++) {
                                if (xMovement.get(index) == row && yMovement.get(index) == col) {
                                    ChessMoveAction mAction = new ChessMoveAction(this, row, col, state.getPiece(x,y));
                                    game.sendAction(mAction);
                                    state.setPiece(x,y,0);
                                    state.setBoard(x,y,0);
                                    for(int k = 0; k < xMovement.size(); k++) {
                                        state.setBoard(xMovement.get(k),yMovement.get(k),0);
                                    }
                                    xMovement.clear();
                                    yMovement.clear();
                                    surfaceView.invalidate();
                                    return true;
                                }
                            }
                            if(x != 8 && y != 8) {
                                state.setBoard(x,y,0);
                                for (int index = 0; index < xMovement.size(); index++) {
                                    state.setBoard(xMovement.get(index), yMovement.get(index), 0);
                                }
                                x = 8;
                                y = 8;
                                xMovement.clear();
                                yMovement.clear();
                                surfaceView.invalidate();
                                if (state.getPiece(x,y) <= 0) {
                                    return true;
                                }
                            }
                            if(state.getPiece(x,y) <= 0) {
                                return true;
                            }
                            x = row;
                            y = col;
                            ChessHighlightAction hAction = new ChessHighlightAction(this, x, y, 1);
                            game.sendAction(hAction);
                            pieceMovement();
                            ChessDotAction dAction = new ChessDotAction(this,xMovement,yMovement,2);
                            game.sendAction(dAction);
                            surfaceView.invalidate();
                        }
                    }
                }
            }
        }
        return true;
    }

    public void pieceMovement() {
        if (state.getPiece(x, y) == 1) {
            movePawn();
        } else if (state.getPiece(x, y) == 2) {
            moveBishop();
        } else if (state.getPiece(x, y) == 3) {
            moveKnight();
        } else if (state.getPiece(x, y) == 4) {
            moveRook();
        } else if (state.getPiece(x, y) == 5) {
            moveQueen();
        } else if (state.getPiece(x, y) == 6) {
            moveKing();
        }
    }

    //general diagonal movement for bishops and queen
    public void generalDiagonalMove() {
        boolean stopUpLeft = false;
        boolean stopUpRight = false;
        boolean stopDownLeft = false;
        boolean stopDownRight = false;

        for (int i = 1; i < 8; i++) {
            if (y - i >= 0 && x - i >= 0) {
                if (state.getPiece(x - i, y - i) < 0 && !stopUpLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y - i);
                    stopUpLeft = true;
                }
                if (state.getPiece(x - i, y - i) > 0) {
                    stopUpLeft = true;
                }
                if (!stopUpLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y - i);
                }
            }
            if (y - i >= 0 && x + i < 8) {
                if (state.getPiece(x + i, y - i) < 0 && !stopUpRight) {
                    xMovement.add(x + i);
                    yMovement.add(y - i);
                    stopUpRight = true;
                }
                if (state.getPiece(x + i, y - i) > 0) {
                    stopUpRight = true;
                }
                if (!stopUpRight) {
                    xMovement.add(x + i);
                    yMovement.add(y - i);
                }
            }
            if (y + i < 8 && x - i >= 0) {
                if (state.getPiece(x - i, y + i) < 0 && !stopDownLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y + i);
                    stopDownLeft = true;
                }
                if (state.getPiece(x - i, y + i) > 0) {
                    stopDownLeft = true;
                }
                if (!stopDownLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y + i);
                }
            }
            if (y + i < 8 && x + i < 8) {
                if (state.getPiece(x + i, y + i) < 0 && !stopDownRight) {
                    xMovement.add(x + i);
                    yMovement.add(y + i);
                    stopDownRight = true;
                }
                if (state.getPiece(x + i, y + i) > 0) {
                    stopDownRight = true;
                }
                if (!stopDownRight) {
                    xMovement.add(x + i);
                    yMovement.add(y + i);
                }
            }
        }
    }

    //general movement for rooks and queen
    public void generalSideMove() {
        boolean stopLeft = false;
        boolean stopRight = false;
        boolean stopUp = false;
        boolean stopDown = false;

        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                if (state.getPiece(x - i, y) < 0 && !stopLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y);
                    stopLeft = true;
                }
                if (state.getPiece(x - i, y) > 0) {
                    stopLeft = true;
                }
                if (!stopLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y);
                }
            }
            if (y - i >= 0) {
                if (state.getPiece(x, y - i) < 0 && !stopUp) {
                    xMovement.add(x);
                    yMovement.add(y - i);
                    stopUp = true;
                }
                if (state.getPiece(x, y - i) > 0) {
                    stopUp = true;
                }
                if (!stopUp) {
                    xMovement.add(x);
                    yMovement.add(y - i);
                }
            }
            if (y + i < 8) {
                if (state.getPiece(x, y + i) < 0 && !stopDown) {
                    xMovement.add(x);
                    yMovement.add(y + i);
                    stopDown = true;
                }
                if (state.getPiece(x, y + i) > 0) {
                    stopDown = true;
                }
                if (!stopDown) {
                    xMovement.add(x);
                    yMovement.add(y + i);
                }
            }
            if (x + i < 8) {
                if (state.getPiece(x + i, y) < 0 && !stopRight) {
                    xMovement.add(x + i);
                    yMovement.add(y);
                    stopRight = true;
                }
                if (state.getPiece(x + i, y) > 0) {
                    stopRight = true;
                }
                if (!stopRight) {
                    xMovement.add(x + i);
                    yMovement.add(y);
                }
            }
        }
    }

    //movement for the pawn
    public void movePawn() {
        if (y == 6) {
            xMovement.add(x);
            xMovement.add(x);
            yMovement.add(y - 1);
            yMovement.add(y - 2);
        } else if (y > 0) {
            if (state.getPiece(x, y - 1) == 0) {
                xMovement.add(x);
                yMovement.add(y - 1);
            }
        }
        if (x > 0 && y > 0) {
            if (state.getPiece(x - 1, y) < 0) {
                xMovement.add(x - 1);
                yMovement.add(y - 1);
            }
        }
        if (x < 7 && y > 0) {
            if (state.getPiece(x + 1, y - 1) < 0) {
                xMovement.add(x + 1);
                yMovement.add(y - 1);
            }
        }
    }

    //movement for the bishop
    public void moveBishop() {
        generalDiagonalMove();
    }

    //movement for the knight
    public void moveKnight() {
        if (x > 1 && y > 0) {
            if (state.getPiece(x - 2, y - 1) <= 0) {
                xMovement.add(x - 2);
                yMovement.add(y - 1);
            }
        }
        if (x > 0 && y > 1) {
            if (state.getPiece(x - 1, y - 2) <= 0) {
                xMovement.add(x - 1);
                yMovement.add(y - 2);
            }
        }
        if (x < 6 && y < 7) {
            if (state.getPiece(x + 2, y + 1) <= 0) {
                xMovement.add(x + 2);
                yMovement.add(y + 1);
            }
        }
        if (x < 7 && y < 6) {
            if (state.getPiece(x + 1, y + 2) == 0) {
                xMovement.add(x + 1);
                yMovement.add(y + 2);
            }
        }
        if (x > 1 && y < 7) {
            if (state.getPiece(x - 2, y + 1) <= 0) {
                xMovement.add(x - 2);
                yMovement.add(y + 1);
            }
        }
        if (x > 0 && y < 6) {
            if (state.getPiece(x - 1, y + 2) <= 0) {
                xMovement.add(x - 1);
                yMovement.add(y + 2);
            }
        }
        if (x < 6 && y > 0) {
            if (state.getPiece(x + 2, y - 1) <= 0) {
                xMovement.add(x + 2);
                yMovement.add(y - 1);
            }
        }
        if (x < 7 && y > 1) {
            if (state.getPiece(x + 1, y - 2) <= 0) {
                xMovement.add(x + 1);
                yMovement.add(y - 2);
            }
        }
    }

    //movement for the rook
    public void moveRook() {
        generalSideMove();
    }

    //movement for the queen
    public void moveQueen() {
        generalDiagonalMove();
        generalSideMove();
    }

    //movement for the king
    public void moveKing() {

    }
}
