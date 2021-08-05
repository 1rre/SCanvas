package es.tmoor.scanvas

import org.scalajs.dom.raw.CanvasRenderingContext2D

abstract trait Shape {
  type Context = CanvasRenderingContext2D
}