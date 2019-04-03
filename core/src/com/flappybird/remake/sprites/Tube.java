package com.flappybird.remake.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {

    /*As we know that our tubes are 52 pixels long.*/
    public static int TUBE_WIDTH = 52;

    /*The difference between the longest and shortest tube.*/
    private static final int FLUCTUATION = 125;

    /*The lowest gap between the top and bottom tube.*/
    private static final int TUBE_GAP = 80;

    /*The lowest possible opening length of a tube.*/
    private static final int LOWEST_OPENING = 120;

    private Texture topTube, bottomTube;
    private Vector2 positionOfTopTube, positionOfBottomTube;
    private Random random;

    /*We're creating rectangles what will bound the tubes to detect if someone touches it.
     * Obviously they'll be invisible.*/
    private Rectangle boundsTopTube, boundsBottomTube;

    /*Takes the initial position of a tube.*/
    public Tube(float f) {

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        random = new Random();

        /*Creating random lengthen tubes at initial position.*/
        positionOfTopTube = new Vector2(f, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionOfBottomTube = new Vector2(f, positionOfTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTopTube = new Rectangle(positionOfTopTube.x, positionOfTopTube.y,
                topTube.getWidth(), topTube.getHeight());
        boundsBottomTube = new Rectangle(positionOfBottomTube.x, positionOfBottomTube.y,
                bottomTube.getWidth(), bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPositionOfTopTube() {
        return positionOfTopTube;
    }

    public Vector2 getPositionOfBottomTube() {
        return positionOfBottomTube;
    }

    public void reposition(float f) {

        /*Repositioning the tube's position by getting another randomized position.*/
        getPositionOfTopTube().set(f, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        getPositionOfBottomTube().set(f, positionOfTopTube.y - TUBE_GAP - bottomTube.getHeight());

        /*We've to also implement the new position of the rectangles to bound the tubes.*/
        boundsTopTube.setPosition(getPositionOfTopTube().x, getPositionOfTopTube().y);
        boundsBottomTube.setPosition(getPositionOfBottomTube().x, getPositionOfBottomTube().y);
    }

    public boolean ifCollides(Rectangle playerObject) {
        return playerObject.overlaps(boundsTopTube) || playerObject.overlaps(boundsBottomTube);
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
