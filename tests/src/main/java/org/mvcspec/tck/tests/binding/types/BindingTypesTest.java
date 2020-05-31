/*
 * Copyright © 2019 Christian Kaltepoth
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
package org.mvcspec.tck.tests.binding.types;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class BindingTypesTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(BindingTypesController.class)
                .addClass(BindingTypesLocaleResolver.class)
                .addView("binding/types/view.jsp")
                .build();
    }

    @Before
    public void before() {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(false);
    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "all-binding-annotations")
    })
    public void bindingWithPathParam() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/binding/types/path/1,234");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Value: [1.234]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "all-binding-annotations")
    })
    public void bindingWithQueryParam() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/binding/types/query?value=2,34");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Value: [2.34]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "all-binding-annotations")
    })
    public void bindingWithHeaderParam() throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/binding/types/header"));
        request.setAdditionalHeader("X-Header-Value", "3,95");
        Page page = webClient.getPage(request);

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Value: [3.95]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "all-binding-annotations")
    })
    public void bindingWithFormParam() throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/binding/types/form"));
        request.setHttpMethod(HttpMethod.POST);
        request.setRequestParameters(Collections.singletonList(
                new NameValuePair("value", "6,2")
        ));
        Page page = webClient.getPage(request);

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Value: [6.2]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "all-binding-annotations")
    })
    public void bindingWithMatrixParam() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/binding/types/matrix;value=2,11");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Value: [2.11]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "all-binding-annotations")
    })
    public void bindingWithCookieParam() throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/binding/types/cookie"));
        request.setAdditionalHeader("Cookie", "X-COOKIE-VALUE=1.234"); // comma doesn't work well for cookies
        Page page = webClient.getPage(request);

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Value: [1234]"));

    }

}
