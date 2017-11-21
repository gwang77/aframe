package sz.internal.common.component.code.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.service.CodeTypeService;
import sz.internal.common.component.code.to.CodeTypeTO;

import java.util.List;

@RestController
@RequestMapping("/json/common/codetype")
public class CodeTypeJsonController extends BaseController {
	private CodeTypeService codetypeService;
	
	private CodeTypeService getCodeTypeService() {
        if (codetypeService == null) {
        	codetypeService = SpringContextHolder.getBean("sz.internal.common.component.code.service.CodeTypeService");
        }
        return codetypeService;
    }

    @RequestMapping("/codeTypeSearch")
    public Object codeTypeSearch(@RequestBody CodeTypeTO codeTypeTO) throws Exception {
       // codeTypeTO.setCodetype_id("");
        List list = getCodeTypeService().search(codeTypeTO);
        return list;
    }

    @RequestMapping("/find/{codetype_id}")
    public Object codeTypeFind(@PathVariable String codetype_id) throws Exception {
        CodeTypeTO codeTypeTO = new CodeTypeTO();
        codeTypeTO.setCodetype_id(codetype_id);
        codeTypeTO = (CodeTypeTO) getCodeTypeService().find(codeTypeTO);
        return codeTypeTO;
    }

    @RequestMapping(value = "/createCodeType")
    public Object createCodeType(@RequestBody CodeTypeTO codetypeTO) throws Exception {
        getCodeTypeService().insertCodeTypeTO(codetypeTO);
        return codetypeTO;
    }

    @RequestMapping(value = "/updateCodeType")
    public Object updateCodeType(@RequestBody CodeTypeTO codetypeTO) throws Exception {
        getCodeTypeService().updateCodeTypeTO(codetypeTO);
        return codetypeTO;
    }

    @RequestMapping("/deleteCodeType/{codetype_id}")
    public Object deleteCodeType(@PathVariable String codetype_id) throws Exception {
        CodeTypeTO codetypeTO = new CodeTypeTO();
        codetypeTO.setCodetype_id(codetype_id);
        getCodeTypeService().delete(codetypeTO);
        return "";
    }

    @RequestMapping("/codeTypeIdSearch")
    public Object codeTypeIdSearch(@RequestBody CodeTypeTO codeTypeTO) throws Exception {
        codeTypeTO.setCodetype_id("");
        List codeTypeIdList = getCodeTypeService().getCodeTypeIdList(codeTypeTO);
        return codeTypeIdList;
    }

}
