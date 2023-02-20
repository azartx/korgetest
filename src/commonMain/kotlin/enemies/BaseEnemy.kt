package enemies

import com.soywiz.korge.view.*

open class BaseEnemy(initialAnimation: SpriteAnimation) : Sprite(initialAnimation) {

    var isAlive: Boolean = true
        protected set
}
