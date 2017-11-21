package sz.internal.acm.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sz.internal.acm.mapper.ApplicationMapper;
import sz.internal.acm.to.ApplicationTO;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ApplicationService extends BaseService {
    @Resource(name = "sz.internal.acm.mapper.ApplicationMapper")
    public void setMapper(ApplicationMapper mapper) {
        super.setMapper(mapper);
    }

	public void updateApplicationTO(ApplicationTO application) throws Exception {
		validationApplicationTO(application);
		update(application);
	}

	public void insertApplicationTO(ApplicationTO application) throws Exception {
		validationApplicationTO(application);
		insert(application);
	}
    
	public void validationApplicationTO(ApplicationTO applicationTO) throws Exception {

		if (StringUtils.isEmpty(applicationTO.getName())) {
			throw new BusinessException("errors_name_null");
		} else if (applicationTO.getName().length() > 100) {
			throw new BusinessException("errors_name_size");
		}

		String rex = "^[A-Za-z0-9_-]+$";
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(applicationTO.getApp_id());
		if (!matcher.find()) {
			throw new BusinessException("errors_appId_rexError");
		}

		ApplicationTO checkApplication = new ApplicationTO();
		checkApplication.setApp_id(applicationTO.getApp_id());
		List<ApplicationTO> applicationList = search(checkApplication);
		if (applicationList.size() >= 1) {
			if (applicationTO.getId() == null || !applicationList.get(0).getId().equals(applicationTO.getId())) {
				throw new BusinessException("errors_application_exist");
			}
		}
	}

}
