package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuestLoginController{

    @FXML
    private TextField idField;

    //������ģ�
    @FXML
    private RXFillButton loginBtn;

    @FXML
    private RXFillButton cancelBtn;

    @FXML
    void handleLogin(ActionEvent event) {
    	try
    	{   
    		
			String user=idField.getText();
			
			String DBDriver="com.mysql.cj.jdbc.Driver";  // ���ݿ� MYSQL װ��
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
              		
			//������������
			Class.forName(DBDriver);
			
			//ͨ�������������������������Դ������
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			System.out.println("���ݿ����ӳɹ���");
			Statement stmt=con.createStatement();
			
				ResultSet rs=stmt.executeQuery("SELECT * from guest_table");
				int flag=0;
				
				while (rs.next()) //���������
				{  
					if (rs.getString("gid").equals(user))
					{
						flag=1;
						System.out.println("�û�"+user+"��¼�ɹ���");
						
						//css��
				        FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goinAllPage.fxml"));
						Stage stage=new Stage();
						Parent root=loader.load();
						Scene scene=new Scene(root);
						URL css=this.getClass().getClassLoader().getResource("application/goinAllPage.css");
						scene.getStylesheets().add(css.toExternalForm());
						Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // ��ȡ����Ļ�Ĵ�С
						stage.setX(primaryScreenBounds.getMinX()); // ���ô��ڵ�x����
						stage.setY(primaryScreenBounds.getMinY()); // ���ô��ڵ�y����
						stage.setWidth(primaryScreenBounds.getWidth()); // ���ô��ڵĿ��
						stage.setHeight(primaryScreenBounds.getHeight()); // ���ô��ڵĸ߶�
						//���ô���ͼ�꣡
						Image icon = new Image("/resourcefile/duck100.png");
			            stage.getIcons().add(icon);
						stage.setResizable(false);
						stage.setScene(scene);
						stage.setTitle("�Ƶ����ϵͳ");
						stage.show();
						
						
						//�ټ����û�п�ָ��
						Stage preStage=(Stage)loginBtn.getScene().getWindow();
						preStage.close();
						
						goinAllPageController gc=loader.getController();
						gc.setUser(user);
						
						break;
					}
				}
				if (flag==0)
				{
					System.out.println("�����ڸ��û���");
					Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("��ܰ��ʾ");
		    		alert.setHeaderText("");
		    		alert.setContentText("id�����ڣ������䣡");
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
