package cn.tedu.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PaymentUtil;

public class CallBack extends HttpServlet {
	
	private static Properties prop= null;
	static{
		prop = new Properties();
		try {
			prop.load(CallBack.class.getClassLoader().getResourceAsStream("merchantInfo.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		
		String hmac = request.getParameter("hmac");
		String KeyValue = prop.getProperty("KeyValue");
		
		boolean flag = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, 
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order,
				r7_Uid, r8_MP, r9_BType, KeyValue);
		
		if("1".equals(r9_BType)){
			OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
			service.updatePaystateByOrderId(r6_Order,1);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("<h1>付款完成，等待第三方支付平台支付成功与否的信息。。</h1>");
		}else if("2".equals(r9_BType)){
			System.out.println("付款成功");
			OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
			service.updatePaystateByOrderId(r6_Order,1);
			//回复第三方支付平台
			response.getWriter().print("SUCCESS");
		}else{
			System.out.println("数据被篡改了");
		}
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
