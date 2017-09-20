package com.secrect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import com.mysql.jdbc.Statement;

public class Uits {
	public static final String SQLPATH = "jdbc:mysql://localhost:3306/secrect";
	public static final String SQLUSER = "root";
	public static final String SQLPASS = "root";
	
	public static final String KEY_TOKEN = "token";
	public static final String KEY_ACTION = "action";
	public static final String KEY_TIMELINE = "timeline";

	/*
	 * 用户
	 */
	public static final String KEY_USER_ID = "Uid";
	public static final String KEY_USER = "user";
	public static final String KEY_PASS = "pass";
	public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_EAMIL = "Email";
	
	public static final String KEY_HERD_IMAGE = "herdImage";
	/*
	 * 消息
	 */
	public static final String KEY_MSG_ID = "msgId";
	public static final String KEY_MSG_CONTEXT = "msgContext";
	public static final String KEY_MSG_TITLE = "msgTitle";
	public static final String KEY_MSG_NUM_STARS = "msgNumStars";
	public static final String KEY_MSG_NUM_COMMENT = "msgNumComment";
	public static final String KEY_MSG_TIME = "msgTime";
	public static final String KEY_MSG_IMAGE = "msgImage";
	
	/*
	 * 评论
	 */
	public static final String KEY_CONTENT = "content";
	
	
	
	//public static final String KEY_CONTENT = "content";
	public static final String KEY_PAGE = "page";
	public static final String KEY_PERPAGE = "perpage";
	
	/*
	 * 公共
	 */
	public static final String KEY_DATE = "date";
	
	public static final String KEY_STATUS = "status";

	/*
	 * 上传标签
	 */
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_TIMELINE = "timeline";
	public static final String ACTION_REGISTERED = "registered";
	public static final String ACTION_GET_COMMENT = "get_comment";
	public static final String ACTION_PUB_COMMENT = "pub_comment";
	public static final String ACTION_PUBLISH = "publish";

	public static final int SUCCESS= 1;
	public static final int FAIL = 0;
	public static final int FAIL_TWO = 3;
	public static final int ERROR = 2;
	
	public static final String QUERY = "query";
	public static final String UPDATA = "updata";
	
	
	public 	static ResultSet mySqlLink(String sql, String tpye) throws SQLException, ClassNotFoundException{
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver"); 
		connection = DriverManager.getConnection(SQLPATH, SQLUSER, SQLPASS);
		
		Statement statement = (Statement) connection.createStatement();
		ResultSet resultSet = null;
		int x;
		switch (tpye) {
		case QUERY:
			resultSet = statement.executeQuery(sql);
			return resultSet;

		case UPDATA:
			x = statement.executeUpdate(sql);
			break;
		}
		
		return null;
		
	}

