package sz.internal.common.component.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.to.CodeTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/json/common/code")
public class CodeController extends BaseController {
    @RequestMapping("/getCode/{code_type_id}")
    public List<CodeTO> getCode(@PathVariable String code_type_id) throws Exception {
        return CodeMgr.getCodes(code_type_id);
    }

    @RequestMapping("/getCodes/{code_type_ids}")
    public List getCodes(@PathVariable String code_type_ids) throws Exception {
        List<List> list = new ArrayList<List>();
        String[] code_type_id_arr = code_type_ids.split(",");
        for (String code_type : code_type_id_arr) {
            List<CodeTO> codeList = CodeMgr.getCodes(code_type);
            list.add(codeList);
        }
        return list;
    }
}
