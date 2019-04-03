package com.flappybird.remake.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flappybird.remake.FlappyBirdRemake;
import com.flappybird.remake.sprites.Bird;
import com.flappybird.remake.sprites.Tube;

import static com.flappybird.remake.sprites.Tube.TUBE_WIDTH;

public class PlayState extends State {

    /*The space between every tube.*/
    private static final int TUBE_SPACING = 100;

    /*How many tubes will remain at a same time on the viewport(both the top tube and bottom tube).*/
    private static final int TUBE_COUNT = 4;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPositionFirst, groundPositionSecond;

    /*To put the ground a bit lower.*/
    private static final int GROUND_Y_OFFSET = -50;

    /*private Tube tube;*/

    /*Using the default Array class provided by BadLogic's GDX.
     * */
    private Array <Tube> tubeArray;

    PlayState(GameStateManager gsm) {

        super(gsm);

        bird = new Bird(35, 300);

        /*This portion is for focusing on a particular point of the screen.
         * At this point we're just focusing on the bird what is at (50, 50) position.
         * It'll show the user that the bird is not the world, it's a part of the world.
         * So, he/she'll get a bewitching experience.*/
        orthographicCamera.setToOrtho(false, FlappyBirdRemake.WIDTH/2,
                FlappyBirdRemake.HEIGHT/2);

        background = new Texture("bg.png");

        /*Creates initial tube at position 100.*/
        /*tube = new Tube(100);*/

        ground = new Texture("ground.png");

        /*Setting the ground at two different positions as we wanna show the user that the ground is continuous.*/
        groundPositionFirst = new Vector2((orthographicCamera.position.x -
                orthographicCamera.viewportWidth / 2), GROUND_Y_OFFSET);
        groundPositionSecond = new Vector2(((orthographicCamera.position.x -
                orthographicCamera.viewportWidth / 2) + ground.getWidth()), GROUND_Y_OFFSET);

        tubeArray = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++)
            tubeArray.add(new Tube(i * (TUBE_SPACING + TUBE_WIDTH)));
    }

    @Override
    protected void handleInput() {
        /*So, if one touch the screen, the bird will jump!*/
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {

        handleInput();
        updateGround();
        bird.update(dt);

        /*As, we want the cam to move with the bird, and a little bit on the X offset of it.*/
        orthographicCamera.position.x = bird.getPosition().x + 80;

        /*This is the logic behind generating new tube at the later viewport.
         * */
        for(int i = 0; i < tubeArray.size; i++){
            Tube tube = tubeArray.get(i);

            if(orthographicCamera.position.x - (orthographicCamera.viewportWidth / 2) >
                    tube.getPositionOfTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPositionOfTopTube().x  + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.ifCollides((bird.getBoundsBird())))
                gameStateManager.set(new MenuState(gameStateManager));
        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gameStateManager.set(new MenuState(gameStateManager));

        orthographicCamera.update();
    }

    private void updateGround() {

        /*Updating the first ground's position after it appear once.*/
        if(orthographicCamera.position.x - (orthographicCamera.viewportWidth / 2) >
                groundPositionFirst.x + ground.getWidth()) {
            groundPositionFirst.add(ground.getWidth() * 2, 0);
        }

        /*Updating the second ground's position after it appear once.*/
        if(orthographicCamera.position.x - (orthographicCamera.viewportWidth / 2) >
                groundPositionSecond.x + ground.getWidth()) {
            groundPositionSecond.add(ground.getWidth() * 2, 0);
        }
    }

    @Override
    public void render(SpriteBatch sB) {

        sB.begin();

        /*As, we'll only render the portion of the screen we're showing,
         * making the sprite batch to move with the camera.*/
        sB.setProjectionMatrix(orthographicCamera.combined);

        /*It'd be drawn at the center if I put just orthographicCamera.position.x at position x.*/
        sB.draw(background, orthographicCamera.position.x - (orthographicCamera.viewportWidth / 2), 0);

        sB.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        /*Drawing the tubes of random length.*/
        /*sB.draw(tube.getTopTube(), tube.getPositionOfTopTube().x, tube.getPositionOfTopTube().y);
        sB.draw(tube.getBottomTube(), tube.getPositionOfBottomTube().x, tube.getPositionOfBottomTube().y);*/

        for(Tube tube : tubeArray) {
            sB.draw(tube.getTopTube(), tube.getPositionOfTopTube().x, tube.getPositionOfTopTube().y);
            sB.draw(tube.getBottomTube(), tube.getPositionOfBottomTube().x, tube.getPositionOfBottomTube().y);
        }

        sB.draw(ground, groundPositionFirst.x, groundPositionFirst.y);
        sB.draw(ground, groundPositionSecond.x, groundPositionSecond.y);

        sB.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubeArray)
            tube.dispose();
    }
}
