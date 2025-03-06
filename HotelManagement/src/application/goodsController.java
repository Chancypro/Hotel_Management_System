package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXTranslationButton;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
//115行加一个下拉控件限制
//7修改
public class goodsController {//implements Initializable{
	private Connection con;//数据库	  有一个问题 数据库一直开着什么时候关掉？
	 private String userName;
	    
    void setUser1(String userName)
    {
    	this.userName=userName;
    	refresh();
    }
	//组件更改！
    @FXML
    private RXTranslationButton add;

    @FXML
    private RXTranslationButton edit;

    @FXML
    private RXTranslationButton delete;
    
    @FXML
    private RXTranslationButton searchBtn;
    
    @FXML
    private TextField goodsname;
    @FXML
    private TableView<goodTable> goodstable;
    
    @FXML
    private Button returnBtn4;
//    @FXML
//    public TableColumn<goodTable, Boolean> selectcol; 
    
    @FXML
    private TableColumn<goodTable, String> goodnamecol;
    @FXML
    private TableColumn<goodTable, Integer> pricecol;
    @FXML
    private TableColumn<goodTable, String> goodtypecol; 
    private ObservableList<goodTable> goodData = FXCollections.observableArrayList();
    
    @FXML
    private ComboBox<String> showgood;
    private ObservableList<String> goodbox = FXCollections.observableArrayList();
    
    @FXML
    void handleReturn4()
    {
    	try
    	{
    		//con.close();//修改1
    		//css！
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Stage.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Stage.css");
			scene.getStylesheets().add(css.toExternalForm());
			
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setTitle("酒店管理系统");
			stage.show();
			
			StageController sc=loader.getController();
			sc.setUser(userName);
			
			Stage preStage=(Stage)returnBtn4.getScene().getWindow();
			preStage.close();
			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    public void handleComboBoxAction(ActionEvent event) {//下拉控件改变
//    	String selectedValue = showgood.getValue();
//    	testField.setText(showgood.getValue());
    	if(showgood.getSelectionModel().getSelectedItem() == null) {
    		return;
    	}
    	goodsname.clear();
//    	if(!showgood.getValue().equals("")) {/////////////////////////////////修改1
    	if(showgood.getSelectionModel().getSelectedItem() != null) {
    		if(showgood.getValue().equals("全部类别")) {
        		goodData.clear();
        		try 
        		{      
        			Statement stmt = con.createStatement();
        			String sql = "SELECT fname,fprice,ftype from food_table";
        			ResultSet rs = stmt.executeQuery( sql );
        			String name;
        			int price;
        			String type;
        			while(rs.next()) 
        			{   
        				name = rs.getString("fname");
        				price = rs.getInt("fprice");
        				type = rs.getString("ftype");
//        				goodData.add(new goodTable(false,name,price,type));//	
        				goodData.add(new goodTable(name,price,type));
        			}
//        			goodnamecol; pricecol; goodtypecol;往表格中写内容
//        			selectcol.setCellValueFactory(new PropertyValueFactory<>("selected"));//
        			goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));//将列与数据模型中的数据属性绑定
        			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        			goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
        			goodstable.setItems(goodData);			
        			//关闭对象。 
        	        rs.close();
        	        stmt.close();
        		}
        		
        		catch(SQLException e) {
        		    System.out.println("错误：" + e);
        		}
        	}
        	else {
        		goodData.clear();
            	//数据库查找 当fname = showgood.getValue()
        		try {
        			Statement st = con.createStatement();
        			ResultSet rs = st.executeQuery("SELECT fname,fprice,ftype FROM food_table WHERE fname = '"+showgood.getValue()+"'");
        			while(rs.next()) 
        			{   
        				String name = rs.getString("fname");
        				int price = rs.getInt("fprice");
        				String type = rs.getString("ftype");
//        				goodData.add(new goodTable(false,name,price,type));	//	
        				goodData.add(new goodTable(name,price,type));
        			}
//        			goodnamecol; pricecol; goodtypecol;往表格中写内容
//        			selectcol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        			goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        			goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
        			goodstable.setItems(goodData);
        			
        			rs.close();
        	        st.close();
//        	        con.close();
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}   	
    	} 	
    }

    void refresh1()
    {
//    	System.out.println(1);
//    	try 
//		{   
//    		System.out.println(2);
//		
//    		goodstable.getItems().clear();
//    		goodData.clear();
//    		goodbox.clear();
//    		goodbox.add("全部类别");
//			Statement stmt = con.createStatement();
//			String sql = "SELECT fname,fprice,ftype from food_table";
//			ResultSet rs = stmt.executeQuery( sql );
//					
//			while(rs.next()) 
//			{   
//				String name = rs.getString("fname");
//				int price = rs.getInt("fprice");
//				String type = rs.getString("ftype");
////				goodData.add(new goodTable(false,name,price,type));//	
//				System.out.println(name+" "+price+" "+type);
//				goodData.add(new goodTable(name,price,type));
//				goodbox.add(name);
//			}
//			showgood.setItems(goodbox);
////			selectcol.setCellValueFactory(new PropertyValueFactory<>("selected"));//
//			goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));//将列与数据模型中的数据属性绑定
//			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
//			goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
//			goodstable.setItems(goodData);			
//			//关闭对象。 
//	        rs.close();
//	        stmt.close();
//		}
//		
//		catch(SQLException e) {
//		    System.out.println("错误：" + e);
//		}	
    	try 
		{      
    		Statement stmt=con.createStatement();
			goodData.clear();
			goodbox.clear();//修改5 
			String sql2 = "SELECT fname,fprice,ftype from food_table";
			ResultSet rs = stmt.executeQuery( sql2 );
			while(rs.next()) 
			{   
				String name = rs.getString("fname");
				int price = rs.getInt("fprice");
				String type = rs.getString("ftype");
//				goodData.add(new goodTable(false,name,price,type));//	
				goodData.add(new goodTable(name,price,type));
				goodbox.add(name);
			}
//			goodnamecol; pricecol; goodtypecol;往表格中写内容
//			selectcol.setCellValueFactory(new PropertyValueFactory<>("selected"));//
			goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));//将列与数据模型中的数据属性绑定
			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
			goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
			goodstable.setItems(goodData);	
			showgood.setItems(goodbox);//修改6
			//关闭对象。 
	        rs.close();
	        stmt.close();
		}
		
