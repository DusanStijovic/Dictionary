package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Order;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Order(0)
public class TrieLoggingAspect {
    private static final Logger logger = Logger.getLogger(TrieLoggingAspect.class.getName());

    private static String getArgumentsAsString(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return Arrays.toString(args);
    }

    @AfterReturning(pointcut = "execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.searchByStrategy(..))", returning = "searchResult")
    public void logSearchedWords(JoinPoint joinPoint, Set<String> searchResult) {
        logger.log(Level.INFO, "searchByStrategy using strategy [{0}] and search string: [{1}]", joinPoint.getArgs());
        if (searchResult.isEmpty()) {
            logger.log(Level.INFO, "Words not fond");
        } else {
            logger.log(Level.INFO, "Found words: {0}", searchResult.toArray());
        }
    }

    @Before("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.removeByStrategy(..))")
    public void logDeletedWords(JoinPoint joinPoint) {
        logger.log(Level.INFO, "Calling removeByStrategy using strategy [{0}] and delete string: [{1}]", joinPoint.getArgs());
    }

    @Before("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.addNewWord(..))")
    public void logAddedWord(JoinPoint joinPoint) {
        String argsString = getArgumentsAsString(joinPoint);
        logger.log(Level.INFO, "Calling addNewWord: {0}", argsString);
    }
}
