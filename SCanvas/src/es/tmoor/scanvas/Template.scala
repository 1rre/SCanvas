package es.tmoor.scanvas

import org.scalajs.dom.window
import collection.mutable.Buffer
import org.scalajs.dom.raw._
import BoundingBoxImplicits._

abstract class Template(parent: Template) {
  var enabled = true

  def rotation: Double
  def relativeOffset: (Double, Double)
  final def relativeX: Double = relativeOffset._1
  final def relativeY: Double = relativeOffset._2
  def relativeSize: (Double, Double)
  final def relativeWidth: Double = relativeSize._1
  final def relativeHeight: Double = relativeSize._2

  def globalX: Double = parent.globalX + parent.globalWidth * relativeX
  def globalY: Double = parent.globalY + parent.globalHeight * relativeY
  def globalWidth: Double = parent.globalWidth * relativeWidth
  def globalHeight: Double = parent.globalHeight * relativeHeight

  final def boundingBox: BoundingBox = (globalX, globalY, globalWidth, globalHeight)

  final def addTimer(after: Double, callback: Function0[_]) = window.setTimeout(callback, after)

  final def addTick(period: Double, callback: () => Unit) = window.setInterval(callback, period)

  def context: Context = parent.context

  val children = Buffer[Template]()

  def render(): Unit

  final def do_render(): Unit = {
    render()
    children.foreach(_.do_render())
  }

  protected def renderAll(): Unit = parent.renderAll()

  private var focused = false

  // The following events propagate from children to parents until "true" is returned
  def onClick(e: MouseEvent): Boolean = true
  def onCopy(e: ClipboardEvent): Boolean = true
  def onHover(e: MouseEvent): Boolean = true
  def onMouseDown(e: MouseEvent): Boolean = true
  def onMouseUp(e: MouseEvent): Boolean = true
  def onTouchStart(e: TouchEvent): Boolean = true
  def onTouchMove(e: TouchEvent): Boolean = true
  def onTouchEnd(e: TouchEvent): Boolean = true

  // The following events are called after focus is requested, either by this template (gain) or another
  def onFocusGain(): Unit = focused = true
  def onFocusLoss(): Unit = focused = false

  // The following events are only called when this template is focused
  def onPaste(e: ClipboardEvent): Unit = {}
  def onScroll(e: UIEvent): Unit = {}
  def onKeyDown(e: KeyboardEvent): Unit = {}
  def onKeyPress(e: KeyboardEvent): Unit = {}
  def onKeyUp(e: KeyboardEvent): Unit = {}

  // The following 

  final protected def do_onClick(e: MouseEvent): Boolean = {
    def childConsumed(): Boolean = {
      for (child <- children if child.enabled && child.boundingBox.intersects(e.clientX, e.clientY) && child.do_onClick(e))
        return true
      false
    }
    childConsumed() || onClick(e)
  }
  final protected def do_onHover(e: MouseEvent): Boolean = {
    def childConsumed(): Boolean = {
      for (child <- children if child.enabled && child.boundingBox.intersects(e.clientX, e.clientY) && child.do_onHover(e))
        return true
      false
    }
    childConsumed() || onHover(e)
  }
  final protected def do_onMouseDown(e: MouseEvent): Boolean = {
    def childConsumed(): Boolean = {
      for (child <- children if child.enabled && child.boundingBox.intersects(e.clientX, e.clientY) && child.do_onMouseDown(e))
        return true
      false
    }
    childConsumed() || onMouseDown(e)
  }
  final protected def do_onMouseUp(e: MouseEvent): Boolean = {
    def childConsumed(): Boolean = {
      for (child <- children if child.enabled && child.boundingBox.intersects(e.clientX, e.clientY) && child.do_onMouseUp(e))
        return true
      false
    }
    childConsumed() || onMouseUp(e)
  }
  final protected def do_onTouchStart(e: TouchEvent): Boolean = {
    def childConsumed(): Boolean = {
      for (child <- children if child.enabled && child.boundingBox.intersects(e.clientX, e.clientY) && child.do_onMouseUp(e))
        return true
      false
    }
    childConsumed() || onMouseUp(e)
  }
  final protected def do_onTouchMove(e: TouchEvent): Boolean = true
  final protected def do_onTouchEnd(e: TouchEvent): Boolean = true
}
