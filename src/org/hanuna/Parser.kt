package org.hanuna


class Parser {
    /*
    {}() = Unit
    {} = Function0<Unit>
    {}{} = Code
     */

    // () -> +
    fun Code.invoke() = Plus(this)
    fun Unit.invoke() = Plus(Plus(null))
    //fun Function0<Unit>.invoke()

    // ({}) -> -
    fun Code.invoke(p: () -> Unit) = Minus(this)
    fun Unit.invoke(p: () -> Unit) = Minus(Plus(null))
    fun Function0<Unit>.invoke(f: () -> Unit) = Minus(null)

    // ({}()) -> >
    fun Code.invoke(p: Unit) = ToRight(this)
    fun Unit.invoke(p: Unit) = ToRight(Plus(null))
    fun Function0<Unit>.invoke(p: Unit) = ToRight(null)

    // ({}()){} -> <
    fun Code.invoke(p: Unit, p1: () -> Unit) = ToLeft(this)
    fun Unit.invoke(p: Unit, p1: () -> Unit) = ToLeft(Plus(null))
    fun Function0<Unit>.invoke(p: Unit, p1: () -> Unit) = ToLeft(null)

    // ({}{}) -> . (print)
    fun Code.invoke(p: Code) = Print(this)
    fun Unit.invoke(p: Code) = Print(Plus(null))
    fun Function0<Unit>.invoke(p: Code) = Print(null)

    // ({}{}){} -> , (read)
    fun Code.invoke(p: Code, p1: () -> Unit) = Read(this)
    fun Unit.invoke(p: Code, p1: () -> Unit) = Read(Plus(null))
    fun Function0<Unit>.invoke(p: Code, p1: () -> Unit) = Read(null)

    // [{}....] -> [...]
    fun Code.get(c: Code) = While(c, this)
    fun Code.get(p: Unit) = While(Plus(null), this)
    fun Code.get(p: () -> Unit) = While(null, this)

    fun Unit.get(c: Code) = While(c, Plus(null))
    fun Unit.get(p: Unit) = While(Plus(null), Plus(null))
    fun Unit.get(p: () -> Unit) = While(null, Plus(null))

    fun Function0<Unit>.get(c: Code) = While(c, null)
    fun Function0<Unit>.get(p: Unit) = While(Plus(null), null)
    fun Function0<Unit>.get(p: () -> Unit) = While(null, null)

    abstract class Code(prev: Code?) : ProgramNode {
        val first: Code

        override var next: ProgramNode? = null
            private set
        ;{
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
