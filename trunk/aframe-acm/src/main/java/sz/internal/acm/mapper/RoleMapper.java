package sz.internal.acm.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import sz.internal.acm.to.RoleTO;
import sz.internal.common.base.mapper.BaseMapper;

import java.util.List;

@Component("sz.internal.acm.mapper.RoleMapper")
public interface RoleMapper extends BaseMapper {
    public List<RoleTO> findAssignedToUser(@Param("user_id") String user_id, @Param("app_id") String app_id) throws Exception;

    public List<RoleTO> findUnassignedToUser(@Param("user_id") String user_id, @Param("app_id") String app_id) throws Exception;

}
