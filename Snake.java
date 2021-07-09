import java.awt.*;
import javax.swing.*;

public class Snake {// snake class that controls the position of the snake and moving of the snake
    private int x, y;// position of snake
    public int speed;// speed of snake
    private Image pic, pic1;// pics of snakes
    private int typeNumber;// type of snake to be used (picture)

    Snake(int x, int y, int speed, int typeNumber){
        pic = new ImageIcon("snake.png").getImage();// load snake image
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.typeNumber = typeNumber;
        Image[] pics1 = new Image[2];// set array with two possible images of snakes (depending on direction they are going)
        for(int i = 0; i < 2; i++){// load all images into array
            pic = new ImageIcon("snake"+i+".png").getImage();
            pics1[i] = pic;
        }
        pic1 = pics1[typeNumber];// pick the image to be used based on typeNumber parameter
    }

    public void update() {// update position of snake
        x += speed;// increase the x value by the speed
        if (speed > 0 && x > 500) {// if the snake is off the screen loop it back around
            x = -1800;
        } 
        else if (speed < 0 && x < -350) {
            x = 1800;
        }
    }

    public Rectangle getRect(){// get rectangle drawn around snake
        return new Rectangle(x, y, 95, 38);
    }

    public void reset(){// reset the snakes position if it hits the frog
        x = -1800;
    }
  
    public void draw(Graphics g) {// draw the snake
        g.drawImage(pic1, x, y, null);
    }
}