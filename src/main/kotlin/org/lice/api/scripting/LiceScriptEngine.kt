package org.lice.api.scripting

import org.lice.Lice
import org.lice.core.SymbolList
import org.lice.util.cast
import java.io.Reader
import javax.script.*

/**
 * Created by Glavo on 17-6-8.
 *
 * @author Glavo
 * @since 0.1.0
 */
class LiceScriptEngine : ScriptEngine {
	private var context: LiceContext = LiceContext()
	override fun getContext() = context
	override fun setContext(context: ScriptContext) {
		if (context is LiceContext) this.context = context
	}

	override fun getFactory() = LiceScriptEngineFactory()
	override fun eval(script: String, context: ScriptContext) = eval(script, (context as LiceContext).bindings)
	override fun eval(script: String) = eval(script, context.bindings)
	override fun eval(reader: Reader) = eval(reader.readText(), context.bindings)
	override fun eval(reader: Reader, n: Bindings) = eval(reader.readText(), n)
	override fun eval(reader: Reader, context: ScriptContext) = eval(reader.readText(), (context as LiceContext).bindings)
	override fun eval(script: String, n: Bindings) = Lice.run(script, cast(n))
	override fun get(key: String?): Any? = context.bindings[key]
	override fun put(key: String, value: Any?) {
		context.bindings[key] = value
	}

	override fun createBindings() = SymbolList()
	override fun getBindings(scope: Int) = context.bindings
	override fun setBindings(bindings: Bindings?, scope: Int) {
		if (bindings is SymbolList) context.bindings = bindings
	}
}
