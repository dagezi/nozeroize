package io.github.dagezi.vbw

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SubPathTest {
    val emptySubPath = SubPath()
    val subPath0 = SubPath()
    val vz = Vector(0.0, 0.0)
    val v1 = Vector(1.0, 2.0)
    val v2 = Vector(-2.0, 1.0)

    @Before
    fun setup() {
        subPath0.addSegment(LineSegment(Directive.L, vz, v1))
        subPath0.addSegment(LineSegment(Directive.L, v1, v2))
    }

    @Test
    fun boundingRect() {
        assertEquals(Rect.VOID, emptySubPath.boundingRect)
        assertEquals(Rect(v2.x, vz.y, v1.x, v1.y), subPath0.boundingRect)
    }

    @Test
    fun close() {
        assertEquals(v2, subPath0.endPoint)
        subPath0.close()
        assertEquals(vz, subPath0.endPoint)
    }
}