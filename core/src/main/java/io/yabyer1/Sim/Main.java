package io.yabyer1.Sim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.physics.box2d.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private World world;
    private Body dynamicBody;
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        world = new World(new Vector2(0, -10), true); //gravity -9.8 m/s^2
        BodyDef bodyDef = new BodyDef(); // moveable object
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 10); // initial position
         dynamicBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape(); // shape of the object
        shape.setAsBox(1,1);
        //define physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.7f;
        dynamicBody.createFixture(fixtureDef); //attatch shape to body
        shape.dispose(); //avoid memory leak
    }


    @Override
    public void render() {
        // Step the physics simulation
        world.step(1 / 60f, 6, 2);

        // Check for touch input
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX() / 50f;
            float y = (Gdx.graphics.getHeight() - Gdx.input.getY()) / 50f;
            dynamicBody.setTransform(x, y, dynamicBody.getAngle());
        }

        // Clear the screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Render the texture at the body's position
        batch.begin();
        batch.draw(image, dynamicBody.getPosition().x * 50, dynamicBody.getPosition().y * 50);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        world.dispose();
    }
}
