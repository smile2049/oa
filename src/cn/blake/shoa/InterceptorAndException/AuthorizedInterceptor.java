package cn.blake.shoa.InterceptorAndException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.blake.shoa.domain.User;

public class AuthorizedInterceptor implements HandlerInterceptor {
	/** ���岻��Ҫ���ص����� */
	private static final String[] IGNORE_URI = { "/oa/login_", "oa/login_",
			"/login_", "login_", "login", "/login", "oa/login", "/oa/login",
			"/oa/addUser_", "/getSysManageLoginCode", "/checkimagecode" };

	/**
	 * �÷�����ҪpreHandle�����ķ���ֵΪtrueʱ�Ż�ִ�С� �÷������������������֮��ִ�У���Ҫ����������������Դ��
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	/**
	 * ���������preHandle��������ֵΪtrue��ʱ��Ż�ִ�С� ִ��ʱ�����ڴ��������д���֮
	 * ��Ҳ������Controller�ķ�������֮��ִ�С�
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		/** Ĭ���û�û�е�¼ */
		boolean flag = false;
		/* ����Ĭ�ϵ�Servlet path */
		String servletPath = request.getServletPath();
		/** �ж������Ƿ���Ҫ���� */
		for (String str : IGNORE_URI) {
			if (servletPath.contains(str)) {
				flag = true;
				break;
			}
		}
		/** �������� */
		if (!flag) {
			/** 1.��ȡsession�е��û� */
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			/** 2:�ж��û��Ƿ��Ѿ���½ */
			if (user != null) {
				flag = true;
			} else {// û�е�¼
				request.setAttribute("error", "no login");
				request.getRequestDispatcher(
						"/WEB-INF/views/system/login/login.jsp").forward(
						request, response);
			}
		}
		return flag;
	}

}