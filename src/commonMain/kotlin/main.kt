import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korinject.*
import com.soywiz.korma.geom.*
import stages.*
import utils.Const.DEF_VIRTUAL_HEIGHT
import utils.Const.DEF_VIRTUAL_WIDTH
import kotlin.reflect.*
suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    lateinit var injector: AsyncInjector
    override val mainScene: KClass<out Scene> = FirstScene::class

    override val size = SizeInt(DEF_VIRTUAL_WIDTH.toInt(), DEF_VIRTUAL_HEIGHT.toInt()) // Virtual Size
    override val windowSize = SizeInt(720, 1280) // Window Size

    override suspend fun AsyncInjector.configure() {
        injector = this
        mapPrototype { FirstScene() }
    }
}
