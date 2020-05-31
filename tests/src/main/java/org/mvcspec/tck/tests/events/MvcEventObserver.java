/*
 * Copyright © 2018 Christian Kaltepoth
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
