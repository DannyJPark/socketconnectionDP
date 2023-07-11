package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;

import java.time.LocalDate;

public class DoctorController implements Initializable {
	
//	public String IDs,PWs,Names, Sexs, Ages, Phones;
//  public String IDs,PWs,Names, Sexs, Ages, Phones;
  private Connection conn; //DB 커넥션 연결 객체
   private static final String USERNAME = "root";//DBMS접속 시 아이디
   private static final String PASSWORD = "1111";//DBMS접속 시 비밀번호
   private static final String URL = "jdbc:mysql://localhost:3306/patient";//DBMS접속할 db명
   
   Connection con; // 멤버변수
   String query;
   Statement stmt;
   ResultSet rs;
  
	public Alert msgbox = new Alert(AlertType.CONFIRMATION);
	Socket socket;
	String IPs="127.0.0.1", Ports="1010";
	 int Port1=1010;
	 String Msg,temp;
	 String selectedName="";
	 String rPatientID1 ,  rPatientID2;
	 String rPatientName1, rPatientName2; 
	 String MasterID = "ddd",MasterPW="111";
	
	 String MyID,MyName;
	 String rDoctorID, rDoctorID1;
	 String PatientName;
	 String selectedPatientName,selectedPatientID;
	 String pID,sPatient;
	 String Chats,rChats;
	// String master="ddd";
	 String recievedPatientName;
	 String completeAll;	
	 String pre1="",pre2="",pre3="",dis1="",dis2="";
	 LocalDate now = LocalDate.now();
	 String nowString;
	 String pastRecord;
	 String readTx,saveTx;
	 
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
	 
	 int indexP,indexD,indexR,indexDrug,indexDisease,tempPNumber;
	 String newPID, newPName, newDID, newDName, newSx, newTx, newDis1, newDis2, newPre1, newPre2, newPre3,newNow;
	 String newPatientID, newPatientPW, newPatientName,newPatientAge, newPatientSex, newPatientPhone;
	 String newPatientPMHx, newPatientDrugHx,rPatientPW; 
	 String newDoctorID, newDoctorPW, newDoctorName;
	 
	 String receivedFilePath;
	 //String receivedFilePath1= "C:";
	 
	 
	 String tempPatientID[]= new String[10];
	 String tempPatientName[] = new String [10];
	 String patientID[]= new String[10];
	 String patientPW[] = new String[10];
	 String patientName[] = new String[10];
	 String patientSex[] = new String[10];
	 String patientAge[]= new String[10];
     String patientPhone[] = new String[10];
	 String patientPMHx[] = new String[10];
	 String patientDrugHx[] = new String[10];	
	 String doctorID[] = new String[10];
	 String doctorPW[] = new String[10];
	 String doctorName[] = new String[10];
	 String recordPatientID[] = new String[100];
	 String recordPatientName[] = new String[100];
	 String recordDoctorID[] = new String[100];
	 String recordDoctorName[] = new String[100];
	 String recordExamDate[] = new String[100];
	 String recordPatientSx[] = new String[100];
	 String recordDoctorOpinion[] = new String[100];
	 String recordDisease1[] = new String[100];
	 String recordDisease2[] = new String[100];
	 String recordDrug1[] = new String[100];
	 String recordDrug2[] = new String[100];
	 String recordDrug3[] = new String[100];
 	 String DiseaseList1[]= new String[12];
	 String DiseaseList2[]= new String[12];
	 String DrugList1[]= new String[12];
	 String DrugList2[]= new String[12];
	 String DrugList3[]= new String[12];

	 
	 
	 
	 @FXML
	 
	 	public TextField ID,dName, Chat;//Name, Sex, Age, Phone,Chat;
		public Button Login, NewID, Logout,SelectPatient, StartConnect, TreatmentSave,Quit,Print, ChatSend,refreshList;	 
		public TextArea TalkBoard, Sx, PHx, FHx, Phone, Sex, Name, Age,pastDxHx,Treatment;
		public ChoiceBox<String> Prescription1,Prescription2,Prescription3,Diagnosis1,Diagnosis2;
		public PasswordField PW;
		public Label LTalkBoard,LName, LSex, LAge, LPhone,LSx,LPHx,LFHx,LSHx, LDx, LTx, LPrescription1,LPrescription2,LPrescription3;
		public Label Label1, Label2, Label3, Label4, Label5;
	    public ListView<String> PatientList;
	    public Pane pane;
	    public ColorPicker colorpick;
		public Canvas canvas, canvasef;
		public Button  Pencil,Clear;
		public String[] PID;
		public String[] PName;
		public int pNumber=0;
			
			public Button fileChooserButton;
			public ImageView imgView = new ImageView();
			public Stage stage;
			
		
	
			
			
