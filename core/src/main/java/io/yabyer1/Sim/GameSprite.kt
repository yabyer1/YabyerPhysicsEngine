package io.yabyer1.Sim

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

class GameSprite(
    texturePath: String,          // Path to the sprite texture
    world: World,                 // Box2D world where the body lives
    initialPosition: Vector2,     // Initial position of the sprite
    private val size: Vector2,                // Width and height of the sprite
    bodyType: BodyDef.BodyType = BodyDef.BodyType.DynamicBody  // Default to dynamic body
) {

    private val texture = Texture(texturePath)  // Load texture
    private val body: Body                     // Box2D body

    init {
        // Define the body and its properties
        val bodyDef = BodyDef().apply {
            type = bodyType
            position.set(initialPosition)
        }
        body = world.createBody(bodyDef)

        // Define the shape and physics properties (density, friction, restitution)
        val shape = PolygonShape().apply {
            setAsBox(size.x / 2, size.y / 2) // Centered box with half width & height
        }

        val fixtureDef = FixtureDef().apply {
            this.shape = shape
            density = 1f
            friction = 0.5f
            restitution = 0.3f  // Bounciness
        }

        body.createFixture(fixtureDef)
        shape.dispose() // Clean up the shape to avoid memory leaks
    }

    // Render the sprite on the screen
    fun render(batch: SpriteBatch) {
        batch.draw(texture, body.position.x - this.size.x / 2, body.position.y - size.y / 2, size.x, size.y)
    }

    // Apply a force to move the sprite
    fun applyForce(force: Vector2) {
        body.applyForceToCenter(force, true)
    }

    // Apply an impulse for sudden movement
    fun applyImpulse(impulse: Vector2) {
        body.applyLinearImpulse(impulse, body.worldCenter, true)
    }

    // Clean up the texture when the sprite is no longer needed
    fun dispose() {
        texture.dispose()
    }
}
