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
package org.mvcspec.tck.tests.security.csrf.base;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;
import org.mvcspec.tck.util.MvcMatchers;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class CsrfBaseTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(CsrfBaseController.class)
                .addView("csrf/base/base.jsp")
                .build();
    }

    @Before
    public void before() {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(false);
    }

    /**
     * Encodes is available via injection
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-obj")
    public void csrfInstanceViaContext() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/csrf/base");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getElementById("csrf-injected").getTextContent(), equalTo("true"));

    }

    /**
     * Encodes is available via EL
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-obj")
    public void csrfInstanceViaEL() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/csrf/base");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getElementById("csrf-el").getTextContent(), equalTo("true"));

    }


    /**
     * CSRF token can be injected into hidden field
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-hidden-field")
    public void canInjectTokenIntoHiddenField() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/csrf/base");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));

        DomElement inputHidden = page.getElementById("token");
        assertNotNull("Hidden input not found", inputHidden);
        assertThat(inputHidden.getAttribute("name"), MvcMatchers.isNotBlank());
        assertThat(inputHidden.getAttribute("value"), MvcMatchers.isNotBlank());

    }

    /**
     * Token is available from response header
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-inject-header")
    public void tokenIsProvidedViaElAndResponseHeader() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/csrf/base");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));

        DomElement hiddenField = page.getElementById("token");

        String hiddenFieldValue = hiddenField.getAttribute("value");
        assertThat(hiddenFieldValue, MvcMatchers.isNotBlank());

        String headerValue = page.getWebResponse().getResponseHeaderValue("X-CSRF-TOKEN");
        assertThat(headerValue, MvcMatchers.isNotBlank());
        assertThat(headerValue, CoreMatchers.equalTo(hiddenFieldValue));

    }


}
