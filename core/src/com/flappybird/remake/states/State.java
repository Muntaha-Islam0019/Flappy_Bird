package com.flappybird.remake.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    OrthographicCamera orthographicCamera; /*A camera to focus on a particular screen.*/
    GameStateManager gameStateManager; /*Manages various states.*/

    State(GameStateManager gsm) {
        this.gameStateManager = gsm;
        orthographicCamera = new OrthographicCamera();
        /*A pointer what has XYZ co-ordinates.*/
        new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sB);
    public abstract void dispose();
}
