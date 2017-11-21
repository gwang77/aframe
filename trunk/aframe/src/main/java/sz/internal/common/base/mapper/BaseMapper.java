package sz.internal.common.base.mapper;

import org.apache.ibatis.annotations.Param;
import sz.internal.common.base.to.BaseTO;

import java.util.List;

/**
 * User: wanggang
 * Date: 12/4/15
 * Time: 9:30 AM
 */
public interface BaseMapper {
    //return inserted count
    public int insert(BaseTO baseTO) throws Exception;

    //return updated count
    public int update(BaseTO baseTO) throws Exception;

    //return deleted count
    public int delete(BaseTO baseTO) throws Exception;

    public List search(BaseTO baseTO) throws Exception;

    public BaseTO find(BaseTO baseTO) throws Exception;

    public void truncateTable(@Param("tableName") String table_name) throws Exception;
    
    public int getTableCount(@Param("tableName") String table_name) throws Exception;

}
