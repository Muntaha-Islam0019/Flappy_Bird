package com.flappybird.remake.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State s) {
        states.push(s);
    }

    void set(State s) {
        states.pop().dispose();
        states.push(s);
    }

    /*It takes delta time (Change in time between two renders) and updates the delta time of the top state.*/
    public void update(float dt) {
        states.peek().update(dt);
    }

    /*It takes a spritebatch and renders it over the screen.*/
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
