package org.lice;

import kotlin.Unit;
import org.lice.compiler.model.ValueNode;
import org.lice.compiler.util.SymbolList;
import org.lice.compiler.util.Utilities;

import java.io.File;

import static org.lice.compiler.parse.Parse.createRootNode;

/**
 * Created by ice1000 on 2017/2/26.
 *
 * @author ice1000
 */
public class JavaAPI {
	public static void main(String[] args) {
		SymbolList sl = new SymbolList();
		final int[] a = {0};
		sl.defineFunction("java-api-invoking", (ln, ls) -> new ValueNode(a[0]++, ln));
		Utilities.forceRun(() -> {
			createRootNode(new File("sample/test10.lice"), sl).eval();
			return Unit.INSTANCE;
		});
	}
}