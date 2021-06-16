/*
 * Copyright 2021 Haulmont.
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

package io.jmix.multitenancyui.screen.tenant;

import io.jmix.core.security.UserRepository;
import io.jmix.multitenancy.entity.Tenant;
import io.jmix.ui.component.SuggestionField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@UiController("mten_Tenant.edit")
@UiDescriptor("tenant-edit.xml")
@EditedEntityContainer("tenantDc")
public class TenantEdit extends StandardEditor<Tenant> {

    @Autowired
    protected SuggestionField<String> adminUsernameField;

    @Autowired
    protected UserRepository userRepository;

    @Subscribe
    public void onInit(InitEvent event) {
        adminUsernameField.setSearchExecutor((searchString, searchParams) -> {
            List<? extends UserDetails> users = userRepository.getByUsernameLike(searchString);
            return users.stream()
                    .map(UserDetails::getUsername)
                    .collect(Collectors.toList());
        });
    }


}