package com.mygdx.game;

public class Fon {
    float x, y;
    float width, height;
    float dx;

    public Fon(float x, float y) {
        this.x = x;
        this.y = y;
        width = MyGame.SCR_WIDTH;
        height = MyGame.SCR_HEIGHT;
        dx = -1;
    }

    void move(){
        x += dx;
        if(x < - MyGame.SCR_WIDTH) x = MyGame.SCR_WIDTH;
    }
}