		catch(SQLException e) {
		    System.out.println("错误：" + e);
		}
    }
    
    @FXML
    void addGood(ActionEvent event) {
//    	testField.setText("添加商品"+goodsname.getText());
    	try {
    		//配置css！不可改变大小    
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("addGood.fxml"));
			Parent root = loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/addGood.css");
			scene.getStylesheets().add(css.toExternalForm());
            Stage sta = new Stage();
          //设置窗口图标！
            Image icon = new Image("/resourcefile/duck100.png");
            sta.getIcons().add(icon);
            sta.setResizable(false);
            sta.setTitle("添加商品");//修改2
            sta.setScene(scene);	            
            sta.show();	
	            //删除关闭主窗口！
	            sta.setOnHidden(eventa -> {			            
		            refresh();
		        });
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void editGood(ActionEvent event) {
//    	testField.setText("编辑商品"+goodsname.getText());
//    	goodTable data = goodstable.getSelectionModel().getSelectedItem();
//        setData(data);
//        setSelectedData(goodstable.getSelectionModel().getSelectedItem());
    	
    	try {    		
	    		//配置css！不可改变大小    		
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
				Parent root = loader.load();
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/edit.css");
				scene.getStylesheets().add(css.toExternalForm());
	            Stage stage = new Stage();
	            //设置窗口图标！
	            Image icon = new Image("/resourcefile/duck100.png");
	            stage.getIcons().add(icon);
	            stage.setResizable(false);
	            stage.setTitle("修改商品");//修改3
	            stage.setScene(scene);
	            stage.show();
	            
	            stage.setOnHidden(eventa -> {			            
		            refresh();
		        });
	            //删除关闭主窗口！
		} catch(Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void deleteGood(ActionEvent event) {
    	
    	if(showgood.getSelectionModel().getSelectedItem()!=null) {
			showgood.getSelectionModel().clearSelection();
		}//修改7
    	
    	goodTable selectedgood = goodstable.getSelectionModel().getSelectedItem();
//    	Boolean checked= selectcol.getCellData(selectedgood);//获取第一栏状态
    	String goodname = goodnamecol.getCellData(selectedgood);
//    	System.out.println(name);
    	
//    	testField.setText("删除商品"+goodsname.getText());
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//修改4
    	alert.setTitle("提示");
    	alert.setHeaderText("");
    	if(goodname == null) {
    		alert.setContentText("请选择要删除的商品！");
    	}
    	else {
    		alert.setContentText("确定要删除商品：<"+goodname +"> 吗？");
    	}
    	
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.CANCEL) {
    		return ;
    	}
    	else {
    		/*@FXML
    private ComboBox<String> showgood;
    private ObservableList<String> goodbox = FXCollections.observableArrayList();*/
    		try 
    		{      
    			Statement stmt = con.createStatement();
    			String sql = "DELETE from food_table WHERE fname = '"+goodname+"'";
    			stmt.executeUpdate(sql);
    			goodData.clear();
    			goodbox.clear();//修改5 
    			String sql2 = "SELECT fname,fprice,ftype from food_table";
    			ResultSet rs = stmt.executeQuery( sql2 );
    			while(rs.next()) 
    			{   
    				String name = rs.getString("fname");
    				int price = rs.getInt("fprice");
    				String type = rs.getString("ftype");
//    				goodData.add(new goodTable(false,name,price,type));//	
    				goodData.add(new goodTable(name,price,type));
    				goodbox.add(name);
    			}
//    			goodnamecol; pricecol; goodtypecol;往表格中写内容
//    			selectcol.setCellValueFactory(new PropertyValueFactory<>("selected"));//
    			goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));//将列与数据模型中的数据属性绑定
    			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
    			goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
    			goodstable.setItems(goodData);	
    			showgood.setItems(goodbox);//修改6
    			//关闭对象。 
    	        rs.close();
    	        stmt.close();
    		}
    		
    		catch(SQLException e) {
    		    System.out.println("错误：" + e);
    		}
    	}
    		
    }
    
    @FXML
    void searchGoodsBtn(ActionEvent event) {//搜索商品按钮被按下
    	
    	if(showgood.getSelectionModel().getSelectedItem()!=null) {
    		showgood.getSelectionModel().clearSelection();
		}
    	
    	goodData.clear();
    	try 
		{      
			Statement stmt = con.createStatement();
			String sql = "SELECT fname,fprice,ftype from food_table";
			ResultSet rs = stmt.executeQuery( sql );
			
			while(rs.next()) 
			{   				
				String name = rs.getString("fname");
				int price = rs.getInt("fprice");
				String type = rs.getString("ftype");
				if(name.contains(goodsname.getText())) {
//					goodData.add(new goodTable(false,name,price,type));//
					goodData.add(new goodTable(name,price,type));
//					System.out.println(goodsname.getText());
				}	
			}
//			goodnamecol; pricecol; goodtypecol;往表格中写内容
//			selectcol.setCellValueFactory(new PropertyValueFactory<>("selected"));//
			goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));//将列与数据模型中的数据属性绑定
			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
			goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
			goodstable.setItems(goodData);			
			//关闭对象。 
	        rs.close();
	        stmt.close();
		}
		
		catch(SQLException e) {
		    System.out.println("错误：" + e);
		}
    }
    
    public class goodTable{
//    	private SimpleBooleanProperty selected;//
    	private SimpleStringProperty name;
    	private SimpleIntegerProperty price;
    	private SimpleStringProperty goodtype;
    	public goodTable() {}
//    	public goodTable(boolean se,String gn,int p,String gType) {//
    	public goodTable(String gn,int p,String gType) {//
//    		this.selected = new SimpleBooleanProperty(se);//
    		this.name = new SimpleStringProperty(gn);
    		this.price = new SimpleIntegerProperty(p);
    		this.goodtype = new SimpleStringProperty(gType);
    	}
//   	
//		public boolean getSelected() {
//			return selected.get();
//		}
/*    	public boolean isChecked() {
            return selected.get();
        }
    	
		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}
		public SimpleBooleanProperty selectedProperty() {
            return selected;
        }
*/		
		public String getName() {
			return name.get();
		}
		public void setName(String name) {
			this.name.set(name);
		}
		public int getPrice() {
			return price.get();
		}
		public void setPrice(int price) {
			this.price.set(price);
		}
		public String getGoodtype() {
			return goodtype.get();
		}
		public void setGoodtype(String goodtype) {
			this.goodtype.set(goodtype);
		}
    }

	//@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
	public void refresh() {

		System.out.println(1111);
		goodData.clear();
		goodstable.getItems().clear();
		showgood.getItems().clear();
		goodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
		pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
		goodtypecol.setCellValueFactory(new PropertyValueFactory<>("goodtype"));
//		System.out.println(2);
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
			
			Statement stmt = con.createStatement();
			String sql = "SELECT fname,fprice,ftype from food_table";
			ResultSet rs = stmt.executeQuery( sql );
			String name;
			int price;
			String type;
			goodbox.add("全部类别");
			
//			System.out.println(3);
			while(rs.next()) 
			{   
				name = rs.getString("fname");
				price = rs.getInt("fprice");
				type = rs.getString("ftype");
//				goodData.add(new goodTable(false,name,price,type));
				goodData.add(new goodTable(name,price,type));
				goodbox.add(name);
				
			}
//			System.out.println(4);
			showgood.setItems(goodbox);
			
			goodstable.setItems(goodData);
//			System.out.println(555);
			
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






