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
package org.mvcspec.tck.tests.mvc.response;

import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

@Controller
@Path("response")
public class ResponseFeaturesController {

    @GET
    @Path("header")
    public Response header() {
        return Response.ok("view.jsp")
                .header("X-Controller-Header", "Foobar")
                .build();
    }

    @GET
    @Path("cache")
    public Response cache() {

        CacheControl cacheControl = new CacheControl();
        cacheControl.setPrivate(true);

        return Response.ok("view.jsp")
                .cacheControl(cacheControl)
                .build();

    }

}
