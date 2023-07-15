package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;


public class PresController {
	
	 Alert alert = new Alert(AlertType.NONE);

	@FXML
	//Label nameLabel;
	TextField presPatientName, presPatientPhone, presDoctorName, presDisease1, presDisease2, presDrug1, presDrug2, presDrug3;
	public Button presPrint,Quit;
	
	 @FXML 
	    private void QuitOnClick(ActionEvent e)
	    {
		 System.exit(0);
	
	    }
	 @FXML 
	    private void presPrintOnCLick(ActionEvent e)
	    {
		 	alert.setAlertType(AlertType.CONFIRMATION);
	        alert.setContentText("<알림> "+ "처방전을 인쇄하였습니다!!");
	        alert.showAndWait();
	
	    }
	
	
	public void displayName(String MyName) {
		presPatientName.setText(MyName);
        
	}
	

	public void displayPhone(String MyPhone) {
		presPatientPhone.setText(MyPhone);
        
	}
	

	public void displayDisease1(String MyDisease1) {
		presDisease1.setText(MyDisease1);
       
	}
	

	public void displayDisease2(String MyDisease2) {
		presDisease2.setText(MyDisease2);
        
	}
	

	public void displayDrug1(String MyDrug1) {
		presDrug1.setText(MyDrug1);
        
	}
	

	public void displayDrug2(String MyDrug2) {
		presDrug2.setText(MyDrug2);
        
	}

	public void displayDrug3(String MyDrug3) {
		presDrug3.setText(MyDrug3);
        
	}
	
	
	public void displayDoctorName(String DoctorName) {
		presDoctorName.setText(DoctorName);
        
	}

	
}