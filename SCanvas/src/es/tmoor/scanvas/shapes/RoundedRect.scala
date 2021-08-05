package es.tmoor.scanvas
package shapes

import org.scalajs.dom.raw.CanvasRenderingContext2D

object RoundedRect extends Shape {
  import Bounds._
  def draw(context: CanvasRenderingContext2D, b: Bounds, r: Double): Unit =
    draw(context, b._1, b._2, b._3, b._4, r)
  def draw(context: CanvasRenderingContext2D, x: Double, y: Double, w: Double, h: Double, r: Double): Unit = {
    context.moveTo(x, y + r)
    context.arc(x + r, y + r, r, math.Pi, 3 * math.Pi / 2)
    context.lineTo(x + w - r, y)
    context.arc(x + w - r, y + r, r, 3 * math.Pi / 2, 0)
    context.lineTo(x + w, y + h - r)
    context.arc(x + w - r, y + h - r, r, 0, math.Pi / 2)
    context.lineTo(x + r, y + h)
    context.arc(x + r, y + h - r, r, math.Pi / 2, math.Pi)
    context.lineTo(x, y + r)
  }

}
