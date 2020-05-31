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
package org.mvcspec.tck.tests.mvc.controller.mediatype;

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
public class MediaTypeTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(MediaTypeController.class)
                .addView("Some rendered view", "view.jsp")
                .build();
    }

    /**
     * Moreover, the default media type for a response is assumed to be text/html, but otherwise can be declared
     * using @Produces just like in JAX-RS
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "default-mediatype")
    public void defaultMediaType() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/mediatype/default")
                .getWebResponse();

        // default content-type of text/html
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentType(), equalTo("text/html"));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }

    /**
     * Moreover, the default media type for a response is assumed to be text/html, but otherwise can be declared
     * using @Produces just like in JAX-RS
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "default-mediatype")
    public void customMediaType() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/mediatype/custom")
                .getWebResponse();

        // custom content-type of text/plain
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentType(), equalTo("text/plain"));
        assertThat(response.getContentAsString(), containsString("Some rendered view"));

    }

}
