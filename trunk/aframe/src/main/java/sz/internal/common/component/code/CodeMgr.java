package sz.internal.common.component.code;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.component.code.service.CodeIntService;
import sz.internal.common.component.code.to.CodeIntTO;
import sz.internal.common.component.code.to.CodeTO;

import java.util.*;

public class CodeMgr {
    private static final Logger logger = Logger.getLogger(CodeMgr.class);

    private static Map codeMap = new HashMap();
    private static Map codeMap_date = new HashMap();

    private static int expire_min = 10;

    public synchronized static void clearCache() {
        codeMap = new HashMap();
        codeMap_date = new HashMap();
    }

    public synchronized static void clearCache(String codetype_id) {
        clearCache(codetype_id, "en");
        clearCache(codetype_id, "zh");
    }

    public synchronized static void clearCache(String codetype_id, String locale) {
        String codeIntStr = codetype_id + "_" + locale;
        if (codeMap.get(codeIntStr) != null) {
            codeMap.remove(codeIntStr);
            codeMap_date.remove(codeIntStr);
        }
    }

    public synchronized static List<CodeTO> prepareCodeTO(String codetype_id, String locale) throws Exception {
        String codeIntStr = codetype_id + "_" + locale;
        List codeTOList = (List) codeMap.get(codeIntStr);
        Date codeTOList_date = (Date) codeMap_date.get(codeIntStr);
        Date currDate = new Date();
        if (codeTOList != null && codeTOList_date != null && (currDate.getTime() - codeTOList_date.getTime() <= expire_min * 60 * 1000)) {
            List codeListTemp = new ArrayList();
            codeListTemp.addAll(codeTOList);
            return codeListTemp;
        }
        CodeIntService codeintService = (CodeIntService) SpringContextHolder.getBean("sz.internal.common.component.code.service.CodeIntService");
        CodeIntTO codeIntTO = new CodeIntTO();
        codeIntTO.setCodetype_id(codetype_id);
        codeIntTO.setLocales(locale);
        codeIntTO.setStatus(CodeConstant.STATUS_ACTIVE);
        try {
            List<CodeIntTO> codeIntList = codeintService.search(codeIntTO);
            // convert CodeIntTO list to CodeTO list.
            codeTOList = new ArrayList<CodeTO>();
            for (CodeIntTO codeIntTOTmp : codeIntList) {
                CodeTO codeTOs = new CodeTO();
                codeTOs.setCode_type_id(codeIntTOTmp.getCodetype_id());
                codeTOs.setCode_id(codeIntTOTmp.getCode_id());
                codeTOs.setCode_desc(codeIntTOTmp.getCode_desc());
                codeTOs.setCode_seq(codeIntTOTmp.getCode_seq());
                codeTOList.add(codeTOs);
            }
            codeMap.put(codeIntStr, codeTOList);
            codeMap_date.put(codeIntStr, new Date());
            List codeListTemp = new ArrayList();
            codeListTemp.addAll(codeTOList);
            return codeListTemp;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ArrayList();
    }

    public static List searchCodeTO(String codetype_id, String locale) throws Exception {
        List<CodeTO> codeList = new ArrayList<CodeTO>();
        List<CodeTO> code_en = prepareCodeTO(codetype_id, "en");
        List<CodeTO> code_zh = prepareCodeTO(codetype_id, "zh");
        if (StringUtils.isEmpty(locale) || locale.equals("en")) {
            code_zh = getList(code_en, code_zh);
        } else {
            code_en = getList(code_zh, code_en);
        }
        codeList.addAll(code_en);
        codeList.addAll(code_zh);
        Collections.sort(codeList, new SeqComparator());
        return codeList;
    }

    public static List getList(List<CodeTO> codeList1, List<CodeTO> codeList2) {
        for (CodeTO codeTO : codeList1) {
            for (int i = 0; i < codeList2.size(); i++) {
                CodeTO codeTOs = codeList2.get(i);
                if (codeTO.getCode_id().equals(codeTOs.getCode_id())) {
                    codeList2.remove(i);
                    break;
                }
            }
        }
        return codeList2;
    }

    static class SeqComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            CodeTO codeTO1 = (CodeTO) o1;
            CodeTO codeTO2 = (CodeTO) o2;
            return codeTO1.getCode_seq().compareTo(codeTO2.getCode_seq());
        }
    }

    public static List searchCodeTOFilter(String codetype_id, String locale, String id_prefix, String id_suffix) throws Exception {
        List<CodeTO> codeTOList = new ArrayList<CodeTO>();
        List<CodeTO> codeList = searchCodeTO(codetype_id, locale);
        for (CodeTO codeTO : codeList) {
            if ((!StringUtils.isEmpty(id_prefix) && StringUtils.isEmpty(id_suffix) && codeTO.getCode_id().startsWith(id_prefix)) || (!StringUtils.isEmpty(id_suffix) && StringUtils.isEmpty(id_prefix) && codeTO.getCode_id().endsWith(id_suffix))) {
                codeTOList.add(codeTO);
            } else if ((StringUtils.isEmpty(id_prefix) && StringUtils.isEmpty(id_suffix)) || (!StringUtils.isEmpty(id_prefix) && !StringUtils.isEmpty(id_suffix) && codeTO.getCode_id().startsWith(id_prefix) && codeTO.getCode_id().endsWith(id_suffix))) {
                codeTOList.add(codeTO);
            }
        }
        return codeTOList;
    }

    public static String getCodeDesc(String codetype_id, String code_id) throws Exception {
        return getCodeDesc(codetype_id, code_id, "en");
    }

    public static String getCodeDesc(String codetype_id, String code_id, String locale) throws Exception {
        List codeTOList = searchCodeTO(codetype_id, locale);
        for (Object aCodeTOList : codeTOList) {
            CodeTO codeTO = (CodeTO) aCodeTOList;
            if (code_id.equals(codeTO.getCode_id())) {
                return codeTO.getCode_desc();
            }
        }
        return "";
    }

    public static String getCodeID(String codetype_id, String code_desc) throws Exception {
        List codeTOList_en = searchCodeTO(codetype_id, "en");
        List codeTOList_zh = searchCodeTO(codetype_id, "zh");

        for (int i = 0; codeTOList_en != null && i < codeTOList_en.size(); i++) {
            CodeTO codeTO = (CodeTO) codeTOList_en.get(i);
            if (codeTO.getCode_desc().equals(code_desc)) {
                return codeTO.getCode_id();
            }
        }
        for (int i = 0; codeTOList_zh != null && i < codeTOList_zh.size(); i++) {
            CodeTO codeTO = (CodeTO) codeTOList_zh.get(i);
            if (codeTO.getCode_desc().equals(code_desc)) {
                return codeTO.getCode_id();
            }
        }
        return "";
    }

    public static List<CodeTO> getCodes(String code_type_id) throws Exception {
        List<CodeTO> codeList = new ArrayList<CodeTO>();

        List<CodeTO> list_en = CodeMgr.searchCodeTO(code_type_id, "en");
        List<CodeTO> list_zh = CodeMgr.searchCodeTO(code_type_id, "zh");

        for (CodeTO codeTO : list_en) {
            putCode(codeTO, codeList, "en");
        }
        for (CodeTO codeTO : list_zh) {
            putCode(codeTO, codeList, "zh");
        }
        return codeList;
    }

    public static CodeTO getCodeTO(String code_type_id, String code_id) throws Exception {
        List<CodeTO> codeList = getCodes(code_type_id);

        if (code_id == null) {
            return null;
        }
        for (CodeTO codeTO : codeList) {
            if (codeTO.getCode_id().equals(code_id)) {
                return codeTO;
            }
        }
        return null;
    }

    private static void putCode(CodeTO codeTO, List<CodeTO> codeList, String local) {
        String langStr = getLanguageStr(local);
        for (CodeTO codeTOTmp : codeList) {
            if (codeTOTmp.getCode_id().equals(codeTO.getCode_id())) {
                if (codeTOTmp.getLabel() == null) {
                    codeTOTmp.setLabel(new HashMap<String, String>());
                }
                codeTOTmp.getLabel().put(langStr, codeTO.getCode_desc());
                return;
            }
        }
        codeTO.getLabel().put(langStr, codeTO.getCode_desc());
        codeList.add(codeTO);
    }

    private static String getLanguageStr(String local) {
        String[][] LANGUAGE_MAP = new String[][]{
                {"en", "en-US"},
                {"zh", "zh-CN"},
        };
        for (String[] langMap : LANGUAGE_MAP) {
            if (langMap[0].equals(local)) {
                return langMap[1];
            }
        }
        return local;
    }

}