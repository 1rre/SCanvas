package es.tmoor.scanvas

object BoundingBoxImplicits {
  type BoundingBox = (Double, Double, Double, Double)
  implicit class IntersectImplicit(b: BoundingBox) {
    def intersects(x: Double, y: Double): Boolean = b._1 <= x && b._1 + b._3 >= x && b._2 <= x && b._2 + b._4 >= y 
  }
}