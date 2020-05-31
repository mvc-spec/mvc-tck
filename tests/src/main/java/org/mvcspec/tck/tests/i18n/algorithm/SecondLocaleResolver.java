/*
 * Copyright © 2019 Christian Kaltepoth
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

package org.mvcspec.tck.tests.i18n.algorithm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.locale.LocaleResolver;
import javax.mvc.locale.LocaleResolverContext;
import java.util.Locale;

// No @Priority => 1000
@ApplicationScoped
public class SecondLocaleResolver implements LocaleResolver {

    @Inject
    private ResolverChainLogger resolverChainLogger;

    @Override
    public Locale resolveLocale(LocaleResolverContext context) {

        // log this invocation
        resolverChainLogger.log(SecondLocaleResolver.class);

        // stop resolving here
        return Locale.GERMAN;

    }

}