	/**
	 * 登录
	 * @param user
	 * @param pass
	 * @return
	 */
	public static int loginCheak(String user, String pass){
		
		String sql = "SELECT Uuser_name,Uuser_pass FROM UserPass";

		ResultSet resultSet;
		try {
			resultSet = mySqlLink(sql, QUERY);
			

			while(resultSet.next()){

				if(pass != null && user != null){
					if(user.equals(resultSet.getString("Uuser_name"))){
						if(pass.equals(resultSet.getString("Uuser_pass"))){
							return SUCCESS;
						}else {
							return FAIL;
						}
					}
				}else 
					return FAIL;	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return ERROR;
	}
	
	/**
	 * 加载消息
	 * @return
	 */
	public static String TimeLineMsgMySql(){
		String sql = "SELECT userpass.Uid,Mid,DherdImage,Uuser_name,Mtitle,Mcontext,Mtime,Mimage,Mnum_stars,Mnum_comment FROM Message,DataUser,userpass WHERE userpass.Uid=message.Uid=datauser.Duser_id ORDER BY Mtime DESC";
		try {
			
			ResultSet resultSet = mySqlLink(sql, QUERY);
		
			JSONObject json = new JSONObject();
			String strJson = "";
			while(resultSet.next()){
				json.put(KEY_USER_ID, resultSet.getString("Uid"));
				json.put(KEY_MSG_ID, resultSet.getString("Mid"));
				json.put(KEY_HERD_IMAGE, resultSet.getString("DherdImage"));
				json.put(KEY_USER, resultSet.getString("Uuser_name"));
				json.put(KEY_MSG_TITLE, resultSet.getString("Mtitle"));
				json.put(KEY_MSG_CONTEXT, resultSet.getString("Mcontext"));
				json.put(KEY_MSG_NUM_STARS, resultSet.getString("Mnum_stars"));	
				json.put(KEY_MSG_NUM_COMMENT, resultSet.getString("Mnum_comment"));	
				json.put(KEY_MSG_TIME, resultSet.getString("Mtime"));
				json.put(KEY_MSG_IMAGE, resultSet.getString("Mimage"));
				strJson +=json.toString() + ",";
			}
			strJson = strJson.substring(0, strJson.length()-1);
			return strJson;		
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 注册
	 * @param user
	 * @param pass
	 * @param phoneNumber
	 * @param Email
	 * @return
	 */
	public static int RegisteredMySql(String user, String pass, String phoneNumber, String Email){

		String sql = "SELECT Uuser_name,Uuser_pass,Uphone FROM UserPass";
		
		try {
		
			ResultSet resultSet = mySqlLink(sql, QUERY);
		
			while(resultSet.next()){
	
				if(pass != null && user != null && phoneNumber != null){
					if(user.equals(resultSet.getString("Uuser_name"))){
						return FAIL;
					}
					if(phoneNumber.equals(resultSet.getString("Uphone"))){
						return FAIL_TWO;
					}
				}		
			}
					
			String date = getDate();
			//创建用户账户	
			String sql2 = "INSERT INTO `UserPass`(`Uuser_name`, `Uuser_pass`, `Uphone`, `Uemal`, `UregisterTime`) VALUES ('"+user+"','"+pass+"',"+phoneNumber+",'"+Email+"','"+date+"')";
			mySqlLink(sql2, UPDATA);  
			//获取用户ID
			String sql3 = "SELECT Uid FROM userpass WHERE Uuser_name in ('"+user+"')";
			resultSet = mySqlLink(sql3, QUERY);
			//填充用户资料
			resultSet.next();
			String sql4 = "INSERT INTO datauser(Duser_id,Duser_name) VALUES ("+ resultSet.getInt("Uid") +",'"+ user +"')" ;
			mySqlLink(sql4, UPDATA); 
			return SUCCESS;		
		
		} catch (SQLException e) {
	
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return ERROR;

	}
	/**
	 * 获取评论
	 * @param Mid
	 * @return
	 */
	public static String CommentMsgMySql(String Mid){
		String sql = "SELECT * FROM ccomment where  CmsgId IN (select Mid from message where Mid = '"+Mid+"' )ORDER BY Cdate DESC";
		//HashMap<Object, Object> date = new HashMap<>();
		//HashMap<String, String> map = new HashMap<>();
		//Msg msg = new Msg();
		try {
			ResultSet resultSet = mySqlLink(sql, QUERY);
			JSONObject json = new JSONObject();
			String strJson = "";
			while(resultSet.next()){
				
				if(resultSet.getString("Ccontent") != null){
					json.put(KEY_MSG_CONTEXT, resultSet.getString("Ccontent"));
					json.put(KEY_USER, resultSet.getString("Cuser"));
					json.put(KEY_DATE, resultSet.getString("Cdate"));	
					strJson +=json.toString() + ",";
				}
			}
			if(strJson != ""){
				strJson = strJson.substring(0, strJson.length()-1);
				return strJson;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 添加评论
	 * @param user
	 * @param msgId
	 * @param content
	 * @return
	 */
	public static int PubCommentMySql(String user, String msgId, String content){
		System.out.println("msgId:"+msgId);
		String date = getDate();
		String sql3 = "'"+user+"','"+content+"','"+date+"','"+msgId+"'";
		String sql4 = "INSERT INTO `ccomment`(`Cuser`, `Ccontent`, `Cdate`, `CmsgId`) VALUES ("+sql3 +")";
			try {
				mySqlLink(sql4, UPDATA);
			
				return SUCCESS;
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		return ERROR;
	}	
	/**
	 * 添加消息
	 * @param user
	 * @param msg
	 * @return
	 */
	public static int PubMsgMySql(String user, String msgTitle, String msgContext){
		
	//	List<Msg> data = new ArrayList<>();

		String sql1 = "SELECT Dnum_message,Duser_id FROM datauser where Duser_name in ('"+user+"');";
		
		try {
			ResultSet resultSet = mySqlLink(sql1, QUERY);
			int num_message = 0;
			int user_id = 0;
			while(resultSet.next()){
				num_message = Integer.parseInt(resultSet.getString("Dnum_message"));
				user_id = Integer.parseInt(resultSet.getString("Duser_id"));	
			}
			
			System.out.println("用户ID:"+user_id+"\n用户消息数:"+num_message);
			
			String date = getDate();
			String imagePath = null;
			String sql2 = "INSERT INTO message(Uid,Mtitle,Mcontext,Mtime,Mimage) VALUES('"+user_id+"','"+msgTitle+"','"+msgContext+"','"+date+"','"+imagePath+"')";
			mySqlLink(sql2, UPDATA);
			
			num_message++;
			String sql3 = "UPDATE datauser SET Dnum_message='"+num_message+"' WHERE Duser_id='"+user_id+"'";
			mySqlLink(sql3, UPDATA);  

			return SUCCESS;
			
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return ERROR;
	}
	
	
	public static String getDate(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		
		return dateString;
	}
}
