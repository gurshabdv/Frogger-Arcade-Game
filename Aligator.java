import java.awt.*;
import javax.swing.*;

public class Aligator {// alligator class that controls the position of the alligatoe and moving of the alligator
    private int x, y;// pos of aligator
    public int speed;// speed of aligator
    private Image pic;// picture of alligator
    
    Aligator(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        pic = new ImageIcon("aligator.png").getImage();//load the image
        pic = pic.getScaledInstance(175, 38, Image.SCALE_SMOOTH);// set the image to the right size
    }

    public void update() {// update the alligatos position
        x = x + speed;// increase the x coord by the speed
        if (speed > 0 && x > 500) {// if the aligaot ris off the screen loop it back around
            x = -350;
        } 
        else if (speed < 0 && x < -350) {
            x = 500;
        }
    }

    public Rectangle getBadRect(){// get the rect that covers that aligators head
        // if the frog touches this the frog will be reset
        return new Rectangle(x, y, 40, 38);
    }

    public Rectangle getGoodRect(){// get the rect that covers the aligators body
        //if the frog touches this the frog will be safe
        return new Rectangle(x+40, y, 135, 38);
    }
  
    public void draw(Graphics g) {// draw the alligator
        g.drawImage(pic, x, y, null);
    }
}
