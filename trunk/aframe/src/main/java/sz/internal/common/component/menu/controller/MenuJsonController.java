package sz.internal.common.component.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.component.menu.MenuMgr;
import sz.internal.common.component.menu.service.MenuService;
import sz.internal.common.component.menu.to.MenuTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/json/menu")
public class MenuJsonController extends BaseController {

	private MenuService menuService;
	
	private MenuService getMenuService() {
        if (menuService == null) {
        	menuService = SpringContextHolder.getBean("sz.internal.common.component.menu.service.MenuService");
        }
        return menuService;
    }

    @RequestMapping("/getMenus/{caption}")
    public Object getMenus(@PathVariable String caption) throws Exception {
        return MenuMgr.showDirectory(caption);
    }

    @RequestMapping("/getMenuList/{caption}")
    public Object getMenuList(@PathVariable String caption) throws Exception {
        return MenuMgr.menuList(caption);
    }

    @RequestMapping("/getMenusUI/{caption}")
    public Object getMenusUI(@PathVariable String caption) throws Exception {
        MenuTO menuTO = MenuMgr.showDirectory(caption);
        return convertMenuTO(menuTO);
    }

    private Map<String, Object> convertMenuTO(MenuTO menuTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", String.valueOf(menuTO.getId()));
        map.put("url", menuTO.getUrl());
        map.put("name", menuTO.getCaption());
        map.put("icon", menuTO.getStyle());
        List list = menuTO.getList();
        if (list != null && list.size() > 0) {
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (Object aList : list) {
                MenuTO menuTOTmp = (MenuTO) aList;
                mapList.add(convertMenuTO(menuTOTmp));
            }
            map.put("submenus", mapList);
        }
        return map;
    }

    @RequestMapping("/getRootCaptions")
    public Object getRootCaptions() throws Exception {
        MenuTO menuTO = new MenuTO();
        menuTO.setParent_id("0");
        List<MenuTO> list = getMenuService().search(menuTO);
        List<String> rootList = new ArrayList<>();
        for (MenuTO menuTOTmp : list) {
            rootList.add(menuTOTmp.getCaption());
        }
        return rootList;
    }

    @RequestMapping("/findMenu/{id}")
    public Object findMenu(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        MenuTO menuTO = new MenuTO();
        menuTO.setId(uID);
        menuTO = (MenuTO) getMenuService().find(menuTO);
        prepareMenuCode(menuTO);
        return menuTO;
    }

    @RequestMapping(value = "/createMenu")
    public Object createMenu(@RequestBody MenuTO menuTO) throws Exception {
        if ("".equals(menuTO.getPermission())) {
            menuTO.setPermission(null);
        }
        getMenuService().insert(menuTO);
        return menuTO;
    }

    @RequestMapping(value = "/updateMenu")
    public Object updateMenu(@RequestBody MenuTO menuTO) throws Exception {
        if ("".equals(menuTO.getPermission())) {
            menuTO.setPermission(null);
        }
        getMenuService().update(menuTO);
        return menuTO;
    }

    @RequestMapping("/deleteMenu/{id}")
    public Object deleteMenu(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        MenuTO menuTO = new MenuTO();
        menuTO.setId(uID);

        getMenuService().delete(menuTO);
        return "";
    }

    private void prepareMenuCode(MenuTO menuTO) throws Exception {
        if (menuTO == null) {
            return;
        }
        CodeTO codeTO = CodeMgr.getCodeTO(CodeConstant.CODE_TYPE_YES_NO, menuTO.getAvailable());
        menuTO.setAvailable_code(codeTO);
    }
}
