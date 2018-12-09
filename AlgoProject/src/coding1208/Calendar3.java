package coding1208;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;
/**
 * <PRE>
 * 1. 자료 구조 정의 및 문제 이해에 시간이 오래걸림
 * 3. 자료 구조만 구현하다보니 , 정확히 이해가 되지 않음 (고민거리들이 잔뜩생김)
 * 4. USER CODE의 함수 호출 흐름으로 빠르게 넘어갔어야. 
 * 5. HASH CHAINING에 대해 응용 하..
 * 6. 심지어 핵심 알고리즘은 손도 못대고 나온거 
 * 7. 하...집에와서 다시 풀어봤지만, 2시간 걸렸음..음
 * 8. Controller 부분(UID / GROUP LIST)을 어디에다가 넣어야 하는지 ..먼가 머리속에 정리 및 이해가 안되어있었다.
 */

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
	
	static UID[] uIDList = new UID[1000];
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
				
				if ( uIDList[uid] == null ){
					uIDList[uid] = new UID();
				}				
				uIDList[uid].addTASK(uid, groupid, task);
				
				//GROUP 관리 따로 //이건 전역
				if (groupList[groupid] == null ) {
					groupList[groupid] = new GROUP(groupid);
				}
				
				UIDTask uIDTask= uIDList[uid].getTask(task);
				groupList[groupid].addGroup(task,uIDTask);
				groupList[groupid].print();
				
			}else if(EVENT == GETCOUNT){
				int uid = sc.nextInt();
				int oriResult = sc.nextInt();
				int returnResult = uIDList[uid].getTaskCount();
				if (oriResult == returnResult) {
					System.out.println(uid + " ID에 속한 TASK갯수 " + oriResult + " 맞습니다");
				}else{
					System.out.println(uid + " ID에 속한 TASK개수 " + returnResult + " 틀립니다 ,원본 " + oriResult);
				}				
				
			}else if(EVENT == DELETE) {
				int uid = sc.nextInt();
				char[] oriResult = sc.next().toCharArray();
				System.out.println(uid + "번 id의 "+ new String(oriResult) + "를 DELETE 수행합니다");				
				
				int groupID = uIDList[uid].getTask(oriResult).gID;
				uIDList[uid].print();
				uIDList[uid].deleteTask(oriResult);
				uIDList[uid].print();
				
				groupList[groupID].print();				
				groupList[groupID].deleteTask(oriResult);				
				groupList[groupID].print();
				
			}else if(EVENT == CHANGE) {
				int uid = sc.nextInt();
				char[] oriResult = sc.next().toCharArray();
				char[] changedResult = sc.next().toCharArray();		
				//TASK 를 가져오고 
				UIDTask oriTask = uIDList[uid].getTask(oriResult);
				int gID = oriTask.gID;
				uIDList[uid].changeTask(oriResult, changedResult);
				uIDList[uid].print();
				
				groupList[gID].print();
				groupList[gID].changeTask(oriResult, changedResult);
				groupList[gID].print();
			}
		}
	}
	
	class UID {
		HashMap<String, UIDTask> taskMap= new HashMap();
		int cnt = 0 ;
		int uID = 99999999 ;
		
		UID() {}
		
		public void print() {
			System.out.println("UID 정보");
			Iterator t = taskMap.keySet().iterator();			
			while(t.hasNext()) {
				String key = (String)t.next();
				UIDTask uIDTask = taskMap.get(key);
				System.out.println("uID:" + uIDTask.uID + " gID:" + uIDTask.gID + " TASK:" + new String(uIDTask.task));
			}			
		}

		public void addTASK(int uID , int gID , char[] taskName) {
			//UID의 TASK 따로 
			this.uID = uID;
			UIDTask t = new UIDTask(uID,gID,taskName);			
			taskMap.put(new String(taskName) , t);
			cnt++;
		}

		public int getTaskCount() {
			return cnt;
		}
		
		public UIDTask getTask(char[] taskName) {
			return taskMap.get(new String(taskName));
		}
		
		public void deleteTask(char[] taskName) {
			UIDTask t = taskMap.get(new String(taskName));
			if (t == null) { 
				System.out.println("delete TASK 존재하지않습니다");
				return;
			}
			
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
				t.setMaster(true);
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
					System.out.println(head.uID + "." + new String(head.task) +" : MASTER: " +head.isMaster());
					head =head.next;
				}				
			}
		}		
	}

}
