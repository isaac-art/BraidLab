int sz = 16;
int sp = 8;
int[] grid = new int[256];

void setup() {
	size(512, 512);
	rectMode(CENTER);
	for(int i = 0; i < 256; i++){
			grid[i] = round(map(sin(i+sin(i*4)),-1,1,1,3));
	}
}

void draw() {
	background(230);
	grid();
}

void grid(){
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


void keyPressed(){
	if(key == 's'){
		save("R-"+ hour()+ minute() + second() + day() + month() + year() +"-"+ frameCount + ".png");
	}
}