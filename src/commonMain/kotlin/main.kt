import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.view.*
import player.*

const val PADDING = 20.0

suspend fun main() = Korge(width = 512, height = 512) {

    val playerAnimations = PlayerAnimations.getInstance()

    val player1 = PlayerCharacter(playerAnimations, Key.W, Key.S, Key.A, Key.D).apply {
        scale(3.0)
        xy(100, 100)
    }

    text("Player 1 controls: ${player1.assignedKeyDesc}") { position(((this@Korge.width / 2.0) - width / 2), PADDING) }

    addChild(player1)

    addUpdater { time ->
        val scale = 16.milliseconds / time
        val disp = 2 * scale
        val keys = views.input.keys

        player1.handleKeys(keys, disp)

        if (keys[Key.L]) { player1.playAnimationLooped(playerAnimations.spriteAnimationDown, 100.milliseconds) }
        if (keys[Key.T]) { player1.playAnimation(spriteAnimation = playerAnimations.spriteAnimationDown, times = 3, spriteDisplayTime = 200.milliseconds) }
        if (keys[Key.C]) { player1.playAnimationForDuration(1.seconds, playerAnimations.spriteAnimationDown); player1.y -= 2 }
        if (keys[Key.ESCAPE]) { player1.stopAnimation() }
    }
}
