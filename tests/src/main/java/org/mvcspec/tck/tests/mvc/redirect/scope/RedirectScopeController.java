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
package org.mvcspec.tck.tests.mvc.redirect.scope;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("scope")
public class RedirectScopeController {

    @Inject
    private RequestScopeBean requestScopedBean;

    @Inject
    private SessionScopeBean sessionScopeBean;

    @Inject
    private RedirectScopeBean redirectScopeBean;

    @GET
    @Path("write-redirect-read")
    public String start() {

        requestScopedBean.setValue("foo");
        sessionScopeBean.setValue("bar");
        redirectScopeBean.setValue("foobar");

        return "redirect:scope/read";

    }

    @GET
    @Path("read")
    public String target() {
        return "read.jsp";
    }

}
