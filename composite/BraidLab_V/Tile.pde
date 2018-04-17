class Tile{

	int state;
	int posX;
	int posY;
	int step;
	PVector[] threadA = new PVector[2];
	PVector[] threadB = new PVector[2];
	Wall parent;
	color c;

	Tile(int x, int y, int stp, Wall _parent){
		parent = _parent;
		step = stp;
		posX = x;
		posY = y;
		c = color(0,0,0);
		
		//random starting position
		// int r = round(random(100));
		// int p = 25;
		// if(r < p){
		// 	state = 1;
		// }else{
		// 	state = 0;
		// }	

		//rythmic startic pos
		// if(stp % 3 == 0){
		// 	state = 1;
		// }
		// else{
		// 	state = 0;
		// }

		//functional starting
		state = round(map(sin(sin(stp)+sin(x)+sin(y)),-1,1,0,1));

		// if(x % 3 == 0 && (y % 3 ==0 || y%6 == 0)){
		// 	state = 1;
		// }
		// else{
		// 	state = 0;
		// }

		setState(state);	
	}

	public void update(){
		int neighbours = 0;
		int st = 0;
		//moore neighboorhood
		for(int x = posX-2; x <= posX+2 ; x++){
			for(int y = posY-2; y <= posY+2; y++){
				if(x>=0 && x<numTiles && y>=0 && y<numThreads){
					if(!(x == posX && y == posY)){
						if(parent.buffer[x][y] == 1){
							neighbours++;
						}
					}
				}
			}
		}
		
		if(parent.buffer[posX][posY]==1){
			if(neighbours < 2 || neighbours > 6){
				st = 0;
			}
		}
		else{
			if(neighbours == 2 || neighbours == 3 || neighbours == 4 || neighbours == 5 || neighbours == 6){
				st = 1;
			}
		}
		setState(st);
	}

	public void setState(int st){

		state = st;
		int sz = parent.tileSize/4;

		if(st == 0){
			c = color(0);
		}
		else if (st == 1) {
			c = color(60,120,220);	
		}else if(st == 2) {
			c = color(20,20,200);
		}

		switch (st) {
			case 0:
				threadA[0] = new PVector(0,sz);
				threadA[1] = new PVector(parent.tileSize,sz);
				threadB[0] = new PVector(0,sz*3);
				threadB[1] = new PVector(parent.tileSize,sz*3);
				break;
			case 1:
				threadA[0] = new PVector(0,sz);
				threadA[1] = new PVector(parent.tileSize,sz*3);
				threadB[0] = new PVector(0,sz*3);
				threadB[1] = new PVector(parent.tileSize,sz);
				break;	
			case 2:
				threadA[0] = new PVector(0,sz);
				threadA[1] = new PVector(parent.tileSize,sz*3);
				threadB[0] = new PVector(0,sz*3);
				threadB[1] = new PVector(parent.tileSize,sz);
				break;	
		}

	}

}