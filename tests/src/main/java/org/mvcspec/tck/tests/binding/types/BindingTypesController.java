/*
 * Copyright © 2019 Christian Kaltepoth
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
 *
 * SPDX-License-Identifier: Apache-2.0
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
