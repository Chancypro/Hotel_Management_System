package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class goinAllPageController{
	private Media media;
	private MediaPlayer mediaPlayer;
	//更改组件！
    @FXML
    private RXFillButton bookroomBtn;
    @FXML
    private RXFillButton listBtn;
    @FXML
    private RXFillButton commentBtn;
    @FXML
    private RXFillButton DetialBtn;
    @FXML
    private Button rePlayBtn;
    @FXML
    private Slider slider;
    
    @FXML
    private Text Address;

    @FXML
    private Button NoSoundBtn;

    @FXML
    private Button playBtn;
    
    @FXML
    private TextArea Detial;

    @FXML
    private MediaView videoPlayer;
    
    private Connection con;//数据库
	@FXML private Text as1;
	@FXML private Text as2;
	@FXML private Text as3;
	@FXML private Text as4;
	@FXML private Text as5;
    @FXML private Text as6;
	@FXML private Text ScoreText;
	@FXML private Button returnbut;
    
    private String guestid="";
    
    void refresh()
    {
    	try
    	{
	    	Statement stmt = con.createStatement();
			String sql = "SELECT * from assess_table";
			ResultSet rs = stmt.executeQuery( sql );
			int jiaotong = 0;
			int tingche = 0;
			int sheshi = 0;
			int ganjing = 0;
			int jiage = 0;
			int fuwu = 0;
			double score = 0.0;
			
			while(rs.next()) 
			{   
				jiaotong = rs.getInt("jiaotong");
				tingche = rs.getInt("tingche");
				sheshi = rs.getInt("sheshi");
				ganjing = rs.getInt("ganjing");
				jiage = rs.getInt("jiage");
				fuwu = rs.getInt("fuwu");
				score = rs.getDouble("score");
			}
			BigDecimal tmp=new BigDecimal(score);
			double newScore=tmp.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
			
	    	as1.setText("交通遍历  " + jiaotong);
			as2.setText("停车方便 "+tingche);
			as3.setText("设施完善 "+sheshi);
			as4.setText("干净舒适 "+ganjing);
			as5.setText("价格实惠 "+jiage);
			as6.setText("服务良好 "+fuwu);
			if(newScore >= 4.0) {
				ScoreText.setText("评分："+newScore+"    非常好！");
			}
			else if(score >= 3.0) {
				ScoreText.setText("评分："+newScore+"    良好");
			}else {
				ScoreText.setText("评分："+newScore+"    一般");
			}
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    @FXML
    void handleQuit(ActionEvent event)
    {
    	try
    	{
    		Stage stage=(Stage)returnbut.getScene().getWindow();
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
    
    @FXML
    void handleComment(ActionEvent event)
    {
    	try
    	{
    		//css！
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Comment.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Comment.css");
			scene.getStylesheets().add(css.toExternalForm());
			//设置图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.setTitle("评价详情");
			stage.setResizable(false);
			stage.show();
			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void handleBook(ActionEvent event)
    {
    	try
    	{
    		//css！
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/BookRoom.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/BookRoom.css");
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
			stage.setTitle("订房");
			stage.show();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

    @FXML
    void gotoList(ActionEvent event) {
    	try
    	{
    		Statement sst=con.createStatement();
    		ResultSet rss=sst.executeQuery("SELECT * FROM book_table WHERE bguestid='"+guestid+"'");
    		int flag=0;
    		while (rss.next())
    			flag=1;
    		if (flag==0)   //没有订房记录
	    	{
    			Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("温馨提示");
	    		alert2.setHeaderText("");
	    		alert2.setContentText("您还没有消费记录噢^^");
	    		alert2.show();
	    		return;
    		}
    		//css！
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/MyOrders.fxml"));		
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/MyOrders.css");
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // 获取主屏幕的大小
			stage.setX(primaryScreenBounds.getMinX()); // 设置窗口的x坐标
			stage.setY(primaryScreenBounds.getMinY()); // 设置窗口的y坐标
			stage.setWidth(primaryScreenBounds.getWidth()); // 设置窗口的宽度
			stage.setHeight(primaryScreenBounds.getHeight()); // 设置窗口的高度
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setResizable(false);
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			stage.setTitle("我的订单");
			stage.show();
			
			stage.setOnHidden(eventa -> {			            
	            refresh();
	        });
			
			MyOrdersController oc=loader.getController();
			if (oc!=null) oc.setUser(guestid);
			else System.out.println("controller为空");
			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void videoPlay(ActionEvent event) {
		if(playBtn.getText().equals("播放"))
        {   
			
			mediaPlayer.play();
        	playBtn.setText("暂停");
        }
        else
        {   mediaPlayer.pause();
        	playBtn.setText("播放");
        }
    }

    @FXML
    void NoSound(ActionEvent event) {
    	if(NoSoundBtn.getText().equals("静音")) {
    		mediaPlayer.setMute(true);//设置静音播放
    		NoSoundBtn.setText("取消静音");
    	}
    	else {
    		mediaPlayer.setMute(false);//设置静音播放
    		NoSoundBtn.setText("静音");
    	}
    }


    @FXML
    void videoReplay(ActionEvent event) {
    	mediaPlayer.seek(Duration.ZERO);
//		mediaPlayer.play();
    	if(playBtn.getText().equals("播放"))
        {   
    		mediaPlayer.pause();
        	playBtn.setText("播放");
        }
        else
        {   
        	mediaPlayer.play();
        	playBtn.setText("暂停");
        }
    }
    @FXML
    void showDetial(ActionEvent event) {
    	try
    	{
    		//css！
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/HotelDetial.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/HotelDetial.css");
			scene.getStylesheets().add(css.toExternalForm());
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("酒店详情");
			stage.show();
			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
	//@Override public void initialize(URL arg0, ResourceBundle arg1) {
	void setUser(String user)
	{
		this.guestid=user;
		System.out.println("主页的user:"+user);
		
		// TODO Auto-generated method stub
//		String video = "file:///D:/hjm.mp4";
		//String video = getClass().getResource("resource/video.mp4").toString();
		// 创建一个媒体播放器和媒体视图
        //media = new Media(video);
		String myvideo = this.getClass().getResource("/resourcefile/video.mp4").toString();
        media = new Media(myvideo);
		mediaPlayer  = new MediaPlayer(media);
		videoPlayer.setMediaPlayer(mediaPlayer);
		//注释！
//		videoPlayer.setFitHeight(400);
//		videoPlayer.setFitWidth(600);

		slider.setMinWidth(30);
		slider.setPrefWidth(600);
		slider.setValue(0);
		mediaPlayer.setOnReady(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				slider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());//当前播放最长时间
			}
			
		});	
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				slider.setValue(mediaPlayer.getCurrentTime().toSeconds());//播放时间每次发生变化
			}
		});
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				double value = arg2.doubleValue();
				if(value == mediaPlayer.getCurrentTime().toSeconds()) {
					return;
				}
				mediaPlayer.seek(Duration.seconds(value));
			}
			
		});
		
			
		try 
			{        
	            String DBDriver="com.mysql.cj.jdbc.Driver";  // 数据库 MYSQL 装载
	            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
	            String DBUser="root", DBPassword="sysu";
	              		
				//加载驱动程序
				Class.forName(DBDriver);
				//通过驱动程序管理器建立与数据源的连接
	//			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
				con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
				
				Statement stmt = con.createStatement();
				String sql = "SELECT * from assess_table";
				ResultSet rs = stmt.executeQuery( sql );
	
				int id;
				int jiaotong = 0;
				int tingche = 0;
				int sheshi = 0;
				int ganjing = 0;
				int jiage = 0;
				int fuwu = 0;
				double score = 0.0;
				while(rs.next()) 
				{   
					jiaotong = rs.getInt("jiaotong");
					tingche = rs.getInt("tingche");
					sheshi = rs.getInt("sheshi");
					ganjing = rs.getInt("ganjing");
					jiage = rs.getInt("jiage");
					fuwu = rs.getInt("fuwu");
					score = rs.getDouble("score");
				}
				BigDecimal tmp=new BigDecimal(score);
	    		double newScore=tmp.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
				if(score >= 4.0) {
					ScoreText.setText("评分："+newScore+"    非常好！");
				}
				else if(score >= 3.0) {
					ScoreText.setText("评分："+newScore+"    良好");
				}else {
					ScoreText.setText("评分："+newScore+"    一般");
				}
				as1.setText("交通便捷  " + jiaotong);
				as2.setText("停车方便 "+tingche);
				as3.setText("设施齐全 "+sheshi);
				as4.setText("干净整洁 "+ganjing);
				as5.setText("价格实惠 "+jiage);
				as6.setText("服务良好 "+fuwu);
	
				
				//关闭对象。 
		        rs.close();
		        stmt.close();
	//	        con.close();
			}
			
			//找不到驱动程序，捕捉异常。如发生该错误，
			//请检查JDK版本是不是在1.1以上
			catch(ClassNotFoundException e) {
			    System.out.println("错误：" + e);
			}
			catch(SQLException e) {
			    System.out.println("错误：" + e);
			}

	}
}

