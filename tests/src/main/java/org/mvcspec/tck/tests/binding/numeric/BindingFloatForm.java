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
package org.mvcspec.tck.tests.binding.numeric;

import javax.mvc.binding.MvcBinding;
import javax.ws.rs.FormParam;

public class BindingFloatForm {

    @MvcBinding
    @FormParam("value")
    private float primitive;

    @MvcBinding
    @FormParam("value")
    private Float object;

    public float getPrimitive() {
        return primitive;
    }

    public void setPrimitive(float primitive) {
        this.primitive = primitive;
    }

    public Float getObject() {
        return object;
    }

    public void setObject(Float object) {
        this.object = object;
    }
    
}
