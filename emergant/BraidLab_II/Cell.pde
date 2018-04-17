class Cell {

	Braid parent;
	int state;
	int thisStep;
	PVector[] threadA = new PVector[2];
	PVector[] threadB = new PVector[2];
	PVector[] threadC = new PVector[2];
	PVector[] threadD = new PVector[2];

	Cell(int step, int st, Braid _parent) {
		parent = _parent; 
		thisStep = step;
		update();
	}

	int fact(int mem){
		if(mem == 0 || mem == 1)return 7;

		for (int i = 2; i*i<= mem; i++) {   
	        if (mem % i == 0){
	            return i;
	        }
	    }
    	return mem;
	}

	public void update(){
		int memory = 0;
		int st = 13;
		//the position of this cell in the braid is further along 
		//than the memory of previous cells needed
		if(thisStep > memoryLength){
			//for the length of the memory (e.g. 3 cells back) 
			//get the cell state add add them together
			for(int i = 1; i < memoryLength; i++){
				Cell c = parent.cells.get(thisStep-i);
				int memSt = c.state;
				memory+=memSt;
			}
			memory += CO;

			int newSt = fact(memory);
			if(newSt > 13) newSt=13;
			st = newSt;
		}else{
			int r = round(random(1, 6));
			switch (r) {
				case 1:
					st = 2;
					break;
				case 2:
					st = 3;
					break;
				case 3:
					st = 5;
					break;
				case 4:
					st = 7;
					break;
				case 5:
					st = 11;
					break;
				case 6:
					st = 13;
					break;
			}
		}
		setState(st);
	}

	public void setState(int st){
		state = st;

		switch (state) {
			case 2:
				//2 bottom
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,100);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,300);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,200);

				break;
			case 3:
				//3 bottom
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,100);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,300);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,200);

				break;
			case 5:
				//5 mid
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,200);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,100);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);

				break;
			case 7:
				//7 mid 
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,200);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,100);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);

				break;
			case 11:
				//11 top
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,100);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,0);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,200);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);
				break;
			case 13:
				//13 top
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,100);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,0);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,200);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);
				break;
		}

	}

}