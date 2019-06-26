import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class Download extends JFrame{
	public static String fileName ="fuvk" ;
	int k = 0;
	
	public Download() throws IOException {
		JFrame jf = new JFrame("input your save ID");
        jf.setSize(300, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(Download.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        final JTextField textField = new JTextField(8);
        textField.setFont(new Font(null, Font.PLAIN, 20));
        panel.add(textField);

        JButton btn = new JButton("OK");
        btn.setFont(new Font(null, Font.PLAIN, 20));
        btn.addActionListener(E->{
        	new Thread(() -> {
        		fileName = textField.getText();
                System.out.print(fileName);
                File file = new File("src\\"+ Download.fileName + ".json");
        		System.out.print(Download.fileName);
        		if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        		FileWriter writer = null;
				try {
					writer = new FileWriter(file);
					for(int i = 0;i<MainFrame.downloadList.size();i++) {
						writer.write(MainFrame.downloadList.get(i));
					}
					writer.flush();
					writer.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		

                jf.dispose();
        }).start();
        });
        panel.add(btn);

        jf.setContentPane(panel);
        jf.setVisible(true);
		
	}
}
