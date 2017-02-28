package org.hanuna

import org.junit.Test as test
import org.junit.Assert.*
public class ParserTest {
    fun doTest(expected: String, f: Parser.() -> ProgramNode) {
        assertEquals(expected, Parser().f().accept(BrainFuckVisitor, Unit))
    }

    @test fun simple() {
        doTest("+[>><][]-") {
            parse(
                    {}()[
                            {}({}())
                            ({}())
                            ({}()){}
                        ]
                        [
                            {}
                        ]
                    ({})
            )
        }
    }

    @test fun stub() {
        doTest("",{
            parse(
                    {}
            )
        })
    }

    @test fun stub2() {
        doTest("+",{
            parse(
                    {}()
            )
        })
    }

    @test fun br() {
        doTest("[][][][]") {
            parse(
                    {}[{}][{}][{}][{}]
            )
        }
    }
    @test fun br2() {
        doTest("[[][]][]") {
            parse(
                    {}[{}[{}][{}]][{}]
            )
        }
    }
}