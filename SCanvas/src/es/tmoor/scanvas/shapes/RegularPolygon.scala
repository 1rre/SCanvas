package es.tmoor.scanvas
package shapes

import Bounds._

object RegularPolygon extends Shape {
  def draw(context: Context, sides: Int, b: Bounds): Unit = {
    val (x,y,w,h) = b
    val anglePerSide = 2 * math.Pi / sides
    var pos = (0d,0d)
    var maxX = 0d
    var maxY = 0d
    var minX = 0d
    var minY = 0d
    var angle = 0d
    val localPoints = for (i <- 0 to sides+1) yield {
      pos = (pos._1 + math.cos(angle), pos._2 + math.sin(angle))
      angle += anglePerSide
      if (pos._1 > maxX) maxX = pos._1
      if (pos._2 > maxY) maxY = pos._2
      if (pos._1 < minX) minX = pos._1
      if (pos._2 < minY) minY = pos._2
      pos
    }
    val points =  localPoints.map {
      case (px,py) => {
        (x + w * ((maxX - px) / (maxX - minX)), y + h * ((maxY - py) / (maxY - minY)))
      }
    }
    context.beginPath()
    context.moveTo(points.head._1, points.head._2)
    points.foreach {
      case (nextX, nextY) => context.lineTo(nextX, nextY)
    }

  }
}