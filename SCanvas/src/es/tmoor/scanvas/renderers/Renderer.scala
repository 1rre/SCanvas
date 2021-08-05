package es.tmoor.scanvas

import org.scalajs.dom.{window, document}
import org.scalajs.dom.html.{Canvas, Body}
import org.scalajs.dom.raw.{Element, MouseEvent, KeyboardEvent, CanvasRenderingContext2D}
import Bounds._
import renderers._
import collection.mutable.ArrayBuffer
import es.tmoor.scanvas.templates._

abstract class Renderer(parent: Renderer, template: Template) {
  def priority: Int = 0
  type Context = CanvasRenderingContext2D
  def darkMode: Boolean = window.matchMedia("(prefers-color-scheme: dark)").matches

  val children = ArrayBuffer.from(template.children.map (t => new Renderer(this, t) {}))

  def callbacks: Seq[(Double, () => Unit)] = Nil
  def addCallback(millis: Double, fn: () => Unit) = window.setInterval(fn, millis)
  def addTimer(millis: Double, fn: () => Unit) = window.setTimeout(fn, millis)
  callbacks.foreach { case (interval, fn) =>
    window.setInterval(fn, interval)
  }

  def boundingBox: Bounds = template.boundingBox(parent.boundingBox)

  def canvas: Canvas = parent.canvas

  // TODO: Way to cease propagation to children (ie for menus)
  protected final def do_onClick(e: MouseEvent): Boolean = enabled && {
    val childConsumers = children.filter(child => child.enabled && child.onClick(e))
    childConsumers.size > 0 || onClick(e)
  }
  def onClick(e: MouseEvent): Boolean = {
    template.isInstanceOf[HasOnClick] && template.asInstanceOf[HasOnClick].onClick(e, this)
  }

  protected final def do_onMouseMove(e: MouseEvent): Boolean = enabled && {
    val childConsumers = children.filter(child => child.enabled && child.do_onMouseMove(e))
    childConsumers.size > 0 || onMouseMove(e)
  }
  def onMouseMove(e: MouseEvent): Boolean = {
    template.isInstanceOf[HasMouseMove] && template.asInstanceOf[HasMouseMove].onMouseMove(e, this)
  }

  protected final def do_onKeyDown(e: KeyboardEvent): Boolean = enabled && {
    val childConsumers = children.filter(child => child.enabled && child.onKeyDown(e))
    childConsumers.size > 0 || onKeyDown(e)
  }
  def onKeyDown(e: KeyboardEvent): Boolean = {
    template.isInstanceOf[HasKeyDown] && template.asInstanceOf[HasKeyDown].onKeyDown(e, this)
  }

  protected final def do_onKeyUp(e: KeyboardEvent): Boolean = enabled && {
    val childConsumers = children.filter(child => child.enabled && child.onKeyUp(e))
    childConsumers.size > 0 || onKeyUp(e)
  }
  def onKeyUp(e: KeyboardEvent): Boolean = {
    template.isInstanceOf[HasKeyUp] && template.asInstanceOf[HasKeyUp].onKeyUp(e, this)
  }

  def context: Context = Option(parent).map(_.context).getOrElse(null)
  var enabled = true

  protected def renderInit() = {
    val (x, y, w, h) = boundingBox
    context.translate(x + w / 2, y + h / 2)
    context.rotate(-template.rotation)
    context.translate(-x - w / 2, -y - h / 2)
  }
  protected def renderFinish() = {
    val (x, y, w, h) = boundingBox
    context.translate(x + w / 2, y + h / 2)
    context.rotate(template.rotation)
    context.translate(-x - w / 2, -y - h / 2)
  }

  final def render(): Unit = {
    renderInit()
    template.render(context, this)
    children.collect {
      case c if c.enabled => c.render()
    }
    renderFinish()
  }
}
