package io.github.dector.pipes

import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration
import java.awt.Font

fun main() {
    launchApp()
}

fun launchApp() {
    val terminal = DefaultTerminalFactory()
        .createTerminal()
    Application(terminal).run()
}
