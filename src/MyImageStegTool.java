import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class MyImageStegTool{



	public static void showWindow() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Steganography Messaging Tool");
		frame.setResizable(false);
		frame.setBounds(100,100,640,440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//--------------------------------------------------------------------------------------	

		JLabel TitleLabel = new JLabel("Steganography Messaging Tool");
		TitleLabel.setForeground(Color.BLACK);
		TitleLabel.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 16));
		TitleLabel.setBounds(171, 97, 281, 35);
		frame.getContentPane().add(TitleLabel);


		//------Embed Button--------------------------------------------------------------------


		JButton EmbedBtn = new JButton("Embed");

		EmbedBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				Window2 w2 = new Window2();
				Window2.showWindow();

			}

		});

		EmbedBtn.setAutoscrolls(true);
		EmbedBtn.setBounds(126, 178, 159, 90);
		frame.getContentPane().add(EmbedBtn);

	

		//------Extract Button-----------------------------------------------------------------------

		JButton ExtractBtn = new JButton("Extract");

		ExtractBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{

				frame.setVisible(false);
				Window3 w3 = new Window3();
				Window3.showWindow();

			}

		});

		ExtractBtn.setAutoscrolls(true);
		ExtractBtn.setBounds(336, 178, 159, 90);


		//-------------------------------------------------------------------------------------------			

		frame.getContentPane().add(ExtractBtn);
		frame.setVisible(true);

	}






	public static void main(String[] args) {
		
		showWindow();
	}
}
