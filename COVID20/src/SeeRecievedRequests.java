import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI New Account Frame for app
 *
 * <p>Purdue University -- CS18000 -- Fall 2020</p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class SeeRecievedRequests extends JComponent implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //fields
    JButton closeButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SeeRecievedRequests());  
    }
    
    @Override
    public void run() {
        SeeRecievedRequests frame = new SeeRecievedRequests();
        frame.setVisible(true);
    }

    public SeeRecievedRequests() {
        System.out.println("into run of seeing all users");
        JFrame frame = new JFrame("Corona Connect See All Users");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel panel = new JPanel(); // top panel
        JLabel labelAllUsers = new JLabel("Corona Connect Friend Requests Listed Below");
        panel.add(labelAllUsers);
        content.add(panel, BorderLayout.NORTH);   

        /*
        Content of labels and fields for user to fill in
        */
        JPanel mPanel = new JPanel(); // panel in the middle 
        JScrollPane jsp = new JScrollPane(mPanel);
        // TODO: add loop for users and display of them
        JLabel lbl = new JLabel("THIS IS FRIEND REQUEST USERS WILL BE");
        mPanel.add(lbl);
        content.add(jsp, BorderLayout.CENTER);

        JPanel sPanel = new JPanel();
        closeButton = new JButton("Return to Main Page");
        closeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check to see if all fields are valid in another method 
                    
                frame.dispose();
                System.out.println("Close button pressed");
            }
        });
        sPanel.add(closeButton);
        content.add(sPanel, BorderLayout.SOUTH);

        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

