import java.io.*; import java.net.*; 

	class UDPServer {    
	public static void main(String args[]) throws Exception       
	{          

		new Thread(){
			final DatagramSocket serverSocket = new DatagramSocket(8000);
			public void run()
			{
				while(true)                
				{                  
					try {             
							             
							final byte[] receiveData = new byte[1024];             
							byte[] sendData = new byte[1024];
							DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);                   
							serverSocket.receive(receivePacket);                  
							String sentence = new String( receivePacket.getData());                   
							System.out.println("RECEIVED: " + sentence);                   
							InetAddress IPAddress = receivePacket.getAddress();                  
							int port = receivePacket.getPort();                   
							String capitalizedSentence = sentence.toUpperCase();                   
							sendData = capitalizedSentence.getBytes();                   
							DatagramPacket sendPacket =                   
							new DatagramPacket(sendData, sendData.length, IPAddress, port);                  
							serverSocket.send(sendPacket);               
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}   
				}
			}  

			@Override
			public void interrupt() {
				serverSocket.close();
				super.interrupt();
			}
			
			
		}.start();
		
		} 
	} 