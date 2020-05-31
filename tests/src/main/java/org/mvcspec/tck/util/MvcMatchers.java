/*
 * Copyright © 2017 Christian Kaltepoth
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
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
