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
package org.mvcspec.tck.tests.mvc.controller.inject;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.UrlUtils;
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
public class InjectParamsTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(InjectParamsController.class)
                .addView("Value = '${value}'", "view.jsp")
                .build();
    }

    /**
     * Path parameters are injected correctly
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "inject-param-types")
    public void injectPathParam() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/inject/path/foobar")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Value = 'foobar'"));

    }

    /**
     * Query parameters are injected correctly
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "inject-param-types")
    public void injectQueryParam() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/inject/query?value=FOOBAR")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Value = 'FOOBAR'"));

    }

    /**
     * Header parameters are injected correctly
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "inject-param-types")
    public void injectHeaderParam() throws IOException {

        WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(baseUrl.toString() + "mvc/inject/header"));
        request.setAdditionalHeader("X-Value", "FooBar");

        WebResponse response = new WebClient()
                .getPage(request)
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Value = 'FooBar'"));

    }


    /**
     * Inject parameter into field
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "inject-field-props")
    public void injectFieldParam() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/inject/field?fieldValue=foo-bar")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Value = 'foo-bar'"));

    }

    /**
     * Inject parameter into property
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "inject-field-props")
    public void injectPropertyParam() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/inject/property?propertyValue=foo-BAR")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Value = 'foo-BAR'"));

    }

}
