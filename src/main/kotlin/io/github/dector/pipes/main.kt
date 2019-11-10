package io.github.dector.pipes

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal

fun main() {
    launchApp()
}

fun launchApp() {
    val terminal = DefaultTerminalFactory()
        .createTerminal()
        .let(::wrap)
    Application(terminal).run()
}

private fun wrap(term: Terminal) =
    object : Term {

        override val width = term.terminalSize.columns
        override val height = term.terminalSize.rows

        override fun hideCursor() {
            term.setCursorVisible(false)
        }

        override fun setTextColor(color: Color) {
            term.setForegroundColor(color.asLanternaColor())
        }

        override fun printAt(x: Int, y: Int, c: Char) {
            term.setCursorPosition(x, y)
            term.putCharacter(c)
        }

        override fun flush() {
            term.flush()
        }
    }

fun Color.asLanternaColor() = when (this) {
    Color.Blue -> TextColor.ANSI.BLUE
    Color.Red -> TextColor.ANSI.RED
    Color.Yellow -> TextColor.ANSI.YELLOW
    Color.Magenta -> TextColor.ANSI.MAGENTA
    Color.Green -> TextColor.ANSI.GREEN
}
