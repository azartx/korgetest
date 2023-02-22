package player

import KeyAssignment
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*
import utils.Const.DEF_VIRTUAL_HEIGHT

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
        val x = this.x + disp * cos(angle)
        val y = this.y + disp * sin(angle)

        println("Character moving coordinates - x: $x :: y: $y")

        if (x in 0.0..((stage?.width?.minus(this.width * 3)) ?: DEF_VIRTUAL_HEIGHT)) this.x = x
        if (y in 0.0..(stage?.height?.minus(this.height * 2) ?: DEF_VIRTUAL_HEIGHT)) this.y = y
    }
}
