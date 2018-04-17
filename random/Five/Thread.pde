class Thread {
  ArrayList<PVector> path;
  PVector initPos;
  PVector currentPos;
  color c;

  Thread(float x, float y, float z) {
    path = new ArrayList<PVector>();
    currentPos = new PVector(x, y, z);
    initPos = new PVector(x,y,z);
    c = color(random(0,200),random(0,200),random(0,200));
  }

  void update() {
    PVector thispos = new PVector(currentPos.x, currentPos.y, currentPos.z);
   
    path.add(thispos); 

    if (path.size() > 256) {
      path.clear();

    }

    int y = 0;

    if(random(1, 10) < 2){
      switch(round(random(1,3))){
        case 1:
          y = 10;
          break;
        case 2:
          y = 0;
          break;
        case 3:
          y = -10;
          break;
      }
    }

    PVector mv = new PVector(1,y,random(-2, 2));
    currentPos.add(mv);


    if(currentPos.x > 384){
      PVector ii = new PVector(initPos.x, initPos.y, initPos.z); 
      currentPos = ii;
      c = color(random(0,200),random(0,200),random(0,200));
    }


  }
}