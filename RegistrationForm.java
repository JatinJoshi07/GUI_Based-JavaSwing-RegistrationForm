//exe Commands:
//cd "C:\Users\Jatin Joshi\Desktop"
//javac RegistrationForm.java
//java -cp ".; *Replace this with your path of mysql-connector-j-9.3.0.jar*" RegistrationForm

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

public class RegistrationForm extends JFrame {
    private JTextField usernameField, emailField, phoneField, secAField;
    private JTextArea addressArea;
    private JComboBox<String> countryBox, dayBox, monthBox, yearBox, secQBox;
    private JRadioButton male, female, other;
    private ButtonGroup genderGroup;
    private JPasswordField passField, confirmPassField;
    private JButton passToggleBtn, confirmPassToggleBtn;
    private JCheckBox terms;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ registration_db";
    private static final String DB_USER = "jatin";   //Replace it with your user
    private static final String DB_PASSWORD = "jatin9527";  //Replaceit with your Password

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(580, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Header 
        JPanel header = new JPanel();
        header.setBackground(new Color(48, 25, 123));
        header.setPreferredSize(new Dimension(550, 50));
        JLabel title = new JLabel("Create Account");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // Form 
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Username
        gbc.gridx = 0; gbc.gridy = y;
        JLabel usernameLabel = new JLabel("Username *");
        usernameLabel.setDisplayedMnemonic('U');
        formPanel.add(usernameLabel, gbc);
        usernameField = createStyledTextField();
        usernameField.setName("Username");
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);
        usernameLabel.setLabelFor(usernameField);
        y++;

        // Email
        gbc.gridx = 0; gbc.gridy = y;
        JLabel emailLabel = new JLabel("Email *");
        emailLabel.setDisplayedMnemonic('E');
        formPanel.add(emailLabel, gbc);
        emailField = createStyledTextField();
        emailField.setName("Email");
        emailField.setToolTipText("Enter a valid email address, e.g. user@example.com");
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        emailLabel.setLabelFor(emailField);
        y++;

        // Phone
        gbc.gridx = 0; gbc.gridy = y;
        JLabel phoneLabel = new JLabel("Phone *");
        phoneLabel.setDisplayedMnemonic('P');
        formPanel.add(phoneLabel, gbc);
        phoneField = createStyledTextField();
        phoneField.setName("Phone");
        phoneField.setToolTipText("Enter a valid phone number with 10 digits");
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        phoneLabel.setLabelFor(phoneField);
        y++;

        // Address
        gbc.gridx = 0; gbc.gridy = y;
        JLabel addressLabel = new JLabel("Address");
        formPanel.add(addressLabel, gbc);
        addressArea = new JTextArea(3, 20);
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        JScrollPane scrollPane = new JScrollPane(addressArea);
        gbc.gridx = 1;
        formPanel.add(scrollPane, gbc);
        y++;

        // Country
        gbc.gridx = 0; gbc.gridy = y;
        JLabel countryLabel = new JLabel("Country *");
        countryLabel.setDisplayedMnemonic('C');
        formPanel.add(countryLabel, gbc);
        String[] countries = {"Select country", "India", "USA", "UK", "Canada", "Australia", "Other"};
        countryBox = new JComboBox<>(countries);
        countryBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        countryBox.setName("Country");
        gbc.gridx = 1;
        formPanel.add(countryBox, gbc);
        countryLabel.setLabelFor(countryBox);
        y++;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = y;
        JLabel dobLabel = new JLabel("Date of Birth *");
        dobLabel.setDisplayedMnemonic('D');
        formPanel.add(dobLabel, gbc);
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dobPanel.setBackground(Color.WHITE);
        dayBox = new JComboBox<>(getDays());
        dayBox.setName("Day");
        monthBox = new JComboBox<>(getMonths());
        monthBox.setName("Month");
        yearBox = new JComboBox<>(getYears());
        yearBox.setName("Year");
        dobPanel.add(dayBox);
        dobPanel.add(monthBox);
        dobPanel.add(yearBox);
        gbc.gridx = 1;
        formPanel.add(dobPanel, gbc);
        dobLabel.setLabelFor(dayBox);
        y++;

