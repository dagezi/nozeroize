package io.github.dagezi.vbw

import org.junit.Assert.*
import org.junit.Test

class VectorTest {
    val z = Vector(0.0, 0.0)
    val a = Vector(1.0, 2.0)
    val b = Vector(2.0, 3.0)
    val delta = 1e-6

    @Test
    fun add() {
        assertEquals(z, z + z)
        assertEquals(a, z + a)
        assertEquals(a, a + z)
        assertEquals(Vector(3.0, 5.0), a + b)
    }

    @Test
    fun unaryMinus() {
        assertEquals(0.0, (-z).x, delta)
        assertEquals(0.0, (-z).y, delta)
        assertEquals(z, a + -a)
    }

    @Test
    fun minus() {
        assertEquals(z, z - z)
        assertEquals(z, a - a)
        assertEquals(a, a - z)
        assertEquals(-a, z -  a)
        assertEquals(Vector(1.0, 1.0), b -  a)
    }

    @Test
    fun dot() {
        assertEquals(0.0, z.dot(z), delta)
        assertEquals(0.0, a.dot(z), delta)
        assertEquals(0.0, z.dot(a), delta)
        assertEquals(5.0, a.dot(a), delta)
        assertEquals(8.0, a.dot(b), delta)
        assertEquals(8.0, b.dot(a), delta)
    }

    @Test
    fun reflect() {
        assertEquals(z, z.reflect(z))
        assertEquals(-a, a.reflect(z))
        assertEquals(a * 2.0, z.reflect(a))
    }
}