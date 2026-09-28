// ================================================================
// BMI Calculator (Beginner Version - Java + Swing)
// Author: Renz A. General
// File  : Bmicalc.java
//
// Compile: javac Bmicalc.java
// Run:     java Bmicalc
// ================================================================

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Bmicalc extends JFrame {

    // ---- Things we need to remember ----
    JTextField heightBox;
    JTextField weightBox;
    JLabel resultNumber;
    JLabel resultCategory;
    ArrayList<String> history = new ArrayList<>();


    // ================================================================
    // CONSTRUCTOR — builds the window
    // ================================================================
    public Bmicalc() {
        setTitle("BMI Calculator");
        setSize(400, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 245));

        makeMenuBar();
        makeMiddleArea();
    }


    // ================================================================
    // MENU BAR
    // ================================================================
    void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // ---- File menu ----
        JMenu fileMenu = new JMenu("File");

        JMenuItem clearItem = new JMenuItem("Clear History");
        clearItem.addActionListener(e -> clearHistory());
        fileMenu.add(clearItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // ---- View menu ----
        JMenu viewMenu = new JMenu("View");

        JMenuItem historyItem = new JMenuItem("Show History");
        historyItem.addActionListener(e -> showHistory());
        viewMenu.add(historyItem);

        JMenuItem statsItem = new JMenuItem("Show Statistics");
        statsItem.addActionListener(e -> showStatistics());
        viewMenu.add(statsItem);

        // ---- Help menu ----
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        // ---- Add menus to the menu bar ----
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }


    // ================================================================
    // MIDDLE AREA — title, inputs, button, result
    // ================================================================
    void makeMiddleArea() {
        // A vertical panel to stack things
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ---------- Title ----------
        JLabel title = new JLabel("BMI Calculator");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(204, 17, 85));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        // ---------- Subtitle ----------
        JLabel subtitle = new JLabel("Based on WHO standards");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 10));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitle);

        panel.add(Box.createVerticalStrut(20));

        // ---------- Height label + box ----------
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        heightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(heightLabel);

        panel.add(Box.createVerticalStrut(4));

        heightBox = new JTextField();
        heightBox.setFont(new Font("Arial", Font.PLAIN, 14));
        heightBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        heightBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        // ⭐ NEW: press Enter in the height box to calculate
        heightBox.addActionListener(e -> calculateBmi());
        panel.add(heightBox);

        panel.add(Box.createVerticalStrut(14));

        // ---------- Weight label + box ----------
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        weightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(weightLabel);

        panel.add(Box.createVerticalStrut(4));

        weightBox = new JTextField();
        weightBox.setFont(new Font("Arial", Font.PLAIN, 14));
        weightBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        weightBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        // ⭐ NEW: press Enter in the weight box to calculate
        weightBox.addActionListener(e -> calculateBmi());
        panel.add(weightBox);

        panel.add(Box.createVerticalStrut(20));

        // ---------- Calculate button ----------
        JButton calcButton = new JButton("Calculate BMI");
        calcButton.setFont(new Font("Arial", Font.BOLD, 14));
        calcButton.setBackground(new Color(204, 17, 85));
        calcButton.setForeground(Color.WHITE);
        calcButton.setFocusPainted(false);
        calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcButton.addActionListener(e -> calculateBmi());
        panel.add(calcButton);

        panel.add(Box.createVerticalStrut(20));

        // ---------- Result number ----------
        resultNumber = new JLabel("—");
        resultNumber.setFont(new Font("Arial", Font.BOLD, 30));
        resultNumber.setForeground(new Color(204, 17, 85));
        resultNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultNumber);

        // ---------- Result category ----------
        resultCategory = new JLabel("Enter your details");
        resultCategory.setFont(new Font("Arial", Font.PLAIN, 14));
        resultCategory.setForeground(Color.GRAY);
        resultCategory.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultCategory);

        add(panel, BorderLayout.CENTER);
    }


    // ================================================================
    // CALCULATE BMI
    // ================================================================
    void calculateBmi() {
        // ---- Step 1: Read what the user typed ----
        String heightText = heightBox.getText().trim();
        String weightText = weightBox.getText().trim();

        // ---- Step 2: Make sure nothing is empty ----
        if (heightText.equals("") || weightText.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in both boxes.",
                    "Oops", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ---- Step 3: Try to turn the text into numbers ----
        double height;
        double weight;
        try {
            height = Double.parseDouble(heightText);
            weight = Double.parseDouble(weightText);
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this,
                    "Please type numbers only.",
                    "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ---- Step 4: Make sure the numbers are positive ----
        if (height <= 0 || weight <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Numbers must be bigger than 0.",
                    "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ---- Step 5: Do the math ----
        double heightInMeters = height / 100;
        double bmi = weight / (heightInMeters * heightInMeters);

        // ---- Step 6: Decide the category ----
        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25) {
            category = "Normal weight";
        } else if (bmi < 30) {
            category = "Overweight";
        } else if (bmi < 35) {
            category = "Obese Class I";
        } else if (bmi < 40) {
            category = "Obese Class II";
        } else {
            category = "Obese Class III";
        }

        // ---- Step 7: Show the result ----
        resultNumber.setText(String.format("%.2f", bmi));
        resultCategory.setText(category);

        // ---- Step 8: Save to history ----
        String line = String.format(
                "Height: %.1f cm | Weight: %.1f kg | BMI: %.2f | %s",
                height, weight, bmi, category);
        history.add(line);
    }


    // ================================================================
    // MENU ACTIONS
    // ================================================================
    void showHistory() {
        if (history.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "You haven't calculated anything yet.",
                    "History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String allText = "";
        int number = 1;
        for (String line : history) {
            allText = allText + number + ". " + line + "\n";
            number = number + 1;
        }

        JOptionPane.showMessageDialog(this, allText,
                "History", JOptionPane.INFORMATION_MESSAGE);
    }

    void clearHistory() {
        if (history.size() == 0) {
            JOptionPane.showMessageDialog(this, "Nothing to clear.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(this,
                "Delete all history?", "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            history.clear();
            JOptionPane.showMessageDialog(this, "History cleared!");
        }
    }

    void showStatistics() {
        if (history.size() == 0) {
            JOptionPane.showMessageDialog(this, "No calculations yet.");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "You have calculated " + history.size() + " time(s).",
                "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    void showAbout() {
        JOptionPane.showMessageDialog(this,
                "BMI Calculator\n" +
                "Version 1.0\n\n" +
                "Made with Java by Renz A. General",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }


    // ================================================================
    // MAIN — START THE PROGRAM
    // ================================================================
    public static void main(String[] args) {
        Bmicalc app = new Bmicalc();
        app.setVisible(true);
    }
}