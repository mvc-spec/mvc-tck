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
package org.mvcspec.tck.tests.i18n.algorithm;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class I18nAlgorithmTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(I18nAlgorithmController.class)
                .addClass(FirstLocaleResolver.class)
                .addClass(SecondLocaleResolver.class)
                .addClass(ThirdLocaleResolver.class)
                .addClass(ResolverChainLogger.class)
                .addView("i18n/algorithm/view.jsp")
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
            @SpecAssertion(section = Sections.I18N_RESOLVING_ALGORITHM, id = "resolver-discovery"),
            @SpecAssertion(section = Sections.I18N_RESOLVING_ALGORITHM, id = "resolve-algorithm")
    })
    public void highestPrioExecutedFirst() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/i18n/algorithm");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(getInvocationLogString(page), startsWith("FirstLocaleResolver"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.I18N_RESOLVING_ALGORITHM, id = "resolver-discovery"),
            @SpecAssertion(section = Sections.I18N_RESOLVING_ALGORITHM, id = "resolve-algorithm")
    })
    public void continueChainForNullResult() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/i18n/algorithm");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(getInvocationLogString(page), containsString(",SecondLocaleResolver"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.I18N_RESOLVING_ALGORITHM, id = "resolver-discovery"),
            @SpecAssertion(section = Sections.I18N_RESOLVING_ALGORITHM, id = "resolve-algorithm")
    })
    public void chainStopsForNonNullResult() throws IOException {

        HtmlPage page = webClient.getPage(baseUrl.toString() + "mvc/i18n/algorithm");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("Language = [de]"));
        assertThat(getInvocationLogString(page), not(containsString("ThirdLocaleResolver")));

    }

    private String getInvocationLogString(HtmlPage page) {
        DomElement element = page.getElementById("log");
        return element != null && element.getTextContent() != null
                ? element.getTextContent().trim()
                : null;
    }

}
