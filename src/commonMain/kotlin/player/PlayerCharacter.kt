package player

import KeyAssignment
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*

class PlayerCharacter(
    animations: PlayerAnimations, upKey: Key, downKey: Key, leftKey: Key, rightKey: Key
) : Sprite(animations.spriteAnimationDown) {

    private val assignments = listOf(
        KeyAssignment(upKey, animations.spriteAnimationUp) { y -= it },
        KeyAssignment(downKey, animations.spriteAnimationDown) { y += it },
        KeyAssignment(leftKey, animations.spriteAnimationLeft) { x -= it },
        KeyAssignment(rightKey, animations.spriteAnimationRight) { x += it }
    )

    /** Allows to know the appropriate moment to stop the movement animation. */
    private var isMoving = false

    val assignedKeyDesc: String
        get() = assignments.map { it.key }.joinToString("/")

    fun handleKeys(inputKeys: InputKeys, disp: Double) {
        // Let's check if any movement keys were pressed during this frame
        val anyMovement: Boolean = assignments // Iterate all registered movement keys
            .filter { inputKeys[it.key] } // Check if this movement key was pressed
            .onEach {
                // If yes, perform its corresponding action and play the corresponding animation
                it.block(disp)
                playAnimation(it.animation)
            }
            .any()

        if (anyMovement != isMoving) {
            if (isMoving) stopAnimation()
            isMoving = anyMovement
        }
    }

    fun handleMouse(disp: Double, angle: Angle) {
        x += disp * cos(angle)
        y += disp * sin(angle)
    }
}
