package es.tmoor.scanvas
import Bounds._
import org.scalajs.dom.raw.{MouseEvent, KeyboardEvent, CanvasRenderingContext2D}
import org.scalajs.dom.window

abstract class Template {
  type Context = CanvasRenderingContext2D
  def rotation = 0d
  def relativeBoundingBox: Bounds
  def boundingBox(parent: Bounds) = parent.relativeBounds(relativeBoundingBox)
  val children: Seq[Template] = Nil
  def render(context: Context, renderer: Renderer): Unit
}

abstract trait HasOnClick {
  def onClick(e: MouseEvent, r: Renderer): Boolean
}

abstract trait HasMouseMove {
  def onMouseMove(e: MouseEvent, t: Renderer): Boolean
}

abstract trait HasKeyDown {
  def onKeyDown(e: KeyboardEvent, t: Renderer): Boolean
}

abstract trait HasKeyUp {
  def onKeyUp(e: KeyboardEvent, t: Renderer): Boolean
}

object Template {
  type Context = CanvasRenderingContext2D
  val blank = new Template {
    def relativeBoundingBox: Bounds.Bounds = (0, 0, 1, 1)
    override def render(context: Context, parent: Renderer): Unit = {}
  }
}
