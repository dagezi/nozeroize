package io.github.dagezi.nonzeroize

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.lessThan
import org.junit.Assert.*
import org.junit.Test

class PathDirectionAdjusterTest {
    @Test
    fun empty() {
        val path = Path()
        val adjuster = PathDirectionAdjuster(path)

        assertThat(adjuster.isFollowingWinding(), `is`(true))
        assertThat(adjuster.adjust(), `is`(path))
    }

    @Test
    fun nested() {
        val path = Path()
                .add(createSquareSubPath(100.0, true, Vector(50.0, 50.0)))
                .add(createSquareSubPath(10.0, true, Vector(30.0, 30.0)))
                .add(createSquareSubPath(20.0, true, Vector(30.0, 30.0)))
                .add(createSquareSubPath(10.0, true, Vector(70.0, 30.0)))

        val adjuster = PathDirectionAdjuster(path)

        assertThat(adjuster.isFollowingWinding(), `is`(false))
        val adjusted = adjuster.adjust()

        assertThat(adjusted.size, `is`(path.size))
        assertThat(adjusted.boundingRect, `is`(path.boundingRect))

        val adjuster1 = PathDirectionAdjuster(adjusted)
        assertThat(adjuster1.isFollowingWinding(), `is`(true))

        adjuster1.tree.nodeIterator().forEach {
            if (it.shape is SubPath) {
                val shape = it.shape
                it.getChildren().forEach {
                    assertThat(shape.area * it.shape.area, lessThan(0.0))
                }
            }
        }
    }
}