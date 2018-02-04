package io.github.dagezi.vbw

class Path : Shape() {
    val subPaths : MutableList<SubPath> = mutableListOf()

    override val boundingRect: Rect
        get() = subPaths.fold(Rect.VOID, {acc, subPath -> acc.union(subPath.boundingRect)})

    override val area: Double
        get() = subPaths.sumByDouble { it.area }

    fun add(subPath: SubPath) : Path {
        subPaths.add(subPath)
        return this
    }

    override fun equals(other: Any?): Boolean =
            other is Path && subPaths.size == other.subPaths.size &&
                    subPaths.zip(other.subPaths).all {it.first == it.second}
}

