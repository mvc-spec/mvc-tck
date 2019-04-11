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
package org.mvcspec.tck.tests.application.inheritance;

import javax.mvc.Controller;
import javax.mvc.View;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("application/inheritance")
public class InheritanceController extends InheritanceBaseClass implements InheritanceBaseInterface {

    @Override
    @GET
    @Path("annotations-only-on-controller-method")
    @View("application/inheritance/controller.jsp")
    public void annotationsOnlyOnControllerMethod() {
        // nothing
    }

    @Override
    public void annotationsOnlyOnSuperMethod() {
        // nothing
    }

    @Override
    @GET
    @Path("annotations-on-controller-and-super-method")
    @View("application/inheritance/controller.jsp")
    public void annotationsOnControllerAndSuperMethod() {
        // nothing
    }

    @Override
    public void annotationsOnlyOnInterfaceMethod() {
        // nothing
    }

    @Override
    @GET
    @Path("annotations-on-controller-and-interface-method")
    @View("application/inheritance/controller.jsp")
    public void annotationsOnControllerAndInterfaceMethod() {
        // nothing
    }

    @Override
    public void annotationsOnSuperClassAndInterfaceMethod() {
        // nothing
    }

}
