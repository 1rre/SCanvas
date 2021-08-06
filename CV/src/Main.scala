import es.tmoor.scanvas._


object Main extends App {
  val canvas = new Canvas("tm_canvas")
  canvas.children += MenuBar
  canvas.render()
}
