package input

import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.Circle
import com.soywiz.korim.color.*
import com.soywiz.korma.geom.*
import kotlin.properties.*

class TouchController(isVisibleByDefault: Boolean = false) : Circle(radius = 64.0) {

    private var screenWidth by Delegates.notNull<Double>()
    private var screenHeight by Delegates.notNull<Double>()

    private var startMouseX = 0.0
    private var startMouseY = 0.0

    private var controlStartPositionX = 0.0
    private var controlStartPositionY = 0.0

    private val control = Circle(fill = Colors.ORANGE).apply {
        visible = false
    }

    init {
        visibility(isVisibleByDefault)
    }

    fun attachToStage(scene: Scene, onUpdate: (Angle?) -> Unit) {
        screenWidth = scene.sceneWidth.toDouble()
        screenHeight = scene.sceneHeight.toDouble()
        scene.sceneView.addChild(this)
        scene.sceneView.addChild(control)
        registerTouchListener(scene.sceneView, onUpdate)
    }

    private fun visibility(isVisible: Boolean) {
        visible = isVisible
        control.visible = isVisible
    }

    private var isTouched: Boolean = false
    private var touchCenter = IPoint(0, 0)

    private fun registerTouchListener(view: Container, updater: (Angle?) -> Unit) {
        view.stage?.addEventListener<MouseEvent> { mouseEvent ->
            if (mouseEvent.type == MouseEvent.Type.DOWN) {
                touchCenter = IPoint(mouseEvent.x, mouseEvent.y)
                this.position(128.0, screenHeight - 128)
                startMouseX = mouseEvent.x.toDouble()
                startMouseY = mouseEvent.y.toDouble()
                controlStartPositionX = 160.0 + control.width / 2
                controlStartPositionY = screenHeight - 96 + control.height / 2

                //  control.position(mouseEvent.x - (control.width / 2), mouseEvent.y - (control.height / 2))
                control.position(controlStartPositionX, controlStartPositionY)
                //println("MAUSE1 - x: $this.x??")
                visibility(true)
                isTouched = true
            }
            if (mouseEvent.type == MouseEvent.Type.DRAG) {
                //    control.position(mouseEvent.x - (control.width / 2), mouseEvent.y - (control.height / 2))
                val xh2 =
                    ((controlStartPositionX - (startMouseX - mouseEvent.x)) - controlStartPositionX) * ((controlStartPositionX - (startMouseX - mouseEvent.x)) - controlStartPositionX)
                val yk2 =
                    ((controlStartPositionY - (startMouseY - mouseEvent.y)) - controlStartPositionY) * ((controlStartPositionY - (startMouseY - mouseEvent.y)) - controlStartPositionY)
                if (xh2 + yk2 <= 55 * 55) {
                    control.position(
                        controlStartPositionX - (startMouseX - mouseEvent.x),
                        controlStartPositionY - (startMouseY - mouseEvent.y)
                    )
                }
                val angle = touchCenter.angleTo(IPoint(mouseEvent.x, mouseEvent.y))
                updater.invoke(angle)
            }
            if (mouseEvent.type == MouseEvent.Type.UP) {
                updater.invoke(null)
                visibility(false)
                isTouched = false
            }
        }
    }
}
