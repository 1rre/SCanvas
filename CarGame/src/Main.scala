import es.tmoor.scanvas._
import es.tmoor.scanvas.renderers.CanvasRenderer
import org.scalajs.dom.raw.{MouseEvent, CanvasRenderingContext2D}
import org.scalajs.dom.{document, window}
import Bounds._

object Main extends App {
  type Context = CanvasRenderingContext2D

  val canvas = new CanvasRenderer(document.getElementById("test_canvas"))
  canvas.canvas.focus()
  Body.addRenderer(canvas)

  val redCar = new Car("darkred", (.1, .1))
  canvas.addMaxWidthChild(redCar, 16d / 9)
  canvas.addCallback(
    10,
    () => {
      import math._
      redCar.currentRotation += redCar.rotationPerTick
      redCar.currentRotation %= 2 * Pi
      val cosX = cos(redCar.currentRotation)
      val sinX = sin(redCar.currentRotation)
      val scaleFactor = sqrt(1 / (abs(cosX) + abs(sinX)))
      val speedX = redCar.movementPerTick * cosX * scaleFactor
      val speedY = -redCar.movementPerTick * sinX * scaleFactor
      val (x,y) = redCar.currentPosition
      redCar.currentPosition = (x+speedX, y+speedY)
      canvas.render()
    }
  )
  /*
  val road = new Road(
    (0,0),
    (0.05,0),
    (0.15,0.1),
    (0.2,0.3),
    (0.3,0.5),
    (0.3,0.7),
    (0.6,0.8),
    (0.7,0.8),
    (1,1)
  ) 
  canvas.addChild(road)
  */
  canvas.render()
}
