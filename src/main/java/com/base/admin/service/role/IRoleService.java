package com.base.admin.service.role;

import java.util.List;
import java.util.Set;

import com.base.admin.entity.role.Role;
import com.base.common.dto.generalresponse.GeneralResponse;

public interface IRoleService {
    /**
     * Get menu ids by role id
     * @param id
     * @return
     */
    GeneralResponse menuIds(Long id);

    /**
     * Update menu by role id
     * @param id
     * @param menuIds
     * @return
     */
    GeneralResponse updateMenu(Long id, Set<Long> menuIds);

    /**
     * Lấy danh sách role theo Priority của user
     * @return
     */
    List<Role> listByPriority();

    /**
     * So sánh priority của 2 user
     * @param firstUsername
     * @param secondaryUsername
     * @return
     */
    GeneralResponse compareUserPriorityByUsername(String firstUsername, String secondaryUsername);
}
