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
package org.mvcspec.tck.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class WebArchiveBuilder {

    private WebArchive archive;

    public WebArchiveBuilder() {
        this(ShrinkWrap.create(WebArchive.class));
    }

    public WebArchiveBuilder(WebArchive archive) {
        this.archive = archive;
    }

    public WebArchiveBuilder addClass(Class<?> clazz) {
        archive.addClass(clazz);
        return this;
    }

    public WebArchiveBuilder addClasses(Class<?>... classes) {
        archive.addClasses(classes);
        return this;
    }


    public WebArchiveBuilder addView(Asset asset, String name) {
        archive.addAsWebInfResource(asset, "views/" + name);
        return this;
    }

    public WebArchiveBuilder addView(String value, String name) {
        return this.addView(new StringAsset(value), name);
    }

    public WebArchiveBuilder addEmptyBeansXml() {
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return this;
    }

    public WebArchive build() {
        return archive;
    }
}
