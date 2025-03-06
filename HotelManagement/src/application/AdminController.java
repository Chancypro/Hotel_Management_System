package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private TextField anameField;

    //组件更改！
    @FXML
    private RXFillButton subBtn;
    
    @FXML
    private Button returnBtn6;

    @FXML
    private TextField apwField;

    @FXML
    private Label label1;

    @FXML
    private TextField aidField;

    @FXML
    private Label label2;

    @FXML
    private Label label3;
    
    private Connection con;
    private String user;
    private String userName;
    
    void setUser1(String userName)
    {
    	this.userName=userName;
    }
    
    @FXML
    void handleReturn6()
    {
    	try
    	{
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Stage.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Stage.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setTitle("酒店管理系统");
			//设置窗口图标！
	        Image icon = new Image("/resourcefile/duck100.png");
	        stage.getIcons().add(icon);
			stage.show();
			
			StageController sc=loader.getController();
			sc.setUser(userName);
			
			Stage preStage=(Stage)returnBtn6.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

    void showInfo(String user)
    {
    	try
    	{
    		this.user=user;
	    	aidField.setText(user);
	    	
	    	String DBDriver="com.mysql.cj.jdbc.Driver";  // 数据库 MYSQL 装载
	        String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
	        String DBUser="root", DBPassword="sysu";
	          		
			//加载驱动程序
			Class.forName(DBDriver);
			//通过驱动程序管理器建立与数据源的连接
			Connection con=DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			this.con=con;
			
			Statement stmt=con.createStatement();
			String sql="SELECT aname,apasswd from admin_table WHERE aid='"+user+"';";
			ResultSet rs=stmt.executeQuery(sql);
	    	while (rs.next())
	    	{
		    	anameField.setText(rs.getString("aname"));
		    	apwField.setText(rs.getString("apasswd"));
	    	}
    	}
    	catch (Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void subInfo(ActionEvent event) {
    	
    	String name=anameField.getText();
    	String passwd=apwField.getText();
    	if (name=="")
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("姓名不能为空！");
    		alert.show();
    		return;
    	}
    	if (passwd=="")
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("密码不能为空！");
    		alert.show();
    		return;
    	}
    	if (passwd.length()<3)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("密码不能少于3位！");
    		alert.show();
    		return;
    	}
    	if (passwd.length()>15)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("密码不能多于15位！");
    		alert.show();
    		return;
    	}
    	for (int i=0;i<passwd.length();i++)
    	{
    		if (passwd.charAt(i)>='0'&&passwd.charAt(i)<='9') continue;
    		if (passwd.charAt(i)>='a'&&passwd.charAt(i)<='z') continue;
    		if (passwd.charAt(i)>='A'&&passwd.charAt(i)<='Z') continue;
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("密码只能由数字和字母构成！");
    		alert.show();
    		return;
    	}
    	
    	Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("温馨提示");
		alert.setHeaderText("");
		alert.setContentText("修改成功！");
		alert.show();
		
		try
		{
			Statement stmt=con.createStatement();
			String sql="UPDATE admin_table SET aname='"+name+"',apasswd='"+passwd+"' WHERE aid='"+user+"';";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
    	{
    		System.out.println(e);
    	}
    }

}
