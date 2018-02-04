package io.github.dagezi.vbw

class Path : Shape() {
    val subPaths : MutableList<SubPath> = mutableListOf()

    override val boundingRect: Rect
        get() = subPaths.fold(Rect.VOID, {acc, subPath -> acc.union(subPath.boundingRect)})
}

