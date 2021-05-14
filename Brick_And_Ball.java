 /**
  * Brick and Ball
  * Authors - Jay Sharma, Varun Agarwal
  */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Brick_And_Ball extends JPanel implements KeyListener, MouseMotionListener, MouseListener
{
    JFrame f;
    int bx,by,px,bh,bw,bdx,bdy,prevx,broken;
    int bv[][]=new int[8][6];
    javax.swing.Timer timer;
    boolean start;
    JLabel gameover,restart,gamewon,lscore;
    int score=0;
    Brick_And_Ball()
    {
        f=new JFrame("BRICK AND BALL");
        f.setBounds(0,0,1000,700);
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);

        setBounds(0,0,1000,700);
        setVisible(true);
        setBackground(Color.black);
        setLayout(null);
        f.addKeyListener(this);        
        f.addMouseListener(this);
        f.addMouseMotionListener(this);

        gameover=new JLabel("GAME OVER YOU LOSE");
        gameover.setBounds(0,400,1000,100);
        gameover.setFont(new Font("CASTELLAR",Font.BOLD,36));
        gameover.setHorizontalAlignment(SwingConstants.CENTER);
        gameover.setForeground(Color.RED);
        gameover.setVisible(false);
        add(gameover);

        gamewon=new JLabel("CONGRATULATIONS YOU WON");
        gamewon.setBounds(0,400,1000,100);
        gamewon.setFont(new Font("CASTELLAR",Font.BOLD,36));
        gamewon.setHorizontalAlignment(SwingConstants.CENTER);
        gamewon.setForeground(Color.RED);
        gamewon.setVisible(false);
        add(gamewon);

        restart=new JLabel("PRESS 'R' OR CLICK TO RESTART");
        restart.setBounds(0,500,1000,100);
        restart.setFont(new Font("CASTELLAR",Font.BOLD,36));
        restart.setHorizontalAlignment(SwingConstants.CENTER);
        restart.setForeground(Color.RED);
        restart.setVisible(false);
        add(restart);

        lscore=new JLabel("SCORE = 0");
        lscore.setBounds(800,10,200,30);
        lscore.setFont(new Font("CASTELLAR",Font.BOLD,16));
        lscore.setHorizontalAlignment(SwingConstants.CENTER);
        lscore.setForeground(Color.RED);
        lscore.setVisible(true);
        add(lscore);

        reset();
    }

    void reset()
    {
        Random random=new Random();
        by=random.nextInt(200)+400;
        bx=random.nextInt(600)+200;
        px=random.nextInt(750)+50;
        bdx=random.nextInt(2)%2==0?-1:1;
        bdy=-1;
        bh=50;
        bw=100;
        score=0;
        broken=0;
        for(int i=0;i<8;i++)
            for(int j=0;j<6;j++)
                bv[i][j]=1;
        gameover.setVisible(false);
        gamewon.setVisible(false);
        restart.setVisible(false);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for(int i=100;i<=800;i+=100)
            for(int j=50;j<=300;j+=50)
            {
                if(bv[i/100-1][j/50-1]==1)
                {
                    g.setColor(Color.white);
                    g.fillRect(i,j,bw,bh);
                    g.setColor(Color.black);
                    g.drawRect(i,j,bw,bh);
                }
        }
        g.setColor(Color.yellow);
        g.fillOval(bx,by,20,20);
        
        g.setColor(Color.red);
        g.fillRect(0,695,1000,5);
        
        g.setColor(Color.green);
        g.fillRect(px,635,200,15);

        lscore.setText("SCORE = "+String.valueOf(score));
        if(start)
        {
            bx+=7*bdx;
            by+=7*bdy;
            if(bx<=0||bx>=980)
                bdx*=-1;
            if(by<=0)
                bdy*=-1;
            if(by>=700)
            {
                start=false;
                //timer.stop();
                gameover.setVisible(true);
                restart.setVisible(true);
            }
            if(bx>=px&&bx<=px+200&&by<=625&&by>=617)//new Rectangle(bx,by,20,20).intersects(new Rectangle(px,735,200,15)))
                bdy*=-1;
            if(broken==48)
            {
                gamewon.setVisible(true);
                restart.setVisible(true);
                start=false;
            }
            A: for(int i=100;i<=800;i+=100)
                for(int j=50;j<=300;j+=50)
                {
                    if(new Rectangle(bx,by,20,20).intersects(new Rectangle(i,j,bw,bh))&&bv[i/100-1][j/50-1]==1)
                    {
                        if(bx-5*bdx<=i||bx-5*bdx>=i+bw)
                            bdx*=-1;
                        else
                            bdy*=-1;
                        bv[i/100-1][j/50-1]=0;
                        broken++;
                        score+=10;
                        break A;
                    }
            }
        }
        try
        {
            Thread.sleep(20);
        }
        catch(Exception e){}
        repaint();
    }

    public void keyTyped(KeyEvent e){}

    public void keyReleased(KeyEvent e){}

    public void mouseDragged(MouseEvent e){}
    
    public void mouseClicked(MouseEvent e)
    {
        if(start == false)
        {
            reset();
            start = true;
        }
    }
    
    public void mousePressed(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}
    
    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            start=true;
            if(1000-(px+200)<=40)
                px=800;
            else
                px+=40;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {
            start=true;
            if(px<=40)
                px=0;
            else
                px-=40;
        }
        if((e.getKeyCode()==82||(e.getKeyCode()==82&&e.getKeyCode()==20))&&start==false)
        {
            reset();    
        }
    }

    public void mouseMoved(MouseEvent e)
    {
        if(e.getX()<=900&&e.getX()>=100)
        px = e.getX()-100;  
        else if(e.getX()<100)
        px = 0;
        else
        px = 800;
        repaint();
    }

    public static void main(String args[])
    {
        Brick_And_Ball obj=new Brick_And_Ball();
    }
}
