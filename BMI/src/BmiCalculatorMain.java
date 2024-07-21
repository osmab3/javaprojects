import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BmiCalculatorMain {
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("./resources/images.png");
        JTextField textfield1 = new JTextField();
        JFrame frame = new JFrame("Floppa BMI Calculator");
        JButton button = new JButton();
        JTextField textField2 = new JTextField();

        textField2.setSize(500,500);
        textField2.setHorizontalAlignment(SwingConstants.LEFT);
        textField2.setText("Enter Weight");

        textfield1.setSize(500,250);
        textfield1.setHorizontalAlignment(SwingConstants.RIGHT);
        textfield1.setText("Enter Height in Meters");


        button.setBounds(200,200,500,250);
        button.setText("Calculator");
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Double Height = Double.parseDouble(textfield1.getText());
                Integer Weight = Integer.parseInt(textField2.getText());

                Double Height1 = Height * Height;
                Double bmi = Weight / Height1;
                System.out.println(bmi);

                if (bmi <= 19){
                    System.out.println("Underweight");
                }
                else if (bmi <= 24){
                    System.out.println("Normal");
                } else if (bmi > 24) {
                    System.out.println("Overweight");
                }
            }
        });

        frame.setSize(300,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.add(textfield1);
        frame.add(textField2);
        frame.setIconImage(icon.getImage());
    }
}
