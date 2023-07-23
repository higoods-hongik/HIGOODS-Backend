package com.higoods.domain.common.lock

import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

/**
 * Spring Expression Language Parser
 *
 * example
 * // (1)
 * @DistributedLock(key = "#lockName")
 * public void shipment(String lockName) {
 *
 * // (2)
 * @DistributedLock(key = "#model.getName().concat('-').concat(#model.getShipmentOrderNumber())")
 * public void shipment(ShipmentModel model) {
 *
 * 위 예시처럼 파싱을 해줍니다.
 */
object CustomSpringELParser {
    fun getDynamicValue(parameterNames: Array<String>, args: Array<Any>, key: String): String {
        val parser: ExpressionParser = SpelExpressionParser()
        val context = StandardEvaluationContext()
        for (i in parameterNames.indices) {
            context.setVariable(parameterNames[i], args[i])
        }
        return parser.parseExpression(key).getValue(context, Any::class.java).toString()
    }
}
