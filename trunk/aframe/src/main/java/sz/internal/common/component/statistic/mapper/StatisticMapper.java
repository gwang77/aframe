package sz.internal.common.component.statistic.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import sz.internal.common.base.mapper.BaseMapper;

@Component("sz.internal.common.mapper.StatisticMapper")
public interface StatisticMapper extends BaseMapper {

    public int increaseCount(@Param("id") int id, @Param("inc_item_count") int inc_item_count, @Param("inc_item_user_count") int inc_item_user_count) throws Exception;

    public int increaseCountByItemKey(@Param("item_key") String item_key, @Param("inc_item_count") int inc_item_count, @Param("inc_item_user_count") int inc_item_user_count) throws Exception;

}
