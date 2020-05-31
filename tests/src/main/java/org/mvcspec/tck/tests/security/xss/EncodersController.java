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
package org.mvcspec.tck.tests.security.xss;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.MvcContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("xss")
public class EncodersController {

    @Inject
    private MvcContext mvcContext;

    @Inject
    private Models models;

    @GET
    @Path("access")
    public String access() {
        models.put("injectedEncoders", mvcContext.getEncoders());
        return "access.jsp";
    }

    @GET
    @Path("encode-html")
    public String encodeHtml() {
        models.put("value", "&<>\"'"); // See API docs of Encoders#html()
        return "encode-html.jsp";
    }

    @GET
    @Path("encode-js")
    public String encodeJs() {
        models.put("value", "\b\t\n\f\r/\\\"&'");  // See API docs of Encoders#js()
        return "encode-js.jsp";
    }

}
