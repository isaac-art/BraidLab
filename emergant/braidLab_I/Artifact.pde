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
    scale = new PVector(1.,1.,1.);
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
