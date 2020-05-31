/*
 * Copyright © 2017 Christian Kaltepoth
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


