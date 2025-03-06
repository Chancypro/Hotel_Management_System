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

    //组件更改！
    @FXML
    private RXFillButton loginBtn;

    @FXML
    private RXFillButton cancelBtn;

    @FXML
    void handleLogin(ActionEvent event) {
    	try
    	{   
    		
			String user=idField.getText();
			
			String DBDriver="com.mysql.cj.jdbc.Driver";  // 数据库 MYSQL 装载
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
              		
			//加载驱动程序
			Class.forName(DBDriver);
			
			//通过驱动程序管理器建立与数据源的连接
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			System.out.println("数据库连接成功！");
			Statement stmt=con.createStatement();
			
				ResultSet rs=stmt.executeQuery("SELECT * from guest_table");
				int flag=0;
				
				while (rs.next()) //遍历结果集
				{  
					if (rs.getString("gid").equals(user))
					{
						flag=1;
						System.out.println("用户"+user+"登录成功！");
						
						//css！
				        FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goinAllPage.fxml"));
						Stage stage=new Stage();
						Parent root=loader.load();
						Scene scene=new Scene(root);
						URL css=this.getClass().getClassLoader().getResource("application/goinAllPage.css");
						scene.getStylesheets().add(css.toExternalForm());
						Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
						stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
						stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
						stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
						stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
						//设置窗口图标！
						Image icon = new Image("/resourcefile/duck100.png");
			            stage.getIcons().add(icon);
						stage.setResizable(false);
						stage.setScene(scene);
						stage.setTitle("酒店管理系统");
						stage.show();
						
						
						//再检查有没有空指针
						Stage preStage=(Stage)loginBtn.getScene().getWindow();
						preStage.close();
						
						goinAllPageController gc=loader.getController();
						gc.setUser(user);
						
						break;
					}
				}
				if (flag==0)
				{
					System.out.println("不存在该用户！");
					Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("温馨提示");
		    		alert.setHeaderText("");
		    		alert.setContentText("id不存在，请重输！");
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
