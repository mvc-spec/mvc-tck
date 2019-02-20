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
package org.mvcspec.tck.tests.binding.numeric;

import javax.mvc.binding.MvcBinding;
import javax.ws.rs.FormParam;

public class BindingDoubleForm {

    @MvcBinding
    @FormParam("value")
    private double primitive;

    @MvcBinding
    @FormParam("value")
    private Double object;

    public double getPrimitive() {
        return primitive;
    }

    public void setPrimitive(double primitive) {
        this.primitive = primitive;
    }

    public Double getObject() {
        return object;
    }

    public void setObject(Double object) {
        this.object = object;
    }
    
}
