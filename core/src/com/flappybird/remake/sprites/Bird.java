package com.flappybird.remake.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {

    /*Position of bird.*/
    private Vector3 position;

    /*Velocity of bird.*/
    private Vector3 velocity;

    /*Denoting bird's horizontal movement.*/
    private static final int MOVEMENT = 100;

    /*private Texture bird;*/

    /*As we'll be using an animated bird instead of an static bird.*/
    private Animation birdFromAnimatedBird;
    private Texture animatedBird;

    /*Same rectangle concept with the bird.*/
    private Rectangle boundsBird;

    /*Gravity to apply on bird.*/
    private static final int GRAVITY = -15;

    private Sound flap;

    /*Taking starting positions.*/
    public Bird(int x, int y) {

        /*Z will be always zero here as it's a 2D game.*/
        position = new Vector3(x, y, 0);

        /*Initial velocity will be zero.*/
        velocity = new Vector3(0, 0, 0);

        /*bird = new Texture("bird.png");*/

        /*Animated bird texture.*/
        animatedBird = new Texture("birdanimation.png");

        /*Getting a portion/region from birdAnimation. Here, totalFrames is 3 as I already know from the birdanimation.png
         * that it has 3 birds.*/
        birdFromAnimatedBird = new Animation(new TextureRegion(animatedBird), 3, 0.5f);

        boundsBird = new Rectangle(x, y, animatedBird.getWidth() / 3, animatedBird.getHeight());

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float deltaTime) {

        birdFromAnimatedBird.update(deltaTime);

        /*Applying gravity to bird.*/
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        /*Scaling through delta time.*/
        velocity.scl(deltaTime);

        /*Updating the position of bird after adding gravity & horizontal movement into delta time.*/
        position.add(MOVEMENT * deltaTime, velocity.y, 0);

        /*Making the scaled gravity normal again as we could use it somewhere somehow.*/
        velocity.scl(1/deltaTime);

        /*To let the boundsBird work properly, we need to update it every time the bird moves.*/
        boundsBird.setPosition(position.x, position.y);

        /*Letting the bird not disappear from the screen. Will be used later to kill the bird,
        * if she hits the ground.*/
        if(position.y < 0)
            position.y = 0;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdFromAnimatedBird.getFrame();
    }

    public Rectangle getBoundsBird() {
        return boundsBird;
    }

    public void jump() {
        /*As the bird already has effect on gravity, or a velocity on negative y, we need to make it positive
        * to make it jump.*/
        velocity.y = 250;
        flap.play(0.5f); /*We can set the volume here too.*/
    }

    public void dispose() {
        animatedBird.dispose();
        flap.dispose();
    }
}
