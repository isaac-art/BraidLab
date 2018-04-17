import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BraidLab_II extends PApplet {


// GENERATES A BRAID 

// WILL BE USED TO MAKE WHEEL TREAD




ControlP5 cp5;

int braidLength;
int memoryLength;
int speed;
int CO, CT, CTT, CF;
boolean gui = true;
boolean auto = true; 
boolean dontLoop = true;
boolean playpause = false;
Textlabel fps;
Textlabel start;
Textlabel pattern;
Braid theBraid;

public void setup() {
	
  	setupGUI();
  	theBraid = new Braid();
}

public void draw() {
	background(218);
	if(playpause){
		if(frameCount % speed == 0){
			theBraid.update();
		}
	}

	theBraid.draw(this.g);
  	if(gui)drawGUI();

	String patt = "";
	String sta = "";
  	for(int i = 0; i < theBraid.cells.size();i++){
	  	int c = theBraid.cells.get(i).state;
  		if(i >= memoryLength){
	  		patt += String.valueOf(c);
	  		patt += ", ";
	  	}else{
			sta += String.valueOf(c);
	  		sta += ", ";
	  	}
	}  	
	start.setValueLabel("start:"+sta);
	start.setColor(160);
	start.draw(this);
	pattern.setValueLabel("pattern:"+patt);
	pattern.setColor(10);
	pattern.draw(this);
}

public void reset(){
	theBraid.setup();
}

public void setupGUI(){
	cp5 = new ControlP5(this);
  	cp5.setAutoDraw(false);
  	start = new Textlabel(cp5,"start",10,height-18,memoryLength*5,16);
  	pattern = new Textlabel(cp5,"pattern",memoryLength*5+155,height-18,width,16);
  	fps = new Textlabel(cp5,"fps",10,10,128,16);

  	cp5.addSlider("braidLength")
  		.setLabel("braid length")
     	.setId(1)
     	.setPosition(70,10)
     	.setSize(128,16)
     	.setValue(35)
     	.setRange(10,60);

  	cp5.addSlider("memoryLength")
  		.setLabel("memory length")
     	.setId(2)
     	.setPosition(280,10)
     	.setSize(128,16)
     	.setValue(5)
     	.setRange(2,10);

    cp5.addSlider("speed")
  		.setLabel("speed")
     	.setId(3)
     	.setPosition(500,10)
     	.setSize(128,16)
     	.setValue(30)
     	.setRange(5,120);

  	cp5.addToggle("playpause").setLabel("").setPosition(700,10).setSize(64,16);

  	cp5.addSlider("CO")
  		.setLabel("CO")
     	.setId(4)
     	.setPosition(70,30)
     	.setSize(56,16)
     	.setValue(0)
     	.setRange(0,6);
    cp5.addSlider("CT")
  		.setLabel("CT")
     	.setId(5)
     	.setPosition(140,30)
     	.setSize(56,16)
     	.setValue(0)
     	.setRange(0,6);
    cp5.addSlider("CTT")
  		.setLabel("CTT")
     	.setId(6)
     	.setPosition(200,30)
     	.setSize(56,16)
     	.setValue(0)
     	.setRange(0,6);
	cp5.addSlider("CF")
	  	.setLabel("CF")
     	.setId(7)
     	.setPosition(270,30)
     	.setSize(56,16)
     	.setValue(0)
     	.setRange(0,6);
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
	if(key == ' ' || key == 'n' || key == 'N'){
		theBraid.setup();
	}
	if(key == 'r' || key == 'R'){
		reset();
	}
}


public void controlEvent(ControlEvent theEvent) {
	if(auto){
		theBraid.setup();
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

	public int fact(int mem){
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
  public void settings() { 	size(1000, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BraidLab_II" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
