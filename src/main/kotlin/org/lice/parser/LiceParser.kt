package org.lice.parser

import org.lice.compiler.model.Node
import org.lice.compiler.model.StringNode
import org.lice.compiler.util.SymbolList
import java.io.Reader
import java.io.StringReader
import kotlin.text.isBlank

/**
 * Created by glavo on 17-4-2.
 *
 * @author Glavo
 * @since v3.0
 */
class LiceParser(private val reader: Reader) : Parser {
	private var line: Int = 1
	private var c: Char? = null
	private var eof: Boolean = false

	private val node: StringNode by lazy {


		TODO("")
	}

	constructor(str: String) : this(StringReader(str))

	override fun stringNode(): StringNode = node

	private fun read(): Char? {
		val i = reader.read()
		if (i == -1) {
			c = null
			return null
		}

		c = i.toChar()
		if (c == '\n')
			line++

		return i.toChar()
	}

	private fun skip(): Unit {
		while (c.isBlank())
			read()
	}

	private fun nextToken(): Token {
		skip()

		if (c == null)
			return EmptyToken

		val sb = StringBuilder()

		when (c) {
			'\"' -> {
				loop@
				while (true) {
					when (read()) {
						'\\' -> {
							when (read()) {
								'\\' -> {
									sb.append('\\')
									continue@loop
								}
								'\"' -> {
									sb.append('\"')
									continue@loop
								}
								'/' -> {
									sb.append('/')
									continue@loop
								}
								'b' -> {
									sb.append('\b')
									continue@loop
								}
								'n' -> {
									sb.append('\n')
									continue@loop
								}
								'r' -> {
									sb.append('\r')
									continue@loop
								}
								't' -> {
									sb.append('\t')
									continue@loop
								}
								'u' -> {
									val ii = Integer.parseInt(StringBuilder()
										.append(read()
											?: throw ParserException("error: unclosed string literal"))
										.append(read()
											?: throw ParserException("error: unclosed string literal"))
										.append(read()
											?: throw ParserException("error: unclosed string literal"))
										.append(read()
											?: throw ParserException("error: unclosed string literal"))
										.toString(), 16)
									sb.append(ii.toChar())
									continue@loop
								}
								null -> throw ParserException("error: unclosed string literal")

								else -> throw ParserException("error: invalid escape character: " + c)
							}
						}
						'\"' -> {
							read()
							return StringToken(sb.toString())
						}
						else -> {
							sb.append(c ?: throw ParserException("error: unclosed string literal"))
						}
					}
				}

			}


		}

		TODO("")
	}
}

interface Token {
	val value: String

	companion object {
		val LP: Token = object : Token {
			override val value: String = "("
		}

		val RP: Token = object : Token {
			override val value: String = ""
		}
	}
}

private data class StringToken(override val value: String) : Token

private data class SymbolToken(override val value: String) : Token

private data class NumberToken(override val value: String) : Token {

}

private object EmptyToken : Token {
	override val value: String = ""
}

