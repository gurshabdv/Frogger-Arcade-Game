import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Vehicle{// vehicle class that controls the position of the vehicle and moving of the vehicle
    private int x, y;// pos of vehicle
    private int speed;// speed of vehicle
    private BufferedImage pic, pic2;// bufferd pics of vehicle to get width and height of diff vehicles
    private Image pic1, pic3;// normal images of vehicles
    private int width, height, width1, height1;// widtsh and height of vehicles
    private int typeNumber; // type of picture of vehicle
  
    Vehicle(int x, int y, int speed, int typeNumber) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.typeNumber = typeNumber;
        BufferedImage[] pics = new BufferedImage[5];// set buffered image array of size 5
        Image[] pics1 = new Image[5];//set pic array of size 5
        int[] widths = new int[5];// set width array of size 5
        int[] heights = new int[5];// set height array of size 5
        for(int i = 0; i < 5; i++){// load all vehicle pictures
            pic1 = new ImageIcon("car"+i+".png").getImage();
            pics1[i] = pic1;
        }
        pic3 = pics1[typeNumber];// pick the picture to be used based on typeNumebr parameter
        try{// load bufferd images pictures and widths and heights of pictures
            for(int j = 0; j < 5; j++){
                pic = ImageIO.read(new File("car"+j+".png"));
                width = pic.getWidth();
                height = pic.getHeight();
                pics[j] = pic;
                widths[j] = width;
                heights[j] = height; 
            }
            pic2 = pics[typeNumber];// pick the picture to be used based on typeNumebr parameter
            width1 = widths[typeNumber];// pick the picture width to be used based on typeNumebr parameter
            height1 = heights[typeNumber]; // pick the picture height to be used based on typeNumebr parameter
        }
        catch (IOException e) {
            System.out.println(e);
        }
        
    }
  
    public void update() {//update position of vehicle by changing x coord by speed
        x = x + speed;
        if (speed > 0 && x > 500) {// if vehicle goes off the screen loop it back around
            x = -350;
        } 
        else if (speed < 0 && x < -350) {
            x = 500;
        }
    }

    public Rectangle getRect(){// get rectangle around vehicle
        return new Rectangle(x, y, width1, height1);
    }
  
    public void draw(Graphics g) {// draw the vehicle
        g.drawImage(pic3, x, y, null);
    }
}