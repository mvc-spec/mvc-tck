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
package org.mvcspec.tck.tests.security.csrf.header;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
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
import org.mvcspec.tck.tests.security.CsrfConstants;
import org.mvcspec.tck.util.Archives;
import org.mvcspec.tck.util.MvcMatchers;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class CsrfDefaultHeaderTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
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
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-verify")
    })
    public void submitValidTokenViaForm() throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/header/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // fill name input
        DomElement nameInputElement = formPage.getElementById("input");
        assertNotNull("Name input element not found", nameInputElement);
        nameInputElement.setAttribute("value", "Alice");

        // submit form
        DomElement submitButton = formPage.getElementById("submit");
        assertNotNull("Submit button not found", submitButton);
        Page resultPage = submitButton.click();

        // assert response
        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), containsString("Hi Alice!"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-verify")
    })
    public void submitInvalidTokenViaForm() throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/header/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // fill name input
        DomElement nameInputElement = formPage.getElementById("input");
        assertNotNull("Name input element not found", nameInputElement);
        nameInputElement.setAttribute("value", "Bob");

        // change token to be invalid
        DomElement tokenInputElement = formPage.getElementById("token");
        assertNotNull("Token input element not found", tokenInputElement);
        tokenInputElement.setAttribute("value", "INVALID-TOKEN");

        // submit form
        DomElement submitButton = formPage.getElementById("submit");
        assertNotNull("Submit button not found", submitButton);
        Page resultPage = submitButton.click();

        // assert response
        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(403));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-verify"),
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-default-header-name")
    })
    public void submitValidTokenViaHeader() throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/header/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // get token from header
        String token = formPage.getWebResponse().getResponseHeaderValue(CsrfConstants.CSRF_TOKEN_HEADER_NAME);
        assertThat(token, MvcMatchers.isNotBlank());

        // prepare post request with valid token
        WebRequest postRequest = new WebRequest(new URL(baseUrl.toString() + "mvc/csrf/header/process"));
        postRequest.setHttpMethod(HttpMethod.POST);
        postRequest.setAdditionalHeader(CsrfConstants.CSRF_TOKEN_HEADER_NAME, token);
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
            @SpecAssertion(section = Sections.SECURITY_CSRF, id = "csrf-default-header-name")
    })
    public void submitInvalidTokenViaHeader() throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/header/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // prepare post request with valid token
        WebRequest postRequest = new WebRequest(new URL(baseUrl.toString() + "mvc/csrf/header/process"));
        postRequest.setHttpMethod(HttpMethod.POST);
        postRequest.setAdditionalHeader(CsrfConstants.CSRF_TOKEN_HEADER_NAME, "INVALID-TOKEN");
        postRequest.setRequestParameters(Collections.singletonList(
                new NameValuePair("name", "David")
        ));

        // assert response
        WebResponse postResponse = webClient.loadWebResponse(postRequest);
        assertThat(postResponse.getStatusCode(), equalTo(403));

    }

}
