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
//修改了确认添加那一块
//3修改  + 窗口名字修改
public class addGoodController implements Initializable{
	private Connection con;
    @FXML
    private TextField goodname;

    @FXML
    private ComboBox<String> goodtype;
    private ObservableList<String> goodtypeData = FXCollections.observableArrayList();

    @FXML
    private TextField goodprice;
    
    //组件更改！
    @FXML
    private RXFillButton checkBtn;

    @FXML
    private RXFillButton exitBtn;


    @FXML
    void exitBtnExit(ActionEvent event) {
    	try {
    		exitBtn.getScene().getWindow().hide();
    		
    		con.close();//修改1
    		//删除打开主窗口！
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
    	//异常判断  不能添加重名商品
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
    	
    	//空字符异常判断
    	if(name.equals("") || price.equals("")  ) {
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
    	if(!price.matches("\\d+")) {//修改2
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入正确格式的价格信息！");
    		alert.showAndWait();
    		return;
    	}
    	if(names.contains(name)) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("商品已存在！");
    		alert.showAndWait();
    		return;
    	}
    	
    	Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO food_table(fname,fprice,ftype) VALUES( '"+name+"',+"+price+",'"+type+"')";
			stmt.executeUpdate(sql);
			
			checkBtn.getScene().getWindow().hide();
			con.close();//修改3
			
//			System.out.println(1);
			//删除打开主窗口！
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
//			con.close();//修改3
//			
////			System.out.println(1);
//			//删除打开主窗口！
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
            String DBDriver="com.mysql.cj.jdbc.Driver";  // 数据库 MYSQL 装载
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
              		
			//加载驱动程序
			Class.forName(DBDriver);
			//通过驱动程序管理器建立与数据源的连接
//			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
//	        con.close();
			
			goodtypeData.add("餐饮类");
			goodtypeData.add("日用品类");
			goodtypeData.add("维修类");
			goodtype.setItems(goodtypeData);
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
