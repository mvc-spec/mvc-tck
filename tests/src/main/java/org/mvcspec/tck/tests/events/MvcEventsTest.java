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
package org.mvcspec.tck.tests.events;

import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvcspec.tck.Sections;
import org.mvcspec.tck.util.Archives;
import org.mvcspec.tck.util.MvcMatchers;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class MvcEventsTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(MvcEventObserver.class)
                .addClass(MvcEventsController.class)
                .addClass(TraceManager.class)
                .addClass(TraceServlet.class)
                .addView("${viewRendered.run()}\nSome rendered view", "view-success.jsp")
                .addView("${viewRendered.run()}\n${failRender.run()}", "view-error.jsp")
                .build();
    }

    @Before
    public void before() {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(false);
    }

    /**
     * The events BeforeControllerEvent and AfterControllerEvent are fired around the invocation of a controller
     */
    @Test
    @SpecAssertion(section = Sections.EVENTS, id = "before-after-controller")
    public void aroundControllerEvents() throws IOException {

        String tid = UUID.randomUUID().toString();

        HtmlPage mvcPage = webClient.getPage(baseUrl.toString() + "mvc/events/success?tid=" + tid);
        assertThat(mvcPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(mvcPage.getWebResponse().getContentAsString(), containsString("Some rendered view"));

        TextPage tracePage = webClient.getPage(baseUrl.toString() + "trace?tid=" + tid);
        assertThat(tracePage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(tracePage.getContent().trim(),
                startsWith("BeforeControllerEvent,ControllerExecuted,AfterControllerEvent"));

    }

    /**
     * Please note that AfterControllerEvent is always fired, even if the controller fails with an exception
     */
    @Test
    @SpecAssertion(section = Sections.EVENTS, id = "after-controller-exception")
    public void afterControllerWithError() throws IOException {

        String tid = UUID.randomUUID().toString();

        HtmlPage mvcPage = webClient.getPage(baseUrl.toString() + "mvc/events/controller-error?tid=" + tid);
        assertThat(mvcPage.getWebResponse().getStatusCode(), equalTo(500));

        TextPage tracePage = webClient.getPage(baseUrl.toString() + "trace?tid=" + tid);
        assertThat(tracePage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(tracePage.getContent().trim(),
                startsWith("BeforeControllerEvent,ControllerExecuted,AfterControllerEvent"));

    }

    /**
     * The events BeforeProcessViewEvent and AfterProcessViewEvent are fired around this call
     */
    @Test
    @SpecAssertion(section = Sections.EVENTS, id = "before-after-view")
    public void aroundRenderView() throws IOException {

        String tid = UUID.randomUUID().toString();

        HtmlPage mvcPage = webClient.getPage(baseUrl.toString() + "mvc/events/success?tid=" + tid);
        assertThat(mvcPage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(mvcPage.getWebResponse().getContentAsString(), containsString("Some rendered view"));

        TextPage tracePage = webClient.getPage(baseUrl.toString() + "trace?tid=" + tid);
        assertThat(tracePage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(tracePage.getContent().trim(),
                endsWith("BeforeProcessViewEvent,ViewRendered,AfterProcessViewEvent"));

    }

    /**
     * Please note that AfterProcessViewEvent is always fired, even if the view engine fails with an exception
     */
    @Test
    @SpecAssertion(section = Sections.EVENTS, id = "after-view-exception")
    public void afterViewWithError() throws IOException {

        String tid = UUID.randomUUID().toString();

        HtmlPage mvcPage = webClient.getPage(baseUrl.toString() + "mvc/events/view-error?tid=" + tid);
        assertThat(mvcPage.getWebResponse().getStatusCode(), equalTo(500));

        TextPage tracePage = webClient.getPage(baseUrl.toString() + "trace?tid=" + tid);
        assertThat(tracePage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(tracePage.getContent().trim(),
                endsWith("BeforeProcessViewEvent,ViewRendered,AfterProcessViewEvent"));

    }


    /**
     * [...] ControllerRedirectEvent, which is fired just before the MVC implementation returns a redirect status code
     * Please note that this event MUST be fired after AfterControllerEvent
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.EVENTS, id = "redirect-event"),
            @SpecAssertion(section = Sections.EVENTS, id = "redirect-after-controller-event")
    })
    public void redirectEvent() throws IOException {

        String tid = UUID.randomUUID().toString();

        HtmlPage mvcPage = webClient.getPage(baseUrl.toString() + "mvc/events/redirect?tid=" + tid);
        assertThat(mvcPage.getWebResponse().getStatusCode(), MvcMatchers.isRedirectStatus());

        TextPage tracePage = webClient.getPage(baseUrl.toString() + "trace?tid=" + tid);
        assertThat(tracePage.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(tracePage.getContent().trim(),
                endsWith("ControllerExecuted,AfterControllerEvent,ControllerRedirectEvent"));

    }

}
