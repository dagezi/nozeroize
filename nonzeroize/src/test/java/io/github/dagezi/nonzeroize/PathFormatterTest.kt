package io.github.dagezi.nonzeroize

import org.junit.Assert.*
import org.junit.Test

class PathFormatterTest {
    val formatter = PathFormatter()
    val creater = PathCreator()

    val v0 = Vector(0.0, 0.0)
    val v1 = Vector(10.0, 20.0)
    val v2 = Vector(-20.0, 10.0)

    @Test
    fun emptyPath() {
        var path = Path()
        assertEquals(path, creater.parse(formatter.format(path)))
    }

    @Test
    fun simplePath() {
        var path = Path()
                .add(SubPath()
                        .add(LineSegment(v0, v1))
                        .close())
        assertEquals(path, creater.parse(formatter.format(path)))
    }

    @Test
    fun arcPath() {
        var path = Path().add(
                SubPath()
                        .add(ArcSegment(v0, v1, Vector(10.0, 20.0), 120.0, true, true))
                        .close())
        assertEquals(path, creater.parse(formatter.format(path)))
    }

    @Test
    fun manySegments() {
        var path = Path().add(
                SubPath()
                        .add(ArcSegment(v0, v1, Vector(10.0, 20.0), 120.0, true, true))
                        .add(LineSegment(v1, v2))
                        .close())
        assertEquals(path, creater.parse(formatter.format(path)))
    }

    @Test
    fun manySubPaths() {
        var path = Path()
                .add(SubPath()
                        .add(ArcSegment(v0, v1, Vector(100.0, 200.0), 120.0, true, true))
                        .close())
                .add(SubPath()
                        .add(LineSegment(v2, v1))
                        .close())
        assertEquals(path, creater.parse(formatter.format(path)))
    }
}