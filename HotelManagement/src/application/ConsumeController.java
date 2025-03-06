package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import application.goodsController.goodTable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//9处修改 + 窗口名字修改
public class ConsumeController implements Initializable{
	private Connection con;//数据库
    //组件更改！
	
    private String userName;
    
    void setUser1(String userName)
    {
    	this.userName=userName;
    }
 	@FXML
    private RXFillButton checkNumBtn;

    @FXML
    private RXFillButton checkBookBtn;

    @FXML
    private RXFillButton delete;

    @FXML
    private ScrollPane scroll;
    
    @FXML
    private TableView<Book> bookview;
    @FXML
    private TableColumn<Book, Integer> numcol;
    @FXML
    private TableColumn<Book, Integer> cidcol;
    @FXML
    private TableColumn<Book, String> roomidcol;
    @FXML
    private TableColumn<Book, Double> pricecol;
    @FXML
    private TableColumn<Book, Integer> daycol;
    @FXML
    private TableColumn<Book, String> goodcol;
    @FXML
    private TableColumn<Book, Integer> monthcol;
    @FXML
    private TableColumn<Book, Integer> yearcol;
    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    @FXML
    private TextField num;
    
    @FXML
    private Button returnBtn5;

    
    @FXML
    private Text orderTime;

    @FXML
    private ComboBox<String> goodsBox;
    private ObservableList<String> goodbox = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> roomid;
    private ObservableList<String> roomdata = FXCollections.observableArrayList();

    
    @FXML//18
    private ComboBox<String> saleBox;
    private ObservableList<String> saleData = FXCollections.observableArrayList();
    
    @FXML
    private TextField saleText;//修改14
    @FXML
    private Text SumSetText;//修改10

