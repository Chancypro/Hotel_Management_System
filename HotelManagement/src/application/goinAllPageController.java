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
	//���������
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
    
    private Connection con;//���ݿ�
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
			
	    	as1.setText("��ͨ����  " + jiaotong);
			as2.setText("ͣ������ "+tingche);
			as3.setText("��ʩ���� "+sheshi);
			as4.setText("�ɾ����� "+ganjing);
			as5.setText("�۸�ʵ�� "+jiage);
			as6.setText("�������� "+fuwu);
			if(newScore >= 4.0) {
				ScoreText.setText("���֣�"+newScore+"    �ǳ��ã�");
			}
			else if(score >= 3.0) {
				ScoreText.setText("���֣�"+newScore+"    ����");
			}else {
				ScoreText.setText("���֣�"+newScore+"    һ��");
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
    		
    		Parent root=FXMLLoader.load(getClass().getResource("/application/LoginAll.fxml"));  //���ص�¼ҳ��
			//������������˼ǵøĻ�ȥ��
			//����css�����ɸı��С
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/LoginAll.css");
			scene.getStylesheets().add(css.toExternalForm());
			 //���ô���ͼ�꣡
	        Image icon = new Image("/resourcefile/duck100.png");
	        Stage primaryStage=new Stage();
	        primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("��¼����");
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
    		//css��
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Comment.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Comment.css");
			scene.getStylesheets().add(css.toExternalForm());
			//����ͼ�꣡
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.setTitle("��������");
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
    		//css��
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/BookRoom.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/BookRoom.css");
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
			stage.setTitle("����");
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
    		if (flag==0)   //û�ж�����¼
	    	{
    			Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("��ܰ��ʾ");
	    		alert2.setHeaderText("");
	    		alert2.setContentText("����û�����Ѽ�¼��^^");
	    		alert2.show();
	    		return;
    		}
    		//css��
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/MyOrders.fxml"));		
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/MyOrders.css");
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // ��ȡ����Ļ�Ĵ�С
			stage.setX(primaryScreenBounds.getMinX()); // ���ô��ڵ�x����
			stage.setY(primaryScreenBounds.getMinY()); // ���ô��ڵ�y����
			stage.setWidth(primaryScreenBounds.getWidth()); // ���ô��ڵĿ��
			stage.setHeight(primaryScreenBounds.getHeight()); // ���ô��ڵĸ߶�
			//���ô���ͼ�꣡
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setResizable(false);
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			stage.setTitle("�ҵĶ���");
			stage.show();
			
			stage.setOnHidden(eventa -> {			            
	            refresh();
	        });
			
			MyOrdersController oc=loader.getController();
			if (oc!=null) oc.setUser(guestid);
			else System.out.println("controllerΪ��");
			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void videoPlay(ActionEvent event) {
		if(playBtn.getText().equals("����"))
        {   
			
			mediaPlayer.play();
        	playBtn.setText("��ͣ");
        }
        else
        {   mediaPlayer.pause();
        	playBtn.setText("����");
        }
    }

    @FXML
    void NoSound(ActionEvent event) {
    	if(NoSoundBtn.getText().equals("����")) {
    		mediaPlayer.setMute(true);//���þ�������
    		NoSoundBtn.setText("ȡ������");
    	}
    	else {
    		mediaPlayer.setMute(false);//���þ�������
    		NoSoundBtn.setText("����");
    	}
    }


    @FXML
    void videoReplay(ActionEvent event) {
    	mediaPlayer.seek(Duration.ZERO);
//		mediaPlayer.play();
    	if(playBtn.getText().equals("����"))
        {   
    		mediaPlayer.pause();
        	playBtn.setText("����");
        }
        else
        {   
        	mediaPlayer.play();
        	playBtn.setText("��ͣ");
        }
    }
    @FXML
    void showDetial(ActionEvent event) {
    	try
    	{
    		//css��
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/HotelDetial.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/HotelDetial.css");
			scene.getStylesheets().add(css.toExternalForm());
			//���ô���ͼ�꣡
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("�Ƶ�����");
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
		System.out.println("��ҳ��user:"+user);
		
		// TODO Auto-generated method stub
//		String video = "file:///D:/hjm.mp4";
		//String video = getClass().getResource("resource/video.mp4").toString();
		// ����һ��ý�岥������ý����ͼ
        //media = new Media(video);
		String myvideo = this.getClass().getResource("/resourcefile/video.mp4").toString();
        media = new Media(myvideo);
		mediaPlayer  = new MediaPlayer(media);
		videoPlayer.setMediaPlayer(mediaPlayer);
		//ע�ͣ�
//		videoPlayer.setFitHeight(400);
//		videoPlayer.setFitWidth(600);

		slider.setMinWidth(30);
		slider.setPrefWidth(600);
		slider.setValue(0);
		mediaPlayer.setOnReady(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				slider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());//��ǰ�����ʱ��
			}
			
		});	
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				slider.setValue(mediaPlayer.getCurrentTime().toSeconds());//����ʱ��ÿ�η����仯
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
	            String DBDriver="com.mysql.cj.jdbc.Driver";  // ���ݿ� MYSQL װ��
	            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
	            String DBUser="root", DBPassword="sysu";
	              		
				//������������
				Class.forName(DBDriver);
				//ͨ�������������������������Դ������
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
					ScoreText.setText("���֣�"+newScore+"    �ǳ��ã�");
				}
				else if(score >= 3.0) {
					ScoreText.setText("���֣�"+newScore+"    ����");
				}else {
					ScoreText.setText("���֣�"+newScore+"    һ��");
				}
				as1.setText("��ͨ���  " + jiaotong);
				as2.setText("ͣ������ "+tingche);
				as3.setText("��ʩ��ȫ "+sheshi);
				as4.setText("�ɾ����� "+ganjing);
				as5.setText("�۸�ʵ�� "+jiage);
				as6.setText("�������� "+fuwu);
	
				
				//�رն��� 
		        rs.close();
		        stmt.close();
	//	        con.close();
			}
			
			//�Ҳ����������򣬲�׽�쳣���緢���ô���
			//����JDK�汾�ǲ�����1.1����
			catch(ClassNotFoundException e) {
			    System.out.println("����" + e);
			}
			catch(SQLException e) {
			    System.out.println("����" + e);
			}

	}
}

