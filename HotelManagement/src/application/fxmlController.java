package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


public class fxmlController implements Initializable{
	
	@FXML
	private Scene scene;

    @FXML
    private Label lbl_year;
    @FXML
    private Label lbl_date;
    @FXML
    private Label lbl_month;
	
	//组件更改！
    @FXML
    private RXFillButton monthlyLineChartMenuItem;

    @FXML
    private RXFillButton dailyLineChartMenuItem;

    @FXML
    private RXFillButton pieChartMenuItem;

    @FXML
    private RXFillButton foodmonthlyLineChartMenuItem;

    @FXML
    private RXFillButton fooddailyLineChartMenuItem;

    @FXML
    private RXFillButton foodpieChartMenuItem;
    
    @FXML
    private LineChart<String, Number> dailyProfitLineChart= new LineChart<>(new javafx.scene.chart.CategoryAxis(), new NumberAxis());
    @FXML
    private LineChart<String, Number> fooddailyProfitLineChart= new LineChart<>(new javafx.scene.chart.CategoryAxis(), new NumberAxis());
    @FXML
    private PieChart roomProfitPieChart;
    @FXML
    private PieChart foodroomProfitPieChart;
    @FXML
    private LineChart<String, Number> monthlyProfitLineChart= new LineChart<>(new javafx.scene.chart.CategoryAxis(), new NumberAxis());
    @FXML
    private LineChart<String, Number> foodmonthlyProfitLineChart= new LineChart<>(new javafx.scene.chart.CategoryAxis(), new NumberAxis());
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private Button returnBtn2;
    
    @FXML
    private ComboBox<Integer> chooseyear;
    @FXML
    private ComboBox<Integer> choosemonth;
    
    private String user;
    
    void setUser1(String user)
    {
    	this.user=user;
    }
    
