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
package org.mvcspec.tck.tests.mvc.response;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
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
public class ResponseFeaturesTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(ResponseFeaturesController.class)
                .addView("Rendered view", "view.jsp")
                .build();
    }

    /**
     * Checks that using Response as the return type of a controller allows to set headers
     */
    @Test
    @SpecAssertion(section = Sections.MVC_RESPONSE, id = "response-header")
    public void responseAllowsSettingHeaders() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/response/header")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Rendered view"));
        assertThat(response.getResponseHeaderValue("X-Controller-Header"), equalTo("Foobar"));

    }

    /**
     * Checks that using Response as the return type of a controller allows to set cache control headers
     */
    @Test
    @SpecAssertion(section = Sections.MVC_RESPONSE, id = "response-header")
    public void responseAllowsSettingCacheControl() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/response/cache")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Rendered view"));
        assertThat(response.getResponseHeaderValue("Cache-Control"), containsString("private"));

    }

}
