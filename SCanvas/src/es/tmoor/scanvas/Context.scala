package es.tmoor.scanvas
import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.window

class Context(val context: CanvasRenderingContext2D) {

  def darkMode: Boolean = window.matchMedia("(prefers-color-scheme: dark)").matches

  object stroke {
    def colour = context.strokeStyle
    def colour_=(s: String): Unit = context.strokeStyle = s
    def colour_=(c: (Int, Int, Int)): Unit = {
      context.strokeStyle = f"#${c._1}%02x${c._2}%02x${c._3}%02x"
    }
    def width = context.lineWidth
    def width_=(w: Double): Unit = context.lineWidth = w
    def apply(): Unit = context.stroke()
  }

  object fill {
    def colour = context.fillStyle
    def colour_=(s: String): Unit = context.fillStyle = s
    def colour_=(c: (Int, Int, Int)): Unit = {
      context.fillStyle = f"#${c._1}%02x${c._2}%02x${c._3}%02x"
    }
    def apply(): Unit = context.fill()
  }

  object font {
    private var _style, _variant, _weight, _stretch, _lineHeight = "normal"
    private var _size = "medium"
    private var _fontFamily = "sans-serif"
    def style = _style
    def variant = _variant
    def weight = _weight
    def stretch = _stretch
    def size = _size
    def lineHeight = _lineHeight
    def fontFamily = _fontFamily

    def style_=(s: String): Unit = {
      _style = s
      context.font = font.toString
    }
    def variant_=(s: String): Unit = {
      _variant = s
      context.font = font.toString
    }
    def weight_=(s: String): Unit = {
      _weight = s
      context.font = font.toString
    }
    def stretch_=(s: String): Unit = {
      _stretch = s
      context.font = font.toString
    }
    def size_=(s: String): Unit = {
      _size = s
      context.font = font.toString
    }
    def lineHeight_=(s: String): Unit = {
      _lineHeight = s
      context.font = font.toString
    }
    def fontFamily_=(s: String): Unit = {
      _fontFamily = s
      context.font = font.toString
    }

    override def toString: String =
      s"${_style} ${_variant} ${_weight} ${_stretch} ${_size} ${_lineHeight} ${_fontFamily}"
  }

  def drawRoundedRectangle(x: Double, y: Double, w: Double, h: Double, r: Double): Unit = {
    context.beginPath()
    context.moveTo(x, y + r)
    context.arc(x + r, y + r, r, math.Pi, 3 * math.Pi / 2)
    context.lineTo(x + w - r, y)
    context.arc(x + w - r, y + r, r, 3 * math.Pi / 2, 0)
    context.lineTo(x + w, y + h - r)
    context.arc(x + w - r, y + h - r, r, 0, math.Pi / 2)
    context.lineTo(x + r, y + h)
    context.arc(x + r, y + h - r, r, math.Pi / 2, math.Pi)
    context.moveTo(x, y + r)
    context.arc(x + r, y + r, r, math.Pi, 3 * math.Pi / 2)
  }

  def drawRegularPolygon(sides: Int, boundingBox: (Double, Double, Double, Double)): Unit = {
    drawRegularPolygon(sides, boundingBox._1, boundingBox._2, boundingBox._3, boundingBox._4)
  }

  def drawRegularPolygon(sides: Int, x: Double, y: Double, w: Double, h: Double): Unit = {
    val anglePerSide = 2 * math.Pi / sides
    var pos = (0d, 0d)
    var maxX = 0d
    var maxY = 0d
    var minX = 0d
    var minY = 0d
    var angle = 0d
    val localPoints = for (i <- 0 to sides + 1) yield {
      pos = (pos._1 + math.cos(angle), pos._2 + math.sin(angle))
      angle += anglePerSide
      if (pos._1 > maxX) maxX = pos._1
      if (pos._2 > maxY) maxY = pos._2
      if (pos._1 < minX) minX = pos._1
      if (pos._2 < minY) minY = pos._2
      pos
    }
    val points = localPoints.map {
      case (px, py) => {
        (x + w * ((maxX - px) / (maxX - minX)), y + h * ((maxY - py) / (maxY - minY)))
      }
    }
    context.beginPath()
    context.moveTo(points.head._1, points.head._2)
    points.foreach { case (nextX, nextY) =>
      context.lineTo(nextX, nextY)
    }
  }
}
