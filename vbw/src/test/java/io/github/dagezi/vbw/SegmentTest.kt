package io.github.dagezi.vbw

import org.junit.Test

import org.junit.Assert.*

class LineSegmentTest {
    private val z = Vector(0.0, 0.0)
    private val v0 = Vector(1.0, 2.0)
    private val v1 = Vector( 3.0, 1.0)
    private val seg = LineSegment(Directive.L, v0, v1)

    private val delta = 1e-6

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

    @Test
    fun reversed() {
        val rSeg = seg.reversed()
        assertEquals(v0, rSeg.end)
        assertEquals(v1, rSeg.start)
        assertEquals(2.5, rSeg.area(z), delta)
    }

    @Test
    fun equals() {
        val seg1 = LineSegment(Directive.L, v0, v1)
        assertEquals(seg, seg1)

        val seg2 = LineSegment(Directive.L, v0, z)
        assertNotEquals(seg, seg2)
        assertNotEquals(seg1, seg2)
    }
}