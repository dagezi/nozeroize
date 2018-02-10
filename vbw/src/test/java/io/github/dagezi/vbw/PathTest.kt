package io.github.dagezi.vbw

import org.junit.Assert.*
import org.junit.Test

class PathTest {
    private val simpleSquarePath = Path().add(createSquareSubPath(10.0, true))
    private val pathWithWinding2 = Path()
            .add(createSquareSubPath(10.0, true))
            .add(createSquareSubPath(20.0, true))
    private val pathWithWinding0 = Path()
            .add(createSquareSubPath(10.0, true))
            .add(createSquareSubPath(20.0, false))

    private val delta = 1e-6

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

        val path = Path().add(createSquareSubPath(10.0, true))
        assertEquals(simpleSquarePath, path)
    }
}