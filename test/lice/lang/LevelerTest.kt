package lice.lang

import lice.lang.NumberOperator.Leveler.plusValue
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ice1000 on 2017/3/1.
 *
 * @author ice1000
 */
class LevelerTest {
	@Test
	fun plusValue() {
		println(plusValue(1, 1L).javaClass)
		println(plusValue(1L, 1.0).javaClass)
		println(plusValue(1L, 1F).javaClass)
		println(plusValue(1, BigInteger("1")).javaClass)
		println(plusValue(BigInteger("1"), BigInteger("1")).javaClass)
		println(plusValue(1.0, BigInteger("1")).javaClass)
		println(plusValue(1F, BigInteger("1")).javaClass)
		println(plusValue(BigInteger("1"), BigDecimal("1.0")).javaClass)
		println(plusValue(BigDecimal("1"), BigDecimal("1.0")).javaClass)
	}

	@Test
	fun plus() {
		val pluser = NumberOperator(1.toByte())
		pluser.plus(1.toByte())
		println(pluser.result.javaClass)
		pluser.plus(1.toShort())
		println(pluser.result.javaClass)
		pluser.plus(BigInteger("1"))
		println(pluser.result.javaClass)
		pluser.plus(1.toInt())
		println(pluser.result.javaClass)
		pluser.plus(1.toFloat())
		println(pluser.result.javaClass)
		pluser.plus(1.toDouble())
		println(pluser.result.javaClass)
		pluser.plus(BigDecimal("1"))
		println(pluser.result.javaClass)
	}
}