        // Gender
        gbc.gridx = 0; gbc.gridy = y;
        JLabel genderLabel = new JLabel("Gender *");
        genderLabel.setDisplayedMnemonic('G');
        formPanel.add(genderLabel, gbc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(Color.WHITE);
        genderGroup = new ButtonGroup();
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        other = new JRadioButton("Other");
        male.setBackground(Color.WHITE);
        female.setBackground(Color.WHITE);
        other.setBackground(Color.WHITE);
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(other);
        male.setMnemonic('M');
        female.setMnemonic('F');
        other.setMnemonic('O');
        gbc.gridx = 1;
        formPanel.add(genderPanel, gbc);
        y++;

        // Security Question
        gbc.gridx = 0; gbc.gridy = y;
        JLabel secQLabel = new JLabel("Security Question *");
        secQLabel.setDisplayedMnemonic('Q');
        formPanel.add(secQLabel, gbc);
        String[] questions = {
            "Select a question",
            "Your mother's maiden name?",
            "First pet's name?",
            "First car?",
            "Favorite book?"
        };
        secQBox = new JComboBox<>(questions);
        secQBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        secQBox.setName("Security Question");
        secQBox.setToolTipText("Select a security question for password recovery");
        gbc.gridx = 1;
        formPanel.add(secQBox, gbc);
        secQLabel.setLabelFor(secQBox);
        y++;

        // Security Answer
        gbc.gridx = 0; gbc.gridy = y;
        JLabel secALabel = new JLabel("Security Answer *");
        secALabel.setDisplayedMnemonic('A');
        formPanel.add(secALabel, gbc);
        secAField = createStyledTextField();
        secAField.setName("Security Answer");
        gbc.gridx = 1;
        formPanel.add(secAField, gbc);
        secALabel.setLabelFor(secAField);
        y++;

        // Password with toggle
        gbc.gridx = 0; gbc.gridy = y;
        JLabel passLabel = new JLabel("Password *");
        passLabel.setDisplayedMnemonic('S');
        formPanel.add(passLabel, gbc);
        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.WHITE);
        passField = createStyledPasswordField();
        passField.setName("Password");
        passField.setToolTipText("<html>Password must be at least 8 characters,<br>include uppercase, lowercase, digit & special char.</html>");
        passToggleBtn = createEyeButton();
        passToggleBtn.addActionListener(e -> togglePasswordVisibility(passField, passToggleBtn));
        passPanel.add(passField, BorderLayout.CENTER);
        passPanel.add(passToggleBtn, BorderLayout.EAST);
        gbc.gridx = 1;
        formPanel.add(passPanel, gbc);
        passLabel.setLabelFor(passField);
        y++;

        // Confirm Password with toggle
        gbc.gridx = 0; gbc.gridy = y;
        JLabel confirmPassLabel = new JLabel("Confirm Password *");
        confirmPassLabel.setDisplayedMnemonic('N');
        formPanel.add(confirmPassLabel, gbc);
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBackground(Color.WHITE);
        confirmPassField = createStyledPasswordField();
        confirmPassField.setName("Confirm Password");
        confirmPassToggleBtn = createEyeButton();
        confirmPassToggleBtn.addActionListener(e -> togglePasswordVisibility(confirmPassField, confirmPassToggleBtn));
        confirmPanel.add(confirmPassField, BorderLayout.CENTER);
        confirmPanel.add(confirmPassToggleBtn, BorderLayout.EAST);
        gbc.gridx = 1;
        formPanel.add(confirmPanel, gbc);
        confirmPassLabel.setLabelFor(confirmPassField);
        y++;

        // Terms checkbox
        gbc.gridx = 1; gbc.gridy = y;
        terms = new JCheckBox("I agree to the Terms and Conditions *");
        terms.setBackground(Color.WHITE);
        terms.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        terms.setName("Terms and Conditions");
        formPanel.add(terms, gbc);
        y++;

        // Buttons panel (Register + Clear)
        gbc.gridx = 1; gbc.gridy = y;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setBackground(new Color(48, 25, 123));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setMnemonic('R');
        btnPanel.add(registerBtn);

        JButton clearBtn = new JButton("Reset");
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearBtn.setBackground(Color.GRAY);
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        clearBtn.setMnemonic('E');
        btnPanel.add(clearBtn);

        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Action listeners
        registerBtn.addActionListener(e -> onSubmit());
        clearBtn.addActionListener(e -> onClear());

