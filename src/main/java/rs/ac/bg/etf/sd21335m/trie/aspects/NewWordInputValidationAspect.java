package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Order;

@Aspect
@Order(3)
public class NewWordInputValidationAspect {

    @Pointcut("execution(* rs.ac.bg.etf.sd21335m.trie.types.Trie.addNewWord(..))")
    void addWordPointcut() {
    }
    @Around("addWordPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object word = proceedingJoinPoint.getArgs()[0];
        if (word != null) {
            String wordAsString = String.valueOf(word);
            word = returnAlphaNumeriricAndWhiteSpaceCharacters(wordAsString);
        }
        return proceedingJoinPoint.proceed(new Object[]{word});
    }

    private String returnAlphaNumeriricAndWhiteSpaceCharacters(String word) {
        return word.replaceAll("[^a-zA-Z0-9\\s]", "");
    }
}
