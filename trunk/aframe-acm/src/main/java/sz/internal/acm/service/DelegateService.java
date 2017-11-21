package sz.internal.acm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import sz.internal.acm.mapper.DelegateMapper;
import sz.internal.acm.to.DelegateTO;
import sz.internal.acm.to.UserTO;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.exception.BusinessException;

@Component
public class DelegateService extends BaseService{
	
	@Resource(name = "sz.internal.acm.mapper.DelegateMapper")
    public void setMapper(DelegateMapper mapper) {
        super.setMapper(mapper);
    }
	
	public void saveDelegate(DelegateTO delegateTO) throws Exception {
        if (delegateTO==null) {
        	throw new BusinessException("errors_delegate_null");
		}
        List<UserTO> delegateTOList = delegateTO.getUserList();
        if (delegateTOList==null) {
        	throw new BusinessException("errors_delegate_userTo_null");
		}
        String user_from=delegateTO.getUser_from();
        if (user_from==null) {
			throw new BusinessException("errors_delegate_userFrom_null");
		}
        String date_from=delegateTO.getDate_from_s();
        if (date_from==null) {
			throw new BusinessException("errors_delegate_dateFrom_null");
		}
        String date_to=delegateTO.getDate_to_s();
        if (date_to==null) {
			throw new BusinessException("errors_delegate_dateTo_null");
		}
        for (UserTO user : delegateTOList) {
        	DelegateTO delegate = new DelegateTO();
        	delegate.setUser_from(user_from);
        	delegate.setUser_to(user.getUsername());
        	delegate.setDate_from_s(date_from);
        	delegate.setDate_to_s(date_to);
			insert(delegate);
        }
    }
	
	public List<DelegateTO> searchDelegate(DelegateTO delegateTO) throws Exception{
		List list = search(delegateTO);
		return list;
	}

	public void updateDelegate(DelegateTO delegateTO)throws Exception{
		Integer Did=delegateTO.getId();
		if (Did==null) {
			throw new BusinessException("errors_delegate_id_null");
		}
		
        update(delegateTO);
	}
}
