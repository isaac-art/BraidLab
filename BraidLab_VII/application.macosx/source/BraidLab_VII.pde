/*
__ ____ _____ ____ _____ ____ _____ ____ _____ ____ _____ _
__X____X_____X____X_____X____X_____X____X_____X____X_____X_
____ _____X____ _____X____ _____X____ _____X____ _____X____

██████╗ ██████╗  █████╗ ██╗██████╗ ██╗      █████╗ ██████╗ 
██╔══██╗██╔══██╗██╔══██╗██║██╔══██╗██║     ██╔══██╗██╔══██╗
██████╔╝██████╔╝███████║██║██║  ██║██║     ███████║██████╔╝
██╔══██╗██╔══██╗██╔══██║██║██║  ██║██║     ██╔══██║██╔══██╗
██████╔╝██║  ██║██║  ██║██║██████╔╝███████╗██║  ██║██████╔╝
╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═╝╚═════╝ 
__ ____ _____ ____ _____ ____ _____ ____ _____ ____ _____ _
__X____X_____X____X_____X____X_____X____X_____X____X_____X_
____ _____X____ _____X____ _____X____ _____X____ _____X____

Version VII – April 2018 – by Isaac Clarke

+------------------+
| Acknowledgements |
+------------------+
Artifact Lab - Lior Ben Gai (2018)
Braids - Ester Dalvit https://www.youtube.com/watch?v=u3Gt578803I
A Processing implementation of Game of Life - 
     Joan Soler-Adillon (processing examples)
Game of Life - John Conway 1970
John Conway (1970) An Enumeration of Knots and Links, and Some of 
     Their Algebraic Properties. Pergamon Press
Tim Ingold (2007) Lines: A Brief History. Routledge Classics.
Katherine Ye (2015) Strange Loops. 
    https://github.com/hypotext/knotation/blob/master/StrangeLoops_PowerfulNotations.pdf
David Balade (2010) Designing Celtic Ornament. Editions Ouest-France.

+------+
| TODO |
+------+
Export pdf diagram
Export 3D model

+-------+
| ABOUT |
+-------+
This program generates Braids by use of a simple cellular automata function.

Carving the shape along the grain of the wood, but also being resisted and 
pushed into where the grain goes, like canoeing down a river.



*/

// import libraries
import controlP5.*;
import nervoussystem.obj.*;
import peasy.*;
//setup variables
PeasyCam cam;
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
boolean dAxis = false;
boolean drawBraid = true;
boolean toggleAxis = true;
boolean inColor = true;
boolean isFilled = false;
boolean diagramMode = false;
Textlabel fps;
Textlabel start;
Textlabel pattern;
WallTD theWallTD;

void setup() {
  
    size(1280, 800, P3D);
  //init camera
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(10);
  cam.setMaximumDistance(2000);
  setupGUI();
  //init the braid
  theWallTD = new WallTD();
}

void draw() {
  background(218);
  if (playpause) {
    //this allows control over the speed of the cellular automata 
    if (frameCount % speed == 0) {
      theWallTD.update();
    }
  }
  //draw the axis
  if (dAxis)drawAxis();
  //draw the braid
  theWallTD.draw(this.g);
  //draw the gui
  if (gui)drawGUI();
}

