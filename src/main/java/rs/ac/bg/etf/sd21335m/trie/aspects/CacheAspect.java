package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategy;

import java.util.HashMap;
import java.util.Set;

@Aspect
public class CacheAspect {

    private final HashMap<String, Set<String>> savedSearchResult = new HashMap<>();


    @Pointcut("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.searchByStrategy(..))")
    void searchForWordWithStrategy() {
    }


    @AfterReturning(pointcut = "searchForWordWithStrategy()", returning = "searchResult")
    public void logSearchedWords(JoinPoint joinPoint, Set<String> searchResult) {
        String cacheKey = makeCacheKey(joinPoint);
        savedSearchResult.put(cacheKey, searchResult);
    }

    @Around("searchForWordWithStrategy()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object articles = savedSearchResult.get(makeCacheKey(proceedingJoinPoint));
        if (articles == null) {
            articles = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        }
        return articles;
    }

    private String makeCacheKey(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return ((MatchStrategy) args[0]).getType() + args[1].toString();
    }

    @After("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.removeByStrategy(..))")
    public void logDeletedWords() {
        savedSearchResult.clear();
    }

    @After("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.addNewWord(..))")
    public void logAddedWord() {
        savedSearchResult.clear();
    }
}
