package com.example.avi.snakeandroid3.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.avi.snakeandroid3.enums.TileType;

/**
 * Created by Avi on 14/06/2018.
 */

public class SnakeView extends View {
    private Paint mPaint = new Paint();
    private TileType snakeViewMap[][];

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSnakeViewMap( TileType[][] map){
        this.snakeViewMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(snakeViewMap != null){
            float tileSizeX = canvas.getWidth() / snakeViewMap.length;
            float tileSizeY = canvas.getHeight() / snakeViewMap[0].length;

            float circleSize = Math.min(tileSizeX, tileSizeY) / 2;

            for (int x = 0; x < snakeViewMap.length; x++){
                for(int y=0; y < snakeViewMap[x].length; y++){
                    switch (snakeViewMap[x][y]){

                        case Empty:
                            mPaint.setColor(Color.WHITE);
                            break;
                        case SnakeHead:
                            mPaint.setColor(Color.BLUE);
                            break;
                        case SnakeTail:
                            mPaint.setColor(Color.GREEN);
                            break;
                        case Food:
                            mPaint.setColor(Color.RED);
                            break;
                        case Wall:
                            mPaint.setColor(Color.BLACK);
                            break;
                    }

                    //centered circles on tiles
                    canvas.drawCircle(x * tileSizeX + tileSizeX / 2f,
                                y * tileSizeY + tileSizeY / 2f ,
                                    circleSize, mPaint);
                }
            }
        }
    }
}
