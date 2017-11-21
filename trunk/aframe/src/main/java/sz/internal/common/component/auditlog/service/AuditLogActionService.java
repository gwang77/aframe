package sz.internal.common.component.auditlog.service;

import org.springframework.stereotype.Component;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.component.auditlog.mapper.AuditLogActionMapper;

import javax.annotation.Resource;

@Component
public class AuditLogActionService extends BaseService {
    @Resource(name = "sz.internal.common.mapper.AuditLogActionMapper")
    public void setMapper(AuditLogActionMapper mapper) {
        super.setMapper(mapper);
    }
}
