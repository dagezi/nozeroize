package io.github.dagezi.vbw

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Assert.*
import org.junit.Test

class SpatialTreeTest {
    private val rectA = Rect(0.0, 0.0, 100.0, 100.0)
    private val rectB = Rect(10.0, 10.0, 60.0, 60.0) // inside A
    private val rectC = Rect(20.0, 20.0, 30.0, 30.0) // inside B
    private val rectD = Rect(40.0, 40.0, 50.0, 50.0) // inside B
    private val rectE = Rect(10.0, 70.0, 20.0, 80.0) // inside A

    @Test
    fun singleElementTree() {
        val tree = SpatialTree(rectA)

        assertThat(tree.size, `is`(1))
        assertThat(tree.isEmpty(), `is`(false))
        assertThat(tree.contains(rectA), `is`(true))
        assertThat(tree.contains(rectB), `is`(false))
        assertThat(tree.containsAll(setOf(rectA)), `is`(true))
        assertThat(tree.containsAll(setOf(rectA, rectC)), `is`(false))

        val acc: List<Shape> = tree.map {it}
        assertThat(acc, `is`(listOf(rectA as Shape)))

        assertThat(tree.nodeOf(rectA)!!.shape, `is`(rectA as Shape))
    }

    @Test
    fun treeAddedInverseOver() {
        val tree = SpatialTree(rectA)

        tree.add(rectC)
        tree.add(rectB)

        assertThat(tree, containsInAnyOrder(rectA as Shape, rectB, rectC))

        val nodeB = tree.nodeOf(rectB)
        assertThat(nodeB!!.shape, `is`(rectB as Shape))
        assertThat(nodeB.isParentOf(rectC), `is`(true))
    }

    @Test
    fun treeWithRandomOrder() {
        val tree = SpatialTree(rectA)

        tree.add(rectC).add(rectE).add(rectB).add(rectD)

        assertThat(tree.isParentOf(rectB), `is`(true))
        assertThat(tree.isParentOf(rectE), `is`(true))
        assertThat(tree.isParentOf(rectD), `is`(false))
        assertThat(tree.nodeOf(rectB)!!.isParentOf(rectD), `is`(true))
        assertThat(tree.nodeOf(rectB)!!.isParentOf(rectC), `is`(true))
    }
}