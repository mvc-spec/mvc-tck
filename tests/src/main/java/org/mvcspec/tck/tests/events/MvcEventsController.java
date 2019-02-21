/*
 * Copyright © 2018 Christian Kaltepoth
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
package org.mvcspec.tck.tests.events;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("events")
public class MvcEventsController {

    @Inject
    private TraceManager traceManager;

    @Inject
    private Models models;

    @GET
    @Path("success")
    public String success() {

        traceManager.controllerExecuted();
        models.put("viewRendered", (Runnable) () -> traceManager.viewRendered());

        return "view-success.jsp";

    }

    @GET
    @Path("controller-error")
    public String controllerError() {

        traceManager.controllerExecuted();

        throw new IllegalStateException();

    }

    @GET
    @Path("view-error")
    public String viewError() {

        traceManager.controllerExecuted();
        models.put("viewRendered", (Runnable) () -> traceManager.viewRendered());
        models.put("failRender", (Runnable) () -> {
            throw new IllegalStateException();
        });

        return "view-error.jsp";

    }

    @GET
    @Path("redirect")
    public String redirect() {

        traceManager.controllerExecuted();

        return "redirect:events/success";

    }

}
