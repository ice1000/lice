package org.lice.repl

import org.lice.compiler.parse.buildNode
import org.lice.compiler.parse.mapAst
import org.lice.compiler.util.println
import org.lice.core.SymbolList
import org.lice.lang.Echoer
import org.lice.VERSION
import org.lice.core.bindings
import javax.script.Bindings

/**
 * starting the read-eval-print-loop machine
 * Created by ice1000 on 2017/2/23.
 *
 * @author ice1000
 * @since 1.0.0
 */
class Repl
@JvmOverloads
constructor(val symbolList: Bindings = bindings(true)) {

	init {
		"""Lice language repl $VERSION
			|see: https://github.com/lice-lang/lice

			|剑未佩妥，出门已是江湖。千帆过尽，归来仍是少年。"""
				.trimMargin()
				.println()
		Echoer.echo(HINT)
	}

	fun handle(str: String): Boolean {
		try {
			mapAst(buildNode(str), symbolList).eval()
		} catch(e: Throwable) {
//			stackTrace = e
			Echoer.echolnErr(e.message ?: "")
		}
		Echoer.echo(HINT)
		return true
	}

	companion object HintHolder {
		const val HINT = "|> "
	}
}
