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

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests {@link PigLatinTransformer}. Tests are boring. The names of the methods
 * should be more or less good enough.
 *
 * @author Kevin Raoofi
 */
public class PigLatinTransformerTest {

    /**
     * Test of transform method, of class PigLatinTransformer.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        String s = "Happy dUck glove\tegg INBOX  eight\n\nbarbacoa";
        PigLatinTransformer instance = new PigLatinTransformer();
        String expResult = "Appyhay Uckday lovegay\teggway INBOXWAY  eightway\n\narbacoabay";
        String result = instance.transform(s);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of transform method, of class PigLatinTransformer.
     */
    @Test
    public void testTransformConsonantCluster() {
        System.out.println("transform");
        String s = "Happy dUck glove\tegg INBOX  eight\n\nbarbacoa";
        PigLatinTransformer instance = new PigLatinTransformer(true);
        String expResult = "Appyhay Uckday oveglay\teggway INBOXWAY  eightway\n\narbacoabay";
        String result = instance.transform(s);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testVowelSet() {
        System.out.println("");
        Set<Character> vowels = PigLatinTransformer.vowels;

        System.out.println(vowels);

        assertTrue(vowels.contains('A'));
        assertTrue(vowels.contains('E'));
        assertTrue(vowels.contains('I'));
        assertTrue(vowels.contains('O'));
        assertTrue(vowels.contains('U'));
    }

    @Test
    public void testCheckVowel() {
        assertTrue(PigLatinTransformer.startsWithVowel("A"));
        assertFalse(PigLatinTransformer.startsWithVowel("B"));

    }

    @Test
    public void testConsonantSet() {

        Set<Character> consonants = PigLatinTransformer.consonants;

        System.out.println(consonants);

        assertFalse(consonants.contains('A'));
        assertFalse(consonants.contains('E'));
        assertFalse(consonants.contains('I'));
        assertFalse(consonants.contains('O'));
        assertFalse(consonants.contains('U'));

        assertTrue(consonants.contains('L'));
    }

    @Test
    public void testCheckConsonant() {
        assertTrue(PigLatinTransformer.startsWithConsonant("B"));
        assertFalse(PigLatinTransformer.startsWithConsonant("A"));
    }
}
