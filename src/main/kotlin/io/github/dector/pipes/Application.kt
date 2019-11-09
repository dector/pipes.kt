package io.github.dector.pipes

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.TextColor.ANSI
import com.googlecode.lanterna.terminal.Terminal
import io.github.dector.pipes.GrowingDirection.Down
import io.github.dector.pipes.GrowingDirection.Left
import io.github.dector.pipes.GrowingDirection.Right
import io.github.dector.pipes.GrowingDirection.Up
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
        /*drawFrame(0, 0, height - 1, width - 1)*/
        drawPipes()
    }

/*
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
*/

    private fun drawPipes() {
        val concurrentPipes = 1

        repeat(concurrentPipes) {
            launchPipe()
        }
    }

    private fun launchPipe(doAfter: () -> Unit) {
        val color = listOf(ANSI.BLUE, ANSI.RED, ANSI.YELLOW, ANSI.MAGENTA, ANSI.GREEN, ANSI.MAGENTA)
            .random()

        scope.launch {
            val x = Random.nextInt(1 until (width - 1))

            drawPipe(x, color)
            doAfter()
        }
    }

    private fun launchPipe() {
        launchPipe(doAfter = { launchPipe() })
    }

    private suspend fun drawPipe(startX: Int, color: TextColor) {
        val xRange = 0 until width
        val yRange = 0 until height

        val startY = height - 1

        terminal.setForegroundColor(color)

        val growingDirections = arrayOf(Up, Up)
        var x = startX
        var y = startY
        var canBuildMore = true
        while (canBuildMore) {
            val char = pipeSegmentChar(growingDirections)

            terminal.setCursorPosition(x, y)
            terminal.putCharacter(char)
            terminal.flush()

            growingDirections.shiftLeft { nextDirection(growingDirections.last()) }
            when (growingDirections.first()) {
                Left -> x--
                Right -> x++
                Up -> y--
                Down -> y++
            }

            canBuildMore = (x in xRange) && (y in yRange)

            delay(20)
        }
    }
}

private enum class GrowingDirection {
    Left, Right, Up, Down
}

private fun nextDirection(currentDirection: GrowingDirection): GrowingDirection {
    val makeTurn = Random.nextDouble() < 0.1
    if (!makeTurn) return currentDirection

    val turnLeft = Random.nextDouble() < 0.5
    return if (turnLeft) {
        when (currentDirection) {
            Left -> Down
            Right -> Up
            Up -> Left
            Down -> Right
        }
    } else {
        when (currentDirection) {
            Left -> Up
            Right -> Down
            Up -> Right
            Down -> Left
        }
    }
}

private fun pipeSegmentChar(directions: Array<GrowingDirection>): Char {
    val a = directions.first()
    val b = directions.last()

    return when (a) {
        Up -> when (b) {
            Up -> '║'
            Down -> '║'
            Left ->'╗'
            Right -> '╔'
        }
        Down -> when (b) {
            Up -> '║'
            Down -> '║'
            Left -> '╝'
            Right -> '╚'
        }
        Left -> when (b) {
            Up -> '╚'
            Down -> '╔'
            Left -> '═'
            Right -> '═'
        }
        Right -> when (b) {
            Up -> '╝'
            Down -> '╗'
            Left -> '═'
            Right -> '═'
        }
    }
}

private fun <T> Array<T>.shiftLeft(newValue: () -> T) {
    for (i in 1..lastIndex) {
        this[i - 1] = this[i]
    }
    this[lastIndex] = newValue()
}
