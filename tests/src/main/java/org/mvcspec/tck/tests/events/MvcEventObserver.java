/*
 * Copyright © 2018 Christian Kaltepoth
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
package org.mvcspec.tck.tests.events;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mvc.event.AfterControllerEvent;
import javax.mvc.event.AfterProcessViewEvent;
import javax.mvc.event.BeforeControllerEvent;
import javax.mvc.event.BeforeProcessViewEvent;
import javax.mvc.event.ControllerRedirectEvent;

@RequestScoped
public class MvcEventObserver {

    @Inject
    private TraceManager traceManager;

    public void observeBeforeControllerEvent(@Observes BeforeControllerEvent event) {
        traceManager.eventObserved(event);
    }

    public void observeAfterControllerEvent(@Observes AfterControllerEvent event) {
        traceManager.eventObserved(event);
    }

    public void observeBeforeProcessViewEvent(@Observes BeforeProcessViewEvent event) {
        traceManager.eventObserved(event);
    }

    public void observeAfterProcessViewEvent(@Observes AfterProcessViewEvent event) {
        traceManager.eventObserved(event);
    }

    public void observeControllerRedirectEvent(@Observes ControllerRedirectEvent event) {
        traceManager.eventObserved(event);
    }

}
