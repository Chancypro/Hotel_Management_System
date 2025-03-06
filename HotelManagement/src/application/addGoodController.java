package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import application.goodsController.goodTable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//�޸���ȷ�������һ��
//3�޸�  + ���������޸�
public class addGoodController implements Initializable{
	private Connection con;
    @FXML
    private TextField goodname;

    @FXML
    private ComboBox<String> goodtype;
    private ObservableList<String> goodtypeData = FXCollections.observableArrayList();

    @FXML
    private TextField goodprice;
    
    //������ģ�
    @FXML
    private RXFillButton checkBtn;

    @FXML
    private RXFillButton exitBtn;


    @FXML
    void exitBtnExit(ActionEvent event) {
    	try {
    		exitBtn.getScene().getWindow().hide();
    		
    		con.close();//�޸�1
    		//ɾ���������ڣ�
//			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
//			goodsController gc=loader.getController();
//			gc.refresh();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
    }

    @FXML
    void ingoodinfor(ActionEvent event) {
    	String name = goodname.getText();
    	String price = goodprice.getText();
    	String type = goodtype.getValue();
    	
    	List<String> names = new ArrayList<>();
    	//�쳣�ж�  �������������Ʒ
    	try {
    		Statement stmt = con.createStatement();
    		String sql = "SELECT fname FROM food_table";
    		ResultSet rs = stmt.executeQuery( sql );
    		
    		while(rs.next()) 
    		{   
//    			String room = rs.getString("fname");	
    			names.add(rs.getString("fname"));
    		}
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
    	
    	//���ַ��쳣�ж�
    	if(name.equals("") || price.equals("")  ) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("����");
    		alert.setHeaderText("");
    		alert.setContentText("������Ϸ���Ϣ��");
    		alert.showAndWait();
    		return;
    	}
    	if(goodtype.getValue()==null) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("����");
    		alert.setHeaderText("");
    		alert.setContentText("��ѡ����Ʒ���ͣ�");
    		alert.showAndWait();
    		return;
    	}
    	if(!price.matches("\\d+")) {//�޸�2
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("����");
    		alert.setHeaderText("");
    		alert.setContentText("��������ȷ��ʽ�ļ۸���Ϣ��");
    		alert.showAndWait();
    		return;
    	}
    	if(names.contains(name)) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("����");
    		alert.setHeaderText("");
    		alert.setContentText("��Ʒ�Ѵ��ڣ�");
    		alert.showAndWait();
    		return;
    	}
    	
    	Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO food_table(fname,fprice,ftype) VALUES( '"+name+"',+"+price+",'"+type+"')";
			stmt.executeUpdate(sql);
			
			checkBtn.getScene().getWindow().hide();
			con.close();//�޸�3
			
//			System.out.println(1);
			//ɾ���������ڣ�
//			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
//			loader.load();
//			goodsController gc=loader.getController();
//			gc.refresh();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
//		try {
//			
//			checkBtn.getScene().getWindow().hide();
//			
//			con.close();//�޸�3
//			
////			System.out.println(1);
//			//ɾ���������ڣ�
//			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
//			loader.load();
//			goodsController gc=loader.getController();
//
////			gc.refresh();
//
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();			
//		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
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
//	        con.close();
			
			goodtypeData.add("������");
			goodtypeData.add("����Ʒ��");
			goodtypeData.add("ά����");
			goodtype.setItems(goodtypeData);
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
