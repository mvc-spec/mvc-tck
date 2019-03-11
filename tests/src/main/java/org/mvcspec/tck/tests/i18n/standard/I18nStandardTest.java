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
package org.mvcspec.tck.tests.i18n.standard;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
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
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class I18nStandardTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(I18nStandardController.class)
                .addView("i18n/standard/view.jsp")
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
            @SpecAssertion(section = Sections.I18N_DEFAULT_RESOLVER, id = "default-locale-resolver")
    })
    public void singleLocaleInAcceptLanguageHeader() throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/i18n/standard"));
        request.setAdditionalHeader("Accept-Language", "es");

        WebResponse response = webClient.getPage(request).getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Language = [es]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.I18N_DEFAULT_RESOLVER, id = "default-locale-resolver")
    })
    public void multipleLocalesInAcceptLanguageHeader() throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/i18n/standard"));
        request.setAdditionalHeader("Accept-Language", "es;q=0.5,fr;q=0.9");

        WebResponse response = webClient.getPage(request).getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Language = [fr]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.I18N_DEFAULT_RESOLVER, id = "default-locale-resolver")
    })
    public void missingAcceptLanguageHeader() throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/i18n/standard"));
        request.removeAdditionalHeader("Accept-Language");

        WebResponse response = webClient.getPage(request).getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Language = [en]"));

    }

}
