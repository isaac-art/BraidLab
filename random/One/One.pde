

int[] myA = new int[20];

void setup() {
	size(512, 512);
	for(int i = 0; i < myA.length; i++){
		myA[i] = int(random(20, width-20));
	}
}



void draw() {
	background(255);
	
	noFill();
	stroke(0);
	strokeWeight(4);

	beginShape();

	for(int i = 0; i < 20; i+=2){

		 curveVertex(myA[i],myA[i+1]);

	}


	endShape(CLOSE);

	stroke(200);
	strokeWeight(2);

	beginShape();

	for(int i = 0; i < 20; i+=2){

		 curveVertex(myA[i],myA[i+1]);

	}


	endShape(CLOSE);

}

void mousePressed(){
	for(int i = 0; i < myA.length; i++){
		myA[i] = int(random(20, width-20));
	}
}


void keyPressed(){
  if(key == 's'){
    save("R-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
  }
}