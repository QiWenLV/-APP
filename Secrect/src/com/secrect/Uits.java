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
	public static final String SQLPATH = "jdbc:mysql://localhost:3306/talkroom";
	public static final String SQLUSER = "root";
	public static final String SQLPASS = "root";
	
	public static final String KEY_TOKEN = "token";
	public static final String KEY_ACTION = "action";
	public static final String KEY_TIMELINE = "timeline";

	public static final String KEY_USER = "user";
	public static final String KEY_PASS = "pass";
	public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_EAMIL = "Email";
	public static final String KEY_PAGE = "page";
	public static final String KEY_PERPAGE = "perpage";
	public static final String KEY_MSG_ID = "msgId";
	public static final String KEY_MSG = "msg";
	public static final String KEY_DATE = "date";
	public static final String KEY_CONTENT = "content";
	

	public static final String KEY_STATUS = "status";

	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_TIMELINE = "timeline";
	public static final String ACTION_REGISTERED = "registered";
	public static final String ACTION_GET_COMMENT = "get_comment";
	public static final String ACTION_PUB_COMMENT = "pub_comment";
	public static final String ACTION_PUBLISH = "publish";

	public static final int SUCCESS= 1;
	public static final int FAIL = 0;
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
		
		String sql = "SELECT Uuser,Upass FROM uuserdata";

		ResultSet resultSet;
		try {
			resultSet = mySqlLink(sql, QUERY);
			

			while(resultSet.next()){

				if(pass != null && user != null){
					if(user.equals(resultSet.getString("Uuser"))){
						if(pass.equals(resultSet.getString("Upass"))){
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
		String sql = "SELECT * FROM message ORDER BY Mdate DESC";
		try {
			
			ResultSet resultSet = mySqlLink(sql, QUERY);
		
			JSONObject json = new JSONObject();
			String strJson = "";
			while(resultSet.next()){
				json.put(KEY_USER, resultSet.getString("Muser"));
				json.put(KEY_MSG, resultSet.getString("Mmsg"));
				json.put(KEY_DATE, resultSet.getString("Mdate"));
				json.put(KEY_MSG_ID, resultSet.getString("Mid"));	
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

		String sql = "SELECT Uuser,UphoneNumber FROM uuserdata";
		try {
		
			ResultSet resultSet = mySqlLink(sql, QUERY);
		
			while(resultSet.next()){
	
				if(pass != null && user != null && phoneNumber != null){
					if(user.equals(resultSet.getString("Uuser"))){
						return FAIL;
					}else if(phoneNumber.equals(resultSet.getString("UphoneNumber"))){
						return FAIL;
					}
				}		
			}
					
			String date = getDate();
				
			String sql2 = "'"+user+"','"+pass+"','"+phoneNumber+"','"+Email+"','"+date+"','"+0+"','"+0+"','"+0+"'";
			String sql3 = "INSERT INTO `uuserdata`(`Uuser`, `Upass`, `UphoneNumber`, `Uemal`, `UregisterTime`,`UmsgNum`,`UCommentNum`,`UmsgId`) VALUES ("+ sql2 +")";
			System.out.println("+++.."+sql3);
			mySqlLink(sql3, UPDATA);  
			
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
					json.put(KEY_CONTENT, resultSet.getString("Ccontent"));
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
	public static int PubMsgMySql(String user, String msg){
		
	//	List<Msg> data = new ArrayList<>();

		String sql1 = "SELECT UmsgNum,Uid,UmsgId FROM `uuserdata` WHERE Uuser IN ('"+user+ "');";
		
		try {
			ResultSet resultSet = mySqlLink(sql1, QUERY);
			int msgNum = 0;
			int userId = 0;
			int UmsgId = 0;
			while(resultSet.next()){
				msgNum = Integer.parseInt(resultSet.getString("UmsgNum"));
				userId = Integer.parseInt(resultSet.getString("Uid"));
				UmsgId = Integer.parseInt(resultSet.getString("UmsgId"));
	
			}
			int MsgId = userId *10000 + UmsgId + 1;
			
			System.out.println("消息ID:"+MsgId+"\n用户ID:"+userId+"\n用户消息数:"+msgNum);
			
			String date = getDate();
			String sql3 = "'"+MsgId+"','"+user+"','"+msg+"','"+date+"'";
			String sql4 = "INSERT INTO message(`Mid`,`Muser`,`Mmsg`,`Mdate`) VALUES ("+sql3+")";
			mySqlLink(sql4, UPDATA);
			
			msgNum++;
			UmsgId++;
			String sql5 = "UPDATE uuserdata SET `UmsgNum`= '"+msgNum+"',`UmsgId`= '"+UmsgId+"'WHERE Uuser IN ('"+user+"')";
			mySqlLink(sql5, UPDATA);  

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
