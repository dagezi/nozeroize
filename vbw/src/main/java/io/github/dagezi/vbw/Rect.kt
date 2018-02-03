package io.github.dagezi.vbw

import java.lang.Math.signum
import kotlin.math.max
import kotlin.math.min


private fun range_intersectedness(a0: Double, a1: Double, b0: Double, b1:Double) : Int =
        signum(
                if (a0 < b0) a1 - b0 else b1 - a0
        ).toInt()

private fun range_containingness(a0: Double, a1: Double, b0: Double, b1:Double) : Int =
        if (a0 <= b0 && b1 <= a1)
            signum((b0 - a0) * (a1 - b1)).toInt()
        else
            -1

class Rect(left: Double, top: Double, right: Double, bottom: Double) : Shape() {
    val left: Double = min(left, right)
    val right: Double = max(left, right)
    val top: Double = min(top, bottom)
    val bottom: Double = max(top, bottom)

    constructor(v0: Vector, v1: Vector) : this(v0.x, v0.y, v1.x, v1.y)

    override val isEmpty: Boolean
        get() = left == right && top == bottom

    override val boundingRect: Rect
        get() = this

    fun union(other: Rect): Rect =
            Rect(min(left, other.left), min(top, other.top),
                    max(right, other.right), max(bottom, other.bottom))

    override fun intersectedness(other: Shape): Int =
            if (other is Rect)
                min(range_intersectedness(left, right, other.left, other.right),
                range_intersectedness(top, bottom, other.top, other.bottom))
            else
                other.intersectedness(this)

    override fun containingness(other: Shape): Int =
        if (other is Rect)
            min(range_containingness(left, right, other.left, other.right),
                    range_containingness(top, bottom, other.top, other.bottom))
        else
            other.containingness(this)
}