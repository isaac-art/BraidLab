ArrayList<Thread> threads;

void setup() {
  size(512, 256, P3D);
  threads = new ArrayList<Thread>();
  for (int i = 0; i < 4; i++) {
    threads.add(new Thread(128,i*10+100,0));
  }
}


void draw() {
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





void keyPressed(){
  if(key == 's'){
    save("S-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
  }
}
