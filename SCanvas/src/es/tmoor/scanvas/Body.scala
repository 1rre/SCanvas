package es.tmoor.scanvas

import org.scalajs.dom.{html, document}

object Body {
  private val body = document.body.asInstanceOf[html.Body]
  private val renderers = collection.mutable.ListBuffer[Renderer]()
  def addRenderer(r: Renderer) = renderers += r
  body.onresize = _ => renderers.foreach(_.render())
}