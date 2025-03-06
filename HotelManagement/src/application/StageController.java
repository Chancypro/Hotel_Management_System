package application;

import java.net.URL;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageController {
	//组件更改！
	@FXML
    private RXFillButton incomeBtn;
    @FXML
    private RXFillButton roomBtn;
    @FXML
    private RXFillButton goodsBtn;
    @FXML
    private RXFillButton guestBtn;
    @FXML
    private RXFillButton consumeBtn;
    @FXML
    private RXFillButton adminBtn;
    @FXML private Button quit;
    @FXML private Label adminLabel;
    
    private String user;
    
    public void setUser(String user)
    {
    	this.user=user;
    	adminLabel.setText(user);
    }
    
    @FXML
    void handleIncome(ActionEvent event) {
    	try
    	{
    		//配置css！不可改变大小
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/fxml.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/fxml.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
			stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
			stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
			stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
			stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("收益数据");
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.show();
			
			fxmlController sc=loader.getController();
			sc.setUser1(user);
			
			Stage preStage=(Stage)incomeBtn.getScene().getWindow();
			preStage.close();
			//RMfxmlController rmController=loader.getController();
			//rmController.updateData();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }

    @FXML
    void handleRoom(ActionEvent event) {
    	try
    	{
    		//配置css！不可改变大小
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/RMfxml.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/RMfxml.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
			stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
			stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
			stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
			stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("客房管理");
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.show();
			
			RMfxmlController sc=loader.getController();
			sc.setUser1(user);
			
			Stage preStage=(Stage)roomBtn.getScene().getWindow();
			preStage.close();
			
			//RMfxmlController rmController=loader.getController();
			//rmController.updateData();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }

    @FXML
    void handleGoods(ActionEvent event) {
    	try
    	{
    		//配置css！不可改变大小
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/goods.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
			stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
			stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
			stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
			stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("商品管理");
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.show();
			
			goodsController sc=loader.getController();
			sc.setUser1(user);
			
			Stage preStage=(Stage)goodsBtn.getScene().getWindow();
			preStage.close();
			
			//RMfxmlController rmController=loader.getController();
			//rmController.updateData();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }

    @FXML
    void handleGuest(ActionEvent event) {
    	try
    	{
    		//配置css！不可改变大小
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/GuestMain.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/GuestMain.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
			stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
			stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
			stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
			stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("住宿管理");
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.show();
			
			GuestMainController sc=loader.getController();
			sc.setUser1(user);
			
			Stage preStage=(Stage)guestBtn.getScene().getWindow();
			preStage.close();
			
	    	GuestMainController gmController=loader.getController();
			gmController.showTable();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    @FXML
    void handleConsume(ActionEvent event) {
    	try
    	{
    		//配置css！不可改变大小
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Consume.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Consume.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
			stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
			stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
			stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
			stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("商品订购");
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.show();
			
			ConsumeController sc=loader.getController();
			sc.setUser1(user);
			
			Stage preStage=(Stage)guestBtn.getScene().getWindow();
			preStage.close();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    @FXML
    void handleAdmin(ActionEvent event) {
    	try
    	{
    		//配置css！不可改变大小
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Admin.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Admin.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("管理员信息");
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.show();
			
			AdminController sc=loader.getController();
			sc.setUser1(user);
			
			Stage preStage=(Stage)adminBtn.getScene().getWindow();
			preStage.close();
			
			AdminController ac=loader.getController();
			ac.showInfo(user);
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    @FXML
    void handleQuit(ActionEvent event) {
    	try
    	{
    		Stage stage=(Stage)quit.getScene().getWindow();
    		stage.close();
    		
    		Parent root=FXMLLoader.load(getClass().getResource("/application/LoginAll.fxml"));  //加载登录页面
			//这里改来测试了记得改回去！
			//配置css！不可改变大小
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/LoginAll.css");
			scene.getStylesheets().add(css.toExternalForm());
			 //设置窗口图标！
	        Image icon = new Image("/resourcefile/duck100.png");
	        Stage primaryStage=new Stage();
	        primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("登录窗口");
			primaryStage.show();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
}
