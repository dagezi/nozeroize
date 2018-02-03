package io.github.dagezi.vbw

import org.hamcrest.Matchers.*
import org.junit.Test

import org.junit.Assert.*

import org.hamcrest.MatcherAssert.assertThat


class RectTest {
    val rect0 = Rect(2.0, 2.0, -1.0, -2.0)
    val rect1 = Rect(0.0, 0.0, 3.0, 3.0)
    val rect2 = Rect(0.0, 0.0, 1.0, 1.0)
    val rect3 = Rect(50.0, 50.0, 100.0, 100.0)

    val delta = 1e-6

    @Test
    fun constructors() {
        assertEquals(-1.0, rect0.left, delta)
        assertEquals(-2.0, rect0.top, delta)
        assertEquals(2.0, rect0.right, delta)
        assertEquals(2.0, rect0.bottom, delta)
    }

    @Test
    fun union() {
        val u = rect0.union(rect1)
        assertEquals(-1.0, u.left, delta)
        assertEquals(-2.0, u.top, delta)
        assertEquals(3.0, u.right, delta)
        assertEquals(3.0, u.bottom, delta)
    }

    @Test
    fun intersectedness() {
        assertThat(rect3.intersectedness(rect0), lessThan(0))
        assertThat(rect1.intersectedness(rect0), greaterThan(0))
        assertThat(rect0.intersectedness(rect1), greaterThan(0))
    }

    @Test
    fun containingness() {
        assertThat(rect0.containingness(rect1), lessThan(0))
        assertThat(rect1.containingness(rect2), equalTo(0))
        assertThat(rect0.containingness(rect2), greaterThan(0))
    }
}