package coding1208;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class Calendar3 {
	/*
	6
	1 1 2 test
	1 1 3 ppp
	1 2 2 test
	1 2 3 xxxx  //ADD 2번 UID의  3GROUP에 XXXX일정을 등록 
	4 2 2 //2번 UID의 TASK(일정)가 등록되어 있는 개수 
	4 1 2 //1번 UID의 TASK(일정)가 등록되어 있는 개수
	2 1 ppp  //Delete 1번 ppp task 를 지운다
	*/
	
	int EVENT = 0;
	int ADD = 1; 
	int DELETE = 2;
	int CHANGE = 3;	
	int GETCOUNT = 4; 
	
	static UID[] uidList = new UID[1000];
	static GROUP[] groupList = new GROUP[100];
	
	@Test
	public void test() throws FileNotFoundException {
		FileInputStream ip = new FileInputStream("src\\coding1208\\input.txt");
		Scanner sc = new Scanner(ip);
		int c = sc.nextInt();
		
		for (int i = 0 ; i < c ; i++) {
			EVENT = sc.nextInt();
			if (EVENT == ADD) {
				int uid = sc.nextInt();
				int groupid = sc.nextInt();
				char[] task = sc.next().toCharArray();
				System.out.println(uid + " : " + groupid + " : " + new String(task));				
				if ( uidList[uid] == null ){
					uidList[uid] = new UID();
				}				
				uidList[uid].addEvent(uid, groupid, task);
				
				//GROUP 관리 따로 //이건 전역
				if (groupList[groupid] == null ) {
					groupList[groupid] = new GROUP(groupid);
				}
				groupList[groupid].addGroup(task,uidList[uid].getTask(task));				
				groupList[groupid].print();
				
			}else if(EVENT == GETCOUNT){
				int uid = sc.nextInt();
				int oriResult = sc.nextInt();
				int returnResult = uidList[uid].getTaskCount();
				if (oriResult == returnResult) {
					System.out.println(uid + " ID에 속한 TASK갯수 " + oriResult + " 맞습니다");
				}else{
					System.out.println(uid + " ID에 속한 TASK개수 " + returnResult + " 틀립니다 ,원본 " + oriResult);
				}				
				
			}else if(EVENT == DELETE) {
				int uid = sc.nextInt();
				char[] oriResult = sc.next().toCharArray();
				System.out.println("DELETE 를 수행합니다");
				uidList[uid].deleteTaskName(oriResult);		
				
			}else if(EVENT == CHANGE) {
				int uid = sc.nextInt();
				char[] oriResult = sc.next().toCharArray();
				char[] changedResult = sc.next().toCharArray();		
				//TASK 를 가져오고 
				UIDTask oriTask = uidList[uid].getTask(oriResult);
				int gID = oriTask.gID;
				uidList[uid].changeTask(oriResult, changedResult);
				uidList[uid].print();
				
				groupList[gID].print();
				groupList[gID].changeTask(oriResult, changedResult);
				groupList[gID].print();
			}
		}
	}
	
	class UID {
		HashMap<String, UIDTask> taskMap= new HashMap();
		int cnt = 0 ;
		
		UID() {}
		
		public void print() {
			Iterator t = taskMap.keySet().iterator();			
			while(t.hasNext()) {
				String key = (String)t.next();
				UIDTask uIDTask = taskMap.get(key);
				System.out.print("uID:" + uIDTask.uID + " gID:" + uIDTask.gID + " TASK:" + new String(uIDTask.task));
			}
			System.out.println();
		}

		public void addEvent(int uID , int gID , char[] taskName) {
			//UID의 TASK 따로 
			UIDTask t = new UIDTask(uID,gID,taskName);
			t.setMaster(true); //이거 어떻게 하지?
			taskMap.put(new String(taskName) , t);
			cnt++;
		}

		public int getTaskCount() {
			return cnt;
		}
		
		public UIDTask getTask(char[] taskName) {
			return taskMap.get(new String(taskName));
		}
		
		public void deleteTaskName(char[] taskName) {
			UIDTask t = taskMap.get(new String(taskName));
			if (t == null) { 
				System.out.println("delete TASK 존재하지않습니다");
				return;
			}
			
			int groupID = t.gID;
			groupList[groupID].deleteTask(taskName);
			taskMap.remove(new String(taskName));			
			cnt--;
		}
		
		public void changeTask(char[] taskName , char[] changeName){
			UIDTask t = taskMap.get(new String(taskName));
			taskMap.remove(new String(taskName));
			taskMap.put(new String(changeName), new UIDTask(t.uID,t.gID, changeName));
		}

	}
	
	class LinkedTask{
		UIDTask head , tail;		
		void add(UIDTask task) { 
			if (head == null) {
				head = task;
				tail = task;
			}else{
				tail.next = task;
				task.prev = tail;
				tail = task;
			}
		}
	}
	
	class UIDTask{
		UIDTask prev, next;
		int uID;
		int gID;
		char[] task;
		boolean isMaster = false;
		public UIDTask(int uID, int gID, char[] task) {
			this.uID = uID;
			this.gID = gID;
			this.task = new char[task.length];
			//copy chars
			for (int i=0;i<task.length;i++) { 
				this.task[i]= task[i];
			}
		}
		
		public void setMaster(boolean master) {
			isMaster = master;
		}
		boolean isMaster(){
			return isMaster;
		}
		
	}
	
	class GROUP{
		HashMap<String, LinkedTask> groupMap= new HashMap();
		int gID;
		public GROUP(int gID) {
			this.gID = gID;
		}

		public void addGroup(char[] taskName, UIDTask t) {
			//존재하지 않는다면,
			LinkedTask gLinked = groupMap.get(new String(taskName));
			if (gLinked == null){
				//Group Hashing 에 대한 Linked 생성 
				LinkedTask ll = new LinkedTask();
				ll.add(t);
				groupMap.put(new String(taskName), ll);
				
				//System.out.println(groupMap.get(new String(taskName)).head.task);
			//존재한다면,	
			}else{
				//해당 Key의 중복 문자열을 지속 넣는다
				LinkedTask ll = groupMap.get(new String(taskName));
				ll.add(t);
			}	
		}

		public LinkedTask getTaskLinked(char[] taskName){
			return groupMap.get(taskName);
		}
		
		public void deleteTask(char[] taskName){
			LinkedTask taskList = groupMap.get(new String(taskName));
			if (taskList != null) {
				//궁금한게 , 이렇게 안에 다 지워버리면, Referece 이니까 모두다 Null 맞음.? 
				UIDTask head = taskList.head;
				if (head.isMaster()) { 
					while (head != null) {
						UIDTask temp = head;
						head = head.next;
						temp = null; //delete
					}
				}
			}
			groupMap.remove(new String(taskName));
		}
		
		public void changeTask(char[] taskName , char[] changeName){
			LinkedTask taskList = groupMap.get(new String(taskName));
			
			if (taskList != null){ 
				UIDTask head = taskList.head;
				while(head !=null){
					//System.out.println(new String(head.task));
					for (int i = 0 ; i < changeName.length ;i++) { 
						head.task[i] = changeName[i];
					}
					//System.out.print(new String(head.task));
					head = head.next;
				}
			}			
			
			groupMap.put(new String(changeName), taskList);			
			groupMap.remove(new String(taskName));//이걸 지우면 taskList 도 지워지냐. 
			
		}	
		
		public void print() {
			Iterator t = groupMap.keySet().iterator();
			System.out.println("GROUP:"+ gID );
			while(t.hasNext()) {
				String key = (String)t.next();
				LinkedTask llTask = groupMap.get(key);
				UIDTask head = llTask.head;
				while(head !=null){
					System.out.println(head.uID + "." + new String(head.task));
					head =head.next;
				}				
			}
		}		
	}

}
