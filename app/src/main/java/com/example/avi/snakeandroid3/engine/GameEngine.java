package com.example.avi.snakeandroid3.engine;

import com.example.avi.snakeandroid3.classes.Coordinates;
import com.example.avi.snakeandroid3.enums.Direction;
import com.example.avi.snakeandroid3.enums.GameStatus;
import com.example.avi.snakeandroid3.enums.TileType;
import com.example.avi.snakeandroid3.views.SnakeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Avi on 14/06/2018.
 */

public class GameEngine {
    public static final int GameWidth = 10;
    public static final int GameHeight = 10;

    private Random randomCoordinate = new Random();

    private List<Coordinates> walls = new ArrayList<>();
    private List<Coordinates> snake = new ArrayList<>();
    private List<Coordinates> food = new ArrayList<>();
    private boolean eaten = false;

    private Coordinates getSnakeHead(){
        return snake.get(0);
    }

    private Direction currentDirection = Direction.Right;
    private GameStatus currentGameStatus = GameStatus.Running;

    public GameEngine(){

    }

    public void initGame(){
        CreateWalls();
        CreateSnake();

        CreateFood();
    }


    public void updateDirection(Direction dir){
        if(Math.abs(dir.ordinal() - currentDirection.ordinal())%2 ==1)
            currentDirection = dir;
    }

    public void update() {
        //update snake
        switch (currentDirection){
            case Up:
                moveSnake(0,-1);
                break;
            case Right:
                moveSnake(1,0);
                break;
            case Down:
                moveSnake(0,1);
                break;
            case Left:
                moveSnake(-1,0);
                break;
        }

        //check for game over - wall collision
        for( Coordinates wall: walls){
            if( snake.get(0).equals(wall)){
                    currentGameStatus = GameStatus.Over;
            }
        }

        //check for game over - tail collision
        for(int i = 1; i < snake.size(); i++){
            if( snake.get(i).equals(snake.get(0))){
                currentGameStatus = GameStatus.Over;
            }
        }

        //check for food
        for( Coordinates foodCord: food){
            if( snake.get(0).equals(foodCord)){
                food.remove(foodCord);
                CreateFood();
                eaten = true;
            }
        }




    }

    private void moveSnake(int x, int y) {

        if(eaten)
        {
            snake.add(new Coordinates(snake.get(snake.size() -1).getX(),
                    snake.get(snake.size() -1).getY()));
            eaten = false;
        }

        for(int i= snake.size()-1; i>0 ; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }



        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
    }

    public TileType[][] getMap(){
        TileType[][] map = new TileType[GameWidth][GameHeight];

        for (int x = 0; x < GameWidth; x++)
            for( int y = 0; y < GameHeight; y++)
                map[x][y] = TileType.Empty;


        //set tile type for walls
        for (Coordinates wall: walls)
        {
            map[wall.getX()][wall.getY()] = TileType.Wall;
        }

        //set tile type for snake
        for(Coordinates snakeCord: snake){
            map[snakeCord.getX()][snakeCord.getY()] = TileType.SnakeTail;
        }

        //set tile type for food
        for (Coordinates foodCord: food)
        {
            map[foodCord.getX()][foodCord.getY()] = TileType.Food;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        return map;
    }

    private void CreateSnake() {
        snake.clear();
        snake.add(new Coordinates(4,4));
        snake.add(new Coordinates(4,5));
    }

    private void CreateWalls(){
        //top and bot
        for(int x = 0; x < GameHeight; x++) {
            walls.add(new Coordinates(x, 0));
            walls.add(new Coordinates(x, GameHeight - 1));
        }
        //sides
        for(int y = 0; y < GameHeight; y++) {
            walls.add(new Coordinates(0, y));
            walls.add(new Coordinates(GameWidth-1, y));
        }

    }

    private void CreateFood() {
        Coordinates foodCoordinates = null;

        boolean added = false;

        while( !added ) {
            //Add coordinate that is not wal
            int x = randomCoordinate.nextInt(GameWidth - 2) + 1;
            int y = randomCoordinate.nextInt(GameHeight - 2) + 1;

            foodCoordinates = new Coordinates(x, y);
            boolean collision = false;
            for (Coordinates snakeCord : snake) {
                if (snakeCord.equals(foodCoordinates)) {
                    collision = true;
                }
            }

            for( Coordinates foodCord: food)
            if(foodCord.equals( foodCoordinates)) {
                collision = true;
            }

            added = !collision;
        }

        food.add(foodCoordinates);
    }

    public GameStatus getCurrentGameStatus() {
        return currentGameStatus;
    }
}
