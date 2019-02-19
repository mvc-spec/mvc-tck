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
package org.mvcspec.tck.tests.security.csrf.header;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
import org.mvcspec.tck.util.MvcMatchers;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class CsrfCustomHeaderTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive(CsrfCustomHeaderApplication.class)
                .addClass(CsrfHeaderController.class)
                .addView("csrf/header/form.jsp")
                .addView("csrf/header/success.jsp")
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
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-verify"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-custom-header-name")
    })
    public void submitValidCustomTokenViaHeader() throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/header/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // get token from header
        String token = formPage.getWebResponse().getResponseHeaderValue("X-CSRF-Custom-Header-Name");
        assertThat(token, MvcMatchers.isNotBlank());

        // prepare post request with valid token
        WebRequest postRequest = new WebRequest(new URL(baseUrl.toString() + "mvc/csrf/header/process"));
        postRequest.setHttpMethod(HttpMethod.POST);
        postRequest.setAdditionalHeader("X-CSRF-Custom-Header-Name", token);
        postRequest.setRequestParameters(Collections.singletonList(
                new NameValuePair("name", "Charlie")
        ));

        // assert response
        WebResponse postResponse = webClient.loadWebResponse(postRequest);
        assertThat(postResponse.getStatusCode(), equalTo(200));
        assertThat(postResponse.getContentAsString(), containsString("Hi Charlie!"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-verify"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-custom-header-name")
    })
    public void submitInvalidCustomTokenViaHeader() throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/header/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // prepare post request with valid token
        WebRequest postRequest = new WebRequest(new URL(baseUrl.toString() + "mvc/csrf/header/process"));
        postRequest.setHttpMethod(HttpMethod.POST);
        postRequest.setAdditionalHeader("X-CSRF-Custom-Header-Name", "INVALID-TOKEN");
        postRequest.setRequestParameters(Collections.singletonList(
                new NameValuePair("name", "David")
        ));

        // assert response
        WebResponse postResponse = webClient.loadWebResponse(postRequest);
        assertThat(postResponse.getStatusCode(), equalTo(403));

    }

}
