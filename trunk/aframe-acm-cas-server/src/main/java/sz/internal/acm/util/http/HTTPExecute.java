package sz.internal.acm.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import sz.internal.acm.util.JsonUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class HTTPExecute {
    private static final Logger logger = Logger.getLogger(HTTPExecute.class);

    public String get(String url) {
        return get(url, null);
    }

    public String get(String url, Map headerMap) {
        HttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        prepareHeader(httpGet, headerMap);
        return sendRequest(httpClient, httpGet);
    }

    public String post(String url, Map<String, String> params) {
        return post(url, params, null);
    }

    public String post(String url, Map<String, String> params, Map headerMap) {
        HttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        prepareHeader(httpPost, headerMap);
        prepareForm(httpPost, params);
        return sendRequest(httpClient, httpPost);
    }

    public String put(String url, Map<String, String> params) {
        return put(url, params, null);
    }

    public String put(String url, Map<String, String> params, Map headerMap) {
        HttpClient httpClient = getHttpClient();
        HttpPut httpPut = new HttpPut(url);
        prepareHeader(httpPut, headerMap);
        prepareForm(httpPut, params);
        return sendRequest(httpClient, httpPut);
    }

    public String delete(String url) {
        return delete(url, null);
    }

    public String delete(String url, Map headerMap) {
        HttpClient httpClient = getHttpClient();
        HttpDelete httpDelete = new HttpDelete(url);
        prepareHeader(httpDelete, headerMap);
        return sendRequest(httpClient, httpDelete);
    }

    public String sendRequest(HttpClient httpClient, HttpUriRequest httpRequest) {
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return parseResponse(response);
    }

    private String parseResponse(HttpResponse response) {
        if (response == null) {
            return "";
        }
        HttpEntity entity = response.getEntity();
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return body;
    }

    private void prepareHeader(HttpRequestBase request, Map headerMap) {
        if (headerMap == null) {
            return;
        }
        Set<Map.Entry> set = headerMap.entrySet();
        for (Map.Entry entry : set) {
            request.setHeader((String) entry.getKey(), (String) entry.getValue());
        }
    }

    private static HttpEntityEnclosingRequestBase prepareForm(HttpEntityEnclosingRequestBase requestBase, Map<String, String> params) {
        try {
            StringEntity entity = new StringEntity(JsonUtils.writeValue(params), HTTP.UTF_8);
            entity.setContentType("application/json");
            requestBase.setEntity(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return requestBase;
    }

    public HttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
    }
}
