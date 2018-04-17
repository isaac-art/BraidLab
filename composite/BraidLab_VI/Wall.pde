class Wall{

	int tileSize;
	ArrayList<Tile> tiles;
	int[][] buffer;

	Wall(){
		setup();
	}

	public void setup(){
		tiles = new ArrayList<Tile>();

		tileSize = width/numTiles;
		int stp = 0;
		for(int x = 0; x<numTiles; x++){
			for(int y = 0; y<numThreads; y++){
				tiles.add(new Tile(x,y,stp,this));
				stp++;
			}
		}

		buffer = new int[numTiles][numThreads];
	}

	public void update(){
		int co = 0;
		for(int x = 0; x<numTiles; x++){
			for(int y = 0; y<numThreads; y++){
				int st = tiles.get(co).state;
				buffer[x][y] = st;
				co++;
			}
		}

		for(int i = 0; i < tiles.size(); i++){
			tiles.get(i).update();
		}
	}

	public void draw(PGraphics pg){
		pg.pushMatrix();
		pg.translate(0, 80);
		int co = 0;
		//pg.beginShape(LINES);
		for(int x = 0; x<numTiles; x++){
			for(int y = 0; y<numThreads; y++){
				int off = 0;
				if(x % 2 == 0)off = tileSize/2;
				float xx = x * tileSize; 
				float yy = y * tileSize + off;
				pg.pushMatrix();
				pg.translate(xx, yy);
			
				//get this tile
				Tile c = tiles.get(co);

				//draw grid
				if(drawGrid){	
					pg.strokeWeight(1);
					pg.stroke(245);

					if(c.state == 0)pg.fill(218);
					if(c.state == 1)pg.fill(56,95,113);
					if(c.state == 2)pg.fill(43,65,98);
					//pg.noFill();
					pg.rect(0, 0, tileSize, tileSize);
				}

				if(drawBraid){
					//draw braid cell
					pg.pushMatrix();
					pg.pushStyle();
					pg.noFill();
					pg.strokeWeight(2);
					//stroke(c.c);
					stroke(0);
					
					if(c.state == 0){
						pg.line(c.threadA[0].x, c.threadA[0].y,c.threadA[1].x, c.threadA[1].y);
						pg.line(c.threadB[0].x, c.threadB[0].y,c.threadB[1].x, c.threadB[1].y);
					}else if(c.state == 1){
						pg.line(c.threadA[0].x, c.threadA[0].y,c.threadA[1].x, c.threadA[1].y);
						pg.pushStyle();
						pg.strokeWeight(0);
						pg.stroke(218);
						pg.fill(218);
						pg.rect(tileSize/3,tileSize/3,tileSize/3,tileSize/3);
						pg.popStyle();
						pg.line(c.threadB[0].x, c.threadB[0].y,c.threadB[1].x, c.threadB[1].y);
					}else if(c.state == 2){
						pg.line(c.threadB[0].x, c.threadB[0].y,c.threadB[1].x, c.threadB[1].y);
						pg.pushStyle();
						pg.strokeWeight(0);
						pg.stroke(218);
						pg.fill(218);
						pg.rect(tileSize/3,tileSize/3,tileSize/3,tileSize/3);
						pg.popStyle();
						pg.line(c.threadA[0].x, c.threadA[0].y,c.threadA[1].x, c.threadA[1].y);
					}
					pg.popStyle();
					pg.popMatrix();

				}
				pg.popMatrix();
				//update counter
				co++;
			}	

		}
		//pg.endShape();
		pg.popMatrix();



	}



}