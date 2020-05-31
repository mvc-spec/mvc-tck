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
