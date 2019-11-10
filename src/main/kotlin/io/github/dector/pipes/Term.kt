package io.github.dector.pipes

interface Term {

    val width: Int
    val height: Int

    fun hideCursor()

    fun setTextColor(color: Color)
    fun printAt(x: Int, y: Int, c: Char)

    fun flush()
}

enum class Color {
    Blue,
    Red,
    Yellow,
    Magenta,
    Green
}
