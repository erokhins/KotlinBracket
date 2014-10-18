package org.hanuna


trait ProgramVisitor<D, R> {
    fun visitWhile(programWhile: ProgramWhile, data: D): R
    fun visitPlusNode(node: PlusNode, data: D): R
    fun visitMinusNode(node: MinusNode, data: D): R
    fun visitToLeftNode(node: ToLeftNode, data: D): R
    fun visitToRightNode(node: ToRightNode, data: D): R
    fun visitWriteNode(node: WriteNode, data: D): R
    fun visitReadNode(node: ReadNode, data: D): R
}

trait ProgramNode {
    val next: ProgramNode?

    fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R
}

trait ProgramWhile : ProgramNode {
    val content: ProgramNode
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitWhile(this, data)
}

trait PlusNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitPlusNode(this, data)
}

trait MinusNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitMinusNode(this, data)
}

trait ToLeftNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitToLeftNode(this, data)
}

trait ToRightNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitToRightNode(this, data)
}

trait WriteNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitWriteNode(this, data)
}

trait ReadNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitReadNode(this, data)
}

abstract class ProgramNodeImpl(override val next: ProgramNode?) : ProgramNode

class ProgramWhileImpl(override val content: ProgramNode, next: ProgramNode?): ProgramWhile, ProgramNodeImpl(next)
class PlusNodeImpl(next: ProgramNode?) : ProgramNodeImpl(next), PlusNode
class MinusNodeImpl(next: ProgramNode?) : ProgramNodeImpl(next), MinusNode
class ToLeftNodeImpl(next: ProgramNode?) : ProgramNodeImpl(next), ToLeftNode
class ToRightNodeImpl(next: ProgramNode?) : ProgramNodeImpl(next), ToRightNode
class WriteNodeImpl(next: ProgramNode?) : ProgramNodeImpl(next), WriteNode
class ReadNodeImpl(next: ProgramNode?) : ProgramNodeImpl(next), ReadNode