package io.github.dector.pipes

import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal

fun main() {
    launchApp()
}

fun launchApp() {
    val terminal = DefaultTerminalFactory().createTerminal()

    println("Terminal size: ${terminal.terminalSize}")
    terminal.println("Hello")

}

fun Terminal.println(str: String) {
    for (c in str) {
        putCharacter(c)
    }
    flush()
}
