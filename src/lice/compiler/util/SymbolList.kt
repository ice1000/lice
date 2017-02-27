/**
 * Created by ice1000 on 2017/2/17.
 *
 * @author ice1000
 */
package lice.compiler.util

import lice.compiler.model.Node
import lice.compiler.model.Node.Objects.NullNode
import lice.compiler.model.ValueNode
import lice.core.*
import lice.lang.Symbol
import java.util.*

@Suppress("NOTHING_TO_INLINE")

class SymbolList
@JvmOverloads
constructor(init: Boolean = true) {
	val functions = mutableMapOf<String, (List<Node>) -> Node>()
	val variables = mutableMapOf<String, Node>()

	val rand = Random(System.currentTimeMillis())
	val loadedModules = mutableListOf<Symbol>()

	init {
		if (init) initialize()
	}

	fun initialize() {
		defineFunction("import", { ls ->
			ls.forEach { node ->
				val res = node.eval()
				if (res.o is Symbol) {
					if (res.o in loadedModules) return@defineFunction ValueNode(false)
					loadedModules.add(res.o)
					when (res.o.name) {
						"lice.io" -> addFileFunctions()
						"lice.gui" -> addGUIFunctions()
						"lice.math" -> addMathFunctions()
						"lice.str" -> addStringFunctions()
						"lice.thread" -> addConcurrentFunctions()
						else -> {
							serr("${res.o} not found!")
							return@defineFunction NullNode
						}
					}
				}
			}
			ValueNode(true)
		})
		addStandard()
	}

	fun defineFunction(name: Symbol, node: (List<Node>) -> Node) =
			defineFunction(name.name, node)

	fun defineFunction(name: String, node: (List<Node>) -> Node) {
		functions.put(name, node)
	}

	fun removeFunction(name: String) {
		functions.remove(name)
	}

	fun removeFunction(name: Symbol) =
			removeFunction(name.name)

	fun setVariable(name: Symbol, value: Node) =
			setVariable(name.name, value)

	fun setVariable(name: String, value: Node) {
		variables[name] = value
	}

	fun getVariable(name: String) =
			variables[name]

	fun getVariable(name: Symbol) =
			getVariable(name.name)

	fun getFunction(name: Symbol) =
			getFunction(name.name)

	fun getFunction(name: String) =
			functions[name]
					?: throw ParseException("functions not found: $functions")
}
