package sz.internal.common.component.auditlog.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.base.to.PageTO;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.component.auditlog.service.AuditLogService;
import sz.internal.common.component.auditlog.to.AuditLogTO;

@Controller
@RequestMapping("/auditLog")
public class AuditLogController extends BaseController {

	private AuditLogService auditLogService;
	
	private AuditLogService getAuditLogService() {
        if (auditLogService == null) {
        	auditLogService = SpringContextHolder.getBean("auditLogService");
        }
        return auditLogService;
    }

	@RequestMapping("/auditLogSearch")
	public String auditLogSearch(AuditLogTO auditLogTO, PageTO pageTO, Model model, HttpServletRequest request) throws Exception {
		String sDate = auditLogTO.getStart_date();
		String eDate = auditLogTO.getEnd_date();
		if (sDate != null && eDate != null) {
			String[] sArr = sDate.split("-");
			String[] eArr = eDate.split("-");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long result = (sdf.parse(eDate).getTime() - sdf.parse(sDate).getTime()) / (24 * 60 * 60 * 1000);
			if (result > 30) {
				this.addBusinessError(request, "err.auditLog.time.interval");
			}
		}
		model.addAttribute("auditLog", auditLogTO);
		String className = auditLogTO.getClass_nameLike();
		String action = auditLogTO.getActionLike();
		if (className != null || action != null || sDate != null || eDate != null) {
			pageTO = getAuditLogService().searchPage(auditLogTO, pageTO);
		}
		model.addAttribute("pageTO", pageTO);

		List<CodeTO> actionList = new ArrayList<CodeTO>();
		CodeTO create = new CodeTO();
		create.setCode_id("Create");
		create.setCode_desc("Create");
		actionList.add(create);

		CodeTO update = new CodeTO();
		update.setCode_id("Update");
		update.setCode_desc("Update");
		actionList.add(update);

		CodeTO delete = new CodeTO();
		delete.setCode_id("Delete");
		delete.setCode_desc("Delete");
		actionList.add(delete);
		model.addAttribute("actionList", actionList);

		return "common/component/auditlog/auditLogList";
	}

}
