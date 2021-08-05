package es.tmoor.scanvas

object Bounds {
  type Bounds = (Double, Double, Double, Double)
  
  implicit class BoundsImplicits(b: Bounds) {
    // x & y are local percentages
    def relativeOffset(x: Double, y: Double): (Double, Double) = {
      (b._1 + b._3 * x, b._2 + b._4 * y)
    }
    // w & h are relative percentages
    def relativeSize(w: Double, h: Double): (Double, Double) = {
      (b._3 * w, b._4 * h)
    }

    def relativeBounds(b1: Bounds): Bounds = {
      val (x,y) = b.relativeOffset(b1._1, b1._2)
      val (w,h) = b.relativeSize(b1._3, b1._4)
      (x,y,w,h)
    }
    // x & y are global
    def intersects(x: Double, y: Double) = {
      x >= b._1 && x <= b._1 + b._3 && y >= b._2 && y <= b._2 + b._4
    }
  }
}