/*
 * Copyright © 2019 Christian Kaltepoth
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
package org.mvcspec.tck.tests.viewengine.algorithm;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("viewengine/algorithm")
public class ViewEngineAlgorithmController {

    @GET
    @Path("custom-ordering")
    public String customOrdering() {
        return "foobar.custom";
    }

    @GET
    @Path("overwrite-builtin")
    public String overwriteBuiltin() {
        return "foobar.xhtml";
    }

    @GET
    @Path("relative-path")
    public String relativePath() {
        return "viewengine/algo/view.jsp";
    }

    @GET
    @Path("absolute-path")
    public String absolutePath() {
        return "/WEB-INF/views/viewengine/algo/view.jsp";
    }

}
