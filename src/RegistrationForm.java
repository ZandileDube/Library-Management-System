import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.regex.Pattern;

public class RegistrationForm extends JDialog {
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JPasswordField pwdtxtPassword;
    private JButton btnCancel;
    private JTextField txtName;
    private JButton btnRegister;
    private JLabel lblAddress;
    private JPanel registerPanel;
    private JPasswordField pwdtxtConfirmpassword;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension( 450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerUser()   {
         String name = txtName.getText();
         String email= txtEmail.getText();
         String phone = txtPhone.getText();
         String address = txtAddress.getText();
         String password = String.valueOf(pwdtxtPassword.getPassword());
         String ConfirmPassword = String.valueOf(pwdtxtConfirmpassword.getPassword());


        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || ConfirmPassword.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields",
                    "Try again" ,
                    JOptionPane.ERROR_MESSAGE);
            return;

        }

        if(!isValidEmail(email)){
            JOptionPane.showMessageDialog(this,
                    "Email is Invalid",
                    "Email invalid" ,
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        if(!password.equals(ConfirmPassword)){
            JOptionPane.showMessageDialog(this,
                    "Password does not match",
                    "Try again" ,
                    JOptionPane.ERROR_MESSAGE);

return;
        }
        user = addNewUserToDB(name,email,phone,address,password);
        if (user != null) {
            dispose();
        }
        else {
             JOptionPane.showMessageDialog(this,"Failed to register new user","Try again", JOptionPane.ERROR_MESSAGE);

        }

    }
    public User user;
    private User addNewUserToDB(String name, String email, String phone, String address, String password) {
        User user = null;

        try{
            Connection conn = DBConnection.getConnection();
            Statement stat = conn.createStatement();
            String sql = "INSERT INTO users (name, email, phone, address, password)" + "VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.address = address;
                user.password = password;
            }

            stat.close();
            conn.close();

        }catch (Exception e) {
            e.printStackTrace();

        }


        return user;
    }

    public boolean isValidEmail(String email){
        String pattern = "^(.+)@(.+)$";

        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
    }

    public static void main(String[] args) {
        RegistrationForm myForm = new RegistrationForm(null);
        User user = myForm.user;
        if (user != null) {
            System.out.println("Successful registration of:" + user.name);
        }else {
            System.out.println("Registration canceled");
        }
    }


}
