import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korinject.*
import com.soywiz.korma.geom.*
import stages.*
import kotlin.reflect.*
suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    override val mainScene: KClass<out Scene> = FirstScene::class

    override val size = SizeInt(480, 640) // Virtual Size
    override val windowSize = SizeInt(720, 1280) // Window Size

    override suspend fun AsyncInjector.configure() {
        mapPrototype { FirstScene() }
    }
}
