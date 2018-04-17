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

public class Two extends PApplet {

int sz = 16;
int sp = 8;
int[] grid = new int[256];

public void setup() {
	
	rectMode(CENTER);
	for(int i = 0; i < 256; i++){
			grid[i] = round(map(sin(i),-1,1,1,3));
	}
}

public void draw() {
	background(230);
	grid();
}

public void grid(){
	for(int x = 0; x < 16; x++){
		for(int y = 0; y < 16; y++){
			int xx = x * sz + sp;
			int yy = y * sz + sp;
			int state = grid[x*16+y];
			switch (state) {
				case 1:
					fill(120,0,0);
					break;
				case 2:
					fill(0,120,0);
					break;
				case 3:
					fill(0,0,120);
					break;
			}
			pushMatrix();
				translate(xx,yy);
				rect(xx, yy, sz, sz);
			popMatrix();
		}	
	}
}


public void keyPressed(){
	if(key == 's'){
		save("R-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
	}
}
  public void settings() { 	size(512, 512); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Two" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
