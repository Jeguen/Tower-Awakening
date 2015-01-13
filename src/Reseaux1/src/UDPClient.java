import java.io.*; import java.net.*; 

class UDPClient {   
	
	public static void main(String args[]) throws Exception    
	{
        Thread monThread = new Thread(){
            
            public void run(){
                
                while(true)
					try {
						{
						    
						BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
						DatagramSocket clientSocket;
						
						try {
							clientSocket = new DatagramSocket();
							InetAddress IPAddress = InetAddress.getByName("localhost");
						    byte[] sendData = new byte[1024];
						    byte[] receiveData = new byte[1024];
						    String sentence = inFromUser.readLine();
						    sendData = sentence.getBytes();
						    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8000);
						    clientSocket.send(sendPacket);
						    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						    clientSocket.receive(receivePacket);
						    String modifiedSentence = new String(receivePacket.getData());
						    System.out.println("FROM SERVER:" + modifiedSentence);       
						    clientSocket.close();
							
						} catch ( IOException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
						
						    
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                
            }
            
            
            
        };
        monThread.start();
        
				 
				
	}
	
} 