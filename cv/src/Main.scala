import es.tmoor.scanvas._
import renderers._
import org.scalajs.dom.raw.{MouseEvent, CanvasRenderingContext2D}
import org.scalajs.dom.{document, window}
import Bounds._

object Main extends App {
  type Context = CanvasRenderingContext2D

  val canvas = new CanvasRenderer(document.getElementById("tm_canvas"))
  
  Body.addRenderer(canvas)

  canvas.children += new Renderer(canvas, Header) {}
  canvas.children += new Renderer(canvas, MenuBar) {}

  canvas.render()
}
