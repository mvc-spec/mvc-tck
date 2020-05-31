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
package org.mvcspec.tck.tests.mvc.controller.returntype;

import javax.mvc.Controller;
import javax.mvc.View;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Controller
@Path("return")
public class ReturnTypesController {

    @GET
    @Path("string")
    public String string() {
        return "view.jsp";
    }

    @GET
    @View("view.jsp")
    @Path("void-with-view")
    public void voidWithView() {
        // void return type but @View annotation
    }

    @GET
    @Path("void-no-view")
    public void voidWithoutView() {
        // void return type AND no @View annotation
    }

    @GET
    @Path("response-string")
    public Response responseWithString() {
        return Response.ok("view.jsp").build();
    }

    @GET
    @View("view.jsp")
    @Path("response-null")
    public Response responseWithNull() {
        return Response.ok(null).build();
    }


    @GET
    @View("view.jsp")
    @Path("string-null")
    public Response stringWithNull() {
        return null;
    }

}

