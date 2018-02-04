package io.github.dagezi.vbw

import org.apache.batik.parser.PathHandler
import org.apache.batik.parser.PathParser

class PathCreator : PathHandler {
    private val pathParser = PathParser()
    private var path: Path? = null
    private var curSubPath: SubPath = SubPath()
    private val dummyLastPoint = Vector(0.0, 0.0)
    private var lastPoint: Vector = dummyLastPoint

    init {
        pathParser.pathHandler = this
    }

    fun parse(s : String) : Path {
        pathParser.parse(s)
        return path!!
    }

    private fun addSegment(segment: Segment) {
        curSubPath.add(segment)
        lastPoint = segment.end
    }

    private fun closeSubPath() {
        curSubPath.close()
        if (!curSubPath.isEmpty) {
            path?.add(curSubPath)
        }
        curSubPath = SubPath()
    }

    private fun toVector(x: Float, y: Float, relative: Boolean) : Vector =
            if (!relative)
                Vector(x.toDouble(), y.toDouble())
            else
                Vector(x.toDouble() + lastPoint.x, y.toDouble() + lastPoint.y)

    override fun startPath() {
        path = Path()
    }

    override fun endPath() {
        closeSubPath()
    }

    override fun closePath() {
        closeSubPath()
    }

    override fun movetoAbs(x: Float, y: Float) {
        moveto(x, y, false)
    }

    override fun movetoRel(x: Float, y: Float) {
        moveto(x, y, true)
    }

    private fun moveto(x: Float, y: Float, relative: Boolean) {
        closeSubPath()
        lastPoint = toVector(x, y, relative)
    }

    override fun linetoHorizontalAbs(x: Float) {
        addSegment(LineSegment(Directive.H, lastPoint, Vector(x.toDouble(), lastPoint.y)))
    }

    override fun linetoHorizontalRel(x: Float) {
        addSegment(LineSegment(Directive.H, lastPoint, Vector(lastPoint.x + x.toDouble(), lastPoint.y)))
    }

    override fun linetoVerticalAbs(y: Float) {
        addSegment(LineSegment(Directive.V, lastPoint, Vector(lastPoint.x, y.toDouble())))
    }

    override fun linetoVerticalRel(y: Float) {
        addSegment(LineSegment(Directive.V, lastPoint, Vector(lastPoint.x, lastPoint.y + y.toDouble())))
    }

    override fun linetoAbs(x: Float, y: Float) {
        addSegment(LineSegment(Directive.L, lastPoint, toVector(x, y, false)))
    }

    override fun linetoRel(x: Float, y: Float) {
        addSegment(LineSegment(Directive.L, lastPoint, toVector(x, y, true)))
    }

    override fun curvetoQuadraticAbs(cx0: Float, cy0: Float, tx: Float, ty: Float) {
        addSegment(QuadraticCurveSegment(
                Directive.Q, lastPoint,
                toVector(tx, ty, false), toVector(cx0, cy0, false)))
    }

    override fun curvetoQuadraticRel(cx0: Float, cy0: Float, tx: Float, ty: Float) {
        addSegment(QuadraticCurveSegment(
                Directive.Q, lastPoint,
                toVector(tx, ty, true), toVector(cx0, cy0, true)))
    }

    override fun curvetoQuadraticSmoothAbs(tx: Float, ty: Float) {
        curvetoQuadraticSmooth(tx, ty, false)
    }

    override fun curvetoQuadraticSmoothRel(tx: Float, ty: Float) {
        curvetoQuadraticSmooth(tx, ty, true)
    }

    private fun curvetoQuadraticSmooth(tx: Float, ty: Float, relative: Boolean) {
        val lastSeg: QuadraticCurveSegment? = curSubPath.segments.lastOrNull() as? QuadraticCurveSegment
        val cp : Vector = (lastSeg?.c0 ?: lastPoint).reflect(lastPoint)
        addSegment(
                QuadraticCurveSegment(Directive.T, lastPoint, toVector(tx, ty, relative), cp))
    }

    override fun curvetoCubicAbs(cx0: Float, cy0: Float, cx1: Float, cy1: Float, tx: Float, ty: Float) {
        addSegment(CubicCurveSegment(
                Directive.Q, lastPoint,
                toVector(tx, ty, false),
                toVector(cx0, cy0, false),
                toVector(cx1, cy1, false)))
    }

    override fun curvetoCubicRel(cx0: Float, cy0: Float, cx1: Float, cy1: Float, tx: Float, ty: Float) {
        addSegment(CubicCurveSegment(
                Directive.Q, lastPoint,
                toVector(tx, ty, true),
                toVector(cx0, cy0, true),
                toVector(cx1, cy1, true)))
    }

    override fun curvetoCubicSmoothAbs(cx1: Float, cy1: Float, tx: Float, ty: Float) {
        curvetoCubicSmooth(cx1, cy1, tx, ty, false)
    }

    override fun curvetoCubicSmoothRel(cx1: Float, cy1: Float, tx: Float, ty: Float) {
        curvetoCubicSmooth(cx1, cy1, tx, ty, true)
    }

    private fun curvetoCubicSmooth(cx1: Float, cy1: Float, tx: Float, ty: Float, relative: Boolean) {
        val lastSeg: CubicCurveSegment? = curSubPath.segments.lastOrNull() as? CubicCurveSegment
        val cp : Vector = (lastSeg?.c1 ?: lastPoint).reflect(lastPoint)
        addSegment(
                CubicCurveSegment(Directive.S, lastPoint, toVector(tx, ty, relative),
                        cp, toVector(cx1, cy1, relative)))
    }

    override fun arcAbs(rx: Float, ry: Float, rotation: Float, largeArc: Boolean, sweep: Boolean, tx: Float, ty: Float) {
        addSegment(
                ArcSegment(Directive.A,
                        lastPoint,
                        toVector(tx, ty, false),
                        toVector(rx, ry, false),
                        rotation.toDouble(), largeArc, sweep)
        )
    }

    override fun arcRel(rx: Float, ry: Float, rotation: Float, largeArc: Boolean, sweep: Boolean, tx: Float, ty: Float) {
        addSegment(
                ArcSegment(Directive.A,
                        lastPoint,
                        toVector(tx, ty, true),
                        toVector(rx, ry, false),
                        rotation.toDouble(), largeArc, sweep)
        )
    }
}