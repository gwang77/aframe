package sz.internal.common.component.code.service;


import org.springframework.stereotype.Component;

import sz.internal.common.base.service.BaseService;
import sz.internal.common.component.code.mapper.CodeTypeMapper;
import sz.internal.common.component.code.to.CodeTypeTO;
import sz.internal.common.exception.BusinessException;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("sz.internal.common.component.code.service.CodeTypeService")
public class CodeTypeService extends BaseService {
	@Resource(name = "sz.internal.common.component.code.mapper.CodeTypeMapper")
	public void setMapper(CodeTypeMapper mapper) {
		super.setMapper(mapper);
	}

	public void updateCodeTypeTO(CodeTypeTO codeTypeTO) throws Exception {
		validationCodeTypeTO(codeTypeTO);
		update(codeTypeTO);
	}
	public void insertCodeTypeTO(CodeTypeTO codeTypeTO) throws Exception {
		validationCodeTypeTO(codeTypeTO);
		insert(codeTypeTO);
	}
	public void validationCodeTypeTO(CodeTypeTO codeTypeTO) throws Exception {
		// format error
		Pattern pt=Pattern.compile("^[0-9a-zA-Z_]+$");
		Matcher mt=pt.matcher(codeTypeTO.getCodetype_id());		
		if (!mt.matches()) {
			throw new BusinessException("codeType.codetype_id.style");
		}	
	}
	public String getCodeTypeDesc(String codetype_id) throws Exception {
		CodeTypeTO codeTypeTO=new CodeTypeTO();
		codeTypeTO.setCodetype_id(codetype_id);
		List<CodeTypeTO> codeTypeTOLists = search(codeTypeTO);
		for (Object aCodeTOList : codeTypeTOLists) {
			CodeTypeTO codeTO = (CodeTypeTO) aCodeTOList;
			if (codetype_id.equals(codeTO.getCodetype_id())) {			
				return codeTO.getCodetype_desc();
			}
		}
		return "";
	}
	public List <String> getCodeTypeIdList(CodeTypeTO codeTypeTO) throws Exception {
		List <CodeTypeTO> codeTypeList = search(codeTypeTO);
		List <String> codeTypeIdList = new ArrayList<String>();
		
		for (int i=0; i<codeTypeList.size(); i++){
			codeTypeIdList.add(codeTypeList.get(i).getCodetype_id());
		}
		
		return codeTypeIdList;
	}
}
