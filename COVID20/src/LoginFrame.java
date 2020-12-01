import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * GUI Login frame for app
 *
 * <p>Purdue University -- CS18000 -- Fall 2020</p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class LoginFrame extends JComponent implements Runnable {
    
    
    private static final long serialVersionUID = 1L;
    // fields
    User currentUser;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    JButton loginButton; // a button selected to login once user and pass are entered
    JButton newUserButton; // a button that will bring a user to a page to create a new account
    JLabel userLabel;
    JLabel passLabel;
    JTextField userField; // user enters their username
    JTextField passField; // user enters their password
    

    //constructor
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //
        }
    };

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new LoginFrame());  
    }

     */

    public LoginFrame(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }

    @Override
    public void run() {
        launchLoginFrame();
    }


    //methods
    //method to send and receive if user and pass match

    public void launchLoginFrame() {
        JFrame frame = new JFrame("Corona Connect");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel panel = new JPanel();        
        userLabel = new JLabel("Username: ");
        panel.add(userLabel);
        userField = new JTextField("", 10);
        panel.add(userField);
        passLabel = new JLabel("Password: ");
        panel.add(passLabel);
        passField = new JTextField("", 10);
        panel.add(passField);
        content.add(panel, BorderLayout.CENTER);        

        JPanel buttonPanel = new JPanel();
        newUserButton = new JButton("Create Account");
        newUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // call method to pull up new account gui
                System.out.println("Create Account pressed");
                launchNewAccountFrame();
                frame.dispose();
                System.out.println("Launched Account");

            }
        }); 
        buttonPanel.add(newUserButton);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // call method to check if pass matches
                boolean valid = false;
                try {
                    oos.writeInt(1);
                    oos.flush();

                    oos.writeObject(userField.getText());
                    oos.flush();

                    oos.writeObject(passField.getText());
                    oos.flush();


                    currentUser = (User) ois.readObject();

                    if (currentUser != null) {
                        valid = true;
                    }
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                if (!valid) { // if they are not correct
                    JOptionPane.showMessageDialog(null, "Credentials are incorrect. Please try again.", "Corona Connect",
                        JOptionPane.ERROR_MESSAGE);            
                }
                if (valid) { // if they are correct
                    JOptionPane.showMessageDialog(null, "You are Logged in Successfully", "Corona Connect",
                        JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    launchMainFrame();

                    // do something?     
                }
                System.out.println("Login button pressed");
            }
        });
        buttonPanel.add(loginButton);
        content.add(buttonPanel, BorderLayout.SOUTH);

        JPanel panelWelcome = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to Corona Connect!");        
        panelWelcome.add(welcomeLabel);
        content.add(panelWelcome, BorderLayout.NORTH);

        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true); 
    }

    public void launchMainFrame() {
        MainFrame main = new MainFrame(oos, ois, currentUser);
    }

    public void launchNewAccountFrame() {
        NewAccountFrame newAccount = new NewAccountFrame(oos, ois);
    }
}
