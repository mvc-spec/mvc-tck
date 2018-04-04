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
package org.mvcspec.tck.tests.mvc.controller.returntype;

import javax.mvc.annotation.Controller;
import javax.mvc.annotation.View;
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

