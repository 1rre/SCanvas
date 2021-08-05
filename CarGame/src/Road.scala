import es.tmoor.scanvas._
import es.tmoor.scanvas.Bounds._
import es.tmoor.scanvas.shapes._

class Road(path: (Double, Double)*) extends Template {
  import math.{min, max}
  val (xL, xR, xT, xB) = path.foldLeft((1d, 0d, 1d, 0d)) { case ((l, r, t, b), (x, y)) =>
    (min(l, x), max(r, x), min(t, y), max(b, y))
  }
  def relativeBoundingBox: Bounds = {
    val x = max(xL, 0)
    val y = max(xT, 0)
    val w = min(xR - x, 1)
    val h = min(xB - y, 1)
    (x, y, w, h)
  }
  def render(context: Context, renderer: Renderer): Unit = {
    val gp = path.map { case (x, y) => renderer.boundingBox.relativeOffset(x, y) }
    context.moveTo(gp.head._1, gp.head._2)
    context.beginPath()
    context.strokeStyle = "white"
    gp.map { case (x, y) =>
      context.lineTo(x, y)
    }
    context.stroke()
  }
}
