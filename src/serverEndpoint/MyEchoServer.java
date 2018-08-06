package serverEndpoint;
import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;
import com.google.gson.JsonObject;
import com.dateitem.model.DateItemService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Date;
import java.sql.Timestamp;
import com.msg.model.*;
import org.json.*;



@ServerEndpoint("/MyEchoServer/{myName}/{myRoom}")
public class MyEchoServer {

	
private final static HashMap<String, Set<Session>> roomMapping = new HashMap<>();


	
	@OnOpen
	public void onOpen(@PathParam("myName") String myName, @PathParam("myRoom") Integer room, Session userSession) throws IOException{ 
//		Sessions.add(userSession);
		String myRoom = room.toString();
		//如果原本沒房間, 就創一間然後把使用者的session加入
		
		String text = String.format("Session ID = %s, connected; myName = %s; room = %s", 
		userSession.getId(), myName, room);
		System.out.println(text);
		
		//載入歷史資料 用dateimteno找出該約會的所有聊天 送給進來的人
		//在訊息中加入一個history屬性來區分新訊息與舊訊息
		MsgService msgSvc=new MsgService();
		List<MsgVO> msgList =msgSvc.findByDateItemNo(room);
		for (MsgVO msgVO:msgList){
			String msgHistory=((msgVO.getMsgContent()).substring(0,msgVO.getMsgContent().length()-1))+",\"history\":\"1\"}";
			userSession.getBasicRemote().sendText(msgHistory);
			System.out.println(msgHistory);
			if (msgVO.getMsgStatus()==1){
				userSession.getBasicRemote().sendText("全部已讀");
			}
		}
		
		List<MsgVO> unreadList= msgSvc.unreadList(Integer.parseInt(myName));
		for(MsgVO msgVO:unreadList){
			msgVO.setMsgStatus(1);
			msgSvc.updateMsgByVO(msgVO);
			}

		
		if (!roomMapping.containsKey(myRoom)){
			Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
			sessions.add(userSession);
			(roomMapping).put(myRoom,sessions);
			}else{
		//如果本來有房間但使用者沒在裡面, 就直接加他進去
				if (!(roomMapping.get(myRoom)).contains(userSession)){
				roomMapping.get(myRoom).add(userSession);
				}
			}
		if (roomMapping.get(myRoom).size()>1){
		for (Session session : roomMapping.get(myRoom)) {
			session.getAsyncRemote().sendText("全部已讀");
			}
		}
		}
						

//		userSession.getBasicRemote().sendText("WebSocket 連線成功");


	
	@OnMessage
	public void onMessage(@PathParam("myName") String myName ,Session userSession, String message, @PathParam("myRoom") Integer room) throws JSONException {
		String myRoom=room.toString();
		System.out.println(roomMapping.get(myRoom).size());
		for (Session session : roomMapping.get(myRoom)) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
		int sendNo = Integer.parseInt(myName);
		int dateItemNo = room;
		DateItemService dSvc = new DateItemService();
		int recNo = dSvc.findTheOtherMem(sendNo, dateItemNo);
		Timestamp msgTime= new Timestamp(System.currentTimeMillis());
		System.out.println(message);
		MsgService msgSvc=new MsgService();
		msgSvc.addMsg(sendNo, recNo, dateItemNo, message, msgTime, 0);
		if (roomMapping.get(myRoom).size()>1){
			List<MsgVO> getAllForThisRoom=msgSvc.findByDateItemNo(dateItemNo);
			for(MsgVO msgVO:getAllForThisRoom){
				msgVO.setMsgStatus(1);
				msgSvc.updateMsgByVO(msgVO);
				}
			for (Session session : roomMapping.get(myRoom)) {
				session.getAsyncRemote().sendText("全部已讀");
			}
		}
	}
	
	@OnError
	public void onError(Session userSession, @PathParam("myRoom") Integer room,Throwable e){
//		e.printStackTrace();
		String myRoom = room.toString();
		roomMapping.get(myRoom).remove(userSession);
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason,@PathParam("myRoom") Integer room) {
		String myRoom=room.toString();
		roomMapping.get(myRoom).remove(userSession);
	}

 
}