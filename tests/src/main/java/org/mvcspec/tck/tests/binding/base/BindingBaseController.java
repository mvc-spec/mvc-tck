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
package org.mvcspec.tck.tests.binding.base;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.binding.BindingError;
import javax.mvc.binding.BindingResult;
import javax.mvc.binding.ValidationError;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("binding/base")
public class BindingBaseController {

    @Inject
    private Models models;

    @Inject
    private BindingResult bindingResult;

    @GET
    public String render() {
        return "binding/base/form.jsp";
    }

    @POST
    public String submit(@BeanParam @Valid BindingBaseForm form) {

        // errors
        if (bindingResult.isFailed()) {

            String message = bindingResult.getAllErrors().stream()
                    .map(error -> {
                        if (error instanceof BindingError) {
                            return "Binding error: " + error.getMessage();
                        }
                        if (error instanceof ValidationError) {
                            return "Validation error: " + error.getMessage();
                        }
                        throw new IllegalStateException("Unsupported type: " + error.getClass().getName());
                    })
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Binding failed but no error found"));


            models.put("message", message);

        }

        // no error
        else {
            models.put("message", "You are " + form.getAge() + " years old.");
        }

        return "binding/base/result.jsp";

    }

}
