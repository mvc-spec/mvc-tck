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
package org.mvcspec.tck.tests.mvc.models;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("builtin")
public class BuiltinEngineModelController {

    @Inject
    private Models models;

    @Inject
    private CdiModelBean cdiModelBean;

    @GET
    @Path("jsp")
    public String jsp() {
        cdiModelBean.setValue("jsp-foo");
        models.put("modelsValue", "jsp-bar");
        return "view.jsp";
    }

    @GET
    @Path("facelets")
    public String facelets() {
        cdiModelBean.setValue("facelets-foo");
        models.put("modelsValue", "facelets-bar");
        return "view.xhtml";
    }

}
