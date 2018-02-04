package io.github.dagezi.vbw

class Path : Shape() {
    val subPaths : MutableList<SubPath> = mutableListOf()

    override val boundingRect: Rect
        get() = subPaths.fold(Rect.VOID, {acc, subPath -> acc.union(subPath.boundingRect)})

    override val area: Double
        get() = subPaths.sumByDouble { it.area }

    fun addSubPath(subPath: SubPath) {
        subPaths.add(subPath)
    }
}

