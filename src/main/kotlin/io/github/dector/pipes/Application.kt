package io.github.dector.pipes

import com.googlecode.lanterna.terminal.Terminal

class Application(private val terminal: Terminal) {

    init {
        terminal.setCursorVisible(false)
    }

    fun run() {
        drawFrame(0, 0, terminal.terminalSize.rows - 1, terminal.terminalSize.columns - 1)
        drawPipes()
    }

    private fun drawFrame(firstRow: Int, firstColumn: Int, lastRow: Int, lastColumn: Int) {
        for (row in firstRow..lastRow) {
            for (column in firstColumn..lastColumn) {

                val char = when {
                    row == firstRow && column == firstColumn -> '╔'
                    row == firstRow && column == lastColumn -> '╗'
                    row == lastRow && column == firstColumn -> '╚'
                    row == lastRow && column == lastColumn -> '╝'
                    row == firstRow || row == lastRow -> '═'
                    column == firstColumn || column == lastColumn -> '║'
                    else -> null
                }
                if (char != null) {
                    terminal.setCursorPosition(column, row)
                    terminal.putCharacter(char)
                }
            }
        }
    }

    private fun drawPipes() {

    }
}
