// Gurshabd Varaich
// Frogger


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.Scanner;

public class Frogger extends JFrame {
 
    public Frogger(){
		super("Frogger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FroggerPanel game = new FroggerPanel();
		add(game);
		pack();
		setVisible(true);
    }

    public static void main(String[] arguments) {
		Frogger frame = new Frogger();		
    }
}

class FroggerPanel extends JPanel implements KeyListener, MouseListener, ActionListener{
    private Timer myTimer;
    private Timer countDown;
	private Frog frog;// instance of frog
    private Movement[] movements, movements2;// twoarrays of movements for two levels
    private Image pic4; // background pic
    private Image intro, play, highScore, levelUp, continueBtn, returnBtn; // buttons to take player to different screens
    private String mode = "intro";// set original mdoe to intro
    private int currentLane = 10;// set the lane the frog startes at to 10
    private int score = 0;// set teh score to 0
    private int time = 25;
    private int direction;// variable to control frog direction
    private boolean spam = true;// dont let user spam arrow keys
    private Font fnt; // font
    // rectangles of all buttons
    private Rectangle playBtnRect = new Rectangle(155,135,200,155);
    private Rectangle highScoreBtnRect = new Rectangle(150,240,200,155);
    private Rectangle returnBtnRect = new Rectangle(75,75,100,125);
    private Rectangle continueBtnRect = new Rectangle(20,20,200,155);
    private Rectangle landing1 = new Rectangle(15,10,50,25);
    private Rectangle landing2 = new Rectangle(115,10,50,20);
    private Rectangle landing3 = new Rectangle(225,10,50,20);
    private Rectangle landing4 = new Rectangle(335,10,50,20);
    private Rectangle landing5 = new Rectangle(435,10,50,20);

	public FroggerPanel(){
		myTimer = new Timer(50, this);
        frog = new Frog(231, 387, 30);// new instance of frog
        int totalLanes = 11;// total number of lanes equals 11
        movements = new Movement[totalLanes];
        // set all lanes for level 1
        movements[0] = new Movement(0, "", 0, 0, 0, 0);
        movements[1] = new Movement(1, "LOG", 0, 2, 200, 5);
        movements[2] = new Movement(2, "LOG", 1, 2, 350, -5);
        movements[3] = new Movement(3, "LOG", 2, 1, 200, 4);
        movements[4] = new Movement(4, "LOG", 0, 3, 250, -4);
        movements[5] = new Movement(5, "SNAKE", 1, 1, 0, -8);
        movements[6] = new Movement(6, "CAR", 0, 3, 150, 4);
        movements[7] = new Movement(7, "CAR", 1, 2, 150, -4);
        movements[8] = new Movement(8, "CAR", 2, 3, 150, 5);
        movements[9] = new Movement(9, "CAR", 3, 3, 150, -5);
        movements[10] = new Movement(10, "SNAKE", 0, 1, 0, 8);
        movements2 = new Movement[totalLanes];
        // set all lanes for level 2
        movements2[0] = new Movement(0, "", 0, 0, 0, 0);
        movements2[1] = new Movement(1, "LOG", 0, 2, 200, 8);
        movements2[2] = new Movement(2, "ALIGATOR", 1, 2, 350, -8);
        movements2[3] = new Movement(3, "LOG", 2, 1, 200, 8);
        movements2[4] = new Movement(4, "LOG", 0, 2, 250, -9);
        movements2[5] = new Movement(5, "SNAKE", 1, 1, 0, -10);
        movements2[6] = new Movement(6, "CAR", 0, 4, 250, 8);
        movements2[7] = new Movement(7, "CAR", 1, 5, 350, -8);
        movements2[8] = new Movement(8, "CAR", 2, 4, 250, 7);
        movements2[9] = new Movement(9, "CAR", 3, 4, 350, -8);
        movements2[10] = new Movement(10, "SNAKE", 0, 1, 0, 10);
        //load all pictures 
        pic4 = new ImageIcon("background.jpg").getImage();
        pic4 = pic4.getScaledInstance(500, 418, Image.SCALE_SMOOTH);
        intro = new ImageIcon("intro.png").getImage();
        intro = intro.getScaledInstance(500, 418, Image.SCALE_SMOOTH);
        play = new ImageIcon("playBtn.png").getImage();
        play = play.getScaledInstance(200, 155, Image.SCALE_SMOOTH);
        continueBtn = new ImageIcon("continue.png").getImage();
        continueBtn = continueBtn.getScaledInstance(200, 155, Image.SCALE_SMOOTH);
        levelUp = new ImageIcon("levelup.png").getImage(); 
        levelUp = levelUp.getScaledInstance(200, 155, Image.SCALE_SMOOTH);
        highScore = new ImageIcon("highScoreBtn.png").getImage();
        highScore = highScore.getScaledInstance(200, 155, Image.SCALE_SMOOTH);
        returnBtn = new ImageIcon("return.png").getImage();
        returnBtn = returnBtn.getScaledInstance(100, 125, Image.SCALE_SMOOTH);
        
        
        fnt = new Font("Comic Sans", Font.BOLD, 20);
		setPreferredSize(new Dimension(500, 418));
        addKeyListener(this);
        addMouseListener(this);
	}
	