    @FXML
    void handleReturn5()
    {
    	try
    	{
    		con.close();//修改1
    		
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
			stage.setScene(scene);
			stage.setTitle("酒店管理系统");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			Stage preStage=(Stage)returnBtn5.getScene().getWindow();
			preStage.close();
			
			StageController sc=loader.getController();
			sc.setUser(userName);
    	}
    	catch(Exception e)
    	{
//    		System.out.println(e);
    		e.printStackTrace();
    	}
    }
    
//    private String []days = new String[] {"31","28","31","30","31","30","31","31","30","31","30","31"};
//    @FXML
//    void monthChange(ActionEvent event) {
//    	if(month.getSelectionModel().getSelectedItem()!=null) {//修改2 加一个非空判断
//    		int chosenmon = Integer.parseInt(month.getValue());
//        	String daynum = days[chosenmon-1];
//        	dayshow.clear();
//        	for(int i=1;i<=Integer.parseInt(daynum);i++) {
//        		dayshow.add(String.valueOf(i));
//        	}
//        	day.setItems(dayshow);
//    	}
//    	else return;
//    }
    
    
    @FXML
    void priceSumSet(ActionEvent event) {//当数量框数据变化的时候   修改11  checkNumBtn
    	String numm = num.getText();//int
    	String good = goodsBox.getValue();
    	
//		if(saleBox.getSelectionModel().getSelectedItem()!=null) {
//			System.out.println("2222222");
//			
//			saleBox.getSelectionModel().clearSelection();
//			
//			System.out.println("3333333");
//		}
    	if(numm.length()==0) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请先输入商品数量！");
    		alert.showAndWait();
    		return;
    	}
    	if(!numm.matches("\\d+")) {//修改4输入数量全为数字
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入正确格式的数量信息！");
    		alert.showAndWait();
    		return;
    	}
    	boolean ifzero = true;
		for(int i=0;i<numm.length();i++) {//修改5价格可以为零
			if(numm.charAt(i) != '0') {
				ifzero = false;
				break;
			}
		}
		if( ifzero) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入合法信息！");
    		alert.showAndWait();
    		return;
    	}
		
		
    	try 
		{        
			Statement stmt = con.createStatement();
			String sql = "SELECT fprice from food_table WHERE fname = '"+good+"'";
			ResultSet rs = stmt.executeQuery( sql );
			int price = 0;
			while(rs.next()) 
			{   
				price = rs.getInt("fprice");
				
			}
			double sum = Integer.parseInt(numm) * price;
			SumSetText.setText(String.valueOf(sum));
			
			//关闭对象。 
	        rs.close();
	        stmt.close();
//	        con.close();
		}
		catch(SQLException e) {
//		    System.out.println("错误：" + e);
			e.printStackTrace();
		}
    	///////////////////////////////////////////////////////////
    	if(!SumSetText.getText().equals("")) {//总价有值
    		if(saleBox.getValue()!=null) {
    			String sale = saleBox.getValue();
        		if(sale.equals("免费")) {
            		saleText.setText("0.0");
            	}
            	else if(sale.equals("不打折")) {
            		saleText.setText(SumSetText.getText());
            	}
            	else {
            		String num = sale.substring(0, 1);
            		int n = Integer.parseInt(num);
            		double price = 0;
            		price = Double.parseDouble(SumSetText.getText()) * n / 10.0;
            		saleText.setText(Double.toString(price));
            	}
    		}
		}
    }
   
    @FXML
    void SetSale(ActionEvent event) {///19  打折下拉控件改变
//    	if(saleBox.getSelectionModel().getSelectedItem()==null)   return ;
//    	
//    	if(SumSetText.getText().length()==0){
//			
//    		System.out.println("11111");
////			if(saleBox.getSelectionModel().getSelectedItem()!=null) {
////				System.out.println("2222222");
////				
////				saleBox.getSelectionModel().clearSelection();
////				
////				System.out.println("3333333");
////			}
////			System.out.println("444444");
//			
//	/*		Alert alert = new Alert(Alert.AlertType.WARNING);
//    		alert.setTitle("警告");
//    		alert.setHeaderText("");
//    		alert.setContentText("请先计算总价！");
//    		alert.showAndWait();
//    		return;*/
//			Alert alert2 = new Alert(AlertType.INFORMATION);
//			alert2.setTitle("警告");
//    		alert2.setHeaderText("");
//    		alert2.setContentText("请先计算总价！");
//    		alert2.show();
//    		//System.out.println(2);
////    		System.out.println("33333");
//    		return;
//		}
//    	
    	if(!SumSetText.getText().equals("")) {//总价有值
    		if(saleBox.getValue()!=null) {
    			String sale = saleBox.getValue();
        		if(sale.equals("免费")) {
            		saleText.setText("0.0");
            	}
            	else if(sale.equals("不打折")) {
            		saleText.setText(SumSetText.getText());
            	}
            	else {
            		String num = sale.substring(0, 1);
            		int n = Integer.parseInt(num);
            		double price = 0;
            		price = Double.parseDouble(SumSetText.getText()) * n / 10.0;
            		saleText.setText(Double.toString(price));
            	}
    		}
    		
		}
//    	System.out.println("11111");
    }

    
    @FXML
    void checkBook(ActionEvent event) {
    	
    	int year = 0;
    	int month = 0;
    	int day = 0;
    	try
		{
			Date date=new Date();   //获取系统时间
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
		    System.out.println(formatter.format(date));
			orderTime.setText(formatter.format(date));
			
			year = Integer.parseInt(formatter.format(date).substring(0,4));
	        month = Integer.parseInt(formatter.format(date).substring(5,7));
	        day = Integer.parseInt(formatter.format(date).substring(8,10));
		}
		catch (Exception e)
	    {
//	    	System.out.println(e);
			e.printStackTrace();
	    }
        
    	
		String room = roomid.getValue();
		String good = goodsBox.getValue();
//		String mon= month.getValue();//int
//		String dayy = day.getValue();//int
		String numm = num.getText();//int
		String price = saleText.getText();//double  16
		
		if( numm.equals("")  || price.equals("")  ) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入合法信息！");
    		alert.showAndWait();
    		return;
    	}
		if(roomid.getValue() == null ||goodsBox.getValue() == null ) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请选择合适信息！");
    		alert.showAndWait();
    		return;
		}
		if(!price.matches("^(0|[1-9]\\d*)\\.?\\d*([eE][-+]?\\d+)?$")) {//修改3输入价格全为数字///修改20
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入正确格式的价格信息！");
    		alert.showAndWait();
    		return;
    	}
		if(!numm.matches("\\d+")) {//修改4输入数量全为数字
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入正确格式的数量信息！");
    		alert.showAndWait();
    		return;
    	}
