package es.tmoor.scanvas

import org.scalajs.dom.{html, raw, document, window}
import org.scalajs.dom.raw.CanvasRenderingContext2D

final class Canvas(canvas: html.Canvas, lightModeBackground: String, darkModeBackground: String) extends Template(null) {
  def this(e: raw.Element, l: String, d: String) = this(e.asInstanceOf[html.Canvas], l, d)
  def this(id: String, l: String = "0xffffff", d: String = "0x000000") = this(document.getElementById(id), l, d)
  
  canvas.setAttribute("tabindex", "0")
  canvas.height = canvas.clientHeight
  canvas.width = canvas.clientWidth

  window.addEventListener("resize", (e: raw.Event) => {
    canvas.height = canvas.clientHeight
    canvas.width = canvas.clientWidth
    render()
  }) 

  def rotation = 0

  override def globalX = 0
  override def globalY = 0
  override def globalWidth = canvas.width
  override def globalHeight = canvas.height

  canvas.onmousemove = (e) => render()

  def relativeOffset = (0,0)
  def relativeSize = (1,1)

  override val context: Context = new Context(canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D])
  
  def render(): Unit = {
    children.foreach(_.do_render())
  }
  
  override protected def renderAll(): Unit = {
    do_render()
  }

}
