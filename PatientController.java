package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import java.io.InputStream;
import java.io.OutputStream;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import application.PatientInfo;


public class PatientController implements Initializable {
	
//	public String IDs,PWs,Names, Sexs, Ages, Phones;
    
	public Alert msgbox = new Alert(AlertType.CONFIRMATION);
	Socket socket;
	String IPs="127.0.0.1", Ports="1010";
	 int Port1=1010;
	 String Msg,temp;
	 String Chats, rChats;
	 String MasterID="ddd";
	 String receivedDoctorID, receivedPatientID;
	 String riD,rPW,rName,rSex,rAge,rPhone,rPMHx,rDrugHx;
	 String DoctorID,DoctorName,MyName,MyID, dName = "의사";
	 int loginOK=0;
	 
	 Path receivedFilePath;
	
	 public GraphicsContext gcb, gcf; // canvas에 색 출력  gcf-canvas gcb-canvasef 
	 public boolean freedesign = true; //true false로 키고 끄기
	 double startX=0, startY=0, lastX=0,lastY=0,oldX=0,oldY=0,holdX=0,holdY=0;
	// double hg;
	 int strokeNow=2; 
//	 int ChatStart=0;
	 double rX, rY;
	 int colorNow;
	 Paint colors=Color.rgb(0,0,0);
	 String colorS=colors.toString();
	 double sliders=2;
	 
	 
	// public Image image = new Image("C:\\Users\\kingd\\OneDrive\\바탕 화면\\다운로드.jpg");
	 //String receivedFilePath1="C:";
	 //String[] pID; String[] pName;
	 @FXML
	 
	 	public TextField ID,Name, Sex, Age, Phone,Chat,Prescription1,Prescription2,Prescription3,Disease1,Disease2;
		public Button Login,NewID, Logout,Submit,TreatmentSave,Quit, ChatSend,InfoSave, clearsCanvas;	 
		public TextArea TalkBoard, Sx, PHx, FHx, Tx;
		public PasswordField PW;
		public Label LTalkBoard,LName, LSex, LAge, LPhone,LSx,LPHx,LFHx, LDx, LTx, LPrescription1,LPrescription2,LPrescription3;
		public Label Label1, Label2, Label3, Label4, Label5;
		
		public ColorPicker colorpick;
		public Canvas canvas, canvasef;
		public Button  Clear;
		
		public Button fileChooserButton;
		public ImageView imgView = new ImageView();
		//public ImageView imgView2 = new ImageView();
		//public Stage stage;
		//public Scene scene;
		//public Parent pres;
		//public Image image = new Image();
		
		
        
	
	 public void startClient(String IP, int port) {
	        Thread thread = new Thread() {
	            public void run() {
	                try {
	                    socket = new Socket(IP, port);
	                  
	                    receive();
	                    //send("");
	                    TalkBoard.appendText("[서버 접속 성공]\n");
	                } catch (Exception e) {
	                    // TODO: handle exception
	                    if (!socket.isClosed()) {    
	                        stopClient();
	                        TalkBoard.appendText("[서버 접속 실패]\n");
	                        Platform.exit();// 프로그램 종료
	                    }
	                }
	            }
	        };
	        thread.start();
	        
	    }
	 
