package io.github.dagezi.vbw

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals
import org.junit.Test


class RectTest {
    val rect0 = Rect(2.0, 2.0, -1.0, -2.0)
    val rect1 = Rect(0.0, 0.0, 3.0, 3.0)
    val rect2 = Rect(0.0, 0.0, 1.0, 1.0)
    val rect3 = Rect(50.0, 50.0, 100.0, 100.0)
    val rectEmpty = Rect(0.0, 0.0, 0.0, 0.0)

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
        assertEquals(Rect(-1.0, -2.0, 3.0, 3.0), u)
    }

    @Test
    fun unionOfVoid() {
        assertEquals(Rect.VOID, Rect.VOID.union(Rect.VOID))
        assertEquals(rect0, Rect.VOID.union(rect0))
        assertEquals(rect0, rect0.union(Rect.VOID))
        assertThat(rectEmpty.union(Rect.VOID), not(Rect.VOID))
    }

    @Test
    fun intersectedness() {
        assertThat(rect3.intersectedness(rect0), lessThan(0))
        assertThat(rect1.intersectedness(rect0), greaterThan(0))
        assertThat(rect0.intersectedness(rect1), greaterThan(0))
        assertThat(rect0.intersectedness(Rect.VOID), lessThan(0))
        assertThat(Rect.VOID.intersectedness(rect0), lessThan(0))
    }

    @Test
    fun containingness() {
        assertThat(rect0.containingness(rect1), lessThan(0))
        assertThat(rect1.containingness(rect2), equalTo(0))
        assertThat(rect0.containingness(rect2), greaterThan(0))
        assertThat(rect0.intersectedness(Rect.VOID), lessThan(0))
        assertThat(Rect.VOID.intersectedness(rect0), lessThan(0))
    }
}