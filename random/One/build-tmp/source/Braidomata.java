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

public class Braidomata extends PApplet {



int[] myA = new int[20];

public void setup() {
	
	for(int i = 0; i < myA.length; i++){
		myA[i] = PApplet.parseInt(random(20, width-20));
	}
}



public void draw() {
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

public void mousePressed(){
	for(int i = 0; i < myA.length; i++){
		myA[i] = PApplet.parseInt(random(20, width-20));
	}
}
  public void settings() { 	size(512, 512); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Braidomata" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
