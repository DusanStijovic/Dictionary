package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Order;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Order(3)
public class PerformanceMonitorAspect {

    private static final Logger logger = Logger.getLogger(PerformanceMonitorAspect.class.getName());

    public static final int ONE_MILLISECOND_NANO_SECONDS = 1000000;

    @Pointcut("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.searchByStrategy(..))")
    public void searchWordPointcut() {
    }

    @Pointcut("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.removeByStrategy(..))")
    public void removeWordsPointcut() {
    }

    @Pointcut("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.addNewWord(..))")
    public void addWordPointcut() {
    }

    @Around("searchWordPointcut() || addWordPointcut() || removeWordsPointcut()")
    public Object calculateExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            double elapsedInMs = calculateDuration(startTime);
            logDuration(proceedingJoinPoint, elapsedInMs);
        }
    }

    private void logDuration(ProceedingJoinPoint proceedingJoinPoint, double elapsedInMs) {
        String methodName = proceedingJoinPoint.getSignature().getName();
        logger.log(Level.INFO, "Method: {0}, execution time: {1}ms", new Object[]{methodName, elapsedInMs});
    }

    private double calculateDuration(long startTime) {
        long now = System.nanoTime();
        long elapsedInNanoSec = now - startTime;
        return elapsedInNanoSec / (double) ONE_MILLISECOND_NANO_SECONDS;
    }

}
