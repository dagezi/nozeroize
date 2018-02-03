package io.github.dagezi.vbw

data class Vector(val x: Double, val y: Double) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)
    operator fun times(a: Double) = Vector(x * a, y * a)
    operator fun unaryMinus() = times(-1.0)
    operator fun minus(other: Vector) = Vector(x - other.x, y - other.y)

    fun dot(other: Vector) = x * other.x + y * other.y
    fun norm() = dot(this)
    fun cross(other: Vector) = x * other.y - y * other.x
    fun reflect(pivot: Vector) = pivot + pivot - this
}