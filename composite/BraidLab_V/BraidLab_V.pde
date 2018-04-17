import controlP5.*;
import nervoussystem.obj.*;

ControlP5 cp5;

int braidLength;
int numTiles;
int memoryLength;
int numThreads;
int speed;
boolean gui = true;
boolean auto = true; 
boolean dontLoop = true;
boolean playpause = false;
boolean drawGrid = false;
Textlabel fps;
Textlabel start;
Textlabel pattern;
Wall theWall;

void setup() {
	  size(1000, 800);
  	setupGUI();
  	//theBraid = new Braid();
    theWall = new Wall();
}
 
void draw() {
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

void reset(){
	//theBraid.setup();
  theWall.setup();
}

void setupGUI(){
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

  	cp5.addSlider("numThreads")
  		.setLabel("num thread pairs")
     	.setId(2)
     	.setPosition(280,10)
     	.setSize(128,16)
     	.setValue(20)
     	.setRange(3,60);

    cp5.addSlider("speed")
  		.setLabel("speed")
     	.setId(3)
     	.setPosition(500,10)
     	.setSize(128,16)
     	.setValue(15)
     	.setRange(5,120);

  	cp5.addToggle("playpause").setPosition(700,10).setSize(40,16);
     cp5.addToggle("drawGrid").setPosition(750,10).setSize(40,16);
}


void drawGUI(){
	cp5.draw();
	fps.setValueLabel("fps: " + floor(frameRate));
	fps.draw(this);	
}

void keyPressed(){
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
