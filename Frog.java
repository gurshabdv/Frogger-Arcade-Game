import javax.swing.*;
import java.awt.*;

public class Frog{// frog class that controls the position of the frog and moving of the frog
    // set the frog nt attached to the alligator or log so that it does not move with it
    private Log attached = null;
    private Aligator attached1 = null;
    private int x, y, w;// variables to draw the frog and the forg's rectangle
    private Image pic00, pic01,pic02,pic03;// all pictures of the frog depending on which way its going
    private SoundEffect sound = new SoundEffect("hop.wav");// sound of frog hopping
    
    Frog(int x, int y, int w) {// load in all pictures of frog
        this.x = x;
        this.y = y;
        this.w = w;
        pic00 = new ImageIcon("frog00.png").getImage();
        pic00 = pic00.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        pic01 = new ImageIcon("frog01.png").getImage();
        pic01 = pic01.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        pic02 = new ImageIcon("frog02.png").getImage();
        pic02 = pic02.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        pic03 = new ImageIcon("frog03.png").getImage();
        pic03 = pic03.getScaledInstance(30, 30, Image.SCALE_SMOOTH);        
    }

    public Rectangle getRect(){// the rectangle surrounding the frog
        return new Rectangle(x, y, 30, 30);
    }

    public int getY(){// get y positoin of frog
        return y;
    }

    public int getX(){// het x position of frog
        return x;
    }
    
    public int getW(){// get width of frog
        return w;
    }
    public void attach(Log log) {// attach the frog to the log
        attached = log;
    }

    public void attach1(Aligator alligator){// attach the frog to the alligator
        attached1 = alligator;
    }
    
    public void update() {// if the frog is attach to the alligator or log move it at the atached objects speed
        if(attached != null) {
            x += attached.speed;
            if(x < 0 || x + w > 500){//  if the frogs goes of the screen reset the frog to original position
                reset();
            }
        }
        else if(attached1 != null){// same concept except for alligator
            x += attached1.speed;
            if(x < 0 || x + w > 500){
                reset();
            }
        }
    }
    
    public void draw(Graphics g, int direction) {// draw all images of frog based on which way it is facing
        if(direction == 0){
            g.drawImage(pic00, x, y, null);
        }
        else if(direction == 1){
            g.drawImage(pic01, x, y, null);
        }
        else if(direction == 2){
            g.drawImage(pic02, x, y, null);
        }
        else if(direction == 3){
            g.drawImage(pic03, x, y, null);
        }
    }
    
    public void move(int xdir, int ydir) {// move the frog
        sound.play();// play the hopping sound
        x += xdir * 38;// the x or y positoin by 38 depending on the direction the frog is moving
        y += ydir * 38;
        attach(null);// unattach the frog from alligator or log
        attach1(null);
    }

    public void reset(){// reset the frog position if it is hit by something
        x = 231;
        y = 387;
        attach(null);// unattach the frog from alligator or log
        attach1(null);
    }
}