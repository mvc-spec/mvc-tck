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
package org.mvcspec.tck.tests.mvc.instances.lifecycle;

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
public class ControllerLifecycleTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(LifecycleController.class)
                .addView("ID = '${id}'", "view.jsp")
                .build();
    }

    /**
     * Checks if the controller instance is created for each request
     */
    @Test
    @SpecAssertion(section = Sections.MVC_CONTROLLER_INSTANCES, id = "request-scope-default")
    public void controllerRequestScope() throws IOException {

        WebResponse response1 = new WebClient()
                .getPage(baseUrl.toString() + "mvc/lifecycle/request")
                .getWebResponse();
        assertThat(response1.getStatusCode(), equalTo(200));
        assertThat(response1.getContentAsString(), containsString("ID = '1'"));

        WebResponse response2 = new WebClient()
                .getPage(baseUrl.toString() + "mvc/lifecycle/request")
                .getWebResponse();
        assertThat(response2.getStatusCode(), equalTo(200));
        assertThat(response2.getContentAsString(), containsString("ID = '2'"));

        WebResponse response3 = new WebClient()
                .getPage(baseUrl.toString() + "mvc/lifecycle/request")
                .getWebResponse();
        assertThat(response3.getStatusCode(), equalTo(200));
        assertThat(response3.getContentAsString(), containsString("ID = '3'"));

    }

}
