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
