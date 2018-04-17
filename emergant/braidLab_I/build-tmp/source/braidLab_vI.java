import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import peasy.*; 
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

public class braidLab_vI extends PApplet {

//////////////////////////////////////
// Artifact Lab - wrapper template project for exploring 3D forms
// VERSION 0.2
// liorbengai
// Jan 2018

// 1. GLOBAL VARIABLES
// 2. SETUP AND DRAW
// 3. GUI - SETUP AND DRAW
// 4. GUIDES AND HELPERS
// 5. EVENT HANDLERS
//////////////////////////////////

// Library import




// camera and GUI
PeasyCam cam;
ControlP5 cp5;

// GUI vars
Textlabel fpsLbl;
boolean toggle_axis = true, toggle_grid = true, auto_update = false;
float param_A, param_B;
float param_C, param_D;

// scene vars
Artifact shape;

public void setup() {
  
  // Setup PeasyCam
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(10);
  cam.setMaximumDistance(4000);

  // Setup ControlP5
  setupGUI();

  // setup scene
  shape = new Artifact(); // Plane // Artifact
}

public void draw() {
  background(218);

  if(toggle_grid)drawGrid();
  if(toggle_axis)drawAxis();

  noFill();
  //stroke(255);
  strokeWeight(3);

  shape.update();
  shape.draw(this.g);

  drawGUI();
}

////////////////////////
// SETUP AND DRAW GUI
//////////////////////

// create a list of controlP5 library GUI elements
// See examples folder in processing
public void setupGUI(){
  cp5 = new ControlP5(this);
  cp5.setAutoDraw(false);

  // draw current framerate as a label
  fpsLbl = new Textlabel(cp5,"fps",10,10,128,16);

  // on/off toggle buttons
  cp5.addToggle("toggle_grid").setPosition(10,40).setSize(16,16);
  cp5.addToggle("toggle_axis").setPosition(10,80).setSize(16,16);
  cp5.addToggle("auto_update").setPosition(10,120).setSize(16,16);

  // float sliders
  cp5.addSlider("param_A")
     .setId(1)
     .setPosition(10,200)
     .setSize(128,16)
     .setRange(0,256);

  cp5.addSlider("param_B")
      .setId(2)
      .setPosition(10,220)
      .setSize(128,16)
      .setRange(0,256);

  // float sliders
  cp5.addSlider("param_C")
     .setId(3)
     .setPosition(10,240)
     .setSize(128,16)
     .setRange(0,256);

  cp5.addSlider("param_D")
      .setId(4)
      .setPosition(10,260)
      .setSize(128,16)
      .setRange(0,256);

  // buttons
  cp5.addButton("recreate").setPosition(10,300).setSize(64,16);
  cp5.addButton("exportMesh").setPosition(10,320).setSize(64,16);

}

public void drawGUI() {
  // do not move the camera while manipulating the gui
  if (cp5.isMouseOver()) {
    cam.setActive(false);
  } else {
    cam.setActive(true);
  }
  // draw the GUI outside of the camera's view
  hint(DISABLE_DEPTH_TEST);
  cam.beginHUD();
  cp5.draw();
  fpsLbl.setValueLabel("fps: " + floor(frameRate));
  fpsLbl.draw(this);
  cam.endHUD();
  hint(ENABLE_DEPTH_TEST);
}

////////////////////////
// GUIDES AND HELPERS
//////////////////////

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

public void drawGrid() {
  pushStyle();
  pushMatrix();
  strokeWeight(1);
  stroke(180);
  float sz = 100;
  int nm = 8;
  // draw lines along the flat XY plane
  for(int i = 0; i <= nm; i++){
    line( (i * sz) - (sz*nm/2), -sz*nm/2, 0, (i * sz) - (sz*nm/2), sz*nm/2, 0);
    line( -sz*nm/2, (i * sz) - (sz*nm/2), 0, sz*nm/2, (i * sz) - (sz*nm/2), 0);
  }
  popMatrix();
  popStyle();
}

//////////////////////
// GUI EVENT HANDLERS
////////////////////

// empty the shape and recreate its vertices
public void recreate(int theValue) {
  shape.setup();
}

public void exportMesh(int theValue) {
  shape.exportMesh("R-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount);
}


// this handler gets called whenever ANY gui element gets changed!

public void controlEvent(ControlEvent theEvent) {

  if(auto_update){
    shape.setup();
  }

  switch(theEvent.getId()) {
    case(1):
      // react to id - 1
    break;
    case(2):
     // react to id - 2
    break;
  }
}


//

public void keyPressed(){
  if(key == 's'){
    save("S-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
  }
}
////////////////////////////////////////////////////////////////////////
// Artifact class
// liorbengai
// Jan 2018

// This class is designed as a base class to manage a compound 3D shape.
// you can modify or extend it to create custom shape structures

// 1. PROPERTIES
// 2. CONSTRUCTOR
// 3. METHODS
//  * setup
//  * update
//  * draw
// 4. EXPORT MESH
// 5. HELPERS / CONVENIENCE / EQUATIONS

////////////////////////////////////////////////////////////////////

public class Artifact{

  ////////////////////////
  // PROPERTIES
  //////////////////////

  ArrayList<Vert> vertices;
  PVector position;
  PVector rotation;
  PVector scale;

  ////////////////////////
  // CONSTRUCTOR
  //////////////////////
  Artifact(){
    // set initial transformtaions
    position = new PVector(0,0,0);
    rotation = new PVector(0,0,0);
    scale = new PVector(1.f,1.f,1.f);
    vertices = new ArrayList<Vert>();
    setup();
  }

  ////////////////////////
  // METHODS
  //////////////////////

  public void setup(){
    // create/empty the arrayList
    vertices = new ArrayList<Vert>();

    for(int i = 0; i < 10; i++){
        pushVert(random(-50,50),random(-50,50),random(-50,50));
    }
  }

  public void update() {
    // update all vertices
    for(int i = 0; i < vertices.size();i++){
      vertices.get(i).update();
    }
  }

  // draws all vertices using LINE_STRIP to a provided PGraphics object
  public void draw(PGraphics pg){

    pushTransform(pg);
    //draw all vertices
    // pg.beginShape(POINTS);
    // for(int i = 0; i < vertices.size();i++){
    //   stroke(240,240,240);
    //   PVector p = vertices.get(i).position;
    //   pg.vertex(p.x, p.y, p.z);
    // }
    // pg.endShape();

    for(int i = 0; i < vertices.size(); i++){
      pg.beginShape(TRIANGLE_STRIP);

      Vert vv = vertices.get(i);
      ArrayList<PVector> poss = vv.positions;
      
      stroke(vv.col);
      
      for(int j = 0; j< poss.size(); j++){
        pg.vertex(poss.get(j).x, poss.get(j).y, poss.get(j).z);
      }

      pg.endShape();
    }


    popTransform(pg);
  }

  ////////////////////////////////////////
  // MESH EXPORT
  //////////////////////////////////////

  // This uses the OBJExport library:
  // https://n-e-r-v-o-u-s.com/tools/obj/
  public void exportMesh(String fileName){
    //export an x3d file, change to OBJExport for obj
    MeshExport output = (MeshExport) createGraphics(10, 10, "nervoussystem.obj.X3DExport", fileName + ".x3d");
    output.beginDraw();
    this.draw(output);
    output.endDraw();
    output.dispose();
    println(fileName + " Export complete.");
  }

  ////////////////////////////////////////
  // HELPERS / CONVENIENCE / EQUATIONS
  //////////////////////////////////////

  public void pushTransform(PGraphics pg){
    // perform all transformations
    pg.pushMatrix();
    pg.translate(position.x, position.y, position.z);
    pg.rotateX(rotation.x);
    pg.rotateY(rotation.y);
    pg.rotateZ(rotation.z);
    pg.scale(scale.x, scale.y, scale.z);
  }

  // pop out of all transformations
  public void popTransform(PGraphics pg){
      pg.popMatrix();
  }

  // Push vertices to arraylist
  // Version A - vector

  public void pushVert(PVector v){
    vertices.add(new Vert(v.x,v.y,v.z,this));
  }
  // Version B - 3 floats
  public void pushVert(float x, float y, float z){
    vertices.add(new Vert(x,y,z,this));
  }

} // END class Artifact

















//
//////////////////////////////////
// Vertex class
// A simple data object wrapper for a 3D vector
// liorbengai
// Jan 2018

// 1. PROPERTIES
// 2. CLASS SETUP
// 3. UPDATE
///////////////////////////////

class Vert{

  ////////////////////////
  // PROPERTIES
  //////////////////////

  PVector position;
  PVector velocity;
  Artifact parent;
  
  ArrayList<PVector> positions;
  int col;
  ////////////////////////
  // SETUP
  //////////////////////

  // Constructor
  Vert(float x, float y, float z, Artifact _parent){
    parent = _parent;
    // set position according to parameters
    positions = new ArrayList<PVector>();
    
    position  = new PVector(x, y, z);
    velocity = new PVector(random(-0.1f, 0.1f),random(-0.1f, 0.1f),random(-0.1f, 0.1f));
    col = color(random(255),random(255),random(255));
  }

  ////////////
  // UPDATE
  //////////

  // modulate the position on runtime
  public void update(){
    //if(frameCount % 60 == 0){
      PVector thispos = new PVector(position.x, position.y, position.z);
      positions.add(thispos);
      if(positions.size() > 1000){
        positions.remove(0);
      }
      //println("positions : "+positions);
    //}

    velocity.add(separation());
    velocity.add(cohesion());
    velocity.add(align());

    velocity.limit(1.5f);
    position.add(velocity);
  }

  public PVector separation(){
    // create a result vector
    PVector res = new PVector(0,0,0);

    // go over everyone
    for(int i = 0 ; i < parent.vertices.size(); i++){
      Vert other = parent.vertices.get(i); 
      float d = position.dist(other.position);
      if(d > 0 && d < 100){
        res.sub(PVector.sub(other.position, position));
      }
    }

    // scale and retrun
    //res.mult(0.07);
    res.mult(0.01f + 0.01f * param_A);
    return res;
  }


  public PVector cohesion(){
    // create a result vector
    PVector res = new PVector(0,0,0);
    int numNei = 0;

    // go over everyone
    for(int i = 0 ; i < parent.vertices.size(); i++){
      Vert other = parent.vertices.get(i);
      float d = position.dist(other.position);
      if(d > 0 && d < 200){
        res.add(other.position);
        numNei++;
      }
    }

    if(numNei == 0){
      return new PVector(0,0,0);
    }

    res.div(numNei);
    res.sub(position);
    // scale and retrun
    //res.mult(0.01);
    res.mult(0.005f + 0.005f * param_B);
    return res;
  }


  public PVector align(){
    // create a result vector
    PVector res = new PVector(0,0,0);
    int numNei = 0;

    // go over everyone
    for(int i = 0 ; i < parent.vertices.size(); i++){
      Vert other = parent.vertices.get(i);  
      float d = position.dist(other.position);
      if(d > 0 && d < 150){
        res.add(other.velocity);
        numNei++;
      }
    }

    if(numNei == 0){
      return new PVector(0,0,0);
    }

    res.div(numNei);
    // scale and retrun
    //res.mult(0.3);
    res.mult(0.1f + 0.3f * param_C);
    return res;
  }


} // class Vert







//
  public void settings() {  size(1024, 768, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "braidLab_vI" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
