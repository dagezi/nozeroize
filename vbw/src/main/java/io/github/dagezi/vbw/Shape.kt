package io.github.dagezi.vbw

abstract class Shape {
    abstract val isEmpty: Boolean
    abstract val boundingRect : Rect

    open fun intersects(other: Shape): Boolean =
            intersectedness(other) >= 0

    /**
     * @return Positive if intersected, 0 if touched, negative otherwise
     *
     * Note this implementation is very naive.
     */
    open fun intersectedness(other: Shape): Int =
            boundingRect.intersectedness(other.boundingRect)

    open fun contains(other: Shape): Boolean =
            containingness(other) >= 0

    /**
     * @return Positive if this contains other perfectly, 0 if with border, negative otherwise
     *
     * Note this implementation is very naive.
     */
    open fun containingness(other: Shape) : Int =
            boundingRect.containingness(other.boundingRect)

}