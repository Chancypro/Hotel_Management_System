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
//3处修改 + 窗口名字修改
public class editController implements Initializable{
	private Connection con;
	//更改组件！
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
    		System.out.println("没选商品");
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请选择商品！");
    		alert.showAndWait();
    		return;
    	}
    	
    	if(name.equals("") || price.equals("")) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入合法信息！");
    		alert.showAndWait();
    		return;
    	}
    	if(goodtype.getValue()==null) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请选择商品类型！");
    		alert.showAndWait();
    		return;
    	}
    	if(!price.matches("\\d+")) {//修改1
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入正确格式的价格信息！");
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
    		    		alert.setTitle("警告");
    		    		alert.setHeaderText("");
    		    		alert.setContentText("商品已存在！");
    		    		alert.showAndWait();
    		    		return;
    				}
    			}
    		}
    	}
    	catch(Exception e) {
		    System.out.println("错误：" + e);
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
			
			//删除打开主窗口！
//			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/goods.fxml"));
//			goodsController gc=loader.getController();
//			gc.refresh();
		}		
		catch(Exception e) {
		    System.out.println("错误：" + e);
		}
    	
    	try {
			con.close();//修改2
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
    		
    		con.close();//关闭数据库  修改3
    		//删除打开主窗口！
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
            String DBDriver="com.mysql.cj.jdbc.Driver";  // 数据库 MYSQL 装载
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
              		
			//加载驱动程序
			Class.forName(DBDriver);
			//通过驱动程序管理器建立与数据源的连接
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
			
			goodtypeData.add("餐饮类");
			goodtypeData.add("日用品类");
			goodtypeData.add("维修类");
			goodtype.setItems(goodtypeData);
			
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
