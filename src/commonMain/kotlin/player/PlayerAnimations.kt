package player

import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import kotlin.native.concurrent.*


class PlayerAnimations private  constructor(characterBitmap: Bitmap) {

    @ThreadLocal()
    companion object {
        private var instance: PlayerAnimations? = null
        suspend fun getInstance() = if (instance == null)
            PlayerAnimations(resourcesVfs["character.png"].readBitmap()) else instance!!
    }

    val spriteAnimationLeft = SpriteAnimation(
        spriteMap = characterBitmap,
        spriteWidth = 16,
        spriteHeight = 32,
        marginTop = 96,
        marginLeft = 1,
        columns = 4,
        rows = 1
    )

    val spriteAnimationRight = SpriteAnimation(
        spriteMap = characterBitmap,
        spriteWidth = 16,
        spriteHeight = 32,
        marginTop = 32,
        marginLeft = 1,
        columns = 4,
        rows = 1
    )

    val spriteAnimationUp = SpriteAnimation(
        spriteMap = characterBitmap,
        spriteWidth = 16,
        spriteHeight = 32,
        marginTop = 64,
        marginLeft = 1,
        columns = 4,
        rows = 1
    )

    val spriteAnimationDown = SpriteAnimation(
        spriteMap = characterBitmap,
        spriteWidth = 16,
        spriteHeight = 32,
        marginTop = 0,
        marginLeft = 1,
        columns = 4,
        rows = 1
    )
}
