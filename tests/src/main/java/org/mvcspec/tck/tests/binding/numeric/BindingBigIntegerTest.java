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
package org.mvcspec.tck.tests.binding.numeric;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class BindingBigIntegerTest extends AbstractNumericTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(ForceGermanLocaleResolver.class)
                .addClass(BindingBigIntegerController.class)
                .addClass(BindingBigIntegerForm.class)
                .addView("binding/numeric/form.jsp")
                .addView("binding/numeric/result.jsp")
                .build();
    }


    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_NUMERIC_TYPE, id = "convert-numeric")
    })
    public void submitValidBigInteger() throws IOException {

        HtmlPage resultPage = submitForm("binding/numeric", "1.234");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), containsString("Object: [1234]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_NUMERIC_TYPE, id = "convert-empty-numeric")
    })
    public void submitEmptyBigInteger() throws IOException {

        HtmlPage resultPage = submitForm("binding/numeric", "");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), containsString("Object: [null]"));

    }

}
