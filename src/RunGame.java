import java.awt.*;
import java.awt.event.*;
//import java.util.ArrayList;
import javax.swing.*;

public class RunGame extends JPanel implements ActionListener {

    private ImageIcon BgPhase1 = new ImageIcon(this.getClass().getResource("11.png"));
    private ImageIcon BgPhase2 = new ImageIcon(this.getClass().getResource("12.png"));
    private ImageIcon Happy = new ImageIcon(this.getClass().getResource("HappyEnd.jpg"));
    private ImageIcon Bad = new ImageIcon(this.getClass().getResource("BadEnd.jpg"));
    //private ImageIcon BgPhase3 = new ImageIcon(this.getClass().getResource("2.Endpic.png"));
    private ImageIcon Bcon = new ImageIcon(this.getClass().getResource("confirm.png"));
    public ImageIcon[] imgmonster1 = new ImageIcon[10];
    public ImageIcon[] imgmonster2 = new ImageIcon[15];
    public ImageIcon[] imgguy = new ImageIcon[5];

    public JTextField Answer = new JTextField(20);
    public JButton Confirm = new JButton(Bcon);
    //public ArrayList<Monster> monster1 = new ArrayList<>();
    
    public int times = 60;
    public int HP = 3;
    public int number = 10;
    public boolean timestart = true;
    public boolean correct = false;
    public boolean Running = true;
    public boolean Runningguy = true;
    public int Question;
    public int q1;
    public int q2;
    public int q3;
    public String Q1;
    public String Q2;
    public String Q3;
    public int Score = 0;
    public int count = 0;
    public int n;

    int monsterX = -300;
    int monsterY = 350; 
    int monsterX2 = -900;
    int monsterY2 = 0; 
    int guyX = 1500;
    int guyXstop = 0;
    int currentImageIndex = 0; 
    int currentImageIndex2 = 0;
    int currentImageIndexG = 0;
    Thread time;
    Thread time2;
    Thread timeguy;
    Thread questionThread; 
    Thread countdown;
    Thread moveThread;

    public RunGame() {
        setLayout(null); // ใช้การจัดตำแหน่งแบบสัมบูรณ์สำหรับเลย์เอาต์ที่กำหนดเอง
        // โหลดภาพมอนสเตอร์
        for (int i = 0; i < 10; i++) {
            String imgm1 = "m" + (i + 1) + ".png";
            imgmonster1[i] = new ImageIcon(this.getClass().getResource(imgm1));  
        }
        for (int i = 0; i < 15; i++) {
            if(i<=7){
                String imgm2 = "2m1.png";
                imgmonster2[i] = new ImageIcon(this.getClass().getResource(imgm2));  
            }else if(i <= 10){
                String imgm2 = "2m" + (i-6) + ".png";
                imgmonster2[i] = new ImageIcon(this.getClass().getResource(imgm2));  
            }else if(i == 11){
                String imgm2 = "2m1.png";
                imgmonster2[i] = new ImageIcon(this.getClass().getResource(imgm2)); 
            }else if(i <= 14){
                String imgm2 = "2m" + (i-7) + ".png";
                imgmonster2[i] = new ImageIcon(this.getClass().getResource(imgm2));  
            }
        }
        for (int i = 0; i < 5; i++) {
            String imgG = "guy" + (i + 1) + ".png";
            imgguy[i] = new ImageIcon(this.getClass().getResource(imgG));  
        }

        // กำหนดฟิลด์ข้อความ Answer

            Answer.setFont(new Font("Dosis", Font.PLAIN, 30));
            Answer.setHorizontalAlignment(JTextField.CENTER);
            Answer.setBounds(1020, 120, 100, 60);
            add(Answer);

            // กำหนดปุ่ม Confirm
            Confirm.setBounds(1150, 110, 80, 80); // ปรับตำแหน่งตามต้องการ
            add(Confirm);
            Confirm.addActionListener(this); // เพิ่ม Action Listener ให้กับ Confirm

            // เริ่มเธรดสำหรับการเคลื่อนไหวและการนับถอยหลัง
            startAnimationM1Thread();
            startAnimationM2Thread();
            startAnimationGuyThread();
            startMoveForwardThread();
            startCountdownThread();
            QuestionThread();
        
    }
//การขยับของ มอนเตอร์ ตัวแรก
    private void startAnimationM1Thread() {
        time = new Thread(() -> {
            while (Running) {
                try {
                    Thread.sleep(150); // เปลี่ยนภาพทุก 0.15 วินาที
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!timestart) {
                    currentImageIndex = (currentImageIndex + 1) % imgmonster1.length; 
                    repaint(); // อัปเดตกราฟิก
                }
            }
        });
        time.start();
    }
//การขยับของ มอนเตอร์ ตัวสอง
    private void startAnimationM2Thread() {
        time2 = new Thread(() -> {
            while (Running) {
                try {
                    Thread.sleep(150); 
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!timestart) {
                    currentImageIndex2 = (currentImageIndex2 + 1) % imgmonster2.length; 
                    repaint(); // อัปเดตกราฟิก
                }
            }
        });
        time2.start();
    }
//การขยับของ ชายหลบหนี
    private void startAnimationGuyThread() {
        timeguy = new Thread(() -> {
            while (Runningguy) {
                try {
                    Thread.sleep(50); // เปลี่ยนภาพทุก 0.1 วินาที
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!timestart) {
                    currentImageIndexG = (currentImageIndexG + 1) % imgguy.length; 
                    repaint(); // อัปเดตกราฟิก
                }
            }
        });
        timeguy.start();
    }
//การเคลื่อนที่ไปด้านหน้าของตัวละคร
    private void startMoveForwardThread() {
        moveThread = new Thread(() -> {
            while (Runningguy) {
                try {
                    Thread.sleep(140); // ความเร็วในการเคลื่อนที่
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    repaint();
                
            }
        });
        moveThread.start();
    }
//ตัวจับเวลา 60 วินาที
    private void startCountdownThread() {
        countdown = new Thread(() -> {
            while (Running) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!timestart) {
                    times -= 1;
                    repaint(); // อัปเดตกราฟิกด้วยเวลาใหม่
                }
            }
        });
        countdown.start();
    }
