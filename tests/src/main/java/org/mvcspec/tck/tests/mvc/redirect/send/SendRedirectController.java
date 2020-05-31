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
package org.mvcspec.tck.tests.mvc.redirect.send;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Controller
@Path("send-redirect")
public class SendRedirectController {

    @GET
    @Path("target")
    public String target() {
        return "target.jsp";
    }

    @GET
    @Path("response")
    public Response header() {
        return Response.seeOther(URI.create("send-redirect/target")).build();
    }

    @GET
    @Path("prefix")
    public String prefix() {
        return "redirect:send-redirect/target";
    }

}
