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

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;

@ApplicationScoped
@Priority(ViewEngine.PRIORITY_APPLICATION + 10000)
public class NoMatchViewEngine implements ViewEngine {

    @Override
    public boolean supports(String view) {
        return false;
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {
        throw new IllegalStateException("Should never be invoked");
    }
    
}
