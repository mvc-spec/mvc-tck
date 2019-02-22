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
package org.mvcspec.tck.tests.application.context;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class MvcContextTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(MvcContextController.class)
                .addView("application/context/result.jsp")
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
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "injection"),
    })
    public void testMvcContextInjected() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(),
                containsString("MvcContext injected = [true]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "injection"),
    })
    public void testMvcContextAccessInformation() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), allOf(
                containsString("CSRF accessible = [true]"),
                containsString("Path accessible = [true]"),
                containsString("Config accessible = [true]"),
                containsString("Encoders accessible = [true]")
        ));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "request-scope"),
    })
    public void testMvcContextScope() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(),
                containsString("MvcContext scope = [javax.enterprise.context.RequestScoped]"));

    }


    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "el-access"),
    })
    public void testMvcContextAccessViaEl() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(),
                containsString("MvcContext via EL = [true]"));

    }

}
