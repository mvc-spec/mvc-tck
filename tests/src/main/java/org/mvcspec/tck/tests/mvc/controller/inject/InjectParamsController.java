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
package org.mvcspec.tck.tests.mvc.controller.inject;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Controller
@Path("inject")
public class InjectParamsController {

    @Inject
    private Models models;

    @QueryParam("fieldValue")
    private String fieldValue;

    // see getter for annotation
    private String propertyValue;

    @GET
    @Path("path/{value}")
    public String pathParam(@PathParam("value") String value) {
        models.put("value", value);
        return "view.jsp";
    }

    @GET
    @Path("query")
    public String queryParam(@QueryParam("value") String value) {
        models.put("value", value);
        return "view.jsp";
    }

    @GET
    @Path("header")
    public String headerParam(@HeaderParam("X-Value") String value) {
        models.put("value", value);
        return "view.jsp";
    }

    @GET
    @Path("field")
    public String fieldParam() {
        models.put("value", fieldValue);
        return "view.jsp";
    }

    @GET
    @Path("property")
    public String propertyParam() {
        models.put("value", propertyValue);
        return "view.jsp";
    }

    @QueryParam("propertyValue")
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}
