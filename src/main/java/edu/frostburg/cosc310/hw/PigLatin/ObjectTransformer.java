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

/**
 * This interface defines a manipulation from one value of a type to another of
 * the same type.
 *
 * @author Kevin
 */
public interface ObjectTransformer<T> {

    /**
     * Takes a value of type {@code T} and mutates it. The value and its mutated
     * result should be more or less the same.
     *
     * @param t value to mutate
     * @return a mutated version of {@code t}
     */
    T transform(T t);
}
