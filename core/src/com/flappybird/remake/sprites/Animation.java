package com.flappybird.remake.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

class Animation {

    /*Region of a texture.*/
    private Array<TextureRegion> arrayOfFrames;

    private float maxFrameTime;
    private float currentFrameTime;
    private int totalFrames;
    private int currentFrameIndex;

    Animation(TextureRegion region, int frameCount, float cycleTime){

        arrayOfFrames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        /*Pushing new textures in array.
         * Here, in the constructor of TextureRegion, firstly I put the texture I'm getting.
         * Then, the X position, the Y position, the frameWidth, and finally
         * the frameHeight.*/
        for(int i = 0; i < frameCount; i++){
            arrayOfFrames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }

        this.totalFrames = frameCount;
        maxFrameTime = cycleTime / frameCount;
        currentFrameIndex = 0;
    }

    void update(float dt) {

        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            currentFrameIndex++;
            currentFrameTime = 0;
        }
        if(currentFrameIndex >= totalFrames)
            currentFrameIndex = 0;
    }

    TextureRegion getFrame() {
        return arrayOfFrames.get(currentFrameIndex);
    }
}