			    @Override
				public void initialize(URL url, ResourceBundle rb) {
					// TODO Auto-generated method stub
			    	

			    try {
			                  //   System.out.println("aaa");
			              Class.forName("com.mysql.cj.jdbc.Driver");
			              conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			              System.out.println("mysql db connection success");
			                     
			             //        conn.close();
			            //      System.out.println("mysql connection close success");
			   } catch (Exception e) {
			            System.out.println("mysql driver error");
			             try {
			                   conn.close();
			             } catch (SQLException e1) { System.out.println("DBconnection fail");   }
			   }   
			                
			    startClient(IPs, Port1); 
		                    
			    nowString = now.toString();   
			    
			    readPatientInfo();
			    readDoctorInfo();
			    readDisease();
			    readDrug();
			    readRecord();
			    
			               

			//    PatientList1 = {"박상훈","박형준"};
			                
			                ID.requestFocus();
				 
				 gcf = canvas.getGraphicsContext2D();
				 gcb = canvasef.getGraphicsContext2D();
			//	 gcb..setStroke(colorpick.setValue(Color.rgb(0,0,0)));	
				 freedesign = true;
				 clearAll();
			     ObservableList<String> PatientArrayList =  FXCollections.observableArrayList(); 
				 PatientList.setItems(FXCollections.observableArrayList());
			//	 PatientList.getItems().add("");
				 Prescription1.setItems(FXCollections.observableArrayList(DrugList1));
				 Prescription2.setItems(FXCollections.observableArrayList(DrugList1));
				 Prescription3.setItems(FXCollections.observableArrayList(DrugList1));
				 Diagnosis1.setItems(FXCollections.observableArrayList(DiseaseList1));
				 Diagnosis2.setItems(FXCollections.observableArrayList(DiseaseList1));
			
				 PatientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				 /*				 PatientList.setOnMouseClicked((MouseEvent)->{
		            Object obj = PatientList.getSelectionModel().getSelectedItem();
		            selectedName = obj.toString();
		        });
		 
		        SelectPatient.setOnAction((AtionEvent)->{
		            String obj = PatientList.getSelectionModel().getSelectedItem();
		            selectedPatientName = obj.toString();
		        });
//			 PatientList.getSelectionModel().selectedItemProperty().addListener(
//			            (ObservableValue observable, Object oldVal, Object newVal) -> {
			          //     selectedName.setText(oldVal + " -> " + newVal);
//			               selectedName=(oldVal + " -> " + newVal).toString();
//			            }
//			        );
	*/		 
				 Prescription1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {         
			            public void changed(ObservableValue ov, Number value, Number new_value) {

			                // text for the label to the selected item
			            	pre1=DrugList1[new_value.intValue()] ;
			            }
			        });
			    
			    
			    Prescription2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {         
		            public void changed(ObservableValue ov, Number value, Number new_value) {

		                // text for the label to the selected item
		            	pre2=DrugList1[new_value.intValue()] ;
		            }
			    });
			   
			    
				Prescription3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {         
					public void changed(ObservableValue ov, Number value, Number new_value) {

        // text for the label to the selected item
						pre3=DrugList1[new_value.intValue()] ;
					}
				 });
				
				Diagnosis1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {         
					public void changed(ObservableValue ov, Number value, Number new_value) {

        // text for the label to the selected item
						dis1=DiseaseList1[new_value.intValue()] ;
					}
				 });
				
				Diagnosis2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {         
					public void changed(ObservableValue ov, Number value, Number new_value) {

        // text for the label to the selected item
						dis2=DiseaseList1[new_value.intValue()] ;
					}
				 });
		/*		
				for (int i=0;i<10;i++) {
					patientID[i]="";
					doctorID[i]="";
				}
				
				for (int i=0;i<12;i++) {
	
					DiseaseList1[i]="";
					DiseaseList2[i]="";
					DrugList1[i]= "";
					DrugList2[i]= "";
					DrugList3[i]= "";
				}
				for (int i=0;i<100;i++) {
					recordPatientID[i]="";
				}
		*/		
				
		}    
				
				 
				 public void startClient(String IP, int port) {
				        Thread thread = new Thread() {
				            public void run() {
				                try {
				                    socket = new Socket(IP, port);
				                    //send("Connect: "+ Ids +" 님이  입장 하였습니다!!\n");
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
				                
				                
				                
				                if (message.contains(":")) {
				                	Msg=message;
				                	receiveMsg();
				                	
				                	
								
				                }
				          
				            } catch (Exception e) {
				                // TODO: handle exception
				                stopClient();
				                break;
				            }
				        }
				    }
				 
				    public void receiveMsg() {
				//    	  System.out.println("Doctor Receive: "+Msg);
						 //String parts[];
							String[] pars = Msg.split(":");
							
							if(pars[0].equals("DoctorInfoRequest")) {
								
											send("DoctorInfo:" + MyID);
											
									}
							
							if (pars[0].equals("patientSubmit")) {
								pars[1].split(">");
								rPatientID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								rPatientName1=pars[1].split(">")[2];
						//		 Sx.setText(pars[1].split(">")[3]);
			
								System.out.println("Doctor Receive: "+rPatientID1+" "+rPatientName1);	
								
								addPatientList();
							//	PatientList.getItems().add(rPatientName1);
							//	PatientList.getItems().add("");
							}	
							
							else if (pars[0].equals("newPatient")) {
								pars[1].split(">");
								if (MasterID.equals(MyID)) {
									newPatientID = pars[1].split(">")[0];
									rDoctorID = pars[1].split(">")[1];
							//	rPatientName1=pars[1].split(">")[2];
									newPatientPW=pars[1].split(">")[2];
									newPatientName = pars[1].split(">")[3];
									newPatientSex= pars[1].split(">")[4];
									newPatientAge= pars[1].split(">")[5];
									newPatientPhone=pars[1].split(">")[6];
									newPatientPMHx=pars[1].split(">")[7];
									newPatientDrugHx= pars[1].split(">")[8];
							 
									newPatientInsert();
								}
							} 
							else if (pars[0].equals("newDoctor")) {
								pars[1].split(">");
								if (MasterID.equals(MyID)) {
									newDoctorID = pars[1].split(">")[0];
									rDoctorID = pars[1].split(">")[1];
									newDoctorPW=pars[1].split(">")[2];
									newDoctorName = pars[1].split(">")[3];
									
							 
									newDoctorInsert();
								}
							} 
							
							else if (pars[0].equals("newRecord")) {
								pars[1].split(">");
								if (MasterID.equals(MyID)) {
									newPID = pars[1].split(">")[2];
									newPName = pars[1].split(">")[3];
									newDID=pars[1].split(">")[4];
									newDName = pars[1].split(">")[5];
									newSx = pars[1].split(">")[6];
									newTx = pars[1].split(">")[7];
									newDis1=pars[1].split(">")[8];
									newDis2 = pars[1].split(">")[9];
									newPre1 = pars[1].split(">")[10];
									newPre2 = pars[1].split(">")[11];
									newPre3=pars[1].split(">")[12];
									newNow = pars[1].split(">")[13];
									
							 
									newRecordInsert();
								}
							} 
							
							else if (pars[0].equals("updatePatientInfo")) {
								pars[1].split(">");
								
								if (MasterID.equals(MyID)) {
									newPatientID = pars[1].split(">")[0];
									rDoctorID = pars[1].split(">")[1];
							//	rPatientName1=pars[1].split(">")[2];
									newPatientPW=pars[1].split(">")[2];
									newPatientName = pars[1].split(">")[3];
									newPatientSex= pars[1].split(">")[4];
									newPatientAge= pars[1].split(">")[5];
									newPatientPhone=pars[1].split(">")[6];
									newPatientPMHx=pars[1].split(">")[7];
									newPatientDrugHx= pars[1].split(">")[8];
								
								 
									updatePatient();
							
								}
							}
							
							
							else if  (pars[0].equals("patientInfo")) {
								pars[1].split(">");
								rPatientID2= pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
							//	rPatientName2=pars[1].split(">")[2];
								
									if((rPatientID2.equals(selectedPatientID))&&(rDoctorID.equals(MyID))) {
							
										Sx.setText(pars[1].split(">")[2]);
										
										StartConnect.setDisable(false);
				
									//	selectedPatientName=rPatientName2;
									}
							}
						
							else if (pars[0].equals("chat")) {
								
								pars[1].split(">");
								rPatientID2= pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
					//			
								if((rPatientID2.equals(rPatientID1))&&(rDoctorID.equals(MyID))) {
											rChats=pars[1].split(">")[2];
											TalkBoard.appendText("["+rPatientID2+"] "+rChats);
									}
								else if((rPatientID2.equals(rPatientID1))&&(rDoctorID.equals("AllDoctor"))) {
									rChats=pars[1].split(">")[2];
									TalkBoard.appendText(rChats);
									
									
								}
							
							}		else if (pars[0].equals("Pencil")) {
								
										pars[1].split(">");
										rPatientID2= pars[1].split(">")[0];
										rDoctorID = pars[1].split(">")[1];
										
										if((rPatientID2.equals(rPatientID1))&&(rDoctorID.equals(MyID))) {
												rX = Double.parseDouble(pars[1].split(">")[2]);
												rY = Double.parseDouble(pars[1].split(">")[3]);
												sliders=Double.parseDouble(pars[1].split(">")[4]);
												colors= Color.web(pars[1].split(">")[5]);
												pencil();
											}
										if((rPatientID2.equals(MyID))&&(rDoctorID.equals(rPatientID1))) {
											rX = Double.parseDouble(pars[1].split(">")[2]);
											rY = Double.parseDouble(pars[1].split(">")[3]);
											sliders=Double.parseDouble(pars[1].split(">")[4]);
											colors= Color.web(pars[1].split(">")[5]);
											pencil();
										}
							
						  
							} else if (pars[0].equals("Clear")) {
								pars[1].split(">");
								rPatientID2 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if((rPatientID2.equals(rPatientID1))&&(rDoctorID.equals(MyID))) {
						
											clearsCanvas();			
								}
								if((rPatientID2.equals(MyID))&&(rDoctorID.equals(rPatientID1))) {
											clearsCanvas();			
								}
							}   
							
							else if (pars[0].equals("patientLoginInfo")) {
								pars[1].split(">");
								rPatientID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								rPatientPW =pars[1].split(">")[2];
					
								checkPatientLoginInfo();
								
							} 
							else if (pars[0].equals("DoctorRequest")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if (MasterID.equals(MyID)) {
									replyDoctorRequest();
								}
							}
							else if (pars[0].equals("PatientRequest")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if (MasterID.equals(MyID)) {
									replyPatientRequest();
								}
							}
						   
							else if (pars[0].equals("DiseaseRequest")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if (MasterID.equals(MyID)) {
									replyDiseaseRequest();
								}
							}
							else if (pars[0].equals("DrugRequest")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if (MasterID.equals(MyID)) {
									replyDrugRequest();
								}
							}
							
							else if (pars[0].equals("RecordRequest")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if (MasterID.equals(MyID)) {
									replyRecordRequest();
								}
							}
						   
							else if (pars[0].equals("replyDrug")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
						//		if (rDoctorID.equals(MyID)) {
									int i=0;
									if (!(pars[1].split(">")[2]).equals("End")) {
										i= Integer.parseInt(pars[1].split(">")[2])	;
										DrugList1[i] = pars[1].split(">")[3];
										DrugList2[i] = pars[1].split(">")[3];
										DrugList3[i] = pars[1].split(">")[3];
										
									}
									else   {
										
										indexDrug= Integer.parseInt(pars[1].split(">")[3]);
										DrugList1[indexDrug]="";
										DrugList2[indexDrug]="";
										DrugList3[indexDrug]="";
										System.out.println(indexDrug);
										
									}
									
									
						//		}
							}
						   
							else if (pars[0].equals("replyDisease")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
						//		if (rDoctorID.equals(MyID)) {
									int i=0;
									if (!(pars[1].split(">")[2]).equals("End")) {
										i= Integer.parseInt(pars[1].split(">")[2])	;
										DiseaseList1[i] = pars[1].split(">")[3];
										DiseaseList2[i] = pars[1].split(">")[3];
										
									}
									else {
										indexDisease= Integer.parseInt(pars[1].split(">")[3]);
										DiseaseList1[indexDisease]="";
										DiseaseList2[indexDisease]="";
										System.out.println(indexDisease);
									}
									
							//	}
							}
							else if (pars[0].equals("replyDoctor")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
							//	if (rDoctorID.equals(MyID)) {
									if ((pars[1].split(">")[2]).equals("End")) {
										indexD = Integer.parseInt(pars[1].split(">")[3]);
							//	String[] dortorID;
										doctorID[indexD]="";
										System.out.println("indexD ="+indexD);
									}
									else {
										int i;
										i= Integer.parseInt(pars[1].split(">")[2])	;
										doctorID[i] = pars[1].split(">")[3];
										doctorPW[i] = pars[1].split(">")[4];
										doctorName[i] = pars[1].split(">")[5];
										System.out.println(i+doctorID[i]+doctorPW[i]+doctorName[i]);
									
										
									}
						//			doctorLoginCheck();
					//				
							//	}
							}
						   
							else if (pars[0].equals("replyPatient")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
						//		if (rDoctorID.equals(MyID)) {
									if ((pars[1].split(">")[2]).equals("End")) {
										indexP = Integer.parseInt(pars[1].split(">")[3]);
										patientID[indexP]="";
									}
									else {
										int i;
										i= Integer.parseInt(pars[1].split(">")[2])	;
										patientID[i] = pars[1].split(">")[3];
										patientPW[i] = pars[1].split(">")[4];
										patientName[i] = pars[1].split(">")[5];
										patientSex[i] = pars[1].split(">")[6];
										patientAge[i] = pars[1].split(">")[7];
										patientPhone[i] = pars[1].split(">")[8];
										patientPMHx[i] = pars[1].split(">")[9];
										patientDrugHx[i] = pars[1].split(">")[10];
										System.out.println(i+patientID[i]+indexP);
									}
						//	}
							}
						   
							
						   
							else if (pars[0].equals("replyRecord")) {
								pars[1].split(">");
								rDoctorID1 = pars[1].split(">")[0];
								rDoctorID = pars[1].split(">")[1];
								if (rDoctorID.equals(MyID)) {
									if ((pars[1].split(">")[2]).equals("End")) {
										indexR = Integer.parseInt(pars[1].split(">")[3]);
									}
									else {
										int i;
										i= Integer.parseInt(pars[1].split(">")[2])	;
									
										recordPatientID[i] = pars[1].split(">")[3];
										recordPatientName[i] = pars[1].split(">")[4];
										recordDoctorID[i] =pars[1].split(">")[5];
										recordDoctorName[i] = pars[1].split(">")[6];
										recordPatientSx[i] = pars[1].split(">")[7];
										recordDoctorOpinion[i] = pars[1].split(">")[8];
										recordDisease1[i] = pars[1].split(">")[9];
										recordDisease2[i] = pars[1].split(">")[10];
										recordDrug1[i] = pars[1].split(">")[11];
										recordDrug2[i] = pars[1].split(">")[12];
										recordDrug3[i] = pars[1].split(">")[13];
										recordExamDate[i] = pars[1].split(">")[14];
									}
								}
						   
							}
							
							
							else if (pars[0].equals("ImageFile")){
								
								receivedFilePath = pars[1]+":"+pars[2];
								System.out.println(receivedFilePath);
								setFile();
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
				 
				 
				 
					
				 
		
			    
		 @FXML 
		    
		   
		    private void LoginOnClick(ActionEvent e)
			 {
	/*		 MyID=ID.getText();
			 if((ID.getText().equals(MasterID)) && (PW.getText().equals(MasterPW))) {
					readPatientInfo();
				    readDoctorInfo();
				    readDisease();
				    readDrug();
				    readRecord();
				    
				    msgbox.setAlertType(AlertType.CONFIRMATION);
                   msgbox.setContentText("로그인 되었습니다.!!");
                   msgbox.showAndWait();
                   
			 } else {
				 send("DrugRequest:"+ID.getText()+ ">" + MasterID );
				 send("DiseaseRequest:"+ID.getText()+ ">" + MasterID );
				 send("DoctorRequest:"+ID.getText()+ ">" + MasterID );
				 send("PatientRequest:"+ID.getText()+ ">" + MasterID );
			//	 send("RecordRequest:"+ID.getText()+ ">" + MasterID );
			
	*/			 
			// 	readPatientInfo();
			//    readDoctorInfo();
			 //   readDisease();
			 //   readDrug();
			//    readRecord();
			    
				 doctorLoginCheck();	 
			 }		 
			 
		 private void doctorLoginCheck() {
			 
			 int loginOK=0;
			 
			 for(int i=0;i<indexD;i++) {
				System.out.println("ID="+ID.getText()+"PW="+PW.getText()+"docrorID="+doctorID[i]+"doctorPW="+doctorPW[i]);
				 if (ID.getText().equals(doctorID[i])) {
					if( PW.getText().equals(doctorPW[i])){
						loginOK=1;
						msgbox.setAlertType(AlertType.CONFIRMATION);
	                    msgbox.setContentText("로그인 되었습니다.!!");
	                    
	                    MyID=ID.getText();
	                    MyName = doctorName[i];
	                    dName.setText(MyName);;
	                    ID.setDisable(true);
	                	PW.setDisable(true);
	                	dName.setDisable(true);
	                    NewID.setDisable(true);
	                    Login.setDisable(true);
	                    Logout.setDisable(false);
	    	          
	                    PatientList.setDisable(false);
						refreshList.setDisable(false);
						SelectPatient.setDisable(false);
						
						TalkBoard.appendText(MyName+"["+MyID+"] 의사님이 로그인하였습니다.\n");
					} else {   
					msgbox.setAlertType(AlertType.CONFIRMATION);
                    msgbox.setContentText("비밀번호가 틀립니다.!!");
				 } 
				}
			 }
			 
			 if(loginOK==0)        {
	                    msgbox.setAlertType(AlertType.WARNING);
	                    msgbox.setContentText("아이디가 없거나 틀립니다.!!");
	                }
	        
	        
			 msgbox.showAndWait();
	            
	    }
			 
		 @FXML 
		    private void LogoutOnClick(ActionEvent e)
		    {
		      
	            msgbox.setAlertType(AlertType.CONFIRMATION);
	            msgbox.setContentText("<알림> "+ ID.getText()  +" 님이  퇴장하였습니다!!");
	            msgbox.showAndWait();
	            
	            ID.clear();
				PW.clear();
				dName.clear();
				clearAll();
				
				ID.setDisable(false);
	        	PW.setDisable(false);
	        	Login.setDisable(false);
	        	NewID.setDisable(false);
	        	Logout.setDisable(true);
	        	
	            
		    }
		
		 @FXML
		    private void NewIDOnClick(ActionEvent e) {

		 

			//pTalkBoard.appendText("<알림> "+ pIDs +" 님을 새로 가입합니다.!!\n");
	     
			if (dName.getText().equals("")) {
				msgbox.setAlertType(AlertType.CONFIRMATION);
	            msgbox.setContentText("이름을 입력해야 합니다.!!");
	       
			} else {
				int loginOK=0;
			
				 
				 for(int i=0;i<indexD;i++) {
					 if (ID.getText().equals(doctorID[i])) {
						
							loginOK=1;
							msgbox.setAlertType(AlertType.CONFIRMATION);
		                    msgbox.setContentText("같은 아이디가 존재합니다.!!");
						}
				 } 
				 
				 if(loginOK==0)        {
					 
					 send("NewDoctor:"+ID.getText()+ ">" + MasterID + ">" + ID.getText()+ ">" + PW.getText()+ ">" + dName.getText()); 
					 
				//	doctorNew(); 
	                 
					msgbox.setAlertType(AlertType.CONFIRMATION);
				    msgbox.setContentText("<알림> "+ ID.getText() + "님을 새로 가입했습니다.!!");
				        
	            	ID.setDisable(true);
	            	PW.setDisable(true);
	            	
	            	Login.setDisable(true);
	            	//Logout.setDisable(true);
	            	NewID.setDisable(true);
	                Name.setDisable(false);
	    			Age.setDisable(false);
	    			Sex.setDisable(false);
	    			Phone.setDisable(false);
	    			//InfoSave.setDisable(false);
	    			Logout.setDisable(false);
	               
	            }
			}	
	        
	        msgbox.showAndWait();
	        
	        
	        
	    }
		
		 private void addPatientList() {
				
				
				
				int duplicate =0;
				for (int i =0; i<tempPNumber; i++) {
					if (tempPatientName[i].equals(rPatientName1)) duplicate =1;
				}
				System.out.println(rPatientID1+" "+rPatientName1+" "+duplicate+"  "+tempPNumber);
				
				if (duplicate==0) {
					tempPatientID[tempPNumber]= rPatientID1;
					tempPatientName[tempPNumber]= rPatientName1;
					System.out.println(tempPatientName[tempPNumber]);
					tempPNumber++;
					
				}
					
				send("chat:"+MyID + ">" +rPatientID1 +">"+rPatientName1+"["+rPatientID1+"] 님이 진료를 신청하였습니다. \n");
				
				PatientList.getItems().add(rPatientName1);
						}
		 
		@FXML
		private void refreshListOnClick(ActionEvent e)
		{
			
		
			
	//		PatientList.getItems().removeAll(
	//			    PatientList.getSelectionModel().getSelectedItems()
	//			);
			
			for (int i =0; i<tempPNumber; i++) {
				if (!tempPatientName[i].equals(rPatientName1)) 
				{
				 PatientList.getItems().add(tempPatientName[i]);
				}
			}
			
			
			
			
			
		//			PatientList.getItems().add(rPatientName1);
					
			//		PatientList.setItems(PatientArrayList);
		}
			//Talk.requestFocus();
		
		
		@FXML 
	    private void SelectPatientOnClick(ActionEvent e)
	    {
			ObservableList<String> obj;
			obj = PatientList.getSelectionModel().getSelectedItems();
			
	//		Object obj = PatientList.getSelectionModel().getSelectedItem();
           // label1.setText(obj.toString());
			selectedName = obj.toString();
			selectedPatientName= selectedName.substring(1,selectedName.length()-1);
		//	selectedPatientName = selectedName;
		//	selectedPatientID =  patientID[0];
			
			for(int i=0;i<indexP;i++) {
				if (patientName[i].equals(selectedPatientName)) {
					selectedPatientID =  patientID[i];
					Name.setText(selectedPatientName);
					Sex.setText(patientSex[i]);
					Age.setText(patientAge[i]+"");
					Phone.setText(patientPhone[i]);
					PHx.setText(patientPMHx[i]);
					FHx.setText(patientDrugHx[i]);
					
				}
			}
			
		//	Sx.setDisable(false);
			Name.setText(selectedPatientName);
			StartConnect.setDisable(false);
			Treatment.setDisable(false);
	//		TalkBoard.appendText(selectedPatientName+"\n");
			TreatmentSave.setDisable(false);
		//	System.out.println(selectedPatientName);
			send("patientInfoRequest:" + MyID + ">" +selectedPatientID + ">" +MyName);
	    }
		
		
		
		@FXML 
	    private void StartConnectOnClick(ActionEvent e)
	    {
			
			send("startEx:"+MyID + ">" +selectedPatientID);
	/*		
			for(int i=0;i<indexP;i++) {
				if (patientID[i].equals(rPatientID1)) {
					Sx.setText(tempSx[i]) ;
					tempPNumber++;
				}
			}
			
	*/
			searchRecord();
			
		//	Sx.setDisable(false);
			Treatment.setDisable(false);
			Diagnosis1.setDisable(false);
			Diagnosis2.setDisable(false);
			Prescription1.setDisable(false);
			Prescription2.setDisable(false);
			Prescription3.setDisable(false);
			
		//	pane.setDisable(true);
	//		canvas.setDisable(false);
	//		canvasef.setDisable(false);
	//		colorpick.setDisable(false);
	//		Clear.setDisable(false);
			
			ChatSend.setDisable(false);
			TalkBoard.setDisable(false);
			Chat.setDisable(false);
		
			TreatmentSave.setDisable(false);
			pastDxHx.setDisable(false);	
	/*		
			try {
				readPatientRecordFile();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
	*/		
	//		pastDxHx.setText(readTx);
			
			send("chat:"+MyID + ">" +selectedPatientID +">"+MyName+"["+MyID+"] 의사가 "+selectedPatientName+" 환자분의 \n진료를 시작하였습니다.!!"+"\n");
	    }
		
		@FXML 
	    private void OnPatient(ActionEvent e)
	    {
	    }
		
		@FXML 
	    private void ChatSendOnClick(ActionEvent e)
	    {
			
			Chats = Chat.getText();
			
			   send("chat:" + MyID + ">" +selectedPatientID +">"+Chats+"\n");
			   //TalkBoard.appendText(Chats+"\n");
			   TalkBoard.appendText("["+MyID+"] "+Chats+"\n");
			
			   Chat.setText("");
	           Chat.requestFocus();
			
	    }
	
		
		@FXML 
	    private void SubmitOnClick(ActionEvent e)
	    {
				 
	    }
		
		@FXML 
	    private void PrintOnClick(ActionEvent e)
	    {
	    }
		
		@FXML 
	    private void QuitOnClick(ActionEvent e)
	    {
			System.exit(0);
	    }
		
		@FXML 
	    private void TreatmentSaveOnClick(ActionEvent e)
	    {
			if (Treatment.getText().equals("")) Treatment.setText("-");
			if (dis1.equals("")) dis1 ="-";
			if (dis2.equals("")) dis2 ="-";
			if (pre1.equals("")) pre1 ="-";
			if (pre2.equals("")) pre2 ="-";
			if (pre3.equals("")) pre3 ="-";
			
			System.out.println("complete:"+ MyID + ">" +selectedPatientID+">"+Treatment.getText()+">"+dis1+">"+dis2+">"+pre1+">"+pre2+">"+pre3);
			
			send("chat:"+MyID + ">" + selectedPatientID+">"+MyName+"["+MyID+"] 의사가 "+selectedPatientName+" 환자분의 \n 진료를 완료하였습니다.!!"+"\n");
			send("complete:"+MyID + ">" +selectedPatientID+">"+Treatment.getText()+">"+dis1+">"+dis2+">"+pre1+">"+pre2+">"+pre3);
			send("newRecord:"+MyID + ">" +MasterID+">"+ selectedPatientID+">"+selectedPatientName+">"+ MyID+">"+MyName+">"+Sx.getText()+">"+Treatment.getText()+">"+dis1+">"+dis2+">"+pre1+">"+pre2+">"+pre3+">"+nowString);
	//	insertRecord();
		//	updateRecord();
			Prescription1.setValue(DrugList1[indexDrug] );
			Prescription2.setValue(DrugList1[indexDrug] );
			Prescription3.setValue(DrugList1[indexDrug] );
			Diagnosis1.setValue(DiseaseList1[indexDisease]);
			Diagnosis2.setValue(DiseaseList1[indexDisease]);
			
			
			
			clearAll();
			//Logout.setDisable(false);
			PatientList.setDisable(false);
			refreshList.setDisable(false);
			SelectPatient.setDisable(false);
			Logout.setDisable(false);
			
			PatientList.getItems().removeAll(
				    PatientList.getSelectionModel().getSelectedItems()
				);
			
			for (int i =0; i<tempPNumber; i++) {
				if (tempPatientName[i].equals(selectedPatientName)) {
					tempPatientName[i]="";
				} else PatientList.getItems().add(tempPatientName[i]);
				
			}
			selectedPatientName="";
			selectedPatientID="";		
			
	    }
		
		
	 
	 
		 @FXML 
		 private void InputID(ActionEvent e)
		 {
			 PW.requestFocus();
		 } 
		

		
		 @FXML 
			
		    public void clearAll() {
				Name.setDisable(true);
				Age.setDisable(true);
				Sex.setDisable(true);
				Phone.setDisable(true);
		
				ChatSend.setDisable(true);
				TalkBoard.setDisable(true);
				Chat.setDisable(true);
				Sx.setDisable(true);
				PHx.setDisable(true);
				FHx.setDisable(true);
			//	PatientList.setDisable(true);

				Treatment.setDisable(true);
				//Diagnosis1.setDisable(true);
				//Diagnosis2.setDisable(true);
				//Prescription1.setDisable(true);
				//Prescription2.setDisable(true);
				//Prescription3.setDisable(true);
				
			//	pane.setDisable(true);
		//		canvas.setDisable(true);
		//		canvasef.setDisable(true);
		//		colorpick.setDisable(true);
		//		Clear.setDisable(true);
		//		clearsCanvas();
				
				ChatSend.setDisable(true);
				Logout.setDisable(true);
				refreshList.setDisable(false);
				SelectPatient.setDisable(false);
				StartConnect.setDisable(true);
				TreatmentSave.setDisable(true);
				pastDxHx.setDisable(true);
				
			//	ID.clear();
			//	PW.clear();
				
				Name.clear();
				Age.clear();
				Sex.clear();
				Phone.clear();
				Chat.clear();
				TalkBoard.clear();
				Sx.clear();
				PHx.clear();
				FHx.clear();
				pastDxHx.clear();
				Treatment.clear();
				
				pre1="";
				pre2="";
				pre3="";
				dis1="";
				dis2="";
				
				Treatment.setText("");
				Sx.setText("");
				
				
			//	Prescription1..clear();
			//	Prescription2.clear();
				//Prescription3.clear();
				
		//		ID.setDisable(false);
		//		PW.setDisable(false);
		//		Login.setDisable(false);
		//		NewID.setDisable(false);
				
				clearsCanvas();
				
			}
		
		
	
		 
		 
		 @FXML
			public void onMousePressedListener(MouseEvent e){ //직선 및 도형 그릴 때 시작 끝 저장 
				this.startX = e.getX();
				this.startY = e.getY();
				
				oldX=0;
				oldY=0;
				
		//		if (Ids.equals("teacher")) {
		//		System.out.println("Pencil:" + startX + "," + startY+ "," + sliders+ "," +colorS);
				send("Pencil:"+MyID + ">" +selectedPatientID + ">"+ oldX + ">" + oldY+ ">" + sliders+ ">" +colorS);
				send("Pencil:"+MyID + ">" +selectedPatientID + ">"+ startX + ">" + startY+ ">" + sliders+ ">" +colorS);
			
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
		//	  rX=0;
		//	  rY=0;
		      
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
		
		    //    gcb.setStroke(colorpick.getValue());
		   //     colorS=colors.toString();
		     //   System.out.println("Pencil:" + startX + "," + startY+ "," + sliders+ "," +colorS);
		//        if (Ids.equals("teacher")) {
		//      	send("Pencil:"+MyID + "," +selectedPatientName + lastX + "," + lastY+ "," + sliders+ "," +colorS);
		      	send("Pencil:"+MyID + ">" +selectedPatientID+ ">"+ lastX + ">" + lastY+ ">" + sliders+ ">" +colorS);
		//        }       
		      	startX=lastX;
		      	startY=lastY;
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
		    
		    
		    private void clearsCanvas()
		    {
		        gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		        gcb.clearRect(0, 0, canvasef.getWidth(), canvasef.getHeight());
		        send("Clear:"+MyID + ">" +selectedPatientID);
		        
		    }
		    

     	 
		    @FXML 
		    public void clearCanvas(ActionEvent e)
		    {
		        
		    	send("Clear:"+MyID + ">" +selectedPatientID);
		    //	send("Clear:");
		    }
		    
		    
		    @FXML
		    public void fileChooserOnClick(ActionEvent e){
		    	
		    	fileChoose();
		    	// Stage stage = (Stage) file.getScene().getWindow();
		    	
		    
		    }
		    
		   
		    

		    @FXML
		    public void setFreeDesign(ActionEvent e)
		    {
		        
		        freedesign = true;
		        gcb.setStroke(colorpick.getValue());
		    }
		    public void replyDrugRequest() { 
		    	
		    	int i=0;
		    	while (!DrugList1[i].equals("")) {
		    		System.out.println("DrugList1["+i+"] = "+ DrugList1[i]);
		    		send("replyDrug:"+MasterID + ">" +rDoctorID1+">"+ i +">"+DrugList1[i]);
		    		i++;
		    	}
		    	indexDrug=i;
		    	send("replyDrug:"+MasterID + ">" +rDoctorID1+">"+"End"+">"+ Integer.toString(indexDrug));
		    	System.out.println("indexDrug = "+ indexDrug);
		    }
		    
		    public void replyDiseaseRequest() {
		    	
		    	int i=0;
		    	while (!DiseaseList1[i].equals("")) {
		    		System.out.println("DiseaseList1["+i+"] = "+ DiseaseList1[i]);
		    		send("replyDisease:"+MasterID + ">" +rDoctorID1+">"+ i +">"+DiseaseList1[i]);
		    		i++;
		    	}
		    	indexDisease = i;
		    	send("replyDisease:"+MasterID + ">" +rDoctorID1+">"+"End"+">"+indexDisease);
		    	System.out.println("indexDisease = "+ indexDisease);
		    }	
		    
		    public void replyDoctorRequest() {
		    //	System.out.println("doctorID[0] = "+ doctorID[0]);
		    	int i=0;
		    	while (!doctorID[i].equals("")) {
		    		System.out.println("doctorID["+i+"] = "+ doctorID[i]);
		    		send("replyDoctor:"+MasterID + ">" +rDoctorID1+">"+ i +">"+doctorID[i]+">" +doctorPW[i]  + ">"  +doctorName[i] );
		    		i++;
		    	}
		    	indexD =i;
		    	send("replyDoctor:"+MasterID + ">" +rDoctorID1+">"+"End"+">"+indexD);
		    	System.out.println("indexD = "+ indexD);
		    	}
		    
		    public void replyPatientRequest() {
		    	int i=0;
		    	while (!patientID[i].equals("")) {
		    		System.out.println("patientID["+i+"] = "+ patientID[i]);
		    		send("replyPatient:"+MasterID + ">" +rDoctorID1+">"+ i +">"+patientID[i]+">" +patientPW[i]   + ">" +patientName[i] + ">" +patientSex[i] + ">" +patientAge[i] + ">" +patientPhone[i] + ">" +patientPMHx[i] + ">" +patientDrugHx[i]);
		    		i++;
		    	}
		    	indexP=i;
		    	send("replyPatient:"+MasterID + ">" +rDoctorID1+">"+"End"+">"+indexP);
		    	System.out.println("indexP = "+ indexP);
		    }
		    
		   
		    
		    public void replyRecordRequest() {
		    	
		    	int i=0;
		    	while (!recordPatientID[i].equals("")) {
		    		System.out.println("recordPatientID["+i+"] = "+ recordPatientID[i]);
		    		send("replyRecord:"+MasterID + ">" +rDoctorID1+">"+ i +">"+recordPatientID[i] +">"+	recordPatientName[i]+">"+ recordDoctorID[i]+">"+recordDoctorName[i] +">"+ recordPatientSx[i]+">"+recordDoctorOpinion[i]+">"+recordDisease1[i]+">"+recordDisease2[i]+">"+recordDrug1[i] +">"+recordDrug2[i]+">"+	recordDrug3[i]+">"+	recordExamDate[i]);
		    	i++;
		    	}	
		    	indexR = i;
		    	send("replyRecord:"+MasterID + ">" +rDoctorID1+">"+"End" +">"+indexR);
		    	System.out.println("indexR = "+ indexR);
		    }
		    
		   
		    
		    
		   public void checkPatientLoginInfo() {
			   int ploginOK =0;
			   System.out.println("rPatienttID1="+rPatientID1+  "rPatienttPW="+rPatientPW);
			   for (int i=0; i<indexP; i++) {
				   System.out.println(patientID[i]+":"+patientPW[i]);
				   if (patientID[i].equals(rPatientID1) && patientPW[i].equals(rPatientPW) ){
					   ploginOK=1;
					   System.out.println("Doctor: 환자정보 login 확인 성공");
					   send("patientLoginOK:"+ MyID + ">"  +patientID[i]  + ">" +patientPW[i] + ">" +patientName[i] + ">" +patientSex[i] + ">" +patientAge[i] + ">" +patientPhone[i] + ">" +patientPMHx[i] + ">" +patientDrugHx[i]);
					   selectedPatientID = patientID[i];
				   }
			   }
			   if (ploginOK==0) {
				   System.out.println("Doctor: 환자정보 login 확인 실패");
				   send("patientLoginFail:"+ MyID + ">" +rPatientID1 ); 
			   }
			   
		   }
			
		    public void readPatientInfo( ) {
		    	try {
					
					
			    	query = "SELECT * FROM patientinfo"; // sql문
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					
					 indexP = 0;
					while (rs.next()) {
						
						 patientID[indexP] = rs.getString(1);
						 patientPW[indexP] = rs.getString(2);
						 patientName[indexP] =rs.getString(3);
						 patientSex[indexP] = rs.getString(4);
						 patientAge[indexP] = rs.getString(5);
						 patientPhone[indexP] = rs.getString(6);
						 patientPMHx[indexP] = rs.getString(7);
						 patientDrugHx[indexP] = rs.getString(8);
						 
			//		 System.out.println(patientID[indexP]+patientPW[indexP]+patientName[indexP]+patientSex[indexP]+patientAge[indexP]+patientPhone[indexP]+patientPMHx[indexP]+patientDrugHx[indexP]);		
								 
					
					indexP++;
					patientID[indexP]="";
					}
			//		conn.close();
					System.out.println("환자정보읽기 성공");		
			    	} catch (Exception e) {
						e.printStackTrace();
					}
		    	
		    }
		    
		    public void readDrug() {
		    	try {
					
					
			    	query = "SELECT * FROM drug"; // sql문
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					
					indexDrug=0;
					while (rs.next()) {
						
						DrugList1[indexDrug] = rs.getString(1);
				//		DrugList2[indexDrug] = rs.getString(1);
				//		DrugList3[indexDrug] =rs.getString(1);
				//		Prescription1.getItems().add(DrugList1[indexDrug]);
				//		Prescription2.getItems().add(DrugList1[indexDrug]);
				//		Prescription3.getItems().add(DrugList1[indexDrug]);
						 
				//		System.out.println(DrugList1[indexDrug]+"  "+DrugList2[indexDrug]+"  "+DrugList3[indexDrug]);			
			//		System.out.println(patientID[indexP]+patientPW[indexP]+patientName[indexP]+patientSex[indexP]+patientAge[indexP]+patientPhone[indexP]+patientPMHx[indexP]+patientDrugHx[indexP]);		
					indexDrug++;
					}
					DrugList1[indexDrug] = "";
			//		DrugList2[indexDrug] = "";
			//		DrugList3[indexDrug] = "";
					Prescription1.getItems().addAll(DrugList1);
					Prescription2.getItems().addAll(DrugList1);
					Prescription3.getItems().addAll(DrugList1);
					//PrescriptionArrayList.add(DrugList1);
			//		conn.close();
					System.out.println("약 읽기 성공");	
			    	} catch (Exception e) {
						e.printStackTrace();
					}
		    	
		    }
		    
		    public void readDisease() {
		    	try {
					
					
			    	query = "SELECT * FROM disease"; // sql문
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					
				    indexDisease = 0;
					while (rs.next()) {
						
						DiseaseList1[indexDisease] = rs.getString(1);
				//		DiseaseList2[indexDisease] = rs.getString(1);
						
						
			//	System.out.println(DiseaseList1[indexDisease]+"  "+DiseaseList2[indexDisease]);		
						indexDisease++;
					}
					DiseaseList1[indexDisease]="";
				//	DiseaseList2[indexDisease]="";
					Diagnosis1.getItems().addAll(DiseaseList1);
					Diagnosis2.getItems().addAll(DiseaseList1);
					System.out.println("병명 읽기 성공");	
			//		conn.close();
			    	
			    	} catch (Exception e) {
						e.printStackTrace();
					}
		    	
		    }
		    public void readDoctorInfo( ) {
		    	try {
					
					
			    	query = "SELECT * FROM doctor"; // sql문
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					
					indexD = 0;
					while (rs.next()) {
						
						 doctorID[indexD] = rs.getString(1);
						 doctorPW[indexD] = rs.getString(2);
						 doctorName[indexD] =rs.getString(3);
						
						 
		//	System.out.println(doctorID[indexD]+doctorPW[indexD]+doctorName[indexD]+doctorLiscence[indexD]+doctorHospital[indexD]+doctorAddress[indexD]+doctorPhone[indexD]);		
					
					indexD++;
					doctorID[indexD]="";
					
					}
					System.out.println("의사 읽기 성공");	
			//		conn.close();
			    	
			    	} catch (Exception e) {
						e.printStackTrace();
					}
		    	
		    }  
		    
		   	
		    	
		    public void newDoctorInsert( ) {
    	        String sql = "insert into doctor values(?,?,?)";
    	        PreparedStatement pstmt = null;
			//	rs = stmt.executeQuery(query);
    	        try {
    	            pstmt = conn.prepareStatement(sql);
    	            pstmt.setString(1, newDoctorID);
    	            pstmt.setString(2, newDoctorPW);
    	            pstmt.setString(3, newDoctorName);
    	 
    	            pstmt.executeUpdate();
    	            System.out.println("의사저장성공");
    	            
    	            
    	        } catch (SQLException e) {
    	            // TODO Auto-generated catch block
    	        	System.out.println("의사저장실패");
    	        }
    	        
    	        send("DoctorRequest:"+ID.getText()+ ">" + MasterID );
	            send("PatientRequest:"+ID.getText()+ ">" + MasterID );
	            send("RecordRequest:"+ID.getText()+ ">" + MasterID );
    	    }
		    
		    
		    public void readRecord( ) {
		    	try {
					
					
			    	query = "SELECT * FROM examrecord"; // sql문
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					
			//		int indexR = 0;
					while (rs.next()) {
						
						recordPatientID[indexR] = rs.getString(1);
						recordPatientName[indexR] = rs.getString(2);
						recordDoctorID[indexR] =rs.getString(3);
						recordDoctorName[indexR] = rs.getString(4);
						
						recordPatientSx[indexR] = rs.getString(5);
						recordDoctorOpinion[indexR] = rs.getString(6);
						recordDisease1[indexR] = rs.getString(7);
						recordDisease2[indexR] = rs.getString(8);
						recordDrug1[indexR] = rs.getString(9);
						recordDrug2[indexR] = rs.getString(10);
						recordDrug3[indexR] = rs.getString(11);
						recordExamDate[indexR] = rs.getString(12);
				//	System.out.println(patientID[indexP]+patientPW[indexP]+patientName[indexP]+patientSex[indexP]+patientAge[indexP]+patientPhone[indexP]+patientPMHx[indexP]+patientDrugHx[indexP]);		
					indexR++;
					recordPatientID[indexR]="";
					}
				//	conn.close();
					System.out.println("진료정보 읽기 성공");	
			    	} catch (Exception e) {
						e.printStackTrace();
					}
		    	
		 
		
		    } 
		    
		    public void searchRecord( ) {
		    
		    	pastRecord="";
					
			    	query = "SELECT * FROM examrecord where PatientID = ? and DoctorID = ?"; // sql문
			    	PreparedStatement pstmt = null;
			        try {
			            pstmt = conn.prepareStatement(query);
			            pstmt.setString(1, selectedPatientID);
			            pstmt.setString(2, MyID);
			            ResultSet rs = pstmt.executeQuery();
			           
					
			         //   int recordExist=0;
			//		int indexR = 0;
			            while (rs.next()) {
			            	pastRecord += "["+rs.getString(12)+"]\n";;
			            	pastRecord +="환자 : "+ rs.getString(1)+" : ";
			            	pastRecord += rs.getString(2);
			            	pastRecord +="   , 의사 : "+rs.getString(3)+" : ";
			            	pastRecord += rs.getString(4)+"\n";
						
			            	pastRecord += "증상 : "+rs.getString(5)+"\n";
			            	pastRecord += "소견 : "+rs.getString(6)+"\n";
			            	pastRecord += "병명 : "+rs.getString(7)+"\n";
			            	pastRecord += "           "+rs.getString(8)+"\n";
			            	pastRecord += "처방 : "+rs.getString(9)+"\n";
			            	pastRecord += "           "+rs.getString(10)+"\n";
			            	pastRecord += "           "+rs.getString(11)+"\n";
			            	pastRecord += "---------------------------------"+"\n\n";
			            	
				//	System.out.println(patientID[indexP]+patientPW[indexP]+patientName[indexP]+patientSex[indexP]+patientAge[indexP]+patientPhone[indexP]+patientPMHx[indexP]+patientDrugHx[indexP]);		
				//	indexR++;
			            }
				//	conn.close();
					pastDxHx.setText(pastRecord);
					System.out.println("과거 진료정보 읽기 성공");	
			    	} catch (Exception e) {
						e.printStackTrace();
					}
		    	
		 
		
		    } 
		    
	    	 public void newRecordInsert() {
		    	        String sql = "insert into examrecord values(?,?,?,?,?,?,?,?,?,?,?,?)";
		    	        PreparedStatement pstmt = null;
					//	rs = stmt.executeQuery(query);
		    	        try {
		    	            pstmt = conn.prepareStatement(sql);
		    	            pstmt.setString(1, newPID);
		    	            pstmt.setString(2, newPName);
		    	            pstmt.setString(3, newDID);
		    	            pstmt.setString(4, newDName);
		    	            pstmt.setString(5, newSx);
		    	            pstmt.setString(6, newTx);
		    	            pstmt.setString(7, newDis1);
		    	            pstmt.setString(8, newDis2);
		    	            pstmt.setString(9, newPre1);
		    	            pstmt.setString(10, newPre2);
	 	            		pstmt.setString(11, newPre3);
	 	            		pstmt.setString(12, newNow);
	 	            		
	 	                    pstmt.executeUpdate();
		    	            System.out.println("진료기록 저장성공");
		    	        } catch (SQLException e) {
		    	        	e.printStackTrace();
		    	            // TODO Auto-generated catch block
		    	        	System.out.println("진료기록 저장실패");
		    	        }
		    	        
		    	        send("DoctorRequest:"+ID.getText()+ ">" + MasterID );
	    	            send("PatientRequest:"+ID.getText()+ ">" + MasterID );
	    	            send("RecordRequest:"+ID.getText()+ ">" + MasterID );
		    	    }
		    	 
		     public void updateRecord() {
		    	        String sql = "update examrecord set PatientSx=?,DoctorOpinion=?,Disease1=?, Disease2=?,Drug1=?, Drug2=?,Drug3=? where PatientID = ? AND DoctorID = ?" ;
		    	        PreparedStatement pstmt = null;
		    	        try {
		    	            pstmt = conn.prepareStatement(sql);
		    	            pstmt.setString(1, Sx.getText());
		    	            pstmt.setString(2, Treatment.getText());
		    	            pstmt.setString(3, dis1);
		    	            pstmt.setString(4, dis2);
		    	            pstmt.setString(5, pre1);
		    	            pstmt.setString(6, pre2);
		    	            pstmt.setString(7, pre3);
		    	            pstmt.setString(8, selectedPatientID);
		    	            pstmt.setString(9, MyID);
		    	    //        pstmt.setDate(10,  DATE_FORMAT(now, '%Y-%m-%d'));
		    	            System.out.println("진료기록있네요");
		    	        
		    	            pstmt.executeUpdate();
		    	        } catch (SQLException e) {
		    	            // TODO Auto-generated catch block
		    	        	System.out.println("진료기록없네요");
		    	        } finally {
		    	            try {
		    	                if (pstmt != null && !pstmt.isClosed())
		    	                    pstmt.close();
		    	            } catch (SQLException e) {
		    	                // TODO Auto-generated catch block
		    	                e.printStackTrace();
		    	            }
		    	        }
		    	    }
		    	 
		
		    	    public void newPatientInsert() {
		    	        String sql = "insert into patientinfo values(?,?,?,?,?,?,?,?)";
		    	        PreparedStatement stmt = null;
					//	rs = stmt.executeQuery(query);
		    	        try {
		    	            stmt = conn.prepareStatement(sql);
		    	            stmt.setString(1, newPatientID);
		    	            stmt.setString(2, newPatientPW);
		    	            stmt.setString(3, newPatientName);
		    	            stmt.setString(4, newPatientSex);
		    	            stmt.setString(5, newPatientAge);
		    	            stmt.setString(6, newPatientPhone);
		    	            stmt.setString(7, newPatientPMHx);
		    	            stmt.setString(8, newPatientDrugHx);

		    	            patientID[indexP] = newPatientID;
		    	            patientPW[indexP] = newPatientPW;
    						patientName[indexP] = newPatientName;
    						patientSex[indexP] = newPatientSex;
    						patientAge[indexP] = newPatientAge;
    						patientPhone[indexP] = newPatientPhone;
    						patientPMHx[indexP] = newPatientPMHx;
    						patientDrugHx[indexP] = newPatientDrugHx;
		    				indexP++;	  
		    	   		
	 	                    stmt.executeUpdate();
		    	            System.out.println("새환자정보 저장성공");
		    	        } catch (SQLException e) {
		    	        	e.printStackTrace();
		    	            // TODO Auto-generated catch block
		    	        	System.out.println("새환자정보 저장실패");
		    	        }
		    	        
		    	        send("DoctorRequest:"+ID.getText()+ ">" + MasterID );
	    	            send("PatientRequest:"+ID.getText()+ ">" + MasterID );
	    	            send("RecordRequest:"+ID.getText()+ ">" + MasterID );
		    	    }
		    
		    	    public void updatePatient() {
		    	        String sql = "update PatientInfo set PW=?,Name=?, Sex=?,Age=?, Phone=?,PMHx=?, DrugHx=? where ID = ? " ;
		    	        PreparedStatement pstmt = null;
		    	        try {
		    	            pstmt = conn.prepareStatement(sql);
		    	            pstmt.setString(1, newPatientPW);
		    	            pstmt.setString(2, newPatientName);
		    	            pstmt.setString(3, newPatientSex);
		    	            pstmt.setString(4, newPatientAge);
		    	            pstmt.setString(5, newPatientPhone);
		    	            pstmt.setString(6, newPatientPMHx);
		    	            pstmt.setString(7, newPatientDrugHx);
		    	            pstmt.setString(8, newPatientID);
		    	            
		    	            pstmt.executeUpdate();
		    	            
		    	             for (int i=0; i<indexP; i++) {
		    				//	   System.out.println(patientID[i]+":"+patientPW[i]);
		    					   if (patientID[i].equals(rPatientID1) && patientPW[i].equals(rPatientPW) ){
		    						patientPW[i] = newPatientPW;
		    						patientName[i] = newPatientName;
		    						patientSex[i] = newPatientSex;
		    						patientAge[i] = newPatientAge;
		    						patientPhone[i] = newPatientPhone;
		    						patientPMHx[i] = newPatientPMHx;
		    						patientDrugHx[i] = newPatientDrugHx;
		    						
		    						
		    					   }
		    				   }            
		    	            
		    	            
		    	            
		    	            System.out.println("환자정보수정저장 완료");
		    	        } catch (SQLException e) {
		    	            // TODO Auto-generated catch block
		    	        	System.out.println("환자정보수정저장 실패");
		    	        	
		    	        	e.printStackTrace();
		    	        } 
		    	        
		    	    }
		    	    
		    	    public void fileChoose() {
				    	FileChooser fileChooser = new FileChooser();
				        fileChooser.setTitle("Open a file");
				        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
				        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
				        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file","*.txt"));
				        // this is for saving a file. remove the setInitialFileName if you are opening a file
				        //fileChooser.setInitialFileName("Untitled");
				        Stage stage = (Stage) fileChooserButton.getScene().getWindow();
				        File selectedFile = fileChooser.showOpenDialog(stage);
				       
				        
				       
				       // stage = (Stage) file.getScene().getWindow();
				        //File selectedFile = fileChooser.showOpenDialog(stage);
				        if(selectedFile != null){

				            // this is for saving a file
				            /*try {
				                FileWriter fileWriter = new FileWriter(selectedFile);
				                BufferedWriter writer = new BufferedWriter(fileWriter);
				                writer.write("Learning how to use the JavaFX FileChooser");
				                writer.close();
				                System.out.println("The file has been saved in "+ selectedFile.getAbsolutePath());
				            } catch (IOException e) {
				                throw new RuntimeException(e);
				            }*/

				                //this is for opening a file
				                //Label.setText(selectedFile.getName());
				                Image image = new Image(selectedFile.getAbsolutePath());
				                send("Imagefile:" + selectedFile.getAbsolutePath());
				                imgView.setImage(image);
				                //send("Imagefile:" + selectedFile.getAbsolutePath());
				                System.out.println(selectedFile.getAbsolutePath());

				                /* This is for reading a text file
				                try {
				                    BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
				                    StringBuilder stringBuilder = new StringBuilder();
				                    String line;
				                    while((line = bufferedReader.readLine()) != null){
				                        stringBuilder.append(line).append("\n");
				                    }
				                    System.out.println(stringBuilder.toString());
				                } catch (FileNotFoundException e) {
				                    throw new RuntimeException(e);
				                } catch (IOException e) {
				                    throw new RuntimeException(e);
				                }*/
				        }else{
				            System.out.println("No file has been selected");
				        }
				    }
		    	    
		    	    public void setFile() {
			    	 	Image image = new Image(receivedFilePath);
		                imgView.setImage(image);
				    }
	    
}
