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
package org.mvcspec.tck.tests.binding.bool;

import javax.mvc.binding.MvcBinding;
import javax.ws.rs.FormParam;

public class BindingBooleanForm {

    @MvcBinding
    @FormParam("value")
    private boolean booleanPrimitive;

    @MvcBinding
    @FormParam("value")
    private Boolean booleanWrapper;

    public boolean getBooleanPrimitive() {
        return booleanPrimitive;
    }

    public void setBooleanPrimitive(boolean booleanPrimitive) {
        this.booleanPrimitive = booleanPrimitive;
    }

    public Boolean getBooleanWrapper() {
        return booleanWrapper;
    }

    public void setBooleanWrapper(Boolean booleanWrapper) {
        this.booleanWrapper = booleanWrapper;
    }
}
