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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dispatches calls to {@code ot} by first splitting the String into paragraphs
 *
 * @author Kevin Raoofi
 */
public class StringTransformerParagraphProxy implements ObjectTransformer<String> {

    /**
     * Allows for cross platform newlines
     */
    private static final Pattern lineDelimiter = Pattern.compile("([\r\n]+)");

    /**
     * Backend object transformer
     */
    private final ObjectTransformer<String> ot;

    /**
     * Dispatches calls to {@code ot} by first splitting the String into
     * paragraphs
     *
     * @param ot backend
     */
    public StringTransformerParagraphProxy(ObjectTransformer<String> ot) {
        this.ot = ot;
    }

    @Override
    public String transform(String s) {

        int index = 0;
        Matcher m = lineDelimiter.matcher(s);
        StringBuilder sb = new StringBuilder();

        while (m.find()) {
            CharSequence delimiter = s.subSequence(m.start(), m.end());
            String word = s.subSequence(index, m.start()).toString();
            index = m.end();

            String transformedWord = ot.transform(word);

            sb.append(transformedWord);
            sb.append(delimiter);
        }

        String lastWord = s.subSequence(index, s.length()).toString();
        sb.append(ot.transform(lastWord));
        return sb.toString();
    }

}
