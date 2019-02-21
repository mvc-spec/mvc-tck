/*
 * Copyright © 2017 Christian Kaltepoth
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
package org.mvcspec.tck.util;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.mvcspec.tck.api.BaseArchiveProvider;
import org.mvcspec.tck.common.DefaultApplication;

import javax.ws.rs.core.Application;

public class Archives {

    public static WebArchiveBuilder getBaseArchive() {
        return new WebArchiveBuilder(createBaseWebArchiveFromProvider());
    }

    public static WebArchiveBuilder getMvcArchive() {
        return getMvcArchive(DefaultApplication.class);
    }

    public static WebArchiveBuilder getMvcArchive(Class<? extends Application> applicationClass) {
        return getBaseArchive()
                .withDefaultWebXml()
                .withDefaultFaceConfig()
                .addClasses(applicationClass)
                .addBeansXml("all");
    }

    private static WebArchive createBaseWebArchiveFromProvider() {

        String implPropName = BaseArchiveProvider.class.getName();

        String implName = System.getProperty(implPropName, null);
        if (implName == null || implName.trim().isEmpty()) {
            throw new IllegalStateException("Please set system property: " + implPropName);
        }

        Class<BaseArchiveProvider> implClass = Reflection.loadClass(implName);

        BaseArchiveProvider provider = Reflection.createInstance(implClass);

        try {

            return provider.getBaseArchive();

        } catch (RuntimeException e) {
            // make sure that errors are logged to the console when running the tests
            e.printStackTrace();
            throw e;
        }

    }

}


