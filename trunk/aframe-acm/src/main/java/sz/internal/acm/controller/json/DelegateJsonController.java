package sz.internal.acm.controller.json;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.acm.service.DelegateService;
import sz.internal.acm.to.DelegateTO;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;

@RestController
@RequestMapping("/json/delegate")
public class DelegateJsonController extends BaseController{

	private DelegateService delegateService;
	
	private DelegateService getDelegateService() {
        if (delegateService == null) {
        	delegateService = SpringContextHolder.getBean("delegateService");
        }
        return delegateService;
    }
	
	@RequestMapping(value = "/searchDelegate")
    public Object searchDelegate(@RequestBody DelegateTO delegateTO) throws Exception {
        List list = getDelegateService().searchDelegate(delegateTO);
        return list;
    }
	
	@RequestMapping(value = "/updateDelegate")
    public Object updateDelegate(@RequestBody DelegateTO delegateTO) throws Exception {
        getDelegateService().updateDelegate(delegateTO);
        return delegateTO;
    }
	
	@RequestMapping("/findDelegate/{id}")
	public Object findDelegate(@PathVariable String id) throws Exception {
		int uID = Integer.parseInt(id);
		DelegateTO delegateTO = new DelegateTO();
		delegateTO.setId(uID);
		delegateTO=(DelegateTO) getDelegateService().find(delegateTO);
		return delegateTO;
	}
	
	@RequestMapping(value = "/findDelegateUserFrom")
    public Object findDelegateUserFrom() throws Exception {
		String user=getDelegateService().getCurrUser();
		DelegateTO delegateTO=new DelegateTO();
		delegateTO.setUser_from(user);
        return delegateTO;
    }
	
	@RequestMapping(value = "/createDelegate")
    public Object createDelegate(@RequestBody DelegateTO delegateTO) throws Exception {
		getDelegateService().saveDelegate(delegateTO);		
        return delegateTO;
    }
	
	@RequestMapping("/deleteDelegate/{id}")
    public Object deleteDelegate(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        DelegateTO delegateTO = new DelegateTO();
        delegateTO.setId(uID);
        getDelegateService().delete(delegateTO);
        return "";
    }
}
