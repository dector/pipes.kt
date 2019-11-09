package io.github.dector.pipes

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.TextColor.ANSI
import com.googlecode.lanterna.terminal.Terminal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class Application(private val terminal: Terminal) {

    private val width = terminal.terminalSize.columns
    private val height = terminal.terminalSize.rows

    private val scope = CoroutineScope(SupervisorJob())

    init {
        terminal.setCursorVisible(false)
    }

    fun run() {
        drawFrame(0, 0, height - 1, width - 1)
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

        terminal.flush()
    }

    private fun drawPipes() {
        scope.launch {
            while(true) {
                val x = Random.nextInt(1 until (width-1))
                val color = listOf(ANSI.BLUE, ANSI.RED, ANSI.YELLOW, ANSI.MAGENTA, ANSI.GREEN, ANSI.MAGENTA)
                    .random()

                drawPipe(x, color)
            }
        }

    }

    private suspend fun drawPipe(startX: Int, color: TextColor) {
        val xRange = 1 until (width - 1)
        val yRange = 1 until (height - 1)

        val startY = (height - 1) - 1

        terminal.setForegroundColor(color)

        var x = startX
        var y = startY
        var canBuildMore = true
        while (canBuildMore) {
            terminal.setCursorPosition(startX, y)
            terminal.putCharacter('█')
            terminal.flush()

            y--
            canBuildMore = (x in xRange) && (y in yRange)

            delay(20)
        }
    }
}
