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
package org.mvcspec.tck.tests.binding.bool;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("binding/bool")
public class BindingBooleanController {

    @Inject
    private Models models;

    @GET
    public String render() {
        return "binding/bool/form.jsp";
    }

    @POST
    public String submit(@BeanParam BindingBooleanForm form) {

        models.put("primitiveResult", "[" + form.getBooleanPrimitive() + "]");
        models.put("wrapperResult", "[" + form.getBooleanWrapper() + "]");

        return "binding/bool/result.jsp";

    }

}
