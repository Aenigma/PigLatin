/*
 * Copyright 2014 Kevin Raoofi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.frostburg.cosc310.hw.PigLatin;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation to transform text to pig latin.
 *
 * @author Kevin Raoofi
 */
public class PigLatinTransformer implements ObjectTransformer<String> {

    private static final Pattern whiteSpace = Pattern.compile("(\\W+)");
    private static final Pattern upperToLowerCase = Pattern.compile("[A-Z][a-z]*");
    private static final Pattern allUpper = Pattern.compile("[A-Z]+");

    static final Set<Character> vowels = new TreeSet<>();
    static final Set<Character> consonants = new TreeSet<>();

    static {
        vowels.addAll(Arrays.asList('A', 'E', 'I', 'O', 'U'));

        for (char c = 'A'; c <= 'Z'; c++) {
            consonants.add(c);
        }

        consonants.removeAll(vowels);
    }

    /**
     *
     * @param word a word
     * @return true if word starts with a vowel
     */
    static boolean startsWithVowel(String word) {
        return vowels.contains(word.toUpperCase().charAt(0));
    }

    /**
     *
     * @param word a word
     * @return true if word starts with a vowel
     */
    static boolean startsWithConsonant(String word) {
        return consonants.contains(word.toUpperCase().charAt(0));
    }

    /**
     * Specifies that a word starting with a consonant will be processed by
     * taking a cluster for consonants at the beginning rather than just taking
     * the first consonant.
     */
    private final boolean doConsonantCluster;

    /**
     * A cache is implemented by having the key be the original word and
     */
    private final Map<String, String> plCache;

    /**
     * Defaults to not clustering consonants with a {@link WeakHashMap} caching
     */
    public PigLatinTransformer() {
        this(false);
    }

    /**
     * Creates instance with consonant cluster.
     *
     * @param doConsonantCluster whether or not to replace just one consonant or
     * a consonant cluster
     */
    public PigLatinTransformer(boolean doConsonantCluster) {
        this(doConsonantCluster, new WeakHashMap<String, String>(0x1000000));
    }

    /**
     * Creates instance with custom caching backend and consonant cluster.
     * Caching implementation should be ready to deal with large amounts of
     * Strings and is recommended to use weak references.
     *
     * @param doConsonantCluster whether or not to replace just one consonant or
     * a consonant cluster
     * @param plCache caching backend
     */
    public PigLatinTransformer(boolean doConsonantCluster, Map<String, String> plCache) {
        this.doConsonantCluster = doConsonantCluster;
        this.plCache = plCache;
    }

    /**
     * Appends {@code way} to word. It is assumed that the given word starts
     * with a vowel.
     *
     * @param word a word which starts with a vowel
     * @return Pig Latin version of word
     */
    protected String processVowelWord(String word) {
        return word + "way";
    }

    /**
     * Appends {@code ay} to word after moving consonants to the end of the
     * world.
     *
     * @param word a word which starts with a consonant
     * @return Pig Latin version of word
     */
    protected String processConsonantWord(String word) {
        int consonantClusterIndex = 1;
        while (doConsonantCluster
                && consonantClusterIndex < word.length()) {
            if (consonants.contains(word.toUpperCase().charAt(consonantClusterIndex))) {
                ++consonantClusterIndex;
            } else {
                break;
            }
        }

        return word.substring(consonantClusterIndex) + word.substring(0, consonantClusterIndex) + "ay";
    }

    /**
     * Checks if a word starts off with a vowel or consonant and processes them
     * appropriately to translate it into Pig Latin.
     *
     * This method does not attempt to do any kind of manipulation of the
     * casing.
     *
     * @param word word to transform into Pig Latin variant
     * @return Pig Latin variant of word
     */
    protected String transformWordNoCase(String word) {
        if (word.length() < 1) {
            return word;
        }
        if (startsWithVowel(word)) {
            return processVowelWord(word);
        } else if (startsWithConsonant(word)) {
            return processConsonantWord(word);
        } else {
            return word;
        }
    }

    /**
     *
     * <p>
     * Makes a call to {@link #transformWordNoCase(java.lang.String)} and
     * determines how to intelligently manage the casing of the results by
     * inspecting the original word. This method manages 3 different scenarios:
     * </p>
     *
     * <ol>
     * <li>All uppercase</li>
     * <li>First letter is uppercase and the rest is lowercase</li>
     * <li>Other mixed casing or all lowercase</li>
     * </ol>
     *
     * <p>
     * For the first scenario, the returned result has
     * {@link String#toUpperCase()} called on it. For the second scenario, the
     * value has just the first letter of the word transformed to uppercase and
     * the rest is made lowercase. Finally, in the last variant, no
     * modifications are made from the call to
     * {@link #transformWordNoCase(java.lang.String)}.
     * </p>
     *
     *
     *
     * @param word word to transform into Pig Latin variant
     * @return Pig Latin variant of word
     */
    protected String transformWord(String word) {
        String result = transformWordNoCase(word);

        if (upperToLowerCase.matcher(word).matches()) {
            result = result.toLowerCase();
            result = Character.toUpperCase(result.charAt(0))
                    + result.substring(1);
        } else if (allUpper.matcher(word).matches()) {
            result = result.toUpperCase();
        }
        return result;
    }

    @Override
    public String transform(String s) {
        String result = plCache.get(s);
        if (result != null) {
            return result;
        }

        StringBuilder sb = new StringBuilder();
        Matcher m = whiteSpace.matcher(s);
        int index = 0;

        while (m.find()) {
            CharSequence delimiter = s.subSequence(m.start(), m.end());
            String word = s.subSequence(index, m.start()).toString();
            index = m.end();

            String transformedWord = plCache.get(word);
            if (plCache.get(word) == null) {
                transformedWord = transformWord(word);
            }

            plCache.put(word, transformedWord);

            sb.append(transformedWord);
            sb.append(delimiter);
        }

        String lastWord = s.subSequence(index, s.length()).toString();
        sb.append(transformWord(lastWord));
        result = sb.toString();

        plCache.put(s, result);

        return result;
    }

}
