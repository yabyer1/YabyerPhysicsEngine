package io.yabyer1.Sim

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

class MainGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var world: World
    private lateinit var sprite: GameSprite

    override fun create() {
        batch = SpriteBatch()
        world = World(Vector2(0f, -9.8f), true)  // Gravity

        // Create the sprite with initial position and size
        sprite = GameSprite(
            texturePath = "libgdx.png",
            world = world,
            initialPosition = Vector2(5f, 10f),
            size = Vector2(2f, 2f)
        )
    }

    override fun render() {
        world.step(1 / 60f, 6, 2)  // Step the world

        batch.begin()
        sprite.render(batch)  // Render the sprite
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        sprite.dispose()
        world.dispose()
    }
}

