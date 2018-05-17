import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;

public class Mandelbrot extends Applet implements MouseListener{
  private Image display;//Frame
  private Graphics drawingArea;//Canvas
  private int height;
  private int width;
  private double imagCenter;
  private double imagRadius;
  private double realCenter;
  private double realRadius;

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
        if(n > 10 && n < 245) {
          switch((n % 6)) {
            case 0: c = new Color(n,n,z);
                    break;
            case 1: c = new Color(n,z,n);
                    break;
            case 2: c = new Color(z,n,n);
                    break;
            case 4: c = new Color(z,z,n);
                    break;
            case 5: c = new Color(z,n,z);
                    break;
            case 3: c = new Color(n,z,z);
                    break;
          }
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
