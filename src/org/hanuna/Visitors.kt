package org.hanuna


object BrainFuckVisitor : ProgramVisitor<Unit, String> {
    override fun visitWhile(programWhile: ProgramWhile, data: Unit): String = render("[${programWhile.content.accept(this, Unit)}]", programWhile.next)

    override fun visitPlusNode(node: PlusNode, data: Unit): String = render("+", node.next)

    override fun visitMinusNode(node: MinusNode, data: Unit): String = render("-", node.next)

    override fun visitToLeftNode(node: ToLeftNode, data: Unit): String = render("<", node.next)

    override fun visitToRightNode(node: ToRightNode, data: Unit): String = render(">", node.next)

    override fun visitPrintNode(node: PrintNode, data: Unit): String = render(".", node.next)

    override fun visitReadNode(node: ReadNode, data: Unit): String = render(",", node.next)

    override fun visitEmptyNode(node: EmptyNode, data: Unit): String = ""


    private fun render(sign: String, next: ProgramNode?): String {
        if (next == null) return sign

        return sign + next.accept(this, Unit)
    }
}

class ProgramRunVisitor(private val input: Sequence<Char>, private val out: (Char) -> Unit) : ProgramVisitor<Unit, Unit> {
    private val array = CharArray(30000)
    private var offset = 0

    override fun visitWhile(programWhile: ProgramWhile, data: Unit) {
        while (array[offset] != 0.toChar()) programWhile.content.accept(this, Unit)

        programWhile.runNext()
    }

    override fun visitPlusNode(node: PlusNode, data: Unit) {
        array[offset]++

        node.runNext()
    }

    override fun visitMinusNode(node: MinusNode, data: Unit) {
        array[offset]--
        node.runNext()
    }

    override fun visitToLeftNode(node: ToLeftNode, data: Unit) {
        offset--
        node.runNext()
    }

    override fun visitToRightNode(node: ToRightNode, data: Unit) {
        offset++
        node.runNext()
    }

    override fun visitPrintNode(node: PrintNode, data: Unit) {
        out(array[offset])
        node.runNext()
    }

    override fun visitReadNode(node: ReadNode, data: Unit) {
        array[offset] = input.first()
        node.runNext()
    }

    override fun visitEmptyNode(node: EmptyNode, data: Unit) {
        // do nothing
    }

    private fun ProgramNode.runNext() {
        this.next?.accept(this@ProgramRunVisitor, Unit)
    }
}