    @FXML
    void handleReturn2()
    {
    	try
    	{
    		Stage sta=new Stage();
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Stage.fxml"));
    		root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Stage.css");
			scene.getStylesheets().add(css.toExternalForm());
			sta.setResizable(false);
			sta.setScene(scene);
			sta.setTitle("酒店管理系统");
			//设置窗口图标！
	        Image icon = new Image("/resourcefile/duck100.png");
	        sta.getIcons().add(icon);
			sta.show();
			
			StageController sc=loader.getController();
			sc.setUser(user);
			
			Stage preStage=(Stage)returnBtn2.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void showMonthlyLineChart(ActionEvent event) {

    	
    	chooseyear.setVisible(true);
    	choosemonth.setVisible(false);
    	lbl_date.setVisible(true);
    	lbl_year.setVisible(true);
    	lbl_month.setVisible(false);
    	    	
    	
    	dailyProfitLineChart.setVisible(false);
    	roomProfitPieChart.setVisible(false);
    	monthlyProfitLineChart.setVisible(true);
    	fooddailyProfitLineChart.setVisible(false);
    	foodroomProfitPieChart.setVisible(false);
    	foodmonthlyProfitLineChart.setVisible(false);
    	
    	
    }

    @FXML
    void showDailyLineChart(ActionEvent event) {
    	chooseyear.setVisible(true);
    	choosemonth.setVisible(true);    	
    	
    	lbl_date.setVisible(true);
    	lbl_year.setVisible(true);
    	lbl_month.setVisible(true);
    	
    	dailyProfitLineChart.setVisible(true);
    	roomProfitPieChart.setVisible(false);
    	monthlyProfitLineChart.setVisible(false);
    	fooddailyProfitLineChart.setVisible(false);
    	foodroomProfitPieChart.setVisible(false);
    	foodmonthlyProfitLineChart.setVisible(false);
    	
    	
    	
    	
    }

    @FXML
    void showPieChart(ActionEvent event) {
    	chooseyear.setVisible(false);
    	choosemonth.setVisible(false);
    	
    	lbl_date.setVisible(false);
    	lbl_year.setVisible(false);
    	lbl_month.setVisible(false);
    	
    	dailyProfitLineChart.setVisible(false);
    	roomProfitPieChart.setVisible(true);
    	monthlyProfitLineChart.setVisible(false);
    	fooddailyProfitLineChart.setVisible(false);
    	foodroomProfitPieChart.setVisible(false);
    	foodmonthlyProfitLineChart.setVisible(false);
    	

    }
    
    @FXML
    void showfoodMonthlyLineChart(ActionEvent event) {
    	chooseyear.setVisible(true);
    	choosemonth.setVisible(false);
    	
    	lbl_date.setVisible(true);
    	lbl_year.setVisible(true);
    	lbl_month.setVisible(false);
    	
    	dailyProfitLineChart.setVisible(false);
    	roomProfitPieChart.setVisible(false);
    	monthlyProfitLineChart.setVisible(false);
    	fooddailyProfitLineChart.setVisible(false);
    	foodroomProfitPieChart.setVisible(false);
    	foodmonthlyProfitLineChart.setVisible(true);
    	
    	

    }

    @FXML
    void showfoodDailyLineChart(ActionEvent event) {
    	chooseyear.setVisible(true);
    	choosemonth.setVisible(true);
    	
    	lbl_date.setVisible(true);
    	lbl_year.setVisible(true);
    	lbl_month.setVisible(true);
    	
    	dailyProfitLineChart.setVisible(false);
    	roomProfitPieChart.setVisible(false);
    	monthlyProfitLineChart.setVisible(false);
    	fooddailyProfitLineChart.setVisible(true);
    	foodroomProfitPieChart.setVisible(false);
    	foodmonthlyProfitLineChart.setVisible(false);
    	
    	
    }

    @FXML
    void showfoodPieChart(ActionEvent event) {
    	chooseyear.setVisible(false);
    	choosemonth.setVisible(false);
    	
    	lbl_date.setVisible(false);
    	lbl_year.setVisible(false);
    	lbl_month.setVisible(false);
    	
    	dailyProfitLineChart.setVisible(false);
    	roomProfitPieChart.setVisible(false);
    	monthlyProfitLineChart.setVisible(false);
    	fooddailyProfitLineChart.setVisible(false);
    	foodroomProfitPieChart.setVisible(true);
    	foodmonthlyProfitLineChart.setVisible(false);

    	
    }
    
    
//    @FXML
//    private Map<Integer, Integer> priceMap=new HashMap<Integer, Integer>();
//    
//    
//
//	private void fillMap(String url, String user, String pwd) throws Exception{
//		
//		Connection connection=DriverManager.getConnection(url,user,pwd);
//		String sql="select * from room_table";
//		Statement statement=connection.createStatement();
//		ResultSet rs=statement.executeQuery(sql);
//		while(rs.next()) {
//			int room_id=rs.getInt(1);
//			int room_price=rs.getInt(2);
//			System.out.println(room_id+":"+room_price);
//			priceMap.put(room_id, room_price);
//		}
//		connection.close();
//	}


    private void monthlyProfit(String url, String user, String pwd,int nowyear) throws Exception{
    	Connection connection=DriverManager.getConnection(url,user,pwd);
    	int arr[]=new int[13];
    	for(int i=0;i<13;i++) {
    		arr[i]=0;
    	}
		String sql="select * from book_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()) {
			int month=rs.getInt("bmon");
			int room_id=rs.getInt("bid");
			
			int year=rs.getInt("byear");
			int price=rs.getInt("bprice");
			System.out.println(month+":"+room_id+":"+price);
			if(year==nowyear) {
				arr[month]+=price;
			}
			
		}
	    XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
	    lineChartData.setName("收入(元)");
	    lineChartData.getData().add(new XYChart.Data<>("一月",arr[1]));
	    lineChartData.getData().add(new XYChart.Data<>("二月",arr[2]));
	    lineChartData.getData().add(new XYChart.Data<>("三月",arr[3]));
	    lineChartData.getData().add(new XYChart.Data<>("四月",arr[4]));
	    lineChartData.getData().add(new XYChart.Data<>("五月",arr[5]));
	    lineChartData.getData().add(new XYChart.Data<>("六月",arr[6]));
	    lineChartData.getData().add(new XYChart.Data<>("七月",arr[7]));
	    lineChartData.getData().add(new XYChart.Data<>("八月",arr[8]));
	    lineChartData.getData().add(new XYChart.Data<>("九月",arr[9]));
	    lineChartData.getData().add(new XYChart.Data<>("十月",arr[10]));
	    lineChartData.getData().add(new XYChart.Data<>("十一月",arr[11]));
	    lineChartData.getData().add(new XYChart.Data<>("十二月",arr[12]));
	    
//	    monthlyProfitLineChart = new LineChart<>(new javafx.scene.chart.CategoryAxis(), new NumberAxis());
	    monthlyProfitLineChart.getData().clear();
	    monthlyProfitLineChart.setTitle(nowyear+"年订房收入折线图");
	    monthlyProfitLineChart.getData().add(lineChartData);
	    
	    connection.close();
    }
    
    public void foodmonthlyProfit(String url, String user, String pwd,int nowyear) throws Exception{
    	Connection connection=DriverManager.getConnection(url,user,pwd);
    	int arr[]=new int[13];
    	for(int i=0;i<13;i++) {
    		arr[i]=0;
    	}
		String sql="select * from consume_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()) {
			int month=rs.getInt("cmon");
			int price=rs.getInt("cprice");
//			System.out.println(month+":"+room_id+":"+priceMap.get(room_id));
			int year=rs.getInt("cyear");
			if(year==nowyear) {
				arr[month]+=price;
			}
		}
	    XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
	    lineChartData.setName("收入(元)");
	    lineChartData.getData().add(new XYChart.Data<>("一月",arr[1]));
	    lineChartData.getData().add(new XYChart.Data<>("二月",arr[2]));
	    lineChartData.getData().add(new XYChart.Data<>("三月",arr[3]));
	    lineChartData.getData().add(new XYChart.Data<>("四月",arr[4]));
	    lineChartData.getData().add(new XYChart.Data<>("五月",arr[5]));
	    lineChartData.getData().add(new XYChart.Data<>("六月",arr[6]));
	    lineChartData.getData().add(new XYChart.Data<>("七月",arr[7]));
	    lineChartData.getData().add(new XYChart.Data<>("八月",arr[8]));
	    lineChartData.getData().add(new XYChart.Data<>("九月",arr[9]));
	    lineChartData.getData().add(new XYChart.Data<>("十月",arr[10]));
	    lineChartData.getData().add(new XYChart.Data<>("十一月",arr[11]));
	    lineChartData.getData().add(new XYChart.Data<>("十二月",arr[12]));
	    
//	    foodmonthlyProfitLineChart = new LineChart<>(new javafx.scene.chart.CategoryAxis(), new NumberAxis());
	    foodmonthlyProfitLineChart.getData().clear();
	    foodmonthlyProfitLineChart.setTitle(nowyear+"年商品收入折线图");
	    foodmonthlyProfitLineChart.getData().add(lineChartData);
	    
	    
	    connection.close();
    }


