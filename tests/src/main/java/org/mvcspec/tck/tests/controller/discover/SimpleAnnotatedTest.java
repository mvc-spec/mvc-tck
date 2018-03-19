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
package org.mvcspec.tck.tests.controller.discover;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.util.Archives;

import java.io.IOException;
import java.net.URL;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class SimpleAnnotatedTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(SimpleAnnotatedController.class)
                .addView("<h1>Hello world!</h1>", "simple.jsp")
                .build();
    }

    @Test
    @SpecAssertion(section = "list-of-assertions", id = "Controller_Annotation")
    public void shouldDoStuff() throws IOException {

        Page page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/simple");

        Assert.assertThat(
                page.getWebResponse().getContentAsString(),
                CoreMatchers.containsString("Hello world!")
        );

    }
}
