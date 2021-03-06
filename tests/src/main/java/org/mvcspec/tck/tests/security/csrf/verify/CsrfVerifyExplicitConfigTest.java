/*
 * Copyright © 2019 Christian Kaltepoth
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
package org.mvcspec.tck.tests.security.csrf.verify;

import com.gargoylesoftware.htmlunit.Page;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class CsrfVerifyExplicitConfigTest extends AbstractCsrfVerifyTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive(CsrfVerifyExplicitConfigApplication.class)
                .addClass(CsrfProtectedController.class)
                .addView("csrf/verify/form.jsp")
                .addView("csrf/verify/success.jsp")
                .build();
    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-hidden-field"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-explict"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-mediatype")
    })
    public void submitFormWithAnnotationAndValidToken() throws IOException {

        Page resultPage = submitForm("with-annotation", true, "Alice");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), containsString("Hi Alice!"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-explict"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-default-mapper")
    })
    public void submitFormWithAnnotationAndInvalidToken() throws IOException {

        Page resultPage = submitForm("with-annotation", false, "Bob");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(403));

    }


    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-hidden-field"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-explict"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-mediatype")
    })
    public void submitFormWithoutAnnotationAndValidToken() throws IOException {

        Page resultPage = submitForm("no-annotation", true, "Charlie");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), containsString("Hi Charlie!"));


    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-explict"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-mediatype")
    })
    public void submitFormWithoutAnnotationAndInvalidToken() throws IOException {

        Page resultPage = submitForm("no-annotation", false, "David");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), containsString("Hi David!"));

    }

}
