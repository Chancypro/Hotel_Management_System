package application;

import java.net.URL;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		try
		{
			Parent root=FXMLLoader.load(getClass().getResource("/application/LoginAll.fxml"));  //加载登录页面
				//这里改来测试了记得改回去！
				//配置css！不可改变大小
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/LoginAll.css");
				scene.getStylesheets().add(css.toExternalForm());
				 //设置窗口图标！
		        Image icon = new Image("/resourcefile/duck100.png");
		        primaryStage.getIcons().add(icon);
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.setTitle("登录窗口");
				primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}