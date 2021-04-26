/*
 * Copyright 2010-2011 Steve Chaloner
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
package controllers;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import controllers.deadbolt.Unrestricted;
import play.mvc.Controller;
import play.mvc.With;

@With(Deadbolt.class)
@Restrictions(@Restrict("uber-secure"))
public class RestrictedController extends Controller
{
    public static void index()
    {
        render("unauthorised.html");
    }

    @Restrict("yet-another-restriction")
    public static void alsoRestricted()
    {
        render("unauthorised.html");
    }

    @Unrestricted
    public static void unrestrictedMethod()
    {
        render("authorised.html");
    }

    @Unrestricted
    @Restrict("yet-another-restriction")
    public static void restrictedAndUnrestrictedMethod()
    {
        render("authorised.html");
    }
}