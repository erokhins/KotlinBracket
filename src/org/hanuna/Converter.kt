package org.hanuna.converter

import org.hanuna.Parser
import org.hanuna.BrainFuckVisitor

fun main(args: Array<String>) {

    var result = ""
    with(Parser()){
        result = parse(

                {}()()()()()

        ).accept(BrainFuckVisitor, Unit)
    }
    println("Brainfuck: $result")

    val brainfuck = """
 ++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++
 .>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.
 ------.--------.>+.>.
    """
    println("Bracket: ")
    println(brainfuck.asBracket())

}


fun String.asBracket(): String = StringBuilder {
    append("{}")
    for (cr in this@asBracket) {
        val to = when (cr) {
            '+' -> "()"
            '-' -> "({})"
            '>' -> "({}())"
            '<' -> "({}()){}"
            '.' -> "({}{})"
            ',' -> "({}{}){}"
            '[' -> "[{}"
            ']' -> "]"
            else -> cr.toString()
        }
        append(to)
    }
}.toString()
