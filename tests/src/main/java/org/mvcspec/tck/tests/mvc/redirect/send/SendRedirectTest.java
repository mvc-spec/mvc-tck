/*
 * Copyright © 2018 Christian Kaltepoth
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
package org.mvcspec.tck.tests.mvc.redirect.send;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
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

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mvcspec.tck.util.MvcMatchers.isRedirectStatus;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class SendRedirectTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(SendRedirectController.class)
                .addView("Redirect target", "target.jsp")
                .build();
    }

    @Before
    public void setUp() throws Exception {
        webClient = new WebClient();
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    }

    /**
     * Controllers can redirect using a JAX-RS Response object
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "redirect-response")
    public void redirectViaResponse() throws IOException {

        WebResponse response = webClient
                .getPage(baseUrl.toString() + "mvc/send-redirect/response")
                .getWebResponse();

        assertThat(response.getStatusCode(), isRedirectStatus());
        assertThat(response.getResponseHeaderValue("Location"), endsWith("/target"));

    }

    /**
     * Controllers can redirect using the "redirect:" prefix
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "redirect-prefix")
    public void redirectViaRedirectPrefix() throws IOException {

        WebResponse response = webClient
                .getPage(baseUrl.toString() + "mvc/send-redirect/prefix")
                .getWebResponse();

        assertThat(response.getStatusCode(), isRedirectStatus());
        assertThat(response.getResponseHeaderValue("Location"), endsWith("/target"));

    }

    /**
     * Path should be relative using response
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "redirect-relative")
    public void relativePathResponse() throws IOException {

        WebResponse response = webClient
                .getPage(baseUrl.toString() + "mvc/send-redirect/response")
                .getWebResponse();

        assertThat(response.getStatusCode(), isRedirectStatus());
        assertThat(
                response.getResponseHeaderValue("Location"),
                equalTo(baseUrl + "mvc/send-redirect/target")
        );

    }

    /**
     * Path should be relative using prefix
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "redirect-relative")
    public void relativePathRedirectPrefix() throws IOException {

        WebResponse response = webClient
                .getPage(baseUrl.toString() + "mvc/send-redirect/prefix")
                .getWebResponse();

        assertThat(response.getStatusCode(), isRedirectStatus());
        assertThat(
                response.getResponseHeaderValue("Location"),
                equalTo(baseUrl + "mvc/send-redirect/target")
        );

    }

    /**
     * Redirect must use 303 or 302 status code
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "redirect-303-302")
    public void usesCorrectStatusCide() throws IOException {

        WebResponse response = webClient
                .getPage(baseUrl.toString() + "mvc/send-redirect/prefix")
                .getWebResponse();

        assertThat(response.getStatusCode(), anyOf(equalTo(303), equalTo(302)));
        assertThat(response.getResponseHeaderValue("Location"), endsWith("/target"));

    }

}
