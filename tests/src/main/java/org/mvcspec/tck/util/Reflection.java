/*
 * Copyright © 2017 Christian Kaltepoth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.mvcspec.tck.util;

public class Reflection {

    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.cast(clazz.newInstance());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot find public default constructor: " + clazz.getName());
        } catch (InstantiationException e) {
            throw new IllegalStateException("Failed to create instance", e);
        }
    }

    public static <T> Class<T> loadClass(String fqcn) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            return (Class<T>) classLoader.loadClass(fqcn);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find class: " + fqcn);
        }
    }

}
