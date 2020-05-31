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
package org.mvcspec.tck.tests.binding.types;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.binding.MvcBinding;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;

@Controller
@Path("binding/types")
public class BindingTypesController {

    @Inject
    private Models models;

    @GET
    @Path("/path/{path}")
    public String path(@MvcBinding @PathParam("path") BigDecimal value) {
        models.put("value", value);
        return "binding/types/view.jsp";
    }

    @GET
    @Path("/query")
    public String query(@MvcBinding @QueryParam("value") BigDecimal value) {
        models.put("value", value);
        return "binding/types/view.jsp";
    }

    @GET
    @Path("/header")
    public String header(@MvcBinding @HeaderParam("X-Header-Value") BigDecimal value) {
        models.put("value", value);
        return "binding/types/view.jsp";
    }

    @POST
    @Path("/form")
    public String form(@MvcBinding @FormParam("value") BigDecimal value) {
        models.put("value", value);
        return "binding/types/view.jsp";
    }

    @GET
    @Path("/matrix")
    public String matrix(@MvcBinding @MatrixParam("value") BigDecimal value) {
        models.put("value", value);
        return "binding/types/view.jsp";
    }

    @GET
    @Path("/cookie")
    public String cookie(@MvcBinding @CookieParam("X-COOKIE-VALUE") BigDecimal value) {
        models.put("value", value);
        return "binding/types/view.jsp";
    }

}
