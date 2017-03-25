package cn.tedu.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.tedu.domain.Order;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PaymentUtil;

public class PayAction extends HttpServlet {
	private static Properties prop = null;
	static{
		prop = new Properties();
		try {
			prop.load(PayAction.class.getClassLoader()
				.getResourceAsStream("merchantInfo.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		String orderid = request.getParameter("orderid");
		Order order = service.findOrderById(orderid);
		double money = order.getMoney();
		//下面的属性一个都不能是null
		String p0_Cmd = "Buy";
		String p1_MerId = prop.getProperty("p1_MerId");
		String p2_Order = orderid;
		String p3_Amt = /*money+*/0.01+"";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = prop.getProperty("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = request.getParameter("pd_FrpId");
		String pr_NeedResponse = "1";
		String keyValue = prop.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF,
				pa_MP, pd_FrpId, pr_NeedResponse,keyValue);
		//System.out.println(hmac);//96a816376a24c2ac2237b262a220a2fe
		request.setAttribute("pd_FrpId", pd_FrpId);
		request.setAttribute("p0_Cmd", p0_Cmd);
		request.setAttribute("p1_MerId", p1_MerId);
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur", p4_Cur);
		request.setAttribute("p5_Pid", p5_Pid);
		request.setAttribute("p6_Pcat", p6_Pcat);
		request.setAttribute("p7_Pdesc", p7_Pdesc);
		request.setAttribute("p8_Url", p8_Url);
		request.setAttribute("p9_SAF", p9_SAF);
		request.setAttribute("pa_MP", pa_MP);
		request.setAttribute("pr_NeedResponse", pr_NeedResponse);
		request.setAttribute("hmac", hmac);
	
		request.getRequestDispatcher("/confirm.jsp").
			forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
