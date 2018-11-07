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

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/trace")
public class TraceServlet extends HttpServlet {

    private static final long serialVersionUID = 4075821198643825653L;

    @Inject
    private TraceManager traceManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("tid");
        if (id == null) {
            resp.sendError(400);
            return;
        }

        TraceManager.TracedRequest tracedRequest = traceManager.getTracedRequest(id);
        if (tracedRequest == null) {
            resp.sendError(400);
            return;
        }

        resp.getWriter().write(String.join(",", tracedRequest.getEvents()));
        resp.flushBuffer();

    }


}
