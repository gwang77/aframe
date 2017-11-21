package sz.internal.common.component.menu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.menu.service.MenuService;
import sz.internal.common.component.menu.to.MenuTO;
import sz.internal.common.util.Constant;

@Controller
@RequestMapping("/menu")
public class MenuController<menuTO> extends BaseController {
	private MenuService menuService;
	
	private MenuService getMenuService() {
        if (menuService == null) {
        	menuService = SpringContextHolder.getBean("sz.internal.common.component.menu.service.MenuService");
        }
        return menuService;
    }

	
	@RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
	public String menuFind(@PathVariable String id, HttpServletRequest request) throws Exception {
		int uID = Integer.parseInt(id);
		MenuTO menuTO = new MenuTO();
		menuTO.setId(uID);
		menuTO = getMenuService().findMenuTO(menuTO);
		request.setAttribute("menuTO", menuTO);
		return "common/component/menu/view";
	}

	@RequestMapping("/{id}/createInit")
	public String createInit(@PathVariable String id,HttpServletRequest request) {
		int uID = Integer.parseInt(id);
		MenuTO menuTO =  new MenuTO();
		menuTO.setAvailable(CodeConstant.YES_NO_YES);
		menuTO.setId(uID);
		request.setAttribute("menuTO",menuTO);
		return "common/component/menu/create";
	}
	@RequestMapping("/createInits")
	public String createInits(HttpServletRequest request){
		MenuTO menuTO =  new MenuTO();
		
		menuTO.setParent_id("0");
		request.setAttribute("menuTO",menuTO);
		return "common/component/menu/create";
	}

	@RequestMapping("/menuCreate")
	public String create(@Valid @ModelAttribute("menuTO") MenuTO menuTO, BindingResult result, HttpServletRequest request) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("menuTO", menuTO);
			return "common/component/menu/create";
		}
		request.setAttribute("test", menuTO);
		request.setAttribute(Constant.VALID_ERROR_URL, "common/create");
		if ("".equals(menuTO.getPermission())){
			menuTO.setPermission(null);
		}
		getMenuService().insert(menuTO);
		return "redirect:/menu/menuSearch";
	}

	@RequestMapping("/{id}/edit")
	public String updateInit(@PathVariable String id, HttpServletRequest request) throws Exception {
		int uID = Integer.parseInt(id);
		MenuTO menuTO = new MenuTO();
		menuTO.setId(uID);
		request.setAttribute(Constant.VALID_ERROR_URL, "redirect:/menu/MenuSearch");
		menuTO = getMenuService().findMenuTO(menuTO);
		request.setAttribute("menuTO", menuTO);
		return "common/component/menu/update";
	}

	@RequestMapping("/menuUpdate")
	public String menuUpdate(@Valid @ModelAttribute("menuTO") MenuTO menuTO, BindingResult result, HttpServletRequest request) throws Exception {
		if (result.hasErrors()) {
			request.setAttribute("menuTO", menuTO);
			return "common/component/menu/update";
		}
		if ("".equals(menuTO.getPermission())){
			menuTO.setPermission(null);
		}
		getMenuService().update(menuTO);
		return "redirect:menuSearch";
	}

	@RequestMapping(value = "/{id}/delete")
	public String delete(@PathVariable String id, HttpServletRequest request) throws Exception {
		int uID = Integer.parseInt(id);
		MenuTO menuTO = new MenuTO();
		menuTO.setId(uID);
		request.setAttribute(Constant.VALID_ERROR_URL, "redirect:/common/Search");
		getMenuService().delete(menuTO);
		return "redirect:/menu/menuSearch";
	}

	@RequestMapping("/menuSearch")
	public String menuSearch(MenuTO menuTO,  HttpServletRequest request) throws Exception {

        String caption = menuTO.getCaption();
//        if(StringUtils.isEmpty(caption)){
//        caption = "root_en";
//       }
      request.setAttribute("caption", caption);
      menuTO.setCaption(null);
      menuTO.setParent_id("0");
       List<MenuTO> RootList = getMenuService().search(menuTO);
        request.setAttribute("RootList", RootList);
        return "common/component/menu/list";

     }
}




