package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.leewyatt.rxcontrols.controls.RXFillButton;
import com.leewyatt.rxcontrols.controls.RXPasswordField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
	
	//������ģ�
	@FXML private AnchorPane back;
    @FXML private StackPane loginwindowpa;
    @FXML private VBox loginwindow;
    @FXML private Label title;
    @FXML private TextField userId;
    @FXML private RXPasswordField userPassword;
    @FXML private RXFillButton loginBtn;
    @FXML private RXFillButton cancelBtn;
    @FXML private Stage stage;
    
    @FXML
    void handleLogin(ActionEvent event) {
    	try
    	{   
    		
			String userName=userId.getText();
			String userPasswd=userPassword.getText();
			
			String DBDriver="com.mysql.cj.jdbc.Driver";  // ���ݿ� MYSQL װ��
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
              		
			//������������
			Class.forName(DBDriver);
			
			//ͨ�������������������������Դ������
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			System.out.println("���ݿ����ӳɹ���");
			Statement stmt=con.createStatement();
			
				ResultSet rs=stmt.executeQuery("SELECT * from admin_table");
				int flag=0;
				
				while (rs.next()) //���������
				{  
					if (rs.getString("aid").equals(userName))
					{
						flag=1;
						if (rs.getString("apasswd").equals(userPasswd))
						{
							System.out.println("�û�"+userName+"��¼�ɹ���");
							
							//css��
							Stage stage=new Stage();
							FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Stage.fxml"));
							Parent root=loader.load();
							Scene scene=new Scene(root);
							URL css=this.getClass().getClassLoader().getResource("application/Stage.css");
							scene.getStylesheets().add(css.toExternalForm());
							stage.setResizable(false);				
							stage.setScene(scene);
							//���ô���ͼ�꣡
							Image icon = new Image("/resourcefile/duck100.png");
							stage.getIcons().add(icon);
							stage.setTitle("�Ƶ����ϵͳ");
							stage.show();
							
							System.out.println("��ָ��ϵ�1");
							
							StageController sc=loader.getController();
							
							System.out.println("��ָ��ϵ�2");
							
							sc.setUser(userName);
							
							System.out.println("��ָ��ϵ�3");
							
							//�ټ����û�п�ָ��
							Stage preStage=(Stage)loginBtn.getScene().getWindow();
							preStage.close();
							
						}
						else
						{
							System.out.println("�������");
							Alert alert=new Alert(AlertType.INFORMATION);
				    		alert.setTitle("��ܰ��ʾ");
				    		alert.setHeaderText("");
				    		alert.setContentText("�������");
				    		alert.show();
				    		return;
						}
						break;
					}
				}
				if (flag==0)
				{
					System.out.println("�����ڸ��û���");
					Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("��ܰ��ʾ");
		    		alert.setHeaderText("");
		    		alert.setContentText("�����ڸ��û���");
		    		alert.show();
		    		return;
				}
				rs.close();
			
	        stmt.close();
	        con.close();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
    	try
    	{
    		Stage stage=(Stage)cancelBtn.getScene().getWindow();
    		stage.close();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }

}
