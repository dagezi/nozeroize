package io.github.dagezi.vbw

enum class Directive {
    L, H, V, Q, T, C, S, A, Z
}

abstract class Segment() {
    abstract val dir: Directive
    abstract val start: Vector
    abstract val end: Vector

    // Directed area: Positive if the path from p, start, end and back to p
    // is clockwise.
    open fun area(p: Vector): Double =
            (start - p).cross(end - start) / 2.0

    open val boundingRect
        get() = Rect(start, end)

    abstract fun reversed() : Segment

    abstract fun toPathData() : String
}

data class LineSegment(
        override val dir: Directive,
        override val start: Vector,
        override val end: Vector)
    : Segment() {
    override fun reversed(): Segment =
            LineSegment(dir, end, start)

    override fun toPathData(): String = String.format("L%f,%f ", end.x, end.y)
}

data class QuadraticCurveSegment(
        override val dir: Directive,
        override val start: Vector,
        override val end: Vector,
        val c0: Vector)
    : Segment() {
    override fun reversed(): Segment =
            QuadraticCurveSegment(dir, end, start, c0)

    override fun toPathData(): String =
            String.format("Q%f,%f %f,%f ", c0.x, c0.y, end.x, end.y)

    // TODO: Override area and boundingRect
}

data class CubicCurveSegment(
        override val dir: Directive,
        override val start: Vector,
        override val end: Vector,
        val c0: Vector,
        val c1: Vector)
    : Segment() {
    override fun reversed(): Segment =
            CubicCurveSegment(dir, end, start, c1, c0)

    override fun toPathData(): String =
            String.format("C%f,%f %f,%f %f,%f", c0.x, c0.y, c1.x, c1.y, end.x, end.y)

    // TODO: Override area and boudingRect
}

fun Boolean.toInt() = if (this) 1 else 0

data class ArcSegment(
        override val dir: Directive,
        override val start: Vector,
        override val end: Vector,
        val radius: Vector,
        val rotation: Double,
        val largeArc: Boolean,
        val sweep: Boolean)
    : Segment() {
    override fun reversed(): Segment =
            ArcSegment(dir, end, start, radius, rotation, largeArc, !sweep)

    override fun toPathData(): String =
            String.format("A%f,%f %f,%d,%d %f,%f", radius.x, radius.y,
                    rotation, largeArc.toInt(), sweep.toInt(), end.x, end.y)

    // TODO: Override area and boudingRect
}
