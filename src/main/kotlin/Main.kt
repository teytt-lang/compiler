package diogommarto.kotlin

import diogommarto.kotlin.generated.TeyttBaseVisitor
import diogommarto.kotlin.generated.TeyttLexer
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import diogommarto.kotlin.generated.TeyttParser

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val stream = CharStreams.fromString("1+1")
    val lexer = TeyttLexer(stream)
    val tokens = CommonTokenStream(lexer)
    val parser = TeyttParser(tokens)

    // print tree
    val tree = parser.expr()
    println(tree.toStringTree(parser))


}