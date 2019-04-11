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
package org.mvcspec.tck.tests.application.inheritance;

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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class InheritanceTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(InheritanceController.class)
                .addClass(InheritanceBaseClass.class)
                .addClass(InheritanceBaseInterface.class)
                .addView("application/inheritance/controller.jsp")
                .addView("application/inheritance/superclass.jsp")
                .addView("application/inheritance/interface.jsp")
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
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "inheritance")
    })
    public void annotationsOnlyOnControllerMethod() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() +
                "mvc/application/inheritance/annotations-only-on-controller-method"
        );

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                containsString("Annotations on controller win!")
        );

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "inheritance")
    })
    public void annotationsOnlyOnSuperMethod() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() +
                "mvc/application/inheritance/annotations-only-on-super-method"
        );

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                containsString("Annotations on superclass win!")
        );

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "inheritance")
    })
    public void annotationsOnControllerAndSuperMethod() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() +
                "mvc/application/inheritance/annotations-on-controller-and-super-method"
        );

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                containsString("Annotations on controller win!")
        );

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "inheritance")
    })
    public void annotationsOnlyOnInterfaceMethod() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() +
                "mvc/application/inheritance/annotations-only-on-interface-method"
        );

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                containsString("Annotations on interface win!")
        );

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "inheritance")
    })
    public void annotationsOnControllerAndInterfaceMethod() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() +
                "mvc/application/inheritance/annotations-on-controller-and-interface-method"
        );

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                containsString("Annotations on controller win!")
        );

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "inheritance"),
            @SpecAssertion(section = Sections.APPLICATION_INHERITANCE, id = "class-vs-iface")
    })
    public void annotationsOnSuperClassAndInterfaceMethod() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() +
                "mvc/application/inheritance/annotations-on-superclass-and-interface-method"
        );

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getWebResponse().getContentAsString(),
                containsString("Annotations on superclass win!")
        );

    }

}
