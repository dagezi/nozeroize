package io.github.dagezi.vbw

fun createSquareSubPath(size: Double, isCw: Boolean, center: Vector = Vector(0.0, 0.0)) : SubPath {
    val p0 = Vector(-size, -size) + center
    val p1 = Vector(size, -size) + center
    val p2 = Vector(size, size) + center
    val p3 = Vector(-size, size) + center

    return if (isCw) {
        SubPath()
                .add(LineSegment(p0, p1))
                .add(LineSegment(p1, p2))
                .add(LineSegment(p2, p3))
                .close()
    } else {
        SubPath()
                .add(LineSegment(p0, p3))
                .add(LineSegment(p3, p2))
                .add(LineSegment(p2, p1))
                .close()
    }
}