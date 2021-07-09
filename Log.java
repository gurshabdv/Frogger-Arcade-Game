import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Log {// controls the position and moving of the log
    private int x, y;// the position of the log
    public int speed;// the speed that the log is moving at
    private Image pic1, pic3;// images to store images of different sized logs
    private BufferedImage pic, pic2;// bufferd images to calculate width of different logs
    private int width, height, width1, height1;// different widths and heights of different logs
    private int typeNumber;// the type of log

    Log(int x, int y, int speed, int typeNumber) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.typeNumber = typeNumber;
        BufferedImage[] pics = new BufferedImage[3];// set an array of size 3 of buffered images
        Image[] pics1 = new Image[3];// set an array of size 3 of  images
        int[] widths = new int[3];// set an array of size 3 of  image widths
        int[] heights = new int[3];// set an array of size 3 of image heights
        // load all log images into array
        for(int i = 0; i < 3; i++){
            pic1 = new ImageIcon("log"+i+".png").getImage();
            pics1[i] = pic1;
        }
        pic3 = pics1[typeNumber];// set the pic that is going to be used based on typeNumber parameter
        // get the widths and heights of all log pics and store them in arrays
        try{    
            for(int j = 0; j < 3; j++){
                pic = ImageIO.read(new File("log"+j+".png"));
                width = pic.getWidth();
                height = pic.getHeight();
                pics[j] = pic;
                widths[j] = width;
                heights[j] = height; 
            }
            pic2 = pics[typeNumber];// set the pic that is going to be used based on typeNumber parameter
            width1 = widths[typeNumber];// set the pic width that is going to be used based on typeNumber parameter
            height1 = heights[typeNumber];// set the pic height that is going to be used based on typeNumber parameter 
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
  
    public void update() {// update position of log
        x = x + speed;// increase the x position by the speed
        // if the log goes off the screen loop it back around
        if (speed > 0 && x > 500) {
            x = -350;
        } 
        else if (speed < 0 && x < -350) {
            x = 500;
        }
    }

    public Rectangle getRect(){// get rect drawn around each log
        return new Rectangle(x, y, width1, height1 - 5);
    }
  
    public void draw(Graphics g){// draw the log
        g.drawImage(pic3, x, y, null);
    }
}