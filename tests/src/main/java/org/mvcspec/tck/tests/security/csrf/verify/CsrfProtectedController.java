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
package org.mvcspec.tck.tests.security.csrf.verify;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.security.CsrfProtected;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("csrf/verify")
public class CsrfProtectedController {

    @Inject
    private Models models;

    @GET
    @Path("form")
    public String renderForm() {
        return "csrf/verify/form.jsp";
    }

    @POST
    @CsrfProtected
    @Path("process-with-annotation")
    public String processFormWithAnnotation(@FormParam("name") String name) {
        models.put("message", "Hi " + name + "!");
        return "csrf/verify/success.jsp";
    }

    @POST
    @Path("process-no-annotation")
    public String processFormNoAnnotation(@FormParam("name") String name) {
        models.put("message", "Hi " + name + "!");
        return "csrf/verify/success.jsp";
    }

}
