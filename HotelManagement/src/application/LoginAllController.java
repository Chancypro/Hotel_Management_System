package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.animation.carousel.AnimFade;
import com.leewyatt.rxcontrols.animation.carousel.CarouselAnimation;
import com.leewyatt.rxcontrols.controls.RXCarousel;
import com.leewyatt.rxcontrols.controls.RXFillButton;
import com.leewyatt.rxcontrols.enums.DisplayMode;
import com.leewyatt.rxcontrols.pane.RXCarouselPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class LoginAllController implements Initializable{

    @FXML
    private ToggleGroup id;

    @FXML
    private RXFillButton yesBtn;

    @FXML
    private RXCarousel test;


    @FXML
    void handleYes(ActionEvent event) {
    	try
		{
			Parent root;
			RadioButton selectedId=(RadioButton) id.getSelectedToggle();
			
			if (selectedId==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请选择您的身份！");
	    		alert.show();
	    		return;
	    	}
			
			if (selectedId.getText().equals("游客")==true)   //游客登录
			{
				//加载css！
				System.out.println("游客");
				root=FXMLLoader.load(getClass().getResource("/application/GuestLogin.fxml"));
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/GuestLogin.css");
				scene.getStylesheets().add(css.toExternalForm());
				
				Stage primaryStage=new Stage();
				
				//设置窗口图标！
		        Image icon = new Image("/resourcefile/duck100.png");
		        primaryStage.getIcons().add(icon);
				
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.setTitle("酒店预定平台");
				primaryStage.show();
				
				Stage preStage=(Stage)yesBtn.getScene().getWindow();
				preStage.close();
			}
			else if (selectedId.getText().equals("管理员")==true)  //管理员登录
			{
				System.out.println("管理员");
				root=FXMLLoader.load(getClass().getResource("/application/Login.fxml"));  //加载登录页面
				//这里改来测试了记得改回去！
				//配置css！不可改变大小
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/Login.css");
				scene.getStylesheets().add(css.toExternalForm());
				
				Stage primaryStage=new Stage();
				primaryStage.setResizable(false);
				//设置窗口图标！
				Image icon = new Image("/resourcefile/duck100.png");
				primaryStage.getIcons().add(icon);
				primaryStage.setScene(scene);
				primaryStage.setTitle("登录窗口");
				primaryStage.show();
				
				Stage preStage=(Stage)yesBtn.getScene().getWindow();
				preStage.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }

    //增加函数！
    private RXCarouselPane initPane(int index, String uurl) {
		//把需要的图片或者组件,放到RXCarouselPane 里;
        //为了保持更佳的切换效果,建议所有的RXCarouselPane和RXCarousel大小保持一致
        RXCarouselPane pane1 = new RXCarouselPane();
        pane1.setStyle("-fx-background-image:"+uurl+";"+"-fx-background-size: cover;");
        System.out.println("yes!");
        //如果不怕麻烦. 为了更好的动画效果 ,可以实现下面四个方法;轮播图组件会在适当的时候被调用;
        pane1.setOnOpening(event -> {
            //System.out.println("正在打开 Pane" + index + " is on opening..");
        });
        pane1.setOnOpened(event -> {
            //System.out.println("已经打开 Pane" + index + " is on opened..");
        });

        pane1.setOnClosing(event -> {
            // System.out.println("正在关闭 Pane" + index + " is on closing..");
        });
        pane1.setOnClosed(event -> {
            //System.out.println("已经关闭 Pane" + index + " is on closed..");
        });
        return pane1;
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	      
        String[] colors = {"url('/resourcefile/1.png')", "url('/resourcefile/2.png')", "url('/resourcefile/3.png')"};
        for (int i = 0; i < colors.length; i++) {
            // 添加RXCarouselPane 到 RXCarousel里
            test.getPaneList().add(initPane(i, colors[i]));
        }
        //给轮播图设置动画类型;
        //CarouselAnimation 是接口. AnimVerBlinds是实现类
        //所有的实现类都在 com.leewyatt.rxcontrols.animation.carousel.*;
        CarouselAnimation anim = new AnimFade();
        test.setCarouselAnimation(anim);
        //设置页面切换的动画时间
        test.setAnimationTime(Duration.millis(1000));
        //设置导航条为显示
        //carousel.setNavDisplayMode(DisplayMode.SHOW);
        //设置前进后退按钮为 鼠标移入显示. 鼠标移除隐藏
        test.setArrowDisplayMode(DisplayMode.AUTO);
        //设置当鼠标移入轮播图时停止自动播放
        test.setHoverPause(true);
        //设置每一页的显示时间
        //carousel.setShowTime(Duration.millis(1500));
        //设置初始选中下标为2的页面
        //carousel.setSelectedIndex(2);
        //设置自动播放/切换 轮播图
        test.setAutoSwitch(true);

        //如果很在乎内存,那么在一个动画效果不用的时候,可以调用dispose,销毁
        //anim.dispose(); //动画的 dispose方法里一般都是清空动画,解除绑定等操作
        //anim = null;//然后置空
        //System.gc();
	}

}
