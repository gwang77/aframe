package sz.internal.common.component.code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.service.CodeIntService;
import sz.internal.common.component.code.to.CodeIntTO;
import sz.internal.common.component.code.to.CodeTO;

@RestController
@RequestMapping("/json/common/codeint")
public class CodeIntJsonController extends BaseController {
	private CodeIntService codeintService;
	
	private CodeIntService getCodeIntService() {
        if (codeintService == null) {
        	codeintService = SpringContextHolder.getBean("sz.internal.common.component.code.service.CodeIntService");
        }
        return codeintService;
    }
	
	@RequestMapping("/codeIntSearch")
	public Object codeIntSearch(@RequestBody CodeIntTO codeIntTO) throws Exception {
		codeIntTO.setCode_id("");
		List list = getCodeIntService().search(codeIntTO);
		for (Object toTmp : list) {
			prepareCodeInt((CodeIntTO) toTmp);
		}
		return list;
	}
	
	@RequestMapping("/find/{code_id}/{locales}/{codetype_id}")
	public Object codeIntFind(@PathVariable String code_id, @PathVariable String locales, @PathVariable String codetype_id) throws Exception {
		CodeIntTO codeIntTO = new CodeIntTO();
		codeIntTO.setCode_id(code_id);
		codeIntTO.setLocales(locales);
		codeIntTO.setCodetype_id(codetype_id);
		codeIntTO = (CodeIntTO) getCodeIntService().find(codeIntTO);
		return codeIntTO;
	}

	@RequestMapping(value = "/createCodeInt")
	public Object createCodeInt(@RequestBody CodeIntTO codeintTO) throws Exception {
		getCodeIntService().insertCodeIntTO(codeintTO);
		return codeintTO;
	}


	@RequestMapping("/deleteCodeInt/{code_id}/{locales}/{codetype_id}")
	public Object deleteCodeInt(@PathVariable String code_id, @PathVariable String locales, @PathVariable String codetype_id) throws Exception {
		CodeIntTO codeIntTO = new CodeIntTO();
		codeIntTO.setCode_id(code_id);
		codeIntTO.setLocales(locales);
		codeIntTO.setCodetype_id(codetype_id);
		getCodeIntService().delete(codeIntTO);
		return "";
	}
	
	@RequestMapping(value = "/updateCodeInt")
	public Object updateCodeInt(@RequestBody CodeIntTO codeintTO) throws Exception {
		getCodeIntService().updateCodeIntTO(codeintTO);
		return codeintTO;
	}
	private void prepareCodeInt(CodeIntTO codeintTO) throws Exception {
		if (codeintTO == null) {
			return;
		}
		CodeTO Status = CodeMgr.getCodeTO(CodeConstant.CODE_TYPE_STATUS, codeintTO.getStatus());
		CodeTO Locales = CodeMgr.getCodeTO(CodeConstant.CODE_TYPE_LOCAL, codeintTO.getLocales());
		codeintTO.setStatus_code(Status);
		codeintTO.setLocales_code(Locales);
	}
}