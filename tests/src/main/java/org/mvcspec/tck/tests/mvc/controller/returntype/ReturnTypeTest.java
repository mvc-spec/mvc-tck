/*
 * Copyright © 2018 Christian Kaltepoth
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
package org.mvcspec.tck.tests.mvc.controller.returntype;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class ReturnTypeTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(ReturnTypesController.class)
                .addView("Some rendered view", "view.jsp")
                .build();
    }

    /**
     * - In particular, a return type of String is interpreted as a view path rather than text content
     * - A string returned is interpreted as a view path
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "return-string"),
            @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "return-string2"),
    })
    public void stringReturnType() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/return/string")
                .getWebResponse();

        // string result just renders the corresponding view
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }

    /**
     * A controller method that returns void is REQUIRED to be decorated by @View
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "return-void")
    public void voidWithViewAnnotation() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/return/void-with-view")
                .getWebResponse();

        // void with @View renders the view referenced in the annotation
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }

    /**
     * A controller method that returns void is REQUIRED to be decorated by @View
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "return-void")
    public void voidWithoutViewAnnotation() throws IOException {

        // prepare client not fail for 500 status code
        WebClient client = new WebClient();
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);

        // void without @View must not work
        Page page = client.getPage(baseUrl.toString() + "mvc/return/void-no-view");
        assertThat("Expected status code != 204", page, notNullValue());
        assertThat(page.getWebResponse().getStatusCode(), equalTo(500));

    }

    /**
     * A JAX-RS Response whose entity's type is one of the above
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "return-response")
    public void responseWithStringEntity() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/return/response-string")
                .getWebResponse();

        // string entity
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }

    /**
     * A JAX-RS Response whose entity's type is one of the above
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "return-response")
    public void responseWithNullEntity() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/return/response-null")
                .getWebResponse();

        // null entity
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }


    /**
     * The default view MUST be used only when such a non-void controller method returns a null value
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "non-null-viewable")
    public void stringWithNullResult() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/return/string-null")
                .getWebResponse();

        // return null from string method uses @View
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }

}