//		if(!numm.matches("\\d+")) {//修改4输入数量全为数字
//    		Alert alert = new Alert(Alert.AlertType.WARNING);
//    		alert.setTitle("警告");
//    		alert.setHeaderText("");
//    		alert.setContentText("请输入正确格式的数量信息！");
//    		alert.showAndWait();
//    		return;
//    	}
		
		boolean ifzero = true;
		boolean ifzero2 = true;
		for(int i=0;i<numm.length();i++) {//修改5价格可以为零
			if(numm.charAt(i) != '0') {
				ifzero = false;
				break;
			}
		}
//		for(int i=0;i<price.length();i++) {
//			if(price.charAt(i) != '0') {
//				ifzero2 = false;
//				break;
//			}
//		}
		if( ifzero) {
    		Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("警告");
    		alert.setHeaderText("");
    		alert.setContentText("请输入合法信息！");
    		alert.showAndWait();
    		return;
    	}
		
		Alert alert0 = new Alert(Alert.AlertType.CONFIRMATION);//修改4
    	alert0.setTitle("提示");
    	alert0.setHeaderText("");
		alert0.setContentText("确定订购商品：<"+good +"> 吗？请确认总价以及折后价无误");
		Optional<ButtonType> result = alert0.showAndWait();
    	if(result.get() == ButtonType.CANCEL) {
    		return ;
    	}
		
		try {
			Statement stmt;
			stmt = con.createStatement();
			String sql = "INSERT INTO consume_table(croom,cfood,cyear,cmon,cday,cnum,cprice) VALUES( '"+room+"',+ '"+good+"', "+year+","+month+","+day+","+numm+","+price +")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		try //刷新表格数据
		{        
			bookData.clear();
			Statement stmt = con.createStatement();
			String sql = "SELECT cid,croom,cfood,cyear,cmon,cday,cnum,cprice from consume_table";
			ResultSet rs = stmt.executeQuery( sql );
			while(rs.next()) 
			{   
//				System.out.println("连接成功");//
				int id1 = rs.getInt("cid");
				String room1 = rs.getString("croom");
				String good1 = rs.getString("cfood");
				int year1 = rs.getInt("cyear");
				int mon1= rs.getInt("cmon");
				int day1 = rs.getInt("cday");
				int num1 = rs.getInt("cnum");
				double price1 = rs.getDouble("cprice");
//				goodData.add(new goodTable(false,name,price,type));
				bookData.add(new Book(id1,room1,good1,year1,mon1,day1,num1,price1));
//				goodbox.add();
				
			}
			
			bookview.setItems(bookData);
			if(roomid.getSelectionModel().getSelectedItem()!=null) {//修改9 清空已提交数据
				roomid.getSelectionModel().clearSelection();
			}
			if(goodsBox.getSelectionModel().getSelectedItem()!=null) {
				goodsBox.getSelectionModel().clearSelection();
			}
//			if(month.getSelectionModel().getSelectedItem()!=null) {
//				month.getSelectionModel().clearSelection();
//			}
//			if(day.getSelectionModel().getSelectedItem()!=null) {
//				day.getSelectionModel().clearSelection();
//			}
			if(saleBox.getSelectionModel().getSelectedItem()!=null) {//17
				saleBox.getSelectionModel().clearSelection();
			}
			
			num.clear();
			saleText.clear();//                17
			SumSetText.setText(null);
			//关闭对象。 
	        rs.close();
	        stmt.close();
//	        con.close();
		}
		catch(SQLException e) {
//		    System.out.println("错误：" + e);
			e.printStackTrace();
		}
		
//		checkBookBtn.getScene().getWindow().hide();
		
    }
    
    @FXML
    void deleteData(ActionEvent event) {//删除订单修改8 删除订单函数
    	Book selectedgood = bookview.getSelectionModel().getSelectedItem();
//    	Boolean checked= selectcol.getCellData(selectedgood);//获取第一栏状态
    	
    	
    	if(selectedgood == null) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("提示");
    		alert.setHeaderText("");
    		alert.setContentText("请选择要删除的订单！");
    		alert.show();
    		return;
    	}
    	
    	String roomid = roomidcol.getCellData(selectedgood);
    	int cid = cidcol.getCellData(selectedgood);//修改6改用cid
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("提示");
		alert.setHeaderText("");
    	alert.setContentText("确定要删除房间：<"+roomid +"> 的订单<"+cid+">吗？");
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.CANCEL) {
    		return ;
    	}
    	else {
    		try 
    		{      
    			Statement stmt = con.createStatement();
    			String sql = "DELETE from consume_table WHERE cid = "+cid+"";
    			stmt.executeUpdate(sql);
    			bookData.clear();
    			String sql2 = "SELECT cid,croom,cfood,cyear,cmon,cday,cnum,cprice from consume_table";
    			ResultSet rs = stmt.executeQuery( sql2 );
    			while(rs.next()) 
    			{   
    				int id = rs.getInt("cid");
    				String room = rs.getString("croom");
    				String good = rs.getString("cfood");
    				int year= rs.getInt("cyear");
    				int mon= rs.getInt("cmon");
    				int day = rs.getInt("cday");
    				int num = rs.getInt("cnum");
    				double price = rs.getDouble("cprice");
//    				goodData.add(new goodTable(false,name,price,type));
    				bookData.add(new Book(id,room,good,year,mon,day,num,price));
//    				goodbox.add();
    				
    			}
    			
    			bookview.setItems(bookData);
//    			goodnamecol; pricecol; goodtypecol;往表格中写内容
//    			cidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
    			roomidcol.setCellValueFactory(new PropertyValueFactory<>("room"));
    			goodcol.setCellValueFactory(new PropertyValueFactory<>("food"));
    			yearcol.setCellValueFactory(new PropertyValueFactory<>("year"));
    			monthcol.setCellValueFactory(new PropertyValueFactory<>("mon"));
    			daycol.setCellValueFactory(new PropertyValueFactory<>("day"));
    			numcol.setCellValueFactory(new PropertyValueFactory<>("num"));
    			pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
    			bookview.setItems(bookData);			
    			//关闭对象。 
    	        rs.close();
    	        stmt.close();
    		}
    		
    		catch(SQLException e) {
//    		    System.out.println("错误：" + e);
    			e.printStackTrace();
    		}
    	}
    }
    
    public class Book{
    	private SimpleIntegerProperty id;
    	private SimpleStringProperty room;
    	private SimpleStringProperty food;
    	private SimpleIntegerProperty year;
    	private SimpleIntegerProperty mon;
    	private SimpleIntegerProperty day;
    	private SimpleIntegerProperty num;
    	private SimpleDoubleProperty price;
    	public Book() {}
    	public Book(int i,String r,String f,int y,int m,int d,int n,double pri) {
    		this.id = new SimpleIntegerProperty(i);
    		this.room = new SimpleStringProperty(r);
    		this.food = new SimpleStringProperty(f);
    		this.year = new SimpleIntegerProperty(y);
    		this.mon = new SimpleIntegerProperty(m);
    		this.day = new SimpleIntegerProperty(d);
    		this.num = new SimpleIntegerProperty(n);
    		this.price = new SimpleDoubleProperty(pri);
    	}
		public int getId() {
			return id.get();
		}
		public void setId(int id) {
			this.id.set(id);
		}
		public String getRoom() {
			return room.get();
		}
		public void setRoom(String room) {
			this.room.set(room);
		}
		public String getFood() {
			return food.get();
		}
		public void setFood(String food) {
			this.food.set(food);
		}
		public int getYear() {
			return year.get();
		}
		public void setYear(int year) {
			this.year.set(year);
		}
		public int getMon() {
			return mon.get();
		}
		public void setMon(int mon) {
			this.mon.set(mon);
		}
		public int getDay() {
			return day.get();
		}
		public void setDay(int day) {
			this.day.set(day);
		}
		public int getNum() {
			return num.get();
		}
		public void setNum(int num) {
			this.num.set(num);
		}
		public double getPrice() {
			return price.get();
		}
		public void setPrice(double price) {
			this.price.set(price);
		}
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		int year0 = 0;
    	int month0 = 0;
    	int day0 = 0;
    	try
		{
			Date date=new Date();   //获取系统时间
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
		    System.out.println(formatter.format(date));
			orderTime.setText(formatter.format(date));
			
			year0 = Integer.parseInt(formatter.format(date).substring(0,4));
	        month0 = Integer.parseInt(formatter.format(date).substring(5,7));
	        day0 = Integer.parseInt(formatter.format(date).substring(8,10));
		}
		catch (Exception e)
	    {
//	    	System.out.println(e);
			e.printStackTrace();
	    }
		
		// TODO Auto-generated method stub
		cidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
		roomidcol.setCellValueFactory(new PropertyValueFactory<>("room"));
		goodcol.setCellValueFactory(new PropertyValueFactory<>("food"));
		yearcol.setCellValueFactory(new PropertyValueFactory<>("year"));
		monthcol.setCellValueFactory(new PropertyValueFactory<>("mon"));
		daycol.setCellValueFactory(new PropertyValueFactory<>("day"));
		numcol.setCellValueFactory(new PropertyValueFactory<>("num"));
		pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
		try 
		{        
//			for(int i=1;i<=12;i++) {//设置月份显示
//				monshow.add(String.valueOf(i));
//			}
//			month.setItems(monshow);
			
            String DBDriver="com.mysql.cj.jdbc.Driver";  // 数据库 MYSQL 装载
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
              		
			//加载驱动程序
			Class.forName(DBDriver);
			//通过驱动程序管理器建立与数据源的连接
//			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			
			Statement stmt = con.createStatement();
			String sql = "SELECT cid,croom,cfood,cyear,cmon,cday,cnum,cprice from consume_table";
			ResultSet rs = stmt.executeQuery( sql );
			while(rs.next()) 
			{   
				int id = rs.getInt("cid");
				String room = rs.getString("croom");
				String good = rs.getString("cfood");
				int year = rs.getInt("cyear");
				int mon= rs.getInt("cmon");
				int day = rs.getInt("cday");
				int num = rs.getInt("cnum");
				double price = rs.getDouble("cprice");
//				goodData.add(new goodTable(false,name,price,type));
				bookData.add(new Book(id,room,good,year,mon,day,num,price));
//				goodbox.add();
				
			}
			
			bookview.setItems(bookData);
			
			//关闭对象。 
	        rs.close();
	        stmt.close();
//	        con.close();
		}
		catch(ClassNotFoundException e) {
//		    System.out.println("错误：" + e);
			e.printStackTrace();
		}
		catch(SQLException e) {
//		    System.out.println("错误：" + e);
			e.printStackTrace();
		}
		
		try 
		{        
			Statement stmt = con.createStatement();
			String sql = "SELECT fname,fprice,ftype from food_table";
			ResultSet rs = stmt.executeQuery( sql );
			String name;
			while(rs.next()) 
			{   
				name = rs.getString("fname");
				goodbox.add(name);
				
			}
			goodsBox.setItems(goodbox);
			
			//关闭对象。 
	        rs.close();
	        stmt.close();
//	        con.close();
		}
		catch(SQLException e) {
//		    System.out.println("错误：" + e);
			e.printStackTrace();
		}
		
		try 
		{        
			saleData.add("不打折");
			for(int i=1;i<=9;i++) {
				saleData.add(i+"折");
			}
			saleData.add("免费");
			
			Statement stmt = con.createStatement();
			String sql = "SELECT broomid,byear,bmon,bday from book_table";
			ResultSet rs = stmt.executeQuery( sql );
			String id;
			int year;
			int month;
			int day;
			while(rs.next()) 
			{   
				id = rs.getString("broomid");
				year = rs.getInt("byear");
				month = rs.getInt("bmon");
				day = rs.getInt("bday");
				if(year == year0 && month==month0 && day==day0) {
					roomdata.add(id);
				}
			}
			roomid.setItems(roomdata);	
			
			saleBox.setItems(saleData);
//			@FXML
//		    private ComboBox<String> roomid;
//		    private ObservableList<String> roomdata = FXCollections.observableArrayList();
			//关闭对象。 
	        rs.close();
	        stmt.close();
//	        con.close();
		}
		catch(SQLException e) {
//		    System.out.println("错误：" + e);
			e.printStackTrace();
		}
	}

}


