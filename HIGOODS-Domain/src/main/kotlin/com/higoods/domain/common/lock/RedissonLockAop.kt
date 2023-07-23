package com.higoods.domain.common.lock

import com.higoods.common.exception.custom.NotAvailableRedissonLockException
import com.higoods.domain.common.lock.CustomSpringELParser.getDynamicValue
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionTimedOutException

@Aspect
@Component
@ConditionalOnExpression("\${ableRedissonLock:true}")
class RedissonLockAop(
    private val redissonClient: RedissonClient,
    private val redissonCallNewTransaction: RedissonCallNewTransaction
) {
    private val REDISSON_LOCK_PREFIX = "LOCK:"

    @Around("@annotation(com.higoods.domain.common.lock.RedissonLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val redissonLock = method.getAnnotation(RedissonLock::class.java)
        val baseKey: String = redissonLock.lockName

        val dynamicKey = getDynamicValue(
            signature.parameterNames,
            joinPoint.args,
            redissonLock.key
        )

        val rLock = redissonClient.getLock("$REDISSON_LOCK_PREFIX$baseKey:$dynamicKey")

        try {
            val available = rLock.tryLock(redissonLock.waitTime, redissonLock.leaseTime, redissonLock.timeUnit)
            if (!available) {
                throw NotAvailableRedissonLockException.EXCEPTION
            }

            return redissonCallNewTransaction
                .proceed(joinPoint)
        } catch (e: TransactionTimedOutException) { // HiGoodsCodeException ,HiGoodsDynamicException
            throw e
        } finally {
            try {
                rLock.unlock()
            } catch (e: IllegalMonitorStateException) {
//                log.error(e.toString() + baseKey + dynamicKey)
                throw e
            }
        }
    }
}
