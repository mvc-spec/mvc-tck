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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.event.MvcEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TraceManager {

    private final Map<String, TracedRequest> tracedRequests = new LinkedHashMap<>();

    @Inject
    private HttpServletRequest request;

    void eventObserved(MvcEvent event) {

        Class<?> eventType = Arrays.stream(event.getClass().getInterfaces())
                .filter(type -> type.getPackage().getName().startsWith("javax.mvc"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot identify event type"));

        logInternal(eventType.getSimpleName());

    }

    void controllerExecuted() {
        logInternal("ControllerExecuted");
    }

    void viewRendered() {
        logInternal("ViewRendered");
    }

    TracedRequest getTracedRequest(String id) {
        return tracedRequests.get(id);
    }

    private void logInternal(String event) {

        String tid = request.getParameter("tid");
        if (tid != null) {
            tracedRequests.computeIfAbsent(tid, _tid -> new TracedRequest()).add(event);
        }

    }


    public static class TracedRequest {

        private final List<String> events = new ArrayList<>();

        void add(String message) {
            this.events.add(message);
        }

        public List<String> getEvents() {
            return events;
        }

    }

}
