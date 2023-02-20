package enemies

import com.soywiz.klock.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.*
import kotlinx.coroutines.*

class Enemy(private val animations: EnemiesAnimation) : BaseEnemy(animations.spriteAnimationDown) {

    private var enemyMovingJob: Job? = null

    suspend fun init(stage: Stage) {
        stage.addChild(this)
        val startPositionX = (0..stage.width.toInt()).random()
        val startPositionY = (0..stage.height.toInt()).random()
        scale(3.0)
        position(startPositionX, startPositionY)

        startEnemyLifecycle(stage)
    }

    private suspend fun startEnemyLifecycle(stage: Stage) {
        stopEnemyLifecycle()
        isAlive = true
        enemyMovingJob = CoroutineScope(Dispatchers.Default).launch {
            do {
                move(stage)
                delay(8000)
            } while (isAlive)
        }
    }

    private fun stopEnemyLifecycle() {
        enemyMovingJob?.cancel()
        enemyMovingJob = null
        isAlive = false
    }

    suspend fun move(stage: Stage) {
        val animation = animations.random()
        val randomX = (0..stage.width.toInt()).random()
        val randomY = (0..stage.height.toInt()).random()

        playAnimation(animation)
        moveTo(randomX, randomY, TimeSpan(2500.0))
        stopAnimation()
    }

    suspend fun makeHit() {

    }

    suspend fun die() {

        stopEnemyLifecycle()
    }
}
