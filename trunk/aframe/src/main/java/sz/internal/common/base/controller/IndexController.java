package sz.internal.common.base.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class IndexController {
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model, @RequestParam(value ="langType", defaultValue="en") String langType) {
		if (!model.containsAttribute("contentModel")) {
			if (langType.equals("en")) {
				Locale locale = new Locale("en", "US");
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			} else if (langType.equals("zh")) {
				Locale locale = new Locale("zh", "CN");
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale); 
			} else
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
		}
		return "forward:index.jsp";
	}
}
