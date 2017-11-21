package sz.internal.acm.service;

import org.springframework.stereotype.Component;
import sz.internal.acm.mapper.RolePermissionMapper;
import sz.internal.common.base.service.BaseService;

import javax.annotation.Resource;

@Component
public class RolePermissionService extends BaseService {
    @Resource(name = "sz.internal.acm.mapper.RolePermissionMapper")
    public void setMapper(RolePermissionMapper mapper) {
        super.setMapper(mapper);
    }

    public void deleteByRoleId(String role_id) throws Exception {
        RolePermissionMapper rolePermissionMapper = (RolePermissionMapper) getMapper();
        rolePermissionMapper.deleteByRoleId(role_id);
    }
}
