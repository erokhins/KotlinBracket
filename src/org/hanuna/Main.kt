package org.hanuna


fun main(args: Array<String>) {
    val runner = ProgramRunVisitor(streamOf()) {
        print(it)
    }

    with(Parser()) {
        parse(

                {}

        ).accept(runner, Unit)
    }
}