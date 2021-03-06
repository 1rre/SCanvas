import mill._, scalalib._, scalajslib._

object SCanvas extends ScalaJSModule {
  def scalaVersion = "2.13.6"
  def scalaJSVersion = "1.6.0"

  def ivyDeps = Agg(ivy"org.scala-js:scalajs-dom_sjs1_2.13:1.1.0")
}

object CarGame extends ScalaJSModule {
  def scalaVersion = "2.13.6"
  def scalaJSVersion = "1.6.0"

  def moduleDeps = Seq(SCanvas)
}


object CV extends ScalaJSModule {
  def scalaVersion = "2.13.6"
  def scalaJSVersion = "1.6.0"

  def moduleDeps = Seq(SCanvas)
}