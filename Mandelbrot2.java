import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;

public class Mandelbrot2 extends Applet implements MouseListener{
  private Image display;//Frame
  private Graphics drawingArea;//Canvas
  private int height;
  private int width;
  private double imagCenter;
  private double imagRadius;
  private double realCenter;
  private double realRadius;
  private static int[][] mapping = {{66, 30, 15},{25, 7, 26},{9, 1, 47},{4, 4, 73},{0, 7, 100},{12, 44, 138},{24, 82, 177},{57, 125, 209},
  {134, 181, 229},{211, 236, 248},{241, 233, 191},{248, 201, 95},{255, 170, 0},{204, 128, 0},{153, 87, 0},{106, 52, 3}};
  private Color[] colorsmap;

  private boolean mouse;
  private int xpos;
  private int ypos;


  public void init() {
    height = getSize().height;
    width = getSize().width;

    imagCenter = 0.0;
    imagRadius = 2.0;

    realCenter = 0.0;
    realRadius = 2.5;

    //Generate colorsmap
    colorsmap = new Color[16];
    for(int i = 0; i < 16; i++) {
      colorsmap[i] = new Color(mapping[i][0],mapping[i][1],mapping[i][2]);
    }

    mouse = false;

    addMouseListener(this);

    display = createImage(width, height);
    drawingArea = display.getGraphics();
    mandel();
  }//init


  public void mandel() {
    for(int xpixel = 0; xpixel <= width; xpixel++) {
      for(int ypixel = 0; ypixel <= height; ypixel++) {
        double x0 = (double)mf(xpixel,0,width,-realRadius+realCenter,realRadius+realCenter);
        double y0 = (double)mf(ypixel,0,height,-imagRadius+imagCenter,imagRadius+imagCenter);
        double x = 0.0;
        double y = 0.0;
        int n = 0;
        for(; n < 255; n++) {
          if(x*x+y*y > 4) break;
          double xtemp = x*x - y*y + x0;
          y = 2*x*y + y0;
          x = xtemp;
        }
        int z = 255 - n;
        Color c = new Color(z,z,z);
        if(n > 0 && n < 255) {
          c = colorsmap[n%16];
        }
        drawingArea.setColor(c);
        drawingArea.fillRect(xpixel,ypixel,1,1);
      }
    }
  }

  public void paint(Graphics g) {
    g.drawImage(display, 0, 0, null);
  }//paint


  public int m(int x, int r1min, int r1max, int r2min, int r2max) {
    double z = (x*1.-r1min)/(r1max-r1min);
    double transform = z*(r2max-r2min) +r2min;
    return (int) transform;
  }

  public double mf(double x, double r1min, double r1max, double r2min, double r2max) {
    double z = (x*1.-r1min)/(r1max-r1min);
    double transform = z*(r2max-r2min) +r2min;
    return transform;
  }

  public void zoom() {
    if(mouse) {
      double newx = mf(xpos*1.0, 0.0, width*1.0, -realRadius+realCenter, realRadius+realCenter);
      double newy = mf(ypos*1.0, 0.0, height*1.0, -imagRadius+imagCenter, imagRadius+imagCenter);
      realCenter = newx;
      imagCenter = newy;
      realRadius *= .6;
      imagRadius *= .6;
      mandel();
      repaint();
    }
  }

  // MouseListener
  public void mousePressed(MouseEvent e)
   {
   }
   public void mouseReleased(MouseEvent e)
   {
   }
   public void mouseEntered(MouseEvent me)
   {

   }
   public void mouseClicked(MouseEvent me)
   {
     mouse = true;
     xpos = me.getX();
     ypos = me.getY();
     zoom();
   }
   public void mouseExited(MouseEvent e)
   {
     mouse = false;
   }
}//Mandelbrot
