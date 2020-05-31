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
package org.mvcspec.tck.tests.security.csrf.verify;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

abstract class AbstractCsrfVerifyTest {

    private WebClient webClient;

    @ArquillianResource
    private URL baseUrl;

    @Before
    public void before() {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(false);
    }

    Page submitForm(String formType, boolean submitValidToken, String name) throws IOException {

        // load page containing the form
        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/csrf/verify/form");
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        // fill name input
        String nameInputId = "input-" + formType;
        DomElement nameInputElement = formPage.getElementById(nameInputId);
        assertNotNull("Name input element not found", nameInputElement);
        nameInputElement.setAttribute("value", name);

        // change CSRF token if the test verifies invalid token usage
        if (!submitValidToken) {
            String tokenInputId = "token-" + formType;
            DomElement tokenInputElement = formPage.getElementById(tokenInputId);
            assertNotNull("Token input element not found", tokenInputElement);
            tokenInputElement.setAttribute("value", "INVALID-TOKEN");
        }

        // submit form
        String submitButtonId = "submit-" + formType;
        DomElement submitButton = formPage.getElementById(submitButtonId);
        assertNotNull("Submit button not found", submitButton);
        return submitButton.click();


    }

}
