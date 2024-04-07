import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class MainApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.GRAY);

        JLabel label1 = new JLabel("Enter Company Name:");
        JTextField textField1 = new JTextField(10);

        JLabel label2 = new JLabel("Enter Your Name:");
        JTextField textField2 = new JTextField(10);

        JLabel label3 = new JLabel("Enter Your Phone Number:");
        JTextField textField3 = new JTextField(10);

        JButton openButton = new JButton("Open Second Window");
        openButton.setBackground(Color.decode("#4CAF50"));
        openButton.setForeground(Color.WHITE);

        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(new JLabel());
        panel.add(openButton);

        frame.add(panel, BorderLayout.CENTER);

        openButton.addActionListener(e -> {
            List<String> inputTexts = new ArrayList<>();
            inputTexts.add(textField1.getText());
            inputTexts.add(textField2.getText());
            inputTexts.add(textField3.getText());

            Collections.shuffle(inputTexts);
            JFrame secondFrame = new JFrame("Second Frame");
            secondFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            secondFrame.setLayout(null);

            Random random = new Random();

            JButton downloadButton = new JButton("Download");
            downloadButton.setBounds(150, 250, 100, 30);
            downloadButton.setBackground(Color.BLUE);
            downloadButton.setForeground(Color.WHITE);

            downloadButton.addActionListener(downloadEvent -> {
                try (FileWriter writer = new FileWriter("output.txt")) {
                    for (String text : inputTexts) {
                        writer.write(text + "\n");
                    }
                    JOptionPane.showMessageDialog(secondFrame, "File created successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            downloadButton.addActionListener(downloadEvent -> {
                try {
                    BufferedImage screenshot = new BufferedImage(secondFrame.getWidth(), secondFrame.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);

                    Graphics2D graphics = screenshot.createGraphics();

                    secondFrame.paint(graphics);

                    graphics.dispose();

                    File outputImageFile = new File("screenshot.png");
                    ImageIO.write(screenshot, "png", outputImageFile);

                    JOptionPane.showMessageDialog(secondFrame, "Screenshot saved as screenshot.png");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            secondFrame.setVisible(true);

            for (int i = 0; i < inputTexts.size(); i++) {
                JLabel label = new JLabel(inputTexts.get(i));
                label.setFont(new Font("Arial", Font.BOLD, random.nextInt(20) + 10));
                label.setForeground(Color.decode("#333333"));

                int x = 50 + 50 * i;
                int y = 100 + random.nextInt(200);

                label.setBounds(x, y, 200, 30);
                secondFrame.add(label);
            }

            secondFrame.add(downloadButton);
            secondFrame.setSize(400, 400);
        });

        frame.setVisible(true);
    }
}