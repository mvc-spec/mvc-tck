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
package org.mvcspec.tck.tests.security.xss;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class EncodersTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(EncodersController.class)
                .addView("<html>" +
                                "<span id='injected'>${injectedEncoders != null}</span>" +
                                "<span id='el'>${mvc.encoders != null}</span>" +
                                "</html>",
                        "access.jsp")
                .addView("${mvc.encoders.html(value)}", "encode-html.jsp")
                .addView("${mvc.encoders.js(value)}", "encode-js.jsp")
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
    @SpecAssertion(section = Sections.SECURITY_XSS, id = "xss-encoders-obj")
    public void encodersInjectable() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/xss/access");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getElementById("injected").getTextContent(), equalTo("true"));

    }

    /**
     * Encodes is available via EL
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_XSS, id = "xss-encoders-obj")
    public void encodersAvailableFromEl() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/xss/access");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getElementById("el").getTextContent(), equalTo("true"));

    }

    /**
     * Encodes HTML
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_XSS, id = "xss-escaping")
    public void encodesHtml() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/xss/encode-html");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                equalTo("&amp;&lt;&gt;&#34;&#39;")
        );

    }

    /**
     * Encodes JavaScript
     */
    @Test
    @SpecAssertion(section = Sections.SECURITY_XSS, id = "xss-escaping")
    public void encodesJavaScript() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/xss/encode-js");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                equalTo("\\b\\t\\n\\f\\r\\/\\\\\\x22\\x26\\x27")
        );

    }

}
