package es.tmoor.scanvas
package templates


abstract class FixedAspectRatio(parent: Template) extends Template(parent) {
  def aspectRatio: Double
  def alignmentInParent: (Double, Double)
  def alignmentOffset: (Double, Double)
  
  override def globalX: Double = getOffset._1
  override def globalY: Double = getOffset._2
  override def globalWidth: Double = getSize._1
  override def globalHeight: Double = getSize._2

  private def getSize: (Double, Double) = {
    val calculatedWidth = parent.globalHeight * aspectRatio
    val calculatedHeight = parent.globalWidth / aspectRatio
    if (calculatedWidth > parent.globalWidth) {
      val paddingHeight = parent.globalHeight - calculatedHeight
      (relativeWidth * parent.globalWidth, relativeHeight * calculatedHeight)
    } else {
      val paddingWidth = parent.globalWidth - calculatedWidth
      (relativeWidth * calculatedWidth, relativeHeight * parent.globalHeight)
    }
  }

  private def getOffset: (Double, Double) = {
    val calculatedWidth = parent.globalHeight * aspectRatio
    val calculatedHeight = parent.globalWidth / aspectRatio
    if (calculatedWidth > parent.globalWidth) {
      val paddingHeight = parent.globalHeight - calculatedHeight
      (relativeX * parent.globalWidth, paddingHeight * alignmentInParent._2 + relativeY * calculatedHeight)
    } else {
      val paddingWidth = parent.globalWidth - calculatedWidth
      (paddingWidth * alignmentInParent._1 + relativeX * calculatedWidth, relativeY * parent.globalHeight)
    }
  }

}