	private void dailyProfit(String url, String user, String pwd,int nowyear,int nowMonth) throws Exception{
		Connection connection=DriverManager.getConnection(url,user,pwd);
		int daysnum[]={0,31,29,31,30,31,30,31,31,30,31,30,31};
		int days=daysnum[nowMonth];
		
    	int arr[]=new int[days+1];
    	for(int i=0;i<days+1;i++) {
    		arr[i]=0;
    	}
    	System.out.println("dailyProfit:nowmonthis="+nowMonth);
		String sql="select * from book_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()) {
			int month=rs.getInt("bmon");
			int year=rs.getInt("byear");
			int price=rs.getInt("bprice");
			if(month==nowMonth&&year==nowyear) {
				int room_id=rs.getInt("broomid");
				int day=rs.getInt("bday");
				System.out.println(month+":"+room_id+":"+price);
				arr[day]+=price;
			}

		}
	    XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
	    lineChartData.setName("收入(元)");
	    for(int i=1;i<days+1;i++) {
	    	lineChartData.getData().add(new XYChart.Data<>(""+i,arr[i]));
	    }
	    
	    dailyProfitLineChart.getData().clear();
	    dailyProfitLineChart.setTitle(nowyear+"年"+(nowMonth)+"月订房收入折线图");
	    dailyProfitLineChart.getData().add(lineChartData);
	    
	    
	    connection.close();
    }
    
	public void fooddailyProfit(String url, String user, String pwd,int nowyear, int nowMonth) throws Exception{
		Connection connection=DriverManager.getConnection(url,user,pwd);
		int daysnum[]={0,31,29,31,30,31,30,31,31,30,31,30,31};
		int days=daysnum[nowMonth];
    	int arr[]=new int[days+1];
    	for(int i=0;i<days+1;i++) {
    		arr[i]=0;
    	}
    	System.out.println("nowmonthis="+nowMonth);
		String sql="select * from consume_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()) {
			int month=rs.getInt("cmon");
			int year=rs.getInt("cyear");
			if(month==nowMonth&&nowyear==year) {
				int price=rs.getInt("cprice");
				int day=rs.getInt("cday");
//				System.out.println(month+":"+room_id+":"+priceMap.get(room_id));
				arr[day]+=price;
			}

		}
	    XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
	    lineChartData.setName("收入(元)");
	    for(int i=1;i<days+1;i++) {
	    	lineChartData.getData().add(new XYChart.Data<>(""+i,arr[i]));
	    }
	    
	    fooddailyProfitLineChart.getData().clear();
	    fooddailyProfitLineChart.setTitle(nowyear+"年"+(nowMonth)+"月商品收入折线图");
	    fooddailyProfitLineChart.getData().add(lineChartData);
	    
	    
	    connection.close();
    }


    private void roomProfit(String url, String user, String pwd) throws Exception{
    	Connection connection=DriverManager.getConnection(url,user,pwd);
    	Map<Integer,Integer> roomProfit=new HashMap<Integer,Integer>();
    	
    	
		String sql="select * from book_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()) {
			int room_id=rs.getInt("broomid");
			int price=rs.getInt("bprice");
			if(roomProfit.containsKey(room_id)) {
				roomProfit.put(room_id, roomProfit.get(room_id)+price);
			}else {
				System.out.println("Add pie room id:"+room_id);
				roomProfit.put(room_id, price);
			}
			System.out.println(room_id+":"+price);
		}
	    
	    for (Integer key : roomProfit.keySet()) {
	        System.out.println("key = " + key + ", value = " + roomProfit.get(key));
	        roomProfitPieChart.getData().add(new PieChart.Data(""+key, roomProfit.get(key)));
	    }
	    roomProfitPieChart.setTitle("累计订房收入扇形图");
	    
	    connection.close();
	    
    }

    public void foodroomProfit(String url, String user, String pwd) throws Exception{
    	Connection connection=DriverManager.getConnection(url,user,pwd);
    	Map<Integer,Integer> foodroomProfit=new HashMap<Integer,Integer>();
    	
    	
		String sql="select * from consume_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()) {
			int room_id=rs.getInt("croom");
			int price=rs.getInt("cprice");
			if(foodroomProfit.containsKey(room_id)) {
				foodroomProfit.put(room_id, foodroomProfit.get(room_id)+price);
			}else {
				System.out.println("Add pie room id food:"+room_id);
				foodroomProfit.put(room_id, price);
			}
		}
	    
	    for (Integer key : foodroomProfit.keySet()) {
	        System.out.println("key = " + key + ", food value = " + foodroomProfit.get(key));
	        foodroomProfitPieChart.getData().add(new PieChart.Data(""+key, foodroomProfit.get(key)));
	    }
	    foodroomProfitPieChart.setTitle("累计商品收入扇形图");
	    
	    connection.close();
    }

    
    public void setChoiceBox(String url, String user, String pwd) throws Exception{
    	
    	
    	
    	
    	Connection connection=DriverManager.getConnection(url,user,pwd);
    	System.out.println("setChoiceBox");
    	
    	String sql="select * from book_table";
    	String sql2="select * from consume_table";
		Statement statement=connection.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		Vector<Integer> years=new Vector<Integer>();
		while(rs.next()) {
	    	int year=rs.getInt("byear");
	    	if(!years.contains(year)) {
	    		System.out.println("Add year: "+year);
	    		years.add(year);
//	    		chooseyear.getItems().add(year);
	    	}
		}
		rs=statement.executeQuery(sql2);
		while(rs.next()) {
	    	int year=rs.getInt("cyear");
	    	if(!years.contains(year)) {
	    		System.out.println("Add year: "+year);
	    		years.add(year);
//	    		chooseyear.getItems().add(year);
	    	}
		}
		System.out.println("years:"+years);
		
		Collections.sort(years);
		for (int i = 0; i < years.size(); i++) {
		    System.out.println(years.get(i));
		    chooseyear.getItems().add(years.get(i));
		}
		
		


		
		
		for(int i=1;i<=12;i++) {
			choosemonth.getItems().add(i);
		}
		
		
		chooseyear.setOnAction(event->{
			if(chooseyear.getValue()!=null) {
				try {
					
			    				    						
					monthlyProfit(url,user,pwd,chooseyear.getValue());
					foodmonthlyProfit(url,user,pwd,chooseyear.getValue());
					dailyProfit(url,user,pwd,chooseyear.getValue(),choosemonth.getValue());
					fooddailyProfit(url,user,pwd,chooseyear.getValue(),choosemonth.getValue());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		
		
		choosemonth.setOnAction(event->{
			if(choosemonth.getValue()!=null) {
				try {
					
					
					monthlyProfit(url,user,pwd,chooseyear.getValue());
					foodmonthlyProfit(url,user,pwd,chooseyear.getValue());
					dailyProfit(url,user,pwd,chooseyear.getValue(),choosemonth.getValue());
					fooddailyProfit(url,user,pwd,chooseyear.getValue(),choosemonth.getValue());
			    				    						
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		connection.close();
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	try {
    		
    		
    		Platform.runLater(()->{
    			Stage stage=(Stage)monthlyLineChartMenuItem.getScene().getWindow();
        		stage.setResizable(false);
    		});
	    	

    		
    		
	    	System.out.println("here");
			String url="jdbc:mysql://localhost:3306/hoteldatabase?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
			String user="root";
			String pwd="sysu";
			Connection connection;
			
			connection = DriverManager.getConnection(url,user,pwd);
	
			System.out.println(connection);
			

			dailyProfit(url,user,pwd,2023,1);
			monthlyProfit(url,user,pwd,2023);
			roomProfit(url,user,pwd);
			foodmonthlyProfit(url,user,pwd,2023);
			fooddailyProfit(url,user,pwd,2023,1);
			foodroomProfit(url,user,pwd);
			
	    	dailyProfitLineChart.setVisible(false);
	    	roomProfitPieChart.setVisible(false);
	    	monthlyProfitLineChart.setVisible(true);
	    	fooddailyProfitLineChart.setVisible(false);
	    	foodroomProfitPieChart.setVisible(false);
	    	foodmonthlyProfitLineChart.setVisible(false);
	    	
	    	lbl_month.setVisible(false);

	    	
	    	setChoiceBox(url,user,pwd);
	    	chooseyear.setValue(chooseyear.getItems().get(0));
	    	choosemonth.setValue(choosemonth.getItems().get(0));
	    	
	    	
	    	chooseyear.setVisible(true);
	    	choosemonth.setVisible(false);
	    	

	    	monthlyProfit(url,user,pwd,chooseyear.getItems().get(0));
	    	

	    	connection.close();

	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



    
}
