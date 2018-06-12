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
