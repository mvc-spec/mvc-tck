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

package org.mvcspec.tck.tests.viewengine.base;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ApplicationScoped
public class CustomViewEngine implements ViewEngine {

    @Inject
    private ServletContext servletContext;

    @Override
    public boolean supports(String view) {
        return view.endsWith(".custom");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {

        try {

            String result = loadTemplateByName(context.getView());

            for (String key : context.getModels()) {
                result = result.replace("%" + key + "%", Objects.toString(context.getModels().get(key)));
            }

            context.getResponseHeaders().putSingle("Content-Type", "text/html; charset=UTF-8");
            context.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new ViewEngineException("Failed to render view", e);
        }

    }


    private String loadTemplateByName(String view) throws IOException {

        String viewPath = "/WEB-INF/views/" + view;

        InputStream inputStream = servletContext.getResourceAsStream(viewPath);
        if (inputStream == null) {
            throw new IOException("Cannot find view: " + viewPath);
        }

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return new String(result.toByteArray(), StandardCharsets.UTF_8);

    }

}
