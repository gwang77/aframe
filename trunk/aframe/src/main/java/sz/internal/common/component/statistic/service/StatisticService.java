package sz.internal.common.component.statistic.service;

import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import sz.internal.common.base.service.BaseService;
import sz.internal.common.component.statistic.mapper.StatisticMapper;
import sz.internal.common.component.statistic.to.StatisticTO;
import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Component
public class StatisticService extends BaseService {
	private static Logger logger = Logger.getLogger(StatisticService.class);
	
    @Resource(name = "sz.internal.common.mapper.StatisticMapper")
    public void setMapper(StatisticMapper mapper) {
        super.setMapper(mapper);
    }
    
    //write
    public void writeExcelStatistic(OutputStream outStream, List<StatisticTO> sList) throws Exception {
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(outStream);
            if (wwb == null) {
                return;
            }
            for(StatisticTO sTO:sList){
            	WritableSheet sheet = getWritableSheet(wwb,"statistic");   
            	writeStatisticToSheet(sheet, sTO);
            }
            wwb.write();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (wwb != null) {
                try {
                    wwb.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
    
    private WritableSheet getWritableSheet(WritableWorkbook wwb, String category) throws Exception {
        WritableSheet sheet = wwb.getSheet(category);
        if (sheet == null) {
            sheet = wwb.createSheet(category, wwb.getSheets().length);
            initialSheet(sheet, category);
        }
        return sheet;
    }
    
    private void initialSheet(WritableSheet ws, String category) throws Exception {
        ws.setColumnView(0, 20);
        ws.setColumnView(1, 15);
        ws.setColumnView(2, 15);
        Label lc = new Label(0, 0, "Statistic List");
        ws.addCell(lc);
        String[] header = new String[]{"功能", "访问次数", "访问人数"};
        for (int i = 0; i < header.length; i++) {
            Label labelC = new Label(i, 2, header[i]);
            ws.addCell(labelC);
        }
    }

    private void writeStatisticToSheet(WritableSheet ws, StatisticTO statisticTO) throws Exception {
        WritableCellFormat cellFormat_vTop = new WritableCellFormat();
        cellFormat_vTop.setVerticalAlignment(VerticalAlignment.TOP);

        int row = ws.getRows();
        Label labelC0 = new Label(0, row, statisticTO.getItem_desc(), cellFormat_vTop);
        ws.addCell(labelC0);
        Label labelC2 = new Label(1, row, statisticTO.getItem_count_s(), cellFormat_vTop);
        ws.addCell(labelC2);
        Label labelC3 = new Label(2, row, statisticTO.getItem_user_count_s(), cellFormat_vTop);
        ws.addCell(labelC3);
        
    }

    public int increaseCount(int id, int inc_item_count, int inc_item_user_count) throws Exception {
        return ((StatisticMapper) getMapper()).increaseCount(id, inc_item_count, inc_item_user_count);
    }

    public int increaseCountByItemKey(String item_key, int inc_item_count, int inc_item_user_count) throws Exception {
        return ((StatisticMapper) getMapper()).increaseCountByItemKey(item_key, inc_item_count, inc_item_user_count);
    }
}
