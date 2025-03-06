package application;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class AddController implements Initializable{

	@FXML
    private AnchorPane back;

    @FXML
    private Label title;

    @FXML
    private Label idlabel;

    @FXML
    private TextField idfield;

    @FXML
    private Label levellabel;

    @FXML
    private RXFillButton savebut;

    @FXML
    private RXFillButton canclebut;

    @FXML
    private ComboBox<String> levelcom;
    private ObservableList<String> levelboxs = FXCollections.observableArrayList();


    @FXML
    void savehandler(ActionEvent event) {

    	String id = idfield.getText();
    	if (id.length()==0)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("��ܰ��ʾ");
    		alert.setHeaderText("");
    		alert.setContentText("�����뷿��ţ�");
    		alert.show();
    		return;
    	}
    	
    	try    //�ж��Ƿ�Ϊ���з����
    	{
			connection=DriverManager.getConnection(urls,user,pwd);
			stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM room_table");
			while (rs.next())
			{
				if (rs.getString("rid").equals(id)==true)
				{
					Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("��ܰ��ʾ");
		    		alert.setHeaderText("");
		    		alert.setContentText("�÷�����Ѵ��ڣ�");
		    		alert.show();
		    		return;
				}
			}
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	
    	if (id.length()<3||id.length()>4)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("��ܰ��ʾ");
    		alert.setHeaderText("");
    		alert.setContentText("������Ϸ�����ţ�");
    		alert.show();
    		return;
    	}
    	if (id.length()==3)
    	{
    		int flag=0;
    		if (id.charAt(1)=='0'&&id.charAt(2)=='0') flag=1;  //���Ϸ�
    		if (id.charAt(1)>'3') flag=1;
    		if (flag==1)
    		{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("��ܰ��ʾ");
	    		alert.setHeaderText("");
	    		alert.setContentText("������Ϸ�����ţ�");
	    		alert.show();
	    		return;
    		}
    	}
    	if (id.length()==4)
    	{
    		int flag=0;
    		if (id.charAt(2)=='0'&&id.charAt(3)=='0') flag=1;  //���Ϸ�
    		if (id.charAt(2)>'3') flag=1;
    		if (flag==1)
    		{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("��ܰ��ʾ");
	    		alert.setHeaderText("");
	    		alert.setContentText("������Ϸ�����ţ�");
	    		alert.show();
	    		return;
    		}
    	}
    	if (levelcom.getValue()==null)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("��ܰ��ʾ");
    		alert.setHeaderText("");
    		alert.setContentText("��ѡ�񷿼�ȼ���");
    		alert.show();
    		return;
    	}
    	
    	String type = levelcom.getSelectionModel().getSelectedItem();
    	int area=0,price=0;
		try {
			//ע�����ﱣ����rstatus���Ѹ�
			if (type.equals("�󴲷�")==true) {area=20;price=100;}
			else if (type.equals("˫����")==true) {area=30;price=200;}
			else if (type.equals("�������˼�")==true) {area=25;price=200;}
			else if (type.equals("����˫����")==true) {area=35;price=350;}
			else if (type.equals("��ͳ�׷�")==true) {area=120;price=800;}
			
			String sql = "INSERT INTO room_table(rid, rprice, rtype,rarea) VALUES ('" + id + "', " + price + ", '" + type + "', " + area + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		savebut.getScene().getWindow().hide();
    }

    @FXML
    void canclehandler(ActionEvent event) {
    	if(!idfield.getText().isEmpty()&&levelcom.getValue()!=null)
    	{
    		idfield.clear();
    		levelcom.setValue(null);
    	}
    	else if(idfield.getText().isEmpty()&&levelcom.getValue()!=null)
    	{   		
    		levelcom.setValue(null);
    	}
    	else if(levelcom.getValue()==null&&!idfield.getText().isEmpty())
    	{
    		idfield.clear();
    	}
    	else
    	{
    		canclebut.getScene().getWindow().hide();
    	}
    	
    }
    
    private String urls="jdbc:mysql://localhost:3306/hoteldatabase?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private String user="root";
    private String pwd="sysu";
    private Connection connection;
    private Statement stmt;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//�������ݿ�
		try {
			connection=DriverManager.getConnection(urls,user,pwd);
			stmt=connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ö�ѡ��
		levelboxs.add("˫����");
		levelboxs.add("�󴲷�");
		levelboxs.add("��ͳ�׷�");
		levelboxs.add("�������˼�");
		levelboxs.add("����˫����");
		levelcom.setItems(levelboxs);
	}

}


