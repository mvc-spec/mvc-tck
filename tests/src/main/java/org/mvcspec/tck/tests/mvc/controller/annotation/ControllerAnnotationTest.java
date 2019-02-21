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
package org.mvcspec.tck.tests.mvc.controller.annotation;

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
public class ControllerAnnotationTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(MethodController.class)
                .addClass(ClassController.class)
                .addClass(HybridController.class)
                .addView("<h1>Rendered view #1</h1>", "view1.jsp")
                .addView("<h1>Rendered view #2</h1>", "view2.jsp")
                .build();
    }

    /**
     * An MVC controller is a JAX-RS resource method decorated by @Controller
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "ctrl-method")
    public void controllerMethod() throws IOException {

        // view rendered because method is a controller
        WebResponse response = new WebClient().getPage(baseUrl.toString() + "mvc/method/view1").getWebResponse();
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentAsString(), containsString("Rendered view #1"));

    }

    /*
     * If this annotation is applied to a class, then all resource methods in it are regarded as controllers
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "ctrl-class")
    public void controllerClass() throws IOException {

        WebClient client = new WebClient();

        // view rendered because method is a controller
        WebResponse response1 = client.getPage(baseUrl.toString() + "mvc/class/view1").getWebResponse();
        assertThat(response1.getStatusCode(), equalTo(200));
        assertThat(response1.getContentAsString(), containsString("Rendered view #1"));

        // view rendered because method is a controller
        WebResponse response2 = client.getPage(baseUrl.toString() + "mvc/class/view2").getWebResponse();
        assertThat(response2.getStatusCode(), equalTo(200));
        assertThat(response2.getContentAsString(), containsString("Rendered view #2"));

    }


    /*
     * Using the @Controller annotation on a subset of methods defines a hybrid class in which certain
     * methods are controllers and others are traditional JAX-RS resource methods.
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLERS, id = "ctrl-hybrid")
    public void controllerHybrid() throws IOException {

        WebClient client = new WebClient();

        // view rendered because method is a controller
        WebResponse response1 = client.getPage(baseUrl.toString() + "mvc/hybrid/view1").getWebResponse();
        assertThat(response1.getStatusCode(), equalTo(200));
        assertThat(response1.getContentAsString(), containsString("Rendered view #1"));

        // view not rendered because method is not a controller
        WebResponse response2 = client.getPage(baseUrl.toString() + "mvc/hybrid/view2").getWebResponse();
        assertThat(response2.getStatusCode(), equalTo(200));
        assertThat(response2.getContentAsString(), containsString("view2.jsp"));

    }

}
