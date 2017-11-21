package sz.internal.acm.service;

import org.springframework.stereotype.Component;
import sz.internal.acm.mapper.UserRoleMapper;
import sz.internal.common.base.service.BaseService;

import javax.annotation.Resource;

@Component
public class UserRoleService extends BaseService {
    @Resource(name = "sz.internal.acm.mapper.UserRoleMapper")
    public void setMapper(UserRoleMapper mapper) {
        super.setMapper(mapper);
    }

    public void deleteByUserId(int user_id, String app_id) throws Exception {
        UserRoleMapper mapper = (UserRoleMapper) getMapper();
        mapper.deleteByUserId(user_id, app_id);
    }
}
