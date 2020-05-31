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
package org.mvcspec.tck.tests.mvc.uri;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
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
