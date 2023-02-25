package player

import KeyAssignment
import MyModule
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.Circle
import com.soywiz.korinject.*
import com.soywiz.korma.geom.*
import kotlinx.coroutines.*
import utils.Const.DEF_PLAYER_ANGLE
import utils.Const.DEF_VIRTUAL_HEIGHT

class PlayerCharacter(
    private val animations: PlayerAnimations, upKey: Key, downKey: Key, leftKey: Key, rightKey: Key
) : Sprite(animations.spriteAnimationDown) {

    private val bulletsJob = SupervisorJob()

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

    private var playerAngle = Angle.fromDegrees(DEF_PLAYER_ANGLE)

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val views = withInjector(MyModule.injector) { views() }
            addUpdater {
                isMoving = views.stage.mouse.currentEvent?.type == MouseEvent.Type.DRAG
            }
        }
    }

    // bullets logic
    init {
        CoroutineScope(Dispatchers.Default + bulletsJob).launch {
            while (true) {
                // if has enemies == true and if isMoving == false // todo
                delay(200)
                if (!isMoving) {

                    val bullet = Circle(radius = 6.0)
                    val bulletAngle = playerAngle
                    val bulletStartPoint = IPoint(
                        x = x + width * cos(bulletAngle),
                        y = y + height * sin(bulletAngle)
                    )
                    parent?.addChild(bullet)
                    bullet.position(bulletStartPoint)

                    launch {
                        // bulletMoving
                        while (bullet.x > 0 && bullet.x < stage?.width!! || bullet.y > 0 && bullet.y < stage?.height!!) {
                            delay(100)
                            bullet.position(bullet.x + 30 * cos(bulletAngle), bullet.y + 30 * sin(bulletAngle))
                        }
                        if (parent?.children?.contains(bullet) == true) {
                            parent?.removeChild(bullet)
                        }
                    }
                }
            }
        }
    }

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

    var movingAnimationJob: Job? = null
    fun handleMouse(disp: Double, angle: Angle) {
        playerAngle = angle
        val x = this.x + disp * cos(angle)
        val y = this.y + disp * sin(angle)

        if (x in 0.0..((stage?.width?.minus(this.width * 3)) ?: DEF_VIRTUAL_HEIGHT)) this.x = x
        if (y in 0.0..(stage?.height?.minus(this.height * 2) ?: DEF_VIRTUAL_HEIGHT)) this.y = y

        when {
            angle.degrees > 225 && angle.degrees < 315 -> {
                playAnimation(animations.spriteAnimationUp)
            }
            angle.degrees > 135 && angle.degrees < 225 -> {
                playAnimation(animations.spriteAnimationLeft)
            }
            angle.degrees > 45 && angle.degrees < 135 -> {
                playAnimation(animations.spriteAnimationDown)
            }
            angle.degrees > 315 && angle.degrees < 360 || angle.degrees > 0 && angle.degrees < 45 -> {
                playAnimation(animations.spriteAnimationRight)
            }
        }
    }

    // todo need to implement logic of destroying character after dying of some els lifecycle events
    fun onDestroy() {
        bulletsJob.cancel()
    }
}
