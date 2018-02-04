package io.github.dagezi.vbw

import org.junit.Test

import org.junit.Assert.*

class LineSegmentTest {
    val z = Vector(0.0, 0.0)
    val v0 = Vector(1.0, 2.0)
    val v1 = Vector( 3.0, 1.0)
    val seg = LineSegment(Directive.L, v0, v1)

    val delta = 1e-6

    @Test
    fun area() {
        assertEquals(0.0, seg.area(v0), delta)
        assertEquals(0.0, seg.area(v0), delta)
        assertEquals(-2.5, seg.area(z), delta)
    }

    @Test
    fun getBoundingRect() {
        assertEquals(Rect(v0, v1), seg.boundingRect)
    }
}