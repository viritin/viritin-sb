/*
 * Copyright 2024 Viritin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.virit.sb.example;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.PageTitle;

/**
 * Due to nasty (although intention has been helpful) limitations in 
 * AppShellConfigurator interface, must be declared separately as opposed to
 * examples by start.vaadin.com. Otherwise the app class on the 
 * DevModeDemoApplication don't start. This is probably a better habit though...
 */
@PageTitle("TESTING")
public class VaadinPageConfig implements AppShellConfigurator{
    
}
