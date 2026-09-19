package view;

import model.LifeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Period;

public class MainView extends JFrame
        implements PropertyChangeListener {

    private final JButton inputButton;

    private final JLabel birthDateLabel;
    private final JLabel ageLabel;
    private final JLabel daysLabel;
    private final JLabel sleepLabel;
    private final JLabel blinksLabel;
    private final JLabel heartLabel;
    private final JLabel bloodLabel;
    private final JLabel waterLabel;
    private final JLabel laughsLabel;

    private static final Color BACKGROUND =
            new Color(245, 247, 250);

    private static final Color CARD_BACKGROUND =
            Color.WHITE;

    private static final Color PRIMARY =
            new Color(52, 120, 246);

    private static final Color TEXT =
            new Color(35, 40, 45);

    private static final Color SECONDARY_TEXT =
            new Color(100, 108, 118);

    private static final Color BORDER =
            new Color(225, 229, 235);

    public MainView(LifeModel model) {

        setTitle("Калькулятор жизни");

        setSize(720, 680);

        setMinimumSize(new Dimension(650, 600));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(
                        new BorderLayout(20, 20));

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel("Калькулятор жизни");

        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 28)
        );

        titleLabel.setForeground(TEXT);

        JLabel subtitleLabel =
                new JLabel("Статистическая оценка основных показателей жизни");

        subtitleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        subtitleLabel.setForeground(SECONDARY_TEXT);

        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );

        titlePanel.setOpaque(false);

        titlePanel.add(titleLabel);

        titlePanel.add(Box.createVerticalStrut(4));

        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel cardPanel =
                new JPanel(new GridLayout(0, 2, 0, 0)
                );

        cardPanel.setBackground(CARD_BACKGROUND);

        cardPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(10, 20, 10, 20)
                )
        );

        birthDateLabel = createValueLabel("Не введена");

        ageLabel = createValueLabel("—");

        daysLabel = createValueLabel("—");

        sleepLabel = createValueLabel("—");

        blinksLabel = createValueLabel("—");

        heartLabel = createValueLabel("—");

        bloodLabel = createValueLabel("—");

        waterLabel = createValueLabel("—");

        laughsLabel = createValueLabel("—");

        addRow(cardPanel, "Дата рождения", birthDateLabel);

        addRow(cardPanel, "Возраст", ageLabel);

        addRow(cardPanel, "Прожито дней", daysLabel);

        addRow(cardPanel, "Проспано", sleepLabel);

        addRow(cardPanel, "Морганий", blinksLabel);

        addRow(cardPanel, "Ударов сердца", heartLabel);

        addRow(cardPanel, "Перекачано крови", bloodLabel);

        addRow(cardPanel, "Выпито воды", waterLabel);

        addRow(cardPanel, "Смеха", laughsLabel);

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        bottomPanel.setOpaque(false);


        inputButton = new JButton("Ввести данные"
        );

        inputButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        inputButton.setForeground(Color.WHITE);

        inputButton.setBackground(PRIMARY);

        inputButton.setFocusPainted(false);

        inputButton.setBorderPainted(false);

        inputButton.setOpaque(true);

        inputButton.setPreferredSize(
                new Dimension(170, 44)
        );

        bottomPanel.add(inputButton, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        model.addPropertyChangeListener(this);
    }

    private JLabel createValueLabel(
            String text
    ) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        label.setForeground(PRIMARY);

        label.setHorizontalAlignment(SwingConstants.RIGHT);

        return label;
    }

    private void addRow(
            JPanel panel,
            String name,
            JLabel value
    ) {

        JLabel nameLabel = new JLabel(name);

        nameLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        nameLabel.setForeground(TEXT);

        nameLabel.setBorder(
                new EmptyBorder(12, 0, 12, 0)
        );

        value.setBorder(
                new EmptyBorder(12, 0, 12, 0)
        );

        panel.add(nameLabel);
        panel.add(value);
    }

    public JButton getInputButton() {
        return inputButton;
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event
    ) {

        if (!event.getPropertyName().equals("birthDate")
                && !event.getPropertyName().equals("statistics")) {

            return;
        }

        LifeModel model =
                (LifeModel) event.getSource();

        if (model.getBirthDate() == null) {
            return;
        }

        birthDateLabel.setText(
                String.format(
                        "%02d.%02d.%04d",
                        model.getBirthDate().getDayOfMonth(),
                        model.getBirthDate().getMonthValue(),
                        model.getBirthDate().getYear()
                )
        );

        Period age = model.getAge();

        ageLabel.setText(
                age.getYears()
                        + " лет "
                        + age.getMonths()
                        + " мес. "
                        + age.getDays()
                        + " дн."
        );

        daysLabel.setText(
                String.format(
                        "%,d дней",
                        model.getDaysLived()
                )
        );

        sleepLabel.setText(
                String.format(
                        "%,.1f ч  /  %,.1f суток",
                        model.getSleepHours(),
                        model.getSleepDays()
                )
        );

        blinksLabel.setText(
                String.format(
                        "%,d раз",
                        model.getBlinks()
                )
        );

        heartLabel.setText(
                String.format(
                        "%,d ударов",
                        model.getHeartBeats()
                )
        );

        bloodLabel.setText(
                String.format(
                        "%,.1f л",
                        model.getBloodLiters()
                )
        );

        waterLabel.setText(
                String.format(
                        "%,.1f л",
                        model.getWaterLiters()
                )
        );

        laughsLabel.setText(
                String.format(
                        "%,d раз",
                        model.getLaughs()
                )
        );
    }
}