package com.chatting;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Server extends JFrame {
	 ServerSocket server;
	    Socket socket;
	    BufferedReader br;
	    PrintWriter out;
	     
	  //declare component
	    private JLabel heading=new JLabel("Server Area");
	    private JTextArea messageArea=new JTextArea();
	    private JTextField messageInput=new JTextField();
	    private Font font=new Font("Roboto",Font.PLAIN,20);

	    public Server() {
	        try {
	           server = new ServerSocket(7789);
	            System.out.println("Server is ready  to accept connection");
	          System.out.println("waiting....");
	            socket = server.accept();
	           br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            out = new PrintWriter(socket.getOutputStream());
	        	createGUI();
	        	handleEvents();
	        	
	        	startReading();
	            //startWriting();
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }

	    }
	    
	    

private void handleEvents() {
	messageInput.addKeyListener(new KeyListener()
			{

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
					
				}

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					//System.out.println("key released"+e.getKeyCode());
					if(e.getKeyCode()==10)
					{
					String contentToSend=messageInput.getText();
					messageArea.append("Me:"+contentToSend+"\n");
					
					out.println(contentToSend);
					out.flush();
					messageInput.setText(" ");
					messageInput.requestFocus();
					}
					
				}
		
			});
	
}

		public void createGUI()
	    {
	        this.setTitle("Server Message");
	        this.setSize(500,500);
	        this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
	        
	        heading.setFont(font);
	        messageArea.setFont(font);
	        messageInput.setFont(font);
	        heading.setHorizontalAlignment(SwingConstants.CENTER);
	        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	        messageArea.setEditable(false);
	        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
	        
	        //frame layout
	        this.setLayout(new BorderLayout());
	        
	      //adding compinet to frame
	        this.add(heading,BorderLayout.NORTH);
	        JScrollPane jScrollPane=new JScrollPane(messageArea);
	        this.add(jScrollPane,BorderLayout.CENTER);
	        this.add(messageInput,BorderLayout.SOUTH);
	    }
	    
	    

	    public void startReading() {
	        // thread-read karke deta rahega
	        Runnable r1 = () -> {
	            System.out.println("reader started....");
	            try{
	            while (true) {
	                
	               
	                String msg = br.readLine();
	                if (msg.equals("exit")) {
	                	 JOptionPane.showMessageDialog(this, "Client terminated the chatt");
	                     messageInput.setEnabled(false);
	                    socket.close();
	                    
	                    break;

	                }
	                //System.out.println("Client:"+msg);
	                messageArea.append("Server:"+msg+ "\n");
	               

	            } 
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        };
	        new Thread(r1).start();
	    }

	    public void startWriting() {
	        // thread-data user lega and then send karega client tak
	        Runnable r2 = () -> {
	            System.out.println("writer started");
	            try{
	            while(true)
	            {
	                
	                    BufferedReader br1=new BufferedReader(new  InputStreamReader(System.in));
	                    String content =br1.readLine();
	                    
	                    out.println(content);
	                    out.flush();
	                    if(content.equals("exit"))
	                    {
	                        socket.close();
	                        break;
	                    }
	                    
	                
	            }
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }

	        };
	        new Thread(r2).start();
	    }

	    public static void main(String[] args) {
	        System.out.println("this is server..going to start server");
	        new Server();
	    }

}
