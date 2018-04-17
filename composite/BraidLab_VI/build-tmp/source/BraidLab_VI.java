import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import nervoussystem.obj.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BraidLab_VI extends PApplet {




ControlP5 cp5;

int braidLength;
int numTiles;
int memoryLength;
int numThreads;
int speed;
int neighbourhood;
boolean gui = true;
boolean auto = true; 
boolean dontLoop = true;
boolean playpause = false;
boolean drawGrid = false;
boolean drawBraid = true;
Textlabel fps;
Textlabel start;
Textlabel pattern;
Wall theWall;

public void setup() {
	  
  	setupGUI();
  	//theBraid = new Braid();
    theWall = new Wall();
}
 
public void draw() {
    background(218);
    if(playpause){
    	if(frameCount % speed == 0){
    		//theBraid.update();
        theWall.update();

    	}
    }

    //theBraid.draw(this.g);
    theWall.draw(this.g);
    if(gui)drawGUI();


}

public void reset(){
	//theBraid.setup();
  theWall.setup();
}

public void setupGUI(){
	  cp5 = new ControlP5(this);
  	cp5.setAutoDraw(false);
  	start = new Textlabel(cp5,"start",10,height-18,memoryLength*5,16);
  	pattern = new Textlabel(cp5,"pattern",memoryLength*5+155,height-18,width,16);
  	fps = new Textlabel(cp5,"fps",10,10,128,16);

  	cp5.addSlider("numTiles")
  		.setLabel("num tiles")
     	.setId(1)
     	.setPosition(70,10)
     	.setSize(128,16)
     	.setValue(30)
     	.setRange(5,120);


    cp5.addSlider("neighbourhood")
      .setLabel("neighbourhood")
      .setId(4)
      .setPosition(70,30)
      .setSize(128,16)
      .setValue(2)
      .setRange(1,4);

  	cp5.addSlider("numThreads")
  		.setLabel("num thread pairs")
     	.setId(2)
     	.setPosition(280,10)
     	.setSize(128,16)
     	.setValue(12)
     	.setRange(2,60);

    cp5.addSlider("speed")
  		.setLabel("speed")
     	.setId(3)
     	.setPosition(500,10)
     	.setSize(128,16)
     	.setValue(15)
     	.setRange(5,120);

  	cp5.addToggle("playpause").setPosition(700,10).setSize(40,16);
     cp5.addToggle("drawGrid").setLabel("drawCells").setPosition(750,10).setSize(40,16);
     cp5.addToggle("drawBraid").setLabel("drawBraid").setPosition(800,10).setSize(40,16);
}


public void drawGUI(){
	cp5.draw();
	fps.setValueLabel("fps: " + floor(frameRate));
	fps.draw(this);	
}

public void keyPressed(){
	if(key == 's' || key == 'S'){
		save("S-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
	}
	if(key == 'g' || key == 'G'){
		gui = !gui;
	}
	if(key == 'n' || key == 'N'){
		//theBraid.setup();
    theWall.setup();
    //theWall.update();
	}
  if(key == ' ' ){
    theWall.update();
  }
	if(key == 'r' || key == 'R'){
		reset();
	}
}


public void exportMesh(int theValue) {
    //theBraid.exportMesh("M-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount);
}

public void controlEvent(ControlEvent theEvent) {
    //println("theEvent: "+theEvent);
    if(auto){
      if(theEvent.getName() == "playpause"){
        theWall.update();
      }
      else if (theEvent.getName()  == "numTiles" || theEvent.getName()  == "numThreads") {
        theWall.setup();
      }
    }
}
class Braid  { 

	ArrayList<Cell> cells;
	PVector position;
	PVector scale;

	Braid() { 
		position = new PVector(0,100);	
		setup();
	}

	public void setup() {
		float sc = 10/PApplet.parseFloat(braidLength);
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


	public void exportMesh(String fileName){
	    //export an x3d file, change to OBJExport for obj
	    MeshExport output = (MeshExport) createGraphics(10, 10, "nervoussystem.obj.X3DExport", fileName + ".x3d");
	    output.beginDraw();
	    this.draw(output);
	    output.endDraw();
	    output.dispose();
	    println(fileName + " Export complete.");
	}

}
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
		int st = round(random(0, 5));
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
class Tile{

	int state;
	int posX;
	int posY;
	int step;
	PVector[] threadA = new PVector[2];
	PVector[] threadB = new PVector[2];
	Wall parent;
	int c;

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
		if(stp % (numThreads-2) == 0){
			int r = round(random(1,2));
			state = r;
		}
		else{
			state = 0;
		}

		//state = round(map(sin(step+x*y),-1,1,0,2));
		//state = round(map( sin( sin(step + x) + cos(step+y) * step),-1,1,0,2));
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
					int r = round(random(1,2));
					st = r;
				}
			}else if (neighbourhood > 1) {
				if(neighbours == 2 || neighbours == 3 || neighbours == 4 || neighbours == 5 || neighbours == 6){
					int r = round(random(1,2));
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
  public void settings() {  size(1000, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BraidLab_VI" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
