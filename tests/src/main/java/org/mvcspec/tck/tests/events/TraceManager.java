/*
 * Copyright © 2017 Ivar Grimstad (ivar.grimstad@gmail.com)
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
