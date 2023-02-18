package input

import com.soywiz.korev.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.Circle
import com.soywiz.korge.view.position
import com.soywiz.korim.color.*
import com.soywiz.korma.geom.*

class TouchController(isVisibleByDefault: Boolean = false) : Circle(radius = 64.0) {

    private val control = Circle(fill = Colors.ORANGE).apply {
        visible = false
    }

    init { visibility(isVisibleByDefault) }

    fun attachToStage(view: Container, onUpdate: (Angle?) -> Unit) {
        view.addChild(this)
        view.addChild(control)
        registerTouchListener(view, onUpdate)
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
                this.position(mouseEvent.x - (this.unscaledWidth / 2), mouseEvent.y - (this.unscaledHeight / 2))
                control.position(mouseEvent.x - (control.width / 2), mouseEvent.y - (control.height / 2))
                visibility(true)
                isTouched = true
            }
            if (mouseEvent.type == MouseEvent.Type.DRAG) {
                control.position(mouseEvent.x - (control.width / 2), mouseEvent.y - (control.height / 2))
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
