package sz.internal.common.component.menu;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.component.menu.service.MenuService;
import sz.internal.common.component.menu.to.MenuTO;

import java.util.List;

public class MenuMgr {

    public static MenuTO showMenu(String caption) throws Exception {
        MenuService menuService = (MenuService) SpringContextHolder.getBean("sz.internal.common.component.menu.service.MenuService");
        return menuService.showMenu(caption);
    }

    public static MenuTO showDirectory(String caption) throws Exception {
        MenuService menuService = (MenuService) SpringContextHolder.getBean("sz.internal.common.component.menu.service.MenuService");
        return menuService.showDirectory(caption);
    }

    public static List<MenuTO> menuList(String caption) throws Exception {
        MenuService menuService = (MenuService) SpringContextHolder.getBean("sz.internal.common.component.menu.service.MenuService");
        return menuService.menuList(caption);
    }

}
