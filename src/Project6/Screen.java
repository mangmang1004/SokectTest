package Project6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

public class Screen extends JFrame implements MouseListener {

    Board board = new Board();
    Container contentPane = getContentPane();

    //JPanel scorePanel = new JPanel();
    JLabel greenScoreLabel;
    JLabel apricotScoreLabel;
    JLabel score;
    JButton Exit = new JButton("Exit");
    JButton Restart = new JButton("Restart");

    Font f1;

    Graphics g;
    ArrayList<Point> points = new ArrayList<Point>();
    Point point = new Point();



    Screen() {
        setSize(775,600);
        setLayout(null);
        setLocationRelativeTo(null); //화면의 가운데 띄움
        setResizable(false); //사이즈 조절 불가능
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창을 닫을 때 실행 중인 프로그램도 같이 종료되도록 함
        contentPane.setBackground(new Color(253, 244, 238));

        board.setLocation(100,100);
        add(board);
        board.addMouseListener(this);



        f1 = new Font("Park Lane",Font.PLAIN,20);
        greenScoreLabel = new JLabel(String.valueOf(board.greenCount));
        greenScoreLabel.setBounds(660,350,50,50);
        greenScoreLabel.setBackground(new Color(253, 244, 238));
        greenScoreLabel.setFont(f1);

        apricotScoreLabel = new JLabel(String.valueOf(board.apricotCount));
        apricotScoreLabel.setBounds(660,432,50,50);
        apricotScoreLabel.setBackground(new Color(253, 244, 238));
        apricotScoreLabel.setFont(f1);

        add(greenScoreLabel);
        add(apricotScoreLabel);
        //add(scorePanel);



        Exit.setBorderPainted(false); //외곽선을 만들어줌
        Exit.setFocusPainted(false); //선택이 되었을때 테두리 사용 안함
        Exit.setFont(f1);
        //Exit.setContentAreaFilled(false); //내용 채우기를 하지 않음
        Exit.setBounds(570,100,100,50);
        add(Exit);

        Restart.setBorderPainted(false); //외곽선을 만들어줌
        Restart.setFocusPainted(false); //선택이 되었을때 테두리 사용 안함
        Restart.setFont(f1);
        //Exit.setContentAreaFilled(false); //내용 채우기를 하지 않음
        Restart.setBounds(570,150,100,50);
        add(Restart);

        score = new JLabel("Score");
        score.setBounds(600,280,100,50);
        score.setBackground(new Color(253, 244, 238));
        score.setFont(f1);
        add(score);

        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (board.turn == 1){
            g.setColor(new Color(61, 101, 96));
            g.fillOval(570, 380, 50, 50);
            g.setColor(new Color(207, 170, 147, 77));
            g.fillOval(570, 458, 50, 50);
        }else if(board.turn == 2) {
            g.setColor(new Color(61, 101, 96, 77));
            g.fillOval(570, 380, 50, 50);
            g.setColor(new Color(207, 170, 147));
            g.fillOval(570, 458, 50, 50);
        }else if(board.turn == 0){
            g.setColor(new Color(61, 101, 96));
            g.fillOval(570, 380, 50, 50);
            g.setColor(new Color(207, 170, 147));
            g.fillOval(570, 458, 50, 50);

        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        greenScoreLabel.setText(String.valueOf(board.greenCount));
        apricotScoreLabel.setText(String.valueOf(board.apricotCount));

        repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void paintXY(String str){
        try{
            String [] s = str.split(",");

            int newX = Integer.parseInt(s[0]);
            int newY = Integer.parseInt(s[1]);
            System.out.println(newX);
            System.out.println(newY);

            condition(newX, newY, board.turn);

            repaint();

        }catch (Exception e){

        }
    }

    public void condition(int X, int Y, int order) {
        board.flagCount = 0;
        for (int i = -1; i <= 1; i++) { //주변의 8개 돌 살피는 중
            for (int j = -1; j <= 1; j++) {
                if (X + i < 0 || X + i > 7 || Y + j < 0 || Y + j > 7) {
                    continue;
                }
                if (board.pointHistory[X + i][Y + j] != 0 && board.pointHistory[X + i][Y + j] != order) { // 그 때 다른 돌을 찾았을 때
                    condition2(i, j, X, Y, order);
                }
            }
        }
    }

    public void condition2(int i, int j, int X, int Y, int order) {
        int changeX = X + i;
        int changeY = Y + j;
//        System.out.println("order : " + order);

        while (changeX < 8 && changeX > -1 && changeY < 8 && changeY > -1) {
            //System.out.println(changeX + " " + changeY);
            //제일 왼쪽, 오른쪽 끝이 아닐 때까지 반복
            if (board.pointHistory[changeX][changeY] == 0) break;
            else if (board.pointHistory[changeX][changeY] != order) {
                point.setLocation(changeX, changeY);
                points.add(point);
            } else if (board.pointHistory[changeX][changeY] == order) {
                for (int k = 0; k < points.size(); k++) {
//                    System.out.println("point : " + points.get(k).x + " " +points.get(k).y );
                    board.pointHistory[points.get(k).x][points.get(k).y] = order;
                }
                board.pointHistory[X][Y] = order;
                points.clear();

                board.flagCount++;
                if (board.flagCount == 1) {
                    if (board.turn == 2) {
                        board.turn = 1;
                    } else {
                        board.turn = 2;
                    }
                    System.out.println("1st" + board.turn);
                    if (board.passCheck(board.turn) == true) {
                        if (board.turn == 2) {
                            board.turn = 1;
                        } else {
                            board.turn = 2;
                        }
                        System.out.println("2nd" + board.turn);
                        if (board.passCheck(board.turn) == true) {
                            board.gameDone();
                        }
                    }
                }
                break;
            }
            changeX += i;
            changeY += j;
        }
    }
}
