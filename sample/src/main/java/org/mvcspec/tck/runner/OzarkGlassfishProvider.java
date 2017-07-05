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
package org.mvcspec.tck.runner;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.mvcspec.tck.api.BaseArchiveProvider;

import java.io.File;

public class OzarkGlassfishProvider implements BaseArchiveProvider {

    @Override
    public WebArchive getBaseArchive() {

        // TODO: Can we get the versions from the pom?
        File[] dependencies = Maven.resolver()
                .resolve(
                        "javax.mvc:javax.mvc-api:1.0-edr2",
                        "org.glassfish.ozark:ozark:1.0.0-m02"
                )
                .withoutTransitivity()
                .asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(dependencies);
    }

}
