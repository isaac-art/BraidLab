import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Five extends PApplet {

ArrayList<Thread> threads;

public void setup() {
  
  threads = new ArrayList<Thread>();
  for (int i = 0; i < 4; i++) {
    threads.add(new Thread(128,i*10+100,0));
  }
}


public void draw() {
  background(218);
  
  for (int i = 0; i < threads.size(); i++) {
    threads.get(i).update();
  }

  for (int i = 0; i < threads.size(); i++) {
    beginShape(TRIANGLE_STRIP);

    Thread vv = threads.get(i);
    ArrayList<PVector> poss = vv.path;
    stroke(vv.c);
    for (int j = 0; j< poss.size(); j++) {
      vertex(poss.get(j).x, poss.get(j).y, poss.get(j).z);
    }

    endShape();
  }
}





public void keyPressed(){
  if(key == 's'){
    save("S-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
  }
}
class Thread {
  ArrayList<PVector> path;
  PVector initPos;
  PVector currentPos;
  int c;

  Thread(float x, float y, float z) {
    path = new ArrayList<PVector>();
    currentPos = new PVector(x, y, z);
    initPos = new PVector(x,y,z);
    c = color(random(0,200),random(0,200),random(0,200));
  }

  public void update() {
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
  public void settings() {  size(512, 256, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Five" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
