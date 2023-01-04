package Project6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Login extends JFrame implements ActionListener {
    JPlaceholderTextField placeholderID = new JPlaceholderTextField("ID");
    JPlaceholderPassword placeholderPW = new JPlaceholderPassword("PW");
    JPanel jPanel = new JPanel();
    JLabel setLoginImage;
    JTextField ID = new JTextField("ID");
    JPasswordField PW = new JPasswordField("PW");
    JButton check = new JButton("로그인");
    JButton signUp = new JButton("회원가입");
    JButton manage = new JButton("관리자");
    ImageIcon login = new ImageIcon("image/로그인.png");


    String id;
    String pw;
    String origin_pw;

    public Login(){
        super("Login"); //타이틀
        setLayout(null);
        setSize(1440, 980);

        jPanel.setBounds(0,0,1440,980);
        jPanel.setLayout(null);
        jPanel.setBackground(Color.white);

//        placeholderTextField.setText("로그인");
//        ID.setBounds(450,380,530,60);
//        jPanel.add(ID);

        //placeholderID.setText("");
        placeholderID.setBounds(450,380,530,60);
        jPanel.add(placeholderID);


//        PW.setBounds(450,450, 530,60);
//        jPanel.add(PW);

        placeholderPW.setBounds(450,450, 530,60);
        jPanel.add(placeholderPW);
        placeholderPW.addActionListener(this);


        check.setForeground(new Color(85,148,255));
        check.setBounds(570,540, 90, 50);
        jPanel.add(check);
        check.addActionListener(this);

        signUp.setForeground(new Color(85,148,255));
        signUp.setBounds(670,540,90,50);
        jPanel.add(signUp);
        signUp.addActionListener(this);

        manage.setForeground(new Color(85,148,255));
        manage.setBounds(770,540,90,50);
        jPanel.add(manage);
        manage.addActionListener(this);

        Image login_Image = login.getImage();
        Image clogin_Image = login_Image.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon clogin = new ImageIcon(clogin_Image);
        setLoginImage = new JLabel(clogin);
        setLoginImage.setBounds(588,150, 250, 250);
        jPanel.add(setLoginImage);

        add(jPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == placeholderPW){
            placeholderPW.setVisible(false);
        }
        else if(e.getSource() == check){
            id = placeholderID.getText();
            pw = placeholderPW.getText();

            if(id == null || pw == null){
                System.out.println("id & pw가 비어 있습니다.");
                JOptionPane.showMessageDialog(null, "iD 또는 PW를 입력해주세요.");

            }else{
                setVisible(false);
                MultiClient multiClient = new MultiClient();
                multiClient.mainRunner();
//                PersonDao pDao = new PersonDao();
//                origin_pw = pDao.selectOne(id).getPw();
//                System.out.println(pw);
//                System.out.println(origin_pw);
//
//                if(pw.equals(origin_pw)){
//                    System.out.println("로그인이 완료되었습니다");
//                    JOptionPane.showMessageDialog(null, "로그인 완료되었습니다.");
//                    Person vo = new Person(id, origin_pw, pDao.selectOne(id).getName(),pDao.selectOne(id).getAge());
//                    new Basic(vo);
//                    setVisible(false);
//                }else{
//                    JOptionPane.showMessageDialog(null, "잘못된 비밀번호입니다.");
//                    System.out.println("잘못된 비밀번호입니다.");

                }
            }
//
//        } else if (e.getSource() == signUp) {
//            new SignUp();
//            setVisible(false);
//        } else if (e.getSource() == manage) {
//            new Manage();
//            setVisible(false);
//        }

    }


}


