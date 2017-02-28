package org.hanuna


class Parser {
    /*
    {}() = Unit
    {} = Function0<Unit>
    {}{} = Code
     */

    // () -> +
    operator fun Code.invoke() = Plus(this)
    operator fun Unit.invoke() = Plus(Plus(null))
    //fun Function0<Unit>.invoke()

    // ({}) -> -
    operator fun Code.invoke(p: () -> Unit) = Minus(this)
    operator fun Unit.invoke(p: () -> Unit) = Minus(Plus(null))
    operator fun Function0<Unit>.invoke(f: () -> Unit) = Minus(null)

    // ({}()) -> >
    operator fun Code.invoke(p: Unit) = ToRight(this)
    operator fun Unit.invoke(p: Unit) = ToRight(Plus(null))
    operator fun Function0<Unit>.invoke(p: Unit) = ToRight(null)

    // ({}()){} -> <
    operator fun Code.invoke(p: Unit, p1: () -> Unit) = ToLeft(this)
    operator fun Unit.invoke(p: Unit, p1: () -> Unit) = ToLeft(Plus(null))
    operator fun Function0<Unit>.invoke(p: Unit, p1: () -> Unit) = ToLeft(null)

    // ({}{}) -> . (print)
    operator fun Code.invoke(p: Code) = Print(this)
    operator fun Unit.invoke(p: Code) = Print(Plus(null))
    operator fun Function0<Unit>.invoke(p: Code) = Print(null)

    // ({}{}){} -> , (read)
    operator fun Code.invoke(p: Code, p1: () -> Unit) = Read(this)
    operator fun Unit.invoke(p: Code, p1: () -> Unit) = Read(Plus(null))
    operator fun Function0<Unit>.invoke(p: Code, p1: () -> Unit) = Read(null)

    // [{}....] -> [...]
    operator fun Code.get(c: Code) = While(c, this)
    operator fun Code.get(p: Unit) = While(Plus(null), this)
    operator fun Code.get(p: () -> Unit) = While(null, this)

    operator fun Unit.get(c: Code) = While(c, Plus(null))
    operator fun Unit.get(p: Unit) = While(Plus(null), Plus(null))
    operator fun Unit.get(p: () -> Unit) = While(null, Plus(null))

    operator fun Function0<Unit>.get(c: Code) = While(c, null)
    operator fun Function0<Unit>.get(p: Unit) = While(Plus(null), null)
    operator fun Function0<Unit>.get(p: () -> Unit) = While(null, null)

    abstract class Code(prev: Code?) : ProgramNode {
        val first: Code

        override var next: ProgramNode? = null
            set
        init {
            if (prev == null) {
                first = this
            } else {
                prev.next = this
                first = prev.getF()
            }
        }

        private fun getF(): Code = first
     }

    class While(lastContentNode: Code?, prev: Code?): Code(prev), ProgramWhile {
        override val content: ProgramNode = if (lastContentNode == null) EmptyNode else lastContentNode.first
    }
    class Plus(prev: Code?) : Code(prev), PlusNode
    class Minus(prev: Code?) : Code(prev), MinusNode
    class ToLeft(prev: Code?) : Code(prev), ToLeftNode
    class ToRight(prev: Code?) : Code(prev), ToRightNode
    class Print(prev: Code?) : Code(prev), PrintNode
    class Read(prev: Code?) : Code(prev), ReadNode

    fun parse(f: Unit): ProgramNode = Plus(null)
    fun parse(f: () -> Unit): ProgramNode = EmptyNode
    fun parse(f: Code): ProgramNode = f.first
}
