
import javax.swing.JFrame;


import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import javax.swing.ScrollPaneConstants;


public class Window3{


	public static String arrMessage;
	public static BufferedImage BuffImg;
	static int [] msg;	
	
	static File UserFileSelected;
	
	public static void showWindow() {
		
		JEditorPane MessageBox = new JEditorPane();
		MessageBox.setEditable(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


		scrollPane.setViewportView(MessageBox);
		
		MessageBox.setDragEnabled(true);
	
		
		JFrame frame = new JFrame("Extract Message");
		frame.setBounds(100,100,640,440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();

		
		//------New Panel for Selected Image-------------------------------------------------------------------------------------------------------------	

		JLabel CoverImagePanel = new JLabel();
		
		

		//------Select Cover Image Button-------------------------------------------------------------------------------------------------------------	


		JButton SelectCoverImageBtn = new JButton("Select a Cover Image");
		SelectCoverImageBtn.setFont(new Font("Tahoma", Font.BOLD, 11));

		SelectCoverImageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				JFileChooser fc = new JFileChooser();

				BufferedImage MyPNG = null;

				int response = fc.showOpenDialog(null);

				if(response == JFileChooser.APPROVE_OPTION)
				{
					File file = new File(fc.getSelectedFile().getAbsolutePath());
					System.out.println(file);
					UserFileSelected = fc.getSelectedFile();


					try 
					{
						MyPNG = ImageIO.read(UserFileSelected);
					} 
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}



					BufferedImage BuffImage = null;
					try {

						BuffImage = ImageIO.read(UserFileSelected);						
						BuffImg = new BufferedImage(BuffImage.getWidth(), BuffImage.getHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics2D graphics = BuffImg.createGraphics();
						graphics.drawRenderedImage(BuffImage, null);
						graphics.dispose();

						//Storing the buffered image into a integer array and reading it with the data buffer further writing to it.
						int[] image = ((DataBufferInt)BuffImg.getRaster().getDataBuffer()).getData();





						/*
						//Loops through the bytes of the image - Length
						for (int i = 0; i < image.length; i++)
						{
							//Get the Red pixels <---- 16 back from 24
							int r = (image[i]>>16)&0xff;
							//Get the Green pixels <----- 8 back from 24
							int g = (image[i]>>8)&0xff;
							//Get the Blue pixels <----0 back from 24
							int b = image[i]&0xff;


							System.out.print("Red: ");
							System.out.println( Integer.toBinaryString(r));

							System.out.print("Green: ");
							System.out.println( Integer.toBinaryString(g));

							System.out.print("Blue: ");
							System.out.println( Integer.toBinaryString(b));

						}
						 */


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ImageIcon icon = new ImageIcon(MyPNG);
					Image img = icon.getImage();
					Image imgScale= img.getScaledInstance(CoverImagePanel.getWidth(), CoverImagePanel.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(imgScale);
					CoverImagePanel.setIcon(scaledIcon);

				}


			}
		});





		//------------------------------------------------------------------------------------------------------------------		



		JButton ExtractMessage = new JButton("Extract Message");
		ExtractMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				BufferedImage BuffImage = null;
				try 
				{
					BuffImage = ImageIO.read(UserFileSelected);
				} 
				catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				
				BufferedImage BuffImg = new BufferedImage(BuffImage.getWidth(), BuffImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics = BuffImg.createGraphics();
				graphics.drawRenderedImage(BuffImage, null);
				graphics.dispose();

				//Storing the buffered image into a integer array and reading it with the data buffer further writing to it.
				int[] image = ((DataBufferInt)BuffImg.getRaster().getDataBuffer()).getData();


				//Extraction Method
	
						
				String SecretMSG = ResultofImage(image, MessageBox);
				String FinalExtractedMessage = null;

				FinalExtractedMessage = SecretMSG.substring(0, SecretMSG.indexOf("~*"));		//Reference: https://stackoverflow.com/questions/7683448/in-java-how-to-get-substring-from-a-string-till-a-character-c

				MessageBox.setText(FinalExtractedMessage);				//Printing the message


			}
		});
		ExtractMessage.setFont(new Font("Tahoma", Font.BOLD, 11));

		//------------------------------------------------------------------------------------------------------------------


		JLabel MessageLabel = new JLabel("Extracted Secret Message:");
		MessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));



		//------------------------------------------------------------------------------------------------------------------


		JButton HomeBtn = new JButton("Back to Home");
		HomeBtn.setMnemonic(KeyEvent.VK_ACCEPT);
		HomeBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				//MyImageStegTool w1 = new MyImageStegTool();
				MyImageStegTool.showWindow();

			}
		});

		HomeBtn.setHorizontalAlignment(SwingConstants.LEFT);
		HomeBtn.setFont(new Font("Tahoma", Font.BOLD, 11));


		//------------------------------------------------------------------------------------------------------------------



		JButton btnEncryptEmbed = new JButton("Decrypt Message");
		btnEncryptEmbed.setFont(new Font("Tahoma", Font.BOLD, 11));
		
	

		//------------------------------------------------------------------------------------------------------------------


		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(MessageLabel, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
						.addComponent(SelectCoverImageBtn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addComponent(HomeBtn))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(96)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(ExtractMessage, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
								.addComponent(btnEncryptEmbed, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(CoverImagePanel, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)))
					.addGap(22))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(CoverImagePanel, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ExtractMessage, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEncryptEmbed, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(HomeBtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addGap(25)
							.addComponent(SelectCoverImageBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
							.addComponent(MessageLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		// TODO Auto-generated method stub
		
		

		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);


	}




	public static byte[] ExtractLSB( int[] image, JEditorPane MessageBox)
	{
		byte[] msg = new byte[1000];
		int ImageIndex = 0;

		System.out.print("My Message in Binary is --> ");
		for (int i = 0; i < msg.length; i++)		//Looping through bytes of the new empty MSG byte [] array 
		{
			for(int x = 0; x<8; x++)				//Looping through 8 bits at a time of each byte
			{
				
				//Filling Current Byte of MSG Array >> right shift by 1
				//Byte of MSG Array is OR'ed with -->(Current Image Byte AND'ed with 1)
				msg[i] = (byte) ((msg[i] << 1) | (image[ImageIndex] & 1));		//Left shifting MSG[i] Bytes by 1 left shift storing the LSB's in the MSG array
				
				System.out.println(Integer.toBinaryString(image[ImageIndex]));
				
				ImageIndex++; 	// Increment  to the next Byte of the image
			}

		}

		return msg;		//Return array after it is filled with the message data

	}



	public static String ResultofImage(int[] image,  JEditorPane MessageBox)
	{	
		byte[] MsgExtractArr;
		MsgExtractArr = ExtractLSB(image, MessageBox);		//Setting the result of the method to the new byte array MsgExtractArr[]

		return (new String(MsgExtractArr));					// returning the array as a new string and printing secret message back to the user.
	}



	public static void main(String[] args) 
	{
		showWindow();
		
	}
}
