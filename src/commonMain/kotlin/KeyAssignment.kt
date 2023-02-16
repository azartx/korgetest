import com.soywiz.korev.*
import com.soywiz.korge.view.*

data class KeyAssignment(
    val key: Key,
    val animation: SpriteAnimation,
    val block: (Double) -> Unit
)
