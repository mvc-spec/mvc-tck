/*
 * Copyright © 2018 Christian Kaltepoth
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
package org.mvcspec.tck.tests.mvc.redirect.scope;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class RedirectScopeTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(RedirectScopeController.class)
                .addClass(RequestScopeBean.class)
                .addClass(SessionScopeBean.class)
                .addClass(RedirectScopeBean.class)
                .addView("<html>" +
                                "<h1>Scopes</h1>" +
                                "<p>Request = [${requestScopeBean.value}]</p>\n" +
                                "<p>Session = [${sessionScopeBean.value}]</p>\n" +
                                "<p>Redirect = [${redirectScopeBean.value}]</p>" +
                                "</html>",
                        "read.jsp")
                .build();
    }

    /**
     * A bean in request scope doesn't survive a redirect. So we won't get the bean value
     * after the redirect to the read page.
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "scope-request")
    public void requestScope() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/scope/write-redirect-read")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Request = []"));

    }

    /**
     * A bean in session scope is available in all following requests. So we can access it
     * after the redirect and in direct requests to the read page after that.
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "scope-session")
    public void sessionScope() throws IOException {

        WebClient webClient = new WebClient();

        WebResponse response1 = webClient
                .getPage(baseUrl.toString() + "mvc/scope/write-redirect-read")
                .getWebResponse();

        assertThat(response1.getStatusCode(), equalTo(200));
        assertThat(response1.getContentAsString(), containsString("Session = [bar]"));

        WebResponse response2 = webClient
                .getPage(baseUrl.toString() + "mvc/scope/read")
                .getWebResponse();

        assertThat(response2.getStatusCode(), equalTo(200));
        assertThat(response2.getContentAsString(), containsString("Session = [bar]"));


    }

    /**
     * A bean in redirect scope is available in the page we redirect to, but not in
     * requests sent after that.
     */
    @Test
    @SpecAssertion(section = Sections.MVC_REDIRECT, id = "scope-redirect")
    public void redirectScope() throws IOException {

        WebClient webClient = new WebClient();

        WebResponse response1 = webClient
                .getPage(baseUrl.toString() + "mvc/scope/write-redirect-read")
                .getWebResponse();

        assertThat(response1.getStatusCode(), equalTo(200));
        assertThat(response1.getContentAsString(), containsString("Redirect = [foobar]"));

        WebResponse response2 = webClient
                .getPage(baseUrl.toString() + "mvc/scope/read")
                .getWebResponse();

        assertThat(response2.getStatusCode(), equalTo(200));
        assertThat(response2.getContentAsString(), containsString("Redirect = []"));


    }

}
