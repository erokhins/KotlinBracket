package org.hanuna


interface ProgramVisitor<D, R> {
    fun visitWhile(programWhile: ProgramWhile, data: D): R
    fun visitPlusNode(node: PlusNode, data: D): R
    fun visitMinusNode(node: MinusNode, data: D): R
    fun visitToLeftNode(node: ToLeftNode, data: D): R
    fun visitToRightNode(node: ToRightNode, data: D): R
    fun visitPrintNode(node: PrintNode, data: D): R
    fun visitReadNode(node: ReadNode, data: D): R
    fun visitEmptyNode(node: EmptyNode, data: D): R
}

interface ProgramNode {
    val next: ProgramNode?

    fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R
}

interface ProgramWhile : ProgramNode {
    val content: ProgramNode
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitWhile(this, data)
}

interface PlusNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitPlusNode(this, data)
}

interface MinusNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitMinusNode(this, data)
}

interface ToLeftNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitToLeftNode(this, data)
}

interface ToRightNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitToRightNode(this, data)
}

interface PrintNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitPrintNode(this, data)
}

interface ReadNode : ProgramNode {
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitReadNode(this, data)
}

object EmptyNode : ProgramNode {
    override val next: ProgramNode? = null
    override fun <D, R> accept(visitor: ProgramVisitor<D, R>, data: D): R = visitor.visitEmptyNode(this, data)
}
