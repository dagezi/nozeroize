package io.github.dagezi.vbw

class PathDirectionAdjuster(val path: Path) {
    val tree: SpatialTree = SpatialTree(path)
    init {
        path.subPaths.forEach {tree.add(it)}
    }

    fun isFollowingWinding(): Boolean =
            tree.getChildren().all {isFollowingWinding(it, it.shape.area)}

    private fun isFollowingWinding(subTree: SpatialTree, expectedAreaSign: Double): Boolean =
            subTree.shape.area * expectedAreaSign >= 0 &&
                    subTree.getChildren().all {isFollowingWinding(it, -expectedAreaSign)}

    fun adjust() : Path {
        return if (isFollowingWinding()) {
            path
        } else {
            val newPath = Path()
            tree.getChildren().forEach {
                adjustSubTree(newPath, it, it.shape.area)
            }
            newPath
        }
    }

    private fun adjustSubTree(newPath: Path, subTree: SpatialTree, expectedAreaSign: Double) {
        val subPath: SubPath = subTree.shape as SubPath
        newPath.add(
                if (subPath.area * expectedAreaSign < 0)
                    subPath.reversed()
                else
                    subPath)
        subTree.getChildren().forEach {
            adjustSubTree(newPath, it, -expectedAreaSign)
        }
    }

    private fun dump(builder: StringBuilder, subTree: SpatialTree, level: Int): StringBuilder =
        with(builder) {
            append(" ".repeat(level * 2))
            append("${subTree.shape.area}: ${subTree.shape.boundingRect}")
            append('\n')
            subTree.getChildren().forEach {dump(this, it, level + 1)}
            this
        }

    fun dump(builder: StringBuilder): StringBuilder =
            dump(builder, tree, 0)
}

