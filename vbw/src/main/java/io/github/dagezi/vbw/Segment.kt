package io.github.dagezi.vbw

enum class Directive {
    L, H, V, Q, T, C, S, A, Z
}

abstract class Segment(val dir: Directive, val start: Vector, val end: Vector) {
    // Directed area: Positive if the path from p, start, end and back to p
    // is clockwise.
    open fun area(p: Vector): Double =
            (start - p).cross(end - start) / 2.0

    open val boundingRect = Rect(start, end)

    abstract fun reversed() : Segment
    // TODO: Declare toDirective
}

class LineSegment(dir: Directive, start: Vector, end: Vector) : Segment(dir, start, end) {
    override fun reversed(): Segment =
            LineSegment(dir, end, start)
}

class QuadraticCurveSegment(dir: Directive, start: Vector, end: Vector, val c0: Vector) :
        Segment(dir, start, end) {
    override fun reversed(): Segment =
            QuadraticCurveSegment(dir, end, start, c0)

    // TODO: Override area and boundingRect
}

class CubicCurveSegment(dir: Directive, start: Vector, end: Vector,
                        val c0: Vector, val c1: Vector) :
        Segment(dir, start, end) {
    override fun reversed(): Segment =
            CubicCurveSegment(dir, end, start, c1, c0)
    // TODO: Override area and boudingRect
}

class ArcSegment(dir: Directive, start: Vector, end: Vector,
                 val radius: Vector, val rotation: Double, val largeArc: Boolean, val sweep: Boolean) :
        Segment(dir, start, end) {
    override fun reversed(): Segment =
            ArcSegment(dir, end, start, radius, rotation, largeArc, !sweep)
    // TODO: Override area and boudingRect
}
