package sz.internal.common.component.menu.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.component.menu.mapper.MenuMapper;
import sz.internal.common.component.menu.to.MenuTO;
import sz.internal.common.util.ConfigPropertyUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component("sz.internal.common.component.menu.service.MenuService")
public class MenuService extends BaseService {
    private static final Logger logger = Logger.getLogger(MenuService.class);

    @Resource(name = "sz.internal.common.component.menu.mapper.MenuMapper")
    public void setMapper(MenuMapper mapper) {
        super.setMapper(mapper);
    }

    public MenuTO findMenuTO(MenuTO menuTO) throws Exception {
        MenuTO to = (MenuTO) find(menuTO);
        prepareMenuCode(to);
        return to;
    }

    public MenuTO showMenu(String caption) throws Exception {
        MenuTO menuTO = new MenuTO();
        menuTO.setCaption(caption);
        menuTO.setParent_id("0");
        List<MenuTO> list = search(menuTO);
        if (list == null || list.size() == 0) {
            String[] arrayCaption = caption.split("_");
            caption = arrayCaption[0] + "_en";
            menuTO.setCaption(caption);
            menuTO.setParent_id("0");
            list = search(menuTO);
        }

        if (list != null && list.size() > 0) {
            menuTO = list.get(0);
            constructMenuTO(menuTO);
            return menuTO;
        }
        return new MenuTO();
    }

    private void constructMenuTO(MenuTO menuTO) throws Exception {
        prepareMenuCode(menuTO);
        MenuTO menuTOTmp = new MenuTO();
        menuTOTmp.setParent_id(String.valueOf(menuTO.getId()));
        List<MenuTO> list = search(menuTOTmp);
        menuTO.setList(list);
        for (int i = 0; list != null && i < list.size(); i++) {
            constructMenuTO(list.get(i));
        }
    }

    public MenuTO showDirectory(String caption) throws Exception {
        return showDirectory(caption, true);
    }

    public MenuTO showDirectory(String caption, boolean filterMenu) throws Exception {
        MenuTO menuTO = showMenu(caption);
        if (filterMenu) {
            filterMenu(menuTO);
        }
        return menuTO;
    }

    private void filterMenu(MenuTO menuTO) {
        if (menuTO == null) {
            return;
        }
        List<MenuTO> list = menuTO.getList();
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            MenuTO menuTOTmp = list.get(i);
            filterMenu(menuTOTmp);
            if ((!StringUtils.isEmpty(menuTOTmp.getPermission()) && !hasPermission(menuTOTmp.getPermission()))
                    || !CodeConstant.YES_NO_YES.equals(menuTOTmp.getAvailable())
                    || (!"-".equals(menuTOTmp.getCaption()) && StringUtils.isEmpty(menuTOTmp.getUrl()) && (menuTOTmp.getList() == null || menuTOTmp.getList().size() == 0))
                    || ("-".equals(menuTOTmp.getCaption()) && i == list.size() - 1)
                    ) {
                list.remove(i);
            }
        }
    }

    private boolean hasPermission(String permission) {
        String debug_ind = ConfigPropertyUtils.getProperties("debug.mode");
        debug_ind = StringUtils.isEmpty(debug_ind) ? "" : debug_ind.trim();
        if ("Y".equalsIgnoreCase(debug_ind) || "YES".equalsIgnoreCase(debug_ind) || "T".equalsIgnoreCase(debug_ind) || "TRUE".equalsIgnoreCase(debug_ind)) {
            return true;
        }
        try {
            Object userBean = SpringContextHolder.getBean("security_user_bean");
            if (userBean == null) {
                return true;
            }
            Method method = userBean.getClass().getMethod("hasPermission", String.class);
            Object obj = method.invoke(userBean, permission);
            return (Boolean) obj;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<MenuTO> menuList(String caption) throws Exception {
        List<MenuTO> list = new ArrayList<MenuTO>();
        MenuTO menuTO = showDirectory(caption, false);
        constructMenuList(list, menuTO, 0);
        return list;
    }

    private void constructMenuList(List<MenuTO> list, MenuTO menuTO, int level) throws Exception {
        menuTO.setLevel(level);
        list.add(menuTO);
        List<MenuTO> subList = menuTO.getList();
        if (subList == null || subList.size() == 0) {
            menuTO.setCanDelete("Y");
        }
        while (subList != null && subList.size() > 0) {
            constructMenuList(list, subList.get(0), level + 1);
            subList.remove(0);
        }
    }

    private void prepareMenuCode(MenuTO menuTO) throws Exception {
        if (menuTO == null) {
            return;
        }
        CodeTO codeTO = CodeMgr.getCodeTO(CodeConstant.CODE_TYPE_YES_NO, menuTO.getAvailable());
        menuTO.setAvailable_code(codeTO);
    }
}