	// addNotify triggers when the Panel gets added to the frame.
	// Using this avoids null-pointer exceptions.
	// x.y() - if x is null, we get null-pointer exception
	@Override
    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        myTimer.start();
    }
	
	public void updateGame(){
        if(mode == "play"){// if it is level 1
            frog.update();// update the frog
            int laneIndex = (((int)(frog.getY() / 38)));// determine which lane the frog is in
            try{
                if(movements[laneIndex].check(frog)){
                    askHighScore();
                    mode = "intro";
                    score = 0;
                    currentLane = 10;
                };// check that lane for collsions
            }
            catch(IndexOutOfBoundsException e){
                System.out.println(e);
            }
            for(Movement lane : movements) {
                lane.update();// update obstacles in that lane
            }
            increaseScore();// increase the score
        }
        else if(mode == "play2"){// if it is level 1
            frog.update();// update the frog
            int laneIndex = (((int)(frog.getY() / 38)));// determine which lane the frog is in
            try{
                if(movements[laneIndex].check(frog)){
                    askHighScore();
                    mode = "intro";
                    score = 0;
                    currentLane = 10;
                };// check that lane for collsions
            }
            catch(IndexOutOfBoundsException e){
                System.out.println(e);
            }
            for(Movement lane : movements2) {
                lane.update();// update obstacles in that lane
            }
            increaseScore();// increase the score
        }
    }
    
    public void increaseScore(){// increase score method
        if((int)(((frog.getY()/38))) == currentLane-1){// if the current lane of the frog is one less than the previous
            score+=10;// increase the score by 10
            currentLane-=1;// set the new lnae the frog is in
            if(currentLane == 1){// if the frog reaches the end
                score+=100;// increase the score by 100
                currentLane = 10;// set the current lane back to the first onw
            }
        }
        if(score == 950){// if the score reaches a certain point
            mode = "level2";// move on to level 2
        }
        else if(score == 1950){// the player beats the game
            mode = "intro";// go back to intro screen
        }
    }

    /*public void decreaseTime(){
        try {
                Thread.spleep(1000);
                time --;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
    }*/


