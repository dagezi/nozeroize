package io.github.dagezi.nonzeroize

abstract class Shape {
    abstract val boundingRect : Rect

    open val isEmpty: Boolean
        get() = boundingRect.isEmpty
    open val isVoid: Boolean
        get() = boundingRect.isVoid

    // Directed area: Positive if the shape is clockwise
    abstract val area: Double

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