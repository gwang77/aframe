package sz.internal.common.component.code.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.SystemConfig;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.service.CodeIntService;
import sz.internal.common.component.code.service.CodeTypeService;
import sz.internal.common.component.code.to.CodeIntTO;
import sz.internal.common.component.code.to.CodeTypeTO;
import sz.internal.common.component.code.to.SystemConfigTO;

@Controller
@RequestMapping("/common/systemConfig")
public class SystemConfigController extends BaseController {
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

	@RequestMapping("/systemConfigSearch")
	public String systemConfigSearch(SystemConfigTO systemConfigTO, HttpServletRequest request) throws Exception {
		String[][] systemStr = SystemConfig.getAllSysConfigDefine();
		CodeIntTO codeintTO = new CodeIntTO();
		codeintTO.setCodetype_id(CodeConstant.CODE_TYPE_SYSTEM_CONFIG);
		List<CodeIntTO> codeIntTOList = getCodeIntService().search(codeintTO);
		List configeList = new ArrayList();
		for (int i = 0; i < codeIntTOList.size(); i++) {
			CodeIntTO codeintTOs = (CodeIntTO) codeIntTOList.get(i);
			SystemConfigTO systemConfigTOss = new SystemConfigTO();
			systemConfigTOss.setCode_id(codeintTOs.getCode_id());
			systemConfigTOss.setCode_desc(codeintTOs.getCode_desc());
			systemConfigTOss.setLabel(getLabel(codeintTOs.getCode_id(), systemStr));
			systemConfigTOss.setValue_codetype_id(getValueCodetypeId(codeintTOs.getCode_id(), systemStr));
			systemConfigTOss.setEditable(codeintTOs.getEditable());
			configeList.add(systemConfigTOss);
		}
		request.setAttribute("systemTO", configeList);
		return "common/component/code/systemConfig";
	}

	private String getSystemInfo(String code_id, String[][] systemStr, int idx) throws Exception {
		for (int i = 0; i < systemStr.length; i++) {
			if (systemStr[i][0].equals(code_id)) {
				return systemStr[i][idx];
			}
		}
		return "";
	}

	private String getLabel(String code_id, String[][] systemStr) throws Exception {
		String definedCodeTypeDesc = getSystemInfo(code_id, systemStr, 1);
		String codetype_desc = getCodeTypeService().getCodeTypeDesc(code_id);
		if (StringUtils.isEmpty(definedCodeTypeDesc)) {
			if (StringUtils.isEmpty(codetype_desc)) {
				return code_id;
			}
			return codetype_desc;
		}
		return definedCodeTypeDesc;
	}

    private String getValueCodetypeId(String code_id, String[][] systemStr) throws Exception {
        String definedValueCodeType = getSystemInfo(code_id, systemStr, 2);
        if (!StringUtils.isEmpty(definedValueCodeType)) {
            return definedValueCodeType;
        }
        CodeTypeTO codeTypeTO = new CodeTypeTO();
        codeTypeTO.setCodetype_id(code_id);
        codeTypeTO = (CodeTypeTO) getCodeTypeService().find(codeTypeTO);
        if (codeTypeTO != null) {
            return code_id;
        }
        return "";
    }

	@RequestMapping("/update")
	public String systemConfigUpdate(@Valid @ModelAttribute("systemConfigTO") SystemConfigTO systemConfigTO, HttpServletRequest request, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("systemConfigTO", systemConfigTO);
			return "common/component/code/systemConfig";
		}
		List systemConfigTOList = systemConfigTO.getValueList();
		CodeIntTO codeIntTO = new CodeIntTO();
		for (int i = 0; i < systemConfigTOList.size(); i++) {
			SystemConfigTO systemConfigTOs = (SystemConfigTO) systemConfigTOList.get(i);
			codeIntTO.setCode_id(systemConfigTOs.getCode_id());
			codeIntTO.setCode_desc(systemConfigTOs.getCode_desc());
			codeIntTO.setCodetype_id(CodeConstant.CODE_TYPE_SYSTEM_CONFIG);
			String[] newCodeIntList = { "code_desc" };
			codeIntTO.setAmendList(newCodeIntList);
			getCodeIntService().updateCodeIntTO(codeIntTO);
		}
		return "redirect:/common/systemConfig/systemConfigSearch";
	}
}
