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
package org.mvcspec.tck.tests.binding.numeric;

import javax.mvc.binding.MvcBinding;
import javax.ws.rs.FormParam;

public class BindingLongForm {

    @MvcBinding
    @FormParam("value")
    private long primitive;

    @MvcBinding
    @FormParam("value")
    private Long object;

    public long getPrimitive() {
        return primitive;
    }

    public void setPrimitive(long primitive) {
        this.primitive = primitive;
    }

    public Long getObject() {
        return object;
    }

    public void setObject(Long object) {
        this.object = object;
    }
}
