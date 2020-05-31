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
package org.mvcspec.tck.tests.application.app;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.webapp31.WebAppDescriptor;
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
public class MvcAppWebXmlTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        // custom web.xml for configuration according to 2.3.2 of the JAX-RS 2.1 spec
        WebAppDescriptor descriptor = Descriptors.create(WebAppDescriptor.class)
                .addDefaultNamespaces()
                .version("3.1")
                .createServlet()
                .servletName(MvcAppWebXmlApplication.class.getName())
                .up()
                .createServletMapping()
                .servletName(MvcAppWebXmlApplication.class.getName())
                .urlPattern("/web-xml-app-path/*")
                .up();

        return Archives.getMvcArchive(MvcAppWebXmlApplication.class)
                .withWebXml(descriptor)
                .addClass(MvcAppWebXmlController.class)
                .addView("1 + 2 = ${1+2}", "view.jsp")
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
            @SpecAssertion(section = Sections.APPLICATION_MVC_APPS, id = "application-class"),
            @SpecAssertion(section = Sections.APPLICATION_MVC_APPS, id = "url-space")
    })
    public void testUrlSpaceViaAnnotation() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "web-xml-app-path/application/app/web-xml");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), containsString("1 + 2 = 3"));

    }

}
