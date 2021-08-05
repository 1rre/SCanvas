package es.tmoor.scanvas
package templates

import Bounds._

import org.scalajs.dom.{window, document}
import org.scalajs.dom.html.{Canvas, Body}
import org.scalajs.dom.raw.{Element, MouseEvent, KeyboardEvent, CanvasRenderingContext2D}
import Bounds._
import collection.mutable.ListBuffer

abstract class MaxWidthTemplate extends Template {
  def maxWidth: Double
  override def boundingBox(parent: Bounds): Bounds = {
    val (x,y,w,h) = relativeBoundingBox
    val (pX,pY,pW,pH) = parent
    if (pH * maxWidth * h < w * pW) {
      (
        pX + (pW - pH * maxWidth * h) / 2,
        pY + pH * y,
        pH * maxWidth * h,
        h * pH
      )
    } else parent.relativeBounds(relativeBoundingBox)
  }
}
