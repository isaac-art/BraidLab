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

public class Three extends PApplet {


int sectionLn = 32;
int[] braidKey = new int[8];

public void setup() {
	
	newBraid();
}
public void draw() {
	background(240);
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
		}

	}
}


public void drawOne(){
	line(0,16, sectionLn,16);
	line(0,24, sectionLn,24);
	line(0,32, sectionLn,40);
	line(0,40, sectionLn,32);

	textSize(8);
	text("1", 8, 64); 

	translate(sectionLn,0);
}

public void drawTwo(){
	line(0,16, sectionLn,16);
	line(0,24, sectionLn,32);
	line(0,32, sectionLn,24);
	line(0,40, sectionLn,40);

	textSize(8);
	text("2", 8, 64); 

	translate(sectionLn,0);
}

public void drawThree(){
	line(0,16, sectionLn,24);
	line(0,24, sectionLn,16);
	line(0,32, sectionLn,32);
	line(0,40, sectionLn,40);


	textSize(8);
	text("3", 8, 64); 

	translate(sectionLn,0);
}

public void newBraid(){
	for(int i = 0; i < braidKey.length; i++){
		braidKey[i] = round(random(1,3));
	}
}


public void mousePressed(){
	newBraid();
}

public void keyPressed(){
	if(key == 's'){
		save("R-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
	}
}
  public void settings() { 	size(256, 128); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Three" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
