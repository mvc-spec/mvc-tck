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

package org.mvcspec.tck.tests.i18n.access;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.MvcContext;
import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class I18nAccessViewEngine implements ViewEngine {

    @Inject
    private MvcContext mvcContext;

    @Override
    public boolean supports(String view) {
        return "i18n-access".equals(view);
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {

        try {

            String response = String.format("ViewEngine = [%s]", mvcContext.getLocale().getLanguage());

            context.getResponseHeaders().putSingle("Content-Type", "text/html; charset=UTF-8");
            context.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new ViewEngineException(e);
        }
    }

}
