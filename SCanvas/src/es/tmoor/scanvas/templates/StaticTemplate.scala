package es.tmoor.scanvas
package templates

import Bounds._

abstract class StaticTemplate extends Template {
  def aspectRatio: Double
  def alignmentOffset: (Double, Double) = (0,0)
  def alignmentInParent: (Double, Double) = (0.5,0.5)
  private def getSize(parent: Bounds): (Double, Double) = {
    val (screenX, screenY, screenW, screenH) = relativeBoundingBox
    val calculatedWidth = parent._4 * aspectRatio
    val calculatedHeight = parent._3 / aspectRatio
    if (calculatedWidth > parent._3) {
      val paddingHeight = parent._4 - calculatedHeight
      (screenW * parent._3, screenH * calculatedHeight)
    } else {
      val paddingWidth = parent._3 - calculatedWidth
      (screenW * calculatedWidth, screenH * parent._4)
    }
  }

  private def getOffset(parent: Bounds): (Double, Double) = {
    val (screenX, screenY, screenW, screenH) = relativeBoundingBox
    val calculatedWidth = parent._4 * aspectRatio
    val calculatedHeight = parent._3 / aspectRatio
    if (calculatedWidth > parent._3) {
      val paddingHeight = parent._4 - calculatedHeight
      (screenX * parent._3, paddingHeight * alignmentInParent._2 + screenY * calculatedHeight)
    } else {
      val paddingWidth = parent._3 - calculatedWidth
      (paddingWidth * alignmentInParent._1 + screenX * calculatedWidth, screenY * parent._4)
    }
  }

  override def boundingBox(parent: Bounds): Bounds = {
    val (w,h) = getSize(parent)
    val (x,y) = getOffset(parent)
    (x + alignmentOffset._1 * w, y + alignmentOffset._2 * h, w, h)
  }
}

