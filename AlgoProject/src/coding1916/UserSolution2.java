
public class UserSolution2 {

	class Hash {
		String key;
		int[] data = new int[100];
		int dc=0;
		boolean delete = false;
		
	}
	
	class Hashtable
	{
		int capacity;
		Hash tb[];
		
		public Hashtable(int capacity){
			this.capacity = capacity;
			tb = new Hash[capacity];
			for (int i = 0; i < capacity; i++){
				tb[i] = new Hash();
			}
		}
		
		private int hash(String str)
		{
			int hash = 5381;
			
			for (int i = 0; i < str.length(); i++)
			{
				int c = (int)str.charAt(i);
				hash = ((hash << 5) + hash) + c;
			}
			if (hash < 0) hash *= -1;
			return hash % capacity;
		}
		
		public Hash find(String key){
			int h = hash(key);
			int cnt = capacity;
			int step = 1;
			while(tb[h].key != null && (--cnt) != 0)
			{
				if (tb[h].delete == false && tb[h].key.equals(key)){
					return tb[h];
				}
				h = (h + 1) % capacity;
			}
			return null;
		}
		
		boolean delete(String key) { 
			int h = hash(key);
			while(tb[h].key != null)
			{
				if (tb[h].key.equals(key)){
					tb[h].delete = true;
					return true;
				}
				h = (h + 1) % capacity;
			}
			return false;
		}
		
		boolean add(String key, int cnt)
		{
			int h = hash(key);
			
			//collision 이 발생하면, 어떻게 다룰것인지 
			while(tb[h].key != null)
			{	
				//기존키에 추가 정보 넣기 위해서..int[] 배열 넣기 위해서..
				if (tb[h].delete == false && tb[h].key.equals(key)){
					tb[h].data[ tb[h].dc++ ] = cnt;
					return true;
				}
				h = (h + 1) % capacity;
			}
			
			//결국 아래에 신규 키를 넣는다
			tb[h].key = key;
			tb[h].data[ tb[h].dc++ ] = cnt;
			return true;
		}
	}

	class record{ 
		String name ; 
		String number;
		String birthday;
		String email;
		String memo;
		boolean isdelete = false;
	}
	
	record[] records;
	int rc  ; //record count
	Hashtable[] tables; 
	void InitDB()
	{
		records = new record[50001];
		rc = 0 ;
		
		tables = new Hashtable[5];
		
		for (int i = 0 ; i <= 4 ; i++){
			tables[i] = new Hashtable(100000);
		}
		
	}
	
	void Add(String name, String number, String birthday, String email, String memo)
	{
		System.out.println(name +"," + number +"," + birthday +"," +email +","+memo );
		records[rc] = new record();
		records[rc].name = name;
		records[rc].number = number;
		records[rc].birthday = birthday;
		records[rc].email = email;
		records[rc].memo = memo;
		
		
		tables[0].add(name, rc);
		tables[1].add(number, rc);
		tables[2].add(birthday, rc);
		tables[3].add(email, rc);
		tables[4].add(memo, rc);
		rc++;
	}
	
	
	int Delete(int field, String str)
	{
		if (str.equals("limyhfoaiwlbtjs")){
			System.out.println("bbb");
		}
		System.out.println("Delete : " + field + " , " + str);
		Hash h = tables[field].find(str); 
		if (h == null ) {
			System.out.println("Delete Not found Key");
			return 0;
		}
		
		//delete flag 로 변경해놓는다.
		int c = 0;
		for (int i = 0; i < h.dc; i++) {
			if ( records[h.data[i]].isdelete == false) { 
				records[h.data[i]].isdelete = true;
				c++;
			}
		}
		
		//key 삭제 ? 나머지는 ? 
		tables[field].delete(str);
		
		return c;
	}

	//field 의 str(키)에 해당하는 records 들을 찾고, 찾은 records 들에 특정 field 의 값을 바꾼다.
	int Change(int field, String str, int changefield, String changestr)
	{
		System.out.println("CHANGE " + str  +" , " + changestr);

		//delete field , str
		Hash h = tables[field].find(str);
		if (h == null ) {
			System.out.println("Change Not found Key");
			return 0;
		}
		//값변경 하고 , 변경된 값을 기존 hash에서 지우고, 신규 hash에 다시 넣는다. 
		int c = 0;
		for (int i = 0; i < h.dc; i++) {
			if ( records[h.data[i]].isdelete == false) {
				
				//delete hash
				tables[changefield].delete(str);
				
				//값 변경
				if (changefield == 0) { 
					records[h.data[i]].name = changestr;
					tables[0].add(changestr, h.data[i]);
				}else if (changefield == 1) { 
					records[h.data[i]].number = changestr;
					tables[1].add(changestr, h.data[i]);
				}else if (changefield == 2) {
					records[h.data[i]].birthday = changestr;
					tables[2].add(changestr, h.data[i]);
				}else if (changefield == 3) {
					records[h.data[i]].email = changestr;
					tables[3].add(changestr, h.data[i]);
				}else if (changefield == 4) {
					records[h.data[i]].memo = changestr;
					tables[4].add(changestr, h.data[i]);
				}
				
				c++;
			}
		}
		
		
		
		return c;
	}
	
	Solution.Result Search(int field, String str, int returnfield)
	{
		System.out.println("Search : " + str + " : " );
		if (str.equals("19710425")){
			System.out.println("aaa");
		}
		
		Solution.Result result = new Solution.Result();
		Hash h = tables[field].find(str);
		if (h == null) { 			
			System.out.println("Search not found key : error ");
			result.count = 0 ;
			return result;
		}
		
		
		int size = h.dc;
		int c = 0 ; 
		for (int i = 0; i < size; i++) {
			if ( records[h.data[i]].isdelete != true ) {
				System.out.println(records[h.data[i]].name);
				//값 변경
				if (returnfield == 0) { 
					result.str = records[h.data[i]].name;				
				}else if (returnfield == 1) { 
					result.str = records[h.data[i]].number;
				}else if (returnfield == 2) {
					result.str = records[h.data[i]].birthday;
				}else if (returnfield == 3) {
					result.str = records[h.data[i]].email;
				}else if (returnfield == 4) {
					result.str = records[h.data[i]].memo;
				}
				c++;
			}
		}
		
		if (c > 1) { 
			result.str = "";
		}
		result.count = c;
		return result;
		
		/*
		if ( h.dc == 1 ) { 
			result.count = 1;
			//값 변경
			if (returnfield == 0) { 
				result.str = records[h.data[0]].name;				
			}else if (returnfield == 1) { 
				result.str = records[h.data[0]].number;
			}else if (returnfield == 2) {
				result.str = records[h.data[0]].birthday;
			}else if (returnfield == 3) {
				result.str = records[h.data[0]].email;
			}else if (returnfield == 4) {
				result.str = records[h.data[0]].memo;
			}		
			
			return result;
		}
		
		result.count = h.dc;		
		return result;
		*/
	}
	
	interface Field 
	{
		public final static int NAME     = 0;
		public final static int NUMBER   = 1;
		public final static int BIRTHDAY = 2;
		public final static int EMAIL    = 3;
		public final static int MEMO     = 4;
	}

}
