package sz.internal.common.component.code.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import sz.internal.common.component.code.to.CodeTypeTO;

@Controller
@RequestMapping("/common/codetype")
public class CodeTypeController extends BaseController {

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
        	codetypeService = SpringContextHolder.getBean("sz.internal.common.component.code.service.CodeTypeService");
        }
        return codetypeService;
    }

	@RequestMapping("/codeTypeSearch")
	public String codeTypeSearch(CodeTypeTO codeTypeTO, HttpServletRequest request) throws Exception {
		codeTypeTO.setCodetype_id("");
		request.setAttribute("codetype", codeTypeTO);
		List pageTO = getCodeTypeService().search(codeTypeTO);
		request.setAttribute("pageTO", pageTO);
		return "common/component/code/codeTypeList";
	}

	@RequestMapping("{codetype_id}/edit")
	public String codeTypeEdit(@PathVariable String codetype_id, HttpServletRequest request) throws Exception {
		CodeTypeTO codeTypeTO = new CodeTypeTO();
		codeTypeTO.setCodetype_id(codetype_id);
		codeTypeTO = (CodeTypeTO) getCodeTypeService().find(codeTypeTO);
		String actionPath = request.getContextPath() + "/common/codetype/update";
		request.getSession().setAttribute("actionPath", actionPath);
		request.setAttribute("codetype", codeTypeTO);
		return "common/component/code/codeTypeUpdate";
	}

	@RequestMapping("/update")
	public String codeTypeUpdate(@Valid @ModelAttribute("codetype") CodeTypeTO codeTypeTO, HttpServletRequest request, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("codetype", codeTypeTO);
			return "common/component/code/codeTypeCommon";
		}
		if (CodeTypeTOValidation(codeTypeTO, request)) {
			request.setAttribute("codetype", codeTypeTO);
			return "common/component/code/codeTypeUpdate";
		}
		getCodeTypeService().updateCodeTypeTO(codeTypeTO);

		return "forward:codeTypeSearch";
	}

	private boolean CodeTypeTOValidation(CodeTypeTO codeTypeTO, HttpServletRequest request) throws Exception {
		Pattern pt = Pattern.compile("^[0-9a-zA-Z_]+$");
		Matcher mt = pt.matcher(codeTypeTO.getCodetype_id());
		if (!mt.matches()) {
			this.addBusinessError(request, "codeType.codetype_id.style");
			return true;
		}
		return false;
	}

	private boolean CodeTypeTODeleteValidation(CodeTypeTO codeTypeTO, HttpServletRequest request) throws Exception {
		CodeIntTO codeIntTO = new CodeIntTO();
		codeIntTO.setCodetype_id(codeTypeTO.getCodetype_id());
		List<CodeIntTO> codeIntTOList = getCodeIntService().search(codeIntTO);
		if (codeIntTOList != null && !codeIntTOList.isEmpty()) {
			this.addBusinessError(request, "codeType.codetype.delete");
			return true;
		}
		return false;
	}

	@RequestMapping("/{codetype_id}/delete")
	public String codeTypeDelete(@PathVariable String codetype_id, HttpServletRequest request) throws Exception {
		CodeTypeTO codeTypeTO = new CodeTypeTO();
		codeTypeTO.setCodetype_id(codetype_id);
		if (CodeTypeTODeleteValidation(codeTypeTO, request)) {
			request.setAttribute("codetype", codeTypeTO);
			codeTypeTO.setCodetype_id(null);
			List pageTO = getCodeTypeService().search(codeTypeTO);
			request.setAttribute("pageTO", pageTO);
			return "common/component/code/codeTypeList";
		}
		getCodeTypeService().delete(codeTypeTO);
		return "redirect:/common/codetype/codeTypeSearch";
	}

	@RequestMapping("/codeTypeCreateInit")
	public String codeTypeCreateInit(HttpServletRequest request) {
		String actionPath = request.getContextPath() + "/common/codetype/codeTypeCreate";
		request.getSession().setAttribute("actionPath", actionPath);
		request.setAttribute("codetype", new CodeTypeTO());
		return "common/component/code/codeTypeCreate";
	}

	@RequestMapping("/codeTypeCreate")
	public String codeTypeCreate(@Valid @ModelAttribute("codetype") CodeTypeTO codeTypeTO, BindingResult result, ModelMap map, HttpServletRequest request) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("codetype", codeTypeTO);
			List<FieldError> errorList = result.getFieldErrors();
			for (FieldError error : errorList) {
				map.put("error_" + error.getField(), error.getDefaultMessage());
			}
			return "common/component/code/codeTypeCreate";
		}
		if (CodeTypeTOValidation(codeTypeTO, request)) {
			request.setAttribute("codetype", codeTypeTO);
			return "common/component/code/codeTypeCreate";
		}
		List codeTypeList = getCodeTypeService().search(codeTypeTO);
		if (codeTypeList == null || codeTypeList.isEmpty()) {
			getCodeTypeService().insertCodeTypeTO(codeTypeTO);
			return "redirect:/common/codeint/codeIntSearch";
		}
		this.addBusinessError(request, "codeType.codetype.create");
		codeTypeTO.setCodetype_id("");
		request.setAttribute("codetype", codeTypeTO);
		return "common/component/code/codeTypeCreate";

	}
}
