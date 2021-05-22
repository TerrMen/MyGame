package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class Man {
    float x, y;
    float width, height;
    float dx, dy;
    int faza;
    Texture imgMan[] = new Texture[29];
    long timeLastFaza, timeInterval = 30;
    int condition;
    public static final int GO = 0;
    public static final int JUMP_UP = 1;
    public static final int JUMP_DOWN = 2;
    public static final int FAIL = 3;

    public Man(float x, float y) {
        this.x = x;
        this.y = y;
        width = MyGame.SCR_HEIGHT / 3f;
        height = MyGame.SCR_HEIGHT / 3f;
    }
    Sound sndShot;
    void move() throws InterruptedException {
        sndShot = Gdx.audio.newSound(Gdx.files.internal("shag.mp3"));
        if (TimeUtils.millis() > timeLastFaza + timeInterval) {
            timeLastFaza = TimeUtils.millis();
            if (condition == GO) if (++faza == 20) faza = 0;
            if (condition == JUMP_UP) {
                faza = 0;
                y += 10;
                if (y > MyGame.SCR_HEIGHT / 1.8f){
                    condition = JUMP_DOWN;
                }
            }
            if (condition == JUMP_DOWN) {
                y -= 10;
                if (y <= MyGame.SCR_HEIGHT / 5){
                    sndShot.play();
                    condition = GO;
                }
            }
            if (condition == FAIL) {
                for(int i=21; i<imgMan.length; i++){
                    y = y - 5;
                }
            }
        }
    }
}