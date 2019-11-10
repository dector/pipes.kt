package io.github.dector.pipes

import com.googlecode.lanterna.terminal.DefaultTerminalFactory

fun main() {
    launchApp()
}

fun launchApp() {
    val terminal = DefaultTerminalFactory()
        .createTerminal()
        .let(::wrap)
    Application(terminal).run()
}
