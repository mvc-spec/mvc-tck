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
package org.mvcspec.tck.tests.mvc.models.builtin;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
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
