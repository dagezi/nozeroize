package io.github.dagezi.nonzeroize

class SubPath: Shape() {
    val segments : MutableList<Segment> = mutableListOf()

    override val boundingRect: Rect
        get() = segments.fold(Rect.VOID, { acc, seg -> acc.union(seg.boundingRect)})

    override val area: Double
        get() = if (segments.isEmpty()) {
            0.0
        } else {
            val p = startPoint
            segments.sumByDouble { it.area(p) }
        }

    val startPoint: Vector
        get() = segments.first().start

    val endPoint: Vector
        get() = segments.last().end

    // Make sure this is closed
    fun close() : SubPath {
        if (!segments.isEmpty()) {
            if (startPoint != endPoint) {
                add(LineSegment(endPoint, startPoint, Directive.Z))
            }
        }
        return this
    }

    fun add(segment: Segment) : SubPath {
        segments.add(segment)
        return this
    }

    fun reversed() : SubPath {
        val newSubPath = SubPath()
        segments.forEach({newSubPath.add(it.reversed())})
        newSubPath.segments.reverse()
        return newSubPath
    }

    override fun equals(other: Any?): Boolean =
            other is SubPath && segments.size == other.segments.size &&
                    segments.zip(other.segments).all {it.first == it.second}

    // Don't rely on hashCode!
}