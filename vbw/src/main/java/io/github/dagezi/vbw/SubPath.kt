package io.github.dagezi.vbw

class SubPath: Shape() {
    val segments : MutableList<Segment> = mutableListOf()

    override val boundingRect: Rect
        get() = segments.fold(Rect.VOID, { acc, seg -> acc.union(seg.boundingRect)})

    val startPoint: Vector
        get() = segments.first().start

    val endPoint: Vector
        get() = segments.last().end

    // Make sure this is closed
    fun close() {
        if (!segments.isEmpty()) {
            if (startPoint != endPoint) {
                addSegment(LineSegment(Directive.Z, endPoint, startPoint))
            }
        }
    }

    fun addSegment(segment: Segment) {
        segments.add(segment)
    }
}