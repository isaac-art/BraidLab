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
  color col;
  ////////////////////////
  // SETUP
  //////////////////////

  // Constructor
  Vert(float x, float y, float z, Artifact _parent){
    parent = _parent;
    // set position according to parameters
    positions = new ArrayList<PVector>();
    
    position  = new PVector(x, y, z);
    velocity = new PVector(random(-0.1, 0.1),random(-0.1, 0.1),random(-0.1, 0.1));
    col = color(random(255),random(255),random(255));
  }

  ////////////
  // UPDATE
  //////////

  // modulate the position on runtime
  void update(){
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

    velocity.limit(1.5);
    position.add(velocity);
  }

  PVector separation(){
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
    res.mult(0.01 + 0.01 * param_A);
    return res;
  }


  PVector cohesion(){
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
    res.mult(0.005 + 0.005 * param_B);
    return res;
  }


  PVector align(){
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
    res.mult(0.1 + 0.3 * param_C);
    return res;
  }


} // class Vert







//