	    // 클라이언트 종료 메소드
	    public void stopClient() {
	        try {
	            if (socket != null && !socket.isClosed()) {
	                socket.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    // 서버로부터 메세지를 전달받는 메소드
	    public void receive() {
	        while (true) {
	            try {
	                InputStream in = socket.getInputStream();
	                byte[] buffer = new byte[512];
	                int length = in.read(buffer);
	                if (length == -1)
	                    throw new IOException();
	                String message = new String(buffer, 0, length, "UTF-8");
	                
	                
	                
	                if ((message.contains(":"))) {
	                	Msg=message;
	                	receiveMsg();
	                }
	                
	                
	                	
	                	
	               
	          
	            } catch (Exception e) {
	                // TODO: handle exception
	                stopClient();
	                break;
	            }
	        }}
	    
	    public void receiveMsg() {
			 
	    	// System.out.println(Msg);
	    	 //String[] pars1 = Msg.split("~");
	    	 //System.out.println(pars1[0]);
	    	// System.out.println(pars1[1]);
	    	 
		    	
	    	
				
				System.out.println("Patient Receive: "+Msg);
				
				String[] pars = Msg.split(":");
				//String[] pars1= Msg.split("~");
				if (pars[0].equals("Imagefile")){
					if(pars[1].equals("Brain")) {
						Image image = new Image("C:\\Users\\kingd\\OneDrive\\바탕 화면\\MRI_Brain.jpg");
			            imgView.setImage(image);
			            
					}
					
					if(pars[1].equals("Neck")){
						Image image = new Image("C:\\Users\\kingd\\OneDrive\\바탕 화면\\MRI_Neck.jpg");
						imgView.setImage(image);
					}
				}
				if  (pars[0].equals("DoctorInfo")) {
					
					receivedDoctorID = pars[1].split(">")[0];
				
					System.out.println("DoctorInfo:"+receivedDoctorID+">"+MyID);
							//StartConnect.setDisable(false);
	
						}
				
				
				
				if(pars[0].equals("patientInfoRequest")) {
					pars[1].split(">");
					receivedDoctorID = pars[1].split(">")[0];
					receivedPatientID =pars[1].split(">")[1];
					DoctorName =pars[1].split(">")[2];
					DoctorID=receivedDoctorID;
						if(receivedPatientID.equals(MyID)){
						//}
								send("patientInfo:" +MyID+">"+DoctorID+">" +Sx.getText());
							
								Chat.setDisable(false);
								ChatSend.setDisable(false);
								TalkBoard.setDisable(false);
						
						}
				}
				
				if(pars[0].equals("startEx")) {
					pars[1].split(">");
					receivedDoctorID = pars[1].split(">")[0];
					receivedPatientID =pars[1].split(">")[1];
					DoctorID=receivedDoctorID;
					if(receivedPatientID.equals(MyID)){
						
								Chat.setDisable(false);
								ChatSend.setDisable(false);
								TalkBoard.setDisable(false);
								
						
						}
				}
				
				else if (pars[0].equals("chat")) {
					pars[1].split(">");
					receivedDoctorID = pars[1].split(">")[0];
					receivedPatientID =pars[1].split(">")[1];
					
					if((receivedPatientID.equals(MyID)) && (receivedDoctorID.equals(DoctorID))){
							rChats=pars[1].split(">")[2];
							TalkBoard.appendText("[" + receivedDoctorID + "] "+rChats);				
						}
				}
				
				else if (pars[0].equals("complete")) {
					pars[1].split(">");
					receivedDoctorID = pars[1].split(">")[0];
					receivedPatientID =pars[1].split(">")[1];
					if((receivedPatientID.equals(MyID)) && (receivedDoctorID.equals(DoctorID))){
							Tx.setText(pars[1].split(">")[2]);
							Disease1.setText(pars[1].split(">")[3]);
							Disease2.setText(pars[1].split(">")[4]);
							Prescription1.setText(pars[1].split(">")[5]);
							Prescription2.setText(pars[1].split(">")[6]);
							Prescription3.setText(pars[1].split(">")[7]);
							
							saveTeatment();
							
						
						}		
				}	
				else if (pars[0].equals("Pencil")) {
								//		System.out.println(pars[0]);
						pars[1].split(">");
						receivedDoctorID = pars[1].split(">")[0];
						receivedPatientID =pars[1].split(">")[1];
						if((receivedPatientID.equals(MyID)) && (receivedDoctorID.equals(DoctorID))){
									
							rX = Double.parseDouble(pars[1].split(">")[2]);
							rY = Double.parseDouble(pars[1].split(">")[3]);
							sliders=Double.parseDouble(pars[1].split(">")[4]);
							colors= Color.web(pars[1].split(">")[5]);
							pencil();
						}
						if((receivedPatientID.equals(DoctorID)) && (receivedDoctorID.equals(MyID))){
							
							rX = Double.parseDouble(pars[1].split(">")[2]);
							rY = Double.parseDouble(pars[1].split(">")[3]);
							sliders=Double.parseDouble(pars[1].split(">")[4]);
							colors= Color.web(pars[1].split(">")[5]);
							pencil();
						}
				}
				else if (pars[0].equals("Clear")) {
					pars[1].split(">");
					receivedDoctorID = pars[1].split(">")[0];
					receivedPatientID =pars[1].split(">")[1];
					if((receivedPatientID.equals(MyID)) && (receivedDoctorID.equals(DoctorID))){
				
							clearsCanvas();
					}
				}
					
				else if (pars[0].equals("patientLoginOK")) {
					pars[1].split(">");
					rName= pars[1].split(">")[3];
					rSex= pars[1].split(">")[4];
					rAge= pars[1].split(">")[5];
					rPhone= pars[1].split(">")[6];
					rPMHx= pars[1].split(">")[7];
					rDrugHx= pars[1].split(">")[8];
					
					loginSuccess();
				}
				
				else if (pars[0].equals("patientLoginFail")) {
					loginFail();
					
				}
				
		
				
	 }
	    // 서버로 메세지를 보내는 메소드
	    public void send(String message) {
	        Thread thread1 = new Thread() {
	            public void run() {
	                try {
	                	
	                	
	                    OutputStream out = socket.getOutputStream();
	                    byte[] buffer = message.getBytes("UTF-8");
	                    
	                    out.write(buffer);
	                    out.flush();
	                } catch (Exception e) {
	                    // TODO: handle exception
	                    stopClient();
	                }
	            }
	        };
	        thread1.start();
	        }	 
		
			 
			 @Override
				public void initialize(URL url, ResourceBundle rb) {
					// TODO Auto-generated method stub
				 
				 ID.requestFocus();
				 
				 startClient(IPs, Port1); 
				 
				 gcf = canvas.getGraphicsContext2D();
				 gcb = canvasef.getGraphicsContext2D();
			//	 gcb..setStroke(colorpick.setValue(Color.rgb(0,0,0)));	
				 freedesign = true;
				 
				 clearAll();
				}	
		 
		 @FXML 
		    private void LoginOnClick(ActionEvent e)
		    {
			 if((!ID.getText().equals("")) && (!PW.getText().equals(""))) {
			 
				 send("patientLoginInfo:" +ID.getText()+">"+MasterID+">"+PW.getText());
				
			
			 }
		    }
		 
		 public void loginSuccess() {
			 //     msgbox.setAlertType(AlertType.CONFIRMATION);
             //       msgbox.setContentText("로그인 되었습니다.!!");
		                    loginOK=1;
		                    System.out.println("환자 : 로그인 성공");
		          //          riD,rPW,rName,rSex,rAge,rPhone,rPMHx,rDrugHx
		                    Name.setText(rName);
		                    Sex.setText(rSex);
		                    Age.setText(rAge);
		                    Phone.setText(rPhone);
		                    PHx.setText(rPMHx);
		                    FHx.setText(rDrugHx);
		              //      SHx.setText(login_user.shx);
		                    
		                    MyName=Name.getText();
		                    MyID=ID.getText();
		                    ID.setDisable(true);
		                	PW.setDisable(true);
		                    NewID.setDisable(true);
		                    Login.setDisable(true);		    	           
		    				Logout.setDisable(false);
		    				
		    				Name.setDisable(false);
		    				Age.setDisable(false);
		    				Sex.setDisable(false);
		    				Phone.setDisable(false);
		    				
		    				FHx.setDisable(false);
		    				PHx.setDisable(false);
		    				InfoSave.setDisable(false);
		    				
		    				Sx.setDisable(false);
		    				Submit.setDisable(false);
		    				
		    			//	 msgbox.showAndWait();
		                    
		                }
		 
		 public void loginFail() {
			 loginOK=0;
			 
		         //           msgbox.setAlertType(AlertType.WARNING);
		         //           msgbox.setContentText("암호가 틀려 로그인하지 못했습니다.!!");
		         //           msgbox.showAndWait();
		                    System.out.println("환자 : 로그인 실패");
		    }
		
		 
		 
		 
		@FXML 
	    private void LogoutOnClick(ActionEvent e)
	    {
			
			 msgbox.setAlertType(AlertType.CONFIRMATION);
	         msgbox.setContentText("<알림> "+ ID.getText()  +" 님이  퇴장하였습니다!!");
	         msgbox.showAndWait();
			
	         ID.clear();
			 PW.clear();
             clearAll();
             
             loginOK=0;
           
            
	    }
		
		@FXML
	    private void NewIDOnClick(ActionEvent e) {

		msgbox.setAlertType(AlertType.CONFIRMATION);
        msgbox.setContentText("<알림> "+ ID.getText() + "님을 새로 가입합니다.!!");
        msgbox.showAndWait();
        
           
            	
            	ID.setDisable(true);
            	PW.setDisable(true);
            	
            	Login.setDisable(true);
            	Logout.setDisable(false);
            	NewID.setDisable(true);
                Name.setDisable(false);
    			Age.setDisable(false);
    			Sex.setDisable(false);
    			Phone.setDisable(false);
    		    Sx.setDisable(false);
     			PHx.setDisable(false);
     			FHx.setDisable(false);
     		
    			
    			InfoSave.setDisable(false);
               
    }
	
		
		@FXML 
	    private void SubmitOnClick(ActionEvent e)
	    {
		//	send("patientSubmit:" +ID.getText() + "," +Name.getText()+ "," +  Age.getText() + "," + Sex.getText()+ "," + Phone.getText()+ "," +PHx.getText()+ "," +FHx.getText()+  "," +Sx.getText());
			send("patientSubmit:" + MyID+ ">" +"AllDoctor" + ">" +MyName );
			System.out.println("patientSubmit:" + MyID+">"+DoctorID + ">" +MyName);
			send("chat:"+ MyID+">"+"AllDoctor" +">"+MyName+" 님이 진료를 신청하였습니다. \n");
			//System.out.println("patientSubmit:" + MyID+">"+masterID + ">" +MyName);
			TalkBoard.appendText(MyName+"["+MyID +"] 님이 진료를 신청하였습니다. \n");
			//send("DoctorInfoRequest:");
			
		//	TreatmentSave.setDisable(false);
	    }
		
		
		
		@FXML 
	    private void QuitOnClick(ActionEvent e)
	    {
			System.exit(0);
	    }
		
		@FXML 
	    private void InfoSaveOnClick(ActionEvent e)
	    {
          
			if (loginOK== 0) {
				send("newPatient:"+ID.getText()+">"+MasterID+">"+PW.getText()+">"+Name.getText()+">"+Sex.getText()+">"+Age.getText()+">"+Phone.getText()+">"+PHx.getText()+">"+FHx.getText());
				
				//		System.out.println("newPatient:"+ID.getText()+">"+Doctor+">"+PW.getText()+">"+Name.getText()+">"+Sex.getText()+">"+Age.getText()+">"+Phone.getText()+">"+PHx.getText()+">"+FHx.getText());
						} else {
							send("updatePatientInfo:"+ID.getText()+">"+MasterID+">"+PW.getText()+">"+Name.getText()+">"+Sex.getText()+">"+Age.getText()+">"+Phone.getText()+">"+PHx.getText()+">"+FHx.getText());
							
					//		System.out.println("updatePatientInfo:"+ID.getText()+">"+Doctor+">"+PW.getText()+">"+Name.getText()+">"+Sex.getText()+">"+Age.getText()+">"+Phone.getText()+">"+PHx.getText()+">"+FHx.getText());
			}
			
         
	    }
    	   
       
           
		@FXML 
	    private void ChatSendOnClick(ActionEvent e)
	    {
			   Chats = Chat.getText();
				
			   send("chat:" + MyID+ ">" +DoctorID+ ">" +Chats+"\n");
			   //TalkBoard.appendText(Chats+"\n");
			   TalkBoard.appendText("["+MyID+"] "+Chats+"\n");
			
			   Chat.setText("");
	           Chat.requestFocus();
			
	    }
			
		public void saveTeatment() {
			TreatmentSave.setDisable(false);
			Chat.setDisable(true);
			ChatSend.setDisable(true);
			TalkBoard.setDisable(true);
			TreatmentSave.setDisable(false);
			clearsCanvas();
		}
		
		@FXML 
		
	    public void clearAll() {
			Name.setDisable(true);
			Age.setDisable(true);
			Sex.setDisable(true);
			Phone.setDisable(true);
			InfoSave.setDisable(false);
			
			
			Submit.setDisable(true);
			TreatmentSave.setDisable(true);
			//Print.setDisable(true);
			Chat.setDisable(true);
			ChatSend.setDisable(true);
			TalkBoard.setDisable(true);
			Sx.setDisable(true);
			PHx.setDisable(true);
			FHx.setDisable(true);
		
			Tx.setDisable(true);
			Prescription1.setDisable(true);
			Prescription2.setDisable(true);
			Prescription3.setDisable(true);
			Disease1.setDisable(true);
			Disease2.setDisable(true);
			
			clearsCanvas();
			
			ChatSend.setDisable(true);
			Logout.setDisable(true);
			
			
			Name.clear();
			Age.clear();
			Sex.clear();
			Phone.clear();
			Chat.clear();
			TalkBoard.clear();
			Sx.clear();
			PHx.clear();
			FHx.clear();
	//	SHx.clear();
		//	Dx.clear();
			Tx.clear();
			Prescription1.clear();
			Prescription2.clear();
			Prescription3.clear();
			Disease1.clear();
			Disease2.clear();
			Tx.setText("");
			Prescription1.setText("");
			Prescription2.setText("");
			Prescription3.setText("");
			Disease1.setText("");
			Disease2.setText("");
				
			ID.setDisable(false);
			PW.setDisable(false);
			Login.setDisable(false);
			NewID.setDisable(false);
				
			clearsCanvas();
		}
		
		
		@FXML 
	    private void TreatmentSaveOnClick(ActionEvent e)
	    {
			msgbox.setAlertType(AlertType.CONFIRMATION);
			msgbox.setContentText(MyName +"님의 진료기록을 저장하였습니다.!!");
			msgbox.showAndWait();
			
			
	    }
		
		 @FXML 
		 private void InputID(ActionEvent e)
		 {
			 ID.requestFocus();
		 } 
		 
		 @FXML
			public void onMousePressedListener(MouseEvent e){ //직선 및 도형 그릴 때 시작 끝 저장 
				this.startX = e.getX();
				this.startY = e.getY();
				
				oldX=0;
				oldY=0;
				
		//		if (Ids.equals("teacher")) {
		//		System.out.println("Pencil:" + startX + "," + startY+ "," + sliders+ "," +colorS);
				send("Pencil:" +MyID+">"+DoctorID+ ">"+ oldX + ">" + oldY+ ">" + sliders+ ">" +colorS);
				send("Pencil:"+MyID+">"+DoctorID+ ">" + startX + ">" + startY+ ">" + sliders+ ">" +colorS);
			
		//		}
			}
		 @FXML
		    public void onMouseDraggedListener(MouseEvent e){ // 마우스 움직임 저장
		        this.lastX = e.getX();
		        this.lastY = e.getY();
		        	// 드래그 할 때 함수들 호출 및 알고리즘 
		       
		        if(freedesign)
		        	
		            freeDrawing();
		       
		    }
		  @FXML 
		    public void onMouseReleaseListener(MouseEvent e){ 
			  rX=0;
			  rY=0;
		      
		    }
		  
		  
		  @FXML 
		    public void onMouseEnteredListener(MouseEvent e){
			  this.holdX = e.getX();
			  this.holdY = e.getY();
		  }
		  
		  @FXML
		    public void onMouseExitedListener(MouseEvent event)
		    { 
		    }
		  

		    public void freeDrawing() // 마우스 이용 그리기  메소드 
		    {
		
		        gcb.setStroke(colorpick.getValue());
		        colorS=colors.toString();
		     //   System.out.println("Pencil:" + startX + "," + startY+ "," + sliders+ "," +colorS);
		//        if (Ids.equals("teacher")) {
		      	send("Pencil:"+MyID+">"+DoctorID+ ">" + lastX + ">" + lastY+ ">" + sliders+ ">" +colorS);
		//        }       
		    }
		    
		    public void pencil() // 마우스 이용 그리기  메소드 
		    {
		    	if (oldX==0) oldX=rX;
		    	if (oldY==0) oldY=rY;
		    	
		    	if (rX!=0) {
		    	
		    	
		    	gcb.setStroke(colors);
		    	gcb.setLineWidth(sliders);
		        gcb.strokeLine(oldX, oldY, rX, rY);
		
		    	}
		        
		        oldX = rX;
		        oldY = rY;
		 
		    }
		    
		    @FXML
		    private void setColorChange(ActionEvent e)
		    {
		    	gcb.setStroke(colorpick.getValue());
		    	colors=colorpick.getValue();
		    	colorS=colors.toString();
		   	    	
		    }
		    
		    
		    public void clearsCanvas()
		    {
		    	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		        gcb.clearRect(0, 0, canvasef.getWidth(), canvasef.getHeight());
		    
		      //  send("Clear:"+MyID+">"+DoctorID);
		    }
		    
     	 
		    @FXML 
		    public void clearCanvas(ActionEvent e)
		    {
		        
		    	send("Clear:"+MyID+">"+DoctorID);
		    	
		    }


		    @FXML
		    public void setFreeDesign(ActionEvent e)
		    {
		        
		        freedesign = true;
		    }
		  
		    
		    @FXML 
		    public void PrintScriptOnClick(ActionEvent e)
		    {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Prescription.fxml"));
		              //Parent pres = FXMLLoader.load(getClass().getResource("Prescription.fxml"));
						Parent pres = (Parent)fxmlLoader.load();
						Stage stage = new Stage();
						stage.setScene(new Scene(pres));
						stage.setTitle("처방전");
						stage.show();
		          } catch (Exception p) {
		        	  p.printStackTrace();
		          }
		    }
		    
		    
		    @FXML
		    public void fileChooserOnClick(ActionEvent e){
		    	
		    	fileChoose();
		    
		    }
		    
		    public void fileChoose() {
		    	FileChooser fileChooser = new FileChooser();
		        fileChooser.setTitle("Open a file");
		        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
		        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
		        
		        Stage stage = (Stage) fileChooserButton.getScene().getWindow();
		        File selectedFile = fileChooser.showOpenDialog(stage);
		       
		        if(selectedFile != null){

		           
		        		Image image = new Image(selectedFile.getAbsolutePath());
	                
		        		if ((selectedFile.getAbsolutePath()).equals("C:\\Users\\kingd\\OneDrive\\바탕 화면\\MRI_Brain.jpg")) {
		        			send("Imagefile:Brain");
		        			imgView.setImage(image);
		        		}
	                
		        		if ((selectedFile.getAbsolutePath()).equals("C:\\Users\\kingd\\OneDrive\\바탕 화면\\MRI_Neck.jpg")) {
		        			send("Imagefile:Neck");
		        			imgView.setImage(image);
		        		}
		               
		        }else{
		            System.out.println("No file has been selected");
		        }
		    }
		    
		    
		   
		   
		    
			
			
		 
		
}		 
 
	    