// Thread สำหรับสร้างโจทย์ข้อถัดไปเมื่อหมดเวลา
    private void QuestionThread(){
        new Thread(() ->{
            while(Running){
                try{
                    if(number <=2){
                        q1 = (int) (Math.random()*1000);
                        q2 = (int) (Math.random()*1000);
                        Q1 = String.valueOf(q1);
                        Q2 = String.valueOf(q2);
                        Question = q1+q2;
                    }else if(number <=4){
                        q1 = (int) (Math.random()*1000);
                        q2 = (int) (Math.random()*1000);
                        Q1 = String.valueOf(q1);
                        Q2 = String.valueOf(q2);
                        Question = q1-q2;
                    }else if(number == 5){
                        q1 = (int) (Math.random()*1000);
                        q2 = (int) (Math.random()*1000);
                        q3 = (int) (Math.random()*1000);
                        Q1 = String.valueOf(q1);
                        Q2 = String.valueOf(q2);
                        Q3 = String.valueOf(q3);
                        Question = q1+q2-q3;
                    }else if (number <= 7) {
                        q1 = (int) (Math.random() * 100);
                        q2 = (int) (Math.random() * 100);
                        q3 = (int) (Math.random() * 100);
                        Q1 = String.valueOf(q1);
                        Q2 = String.valueOf(q2);
                        Q3 = String.valueOf(q3);
                        Question = q1 * q2 * q3;
                    }else if (number <= 9) {
                        q1 = (int) (Math.random() * 999) + 1;
                        q2 = (int) (Math.random() * 99) + 1;
                        q3 = (int) (Math.random() * 99) + 1;
                        Q1 = String.valueOf(q1);
                        Q2 = String.valueOf(q2);
                        Q3 = String.valueOf(q3);
                        Question = q1 / q2 / q3;
                    }else if(number == 10){
                        q1 = (int) (Math.random() * 999) + 1;
                        q2 = (int) (Math.random() * 999) + 1;
                        q3 = (int) (Math.random() * 999) + 1; 
                        Q1 = String.valueOf(q1);
                        Q2 = String.valueOf(q2);
                        Q3 = String.valueOf(q3);
                        Question = q1 * q2 / q3 * q1 / q3 * q2 / q1 * q2 ;
                    }

                    Thread.sleep(1000000);
                    number++;
                    times=60;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
//Thread หยุดการทำงานเมื่อตอบ
    private void startQuestionThread() {
        if (questionThread != null && questionThread.isAlive()) {
            questionThread.interrupt();  // หยุดเธรดเดิมก่อน
        }
        questionThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    generateNewQuestion();  // สร้างโจทย์ใหม่ตามเงื่อนไข number
                    Thread.sleep(60000);   // รอ 60 วินาที
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // รีเซ็ตสถานะ interrupted
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        questionThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // ตรวจสอบสถานะของเกม
        if (HP <= 0 || number > 10) {
            this.setLayout(null);
            this.remove(Answer);
            this.remove(Confirm);
            String score = Integer.toString(Score);
            if(HP > 0){
                g.drawImage(Happy.getImage(), 0, 0, 1800, 800, this);
                EndGame(g);
                g.setFont(new Font("Dosis",Font.CENTER_BASELINE,85));		
                g.drawString("You Win",735,200);
                g.setFont(new Font("Dosis",Font.CENTER_BASELINE,65));	
                g.setColor(Color.YELLOW);
                g.drawString("SCORE : "+score,765,400);
            }
            else{
                g.drawImage(Bad.getImage(), 0, 0, 1800, 800, this);
                EndGame(g);
                g.setFont(new Font("Dosis",Font.CENTER_BASELINE,85));		
                g.drawString("You Lose",735,200);
                g.setFont(new Font("Dosis",Font.CENTER_BASELINE,65));	
                g.setColor(Color.YELLOW);
                g.drawString("SCORE : "+score,765,400);
            }
                stopAllThreads();
        } else if (number >= 6) {
            g.drawImage(BgPhase2.getImage(), 0, 0, 1800, 800, this);
            //g.drawImage(imgguy[currentImageIndexG].getImage(), 1500, 520, 200, 200, this);
            g.drawImage(imgguy[currentImageIndexG].getImage(), guyX, 520, 200, 200, this);
            if(correct == true){
                g.drawImage(imgguy[currentImageIndexG].getImage(), guyX, 520, 200, 200, this);
                guyX+=30;
                guyXstop += 30 ;
                if(guyXstop >= 1800){
                    //Runningguy = false;
                    correct = false;
                    guyXstop = 0;
                }
                if(guyX == 1800){
                    guyX = 0;
                }
            }
            g.drawImage(imgmonster2[currentImageIndex2].getImage(), monsterX2, monsterY2, 900, 900, this);
            monsterX2 += 1;
            drawGameStatus(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 50));
            if(number <=7){
                g.drawString(Q1 + "*" + Q2 + "*" + Q3 + " = ",650,175 );
            }
            else if(number <=9){
                g.drawString(Q1 + "/" + Q2 + "/" + Q3 +" = ",650,175 );
            }
            else if(number == 10){
                g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 20));
                g.drawString(Q1 + "*" + Q2 + "/" + Q3 + "*" + Q1 + "/" + Q3 + "*" + Q2 + "/" + Q1 + "*" + Q2 + " = ",650,165 );
                System.out.println(Question);
            }
            // โจทย์ข้อ 1 - 5
        } else { 
            g.drawImage(BgPhase1.getImage(), 0, 0, 1800, 800, this);
            // วาดตัวละครหลักและมอนสเตอร์
            g.drawImage(imgguy[currentImageIndexG].getImage(), guyX, 520, 200, 200, this);
            if(correct == true){
                g.drawImage(imgguy[currentImageIndexG].getImage(), guyX, 520, 200, 200, this);
                guyX+=20;
                guyXstop += 20 ;
                if(guyXstop >= 1800){
                    //Runningguy = false;
                    correct = false;
                    guyXstop = 0;
                }
                if(guyX == 1800){
                    guyX = 0;
                }
            }
            g.drawImage(imgmonster1[currentImageIndex].getImage(), monsterX, monsterY, 512, 512, this);
            monsterX += 1;
            drawGameStatus(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 50));
            if(number <=2){
                g.drawString(Q1+"+"+Q2+" = ",650,175 );
            }
            else if(number <=4){
                g.drawString(Q1+"-"+Q2+" = ",650,175 );
            }
            else if(number == 5){
                g.drawString(Q1+"+"+Q2+"-"+Q3+ " = ",650,175 );
            }
        }
    }
    private void drawGameStatus(Graphics g) {
        // แสดงสถานะเวลาและ HP ของผู้เล่น
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 50));
        g.drawString("Time: " + times, 820, 80);
        g.setColor(Color.BLACK);
        g.fillRect(640, 100, 500, 100); 
        g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 20));
        g.drawString(number + ") ", 650, 125);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 50));
        g.drawString("HP: " + HP, 25, 70);
        if(times < 0 || monsterX == 1320 || monsterX2 == 850){
            times = 60;
            number ++;
            monsterX = -300;
            monsterX2 = -900;
            HP--;
            generateNewQuestion();
            startQuestionThread();
        }else{
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dosis", Font.CENTER_BASELINE, 20));
            g.drawString(number + ") " , 650, 125);
        }
    }

    private void EndGame(Graphics g){
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(10)); // กำหนดความหนาเป็น 5 หน่วย
		g2.drawRect(300, 100, 1200, 150);
        g.setColor(Color.YELLOW);
		g.fillRect(300, 100, 1200, 150);
		g.setColor(Color.BLACK);
    }
    

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Confirm) {
            String answerText = Answer.getText(); // อ่านคำตอบจาก JTextField
            int answerValue;
            try {
                answerValue = Integer.parseInt(answerText);
                if (answerValue == Question) {
                    correct = true;
                    Score++;
                    monsterX = -300;  // รีเซ็ตตำแหน่งมอนสเตอร์ 
                    monsterX2 = -900;
                    times = 60;  // รีเซ็ตเวลา
                    number ++;
                    System.out.println("Correct");
                    startQuestionThread(); // เริ่มเธรดใหม่เพื่อสร้างโจทย์คำถามใหม่
                } else {
                    times = -1; // รีเซ็ตตำแหน่งมอนสเตอร์ให้ไปสุดหน้าจอ
                    System.out.println("Wrong");
                }
    
                // ไปยังข้อถัดไปโดยเพิ่ม number และสร้างโจทย์ใหม่
                generateNewQuestion();
    
                // รีเซ็ต JTextField และรีเฟรชหน้าจอ
                Answer.setText("");
                repaint();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid");
            }
        }
    }

