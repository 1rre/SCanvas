import es.tmoor.scanvas._
import es.tmoor.scanvas.Bounds._
import es.tmoor.scanvas.shapes._
import org.scalajs.dom.raw.KeyboardEvent
import org.scalajs.dom.raw.CanvasRenderingContext2D

class Car(colour: String, size: (Double, Double) = (1, 1)) extends Template with HasKeyDown with HasKeyUp {

  def render(context: CanvasRenderingContext2D, renderer: Renderer): Unit = {}

  var currentRotation = 0d
  override def rotation: Double = currentRotation
  def rotationBySpeed = math.Pi / 50
  var leftEn = false
  var rightEn = false
  def leftTurn = (if (leftEn) rotationBySpeed else 0)
  def rightTurn = (if (rightEn) rotationBySpeed else 0)
  def wheelTurn = leftTurn - rightTurn
  def rotationPerTick = movementPerTick / speedIncrement * wheelTurn

  def speedIncrement = 0.01
  var forwardEn = false
  var backEn = false
  def forwardSpeed = (if (forwardEn) speedIncrement else 0)
  def backSpeed = (if (backEn) speedIncrement else 0)
  def movementPerTick = forwardSpeed - backSpeed

  override def onKeyDown(e: KeyboardEvent): Unit = e.key match {
    case "ArrowLeft" => leftEn = true
    case "ArrowRight" => rightEn = true
    case "ArrowUp" => forwardEn = true
    case "ArrowDown" => backEn = true
    case k => println(s"$k not a valid key down")
  }
  override def onKeyUp(e: KeyboardEvent): Unit = e.key match {
    case "ArrowLeft" => leftEn = false
    case "ArrowRight" => rightEn = false
    case "ArrowUp" => forwardEn = false
    case "ArrowDown" => backEn = false
    case k => println(s"$k not a valid key up")
  }

  var speed = 0d
  private abstract class Wheel(steers: Boolean) extends Template {
    def drawShape(c: Context, bb: Bounds, r: Double, pc: Double): Unit
    override def rotation = if (steers) 5 * wheelTurn else 0
    override def render(context: Context, renderer: Renderer): Unit = {
      context.fillStyle = "black"
      val (x, y, w, h) = renderer.boundingBox
      val radius = math.max(w, h) / 10
      drawShape(context, renderer.boundingBox, radius, 0.5)
      context.fill()
    }
  }
  private abstract class UpperWheel(steers: Boolean) extends Wheel(steers) {
    def drawShape(c: Context, bb: Bounds, r: Double, pc: Double): Unit = {
      val (x,y,w,h) = bb
      RoundedRect.Upper.draw(c, bb, r, pc)
    }
  }
  private abstract class LowerWheel(steers: Boolean) extends Wheel(steers) {
    def drawShape(c: Context, bb: Bounds, r: Double, pc: Double): Unit = {
      RoundedRect.Lower.draw(c, bb, r, pc)
    }
  }
  private object CarBody extends Template {
    override def render(context: Context, renderer: Renderer): Unit = {
      context.fillStyle = colour
      val (x, y, w, h) = renderer.boundingBox
      val radius = math.max(w, h) / 30
      RoundedRect.draw(context, renderer.boundingBox, radius)
      context.fill()
    }
    override def relativeBoundingBox: Bounds = (.05, .1, .9, .8)
  }
  var currentPosition = ((1-size._1)/2, (1-size._2)/2)
  override def relativeBoundingBox: Bounds = (currentPosition._1, currentPosition._2, size._1, size._2)
  override val children: Seq[Template] = Seq(
    new UpperWheel(false) {
      override def relativeBoundingBox: Bounds = (.15, 0, .2, .2)
    },
    new UpperWheel(true) {
      override def relativeBoundingBox: Bounds = (.65, 0, .2, .2)
    },
    new LowerWheel(false) {
      override def relativeBoundingBox: Bounds = (.15, .8, .2, .2)
    },
    new LowerWheel(true) {
      override def relativeBoundingBox: Bounds = (.65, .8, .2, .2)
    },
    CarBody
  )
}
