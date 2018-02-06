import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import java.net.*;

import java.io.*;

import java.util.*;

public class TestServer {
   
   public static HashMap<Integer, Vector<Vector<String>>> messageQueue    = new HashMap<Integer, Vector<Vector<String>>>();
   public static HashMap<Integer, Vector<Vector<String>>> messageDispatch = new HashMap<Integer, Vector<Vector<String>>>();
   private Socket clientSocket = null;
   public static final boolean DEBUG_MODE = true;

   public static void main(String[] args) throws Exception {
      TestServer dummy = new TestServer();
      dummy.startServer(args);
   }

   public void startServer(String[] args) throws Exception {
      if (args.length < 1) {
         System.out.println("ERROR: Not enough parameters. Usage: java TestServer <port>");
         System.exit(1);
      } 

      int portNumber = Integer.parseInt(args[0]);
      System.out.println("Listening on port " + portNumber + ".");
      ServerSocket server = new ServerSocket(portNumber);
      server.setSoTimeout(1000);

      while (true) {
         loopThroughHash();
         
         if (DEBUG_MODE) System.out.println("Looping...");
         Thread.sleep(100);
         if (DEBUG_MODE) System.out.println("Please Proceed.");
 
         try {
            clientSocket = server.accept();
            System.out.println("New Client: " + clientSocket);
            Thread.sleep(1000);
            Scanner scan = new Scanner(clientSocket.getInputStream());
            String init = scan.next();
            if (DEBUG_MODE) System.out.println(init);
            if (init.equals("join")) {
               int id = Integer.parseInt(scan.next());
               
               System.out.println(messageQueue.get(1));
               System.out.println(id);
               messageQueue.get(id).add(new Vector<String>());
               messageDispatch.get(id).add(new Vector<String>());
               new MessageListener(clientSocket, messageQueue.get(id).size() - 1, id).start();
            }

            if (init.equals("gc")) {
               int id = Integer.parseInt(scan.next());
               System.out.println(id);
               messageQueue.put(id, new Vector<Vector<String>>());
               messageDispatch.put(id, new Vector<Vector<String>>());
               messageQueue.get(id).add(new Vector<String>());
               messageDispatch.get(id).add(new Vector<String>());
               
               System.out.println(messageQueue.get(1));
               new MessageListener(clientSocket, messageQueue.get(id).size() - 1, id).start();
            }

         } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
               + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
         }
      }
   }

   public void loopThroughHash() {
      Iterator queueIter    = messageQueue.entrySet().iterator();
      Iterator dispatchIter = messageDispatch.entrySet().iterator();

      while (queueIter.hasNext()) {
         Map.Entry queuePair    = (Map.Entry)queueIter.next();
         Map.Entry dispatchPair = (Map.Entry)dispatchIter.next();
         //System.out.println(pair.getKey() + " = " + pair.getValue());

         for (int i = 0; i < ((Vector<Vector<String>>)(queuePair.getValue())).size(); i++) {
            while (((Vector<Vector<String>>)(queuePair.getValue())).get(i).size() != 0) {
	       String key = (((Vector<Vector<String>>)(queuePair.getValue())).get(i).get(0));
               System.out.println("Received Message: " + key);
               
	       for (int j = 0; j < ((Vector<Vector<String>>)(queuePair.getValue())).size(); j++) {
                  System.out.println("Dispatching message '" + key + "' to ID " + j);
	          if (j != i) ((Vector<Vector<String>>)(dispatchPair.getValue())).get(j).add(key);
               }
               messageQueue.get(i).removeElementAt(0);
            }
         }
      }
   }
}

// Thanks Horstmann!
class MessageListener extends Thread {
   private Socket s;
   private Scanner in;
   private PrintWriter out;
   private int gID;
   private int gcID;

   public MessageListener(Socket client, int groupID, int groupChat) {
      s    = client;
      gID  = groupID;
      gcID = groupChat;

      if (TestServer.DEBUG_MODE) System.out.println("New MessageListener created for: " + s + " with ID " + gID + ".");
   }
   
   public void run() {
      in = null;
      out = null;

      try {
         in  = new Scanner(s.getInputStream());
         out = new PrintWriter(s.getOutputStream());
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      while (true) {
         //if (TestServer.DEBUG_MODE) System.out.println(gID + ": Looping Thread...");
         
         try {
            queueMessage();
            dispatchMessage();
         } catch (Exception e) {
            //e.printStackTrace();
         }
      }
   }
   
   public void queueMessage() throws Exception {
      if (s.getInputStream().available() != 0) {
         String message = in.next();
         if (TestServer.DEBUG_MODE) System.out.println(gID + ": Queueing Message.");
         TestServer.messageQueue.get(gcID).get(gID).add(message);
         Thread.sleep(1000);
      }
   }
   
   public void dispatchMessage() {
      //if (TestServer.DEBUG_MODE) System.out.println(gID + ": SIZE: " + TestServer.messageDispatch.get(gID).size());
      for (int i = 0; i < TestServer.messageDispatch.get(gcID).size(); i++) {
         if (TestServer.DEBUG_MODE) System.out.println(gID + "@" + gcID + ": Sending message: " + TestServer.messageDispatch.get(gcID).get(gID).get(0));
         out.println(TestServer.messageDispatch.get(gcID).get(gID).get(0));
         out.flush();
         TestServer.messageDispatch.get(gcID).get(gID).removeElementAt(0);
      }
   }
}
