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
package org.mvcspec.tck.tests.mvc.models;

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
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class BuiltinEngineModelTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(BuiltinEngineModelController.class)
                .addClass(CdiModelBean.class)
                .addView("<html>" +
                                "<p>CDI-Model = [${cdiModelBean.value}]</p>\n" +
                                "<p>MVC-Model = [${modelsValue}]</p>\n" +
                                "</html>",
                        "view.jsp")
                .addView("<html>" +
                                "<p>CDI-Model = [#{cdiModelBean.value}]</p>\n" +
                                "<p>MVC-Model = [#{modelsValue}]</p>\n" +
                                "</html>",
                        "view.xhtml")
                .build();
    }

    /**
     * CDI models can be accessed from a JSP view
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.MVC_MODELS, id = "builtin-both-models"),
            @SpecAssertion(section = Sections.MVC_MODELS, id = "cdi-model-inject"),
            @SpecAssertion(section = Sections.MVC_MODELS, id = "cdi-model-el"),
            @SpecAssertion(section = Sections.MVC_VIEWS, id = "jsp-el")
    })
    public void cdiModelJsp() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/builtin/jsp")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("CDI-Model = [jsp-foo]"));

    }

    /**
     * javax.mvc.Models can be accessed from a JSP view
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.MVC_MODELS, id = "builtin-both-models"),
            @SpecAssertion(section = Sections.MVC_MODELS, id = "models-inject"),
            @SpecAssertion(section = Sections.MVC_VIEWS, id = "jsp-el")
    })
    public void mvcModelsJsp() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/builtin/jsp")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("MVC-Model = [jsp-bar]"));

    }

    /**
     * CDI models can be accessed from a Facelets view
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.MVC_MODELS, id = "builtin-both-models"),
            @SpecAssertion(section = Sections.MVC_MODELS, id = "cdi-model-inject"),
            @SpecAssertion(section = Sections.MVC_MODELS, id = "cdi-model-el")
    })
    public void cdiModelFacelets() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/builtin/facelets")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("CDI-Model = [facelets-foo]"));

    }

    /**
     * javax.mvc.Models can be accessed from a Facelets view
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.MVC_MODELS, id = "builtin-both-models"),
            @SpecAssertion(section = Sections.MVC_MODELS, id = "models-inject")
    })
    public void mvcModelsFacelets() throws IOException {

        WebResponse response = new WebClient()
                .getPage(baseUrl.toString() + "mvc/builtin/facelets")
                .getWebResponse();

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("MVC-Model = [facelets-bar]"));

    }

}
