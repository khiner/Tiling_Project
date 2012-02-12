import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Robinson_Tiling extends PApplet {

Tile tile0 = new Tile(new int[][]{{-1, -6}, {0, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {4, 0}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {0, 4}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-4, 0}, {-6, -1}, {-6, -5}, {-5, -6}},
                      color(255, 120, 0));
Tile tile1 = new Tile(new int[][]{{-1, -6}, {0, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {4, -1}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {0, 4}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-4, -1}, {-6, -1}, {-6, -5}, {-5, -6}},
                      color(84, 153, 179));
Tile tile2 = new Tile(new int[][]{{-1, -6}, {-1, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {4, 0}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {-1, 4}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-4, 0}, {-6, -1}, {-6, -5}, {-5, -6}},
                      color(201, 181, 66));
Tile tile3 = new Tile(new int[][]{{-1, -6}, {-1, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {4, -1}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {-1, 4}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-4, -1}, {-6, -1}, {-6, -5}, {-5, -6}},
                      color(153, 186, 92));
Tile tile4 = new Tile(new int[][]{{-1, -6}, {1, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {4, 0}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {1, 4}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-4, 0}, {-6, -1}, {-6, -5}, {-5, -6}},
                      color(201, 181, 66));
Tile tile5 = new Tile(new int[][]{{-1, -6}, {1, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {4,-1}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {1, 4}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-4, -1}, {-6, -1}, {-6, -5}, {-5, -6}},
                      color(153, 186, 92));
Tile startTile = new Tile(new int[][]{{-1, -6}, {0, -8}, {1, -6}, {5, -6}, {6, -5}, {6, -1}, {8, -1}, {6, 1}, {6, 5}, {5, 6}, {1, 6}, {-1, 8}, {-1, 6}, {-5, 6}, {-6, 5}, {-6, 1}, {-8, 0}, {-6, -1}, {-6, -5}, {-5, -6}},
                          color(122, 28, 143));
Tile endTile = new Tile(new int[][]{{-1, -6}, {0, -8}, {1, -6}, {5, -6}, {6, -7}, {7, -6}, {6, -5}, {6, -1}, {8, -1}, {6, 1}, {6, 5}, {7, 6}, {6, 7}, {5, 6}, {1, 6}, {-1, 8}, {-1, 6}, {-5, 6}, {-6, 7}, {-7, 6}, {-6, 5}, {-6, 1}, {-8, 0}, {-6, -1}, {-6, -5}, {-7, -6}, {-6, -7}, {-5, -6}},
                        color(200));
List<Tile> tiles = new ArrayList<Tile>();
int sz = 512;
int order = 3;
int n = 1 << order;
int t = 3*(1 << 5 - order);
int[][] corners = {{-1,1}, {1,1}, {1,-1}, {-1,-1}};
int[][] sides = {{-1,0}, {0,1},{1,0},{0,-1}};
float[] rotates = {-HALF_PI, PI, HALF_PI, 0};
int[] tileIndices = {1,5,3,1};

public void setup() {
  size(sz, sz);
  stroke(0);
  strokeWeight(2);
  tiles.add(tile0);
  tiles.add(tile1);
  tiles.add(tile2);
  tiles.add(tile3);
  tiles.add(tile4);
  tiles.add(tile5);
  tiles.add(startTile);
  tiles.add(endTile);
  noLoop();
}  

public void draw() {
  background(255);
  translate(sz/2, sz/2);
  rotate(-HALF_PI);
  drawTiles(n);
}

public void drawTiles(int i) {
  if (i == 1) {
    startTile.draw();
    for (int j = 0; j < corners.length; ++j) {
      pushMatrix();
      translate(t*corners[j][0], t*corners[j][1]);
      rotate(rotates[j]);
      endTile.draw();
      popMatrix();
    }
    for (int j = 0; j < sides.length; ++j) {
      pushMatrix();
      translate(t*sides[j][0], t*sides[j][1]);
      rotate(rotates[j]);
      tiles.get(tileIndices[j]).draw();
      popMatrix();
    }
  } else {
    startTile.draw();
    for (int j = 0; j < corners.length; ++j) {
      pushMatrix();
      translate(t*i*corners[j][0], t*i*corners[j][1]);
      rotate(rotates[j]);
      drawTiles(i/2);
      popMatrix();
    }
    for (int j = 0; j < sides.length; ++j) {
      for (int k = 1; k < i*2; ++k) {
        pushMatrix();
        translate(t*sides[j][0]*k, t*sides[j][1]*k);
        rotate(rotates[j]);
        if (k == i) {
          if (j == 0 || j == 3)
            tiles.get(1).draw();
          else if (j == 2)
            tiles.get(3).draw();
          else
            tiles.get(5).draw();
        } else {
          if (j == 0 || j == 3)
            tiles.get(0).draw();
          else if (j == 2)
            tiles.get(2).draw();
          else
            tiles.get(4).draw();
        }
        popMatrix();
      }
    }
  }
  
}

class Tile {
  int[][] vertices;
  int c;
  public Tile(int[][] vertices, int c) {
    this.vertices = vertices;
    this.c = c;
  }
  public void draw() {
    fill(c);
    beginShape();
    for (int[] pos : vertices) {
      vertex(pos[0]*8/n, pos[1]*8/n);
    }
    endShape(CLOSE);
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#DFDFDF", "Robinson_Tiling" });
  }
}
