package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import application.goodsController.goodTable;
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
//3���޸� + ���������޸�
public class editController implements Initializable{
	private Connection con;
	//���������
    @FXML
    private RXFillButton saveBtn;

    @FXML
    private RXFillButton quitBtn;
    @FXML
    private TextField goodname;

    @FXML
    private ComboBox<String> goodtype;
    private ObservableList<String> goodtypeData = FXCollections.observableArrayList();

    @FXML
    private TextField goodprice;
    @FXML
    private ComboBox<String> goodsBox;
    private ObservableList<String> goodboxs = FXCollections.observableArrayList();
    @FXML
    void saveEdit(ActionEvent event) {
    	String name = goodname.getText();
    	String price = goodprice.getText();
    	String type = goodtype.getValue();
    	
    	
    	if (goodsBox.getValue()==null)
    	{
    		System.out.println("ûѡ��Ʒ");
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("����");
    		alert.setHeaderText("");
    		alert.setContentText("��ѡ����Ʒ��");
    		alert.showAndWait();
    		return;
    	}
    	
    	if(name.equals("") || price.equals("")) {
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
    	if(!price.matches("\\d+")) {//�޸�1
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("����");
    		alert.setHeaderText("");
    		alert.setContentText("��������ȷ��ʽ�ļ۸���Ϣ��");
    		alert.showAndWait();
    		return;
    	}
    	
    	try
    	{
    		if(goodsBox.getValue().equals(name)==false) {
    			Statement stmtt = con.createStatement();
    	    	String sql = "SELECT fname FROM food_table";
    			ResultSet rs = stmtt.executeQuery( sql );
    			
    			while(rs.next()) 
    			{
    				if(rs.getString("fname").equals(name))
    				{
    		    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		    		alert.setTitle("����");
    		    		alert.setHeaderText("");
    		    		alert.setContentText("��Ʒ�Ѵ��ڣ�");
    		    		alert.showAndWait();
    		    		return;
    				}
    			}
    		}
    	}
    	catch(Exception e) {
		    System.out.println("����" + e);
		}
    	
    	try 
		{        
			
			String sql = "UPDATE food_table SET fname = ?, fprice = ?, ftype = ? WHERE fname = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setInt(2, Integer.parseInt(price));
			stmt.setString(3,type);
			stmt.setString(4,goodsBox.getValue());
			stmt.executeUpdate();
			
			//ɾ���������ڣ�
//			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
//			goodsController gc=loader.getController();
//			gc.refresh();
		}		
		catch(Exception e) {
		    System.out.println("����" + e);
		}
    	
    	try {
			con.close();//�޸�2
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	saveBtn.getScene().getWindow().hide();
    }

    @FXML
    void exit(ActionEvent event) {
    	try {
    		quitBtn.getScene().getWindow().hide();
    		
    		con.close();//�ر����ݿ�  �޸�3
    		//ɾ���������ڣ�
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
			loader.load();
			goodsController gc=loader.getController();
			gc.refresh();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
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
			
			Statement stmt = con.createStatement();
			String sql = "SELECT fname from food_table";
			ResultSet rs = stmt.executeQuery( sql );
			String name;
			while(rs.next()) 
			{   
				name = rs.getString("fname");
				goodboxs.add(name);
				
			}
			goodsBox.setItems(goodboxs);
			
			goodtypeData.add("������");
			goodtypeData.add("����Ʒ��");
			goodtypeData.add("ά����");
			goodtype.setItems(goodtypeData);
			
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
