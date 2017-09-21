package com.secrect;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecrectServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6537233611988454599L;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8"); 
        request.setCharacterEncoding("utf-8"); 
        PrintWriter out = response.getWriter();

		out.println("...OK");
        out.flush();
        out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8"); 
        request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter(Uits.KEY_ACTION);	
		
		String user = request.getParameter(Uits.KEY_USER);
		String pass = request.getParameter(Uits.KEY_PASS);
		
		System.out.println("wwww"+action);
		
		switch (action) {
		//登陆
		case Uits.ACTION_LOGIN:
			
			int p = Uits.loginCheak(user, pass);
			if(p == Uits.SUCCESS){
				out.print("{\"status\":1,\"token\":\"asdfdsgfdf\"}");
			}else if(p == Uits.FAIL){
				out.print("{\"status\":0}");
			}else{
				out.print("{\"status\":2}");
			}
			break;
		//加载消息列表
		case Uits.ACTION_TIMELINE:
			
			String json = Uits.TimeLineMsgMySql();
			System.out.println("@%@#"+json);
			out.print("{\"status\":1,\"page\":1,\"perpage\":20,\"timeline\":["+json+"]}");
			break;
		//注册
		case Uits.ACTION_REGISTERED:
	
			
			String phoneNumber = request.getParameter(Uits.KEY_PHONENUMBER);
			String Email = request.getParameter(Uits.KEY_EAMIL); 
			int k = Uits.RegisteredMySql(user, pass, phoneNumber, Email);
			if(k == Uits.SUCCESS){
				out.print("{\"status\":1,\"token\":\"asdfdsgfdf\"}");
				
			}else if(k == Uits.FAIL){
				out.print("{\"status\":0}");
			}else if(k == Uits.FAIL_TWO){
				out.print("{\"status\":3}");
			}else{
				out.print("{\"status\":2}");
			}
			break;	
		//加载评论
		case Uits.ACTION_GET_COMMENT: 
			
			String Mid = request.getParameter(Uits.KEY_MSG_ID);
			String comement = Uits.CommentMsgMySql(Mid);
			if(comement != null){
				out.print("{\"status\":1,\"page\":1,\"perpage\":20,\"msgId\":\""+Mid+"\",\"comments\":["+comement+"]}");
			}else {
				out.print("{\"status\":3}");
			}
		
			break;
		//发布评论
		case Uits.ACTION_PUB_COMMENT:
			String content = request.getParameter(Uits.KEY_CONTENT);
			String msgId = request.getParameter(Uits.KEY_MSG_ID);
		
			int pubComment = Uits.PubCommentMySql(user, msgId, content);
			if(pubComment == Uits.SUCCESS){
				out.print("{\"status\":1}");
			}else{
				out.print("{\"status\":0}");
			}
			
			break;
		//发布消息
		case Uits.ACTION_PUBLISH:
			
			String msg = request.getParameter(Uits.KEY_MSG_CONTEXT);
			String a = "zhes biaot";
			int pubMsg = Uits.PubMsgMySql(user,a,msg);
			if(pubMsg == Uits.SUCCESS){
				out.print("{\"status\":1}");
			}else{
				out.print("{\"status\":0}");
			}
			
			break;
		//上传头像
		case Uits.ACTION_UPLOAD_HEAD:
			String img = request.getParameter(Uits.KEY_HERD_IMAGE);
			
			if(Uits.UqloadIamgeMySql(user, img) == Uits.SUCCESS){
				out.print("{\"status\":1,\""+Uits.KEY_HERD_IMAGE+"\":\""+img+"\"}");
			}
			
			break;
			
		default:
				out.print("{\"status\":qingzhuchu action}");
		}
	}

}
