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
		double executionTimeInSeconds = (end - start) / 1000.0;
		doLogging((HttpServletRequest)request, requestWrapper, responseWrapper, executionTimeInSeconds);

	}

	private void doLogging(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper,
		ContentCachingResponseWrapper responseWrapper, double executionTimeInSeconds) throws IOException {

		int statusCode = responseWrapper.getStatus();

		if (is4xx(statusCode)) {
			doLoggingByWarn(request, requestWrapper, responseWrapper, executionTimeInSeconds);
			return;
		}

		if (is5xx(statusCode)) {
			doLoggingByError(request, requestWrapper, responseWrapper, executionTimeInSeconds);
			return;
		}

		doLoggingByInfo(request, requestWrapper, responseWrapper, executionTimeInSeconds);
	}

	private void doLoggingByInfo(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper,
		ContentCachingResponseWrapper responseWrapper, double executionTimeInSeconds) throws IOException {

		if (!log.isInfoEnabled()) {
			return;
		}

		log.info(getLoggingFormat(),
			request.getRequestURI(),
			request.getMethod(),
			getHeaders(request),
			getRequestBody(requestWrapper),
			responseWrapper.getStatus(),
			responseWrapper.getContentType(),
			getResponseBody(responseWrapper),
			executionTimeInSeconds);
	}

	private void doLoggingByWarn(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper,
		ContentCachingResponseWrapper responseWrapper, double executionTimeInSeconds) throws IOException {

		if (!log.isWarnEnabled()) {
			return;
		}
		log.warn(getLoggingFormat(),
			request.getRequestURI(),
			request.getMethod(),
			getHeaders(request),
			getRequestBody(requestWrapper),
			responseWrapper.getStatus(),
			responseWrapper.getContentType(),
			getResponseBody(responseWrapper),
			executionTimeInSeconds);
	}

	private void doLoggingByError(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper,
		ContentCachingResponseWrapper responseWrapper, double executionTimeInSeconds) throws IOException {

		if (!log.isErrorEnabled()) {
			return;
		}
		log.error(getLoggingFormat(),
			request.getRequestURI(),
			request.getMethod(),
			getHeaders(request),
			getRequestBody(requestWrapper),
			responseWrapper.getStatus(),
			responseWrapper.getContentType(),
			getResponseBody(responseWrapper),
			executionTimeInSeconds);
	}

	private String getLoggingFormat() {
		return """
			\nREQUEST: 
			  uri: {}
			  method: {}
			  headers: {}
			  body: {}
			====================
			RESPONSE:
			  status: {}
			  content-type: {}
			  body: {}
			  execution time: {} (in seconds)
			""";
	}

	private boolean is5xx(int statusCoude) {
		return statusCoude >= 500;
	}

	private boolean is4xx(int statusCode) {
		return statusCode >= 400 && statusCode < 500;
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

	private Map getHeaders(HttpServletRequest request) {
		Map headerMap = new HashMap<>();

		Enumeration headerArray = request.getHeaderNames();
		while (headerArray.hasMoreElements()) {
			String headerName = (String)headerArray.nextElement();
			headerMap.put(headerName, request.getHeader(headerName));
		}
		return headerMap;
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
