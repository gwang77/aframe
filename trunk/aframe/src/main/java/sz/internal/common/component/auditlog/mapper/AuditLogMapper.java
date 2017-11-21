package sz.internal.common.component.auditlog.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import sz.internal.common.component.auditlog.to.AuditLogTO;


/**
 * 
 * @author yangdan
 *
 */
@Component("sz.internal.common.component.auditlog.mapper.AuditLogMapper")
public interface AuditLogMapper {

	public int insert(AuditLogTO auditlogTO) throws Exception;

	public List search(AuditLogTO auditlogTO) throws Exception;
	
	public AuditLogTO find(AuditLogTO auditlogTO) throws Exception;

}
