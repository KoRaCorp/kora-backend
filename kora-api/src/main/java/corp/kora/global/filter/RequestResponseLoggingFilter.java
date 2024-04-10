package corp.kora.global.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestResponseLoggingFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest)request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
			(HttpServletResponse)response);

		long start = System.currentTimeMillis();
		chain.doFilter(requestWrapper, responseWrapper);
		long end = System.currentTimeMillis();

		if (is4xx(responseWrapper)) {
			doRequestResponseLoggingByWarn((HttpServletRequest)request, requestWrapper, responseWrapper, start, end);
		} else if (is5xx(responseWrapper)) {
			doRequestResponseLoggingByError((HttpServletRequest)request, requestWrapper, responseWrapper, start, end);
		} else {
			doRequestResponseLoggingByInfo((HttpServletRequest)request, requestWrapper, responseWrapper, start, end);
		}

	}

	private boolean is5xx(ContentCachingResponseWrapper responseWrapper) {
		return responseWrapper.getStatus() >= 500;
	}

	private boolean is4xx(ContentCachingResponseWrapper responseWrapper) {
		return responseWrapper.getStatus() >= 400 && responseWrapper.getStatus() < 500;
	}

	private void doRequestResponseLoggingByInfo(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper,
		ContentCachingResponseWrapper responseWrapper, long start, long end) throws IOException {

		log.info(getLoggingFormat(),
			request.getMethod(),
			request.getRequestURI(),
			responseWrapper.getStatus(),
			(end - start) / 1000.0,
			getHeaders(request),
			getRequestBody(requestWrapper),
			getResponseBody(responseWrapper));

	}

	private void doRequestResponseLoggingByError(HttpServletRequest request,
		ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, long start,
		long end) throws IOException {

		log.error(getLoggingFormat(),
			request.getMethod(),
			request.getRequestURI(),
			responseWrapper.getStatus(),
			(end - start) / 1000.0,
			getHeaders(request),
			getRequestBody(requestWrapper),
			getResponseBody(responseWrapper));

	}

	private void doRequestResponseLoggingByWarn(HttpServletRequest request,
		ContentCachingRequestWrapper requestWrapper,
		ContentCachingResponseWrapper responseWrapper, long start, long end) throws IOException {
		log.warn(getLoggingFormat(),
			request.getMethod(),
			request.getRequestURI(),
			responseWrapper.getStatus(),
			(end - start) / 1000.0,
			getHeaders(request),
			getRequestBody(requestWrapper),
			getResponseBody(responseWrapper));
	}

	private String getLoggingFormat() {
		return "\n" +
			"[REQUEST] {} - {} {} - {}\n" +
			"Headers : {}\n" +
			"Request : {}\n" +
			"Response : {}\n";
	}

	private Map getHeaders(HttpServletRequest request) {
		Map headerMap = new HashMap<>();

		Enumeration headerArray = request.getHeaderNames();
		while (headerArray.hasMoreElements()) {
			String headerName = (String)headerArray.nextElement();
			headerMap.put(headerName, request.getHeader(headerName));
		}
		return headerMap;
	}

	private String getRequestBody(ContentCachingRequestWrapper request) {
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				try {
					return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					return " - ";
				}
			}
		}
		return " - ";
	}

	private String getResponseBody(final HttpServletResponse response) throws IOException {
		String payload = null;
		ContentCachingResponseWrapper wrapper =
			WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
				wrapper.copyBodyToResponse();
			}
		}
		return null == payload ? " - " : payload;
	}
}
