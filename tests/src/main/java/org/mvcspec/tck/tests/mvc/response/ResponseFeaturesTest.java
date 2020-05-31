/*
 * Copyright © 2018 Christian Kaltepoth
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
