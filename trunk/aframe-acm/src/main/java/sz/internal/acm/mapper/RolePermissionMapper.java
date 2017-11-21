package sz.internal.acm.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import sz.internal.common.base.mapper.BaseMapper;

@Component("sz.internal.acm.mapper.RolePermissionMapper")
public interface RolePermissionMapper extends BaseMapper {
    public void deleteByRoleId(@Param("role_id") String role_id) throws Exception;

}
