/*
 * Copyright © 2017 Ivar Grimstad (ivar.grimstad@gmail.com)
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
 */
package org.mvcspec.tck.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class MvcMatchers {

    public static Matcher<Integer> isRedirectStatus() {
        return new BaseMatcher<Integer>() {

            @Override
            public boolean matches(Object o) {
                int status = (int) o;
                return status >= 300 && status < 400;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is not a 3xx status code");
            }

        };
    }

    public static Matcher<String> isNotBlank() {
        return new BaseMatcher<String>() {

            @Override
            public boolean matches(Object item) {
                return item != null && !item.toString().trim().isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("to be not blank");
            }
        };
    }

}
