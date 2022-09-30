package edu.up.cs301.tictactoe.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;
import edu.up.cs301.game.R;
import edu.up.cs301.tictactoe.infoMessage.ChessState;

public class ChessSurfaceView extends FlashSurfaceView {

    //variables for creating board
    private float top = 40;
    private float left = 40;
    private float size = 115;
    private float bottom = top + size;
    private float right = left + size;

    protected ChessState state;

    //images for chess pieces
    protected Bitmap whitePawnImage;
    protected Bitmap whiteKnightImage;
    protected Bitmap whiteBishopImage;
    protected Bitmap whiteRookImage;
    protected Bitmap whiteKingImage;
    protected Bitmap whiteQueenImage;
    protected Bitmap blackPawnImage;
    protected Bitmap blackKnightImage;
    protected Bitmap blackBishopImage;
    protected Bitmap blackKingImage;
    protected Bitmap blackQueenImage;
    protected Bitmap blackRookImage;

    public ChessSurfaceView(Context context) {
        super(context);
        init();
    }

    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        whitePawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.wp);
        whiteKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.wn);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.wb);
        whiteRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.wr);
        whiteKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.wk);
        whiteQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.wq);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.wb);
        blackPawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.bp);
        blackBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.bb);
        blackKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.bn);
        blackRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.br);
        blackKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        blackQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.bq);
        blackBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.bb);
        whitePawnImage = Bitmap.createScaledBitmap(whitePawnImage,120,120,false);
        whiteRookImage = Bitmap.createScaledBitmap(whiteRookImage,120,120,false);
        whiteKnightImage = Bitmap.createScaledBitmap(whiteKnightImage,120,120,false);
        whiteKingImage = Bitmap.createScaledBitmap(whiteKingImage,120,120,false);
        whiteQueenImage = Bitmap.createScaledBitmap(whiteQueenImage,120,120,false);
        whiteBishopImage = Bitmap.createScaledBitmap(whiteBishopImage,120,120,false);
        blackPawnImage = Bitmap.createScaledBitmap(blackPawnImage,120,120,false);
        blackRookImage = Bitmap.createScaledBitmap(blackRookImage,120,120,false);
        blackKnightImage = Bitmap.createScaledBitmap(blackKnightImage,120,120,false);
        blackKingImage = Bitmap.createScaledBitmap(blackKingImage,120,120,false);
        blackQueenImage = Bitmap.createScaledBitmap(blackQueenImage,120,120,false);
        blackBishopImage = Bitmap.createScaledBitmap(blackBishopImage,120,120,false);
        init();
    }

    private void init() {
        setBackgroundColor(backgroundColor());
    }


    public void setState(ChessState state) {
        this.state = state;
    }

    public int blackSquare() {
        return Color.rgb(1, 50, 32);
    }

    public int whiteSquare() {
        return Color.WHITE;
    }

    public int highlightedSquare() {
        return Color.YELLOW;
    }

    public int dot() {
        return Color.LTGRAY;
    }

    public int backgroundColor() {
        return Color.BLACK;
    }

    public void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 && j % 2 != 0) || (j % 2 == 0 && i % 2 != 0)) {
                    paint.setColor(blackSquare());
                } else {
                    paint.setColor(whiteSquare());
                }
                canvas.drawRect(left + (right - left) * i, top + (bottom - top) * j,
                        right + (right - left) * i, bottom + (bottom - top) * j, paint);
            }
        }

        if(state == null) {
            return;
        }

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                drawHighlight(canvas, state.getBoard(row, col), row, col);
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                drawPiece(canvas, state.getPiece(row, col), row, col);
            }
        }
    }

    protected void drawHighlight(Canvas canvas, int num, int col, int row) {
        float leftLoc = left + size * col;
        float topLoc = top + size * row;
        float rightLoc = right + size * col;
        float bottomLoc = bottom + size * row;

        float centerX = left + (size/2) + size * col;
        float centerY = top + (size/2) + size * row;
        float radius = (right - left) / 5;

        Paint highlightPaint = new Paint();
        highlightPaint.setColor(highlightedSquare());
        Paint dotPaint = new Paint();
        dotPaint.setColor(dot());

        if(num == 1) {
            canvas.drawRect(leftLoc, topLoc, rightLoc, bottomLoc, highlightPaint);
        } else if(num == 2) {
            canvas.drawCircle(centerX, centerY, radius, dotPaint);
        }
    }

    protected void drawPiece(Canvas canvas, int num, int col, int row) {
        float xLoc = left + (col * size);
        float yLoc = top + (row * size);

        Paint paint = new Paint();
        if(num == 1) {
            canvas.drawBitmap(whitePawnImage, xLoc, yLoc, paint);
        } else if(num == 2) {
            canvas.drawBitmap(whiteBishopImage, xLoc, yLoc, paint);
        } else if(num == 3) {
            canvas.drawBitmap(whiteKnightImage, xLoc, yLoc, paint);
        } else if(num == 4) {
            canvas.drawBitmap(whiteRookImage, xLoc, yLoc, paint);
        } else if(num == 5) {
            canvas.drawBitmap(whiteQueenImage, xLoc, yLoc, paint);
        } else if(num == 6) {
            canvas.drawBitmap(whiteKingImage, xLoc, yLoc, paint);
        }
        if(num == -1) {
            canvas.drawBitmap(blackPawnImage, xLoc, yLoc, paint);
        } else if(num == -2) {
            canvas.drawBitmap(blackBishopImage, xLoc, yLoc, paint);
        } else if(num == -3) {
            canvas.drawBitmap(blackKnightImage, xLoc, yLoc, paint);
        } else if(num == -4) {
            canvas.drawBitmap(blackRookImage, xLoc, yLoc, paint);
        } else if(num == -5) {
            canvas.drawBitmap(blackQueenImage, xLoc, yLoc, paint);
        } else if(num == -6) {
            canvas.drawBitmap(blackKingImage, xLoc, yLoc, paint);
        }
    }
}
