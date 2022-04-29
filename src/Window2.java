
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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class Window2{

	static File UserFileSelected;
	static long fileLimit;

	static 	long MeasureBytes;
	public static void showWindow() 
	{
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Embed Image");
		frame.setResizable(false);
		frame.setBounds(100,100,640,440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();

		//------New Panel for Selected Image-------------------------------------------------------------------------------------------------------------	

		JLabel CoverImagePanel = new JLabel();
		CoverImagePanel.setOpaque(true);


		//------Select Cover Image Button-------------------------------------------------------------------------------------------------------------	
		JButton SelectCoverImageBtn = new JButton("Select a Cover Image");
		SelectCoverImageBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		

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

					//----------------Measuring Embedding Capacity--------------------------------------------------------------------------
					Path FileMeasureByte = Paths.get(fc.getSelectedFile().getAbsolutePath());			
					MeasureBytes = 0;
					try 
					{
						MeasureBytes = Files.size(FileMeasureByte);
						lblNewLabel.setText("Max Embedding Capacity = " + String.valueOf(MeasureBytes/8 + " Characters"));
					} 
					catch (IOException e2) 
					{
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					System.out.print(" The Maximum embedding capacity is " + MeasureBytes / 8 + " characters or bytes" );
					
					//----------------------------------------------------------------------------------------------------------------------
					
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
						BufferedImage BuffImg = new BufferedImage(BuffImage.getWidth(), BuffImage.getHeight(), BufferedImage.TYPE_INT_RGB);
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
		
		JButton EmbedPlaintext = new JButton("Embed Plaintext");
		JEditorPane MessageBox = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(MessageBox);
		
		EmbedPlaintext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				
				String EnteredMSG = MessageBox.getText()+"~*";
				byte[] MSGbyteArray = EnteredMSG.getBytes();

				BufferedImage BuffImage = null;
				try
				{
					BuffImage = ImageIO.read(UserFileSelected);
					BufferedImage BuffImg = new BufferedImage(BuffImage.getWidth(), BuffImage.getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D graphics = BuffImg.createGraphics();
					graphics.drawRenderedImage(BuffImage, null);
					graphics.dispose();
			
					//Storing the buffered image within an integer array [] + reading it with the data buffer further writing to it.
					int[] image = ((DataBufferInt)BuffImg.getRaster().getDataBuffer()).getData();
				
					
					JFileChooser fc2 = new JFileChooser();
					int response = fc2.showSaveDialog(null);

					if(response == JFileChooser.APPROVE_OPTION)
					{
						
						File SavedNewImage = new File(fc2.getSelectedFile().getAbsolutePath());					
						UserFileSelected = fc2.getSelectedFile();
						System.out.println();
						System.out.println("Image Saved to --> " + SavedNewImage);
						System.out.println();

						
						BufferedImage EmbeddedImage = WriteStegoImage(image, BuffImg, MSGbyteArray);	//Writing altered image array with new LSB's to a new buffered image
						ImageIO.write(EmbeddedImage, "png", SavedNewImage);								//Saving + writing buffered image to a directory 

					}


				} 
				
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				
				
				
				//Printing Contents of the message ----------------------------------------------------

				System.out.println("The Secret Message being embedded is --> " + EnteredMSG);
				
				System.out.println("Secret Message in Binary --> ");
				for(byte l = 0; l < EnteredMSG.length(); l++)
				{
					//Reading Message in a full binary string bit by bit of each byte
					System.out.print("" + Integer.toBinaryString(MSGbyteArray[l]));		
				}
				
				//--------------------------------------------------------------------------------------

				return;

			}
		});

		EmbedPlaintext.setFont(new Font("Tahoma", Font.BOLD, 11));

		//------------------------------------------------------------------------------------------------------------------


		JLabel MessageLabel = new JLabel("Enter a Secret Message:");
		MessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));


		//------------------------------------------------------------------------------------------------------------------

		JButton HomeBtn = new JButton("Back to Home");
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



		JButton btnEncryptEmbed = new JButton("Encrypt & Embed");
		btnEncryptEmbed.setFont(new Font("Tahoma", Font.BOLD, 11));

		
		
	//	JLabel EmbeddingCapacityMSG = new JLabel("Max Embedding Capacity bytes = " + fileLimit);

		//------------------------------------------------------------------------------------------------------------------


		//------------------------------------------------------------------------------------------------------------------


		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(MessageLabel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addComponent(SelectCoverImageBtn, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(HomeBtn)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(CoverImagePanel, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(99)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(EmbedPlaintext, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
								.addComponent(btnEncryptEmbed, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))))
					.addGap(28))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(57)
					.addComponent(HomeBtn, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(EmbedPlaintext, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEncryptEmbed, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(SelectCoverImageBtn, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(54)
							.addComponent(lblNewLabel)
							.addGap(18)
							.addComponent(MessageLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
					.addGap(16))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(CoverImagePanel, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(120, Short.MAX_VALUE))
		);
		
		
		
		
		
				//------------------------------------------------------------------------------------------------------------------		
		
		
				

		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);


	}


	//---------------------Methods-------------------------------------

	//Reference --> https://stackoverflow.com/questions/26610638/changing-lsb-value-of-image-rgb-value-giving-inconsistent-value
	public static int[]EmbedMessage(int[] image, byte[]MSGbyteArray)
	{
		int CurrentByteIMG = 0;
		int MaskBits = 0xfffffffe;  // in binary 11111110

		System.out.println(" Original Image Bit's before embedding");
		System.out.println();
		System.out.println();
		for(int R = 0; R < MSGbyteArray.length; R ++)	//Loop through the length of the message
		{
			System.out.println(Integer.toBinaryString(image[R]));

		}


		
		System.out.println();
		System.out.println("Image After Embedding of LSB's ");
		System.out.println();
		
		for(int i = 0; i < MSGbyteArray.length; i ++)	//Looping through MSGByteArray - Entered Message
		{		
			for(int j = 7; j >= 0; j--)					//Looping through from MSB to the LSB
			{

				int BitOfMSG =  (MSGbyteArray[i] >> j) & 1;	//Extract MSG bits 1 by 1 using right shift then --> AND MSG bits with 1

				//Embedding into every 32nd bit of Image [A,R,G,B]
				image[CurrentByteIMG]  = (image[CurrentByteIMG]  & MaskBits) + BitOfMSG; //Current Byte of image[] & with 0xfffffffe to set first 31 bits to ZERO then added the LSB[Bit of MSG]
				System.out.println(Integer.toBinaryString(image[CurrentByteIMG]));
				CurrentByteIMG++;	//Incrementing the count.
			}

		}

		return image;
	}
	

	public static BufferedImage WriteStegoImage(int [] image, BufferedImage imageSteg, byte[] MSGbyteArray)
	{
		EmbedMessage(image, MSGbyteArray);	//Take the result (return image) from the Embed message  method
		
		System.out.println();
		System.out.println("Image Successfully Embedded !!!!");

		return imageSteg;	//writing the result from the EmbedMessage method to a new buffered image.

	}
	

	
	

	public static void main(String[] args) 
	{
		showWindow();			//Calling JFrame

	}
}



