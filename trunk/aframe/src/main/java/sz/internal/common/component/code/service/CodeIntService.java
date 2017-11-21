package sz.internal.common.component.code.service;

import org.springframework.stereotype.Component;

import sz.internal.common.base.service.BaseService;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.mapper.CodeIntMapper;
import sz.internal.common.component.code.to.CodeIntTO;
import sz.internal.common.exception.BusinessException;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("sz.internal.common.component.code.service.CodeIntService")
public class CodeIntService extends BaseService {
    @Resource(name = "sz.internal.common.component.code.mapper.CodeIntMapper")
    public void setMapper(CodeIntMapper codeIntMapper) {
        super.setMapper(codeIntMapper);
    }

    private static Map codeMap = new HashMap();
    
    public void updateCodeIntTO(CodeIntTO codeIntTO) throws Exception {
        validationCodeIntTO(codeIntTO);
        update(codeIntTO);
        CodeMgr.clearCache(codeIntTO.getCodetype_id());
    }

    public void insertCodeIntTO(CodeIntTO codeIntTO) throws Exception {
        validationCodeIntTO(codeIntTO);    
        insert(codeIntTO);
        CodeMgr.clearCache(codeIntTO.getCodetype_id());
    }

    public void validationCodeIntTO(CodeIntTO codeIntTO) throws Exception {
        // format error
        Pattern pt = Pattern.compile("^[0-9a-zA-Z_]+$");
        Matcher mt = pt.matcher(codeIntTO.getCode_id());
        if (!mt.matches()) {
            throw new BusinessException("codeInt.code_id.style");
        }
    }

    

    public List getCodeIntList(String code_id) throws Exception {
        CodeIntTO codeintTO = new CodeIntTO();
        codeintTO.setCodetype_id(code_id);
        List<CodeIntTO> codeIntTOLists = search(codeintTO);
        return codeIntTOLists;
    }


}

