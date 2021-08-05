package es.tmoor.scanvas
package renderers

import org.scalajs.dom.{window, document}
import org.scalajs.dom.html.{Canvas, Body}
import org.scalajs.dom.raw.{Element, MouseEvent, KeyboardEvent, CanvasRenderingContext2D}
import Bounds._
import collection.mutable.ListBuffer


class CanvasRenderer(c: Canvas, darkBG: String, lightBG: String) extends Renderer(null, Template.blank) {
  def this(e: Element, darkBG: String = "#525252", lightBG: String = "#FFFFFF") =
    this(e.asInstanceOf[Canvas], darkBG, lightBG)

  c.setAttribute("tabindex", "0")

  c.onclick = e => do_onClick(e)
  c.onmousemove = e => do_onMouseMove(e)
  c.onkeydown = e => do_onKeyDown(e)
  c.onkeyup = e => do_onKeyUp(e)

  override def onClick(e: MouseEvent): Boolean = {
    canvas.blur()
    false
  }

  override def canvas: Canvas = c
  canvas.height = canvas.clientHeight
  canvas.width = canvas.clientWidth
  def aspectRatio = canvas.width / canvas.height.toDouble
  override def boundingBox: Bounds = (0, 0, c.width, c.height)

  override val context: CanvasRenderingContext2D = c.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  if (darkMode) {
    canvas.style.backgroundColor = darkBG
    context.strokeStyle = lightBG
  } else {
    canvas.style.backgroundColor = lightBG
    context.strokeStyle = darkBG
  }

  override protected def renderInit(): Unit = {
    canvas.height = canvas.clientHeight
    canvas.width = canvas.clientWidth
  }
  override protected def renderFinish(): Unit = {}

}