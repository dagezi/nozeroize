package io.github.dagezi.vbw

class PathFormatter {
    fun format(subPath: SubPath): String {
        var b = StringBuilder()
        subPath.segments.joinTo(b, separator = "", transform = { it.toPathData() })
        return b.toString()
    }

    fun format(path: Path): String {
        var b = StringBuilder()
        path.subPaths.joinTo(b, separator = " ", transform = { format(it) })
        return b.toString()
    }
}
