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
