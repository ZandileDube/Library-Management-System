import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{

    public void login() {
//        JRadioButton rbBtnstudent = new JRadioButton("Student");
//        JRadioButton rbBtnStuff = new JRadioButton("Stuff")

        ButtonGroup group = new ButtonGroup();
        group.add(rbBtnstudent);
        group.add(rbBtnstuff);
        JPasswordField passwordField1 = new JPasswordField();
        String username = txtUsername.getText();
        char[] password = passwordField1.getPassword();
        // Convert the char array to a string
        String passwordString = new String(password);


        System.out.println(username + password );
    }

    private JLabel Username;
    private JTextField txtUsername;
    private JRadioButton rbBtnstudent;
    private JRadioButton rbBtnstuff;
    private JButton btnLogin;
    private JPanel panelmain;
    private JPasswordField passwordField1;

    public Login() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
login();

            }
        });
    }

    public static void main(String[] args) {
        Login login=new Login();
        login.setContentPane(login.panelmain);
        login.setTitle("Login");
        login.setSize(300,400);
        login.setVisible(true);
    }
    
}
