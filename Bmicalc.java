import java.awt.*;
import javax.swing.*;

public class Bmicalc {

    public static void main(String[] args) {
        // Create the window
        JFrame window = new JFrame("BMI Calculator");
        window.setSize(300, 300);
        window.setLayout(new FlowLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the components
        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightBox = new JTextField(10);

        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightBox = new JTextField(10);

        JButton button = new JButton("Calculate");

        JLabel result = new JLabel("Result will appear here");

        // What happens when the button is clicked
        button.addActionListener(e -> {
            double height = Double.parseDouble(heightBox.getText());
            double weight = Double.parseDouble(weightBox.getText());

            double meters = height / 100;
            double bmi = weight / (meters * meters);

            String category;
            if (bmi < 18.5) {
                category = "Underweight";
            } else if (bmi < 25) {
                category = "Normal weight";
            } else if (bmi < 30) {
                category = "Overweight";
            } else {
                category = "Obese";
            }

            result.setText("BMI: " + String.format("%.2f", bmi) + " (" + category + ")");
        });

        // Add components to the window
        window.add(heightLabel);
        window.add(heightBox);
        window.add(weightLabel);
        window.add(weightBox);
        window.add(button);
        window.add(result);

        // Show the window
        window.setVisible(true);
    }
}