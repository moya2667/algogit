
//solution code 
class UserSolution {
	// Main API :
	//   Solution.calcHash(char buf[], int pos, int len)

	private final static int MAXSERVER   = 5;
	private final static int IMAGESZE    = 400000;

	private final static int MAXSIZE 	 = 16;
	
	private final static int MAXBLOCK	 = 15015;
	private final static int MAXCHILD    = 20;
	
	class Transaction {
		char exchangedid;
		int amount;
	}
	 
	class Block {
		int hashpreblock;
		int random;
		char size;
		Transaction trans[];
		
		int hash;
		int hit;
		boolean available;
		int next;
		
		int parent;
		int nchild;
		int child[];
		
		Block() {
			trans = new Transaction[MAXSIZE];
			for (int i = 0; i < MAXSIZE; ++i)
				trans[i] = new Transaction();
			child = new int[MAXCHILD];
		}
	}
	
	static final int MOD = 1 << 16;
	
	int nb;
	Block block[];
	int htable[];
	
	UserSolution() {
		block = new Block[MAXBLOCK];
		for (int i = 0; i < MAXBLOCK; ++i)
			block[i] = new Block();
		htable = new int[MOD];
	}
	
	int getInt(char image[], int p) {
		return (image[p] << 24) + (image[p + 1] << 16) + (image[p + 2] << 8) + image[p + 3];
	}
	
	int getShort(char image[], int p) {
		return (image[p] << 8) + image[p + 1];
	}
	
	char getChar(char image[], int p) {
		return image[p];
	}
	
	int get(char image[], int p, Block block) {
		block.hashpreblock = getInt(image, p); p += 4;
		block.random = getShort(image, p); p += 2;
		block.size = getChar(image, p); p += 1;
		for (int i = 0; i < block.size; ++i) {
			block.trans[i].exchangedid = getChar(image, p); p += 1;
			block.trans[i].amount = getShort(image, p); p += 2;
		}
		return p;
	}
	
	int getblockid(int hash) {
		if (hash == 0)
			return 0;
		
		int b = htable[hash % MOD];
		while (b != 0) {
			if (block[b].hash == hash)
				return b;
			b = block[b].next;
		}

		return -1;
	}
	
	int dfs(int id, int exchangeid) {
		int ret = 0;
		
		for (int i = 0; i < block[id].size; ++i)
			if (exchangeid == block[id].trans[i].exchangedid) {
				ret += block[id].trans[i].amount;
			}
		
		for (int i = 0; i < block[id].nchild; ++i)
			ret += dfs(block[id].child[i], exchangeid);
				
		return ret;
	}
	
	public void syncBlockChain(int S, char blockchainimage[][]) {
		block[0].nchild = 0;
		block[0].available = true;
		
		nb = 1;
		
		for (int i = 0; i < MOD; ++i)
			htable[i] = 0;
		
		//block 을 생성하고, hash 에 저장하는 루틴.
		for (int s = 0; s < S; ++s) {
			int len, p = 0;
			len = getInt(blockchainimage[s], p); p += 4;
			while(p < len + 4) {
				int pp = p;
				p = get(blockchainimage[s], p, block[nb]);
				block[nb].hash = Solution.calcHash(blockchainimage[s], pp, p - pp);
				int id = getblockid(block[nb].hash);
				if (id == -1) {
					int h = block[nb].hash % MOD;
					block[nb].next = htable[h];
					htable[h] = nb;
					block[nb].hit = 1;
					block[nb].nchild = 0;
					++nb;
				} else {
					++block[id].hit;
				}
			}
		}
		
		for (int i = 0; i < MOD; ++i) {
			int b = htable[i];
			while(b != 0) {
				block[b].available = block[b].hit > S / 2;
				b = block[b].next;
			}
		}
		
		for (int i = 0; i < MOD; ++i) {
			int b = htable[i];
			while(b != 0) {
				if (block[b].available) {
					block[b].parent = getblockid(block[b].hashpreblock);
					block[block[b].parent].child[block[block[b].parent].nchild++] = b;
				}
				b = block[b].next;
			}
		}
	}
	
	public int calcAmount(int hash, int exchangeid) {
		int id = getblockid(hash);
		
		return dfs(id, exchangeid);
	}
}