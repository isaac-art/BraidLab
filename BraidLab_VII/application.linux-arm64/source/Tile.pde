class Tile{

	int state;
	int posX;
	int posY;
	int step;
	PVector[] threadA = new PVector[2];
	PVector[] threadB = new PVector[2];
	WallTD parent;
	color c;

	Tile(int x, int y, int stp, WallTD _parent){
		parent = _parent;
		step = stp;
		posX = x;
		posY = y;
		if(stp % (numThreads-2) == 0){
			int r = round(random(1,2));
			state = r;
		}
		else{
			state = 0;
		}

		setState(state);	
	}

	public void update(){
		int neighbours = 0;
		int st = 0;

		for(int x = posX-neighbourhood; x <= posX+neighbourhood ; x++){
			for(int y = posY-neighbourhood; y <= posY+neighbourhood; y++){
				if(x>=0 && x<numTiles && y>=0 && y<numThreads){
					if(!(x == posX && y == posY)){
						if(parent.buffer[x][y] == 1 || parent.buffer[x][y] == 2){
							neighbours++;
						}
					}
				}
			}
		}
		
		if(parent.buffer[posX][posY]==1){
			if(neighbours < 2 || neighbours > neighbourhood+2){
				st = 0;
			}
		}
		else{
			if(neighbourhood == 1){
				if(neighbours == 2 || neighbours == 3 || neighbours == 4 ){
					int r = 0;
			          if(neighbours == 2 || neighbours == 4) {
			            r = 1;
			          }else{
			            r = 2;
			          }
					st = r;
				}
			}else if (neighbourhood > 1) {
				if(neighbours == 2 || neighbours == 3 || neighbours == 4 || neighbours == 5 || neighbours == 6){
					int r = 0;
			          if(neighbours ==2 ||  neighbours == 4 || neighbours == 6){
			            r = 1;
			          }else{
			            r = 2;
			          }
			          st = r;
				}
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
			c = color(20,220,200);
		}

		switch (st) {
			case 0:
				threadA[0] = new PVector(0,sz,0);
				threadA[1] = new PVector(parent.tileSize,sz,0);
				threadB[0] = new PVector(0,sz*3,0);
				threadB[1] = new PVector(parent.tileSize,sz*3,0);
				break;
			case 1:
				threadA[0] = new PVector(0,sz,0);
				threadA[1] = new PVector(parent.tileSize,sz*3,0);
				threadB[0] = new PVector(0,sz*3,0);
				threadB[1] = new PVector(parent.tileSize,sz,0);
				break;	
			case 2:
				threadA[0] = new PVector(0,sz,0);
				threadA[1] = new PVector(parent.tileSize,sz*3,0);
				threadB[0] = new PVector(0,sz*3,0);
				threadB[1] = new PVector(parent.tileSize,sz,0);
				break;	
		}

	}

}