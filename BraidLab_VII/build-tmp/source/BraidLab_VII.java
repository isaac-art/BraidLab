import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import nervoussystem.obj.*; 
import peasy.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BraidLab_VII extends PApplet {

/*
__ ____ _____ ____ _____ ____ _____ ____ _____ ____ _____ _
__X____X_____X____X_____X____X_____X____X_____X____X_____X_
____ _____X____ _____X____ _____X____ _____X____ _____X____

\u2588\u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2588\u2588\u2588\u2588\u2557  \u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2557\u2588\u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2557      \u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2588\u2588\u2588\u2588\u2557 
\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2551\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2551     \u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557
\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2551\u2588\u2588\u2551\u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2551     \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2551\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d
\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2551\u2588\u2588\u2551\u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2551     \u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2551\u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557
\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d\u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2551\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557\u2588\u2588\u2551  \u2588\u2588\u2551\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d
\u255a\u2550\u2550\u2550\u2550\u2550\u255d \u255a\u2550\u255d  \u255a\u2550\u255d\u255a\u2550\u255d  \u255a\u2550\u255d\u255a\u2550\u255d\u255a\u2550\u2550\u2550\u2550\u2550\u255d \u255a\u2550\u2550\u2550\u2550\u2550\u2550\u255d\u255a\u2550\u255d  \u255a\u2550\u255d\u255a\u2550\u2550\u2550\u2550\u2550\u255d 
__ ____ _____ ____ _____ ____ _____ ____ _____ ____ _____ _
__X____X_____X____X_____X____X_____X____X_____X____X_____X_
____ _____X____ _____X____ _____X____ _____X____ _____X____

Version VII \u2013 April 2018 \u2013 by Isaac Clarke

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

So logical but mystical.

*/

// import libraries



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

public void setup() {
  
    
  //init camera
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(10);
  cam.setMaximumDistance(2000);
  setupGUI();
  //init the braid
  theWallTD = new WallTD();
}

public void draw() {
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

public void setupGUI() {
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


public void drawGUI() {
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

public void keyPressed() {
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
public void drawAxis() {
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
class Tile{

	int state;
	int posX;
	int posY;
	int step;
	PVector[] threadA = new PVector[2];
	PVector[] threadB = new PVector[2];
	WallTD parent;
	int c;

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
/*
Tile 3D Class
This class holds the state of each cell of the cellular 
automata (the direction of the knot of two threads)

*/

class TileTD {
  int state;
  int posX;
  int posY;
  int step;	
  WallTD parent;
  ArrayList<PVector> points;

  TileTD(int x, int y, int stp, WallTD _parent) {
    parent = _parent;
    posX = x;
    posY = y;
    step = stp;
    // initial states are rythmic 
    // every 3rd is a crossing
    if(stp % (numThreads-2) == 0){
      int r = round(random(1,2));
      state = r;
    }
    else{
      state = 0;
    }

    
  }

  public void update() {
    ///CELLULAR AUTOMATA update function adapted from Joan Soler-Adillon Processing Example.
    //reset state and counted neighbours to 0
    int neighbours = 0;
    int st = 0;
    //for the cells to the left and right of this one (depending on neighbourhood slider)
    for (int x = posX-neighbourhood; x <= posX+neighbourhood; x++) {
    // and for the cells above and below this one (depending on neighbourhood slider)
      for (int y = posY-neighbourhood; y <= posY+neighbourhood; y++) {
        //where this isnt the last of first in a row or column
        if (x>=0 && x<numTiles && y>=0 && y<numThreads) {
          //and it isnt this one
          if (!(x == posX && y == posY)) {
            //and the cell has a crossing
            if (parent.buffer[x][y] == 1 || parent.buffer[x][y] == 2) {
              //add to neighbours count
              neighbours++;
            }
          }
        }
      }
    }

   //if this cell is crossing 1
    if (parent.buffer[posX][posY]==1) {
      // and has less than 2 or more than x neighbours
      if (neighbours < 2 || neighbours > neighbourhood+2) {
        //kill ths cell
        st = 0;
      }
    } else {
      //if the size of the neighbourhood is one
      if (neighbourhood == 1) {
        //and has 2,3or4 neighbours
        if (neighbours == 2 || neighbours == 3 || neighbours == 4 ) {
          //keep alive and pick a random crossing
          int r = 0;
          if(neighbours == 2 || neighbours == 4) {
            r = 1;
          }else{
            r = 2;
          }
          st = r;
        }
      } else if (neighbourhood > 1) {
        //if bigger neighbourhood then allow for more neighbours (or they all die fast from overpopulation)
        if (neighbours == 2 || neighbours == 3 || neighbours == 4 || neighbours == 5 || neighbours == 6) {
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

    // set the state of this tile/cell.
    state = st;
  }
}
/*
Wall 3D Class
 This class holds the braid and is made up of TileTD (3d tiles)
 */


class WallTD {
  //this is pretty similar to artifactlab artifact class
  //a list of tiles
  ArrayList<TileTD> tiles;
  ArrayList<Tile> diagramTiles;
  PVector position;
  PVector rotation;
  PVector scale;
  int[][] buffer;
  int tileSize;
  // no longer used left for reference
  // we will load up the three shapes once and store them in these
  // arraylists so they can be called as needed
  ArrayList<PVector> straightPoints;
  ArrayList<PVector> twistAPoints;
  ArrayList<PVector> twistBPoints;
  //using the Pshapes directly much faster, I dont need to 
  // manipulate the vertices anyway. Though i need to rethink it 
  // for exporting 
  PShape straightShape;
  PShape twistAShape;
  PShape twistBShape;


  WallTD() {
    position = new PVector(0, 0, 0);
    rotation = new PVector(0, 0, 0);
    scale = new PVector(1.f, 1.f, 1.f);
    setup();
  }

  public void setup() {

    
      // create/empty the arrayList
      diagramTiles = new ArrayList<Tile>();
      tiles = new ArrayList<TileTD>();
      
      //the size of the models, not sure how to scale this yet?
      tileSize = 16;
      int stp = 0;
      //add the tiles to the braid for the size on the sliders.
      for (int x = 0; x<numTiles; x++) {
        for (int y = 0; y<numThreads; y++) {
          diagramTiles.add(new Tile(x, y, stp, this));
          tiles.add(new TileTD(x, y, stp, this));
          stp++;
        }
      }
    //make a buffer to store the states of the tiles/cells
    buffer = new int[numTiles][numThreads];
    //load in the objs of the thread crossings.
    straightPoints = new ArrayList<PVector>();
    twistAPoints = new ArrayList<PVector>();
    twistBPoints = new ArrayList<PVector>();
    loadShapePoints(0);
    loadShapePoints(1);
    loadShapePoints(2);
  }

  public void update() {
    // update all vertices
    int co = 0;
    //for every tile/cell add the state to the buffer
    for (int x = 0; x<numTiles; x++) {
      for (int y = 0; y<numThreads; y++) {
        //get the state from the active mode tiles/cells
        int st = 0;
        if(diagramMode){
         st = diagramTiles.get(co).state;
        }else{
         st = tiles.get(co).state;
        }
        buffer[x][y] = st;
        co++;
      }
    }
    //for every tile/cell update the cellular automata 
    // update both sets for 3d and 2d so that they stay in sync
      for (int i = 0; i < diagramTiles.size(); i++) {
        diagramTiles.get(i).update();
      }

      for (int i = 0; i < tiles.size(); i++) {
        tiles.get(i).update();
      }  

  }

  public void draw(PGraphics pg) {


    if(diagramMode){ 
      pushTransform(pg);
      //translate to the middle of the model size so that it fits the screen better
      pg.translate( -((numTiles*tileSize)/2), -((numThreads*tileSize)/2), 1);
     
      int co = 0;
      pg.beginShape(LINES);
      for(int x = 0; x<numTiles; x++){
        for(int y = 0; y<numThreads; y++){
          int off = 0;
          if(x % 2 == 0)off = tileSize/2;
          float xx = x * tileSize; 
          float yy = y * tileSize + off;
          pg.pushMatrix();
          pg.translate(xx, yy, 0);
        
          //get this tile
          Tile c = diagramTiles.get(co);

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
            if(!drawGrid){
              pg.fill(218);
            }else{
              if(c.state == 0)pg.fill(218);
              if(c.state == 1)pg.fill(56,95,113);
              if(c.state == 2)pg.fill(43,65,98);
            }


            if(c.state == 0){
              pg.line(c.threadA[0].x, c.threadA[0].y,0.3f,c.threadA[1].x, c.threadA[1].y,0.3f);
              pg.line(c.threadB[0].x, c.threadB[0].y,0.3f,c.threadB[1].x, c.threadB[1].y,0.3f);
            }else if(c.state == 1){
              pg.line(c.threadA[0].x, c.threadA[0].y,0.3f,c.threadA[1].x, c.threadA[1].y,0.3f);
              pg.pushMatrix();
              pg.pushStyle();
              if(!drawGrid){
                pg.stroke(218);
              }else{
                if(c.state == 0)pg.stroke(218);
                if(c.state == 1)pg.stroke(56,95,113);
                if(c.state == 2)pg.stroke(43,65,98);
              }
                pg.translate(tileSize/2,tileSize/2, 0.1f);
                pg.box(tileSize/3,tileSize/3,0.2f);
              pg.popStyle();
              pg.popMatrix();
              pg.line(c.threadB[0].x, c.threadB[0].y,0,c.threadB[1].x, c.threadB[1].y,0);
            }else if(c.state == 2){
              pg.line(c.threadB[0].x, c.threadB[0].y,0.3f,c.threadB[1].x, c.threadB[1].y,0.3f);
              pg.pushMatrix();
              pg.pushStyle();
              if(!drawGrid){
                pg.stroke(218);
              }else{
                if(c.state == 0)pg.stroke(218);
                if(c.state == 1)pg.stroke(56,95,113);
                if(c.state == 2)pg.stroke(43,65,98);
              }
                pg.translate(tileSize/2,tileSize/2, 0.1f);
                pg.box(tileSize/3,tileSize/3,0.2f);
              pg.popStyle();
              pg.popMatrix();
              pg.line(c.threadA[0].x, c.threadA[0].y,0,c.threadA[1].x, c.threadA[1].y,0);
            }
            pg.popStyle();
            pg.popMatrix();

          }
          pg.popMatrix();
          //update counter
          co++;
        } 

      }
      pg.endShape();
      popTransform(pg);

    }

    else{
      //3D MODE
      //draw the model
      int co = 0;
      pushTransform(pg);
      //translate to the middle of the model size so that it fits the screen better
      pg.translate( -((numTiles*tileSize)/2)+(tileSize/2), -((numThreads*tileSize)/2)+(tileSize/3), 1);
      //draw all vertices
      //change fill mode with the switches
   
      //for every tile.
      for (int x = 0; x<numTiles; x++) {

        for (int y = 0; y<numThreads; y++) {

          int off = 0;
          //off set every other tile so that it makes a 
          //brickwork like layout, this allows the threads
          // to overlap as they cross!
          if (x % 2 == 0)off = tileSize/2;
          float xx = x * tileSize; 
          float yy = y * tileSize + off;
          pg.pushMatrix();
          TileTD c = tiles.get(co);
          //pg.translate(xx, yy, 0);
          // this was an older version leaving here for ref.
          // for(int i = 0; i < c.points.size(); i++){
          //   PVector p = c.points.get(i).copy();
          //   p.add(xx,yy,0);
          //   pg.vertex(p.x, p.y, p.z);
          // }
          
          //based on the state of that tile add the points of the 
          //correct crossing shape
          if (c.state == 0) {
            if(inColor){
              straightShape.setStroke(color(118));
              straightShape.setFill(color(118));
            }else{
              straightShape.setStroke(color(0));
              straightShape.setFill(color(80));
            }
            // Straight Threads
            pg.shape(straightShape,  xx, yy);
            //leaving this for reference, i was loading the shapes
            //by points, which has advantages for vetext manipulation
            //things, but massivly hits performance compared to just 
            //loading in the shapes as PShape
            // for (int i = 0; i < straightPoints.size(); i++) {
            //   PVector p = straightPoints.get(i).copy();
            //   p.add(xx, yy, 0);
            //   pg.vertex(p.x, p.y, p.z);
            // }
          }
          // top thread cross in front lower
          else if (c.state == 1) {
            if(inColor){
              twistAShape.setStroke(color(56,95,113));
              twistAShape.setFill(color(56,95,113));
            }else{
              twistAShape.setStroke(color(0));
              twistAShape.setFill(color(120));
            }
            pg.shape(twistAShape, xx, yy);
          } 
          //lower thread cross in front of top
          else if (c.state == 2) {
            if(inColor){
              twistBShape.setStroke(color(43,65,98));
              twistBShape.setFill(color(43,65,98));
            }else{
              twistBShape.setStroke(color(0));
              twistBShape.setFill(color(180));
            }
            pg.shape(twistBShape,  xx, yy);
          }

          pg.popMatrix();
          co++;
        }
      }
      popTransform(pg);
    }
  }


  //load the shapes
  public void loadShapePoints(int st) {
    PShape s = new PShape();
    switch (st) {
    case 0:
      s = loadShape("straight.obj");
      straightShape = s;
      break;
    case 1:
      s = loadShape("twistA.obj");
      twistAShape = s;
      break;
    case 2:
      s = loadShape("twistB.obj");
      twistBShape = s;
      break;
    }

    int children = s.getChildCount();
    //add all the points in the obj to an 
    //arraylist for that shape
    for (int i = 0; i < children; i++) {
      PShape child = s.getChild(i);
      int total = child.getVertexCount();
      for (int j = 0; j < total; j+=1) {
        PVector v = child.getVertex(j);
        if (st == 0) {
          straightPoints.add(v);
        } else if (st == 1) {
          twistAPoints.add(v);
        } else if (st == 2) {
          twistBPoints.add(v);
        }
      }
    }
  }



  ////////////////////////////////////////
  // HELPERS / CONVENIENCE / EQUATIONS
  //////////////////////////////////////

  public void pushTransform(PGraphics pg) {
    pg.pushMatrix();
    pg.translate(position.x, position.y, position.z);
    pg.rotateX(rotation.x);
    pg.rotateY(rotation.y);
    pg.rotateZ(rotation.z);
    pg.scale(scale.x, scale.y, scale.z);
  }
  public void popTransform(PGraphics pg) {
    pg.popMatrix();
  }

  public void exportMesh(String fileName) {
    //export an x3d file, change to OBJExport for obj
    MeshExport output = (MeshExport) createGraphics(10, 10, "nervoussystem.obj.X3DExport", fileName + ".x3d");
    output.beginDraw();
    this.draw(output);
    output.endDraw();
    output.dispose();
    println(fileName + " Export complete.");
  }
}
  public void settings() {  size(1280, 800, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BraidLab_VII" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
