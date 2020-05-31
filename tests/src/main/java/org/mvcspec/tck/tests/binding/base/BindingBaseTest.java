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
package org.mvcspec.tck.tests.binding.base;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hamcrest.CoreMatchers;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class BindingBaseTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(BindingBaseController.class)
                .addClass(BindingBaseForm.class)
                .addView("binding/base/form.jsp")
                .addView("binding/base/result.jsp")
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
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "mvc-binding"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "inject-binding-result")
    })
    public void submitValidValue() throws IOException {

        HtmlPage resultPage = submitForm("20");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getElementById("message").getTextContent().trim(),
                CoreMatchers.startsWith("You are 20 years old"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "mvc-binding"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "invoke-controller"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "inject-binding-result")
    })
    public void submitValidationError() throws IOException {

        HtmlPage resultPage = submitForm("12");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getElementById("message").getTextContent().trim(),
                CoreMatchers.startsWith("Validation error: You are too young"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ANNOTATION, id = "mvc-binding"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "invoke-controller"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "inject-binding-result")
    })
    public void submitBindingError() throws IOException {

        HtmlPage resultPage = submitForm("foobar");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getElementById("message").getTextContent().trim(),
                CoreMatchers.startsWith("Binding error"));

    }

    private HtmlPage submitForm(String value) throws IOException {

        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/binding/base");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        HtmlElement ageInput = (HtmlElement) formPage.getElementById("age-input");
        ageInput.type(value);

        return formPage.getElementById("submit-btn").click();

    }

}
