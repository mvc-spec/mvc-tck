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
import java.math.BigInteger;

public class BindingBigIntegerForm {

    @MvcBinding
    @FormParam("value")
    private BigInteger object;

    public BigInteger getObject() {
        return object;
    }

    public void setObject(BigInteger object) {
        this.object = object;
    }

}
