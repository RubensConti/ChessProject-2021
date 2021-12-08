package gui;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class StartScreen extends JFrame {


    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 338);



    public StartScreen() {

        final BlinkButton startButton = new BlinkButton("Press here to start");
        startButton.setBounds(150,280,300,20);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setEnabled(true);
        startButton.setForeground(Color.WHITE);
        startButton.setBlink(Color.BLACK);
        startButton.start();
        add(startButton, BorderLayout.CENTER);


        setSize(OUTER_FRAME_DIMENSION);
        assignBackground();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Table table = new Table();
                playSound("sounds/cat.wav");
                startButton.stop();
                playSoundLoop("sounds/background.wav");
                dispose();
            }
        });

    }


    private void assignBackground(){
        try {
            final BufferedImage image = ImageIO.read(new File("bk/bk.png"));
            Image scaled = image.getScaledInstance(600,338,Image.SCALE_SMOOTH);
            add(new JLabel(new ImageIcon(scaled)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void playSound (String soundName){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip= AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void playSoundLoop (String soundName){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip= AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e){
            e.printStackTrace();
        }
    }



    public class BlinkButton extends JButton {
        private static final long serialVersionUID = 0x525350504700000EL;

        private Timer blinkTimer;
        private boolean blinked;
        private Color blinkColour = Color.black;
        private Color foreground;

        /**
         * Creates a button with text and default blink interval of 1s.
         * @param text
         * The text to be displayed on the button.
         */
        public BlinkButton(String text) {
            this();
            this.setText(text);
        }

        /**
         * Creates a button with text and sets the blink interval.
         * @param text
         * The text to be displayed on the button.
         * @param interval
         * The number of milliSeconds between changes in colour.
         */
        public BlinkButton(String text, int interval ) {
            this(interval);
            this.setText(text);
        }

        public BlinkButton() {
            super();
            blinkTimer = new Timer(400, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    blinked = !blinked;
                    repaint();
                }
            });
            blinkTimer.setRepeats(true);
            foreground = getForeground();
        }

        /**
         * Creates a <code>BlinkButton</code> which will blink at the specified intervael.
         * @param interval
         * the blink interval in milliseconds.
         */
        public BlinkButton(int interval) {
            super();
            blinkTimer = new Timer(interval, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    blinked = !blinked;
                    repaint();
                }
            });
            blinkTimer.setRepeats(true);
            foreground = getForeground();
        }

        /**
         * Sets the foreground colour
         */
        public void setForeground(Color foreground) {
            this.foreground = foreground;
            if (!blinked)
                super.setForeground(foreground);
        }

        /**
         * Sets the blink colour
         * @param blink
         * the colour to blink (alternate) with the foregroundColor
         */
        public void setBlink(Color blink) {
            this.blinkColour = blink;
        }

        public void paintComponent(Graphics g) {

            if (blinked) {
                super.setForeground(foreground);
            } else {
                super.setForeground(blinkColour);
            }
            super.paintComponent(g);
        }

        /**
         * Overrides the default method to also start and stop
         * the <code>BlinkButton</code> when it is enabled/disabled.
         */
        public void setEnabled(boolean enable) {
            super.setEnabled(enable);
            if (enable)
                start();
            else
                stop();
        }

        /**
         * Starts the <code>BlinkButton</code> blinking.
         */
        public void start() {
            blinkTimer.start();
        }
        /**
         * Stops the <code>BlinkButton</code> blinking.
         */
        public void stop() {
            super.setForeground(foreground);
            blinked = false;
            blinkTimer.stop();
        }

    }



}


