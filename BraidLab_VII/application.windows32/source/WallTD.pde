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
    scale = new PVector(1., 1., 1.);
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
              pg.line(c.threadA[0].x, c.threadA[0].y,0.3,c.threadA[1].x, c.threadA[1].y,0.3);
              pg.line(c.threadB[0].x, c.threadB[0].y,0.3,c.threadB[1].x, c.threadB[1].y,0.3);
            }else if(c.state == 1){
              pg.line(c.threadA[0].x, c.threadA[0].y,0.3,c.threadA[1].x, c.threadA[1].y,0.3);
              pg.pushMatrix();
              pg.pushStyle();
              if(!drawGrid){
                pg.stroke(218);
              }else{
                if(c.state == 0)pg.stroke(218);
                if(c.state == 1)pg.stroke(56,95,113);
                if(c.state == 2)pg.stroke(43,65,98);
              }
                pg.translate(tileSize/2,tileSize/2, 0.1);
                pg.box(tileSize/3,tileSize/3,0.2);
              pg.popStyle();
              pg.popMatrix();
              pg.line(c.threadB[0].x, c.threadB[0].y,0,c.threadB[1].x, c.threadB[1].y,0);
            }else if(c.state == 2){
              pg.line(c.threadB[0].x, c.threadB[0].y,0.3,c.threadB[1].x, c.threadB[1].y,0.3);
              pg.pushMatrix();
              pg.pushStyle();
              if(!drawGrid){
                pg.stroke(218);
              }else{
                if(c.state == 0)pg.stroke(218);
                if(c.state == 1)pg.stroke(56,95,113);
                if(c.state == 2)pg.stroke(43,65,98);
              }
                pg.translate(tileSize/2,tileSize/2, 0.1);
                pg.box(tileSize/3,tileSize/3,0.2);
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