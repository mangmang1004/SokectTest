package Project6;

import javax.swing.*;
import java.awt.*;

public class GameDone extends JFrame {
    Board board = new Board();

    Container contentPane = getContentPane();

    Font f2 = new Font("Park Lane",Font.BOLD,40);
    Font f3 = new Font("Park Lane",Font.PLAIN,25);

    JLabel gameOver = new JLabel("GameOver");
    JLabel win = new JLabel("Win");
    JLabel lose = new JLabel("Lose");
    JLabel greenScore;
    JLabel apricotScore;
    JLabel vs = new JLabel(":");

    ImageIcon greenImage = new ImageIcon("이미지/초록원.png");
    JLabel green = new JLabel(greenImage);

    ImageIcon apricotImage = new ImageIcon("이미지/살구원.png");
    JLabel apricot = new JLabel(apricotImage);




    GameDone(){
        setSize(516,332);
        contentPane.setBackground(new Color(253, 244, 238));
        setLayout(null);
        setLocationRelativeTo(null); //화면의 가운데 띄움
        setResizable(false); //사이즈 조절 불가능
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창을 닫을 때 실행 중인 프로그램도 같이 종료되도록 함

        gameOver.setBounds(168,75,200,48);
        gameOver.setFont(f2);
        add(gameOver);

        greenScore = new JLabel(String.valueOf(board.greenCount));
        greenScore.setBounds(209,173,50,50);
        System.out.println(String.valueOf(board.greenCount));
        greenScore.setFont(f3);
        add(greenScore);

        apricotScore = new JLabel(String.valueOf(board.apricotCount));
        apricotScore.setBounds(370,173,50,50);
        System.out.println(String.valueOf(board.apricotCount));
        apricotScore.setFont(f3);
        add(apricotScore);

        vs.setBounds(255,173,50,50);
        vs.setFont(f3);
        add(vs);

        green.setBounds(117,150,100,100);
        apricot.setBounds(280,150,100,100);
        add(green);
        add(apricot);

        if(board.greenCount > board.apricotCount){
            win.setBounds(45,181,54,36);
            win.setFont(f3);
            add(win);
            lose.setBounds(425,181,53,36);
            lose.setFont(f3);
            add(lose);
        }else if(board.greenCount < board.apricotCount){
            win.setBounds(425,181,54,36);
            win.setFont(f3);
            add(win);
            lose.setBounds(45,181,53,36);
            lose.setFont(f3);
            add(lose);

        }else if(board.greenCount == board.apricotCount){
            win.setBounds(425,181,54,36);
            win.setText("Draw");
            win.setFont(f3);
            add(win);
            lose.setBounds(45,181,53,36);
            lose.setText("Draw");
            lose.setFont(f3);
            add(lose);

        }

        setVisible(true);








    }
}
