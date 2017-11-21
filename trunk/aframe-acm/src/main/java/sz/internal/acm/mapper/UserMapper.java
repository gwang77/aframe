package sz.internal.acm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import sz.internal.acm.to.UserTO;
import sz.internal.common.base.mapper.BaseMapper;

@Component("sz.internal.acm.mapper.UserMapper")
public interface UserMapper extends BaseMapper {
    public int changePassword(UserTO userTO) throws Exception;
    public List<UserTO> findUserByUser_from(@Param("user_from") String user_from) throws Exception;
    public List<UserTO> findUserNotUser_from(@Param("user_from") String user_from) throws Exception;
}
