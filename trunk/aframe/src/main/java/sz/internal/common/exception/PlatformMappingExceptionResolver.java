package sz.internal.common.exception;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import sz.internal.common.util.Constant;
import sz.internal.common.util.HttpUtil;
import sz.internal.common.util.JsonUtil;
import sz.internal.common.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: wanggang
 * Date: 12/21/15
 * Time: 11:18 AM
 */
public class PlatformMappingExceptionResolver extends SimpleMappingExceptionResolver {
    private static Logger logger = Logger.getLogger(PlatformMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);
        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            if (request.getHeader("accept") == null
                    || !(request.getHeader("accept").contains("application/json")
                    || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
                //not json calling
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                handlerBusinessErrorMessage(ex, request);
                return getModelAndView(viewName, ex, request);
            } else {
                //json calling
                HttpUtil.responseInfo(response, getMessageInfo(ex), getErrorCode(ex));
                return new ModelAndView();
            }
        } else {
            return null;
        }
    }

    @Override
    protected String determineViewName(Exception ex, HttpServletRequest request) {
        if (ex instanceof BusinessException) {
            String url = (String) request.getAttribute(Constant.VALID_ERROR_URL);
            if (url != null && !url.trim().equals("")) {
                return url;
            }
        }
        return super.determineViewName(ex, request);
    }

    public void handlerBusinessErrorMessage(Exception e, HttpServletRequest request) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            MessageInfos errorInfos = be.getErrorInfos();
            request.setAttribute("business_errors", errorInfos);
        }
    }

    private int getErrorCode(Exception ex) {
        if (ex instanceof BusinessException) {
            MessageInfo messageInfo = ((BusinessException) ex).getMessageInfo();
            if (messageInfo != null) {
                return messageInfo.getErrorCode();
            }
        }
        return HttpServletResponse.SC_BAD_REQUEST;
    }

    private String getMessageInfo(Exception ex) {
        String msg = ex.getMessage();
        String[] args = new String[0];
        if (ex instanceof BusinessException) {
            args = ((BusinessException) ex).getMessageArg();
        }
        String str = MessageUtil.getLocaleMessage(msg, args);

        MessageInfo info = new MessageInfo(str);
        return JsonUtil.writeValue(info);
    }
}
