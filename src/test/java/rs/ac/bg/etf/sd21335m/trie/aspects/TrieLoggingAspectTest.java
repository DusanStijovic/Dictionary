package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategy;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.PrefixMatchStrategy;

import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TrieLoggingAspectTest {

    private final TrieLoggingAspect aspect = new TrieLoggingAspect();
    @Mock
    private JoinPoint joinPoint;

    @Test
    void logSearchedWords() {
        MatchStrategy matchStrategy = Mockito.mock(PrefixMatchStrategy.class);
        HashSet<String> searchResult = new HashSet<>(List.of("dusan"));
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{matchStrategy, "dusan"});
        aspect.logSearchedWords(joinPoint, searchResult);
    }

    @Test
    void logDeletedWords() {
        MatchStrategy matchStrategy = Mockito.mock(PrefixMatchStrategy.class);
        joinPoint = Mockito.mock(JoinPoint.class);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{matchStrategy, "dusan"});
        aspect.logDeletedWords(joinPoint);
    }

    @Test
    void logAddedWord() {
        joinPoint = Mockito.mock(JoinPoint.class);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{"dusan"});
        aspect.logAddedWord(joinPoint);
    }
}