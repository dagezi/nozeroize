package io.github.dagezi.vbw

class SpatialTree(val shape: Shape) : Collection<Shape> {
    override val size: Int
        get() = 1 + children.sumBy {it.size}

    override fun contains(element: Shape): Boolean =
         (element == shape) || children.any {it.contains(element)}

    override fun containsAll(elements: Collection<Shape>): Boolean =
            elements.all {contains(it)}

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<Shape> = ShapeIterator()

    fun nodeIterator(): Iterator<SpatialTree> = NodeIterator()

    private val children: MutableList<SpatialTree> = mutableListOf()

    fun getChildren() : List<SpatialTree> = children

    fun add(newNode: SpatialTree): SpatialTree {
        // TODO: check the shape is inside my shape

        val parent = children.find {it.shape.contains(newNode.shape)}
        if (parent != null) {
            parent.add(newNode)
        } else {
            val grandChildren = children.filter { newNode.shape.contains(it.shape)}
            children.removeAll(grandChildren)
            grandChildren.forEach {newNode.add(it)}
            children.add(newNode)
            // TODO: warn if newNode conflicts with other siblings
        }
        return this
    }

    fun add(shape: Shape): SpatialTree =
        add(SpatialTree(shape))

    fun nodeOf(target: Shape): SpatialTree? {
        NodeIterator().forEach { if (it.shape == target) return it }
        return null
    }

    fun isParentOf(target: Shape): Boolean = children.any {it.shape == target}

    fun isAncestorOf(target: Shape):Boolean =
            shape == target ||
                    (children.any {it.shape.intersects(target) && it.isAncestorOf(target)})

    inner class NodeIterator : Iterator<SpatialTree> {
        private val stack: MutableList<SpatialTree> = mutableListOf(this@SpatialTree)

        override fun hasNext(): Boolean = !stack.isEmpty()

        override fun next(): SpatialTree {
            val node = stack.last()
            stack.removeAt(stack.size - 1)
            stack.addAll(node.children)
            return node
        }
    }

    inner class ShapeIterator : Iterator<Shape> {
        private val nodeIterator = NodeIterator()

        override fun hasNext(): Boolean = nodeIterator.hasNext()

        override fun next(): Shape = nodeIterator.next().shape
    }
}