import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Movement{// movement class acts as lanes that obstacles can travel on
    private Vehicle[] vehicles;// array of vehicles
    private Log[] logs;// array of logs
    private Snake[] snakes;// array of snakes
    private Aligator[] aligators;// array of aligators
    private String type;// parameter indicate type of obstacle
    private int laneNumber, typeNumber, n, spacing, speed;//parameters to indicate lane numebr, type of obstacle,  number of onstacles, spacing of obstacles, speed of obstacles
    private Rectangle firstLane = new Rectangle(0,390,500,25);// rect around first lane
    private Rectangle Road = new Rectangle(0,233,500,152); // rect around road 
    private Rectangle secondLane = new Rectangle(0,205,500,25); // rect around second median
    private Rectangle Water = new Rectangle(0,43,500,157); // rect around water
    private Rectangle landing1 = new Rectangle(15,10,50,25);// rectangles of all landing positions
    private Rectangle landing2 = new Rectangle(115,10,50,20);
    private Rectangle landing3 = new Rectangle(225,10,50,20);
    private Rectangle landing4 = new Rectangle(335,10,50,20);
    private Rectangle landing5 = new Rectangle(435,10,50,20);
    private Rectangle[] landings = {landing1,landing2,landing3,landing4,landing5};// array with all landing positions
    private int lives = 3;// set the frgos lives to 3
    private boolean[] checkLandings = new boolean[5];// boolean array to check which landing positions have been reached
    private SoundEffect sound1 = new SoundEffect("plunk.wav");// sound to play when frog drowns
    private SoundEffect sound2 = new SoundEffect("squash.wav");// sound to play when frog gets run over
    private Image pad; // lilypad image when landing area has been reached
    private Image health0, health1, health2;// health bars

  
    Movement(int laneNumber, String type, int typeNumber,int n, int spacing, int speed){
        this.laneNumber = laneNumber;
        this.type = type;
        this.typeNumber = typeNumber;
        this.n = n;
        this.spacing = spacing;
        this.speed = speed;
        // load all images
        pad = new ImageIcon("lilypad.png").getImage(); 
        pad = pad.getScaledInstance(40, 15, Image.SCALE_SMOOTH);
        health0 = new ImageIcon("health0.png").getImage(); 
        health0 = health0.getScaledInstance(90, 20, Image.SCALE_SMOOTH);
        health1 = new ImageIcon("health1.png").getImage(); 
        health1 = health1.getScaledInstance(90, 20, Image.SCALE_SMOOTH);
        health2 = new ImageIcon("health2.png").getImage(); 
        health2 = health2.getScaledInstance(90, 20, Image.SCALE_SMOOTH);
        //set the array sizes to the numebr of obstacles parameter
        vehicles = new Vehicle[n];
        logs = new Log[n];
        snakes = new Snake[n];
        aligators = new Aligator[n];
        Random rand = new Random();//get a random number to choose random x position of where obstacles start
        int offset = rand.nextInt(201);
        for (int i = 0; i < n; i++){// loop the the parameter that contains number of obstacles
            //create new instances based the on type parameter which declares the type of obstacle that will be in the lane
            if(type == "CAR"){
                vehicles[i] = new Vehicle(offset + spacing * i, laneNumber*38, speed, typeNumber);
            }
            else if(type == "LOG"){
                logs[i] = new Log(offset + spacing * i, laneNumber*41, speed, typeNumber);
            }
            else if(type == "SNAKE"){
                snakes[i] = new Snake(-(offset+spacing*i)*15, laneNumber*38, speed, typeNumber);             
            }
            else if (type == "ALIGATOR"){
                aligators[i] = new Aligator(offset+spacing*i, laneNumber*41, speed);
            }
        }
    }
  
  
    public boolean check(Frog frog) {// check method to check for frog collsions
        boolean gameOver = false;
        if (type == "CAR" && (frog.getRect().intersects(Road))) {// if frog rect intersects with road rectangle
            for (Vehicle v : vehicles) {// loop though all cars in lane
                if (v.getRect().intersects(frog.getRect())) {// see if the rectangles intersect
                    sound2.play();// play sound
                    frog.reset();// rest frog position
                    gameOver = true;
                }
                
            }
        } 
        else if (type == "LOG" && (frog.getRect().intersects(Water))) {// if frog rect intersects with water rectangle
            boolean drowning = true;//set boolean variabe to determine if frog touches the water
            for (Log l : logs) {// loop though all logs in lane
                if (l.getRect().intersects(frog.getRect())){// see if rectangles intersect
                    frog.attach(l);// attach frog to the log
                    drowning = false;// set the frog to not drowning
                }
            }
            if(drowning){// if the frog has not attached on
                sound1.play();// play drowning sound
                frog.reset();// reset frog positon
                gameOver = true;
            }
        }
        else if (type == "SNAKE" && ((frog.getRect().intersects(firstLane) || frog.getRect().intersects(secondLane)))){// if frog rect intersects with median rectangles
            
            for(Snake s : snakes){// loop though all snakes in lane
                if (s.getRect().intersects(frog.getRect())){// see if rectangles intersect
                    sound2.play();// play sound
                    frog.reset();// reset frog positon
                    gameOver = true;
                }
            }
        }
        else if (type == "ALIGATOR" && (frog.getRect().intersects(Water))) {// if frog rect intersects with water rectangle
            boolean drowning = true;//set boolean variabe to determine if frog touches the water
            for (Aligator a :aligators) {// loop though all alligators in lane
                if (a.getBadRect().intersects(frog.getRect())){// see if frog is on alligator head
                    sound2.play();// play drowning sound
                    frog.reset();// reset frog positon
                }
                else if(a.getGoodRect().intersects(frog.getRect())){// see if frog is on alligator body
                    frog.attach1(a);// attach frog to the log
                    drowning = false;// set the frog to not drowning
                }

            }
            if(drowning){// if the frog has not attached on
                sound1.play();// play drowning sound
                frog.reset();// reset frog positon
                gameOver = true;
            }
        }
        else if (type == "" && ((frog.getRect().intersects(landing1)) || (frog.getRect().intersects(landing2)) || (frog.getRect().intersects(landing3)) || (frog.getRect().intersects(landing4)) || (frog.getRect().intersects(landing5)))){// if frog rect intersects with landing rectangles
            boolean landed = false;// check to see if frog has already landed
            for(int i = 0; i < 5; i++){// loop through 5 landing positions 
                if((frog.getRect().intersects(landings[i])) && checkLandings[i] == false){// if rectangles intersedct
                    frog.reset();//reset frog
                    checkLandings[i] = true;// set the landing to true
                    landed = true;   // set landed to ture                 
                }
            }
            if(!(landed)){// if frog has already landed at that position before
                frog.reset();// reset the frog position
                gameOver = true;
            }
           
                        
        }
        if(gameOver){
            return true;
            
        }
        else{
            return false;
        }
    }
  
    public void draw(Graphics g) {//draw method
        // depending on the type in the lane loop through all instances of classes and draw them
        if(type == "LOG"){
            for (Log l : logs) {
                l.draw(g);
            }
            
        }
        else if(type == "CAR"){
            for (Vehicle v : vehicles) {
                v.draw(g);
                
            }
        }
        else if(type == "SNAKE"){
            for(Snake s: snakes){
                s.draw(g);
            }          
        }
        else if(type == "ALIGATOR"){
            for(Aligator a: aligators){
                a.draw(g);
            }          
        }
        else if(type == ""){
            for(int j = 0; j < 5; j++){
                if(checkLandings[j] == true){
                    g.drawImage(pad, landings[j].x+10, landings[j].y+10, null);// draw lilypads when a frog lands in a new position
                }
            }
        }
    }

    public int getLives(){// get frogs lives
        return lives;
    }

    public Snake[] getSnakes(){// get snake array
        return snakes;
    }

    public Log[] getLogs(){// get log array
        return logs;
    }

    public Vehicle[] getVehicles(){// get vehicle array
        return vehicles;
    }

    public boolean[] getCheckLandings(){// get landings array
        return checkLandings;
    }

    public void update(){// update positions of all instances of classes
        if(type == "LOG"){
            for (Log l : logs) {
                l.update();
            }
        }
        else if(type == "CAR"){
            for (Vehicle v : vehicles) {
                v.update();
            }
        }
        else if(type == "SNAKE"){
            for (Snake s: snakes){
                s.update();
            }
        }
        else if(type == "ALIGATOR"){
            for (Aligator a: aligators){
                a.update();
            }
        }
    }
}