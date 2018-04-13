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

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.mvc.annotation.UriRef;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Controller
@Path("uri")
public class UriBuildingController {

    @Inject
    private Models models;

    @GET
    @Path("links")
    public String links() {
        return "links.jsp";
    }

    @GET
    @Path("simple")
    public String simple() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("param/path/{value}")
    public String pathParam(@PathParam("value") String value) {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("param/query")
    public String queryParam(@QueryParam("value") String value) {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("param/matrix")
    public String matrixParam(@MatrixParam("value") String value) {
        throw new UnsupportedOperationException();
    }

    @GET
    @UriRef("ref-id")
    @Path("uriref")
    public String ref() {
        throw new UnsupportedOperationException();
    }

}
