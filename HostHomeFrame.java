import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class HostHomeFrame extends JFrame {
    private JPanel mainPanel, textPanel, buttonPanel;
    private JLabel title;
    private JButton animalBtn, foodBtn, objectBtn, customBtn;
    Socket hostsocket = null;
    PrintWriter out = null;

    public HostHomeFrame(Socket hostsocket) {
        this.hostsocket = hostsocket;

        try {
            out = new PrintWriter(hostsocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("CatchMind - 출제자");
        setSize(900, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // 연한 파란색 배경

        title = new JLabel("캐치마인드에 오신 것을 환영합니다~!", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        title.setBorder(BorderFactory.createEmptyBorder(60, 20, 40, 20));
        mainPanel.add(title, BorderLayout.NORTH);

        // 버튼 패널을 GridBagLayout으로 변경하여 중앙 배치
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));

        buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setPreferredSize(new Dimension(600, 300));
        buttonPanel.setBackground(new Color(240, 248, 255));

        animalBtn = createStyledButton("🐾 동물", new Color(100, 200, 100));
        foodBtn = createStyledButton("🍕 음식", new Color(255, 180, 100));
        objectBtn = createStyledButton("📦 사물", new Color(150, 180, 255));
        customBtn = createStyledButton("✏️ 사용자 직접 출제", new Color(255, 150, 180));

        animalBtn.addActionListener(e -> {
            out.println("animal");
            new HostDrawFrame(hostsocket);
            dispose();
        });

        foodBtn.addActionListener(e -> {
            out.println("food");
            new HostDrawFrame(hostsocket);
            dispose();
        });

        objectBtn.addActionListener(e -> {
            out.println("object");
            new HostDrawFrame(hostsocket);
            dispose();
        });

        customBtn.addActionListener(e -> {
            out.println("custom");
            new HostCustomFrame(hostsocket);
            dispose();
        });

        buttonPanel.add(animalBtn);
        buttonPanel.add(foodBtn);
        buttonPanel.add(objectBtn);
        buttonPanel.add(customBtn);

        centerPanel.add(buttonPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 스타일이 적용된 버튼 생성
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 20f));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 호버 효과
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}