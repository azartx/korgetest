import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korinject.*
import stages.*
import kotlin.reflect.*
suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    override val mainScene: KClass<out Scene> = FirstStage::class

    override suspend fun AsyncInjector.configure() {
        mapPrototype { FirstStage() }
    }
}
