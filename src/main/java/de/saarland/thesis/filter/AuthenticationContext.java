package de.saarland.thesis.filter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sireto
 */
public class AuthenticationContext implements FilterContext {

    private String[] rootWords = new String[]{"login", "authenticate", "sign up", "password", "register"};

    @Override
    public Set<String> getRootWords() {
        Set<String> words = new HashSet<String>();
        for (String word : rootWords) {
            words.add(word);
        }
        return words;
    }

    @Override
    public Set<String> getRelatedWords() {
        Set<String> words = new HashSet<String>();
        for (String word : rootWords) {
            words.addAll(SynonymFinder.getInstance().getSynonyms(word));
        }
        return words;
    }

}