void setupGUI() {
  //setup all the sliders and switches
  cp5 = new ControlP5(this);
  cp5.setAutoDraw(false);
  // this is from a previous version, might bring it back in later
  //start = new Textlabel(cp5, "start", 10, height-18, memoryLength*5, 16);
  //pattern = new Textlabel(cp5, "pattern", memoryLength*5+155, height-18, width, 16);
  fps = new Textlabel(cp5, "fps", 10, 10, 128, 16);

  //the length of the braid threads
  cp5.addSlider("numTiles")
    .setLabel("num tiles")
    .setColorBackground(color(30))
    .setColorForeground(color(80))
    .setColorActive(color(120))
    .setColorLabel(color(0))
    .setId(1)
    .setPosition(70, 10)
    .setSize(128, 16)
    .setValue(9)
    .setRange(5, 25);
   
  //how many rings of neighboors to look at for cellular automata
  cp5.addSlider("neighbourhood")
    .setLabel("neighbourhood")
    .setColorBackground(color(30))
    .setColorForeground(color(80))
    .setColorActive(color(120))
    .setColorLabel(color(0))
    .setId(4)
    .setPosition(675, 10)
    .setSize(80, 16)
    .setValue(1)
    .setRange(1, 4);

  //the number of threads(actually double this, their are two threads per tile)
  cp5.addSlider("numThreads")
    .setLabel("num thread pairs")
    .setColorBackground(color(30))
    .setColorForeground(color(80))
    .setColorActive(color(120))
    .setColorLabel(color(0))
    .setId(2)
    .setPosition(280, 10)
    .setSize(128, 16)
    .setValue(5)
    .setRange(3, 25);
    
  //speed cellular automata updates
  cp5.addSlider("speed")
    .setLabel("speed")
    .setColorBackground(color(30))
    .setColorForeground(color(80))
    .setColorActive(color(120))
    .setColorLabel(color(0))
    .setId(3)
    .setPosition(500, 10)
    .setSize(128, 16)
    .setValue(15)
    .setRange(1, 120);

  cp5.addToggle("playpause")
  .setPosition(850, 10)
  .setColorBackground(color(30))
  .setColorForeground(color(80))
  .setColorActive(color(120))
    .setColorLabel(color(0))
  .setSize(40, 16);

  cp5.addToggle("diagramMode")
  .setColorBackground(color(30))
  .setColorForeground(color(80))
  .setColorActive(color(120))
    .setColorLabel(color(0))
  .setLabel("diagram")
  .setPosition(900, 10)
  .setColorBackground(color(30))
  .setColorForeground(color(80))
    .setColorLabel(color(0))
  .setColorActive(color(120))
  .setSize(40, 16);
  //NEED TO RETHINK THE EXPORT
  //cp5.addToggle("exportMesh").setLabel("export").setPosition(800, 10).setSize(40, 16);
  cp5.addToggle("drawGrid")
  .setColorBackground(color(30))
  .setColorForeground(color(80))
    .setColorLabel(color(0))
  .setColorActive(color(120))
  .setLabel("drawCells")
  .setPosition(950, 10)
  .setSize(40, 16);

  cp5.addToggle("dAxis")
  .setColorBackground(color(30))
  .setColorForeground(color(80))
    .setColorLabel(color(0))
  .setColorActive(color(120))
  .setLabel("axis")
  .setPosition(1000, 10)
  .setSize(40, 16);
  
  cp5.addToggle("inColor")
  .setLabel("colour")
  .setColorBackground(color(30))
  .setColorForeground(color(80))
  .setColorLabel(color(0))
  .setColorActive(color(120))
  .setPosition(1050, 10)
  .setSize(40, 16);

}


void drawGUI() {
  if (cp5.isMouseOver()) {
    cam.setActive(false);
  } else {
    cam.setActive(true);
  }
  // draw the GUI outside of the camera's view
  hint(DISABLE_DEPTH_TEST);
  cam.beginHUD();
  cp5.draw();
  fps.setValueLabel("fps: " + floor(frameRate));
  fps.setColor(color(0));
  fps.draw(this);
  cam.endHUD();
  hint(ENABLE_DEPTH_TEST);
}

void keyPressed() {
  //save a screenshot with time+frame stamp
  if (key == 's' || key == 'S') {
    save("S-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
  }
  //toggle the gui
  if (key == 'g' || key == 'G') {
    gui = !gui;
  }
  //get new braid
  if (key == 'n' || key == 'N') {
    //theBraid.setup();
    theWallTD.setup();
    //theWall.update();
  }
  //update to next state in cellular automata
  if (key == ' ' ) {
    theWallTD.update();
  }
  //reset the braid(eg get new braid)
  if (key == 'r' || key == 'R') {
    theWallTD.setup();
    //theWall.update();
  }
}

//draw the axis 
void drawAxis() {
  pushStyle();
  // X
  strokeWeight(3);
  stroke(255, 0, 0);
  line(0, 0, 0, 100, 0, 0);
  strokeWeight(9);
  point(100, 0, 0);
  // Y
  strokeWeight(3);
  stroke(0, 255, 0);
  line(0, 0, 0, 0, 100, 0);
  strokeWeight(9);
  point(0, 100, 0);
  // Z
  strokeWeight(3);
  stroke(0, 0, 255);
  line(0, 0, 0, 0, 0, 100);
  strokeWeight(9);
  point(0, 0, 100);

  popStyle();
}

//export the 3d model
public void exportMesh(int theValue) {
  theWallTD.exportMesh("M-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount);
}


// update or redraw the braid as sliders change
public void controlEvent(ControlEvent theEvent) {
  //println("theEvent: "+theEvent);
  if (auto) {
    if (theEvent.getName() == "playpause") {
      //theWall.update();
      theWallTD.update();
    } else if (theEvent.getName()  == "numTiles" || theEvent.getName()  == "numThreads") {
      //theWall.setup();
      theWallTD.setup();
    } else if(theEvent.getName() == "diagramMode"){
      //theWallTD.update();
    }
  }
}