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
package org.mvcspec.tck.tests.mvc.uri;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class UriBuildingTest {

    @ArquillianResource
    private URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(UriBuildingController.class)
                .addView("<html>" +
                                "<span id='simple'>${mvc.uri('UriBuildingController#simple')}</span>" +
                                "<span id='path-param'>${mvc.uri('UriBuildingController#pathParam', {'value': 'foo'})}</span>" +
                                "<span id='query-param'>${mvc.uri('UriBuildingController#queryParam', {'value': 'bar'})}</span>" +
                                "<span id='matrix-param'>${mvc.uri('UriBuildingController#matrixParam', {'value': 'foobar'})}</span>" +
                                "<span id='path-encoding'>${mvc.uri('UriBuildingController#pathParam', {'value': 'foo bar'})}</span>" +
                                "<span id='query-encoding'>${mvc.uri('UriBuildingController#queryParam', {'value': 'foo bar'})}</span>" +
                                "<span id='matrix-encoding'>${mvc.uri('UriBuildingController#matrixParam', {'value': 'foo bar'})}</span>" +
                                "<span id='uri-ref'>${mvc.uri('ref-id')}</span>" +
                                "</html>",
                        "links.jsp")
                .addView("Rendered view", "view.jsp")
                .build();
    }

    /**
     * Create simple URI via EL expression
     */
    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "el-access"),
            @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "class-method-name")
    })
    public void simpleUriViaEl() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("simple").getTextContent(),
                endsWith("/mvc/uri/simple")
        );

    }

    /**
     * Set path parameter via map
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "tck-id-map")
    public void mapPathParam() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("path-param").getTextContent(),
                endsWith("/mvc/uri/param/path/foo")
        );

    }

    /**
     * Set query parameter via map
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "tck-id-map")
    public void mapQueryParam() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("query-param").getTextContent(),
                endsWith("/mvc/uri/param/query?value=bar")
        );

    }

    /**
     * Set matrix parameter via map
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "tck-id-map")
    public void mapMatrixParam() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("matrix-param").getTextContent(),
                endsWith("/mvc/uri/param/matrix;value=foobar")
        );

    }

    /**
     * Use correct encoding for path parameters
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "uri-encoding")
    public void encodingPathParam() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("path-encoding").getTextContent(),
                endsWith("/mvc/uri/param/path/foo%20bar")
        );

    }

    /**
     * Use correct encoding for query parameters
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "uri-encoding")
    public void encodingQueryParam() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("query-encoding").getTextContent(),
                endsWith("/mvc/uri/param/query?value=foo+bar")
        );

    }

    /**
     * Use correct encoding for matrix parameters
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "uri-encoding")
    public void encodingMatrixParam() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("matrix-encoding").getTextContent(),
                endsWith("/mvc/uri/param/matrix;value=foo%20bar")
        );

    }

    /**
     * Can reference controller method via @UriRef
     */
    @Test
    @SpecAssertion(section = Sections.MVC_BUILDING_URIS, id = "uri-ref")
    public void supportsUriRef() throws IOException {

        HtmlPage page = new WebClient()
                .getPage(baseUrl.toString() + "mvc/uri/links");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(
                page.getElementById("uri-ref").getTextContent(),
                endsWith("/mvc/uri/uriref")
        );

    }

}
