package sz.internal.acm.controller.json;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.acm.service.ApplicationService;
import sz.internal.acm.to.ApplicationSearchTO;
import sz.internal.acm.to.ApplicationTO;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.to.PageTO;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.to.CodeTO;

@RestController
@RequestMapping("/json/application")
public class ApplicationJsonController {

	private ApplicationService applicationService;
	
	private ApplicationService getApplicationService() {
        if (applicationService == null) {
        	applicationService = SpringContextHolder.getBean("applicationService");
        }
        return applicationService;
    }

	@RequestMapping(value = "/searchApplication")
	public Object searchApplication(@RequestBody ApplicationTO applicationTO) throws Exception {
		List list = getApplicationService().search(applicationTO);
		for (Object toTmp : list) {
			prepareApplicationCode((ApplicationTO) toTmp);
		}
		return list;
	}

	@RequestMapping("/searchApplicationPage")
	public Object searchApplicationPage(@RequestBody ApplicationSearchTO searchTO) throws Exception {
		PageTO pageTO = searchTO.getPageTO();
		ApplicationTO applicationTO = new ApplicationTO();
		applicationTO.setName_like(searchTO.getName_like());
		applicationTO.setSortBy(searchTO.getSortBy());
		applicationTO.setSortType(searchTO.getSortType());
		pageTO = getApplicationService().searchPage(applicationTO, pageTO);
		List list = pageTO.getList();
		for (Object toTmp : list) {
			prepareApplicationCode((ApplicationTO) toTmp);
		}
		return pageTO;
	}

	@RequestMapping("/findApplication/{id}")
	public Object findApplication(@PathVariable String id) throws Exception {
		int uID = Integer.parseInt(id);
		ApplicationTO applicationTO = new ApplicationTO();
		applicationTO.setId(uID);
		applicationTO = (ApplicationTO) getApplicationService().find(applicationTO);
		prepareApplicationCode(applicationTO);
		return applicationTO;
	}

	@RequestMapping("/findAppByAppId/{appId}")
	public Object findApplicationByAppId(@PathVariable String appId) throws Exception {
		ApplicationTO applicationTO = new ApplicationTO();
		applicationTO.setApp_id(appId);
		List list = getApplicationService().search(applicationTO);
		if (list.size() > 0) {
			ApplicationTO applicationTOResult = (ApplicationTO) list.get(0);
			prepareApplicationCode(applicationTOResult);
			return applicationTOResult;
		}
		return "";
	}

	@RequestMapping(value = "/createApplication")
	public Object createApplication(@RequestBody ApplicationTO applicationTO) throws Exception {
		getApplicationService().insertApplicationTO(applicationTO);

		return applicationTO;
	}

	@RequestMapping(value = "/updateApplication")
	public Object updateApplication(@RequestBody ApplicationTO applicationTO) throws Exception {
		getApplicationService().updateApplicationTO(applicationTO);

		return applicationTO;
	}

	@RequestMapping("/deleteApplication/{id}")
	public Object deleteApplication(@PathVariable String id) throws Exception {
		int uID = Integer.parseInt(id);
		ApplicationTO applicationTO = new ApplicationTO();
		applicationTO.setId(uID);

		getApplicationService().delete(applicationTO);

		return "";
	}

	private void prepareApplicationCode(ApplicationTO applicationTO) throws Exception {
		if (applicationTO == null) {
			return;
		}
		CodeTO codeTO = CodeMgr.getCodeTO(CodeConstant.CODE_TYPE_YES_NO, applicationTO.getLocked());
		applicationTO.setLocked_code(codeTO);
	}

	
}
