class Braid  {

	ArrayList<Cell> cells;
	PVector position;
	PVector scale;

	Braid() { 
		position = new PVector(0,100);	
		setup();
	}

	public void setup() {
		float sc = 10/float(braidLength);
		scale = new PVector(sc, sc, sc);
		cells = new ArrayList<Cell>();
		for(int i = 0; i < braidLength; i++){
			cells.add(new Cell(i,0,this));
		}
	}

	public void update(){
		for(int i = 0; i < cells.size(); i++){
			cells.get(i).update();
		}
	}

	public void draw(PGraphics pg){

		pushTransform(pg);
		stroke(0);
		strokeWeight(3);

		pg.pushMatrix();
		pg.beginShape(LINES);
		for(int i = 0; i < cells.size(); i++){
			Cell c = cells.get(i);

			if(c.thisStep < memoryLength){
				stroke(218,0,0);
			}else{
				stroke(0);
			}

			pg.vertex(c.threadA[0].x, c.threadA[0].y);
			pg.vertex(c.threadA[1].x, c.threadA[1].y);
			pg.translate(100, 0);
		}
		pg.endShape();
		pg.popMatrix();

		pg.pushMatrix();
		pg.beginShape(LINES);
		for(int i = 0; i < cells.size(); i++){
			Cell c = cells.get(i);

			if(c.thisStep < memoryLength){
				stroke(218,0,0);
			}else{
				stroke(0);
			}

			pg.vertex(c.threadB[0].x, c.threadB[0].y);
			pg.vertex(c.threadB[1].x, c.threadB[1].y);
			pg.translate(100, 0);
		}
		pg.endShape();
		pg.popMatrix();

		pg.pushMatrix();
		pg.beginShape(LINES);
		for(int i = 0; i < cells.size(); i++){
			Cell c = cells.get(i);

			if(c.thisStep < memoryLength){
				stroke(218,0,0);
			}else{
				stroke(0);
			}

			pg.vertex(c.threadC[0].x, c.threadC[0].y);
			pg.vertex(c.threadC[1].x, c.threadC[1].y);
			pg.translate(100, 0);
		}
		pg.endShape();
		pg.popMatrix();

		pg.pushMatrix();
		pg.beginShape(LINES);
		for(int i = 0; i < cells.size(); i++){
			Cell c = cells.get(i);

			if(c.thisStep < memoryLength){
				stroke(218,0,0);
			}else{
				stroke(0);
			}


			pg.vertex(c.threadD[0].x, c.threadD[0].y);
			pg.vertex(c.threadD[1].x, c.threadD[1].y);
			pg.translate(100, 0);
		}
		pg.endShape();
		pg.popMatrix();

		popTransform(pg);	
	}


	public void pushTransform(PGraphics pg){
		pg.pushMatrix();
		pg.translate(position.x, position.y);
		pg.scale(scale.x, scale.y);
	}

	public void popTransform(PGraphics pg){
		pg.popMatrix();
	}

}