package sz.internal.common.component.statistic;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.component.statistic.service.StatisticService;
import sz.internal.common.component.statistic.to.StatisticTO;

import java.util.*;

public class StatisticUtil {

    private static final Logger logger = Logger.getLogger(StatisticUtil.class);

    private static Map<String, StatisticTO> increase_map;
    private static Map<String, Date> increase_datetime_map;
    private static int sync_mins = 1;

    private static StatisticService statisticService;

    private static StatisticService getStatisticService() {
        if (statisticService == null) {
            statisticService = SpringContextHolder.getBean("statisticService");
        }
        return statisticService;
    }

    public static synchronized void updateCount(String item_key, String item_desc, int inc_count, int inc_user_count) throws Exception {
        if (increase_map == null) {
            increase_map = new HashMap<>();
            increase_datetime_map = new HashMap<>();
        }
        StatisticTO statisticTO = increase_map.get(item_key);
        if (statisticTO == null) {
            statisticTO = new StatisticTO();
            statisticTO.setItem_key(item_key);
            statisticTO.setItem_desc(item_desc);
            statisticTO.setItem_count(0);
            statisticTO.setItem_user_count(0);
            increase_map.put(item_key, statisticTO);
            Date dt = new Date();
            increase_datetime_map.put(item_key, dt);
        }
        statisticTO.setItem_count(statisticTO.getItem_count() + inc_count);
        statisticTO.setItem_user_count(statisticTO.getItem_user_count() + inc_user_count);
        Date dt = increase_datetime_map.get(item_key);
        Date curr_dt = new Date();
        if (curr_dt.getTime() - dt.getTime() > sync_mins * 60 * 1000) {
            syncItemToDB(item_key, item_desc, statisticTO, curr_dt);
        }
    }

    private static synchronized void syncItemToDB(String item_key, String item_desc, StatisticTO statisticTO, Date curr_dt) throws Exception {
        updateCountToDB(item_key, item_desc, statisticTO.getItem_count(), statisticTO.getItem_user_count());
        statisticTO.setItem_count(0);
        statisticTO.setItem_user_count(0);
        increase_datetime_map.put(item_key, curr_dt);
    }

    private static void updateCountToDB(String item_key, String item_desc, int inc_count, int inc_user_count) throws Exception {
        if (StringUtils.isEmpty(item_key)) {
            logger.error("no item_key");
            return;
        }
        StatisticTO searchTO = new StatisticTO();
        searchTO.setItem_key(item_key);
        List list = getStatisticService().search(searchTO);
        if (list == null || list.size() == 0) {
            StatisticTO statisticTO = new StatisticTO();
            statisticTO.setItem_key(item_key);
            statisticTO.setItem_desc(item_desc);
            statisticTO.setItem_count(1);
            statisticTO.setItem_user_count(1);
            statisticTO.setSeq_no(100);
            statisticService.insert(statisticTO);
            //update seq_no
            StatisticTO statisticTOTmp = new StatisticTO();
            statisticTOTmp.setId(statisticTO.getId());
            statisticTOTmp = (StatisticTO) statisticService.find(statisticTOTmp);
            statisticTOTmp.setSeq_no(statisticTO.getId());
            statisticTOTmp.setAmendList(new String[]{"id", "seq_no"});
            statisticService.update(statisticTOTmp);
        } else {
            StatisticTO statisticTO = (StatisticTO) list.get(0);
            getStatisticService().increaseCount(statisticTO.getId(), inc_count, inc_user_count);
        }
    }

    public static synchronized void syncToDB() throws Exception {
        if (increase_map == null) {
            return;
        }
        Set<String> key_set = increase_map.keySet();
        Date curr_dt = new Date();
        for (String key : key_set) {
            StatisticTO statisticTO = increase_map.get(key);
            if (statisticTO == null) {
                continue;
            }
            syncItemToDB(statisticTO.getItem_key(), statisticTO.getItem_desc(), statisticTO, curr_dt);
        }
    }
}