private void generateNewQuestion() {
    if (number <= 2) {
        q1 = (int) (Math.random() * 1000);
        q2 = (int) (Math.random() * 1000);
        Q1 = String.valueOf(q1);
        Q2 = String.valueOf(q2);
        Question = q1 + q2;
    } else if (number <= 4) {
        q1 = (int) (Math.random() * 1000);
        q2 = (int) (Math.random() * 1000);
        Q1 = String.valueOf(q1);
        Q2 = String.valueOf(q2);
        Question = q1 - q2;
    } else if (number == 5) {
        q1 = (int) (Math.random() * 1000);
        q2 = (int) (Math.random() * 1000);
        q3 = (int) (Math.random() * 1000);
        Q1 = String.valueOf(q1);
        Q2 = String.valueOf(q2);
        Q3 = String.valueOf(q3);
        Question = q1 + q2 - q3;
    } else if (number <= 7) {
        q1 = (int) (Math.random() * 100);
        q2 = (int) (Math.random() * 100);
        q3 = (int) (Math.random() * 100);
        Q1 = String.valueOf(q1);
        Q2 = String.valueOf(q2);
        Q3 = String.valueOf(q3);
        Question = q1 * q2 * q3;
    } else if (number <= 9) {
        q1 = (int) (Math.random() * 999) + 1;
        q2 = (int) (Math.random() * 99) + 1;
        q3 = (int) (Math.random() * 9) + 1;
        Q1 = String.valueOf(q1);
        Q2 = String.valueOf(q2);
        Q3 = String.valueOf(q3);
        Question = q1 / q2 / q3;
    }else if(number == 10){
        q1 = (int) (Math.random() * 999) + 1;
        q2 = (int) (Math.random() * 999) + 1;
        q3 = (int) (Math.random() * 999) + 1; // จะได้ค่า 1 ถึง 999
        Q1 = String.valueOf(q1);
        Q2 = String.valueOf(q2);
        Q3 = String.valueOf(q3);
        Question = q1 * q2 / q3 * q1 / q3 * q2 / q1 * q2 ;
    }
}

// หยุกการทำงานเมื่อ จบเกม
private void stopAllThreads() {
    Running = false;
    Runningguy = false;
    if (time != null) time.interrupt();
    if (time2 != null) time2.interrupt();
    if (timeguy != null) timeguy.interrupt();
    if (countdown != null) countdown.interrupt();
    if (questionThread != null) questionThread.interrupt();
}
}


