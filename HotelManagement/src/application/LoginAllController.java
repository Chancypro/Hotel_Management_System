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
	    		alert.setTitle("��ܰ��ʾ");
	    		alert.setHeaderText("");
	    		alert.setContentText("��ѡ��������ݣ�");
	    		alert.show();
	    		return;
	    	}
			
			if (selectedId.getText().equals("�ο�")==true)   //�ο͵�¼
			{
				//����css��
				System.out.println("�ο�");
				root=FXMLLoader.load(getClass().getResource("/application/GuestLogin.fxml"));
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/GuestLogin.css");
				scene.getStylesheets().add(css.toExternalForm());
				
				Stage primaryStage=new Stage();
				
				//���ô���ͼ�꣡
		        Image icon = new Image("/resourcefile/duck100.png");
		        primaryStage.getIcons().add(icon);
				
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.setTitle("�Ƶ�Ԥ��ƽ̨");
				primaryStage.show();
				
				Stage preStage=(Stage)yesBtn.getScene().getWindow();
				preStage.close();
			}
			else if (selectedId.getText().equals("����Ա")==true)  //����Ա��¼
			{
				System.out.println("����Ա");
				root=FXMLLoader.load(getClass().getResource("/application/Login.fxml"));  //���ص�¼ҳ��
				//������������˼ǵøĻ�ȥ��
				//����css�����ɸı��С
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/Login.css");
				scene.getStylesheets().add(css.toExternalForm());
				
				Stage primaryStage=new Stage();
				primaryStage.setResizable(false);
				//���ô���ͼ�꣡
				Image icon = new Image("/resourcefile/duck100.png");
				primaryStage.getIcons().add(icon);
				primaryStage.setScene(scene);
				primaryStage.setTitle("��¼����");
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

    //���Ӻ�����
    private RXCarouselPane initPane(int index, String uurl) {
		//����Ҫ��ͼƬ�������,�ŵ�RXCarouselPane ��;
        //Ϊ�˱��ָ��ѵ��л�Ч��,�������е�RXCarouselPane��RXCarousel��С����һ��
        RXCarouselPane pane1 = new RXCarouselPane();
        pane1.setStyle("-fx-background-image:"+uurl+";"+"-fx-background-size: cover;");
        System.out.println("yes!");
        //��������鷳. Ϊ�˸��õĶ���Ч�� ,����ʵ�������ĸ�����;�ֲ�ͼ��������ʵ���ʱ�򱻵���;
        pane1.setOnOpening(event -> {
            //System.out.println("���ڴ� Pane" + index + " is on opening..");
        });
        pane1.setOnOpened(event -> {
            //System.out.println("�Ѿ��� Pane" + index + " is on opened..");
        });

        pane1.setOnClosing(event -> {
            // System.out.println("���ڹر� Pane" + index + " is on closing..");
        });
        pane1.setOnClosed(event -> {
            //System.out.println("�Ѿ��ر� Pane" + index + " is on closed..");
        });
        return pane1;
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	      
        String[] colors = {"url('/resourcefile/1.png')", "url('/resourcefile/2.png')", "url('/resourcefile/3.png')"};
        for (int i = 0; i < colors.length; i++) {
            // ���RXCarouselPane �� RXCarousel��
            test.getPaneList().add(initPane(i, colors[i]));
        }
        //���ֲ�ͼ���ö�������;
        //CarouselAnimation �ǽӿ�. AnimVerBlinds��ʵ����
        //���е�ʵ���඼�� com.leewyatt.rxcontrols.animation.carousel.*;
        CarouselAnimation anim = new AnimFade();
        test.setCarouselAnimation(anim);
        //����ҳ���л��Ķ���ʱ��
        test.setAnimationTime(Duration.millis(1000));
        //���õ�����Ϊ��ʾ
        //carousel.setNavDisplayMode(DisplayMode.SHOW);
        //����ǰ�����˰�ťΪ ���������ʾ. ����Ƴ�����
        test.setArrowDisplayMode(DisplayMode.AUTO);
        //���õ���������ֲ�ͼʱֹͣ�Զ�����
        test.setHoverPause(true);
        //����ÿһҳ����ʾʱ��
        //carousel.setShowTime(Duration.millis(1500));
        //���ó�ʼѡ���±�Ϊ2��ҳ��
        //carousel.setSelectedIndex(2);
        //�����Զ�����/�л� �ֲ�ͼ
        test.setAutoSwitch(true);

        //������ں��ڴ�,��ô��һ������Ч�����õ�ʱ��,���Ե���dispose,����
        //anim.dispose(); //������ dispose������һ�㶼����ն���,����󶨵Ȳ���
        //anim = null;//Ȼ���ÿ�
        //System.gc();
	}

}
