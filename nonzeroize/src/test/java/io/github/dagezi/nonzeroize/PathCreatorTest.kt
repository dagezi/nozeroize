package io.github.dagezi.nonzeroize

import org.junit.Test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

class PathCreatorTest {
    val pathCreator = PathCreator()

    @Test
    fun linePath() {
        val path = pathCreator.parse("M0,0 10,10 v-10 Z")
        assertThat(path.boundingRect, equalTo(Rect(0.0, 0.0, 10.0, 10.0) ))

        val subPath = path.subPaths[0]
        assertThat(subPath.startPoint, equalTo(subPath.endPoint))
        assertThat(subPath.segments.size, equalTo(3))
        assertThat(subPath.segments[0], instanceOf(LineSegment::class.java))
    }

    @Test
    fun cubicCurvePath() {
        val path = pathCreator.parse("M100,200 C100,100 250,100 250,200 S400,300 400,200")
        // Current Implementation is too rough
        assertThat(path.boundingRect, equalTo(Rect(100.0, 200.0, 400.0, 200.0) ))

        val subPath = path.subPaths[0]
        assertThat(subPath.startPoint, equalTo(subPath.endPoint))
        assertThat(subPath.segments.size, equalTo(3))
        assertThat(subPath.segments[1], instanceOf(CubicCurveSegment::class.java))

        val seg1 = subPath.segments[1] as CubicCurveSegment
        assertThat(seg1.c0, equalTo(Vector(250.0, 300.0)))
    }

    @Test
    fun arcPath() {
        val path = pathCreator.parse("M300,200 h-150 a150,150 0 1,0 150,-150 z")
        // TODO: test it with correct boundRect

        val subPath = path.subPaths[0]
        assertThat(subPath.startPoint, equalTo(subPath.endPoint))
        assertThat(subPath.segments.size, equalTo(3))
        assertThat(subPath.segments[1], instanceOf(ArcSegment::class.java))

        val seg1 = subPath.segments[1] as ArcSegment
        assertThat(seg1.radius, equalTo(Vector(150.0, 150.0)))
        assertThat(seg1.largeArc, equalTo(true))
        assertThat(seg1.sweep, equalTo(false))
    }

    private fun boxCw(size: Int) =
            String.format("M%d,%d %d,%d %d,%d %d,%d z",
                    -size, -size, size, -size, size, size, -size, size)

    private fun boxCcw(size: Int) =
            String.format("M%d,%d %d,%d %d,%d %d,%d z",
                    -size, -size, -size, size, size, size, size, -size)

    @Test
    fun compoundPath() {
        val path = pathCreator.parse(boxCw(20) + boxCw(10))

        assertThat(path.subPaths.size, equalTo(2))
    }
}