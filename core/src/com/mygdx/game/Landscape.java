package com.mygdx.game;

public class Landscape {
    float x, y;
    float width, height;
    float dx;
    int type;

    public Landscape(float x, int type) {
        this.x = x;
        this.type = type;
        width = MyGame.SCR_WIDTH/5;
        height = MyGame.SCR_HEIGHT/2.4f;
        dx = -3;
    }

    void move(){
        x += dx;
    }
}
