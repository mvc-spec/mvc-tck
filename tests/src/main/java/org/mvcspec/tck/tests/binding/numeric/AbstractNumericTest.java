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
package org.mvcspec.tck.tests.binding.numeric;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public abstract class AbstractNumericTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Before
    public void before() {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(false);
    }

    HtmlPage submitForm(String path, String value) throws IOException {

        HtmlPage formPage = webClient.getPage(baseUrl.toString() + "mvc/" + path);
        assertThat(formPage.getWebResponse().getStatusCode(), equalTo(200));

        HtmlElement ageInput = (HtmlElement) formPage.getElementById("input");
        ageInput.type(value);

        return formPage.getElementById("submit").click();

    }

}
