import es.tmoor.scanvas.Template
import es.tmoor.scanvas.templates.FixedAspectRatio

object MenuBar extends Template(Main.canvas) {
  def rotation = 0
  def relativeOffset = (0,0)
  def relativeSize = (1,0.1)
  def render(): Unit = {
    println(boundingBox)
    context.fill.colour = (0,0,0)
    context.drawRegularPolygon(4, boundingBox)
    context.fill()
  }

  object Logo extends FixedAspectRatio(this) {
    def alignmentInParent: (Double, Double) = (0,0.5)
    def alignmentOffset: (Double, Double) = (0.5,0)
    def rotation = 0
    def aspectRatio = 2/math.sqrt(3)
    def relativeOffset = (0.1,0.1)
    def relativeSize = (0.8,0.8)

    def render(): Unit = {
      context.stroke.colour = (0,255,0)
      context.drawRegularPolygon(6, boundingBox)
      context.stroke()
    }
  }
  children += Logo
}
