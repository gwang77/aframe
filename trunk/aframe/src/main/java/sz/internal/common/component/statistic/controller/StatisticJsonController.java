package sz.internal.common.component.statistic.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.statistic.StatisticUtil;
import sz.internal.common.component.statistic.service.StatisticService;
import sz.internal.common.component.statistic.to.StatisticTO;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/json/statistic")
public class StatisticJsonController extends BaseController {

    private StatisticService statisticService;

    private StatisticService getStatisticService() {
        if (statisticService == null) {
            statisticService = SpringContextHolder.getBean("statisticService");
        }
        return statisticService;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/searchStatistic")
    public Object searchStatistic() throws Exception {
        StatisticUtil.syncToDB();
        List<StatisticTO> list = getStatisticService().search(null);
        return list;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/statisticDownload")
    public void download(HttpServletResponse response) throws Exception {
        response.setContentType("application/msexcel");
        response.addHeader("Content-Disposition", "attachment;filename=" + "Statistic.xls");
        List<StatisticTO> list = getStatisticService().search(null);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            getStatisticService().writeExcelStatistic(os, list);
        } catch (Exception e) {
            throw e;
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