        // Submit on Enter key for all fields
        KeyAdapter enterSubmitListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onSubmit();
                }
            }
        };

        usernameField.addKeyListener(enterSubmitListener);
        emailField.addKeyListener(enterSubmitListener);
        phoneField.addKeyListener(enterSubmitListener);
        addressArea.addKeyListener(enterSubmitListener);
        secAField.addKeyListener(enterSubmitListener);
        passField.addKeyListener(enterSubmitListener);
        confirmPassField.addKeyListener(enterSubmitListener);
        countryBox.addKeyListener(enterSubmitListener);
        dayBox.addKeyListener(enterSubmitListener);
        monthBox.addKeyListener(enterSubmitListener);
        yearBox.addKeyListener(enterSubmitListener);
        secQBox.addKeyListener(enterSubmitListener);
        male.addKeyListener(enterSubmitListener);
        female.addKeyListener(enterSubmitListener);
        other.addKeyListener(enterSubmitListener);
        terms.addKeyListener(enterSubmitListener);

        setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return field;
    }

    private JButton createEyeButton() {
        JButton btn = new JButton("\uD83D\uDC41"); // Eye emoji as icon substitute
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(40, 28));
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setToolTipText("Show/Hide Password");
        return btn;
    }

    private void togglePasswordVisibility(JPasswordField passField, JButton toggleBtn) {
        if (passField.getEchoChar() != (char) 0) {
            passField.setEchoChar((char) 0);
            toggleBtn.setText("\uD83D\uDC41\u200D\uD83D\uDD12"); // eye with lock emoji
        } else {
            passField.setEchoChar('•');
            toggleBtn.setText("\uD83D\uDC41");
        }
    }

    private String[] getDays() {
        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) days[i] = String.valueOf(i);
        return days;
    }

    private String[] getMonths() {
        return new String[]{
            "Month", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
    }

    private String[] getYears() {
        int currentYear = java.time.Year.now().getValue();
        int count = 100;
        String[] years = new String[count + 1];
        years[0] = "Year";
        for (int i = 1; i <= count; i++) {
            years[i] = String.valueOf(currentYear - (count - i));
        }
        return years;
    }

    private void onSubmit() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressArea.getText().trim();
        String country = (String) countryBox.getSelectedItem();
        String day = (String) dayBox.getSelectedItem();
        String month = (String) monthBox.getSelectedItem();
        String year = (String) yearBox.getSelectedItem();
        String gender = null;
        if (male.isSelected()) gender = "Male";
        else if (female.isSelected()) gender = "Female";
        else if (other.isSelected()) gender = "Other";

        String secQ = (String) secQBox.getSelectedItem();
        String secA = secAField.getText().trim();

        char[] pass = passField.getPassword();
        char[] confirmPass = confirmPassField.getPassword();

        // Validate
        if (username.isEmpty()) {
            showError("Username is required.");
            usernameField.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            showError("Email is required.");
            emailField.requestFocus();
            return;
        }
        if (!isValidEmail(email)) {
            showError("Please enter a valid email address.");
            emailField.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            showError("Phone number is required.");
            phoneField.requestFocus();
            return;
        }
        if (!isValidPhone(phone)) {
            showError("Phone number must be exactly 10 digits.");
            phoneField.requestFocus();
            return;
        }
        if (country.equals("Select country")) {
            showError("Please select a country.");
            countryBox.requestFocus();
            return;
        }
        if (day.equals("Day") || month.equals("Month") || year.equals("Year")) {
            showError("Please select a valid Date of Birth.");
            dayBox.requestFocus();
            return;
        }
        if (gender == null) {
            showError("Please select your gender.");
            male.requestFocus();
            return;
        }
        if (secQ.equals("Select a question")) {
            showError("Please select a security question.");
            secQBox.requestFocus();
            return;
        }
        if (secA.isEmpty()) {
            showError("Security answer is required.");
            secAField.requestFocus();
            return;
        }
        if (pass.length == 0) {
            showError("Password is required.");
            passField.requestFocus();
            return;
        }
        String passwordStr = new String(pass);
        if (!isValidPassword(passwordStr)) {
            showError("Password must be at least 8 characters and include uppercase, lowercase, digit, and special character.");
            passField.requestFocus();
            return;
        }
        if (!java.util.Arrays.equals(pass, confirmPass)) {
            showError("Password and Confirm Password do not match.");
            confirmPassField.requestFocus();
            return;
        }
        if (!terms.isSelected()) {
            showError("You must agree to the Terms and Conditions.");
            terms.requestFocus();
            return;
        }

        // Compose dob string in yyyy-MM-dd format
        String dob = year + "-" + getMonthNumber(month) + "-" + (day.length() == 1 ? "0" + day : day);

        // Try to insert into database
        if (saveToDatabase(username, email, phone, address, country, dob, gender, secQ, secA, passwordStr)) {
            JOptionPane.showMessageDialog(this,
                    "Registration Successful!\nWelcome, " + username + ".",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            onClear();
        }
    }

    private boolean saveToDatabase(String username, String email, String phone, String address, String country,
                                   String dob, String gender, String secQ, String secA, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO users (username, email, phone, address, country, dob, gender, security_question, security_answer, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, country);
            pstmt.setDate(6, java.sql.Date.valueOf(dob));
            pstmt.setString(7, gender);
            pstmt.setString(8, secQ);
            pstmt.setString(9, secA);
            pstmt.setString(10, password); 

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (ClassNotFoundException e) {
            showError("MySQL JDBC Driver not found. Please ensure it is added to the project.");
            e.printStackTrace();
        } catch (SQLException e) {
            showError("Error connecting to the database or executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ignore) {}
        }
        return false;
    }

    private int getMonthNumber(String monthName) {
        String[] months = {"Month", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (int i = 1; i < months.length; i++) {
            if (months[i].equals(monthName)) {
                return i < 10 ? Integer.parseInt("0" + i) : i; 
            }
        }
        return 0;
    }

    private void onClear() {
        usernameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        countryBox.setSelectedIndex(0);
        dayBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        genderGroup.clearSelection();
        secQBox.setSelectedIndex(0);
        secAField.setText("");
        passField.setText("");
        confirmPassField.setText("");
        terms.setSelected(false);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidEmail(String email) {
        // Simple RFC5322 email regex pattern
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    private boolean isValidPassword(String password) {
        // At least 8 chars, 1 upper, 1 lower, 1 digit, 1 special char
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           