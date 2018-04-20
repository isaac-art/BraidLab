/*
Tile 3D Class
This class holds the state of each cell of the cellular 
automata (the direction of the knot of two threads)

*/

class TileTD {
  int state;
  int posX;
  int posY;
  int step;	
  WallTD parent;
  ArrayList<PVector> points;

  TileTD(int x, int y, int stp, WallTD _parent) {
    parent = _parent;
    posX = x;
    posY = y;
    step = stp;
    // initial states are rythmic 
    // every 3rd is a crossing
    if(stp % (numThreads-2) == 0){
      int r = round(random(1,2));
      state = r;
    }
    else{
      state = 0;
    }

    
  }

  public void update() {
    ///CELLULAR AUTOMATA update function adapted from Joan Soler-Adillon Processing Example.
    //reset state and counted neighbours to 0
    int neighbours = 0;
    int st = 0;
    //for the cells to the left and right of this one (depending on neighbourhood slider)
    for (int x = posX-neighbourhood; x <= posX+neighbourhood; x++) {
    // and for the cells above and below this one (depending on neighbourhood slider)
      for (int y = posY-neighbourhood; y <= posY+neighbourhood; y++) {
        //where this isnt the last of first in a row or column
        if (x>=0 && x<numTiles && y>=0 && y<numThreads) {
          //and it isnt this one
          if (!(x == posX && y == posY)) {
            //and the cell has a crossing
            if (parent.buffer[x][y] == 1 || parent.buffer[x][y] == 2) {
              //add to neighbours count
              neighbours++;
            }
          }
        }
      }
    }

   //if this cell is crossing 1
    if (parent.buffer[posX][posY]==1) {
      // and has less than 2 or more than x neighbours
      if (neighbours < 2 || neighbours > neighbourhood+2) {
        //kill ths cell
        st = 0;
      }
    } else {
      //if the size of the neighbourhood is one
      if (neighbourhood == 1) {
        //and has 2,3or4 neighbours
        if (neighbours == 2 || neighbours == 3 || neighbours == 4 ) {
          //keep alive and pick a random crossing
          int r = 0;
          if(neighbours == 2 || neighbours == 4) {
            r = 1;
          }else{
            r = 2;
          }
          st = r;
        }
      } else if (neighbourhood > 1) {
        //if bigger neighbourhood then allow for more neighbours (or they all die fast from overpopulation)
        if (neighbours == 2 || neighbours == 3 || neighbours == 4 || neighbours == 5 || neighbours == 6) {
          int r = 0;
          if(neighbours ==2 ||  neighbours == 4 || neighbours == 6){
            r = 1;
          }else{
            r = 2;
          }
          st = r;
        }
      }
    }

    // set the state of this tile/cell.
    state = st;
  }
}