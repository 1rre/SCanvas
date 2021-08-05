import es.tmoor.scanvas._

import Bounds._
import shapes._
import templates._
import org.scalajs.dom.raw.MouseEvent

object Header extends Template {
  def relativeBoundingBox: Bounds = (0,0,1,0.1)
  object Logo extends StaticTemplate with HasMouseMove {
    def aspectRatio = 2/math.sqrt(3)
    override def alignmentInParent: (Double, Double) = (0.01, 0.5)
    override def alignmentOffset: (Double, Double) = (0.1, 0)
    def relativeBoundingBox: Bounds = (0, 0.15, 0.7, 0.7)
    var hasIntersection = false
    var innerScale = 1d
    val scalingIncrement = 0.03
    var scalingUp = true
    val maxScale = 1.65
    val minScale = 1d
    val tickRate = 5
    def scaleSpeed = scalingIncrement/(2 - innerScale)
    def setScale() = {
      if (scalingUp) {
        if (innerScale < maxScale) {
          innerScale = math.min(maxScale, innerScale + scalingIncrement)
          true
        } else {
          scalingUp = false
          false
        }
      } else {
        if (innerScale > minScale) {
          innerScale = math.max(minScale, innerScale - scalingIncrement)
          true
        } else {
          scalingUp = true
          false
        }
      }
    }
    def render(context: Context, renderer: Renderer): Unit = {
      val (x,y,w,h) = renderer.boundingBox
      val lineSize = 0.5 * renderer.boundingBox.relativeSize(0.4,0.4)._1
      context.lineWidth = innerScale * lineSize
      val innerHex = renderer.boundingBox.relativeBounds(0.3125,0.3125,0.375,0.375)
      context.strokeStyle = "lime"
      RegularPolygon.draw(context, 6, innerHex)
      context.stroke()
      context.lineWidth = lineSize
      
      context.strokeStyle = "dimgrey"
      RegularPolygon.draw(context, 6, renderer.boundingBox)
      context.stroke()
    }
    
    def onMouseMove(e: MouseEvent, r: Renderer): Boolean = {
      val last = hasIntersection
      def updateScaleTimer(): Unit = {
        if (hasIntersection != scalingUp) scalingUp = hasIntersection
        if (setScale()) {
          Main.canvas.render()
          r.addTimer(tickRate, () => updateScaleTimer())
        }
      }
      val outerHex = r.boundingBox.relativeBounds(-0.1,-0.1,1.2,1.2)
      r.context.save()
      RegularPolygon.draw(r.context, 6, outerHex)
      hasIntersection = r.context.isPointInPath(e.clientX, e.clientY)
      r.context.restore()
      val innerHex = r.boundingBox.relativeBounds(0.15,0.15,0.7,0.7)
      r.context.save()
      RegularPolygon.draw(r.context, 6, innerHex)
      val hasInnerIntersection = r.context.isPointInPath(e.clientX, e.clientY)
      r.context.restore()
      if (hasIntersection != last) {
        r.addTimer(tickRate, () => updateScaleTimer())
      }
      if (hasIntersection) {
        if (hasInnerIntersection) r.canvas.style.cursor = "pointer"
        else  r.canvas.style.cursor = "auto"
      }
      hasIntersection
    }
  }
  override val children: Seq[Template] = Seq (
  Logo
  )
  
  def render(context: Context, renderer: Renderer): Unit = {
    context.fillStyle = "black"
    Rectangle.draw(context, renderer.boundingBox)
    context.fill()
  }
  
}