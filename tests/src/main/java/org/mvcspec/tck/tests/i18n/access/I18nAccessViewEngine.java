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
