
int sectionLn = 32;
int[] braidKey = new int[8];

void setup() {
	size(256, 128);
	newBraid();
}
void draw() {
	background(218);
	strokeWeight(2);
	stroke(0);
	fill(0);
	for(int i = 0; i < braidKey.length; i++){

		switch (braidKey[i]) {
			case 1:
				drawOne();
				break;
			case 2:
				drawTwo();
				break;
			case 3:
				drawThree();
				break;
		  case 4:
        drawFour();
        break;
      case 5:
        drawFive();
        break;
      case 6:
        drawSix();
        break;
    }

	}
}


void drawOne(){
	line(0,16, sectionLn,16);
	line(0,24, sectionLn,24);
	line(0,32, sectionLn,40);
  stroke(218);
  fill(218);
  ellipse(16,36,6,6);
  stroke(0);
  fill(0);
	line(0,40, sectionLn,32);

	textSize(8);
	text("1", 14, 64); 

	translate(sectionLn,0);
}

void drawTwo(){
	line(0,16, sectionLn,16);
	line(0,24, sectionLn,32);
  stroke(218);
  fill(218);
  ellipse(16,26,6,6);
  stroke(0);
  fill(0);
	line(0,32, sectionLn,24);
	line(0,40, sectionLn,40);

	textSize(8);
	text("2", 14, 64); 

	translate(sectionLn,0);
}

void drawThree(){
	line(0,16, sectionLn,24);
  stroke(218);
  fill(218);
  ellipse(16,20,6,6);
  stroke(0);
  fill(0);
	line(0,24, sectionLn,16);
	line(0,32, sectionLn,32);
	line(0,40, sectionLn,40);


	textSize(8);
	text("3", 14, 64); 

	translate(sectionLn,0);
}



void drawFour(){
  line(0,16, sectionLn,16);
  line(0,24, sectionLn,24);
  line(0,40, sectionLn,32);
  stroke(218);
  fill(218);
  ellipse(16,36,6,6);
  stroke(0);
  fill(0);
  line(0,32, sectionLn,40);
  
  textSize(8);
  text("4", 14, 64); 

  translate(sectionLn,0);
}

void drawFive(){
  line(0,16, sectionLn,16);
  
  
  line(0,32, sectionLn,24);
  
  stroke(218);
  fill(218);
  ellipse(16,26,6,6);
  stroke(0);
  fill(0);

  line(0,24, sectionLn,32);
  
  
  line(0,40, sectionLn,40);

  textSize(8);
  text("5", 14, 64); 

  translate(sectionLn,0);
}

void drawSix(){
  line(0,24, sectionLn,16);
  stroke(218);
  fill(218);
  ellipse(16,20,6,6);
  stroke(0);
  fill(0);
  line(0,16, sectionLn,24);
  line(0,32, sectionLn,32);
  line(0,40, sectionLn,40);


  textSize(8);
  text("6", 14, 64); 

  translate(sectionLn,0);
}

void newBraid(){
	for(int i = 0; i < braidKey.length; i++){
		braidKey[i] = round(random(1,6));
	}
}


void mousePressed(){
	newBraid();
}

void keyPressed(){
	if(key == 's'){
		save("R-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
	}
}