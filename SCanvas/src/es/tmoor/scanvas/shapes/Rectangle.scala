package es.tmoor.scanvas
package shapes

import org.scalajs.dom.raw.CanvasRenderingContext2D
import Bounds._

object Rectangle extends Shape {
  def draw(context: Context, b: Bounds): Unit = draw(context, b._1, b._2, b._3, b._4)
  def draw(context: Context, x: Double, y: Double, w: Double, h: Double): Unit = {
    context.beginPath()
    context.moveTo(x, y)
    context.lineTo(x + w, y)
    context.lineTo(x + w, y + h)
    context.lineTo(x, y + h)
    context.lineTo(x, y)
    context.closePath()
  }
}
