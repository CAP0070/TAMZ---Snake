package com.example.snake.snake;

import java.util.ArrayList;
import java.util.List;

public class ObjectsHandler {
    private List<Obstacle> obstacles = new ArrayList<>();

    public List<Obstacle> getHandler(){
        return this.obstacles;
    }

    public void addObstacle(Obstacle obstacle){
        obstacles.add(obstacle);
    }

    public void clearObstacles(){
        obstacles.clear();
    }
}
