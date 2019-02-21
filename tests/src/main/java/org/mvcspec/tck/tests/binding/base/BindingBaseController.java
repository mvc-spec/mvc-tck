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