    public void askHighScore(){//method to ask user their name
        // called when frog is hot with something
        ArrayList<String>names = new ArrayList<String>();// arraylsit to store names
        ArrayList<Integer>scores = new ArrayList<Integer>();// arraylist to store scores 
        String name;
        String playerName = JOptionPane.showInputDialog("Enter your Name:");
        //String scoreText = Integer.toString(score);

        try{			
			Scanner inFile = new Scanner(new BufferedReader(new FileReader(new File("highscore.txt"))));
            // add scores and names to array list
			while(inFile.hasNextLine()){
				name = inFile.nextLine();
				int score = Integer.parseInt(inFile.nextLine());
				names.add(name);
				scores.add(score);
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e);
        }
        // if players score is more than the five high scores, add score to array list
        for(int i=0; i<5; i++){
			if(score > scores.get(i)){
				names.add(i, playerName);
				scores.add(i, score);
				break;
			}
        }
        // print the new contents fo array to the file
        try{
			PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter ("highscore.txt")));
			for(int i=0; i<5; i++){
				outFile.println(names.get(i));
				outFile.println(scores.get(i));
			}
			outFile.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
    }

    

	@Override
    public void paint(Graphics g){ // draw images on the screen
        Point mouse = MouseInfo.getPointerInfo().getLocation();// get information from mouse
		Point offset = getLocationOnScreen();
		//int mx = mouse.x - offset.x;
        //int my = mouse.y - offset.y;
        if(mode == "intro"){// if the game mode is intro
    		g.drawImage(intro, 0, 0, null);// draw the background
            g.drawImage(play, 155, 135,null);// draw the play button
            g.drawImage(highScore, 150, 240,null);// draw the highscore button
        }
        else if(mode == "highscore"){// if the game mode is high score
            ArrayList<String>nameScores = new ArrayList<String>();
            g.setColor(Color.black);// draw black background
            g.fillRect(0, 0, 500, 418);
            g.drawImage(returnBtn, 75, 75, null);// draw return button
            String line1, line2;
            // print top 5 high scores onto screen
            try{			
                Scanner inFile = new Scanner(new BufferedReader(new FileReader(new File("highscore.txt"))));
    
                while(inFile.hasNextLine()){
                    String nameScore = inFile.nextLine();
                    nameScores.add(nameScore);
                }
            }
            catch(FileNotFoundException ex){
                System.out.println("Where did you put scores.txt?");
            }
            for(int i = 0; i < 10; i+=2){
                line1 = nameScores.get(i);
                line2 = nameScores.get(i+1); 
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString(line1+"     "+line2, 235, i*25+50);
            }
        }
        else if (mode == "play"){// if game mode is play level 1
            g.drawImage(pic4, 0, 0, null);// draw background
            for(Movement lane : movements){// move obstacles in lanes
                lane.draw(g);
            }
            frog.draw(g, direction);// draw the frog
            g.setFont(fnt);// set the font
            g.setColor(Color.yellow);
            g.drawString("Score: "+score, 10, 410);// display the highscore
            //g.setFont(fnt);
            //g.setColor(Color.yellow);
            g.drawString("Time: "+time, 400, 410);// display the time
        }

        else if(mode == "level2"){// if game mode is about to play level 2
            score = 1000;// increase score to 1000
            // show screen that instructs user to press space to continue
            g.setColor(Color.white);
            g.setFont(fnt);
            g.drawString("Press Space to Continue", 20,20);
            g.setColor(Color.black);
            g.fillRect(0,0,500,418);
            g.drawImage(levelUp, 220, 200, null);
            //g.drawImage(continueBtn, 20, 20,null);
        }
        else if (mode == "play2"){// if game mode is play level 2
            g.drawImage(pic4, 0, 0, null);// draw background image
            for(Movement lane : movements2){// move obstacles in lanes
                lane.draw(g);
            }
            frog.draw(g, direction);// draw the frog
            g.setFont(fnt);// set the font
            g.setColor(Color.yellow);
            g.drawString("Score: "+score, 10, 410);// draw the score
            g.drawString("Time: "+time, 400, 410);// display the time
        }	
    }
	@Override
    public void keyTyped(KeyEvent e) {}

	@Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){// if playerpresses space move onto level 2;
        	mode = "play2";
        }
        // move frog in direction the player presses with the arrow key
        if(spam && e.getKeyCode() == KeyEvent.VK_UP && (frog.getY() <= 462)){
            direction = 0;
            frog.move(0, -1);
            spam = false;// prevents user from spamming
             
        }
        else if(spam && e.getKeyCode() == KeyEvent.VK_DOWN && (frog.getY() < 424 )){
            direction = 2;
            frog.move(0, 1);
            spam = false;
        }
        else if(spam && e.getKeyCode() == KeyEvent.VK_RIGHT && (frog.getX() + frog.getW() < 489)){
            direction = 1;
            frog.move(1, 0);
            spam = false;
        }
        else if(spam && e.getKeyCode() == KeyEvent.VK_LEFT && (frog.getX() > 5)){
            direction = 3;
            frog.move(-1, 0);
            spam = false;
        }
    }
    
	@Override
    public void keyReleased(KeyEvent e){
        spam = true;// set spam back to true so that user can move frog again
    }

    @Override
    public void mouseClicked(MouseEvent e){}

	@Override
    public void mouseEntered(MouseEvent e){}

	@Override
    public void mouseExited(MouseEvent e){}

	@Override
    public void mousePressed(MouseEvent e){
    	if(playBtnRect.contains(e.getPoint())){
    		mode = "play";// if player clicks on play button go to level 1
        }
        else if(highScoreBtnRect.contains(e.getPoint())){
            mode = "highscore";// if player clicks on high score button go to high score
        }
        else if(returnBtnRect.contains(e.getPoint())){
            mode = "intro";// if player clicks on return button go to main screen
        }
    }

	@Override
    public void mouseReleased(MouseEvent e){}


	@Override
	public void actionPerformed(ActionEvent evt){
		updateGame(); // update game 
		repaint();   // Asks the JVM to indirectly call paint
    }
}