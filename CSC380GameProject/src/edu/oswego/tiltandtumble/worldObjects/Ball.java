package edu.oswego.tiltandtumble.worldObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import edu.oswego.tiltandtumble.levels.UnitScale;

public class Ball extends AbstractWorldObject {
    public static final float FRICTION = 0.1f;
    public static final float DENSITY = 1.0f;
    public static final float RESTITUTION = 0.7f;
    public static final BodyType BODY_TYPE = BodyType.DynamicBody;
    public static final float ANGULAR_DAMPENING = 0.1f;
    public static final float LINEAR_DAMPENING = 0.1f;

    private final Texture texture;
    private final Sprite sprite;

    UnitScale scale;

    public Ball(Body body, UnitScale scale) {
        super(body);
        this.scale = scale;
        body.setUserData(this);

        // http://opengameart.org/content/orbs-wo-drop-shadows
        texture = new Texture(Gdx.files.internal("data/GreenOrb.png"));
        sprite = new Sprite(texture);

        // NOTE: the ball is 64x64 so we scale it down to 32x32
        // TODO: can i figure this out programmatically?
        sprite.setScale(0.5f);
    }

    public void applyLinearImpulse(float x, float y) {
        body.applyLinearImpulse(
            x,
            y,
            body.getPosition().x,
            body.getPosition().y,
            true);
//        body.applyForceToCenter(x, y, false);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    public float getX() {
        return scale.metersToPixels(body.getPosition().x) - (sprite.getWidth() * 0.5f);
    }

    public float getY() {
        return scale.metersToPixels(body.getPosition().y) - (sprite.getHeight() * 0.5f);
    }

    public void dispose() {
        texture.dispose();
    }
}
