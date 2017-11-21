package sz.internal.acm.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import sz.internal.common.base.mapper.BaseMapper;

@Component("sz.internal.acm.mapper.UserRoleMapper")
public interface UserRoleMapper extends BaseMapper {
    public void deleteByUserId(@Param("user_id") int user_id, @Param("app_id") String app_id) throws Exception;

}
