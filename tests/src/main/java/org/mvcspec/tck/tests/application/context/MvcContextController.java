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
package org.mvcspec.tck.tests.application.context;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.MvcContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;

@Controller
@Path("application/context")
public class MvcContextController {

    @Inject
    private MvcContext context;

    @Inject
    private Models models;

    @Inject
    private BeanManager beanManager;

    @GET
    public String render() {

        // Context injected
        models.put("mvcContextInjected", context != null);

        // Access other objects
        models.put("csrfAccessible", context != null && context.getCsrf() != null);
        models.put("pathAccessible", context != null && context.getBasePath() != null);
        models.put("configAccessible", context != null && context.getConfig() != null);
        models.put("encodersAccessible", context != null && context.getEncoders() != null);

        // Scope of MvcContext
        models.put("mvcContextScope", getScopeName(beanManager, MvcContext.class));

        // render result
        return "application/context/result.jsp";

    }

    private static Object getScopeName(BeanManager beanManager, Class<?> type) {
        Set<Bean<?>> beans = beanManager.getBeans(type);
        if (beans.size() == 1) {
            return beans.iterator().next().getScope().getName();
        }
        return null;
    }

}
