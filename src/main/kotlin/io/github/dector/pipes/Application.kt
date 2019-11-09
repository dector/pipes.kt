package io.github.dector.pipes

import com.googlecode.lanterna.terminal.Terminal

class Application(private val terminal : Terminal) {

    init {
        terminal.setCursorVisible(false)
    }

    fun run() {
        drawFrame()
    }

    private fun drawFrame() {
        val size = terminal.terminalSize

        val lastRow = size.rows - 1
        val lastColumn = size.columns - 1

        for (row in 0..lastRow) {
            for (column in 0..lastColumn) {

                val char = when {
                    row == 0 && column == 0 -> '╔'
                    row == 0 && column == lastColumn -> '╗'
                    row == lastRow && column == 0 -> '╚'
                    row == lastRow && column == lastColumn -> '╝'
                    row == 0 || row == lastRow -> '═'
                    column == 0 || column == lastColumn -> '║'
                    else -> null
                }
                if (char != null) {
                    terminal.setCursorPosition(column, row)
                    terminal.putCharacter(char)
                }
            }
        }
    }
}
