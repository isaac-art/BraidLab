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

	public void update(){
		int st = 0;
		if(thisStep > memoryLength){
			st = parent.cells.get(thisStep-memoryLength).state;
			//println(st);
		}else{
			st = round(random(0, 5));
 		}

		setState(st);
	}

	public void setState(int st){
		state = st;
		switch (state) {
			case 0:
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,100);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,300);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,200);

				break;
			case 1:
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,100);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,300);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,200);

				break;
			case 2:
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,200);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,100);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);

				break;
			case 3:
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,0);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,200);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,100);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);

				break;
			case 4:
				threadA[0] = new PVector(0,0);
				threadA[1] = new PVector(100,100);

				threadB[0] = new PVector(0,100);
				threadB[1] = new PVector(100,0);

				threadC[0] = new PVector(0,200);
				threadC[1] = new PVector(100,200);

				threadD[0] = new PVector(0,300);
				threadD[1] = new PVector(100,300);
				break;
			case 5:
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