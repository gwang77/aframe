package sz.internal.common.component.auditlog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.base.to.PageTO;
import sz.internal.common.component.auditlog.service.AuditLogService;
import sz.internal.common.component.auditlog.to.AuditLogSearchTO;
import sz.internal.common.component.auditlog.to.AuditLogTO;
import sz.internal.common.util.DateUtil;
import sz.internal.common.util.JsonUtil;


@RestController
@RequestMapping("/json/auditLog")
public class AuditLogJsonController extends BaseController {

	private AuditLogService auditLogService;
	
	private AuditLogService getAuditLogService() {
        if (auditLogService == null) {
        	auditLogService = SpringContextHolder.getBean("auditLogService");
        }
        return auditLogService;
    }

	@RequestMapping(value = "/searchAuditLog")
	public Object searchAuditLog(@RequestBody AuditLogSearchTO searchTO) throws Exception {
		PageTO pageTO = searchTO.getPageTO();
		AuditLogTO auditLogTO = new AuditLogTO();
		auditLogTO.setClass_nameLike(searchTO.getClass_nameLike());
		auditLogTO.setActionLike(searchTO.getActionLike());
		if (StringUtils.hasText(searchTO.getStart_date())) {
			auditLogTO.setStart_date(DateUtil.parseDate(searchTO.getStart_date()));
		}
		if (StringUtils.hasText(searchTO.getEnd_date())) {
			auditLogTO.setEnd_date(DateUtil.parseDate(searchTO.getEnd_date()));
		}
		auditLogTO.setSortBy(searchTO.getSortBy());
		auditLogTO.setSortType(searchTO.getSortType());
		pageTO = getAuditLogService().searchPage(auditLogTO, pageTO);
		List list = pageTO.getList();
		return pageTO;
	}

	@RequestMapping(value = "/convertAuditLogTOtoMap")
	public Object convertAuditLogTOtoMap(@RequestBody AuditLogTO auditLogTO) throws Exception {
		Map b_map = (Map) JsonUtil.readValue(auditLogTO.getB_img(), HashMap.class);
		Map a_map = (Map) JsonUtil.readValue(auditLogTO.getA_img(), HashMap.class);
		Set fieldsSet = new HashSet();
		fieldsSet.addAll(b_map.keySet());
		fieldsSet.addAll(a_map.keySet());
		// Map details = new HashMap();
		// Iterator it = fieldsSet.iterator();
		// while(it.hasNext()){
		// String property=it.next()+"";
		// List detailItem = new ArrayList();
		// detailItem.add(b_map.get(property));
		// detailItem.add(a_map.get(property));
		// details.put(property, detailItem);
		// }
		// return details;

		List details = new ArrayList();
		Iterator it = fieldsSet.iterator();
		while (it.hasNext()) {
			String property = it.next() + "";
			List detailItem = new ArrayList();
			detailItem.add(property);
			detailItem.add(b_map.get(property));
			detailItem.add(a_map.get(property));
			details.add(detailItem);
		}
		return details;
	}

}
