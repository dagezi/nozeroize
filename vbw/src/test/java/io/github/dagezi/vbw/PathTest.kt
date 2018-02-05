package io.github.dagezi.vbw

import org.junit.Assert.*
import org.junit.Test

class PathTest {
    val simpleSquarePath = Path().add(square(10.0, true))
    val pathWithWinding2 = Path()
            .add(square(10.0, true))
            .add(square(20.0, true))
    val pathWithWinding0 = Path()
            .add(square(10.0, true))
            .add(square(20.0, false))

    val delta = 1e-6

    private fun square(size: Double, isCw: Boolean) : SubPath {
        val p0 = Vector(-size, -size)
        val p1 = Vector(size, -size)
        val p2 = Vector(size, size)
        val p3 = Vector(-size, size)

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

    @Test
    fun area() {
        assertEquals(400.0, simpleSquarePath.area, delta)
        assertEquals(2000.0, pathWithWinding2.area, delta)
        assertEquals(-1200.0, pathWithWinding0.area, delta)
    }

    @Test
    fun equals() {
        assertEquals(Path(), Path())

        assertNotEquals(Path(), simpleSquarePath)
        assertNotEquals(pathWithWinding0, pathWithWinding2)

        val path = Path().add(square(10.0, true))
        assertEquals(simpleSquarePath, path)
    }
}