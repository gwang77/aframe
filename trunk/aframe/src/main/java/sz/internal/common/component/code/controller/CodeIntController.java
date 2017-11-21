package sz.internal.common.component.code.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.service.CodeIntService;
import sz.internal.common.component.code.service.CodeTypeService;
import sz.internal.common.component.code.to.CodeIntTO;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.component.code.to.CodeTypeTO;

@Controller
@RequestMapping("/common/codeint")
public class CodeIntController extends BaseController {
	
	private CodeIntService codeintService;
	
	private CodeIntService getCodeIntService() {
        if (codeintService == null) {
        	codeintService = SpringContextHolder.getBean("sz.internal.common.component.code.service.CodeIntService");
        }
        return codeintService;
    }
	
	private CodeTypeService codetypeService;
	
	private CodeTypeService getCodeTypeService() {
        if (codetypeService == null) {
        	codetypeService = SpringContextHolder.getBean("codetypeService");
        }
        return codetypeService;
    }

	@RequestMapping("/codeIntSearch")
	public String codeIntSearch(CodeIntTO codeIntTO, HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(codeIntTO.getLocales())) {
			codeIntTO.setLocales("en");
		}
		request.setAttribute("codeTO", codeIntTO);
		CodeTypeTO codeTypeTO = new CodeTypeTO();
		List<CodeTypeTO> codeTypeTOList = getCodeTypeService().search(codeTypeTO);
		List codeTOList = new ArrayList();
		for (CodeTypeTO codeIntTOTmp : codeTypeTOList) {
			CodeTO codeTOs = new CodeTO();
			codeTOs.setCode_id(codeIntTOTmp.getCodetype_id());
			codeTOs.setCode_desc(codeIntTOTmp.getCodetype_desc());
			codeTOList.add(codeTOs);
		}
		request.setAttribute("codetypelist", codeTOList);
		if (StringUtils.isEmpty(codeIntTO.getCodetype_id())) {
			codeIntTO.setCodetype_id("comm_sys_config");
			List pageTO = codeintService.search(codeIntTO);
			request.setAttribute("pageTO", pageTO);
			return "common/component/code/codeIntList";
		}
		CodeIntTO codeIntTOs = new CodeIntTO();
		codeIntTOs.setCodetype_id(codeIntTO.getCodetype_id());
		codeIntTOs.setLocales(codeIntTO.getLocales());
		List pageTO = getCodeIntService().search(codeIntTOs);
		request.setAttribute("pageTO", pageTO);
		return "common/component/code/codeIntList";
	}

	@RequestMapping("{codetype_id}/{code_id}/{locales}/edit")
	public String codeIntEdit(@PathVariable String codetype_id, @PathVariable String code_id, @PathVariable String locales, HttpServletRequest request) throws Exception {
		CodeIntTO codeIntTO = new CodeIntTO();
		codeIntTO.setCode_id(code_id);
		codeIntTO.setCodetype_id(codetype_id);
		codeIntTO.setLocales(locales);
		codeIntTO = (CodeIntTO) getCodeIntService().find(codeIntTO);
		String actionPath = request.getContextPath() + "/common/codeint/update";
		request.getSession().setAttribute("actionPath", actionPath);
		request.setAttribute("codeint", codeIntTO);
		return "common/component/code/codeIntCommon";
	}

	@RequestMapping("/update")
	public String codeIntUpdate(CodeIntTO codeIntTO, HttpServletRequest request, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("codeint", codeIntTO);
			return "common/component/code/codeIntCommon";
		}
		if (CodeIntTOValidation(codeIntTO, request)) {
			request.setAttribute("codeint", codeIntTO);
			return "common/component/code/codeIntCommon";
		}
		getCodeIntService().updateCodeIntTO(codeIntTO);

		return "forward:/common/codeint/codeIntSearch";
	}

	@RequestMapping("{codetype_id}/{code_id}/{locales}/delete")
	public String codeIntDelete(@PathVariable String codetype_id, @PathVariable String code_id, @PathVariable String locales, HttpServletRequest request) throws Exception {
		CodeIntTO codeIntTO = new CodeIntTO();
		codeIntTO.setCodetype_id(codetype_id);
		codeIntTO.setCode_id(code_id);
		codeIntTO.setLocales(locales);
		getCodeIntService().delete(codeIntTO);
		codeIntTO.setLocales("");
		return codeIntSearch(codeIntTO, request);
	}

	@RequestMapping("/codeIntCreateInit")
	public String codeIntCreateInit(CodeIntTO codeIntTO, HttpServletRequest request) {
		String actionPath = request.getContextPath() + "/common/codeint/codeIntCreate";
		request.getSession().setAttribute("actionPath", actionPath);
		codeIntTO.setLocales("en");
		codeIntTO.setStatus("A");
		request.setAttribute("codeint", codeIntTO);
		return "common/component/code/codeIntCommon";
	}

	@RequestMapping("/codeIntCreate")
	public String codeIntCreate(@Valid @ModelAttribute("codeint") CodeIntTO codeIntTO, BindingResult result, HttpServletRequest request, ModelMap map) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("codeint", codeIntTO);
			List<FieldError> errorList = result.getFieldErrors();
			for (FieldError error : errorList) {
				map.put("error_" + error.getField(), error.getDefaultMessage());
			}
			return "common/component/code/codeIntCommon";
		}
		if (CodeIntTOValidation(codeIntTO, request)) {
			request.setAttribute("codeint", codeIntTO);
			return "common/component/code/codeIntCommon";
		}
		CodeIntTO codeIntTOs = new CodeIntTO();
		codeIntTOs.setCode_id(codeIntTO.getCode_id());
		codeIntTOs.setCodetype_id(codeIntTO.getCodetype_id());
		codeIntTOs.setLocales(codeIntTO.getLocales());
		List codeIntList = getCodeIntService().search(codeIntTOs);
		if (codeIntList == null || codeIntList.isEmpty()) {
			getCodeIntService().insertCodeIntTO(codeIntTO);
			codeIntTO.setLocales("");
			return codeIntSearch(codeIntTO, request);
		}
		this.addBusinessError(request, "code_id is already exist");
		codeIntTO.setCode_id("");
		request.setAttribute("codeint", codeIntTO);
		return "common/component/code/codeIntCommon";

	}

	private boolean CodeIntTOValidation(CodeIntTO codeIntTO, HttpServletRequest request) throws Exception {
		Pattern pt = Pattern.compile("^[0-9a-zA-Z_]+$");
		Pattern pts = Pattern.compile("^[0-9]*$");
		Matcher mt = pt.matcher(codeIntTO.getCode_id());
		Matcher mts = pts.matcher(codeIntTO.getCode_seq());
		if (!mt.matches()) {
			this.addBusinessError(request, "codeInt.code_id.style");
			return true;
		}
		if (!mts.matches()) {
			this.addBusinessError(request, "codeInt.code_seq.style");
			return true;
		}
		return false;
	}

	@RequestMapping("/doBack")
	public String doBack(CodeIntTO codeIntTO, HttpServletRequest request) throws Exception {
		codeIntTO.setLocales("");
		return codeIntSearch(codeIntTO, request);
	}
}
