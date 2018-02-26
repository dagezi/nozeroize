package io.github.dagezi.nonzeroize

class PathFormatter {
    fun format(subPath: SubPath): String {
        val b = StringBuilder()
        val start = subPath.startPoint
        b.append("M${start.x},${start.y} " )
        subPath.segments.joinTo(b, separator = "", transform = { it.toPathData() })
        return b.toString()
    }

    fun format(path: Path): String {
        val b = StringBuilder()
        path.subPaths.joinTo(b, separator = " ", transform = { format(it) })
        return b.toString()
    }
}
