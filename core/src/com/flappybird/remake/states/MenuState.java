package com.flappybird.remake.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.remake.FlappyBirdRemake;

public class MenuState extends State {

    private Texture background;
    private Texture playButton;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        orthographicCamera.setToOrtho(false, FlappyBirdRemake.WIDTH/2, FlappyBirdRemake.HEIGHT/2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
    }

    /*So if the user touches the screen, the state will change to PlayState.*/
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    /*This portion will help the program to update the screen in particular situations.
     * Now, as I have written handleInput() inside it, this method will update the screen
     * when only it gets an input, in this case, only a touch.*/
    @Override
    public void update(float dt) {
        handleInput();
    }

    /*This actually behaves as a container.
     * Firstly it put everything in a container.
     * Then it starts rendering them.
     * And also, the display is mainly a 1st quadrant of a XY plane.
     * The lower left position is (0, 0).*/
    @Override
    public void render(SpriteBatch sb) {

        /*As I wanna move my menu state with the screen(Invisibly).*/
        sb.setProjectionMatrix(orthographicCamera.combined);

        /*Creating the container.*/
        sb.begin();

        /*Starting drawing things on the screen.
         * Here, first XY position is where I wanna put the image.
         * Later ones are the desired width and height of my image.*/
        sb.draw(background, 0,0);

        /*Setting the X co-ordinates value as (FlappyBirdRemake.WIDTH/2) - (playButton.getWidth()/2).
         * Because, if I did just (FlappyBirdRemake.WIDTH/2), then the picture will be rendered from the middle.
         * So, the button will be on the right side of he screen.
         * But, what I wanna do is to put the button in perfectly center.
         * Therefore, I moved it or left at the value of (playButton.getWidth()/2).
         *
         * Also, If I do not put the height and width of my texture, it'll be rendered as per it's size.*/
        sb.draw(playButton, orthographicCamera.position.x - playButton.getWidth() / 2, orthographicCamera.position.y);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}