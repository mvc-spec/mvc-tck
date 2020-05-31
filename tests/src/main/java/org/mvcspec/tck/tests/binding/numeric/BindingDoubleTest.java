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
package org.mvcspec.tck.tests.binding.numeric;

import com.gargoylesoftware.htmlunit.Page;
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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class BindingDoubleTest extends AbstractNumericTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(ForceGermanLocaleResolver.class)
                .addClass(BindingDoubleController.class)
                .addClass(BindingDoubleForm.class)
                .addView("binding/numeric/form.jsp")
                .addView("binding/numeric/result.jsp")
                .build();
    }


    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_NUMERIC_TYPE, id = "convert-numeric")
    })
    public void submitValidDouble() throws IOException {

        Page resultPage = submitForm("binding/numeric", "1.234,56");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), allOf(
                containsString("Primitive: [1234.56]"),
                containsString("Object: [1234.56]")
        ));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_NUMERIC_TYPE, id = "convert-empty-numeric")
    })
    public void submitEmptyDouble() throws IOException {

        Page resultPage = submitForm("binding/numeric", "");

        assertThat(resultPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(resultPage.getWebResponse().getContentAsString(), allOf(
                containsString("Primitive: [0.0]"),
                containsString("Object: [null]")
        ));

    }

}
