package sz.internal.acm.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sz.internal.acm.mapper.PermissionMapper;
import sz.internal.acm.to.PermissionTO;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

@Component
public class PermissionService extends BaseService {
    @Resource(name = "sz.internal.acm.mapper.PermissionMapper")
    public void setMapper(PermissionMapper mapper) {
        super.setMapper(mapper);
    }

    public void insertPermission(PermissionTO permissionTO) throws Exception {
        validate(permissionTO);
        insert(permissionTO);
    }

    public void updatePermission(PermissionTO permissionTO) throws Exception {

        validate(permissionTO);
        update(permissionTO);
    }

    private void validate(PermissionTO permissionTO) throws Exception {
        String name = permissionTO.getName();
        String app_id = permissionTO.getApp_id();
        String url = permissionTO.getUrl();
        String requestMethod = permissionTO.getRequest_method();
        String permission = permissionTO.getPermission();

        if (StringUtils.isEmpty(name)) {
            throw new BusinessException("errors_name_null");
        }
        if (name.length() > 100) {
            throw new BusinessException("errors_name_size");
        }
        checkDuplicate(permissionTO);

        if (StringUtils.isEmpty(app_id)) {
            throw new BusinessException("errors_app_id_null");
        }
        if (app_id.length() > 100) {
            throw new BusinessException("errors_app_id_size");
        }

        if (url != null && url.length() > 100) {
            throw new BusinessException("errors_url_size");
        }

        if (requestMethod != null && requestMethod.length() > 10) {
            throw new BusinessException("errors_requestMethod_size");
        }

        if (permission != null && permission.length() > 100) {
            throw new BusinessException("errors_permission_size");
        }
    }

    public void checkDuplicate(PermissionTO permissionTO) throws Exception {
        PermissionTO permissionTOTmp = new PermissionTO();

        permissionTOTmp.setName(permissionTO.getName());
        permissionTOTmp.setApp_id(permissionTO.getApp_id());
        List<PermissionTO> list = search(permissionTOTmp);
        if (list.size() >= 1) {
            if (permissionTO.getId() == null || !list.get(0).getId().equals(permissionTO.getId())) {
                throw new BusinessException("errors_resource_exist");
            }
        }
    }

    public List<PermissionTO> findAssignedPermission(String permissionId) throws Exception {
        PermissionMapper resourceMapper = (PermissionMapper) getMapper();
        return resourceMapper.findAssigned(permissionId);
    }

    public List<PermissionTO> findUnassignedPermission(String permissionId, String appId) throws Exception {
        PermissionMapper resourceMapper = (PermissionMapper) getMapper();
        return resourceMapper.findUnassigned(permissionId, appId);
    }
}
