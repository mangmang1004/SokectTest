package Project6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private int maxLine = 9; //9줄(8칸)
    private int stoneSize = 50; //알의 크기
    private int LineWidth = 50;
    int flagCount = 0;

    Point s = null; //click할 때 포인트 얻어오는 것

    int turn = 1; //1일때는 녹색, 2일때는 살구색
    int count = 0;

    static int greenCount = 2;
    static int apricotCount = 2;
    int passCount = 0;
    static PrintWriter writer; //상대방에게 메세지를 전달하는 스트림

    static int[][] pointHistory = new int[8][8];//이때까지의 직접 누른 알을 저장

    static String output = new String(); //x,y좌표 다른 클라이언트에게 보내주기 위해 만드는 string
    static boolean isOutputReal = false;


    ArrayList<Point> points = new ArrayList<Point>();

    Graphics g;

    Board() {
        setSize(400, 400);
        setBackground(new Color(253, 244, 238));
        addMouseListener(this);
        addMouseMotionListener(this);

        pointHistory[3][3] = 1;
        pointHistory[3][4] = 2;
        pointHistory[4][3] = 2;
        pointHistory[4][4] = 1;


    }

    public void paint(Graphics g) {
        //System.out.println(temporal);
        greenCount = 0;
        apricotCount = 0;

        super.paint(g);
        g.setColor(new Color(207, 170, 147));


        for (int i = 0; i <= maxLine; i++) {
            g.drawLine(0, i * LineWidth, 400, i * LineWidth);
            g.drawLine(i * LineWidth, 0, i * LineWidth, 400);
        }


        //조건을 부여한대로 처음부터 그림만 그리는 곳
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pointHistory[i][j] == 1) {
                    g.setColor(new Color(61, 101, 96));
                    Point setp = setPoint(i, j);
                    g.fillOval(setp.x, setp.y, stoneSize, stoneSize);
                    greenCount++;

                } else if (pointHistory[i][j] == 2) {
                    g.setColor(new Color(207, 170, 147));
                    Point setp = setPoint(i, j);
                    g.fillOval(setp.x, setp.y, stoneSize, stoneSize);
                    apricotCount++;

                } else if (pointHistory[i][j] == 0) {
                    continue;
                }
            }
        }



        if(greenCount == 0 || apricotCount == 0){
            gameDone();
        }
        else if(greenCount + apricotCount == 64){
            gameDone();
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
//        if (count % 2 == 0) turn = 1;
//        else  turn = 2;

        //temporal = false;

        //마우스를 누른 곳의 위치를 얻음
        s = e.getPoint();
        int X = s.x / 50;
        int Y = s.y / 50;


        //마우스를 누른 곳에 넣을 수 있는 것인지 알아내는 조건 부여
        condition(X, Y, turn);
        //System.out.println("count " + count);
        //System.out.println("order " + order);


        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //마우스가 지나는 곳을 받아옴
//        s = e.getPoint();
//        //System.out.println(s);
//        temporalX = s.x/50;
//        temporalY = s.y/50;
//        //temporal = true;
//
//        condition(temporalX,temporalY,order);
//
//        repaint();
//        pointHistory[temporalX][temporalY] = 0;
    }

    public Point setPoint(int X, int Y) {
        Point point[][] = new Point[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                point[i][j] = new Point();
                point[i][j].x = i * 50;
                point[i][j].y = j * 50;
            }
        }

        return point[X][Y];

    }

    public void condition(int X, int Y, int order) {
        flagCount = 0;
        for (int i = -1; i <= 1; i++) { //주변의 8개 돌 살피는 중
            for (int j = -1; j <= 1; j++) {
                if (X + i < 0 || X + i > 7 || Y + j < 0 || Y + j > 7) {
                    continue;
                }
                if (pointHistory[X + i][Y + j] != 0 && pointHistory[X + i][Y + j] != order) { // 그 때 다른 돌을 찾았을 때
                    condition2(i, j, X, Y, order);
                }
            }
        }
    }

    public void condition2(int i, int j, int X, int Y, int order) {
        ArrayList<Point> points = new ArrayList<Point>();
        int changeX = X + i;
        int changeY = Y + j;
//        System.out.println("order : " + order);

        while (changeX < 8 && changeX > -1 && changeY < 8 && changeY > -1) {
            //System.out.println(changeX + " " + changeY);
            //제일 왼쪽, 오른쪽 끝이 아닐 때까지 반복
            if (pointHistory[changeX][changeY] == 0) break;
            else if (pointHistory[changeX][changeY] != order) {
                points.add(new Point(changeX, changeY));
            } else if (pointHistory[changeX][changeY] == order) {
                for (int k = 0; k < points.size(); k++) {
//                    System.out.println("point : " + points.get(k).x + " " +points.get(k).y );
                    pointHistory[points.get(k).x][points.get(k).y] = order;
                }
                pointHistory[X][Y] = order;

                output =  X + "," + Y;
                isOutputReal = true;

                flagCount++;
                if (flagCount == 1) {
                    if (turn == 2) {
                        turn = 1;
                    }
                    else {
                        turn = 2;
                    }
                    System.out.println("1st"+turn);
                    if(passCheck(turn) == true){
                        if (turn == 2){
                            turn = 1;
                        }
                        else{
                            turn = 2;
                        }
                        System.out.println("2nd"+turn);
                        if(passCheck(turn) == true){
                            gameDone();
                        }
                    }
                }
                break;
            }
            changeX += i;
            changeY += j;

        }
        if( isOutputReal = true){
            try {
                MultiClient.ClientSender.out.writeUTF(output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            isOutputReal = false;

        }

    }

    public Boolean passCheck(int order) {
        boolean isPass = true;

        for (int i = 0; i < 8; i++) { //전체를 다 훑어 보는 중
            for (int j = 0; j < 8; j++) {
                if (pointHistory[i][j] == 0) { //살펴보던 중 비어있을 때

                    for (int m = -1; m <= 1; m++) { //주변의 8개 돌 살피는 중
                        for (int n = -1; n <= 1; n++) {
                            if (i + m < 0 || i + m > 7 || j + n < 0 || j + n > 7) {
                                continue;
                            }
                            if (pointHistory[i + m][j + n] != 0 && pointHistory[i + m][j + n] != order) { //즉 놀 수 있는 곳을 찾은거지
                                int changeX = i + m;
                                int changeY = j + n;

                                while (changeX < 8 && changeX > -1 && changeY < 8 && changeY > -1) {
                                    //System.out.println(changeX + " " + changeY);
                                    //제일 왼쪽, 오른쪽 끝이 아닐 때까지 반복
                                    if (pointHistory[changeX][changeY] == 0) break;
                                    else if (pointHistory[changeX][changeY] != order) {
                                        points.add(new Point(changeX, changeY));
                                    } else if (pointHistory[changeX][changeY] == order) {
                                        isPass = false;
                                        return isPass;
                                    }
                                    changeX += m;
                                    changeY += n;
                                }
                                points.clear();
                            }
                        } if(isPass == false) break;
                    } if(isPass == false) break;
                } if(isPass == false) break;
            } if(isPass == false) break;
        }
        return isPass;
    }


    public void gameDone(){
        //보드판이 꽉 차면 count 비교하면서 승부 가르기
        new GameDone();
    }
}





