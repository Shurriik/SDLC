package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class InputDialog extends JDialog {

    private final JTextField dateField;
    private final JButton saveButton;
    private final JButton cancelButton;

    private LocalDate result;

    private static final Color BACKGROUND_COLOR =
            new Color(245, 247, 250);

    private static final Color PRIMARY_COLOR =
            new Color(52, 120, 246);

    private static final Color TEXT_COLOR =
            new Color(35, 40, 45);

    private static final Color SECONDARY_TEXT_COLOR =
            new Color(100, 108, 118);

    public InputDialog(JFrame parent, LocalDate previousDate) {

        super(parent, "Ввод данных", true);

        setSize(500, 300);

        setLocationRelativeTo(parent);

        setResizable(false);

        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15));

        mainPanel.setBackground(BACKGROUND_COLOR);

        mainPanel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Введите дату рождения");

        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 22)
        );

        titleLabel.setForeground(TEXT_COLOR);

        headerPanel.add(titleLabel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        centerPanel.setOpaque(false);

        JLabel label = new JLabel("Дата рождения");

        label.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        label.setForeground(TEXT_COLOR);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(label);

        centerPanel.add(Box.createVerticalStrut(8));

        dateField = new JTextField();

        dateField.setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );

        dateField.setForeground(TEXT_COLOR);

        dateField.setBackground(Color.WHITE);

        dateField.setCaretColor(PRIMARY_COLOR);

        dateField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 198, 210),
                                1
                        ),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );

        dateField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 48)
        );

        dateField.setAlignmentX(Component.LEFT_ALIGNMENT);

        ((AbstractDocument) dateField.getDocument())
                .setDocumentFilter(new DateDocumentFilter()
                );

        if (previousDate != null) {

            dateField.setText(
                    String.format(
                            "%02d.%02d.%04d",
                            previousDate.getDayOfMonth(),
                            previousDate.getMonthValue(),
                            previousDate.getYear()
                    )
            );
        }

        centerPanel.add(dateField);

        centerPanel.add(Box.createVerticalStrut(8));

        JLabel hintLabel = new JLabel(
                "Формат: ДД.ММ.ГГГГ   -   Например: 15.03.2004"
        );

        hintLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        hintLabel.setForeground(SECONDARY_TEXT_COLOR);

        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(hintLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );

        buttonPanel.setOpaque(false);

        cancelButton = createSecondaryButton("Отмена");

        saveButton = createPrimaryButton("Сохранить");

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        saveButton.addActionListener(e -> saveDate());

        cancelButton.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(saveButton);

        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_ESCAPE,
                        0
                ),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        SwingUtilities.invokeLater(() -> {

            dateField.requestFocusInWindow();

            dateField.setCaretPosition(
                    dateField.getText().length()
            );
        });
    }

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        button.setForeground(Color.WHITE);

        button.setBackground(PRIMARY_COLOR);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setPreferredSize(
                new Dimension(130, 42)
        );

        return button;
    }

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        button.setForeground(TEXT_COLOR);

        button.setBackground(Color.WHITE);

        button.setFocusPainted(false);

        button.setPreferredSize(
                new Dimension(110, 42)
        );

        return button;
    }

    private void saveDate() {

        String text =
                dateField.getText().trim();

        if (text.isEmpty()) {

            showError(
                    "Дата не введена",
                    "Введите дату рождения в формате ДД.ММ.ГГГГ."
            );

            dateField.requestFocusInWindow();

            return;
        }

        if (text.length() != 10) {

            showError(
                    "Неполная дата",
                    "Дата должна содержать 10 символов.\n"
                            + "Используйте формат ДД.ММ.ГГГГ."
            );

            dateField.requestFocusInWindow();

            return;
        }

        if (text.charAt(2) != '.' || text.charAt(5) != '.') {

            showError(
                    "Неверный формат",
                    "В качестве разделителей используйте точки:\n"
                            + "ДД.ММ.ГГГГ"
            );

            dateField.requestFocusInWindow();

            return;
        }

        for (int i = 0; i < text.length(); i++) {

            if (i == 2 || i == 5) {
                continue;
            }

            if (!Character.isDigit(
                    text.charAt(i)
            )) {

                showError(
                        "Недопустимый символ",
                        "Дата должна содержать только цифры\n"
                                + "и точки."
                );

                dateField.requestFocusInWindow();

                return;
            }
        }

        try {

            int day = Integer.parseInt(
                    text.substring(0, 2)
            );

            int month = Integer.parseInt(
                    text.substring(3, 5)
            );

            int year = Integer.parseInt(
                    text.substring(6, 10)
            );

            if (day < 1 || day > 31) {

                showError(
                        "Некорректный день",
                        "День должен находиться в диапазоне от 01 до 31."
                );

                dateField.requestFocusInWindow();

                return;
            }

            if (month < 1 || month > 12) {

                showError(
                        "Некорректный месяц",
                        "Месяц должен находиться в диапазоне от 01 до 12."
                );

                dateField.requestFocusInWindow();

                return;
            }

            int currentYear =
                    LocalDate.now().getYear();

            if (year < 1900) {

                showError(
                        "Некорректный год",
                        "Год рождения не может быть меньше 1900."
                );

                dateField.requestFocusInWindow();

                return;
            }

            if (year > currentYear) {

                showError(
                        "Некорректный год",
                        "Год рождения не может быть больше "
                                + currentYear
                                + "."
                );

                dateField.requestFocusInWindow();

                return;
            }

            Month selectedMonth = Month.of(month);

            int maxDays =
                    selectedMonth.length(Year.isLeap(year));

            if (day > maxDays) {

                String monthName = getMonthName(month);

                showError(
                        "Несуществующая дата",
                        "В месяце "
                                + monthName
                                + " "
                                + year
                                + " года нет "
                                + day
                                + "-го дня."
                );

                dateField.requestFocusInWindow();

                return;
            }

            LocalDate date =
                    LocalDate.of(year, month, day);

            LocalDate today = LocalDate.now();

            if (date.isAfter(today)) {

                showError(
                        "Дата находится в будущем",
                        "Дата рождения не может быть позже сегодняшнего дня."
                );

                dateField.requestFocusInWindow();

                return;
            }

            LocalDate minimumDate =
                    today.minusYears(150);

            if (date.isBefore(minimumDate)) {

                showError(
                        "Некорректная дата",
                        "Возраст человека не должен превышать 150 лет."
                );

                dateField.requestFocusInWindow();

                return;
            }

            result = date;

            dispose();

        } catch (DateTimeException |
                 NumberFormatException exception) {

            showError(
                    "Ошибка даты",
                    "Не удалось распознать введённую дату.\n"
                            + "Проверьте правильность данных."
            );

            dateField.requestFocusInWindow();
        }
    }

    private void showError(String title, String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    private String getMonthName(int month) {

        String[] months = {
                "январе",
                "феврале",
                "марте",
                "апреле",
                "мае",
                "июне",
                "июле",
                "августе",
                "сентябре",
                "октябре",
                "ноябре",
                "декабре"
        };

        return months[month - 1];
    }

    public LocalDate getResult() {
        return result;
    }

    private static class DateDocumentFilter
            extends DocumentFilter {

        @Override
        public void insertString(
                FilterBypass fb,
                int offset,
                String string,
                AttributeSet attr
        ) throws BadLocationException {

            if (string == null) {
                return;
            }

            StringBuilder filtered =
                    new StringBuilder();

            for (char c : string.toCharArray()) {

                if (Character.isDigit(c)
                        || c == '.') {

                    filtered.append(c);
                }
            }

            String current =
                    fb.getDocument().getText(
                            0,
                            fb.getDocument().getLength()
                    );

            if (current.length()
                    + filtered.length() <= 10) {

                fb.insertString(
                        offset,
                        filtered.toString(),
                        attr
                );
            }
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int length,
                String text,
                AttributeSet attrs
        ) throws BadLocationException {

            if (text == null) {
                text = "";
            }

            StringBuilder filtered =
                    new StringBuilder();

            for (char c : text.toCharArray()) {

                if (Character.isDigit(c)
                        || c == '.') {

                    filtered.append(c);
                }
            }

            String current =
                    fb.getDocument().getText(
                            0,
                            fb.getDocument().getLength()
                    );

            int newLength =
                    current.length()
                            - length
                            + filtered.length();

            if (newLength <= 10) {

                fb.replace(
                        offset,
                        length,
                        filtered.toString(),
                        attrs
                );
            }
        }
    }
}