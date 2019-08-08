package coding1916;
import java.util.ArrayList;
import java.util.HashMap;


class UserSolutionSNS {

	private final static int MAX_USER = 1000;
	private final static int MAX_TAG  = 5000;

	// The below commented functions are for your reference. If you want 
	// to use it, uncomment these functions.
	/*
	int mstrcmp(char[] a, char[] b)
	{
		int i;
		for (i = 0; a[i] != '\0'; ++i) if (a[i] != b[i]) return a[i] - b[i];
		return a[i] - b[i];
	}
	*/
	void mstrcpy(char[] dest, char[] src)
	{
		int i = 0;
		while (src[i] != '\0') { dest[i] = src[i]; i++; }
		dest[i] = src[i];
	}
	
	
	class user { 
		int[] followuser = new int[MAX_USER];
		int fc = 0;
		int[] msg = new int[100];
		int msgc = 0;
		public int id; 
	}
	class msg { 
		int userid;
		int time;
		//list? why? 
		/*
		tag[] taglist = new tag[10];
		msg(){
			for (int i = 0; i < 10; i++) {
				taglist[i] = new tag();
			}
		}
		*/
	}
	//hash tag 에 대한 msg id 쓸대 필요하지 않나.. 음.
	class tag{ 
		char[] hashtag;
		int msgid;
	}
	
	boolean debug = true;
	HashMap<String , ArrayList<Integer>> taghash;
	
	msg[] msgs; 
	user[] users;
	public void init()
	{
		
		taghash = new HashMap();
		msgs = new msg[1000000];
		users = new user[MAX_USER];
		
	}
	
	public void createMessage(int msgID, int userID, char[] msgData)
	{
		if(debug){ 
			System.out.println("createMessage" +  msgID + "," + userID +"," +  new String(msgData) );
		}
		msg msg = new msg();
		msg.userid = userID;
		msg.time = gettime(msgData);
		for (int i = 8; i < msgData.length; i++) {
			
			if (msgData[i] == '#') { 
				char[] tag = new char[10];
				int c = 0 ; 
				for (int j = i; j < i+9 ; j++) {
					if (msgData[j] == ' ' || msgData[j] == '\0') { 
						break;
					}
					tag[c++] = msgData[j];
				}
				
				System.out.println(new String(tag));				
				addhashtag(tag,msgID);	
			}
			//init 에서 미리 백만을 생성할 필요없을듯
			msgs[msgID] = msg;
		}
	}
	
	void addhashtag(char[] tag , int msgID){ 
		ArrayList t= taghash.get(new String(tag));
		if (t==null) {
			t = new ArrayList<Integer>();
		}
		t.add(msgID);
		taghash.put(new String(tag), t);
	}
	
	int gettime(char[] msgdata) {
		int h1  =(msgdata[0] - 48)*100000;
		int h2  =(msgdata[1] - 48)*10000;
		
		int m1  =(msgdata[3] - 48)*1000;
		int m2  =(msgdata[4] - 48)*100;
		
		int s1  =(msgdata[6] - 48)*10;
		int s2  =(msgdata[7] - 48)*1;
		return h1+h2+m1+m2+s1+s2;
	}
	
	public void followUser(int userID1, int userID2)
	{
		user u = new user();
		u.id = userID1;
		u.followuser[u.fc++] = userID2; 
		users[userID1] = u;
	}
	
	public int searchByHashtag(char[] tagName, int[] retIDs)
	{
		if(debug){ 
			System.out.println("searchByHashtag : "  + new String(tagName) );
		}
		
		char[] src = new char[10]; 
		mstrcpy(src, tagName);
		
		ArrayList l = taghash.get(new String(src));
		if (l == null) {
			System.out.println("There is not found ");
			return 0;
		}
		for (int i = 0 ; i < l.size() ; i++) { 
			int msgID = (int)l.get(i);
			System.out.println("msgid = " + msgID + "," + msgs[msgID].time );
		}
		if (l.size()>5) {
			return 5;
		}
		return l.size();
	}
	
	public int getMessages(int userID, int[] retIDs)
	{
		return 0;
	}
}