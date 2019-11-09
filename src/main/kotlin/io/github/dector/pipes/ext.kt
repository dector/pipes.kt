package io.github.dector.pipes

import com.googlecode.lanterna.terminal.Terminal

fun Terminal.println(str: String) {
    for (c in str) {
        putCharacter(c)
    }
    flush()
}
