import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener {

    static JFrame f=new JFrame();
    JTextField text;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static DataOutputStream dout;
    Server(){
        f.setLayout(null);

        JPanel jPanel=new JPanel();
        jPanel.setBackground(new Color(7, 84, 74));
        jPanel.setBounds(0,0,450,70);
        jPanel.setLayout(null);
        f.add(jPanel);

        ImageIcon I1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=I1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        jPanel.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               System.exit(0);
            }
        });

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image I2=i1.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon I3=new ImageIcon(I2);
        JLabel profile=new JLabel(I3);
        profile.setBounds(40,10,50,50);
        jPanel.add(profile);

        ImageIcon v=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image v2=v.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon v3=new ImageIcon(v2);
        JLabel vedio=new JLabel(v3);
        vedio.setBounds(300,20,30,30);
        jPanel.add(vedio);

        ImageIcon p=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image p2=p.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon p3=new ImageIcon(p2);
        JLabel phone=new JLabel(p3);
        phone.setBounds(360,20,35,30);
        jPanel.add(phone);

        ImageIcon m=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image m2=m.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon m3=new ImageIcon(m2);
        JLabel moreVert=new JLabel(m3);
        moreVert.setBounds(420,20,8,25);
        jPanel.add(moreVert);

        JLabel name=new JLabel("Abdul Sami");
        name.setBounds(110,15,100,12);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial",Font.BOLD,13));
        jPanel.add(name);

        JLabel Status=new JLabel("Active Now");
        Status.setBounds(110,35,100,18);
        Status.setForeground(Color.WHITE);
        Status.setFont(new Font("Arial",Font.BOLD,10));
        jPanel.add(Status);

        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);
        f.setUndecorated(true);
        f.setSize(450,700);
        f.setLocation(200,30);
        f.getContentPane().setBackground(Color.WHITE);

     text=new JTextField();
     text.setBounds(5,655,310,40);
     text.setFont(new Font("Arial",Font.PLAIN,16));
        f.add(text);

     JButton send=new JButton("Send");
     send.setBounds(320,655,123,40);
     send.setFont(new Font("Arial",Font.PLAIN,16));
     send.setForeground(Color.WHITE);
     send.setBackground(new Color(7, 84, 74));
     send.addActionListener(this);
        f.add(send);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    String ms=text.getText();
    JPanel msp=formatLabel(ms);
    a1.setLayout(new BorderLayout());
    JPanel right=new JPanel(new BorderLayout());
    right.add(msp,BorderLayout.LINE_END);
    vertical.add(right);
    vertical.add(Box.createVerticalStrut(15));
    a1.add(vertical,BorderLayout.PAGE_START);

    text.setText("");
        try {
            dout.writeUTF(ms);
        } catch (Exception ex) {
           ex.printStackTrace();
        }

        f.repaint();
        f.invalidate();
        f.validate();
    }
    void sendv() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file=new File("audio.wav");
        AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(file);
        Clip clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public static JPanel formatLabel(String out){
      JPanel panel=new JPanel();
      panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
      JLabel output=new JLabel("<html><p style=\"width: 115px\">"+out+"</p></html>");
      output.setFont(new Font("Thoma",Font.PLAIN,16));
      output.setBackground(new Color(37,211,102));
      output.setOpaque(true);
      output.setBorder(new EmptyBorder(15,15,15,50));

      panel.add(output);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

      return panel;
    }
    public static void serverMake(){

    }
    public static void main(String[] args) {
        new Server();

        try {
            ServerSocket skt =new ServerSocket(6001);
            while (true){
                Socket s=skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                 dout=new DataOutputStream(s.getOutputStream());
                while (true){
                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
