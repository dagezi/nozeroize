package io.github.dagezi.vbw

enum class Directive {
    L, H, V, Q, T, C, S, A, Z
}

abstract class Segment(val dir: Directive, val start: Vector, val end: Vector) {
    open fun area(p: Vector): Double =
            (start - p).cross(end - start) / 2.0
    open val boundingRect = Rect(start, end)
    // TODO: Declare reverse and toDirective
}

class LineSegment(dir: Directive, start: Vector, end: Vector) : Segment(dir, start, end) {
}

class QuadraticCurveSegment(dir: Directive, start: Vector, end: Vector, val c0: Vector) :
        Segment(dir, start, end) {
    // TODO: Override area and boundingRect
}

class CubicCurveSegment(dir: Directive, start: Vector, end: Vector,
                        val c0: Vector, val c1: Vector) :
        Segment(dir, start, end) {
    // TODO: Override area and boudingRect
}

class ArcSegment(dir: Directive, start: Vector, end: Vector,
                 val radius: Vector, val rotation: Double, val largeArc: Boolean, val sweep: Boolean) :
        Segment(dir, start, end) {
    // TODO: Override area and boudingRect
}
