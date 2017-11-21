package sz.internal.acm.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import sz.internal.acm.to.PermissionTO;
import sz.internal.common.base.mapper.BaseMapper;

import java.util.List;

@Component("sz.internal.acm.mapper.PermissionMapper")
public interface PermissionMapper extends BaseMapper {
    public List<PermissionTO> findAssigned(@Param("role_id") String role_id) throws Exception;

    public List<PermissionTO> findUnassigned(@Param("role_id") String role_id, @Param("app_id") String app_id) throws Exception;

}
