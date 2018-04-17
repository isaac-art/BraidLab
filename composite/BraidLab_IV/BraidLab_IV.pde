import controlP5.*;
import nervoussystem.obj.*;

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

void setup() {
	size(1000, 500);
  	setupGUI();
  	theBraid = new Braid();
}
 
void draw() {
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

void reset(){
	theBraid.setup();
}

void setupGUI(){
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
     	.setRange(3,10);

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


  cp5.addButton("exportMesh").setPosition(790,10).setSize(64,16);
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
	if(key == ' ' || key == 'n' || key == 'N'){
		theBraid.setup();
	}
	if(key == 'r' || key == 'R'){
		reset();
	}
}


public void exportMesh(int theValue) {
    theBraid.exportMesh("M-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount);
}

// public void controlEvent(ControlEvent theEvent) {
// 	if(auto){
// 		theBraid.setup();
// 	}
// }
