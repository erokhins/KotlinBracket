package org.hanuna


fun main(args: Array<String>) {
    val runner = ProgramRunVisitor(sequenceOf()) {
        print(it)
    }

    with(Parser()) {
        parse(

{}()()()()()()()()()()[{}({}())()()()()()()()({}())()()()()()()()()()()({}())()
()()({}())()({}()){}({}()){}({}()){}({}()){}({})]({}())()()({}{})({}())()({}{})
()()()()()()()({}{})({}{})()()()({}{})({}())()()({}{})({}()){}({}()){}()()()()
()()()()()()()()()()()({}{})({}())({}{})()()()({}{})({})({})({})({})({})({})
({}{})({})({})({})({})({})({})({})({})({}{})({}())()({}{})({}())({}{})

        ).accept(runner , Unit)
    }
}