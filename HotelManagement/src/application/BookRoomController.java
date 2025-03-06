package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import application.GuestMainController.RoomInfo;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BookRoomController implements Initializable{

    @FXML private TableColumn<RoomInfo,String> tableType;
    @FXML private Label titleLabel;
    @FXML private TableColumn<RoomInfo,Integer> tableOldPrice;
    @FXML private Label dbClickLabel;
    //组件更改！
    @FXML
    private RXFillButton searchBtn;
    @FXML
    private RXFillButton bookBtn;
    @FXML private TableView<RoomInfo> roomTable;
    @FXML private ComboBox<String> monthField;
    @FXML private ComboBox<String> dayField;
    @FXML private ComboBox<Integer> timeField;
    
    @FXML private Button returnBtn;
	@FXML
    void handleReturn(ActionEvent event) {
		try
    	{
    		Stage stage=(Stage)returnBtn.getScene().getWindow();
    		stage.close();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
	}
    
    private Connection con;
    private ObservableList<RoomInfo> infoList=FXCollections.observableArrayList();
    private String []days=new String[] {"31","29","31","30","31","30"};
    
    ObservableList<String> dayList=FXCollections.observableArrayList();
    
    @FXML
    void monthFieldChange(ActionEvent event)   //根据月份选择日期显示
    {
    	int chosenmon=Integer.parseInt(monthField.getValue());
    	String daynum=days[chosenmon-1];
    	dayList.clear();
    	for(int i=1;i<=Integer.parseInt(daynum);i++) {
    		if (chosenmon==1&&i<4) continue;
    		dayList.add(String.valueOf(i));
    	}
    	dayField.setItems(dayList);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)   //初始化空表
    {
    	try
    	{
    		ObservableList<Integer> timeList=FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
    		timeField.setItems(timeList);
    		ObservableList<String> monList=FXCollections.observableArrayList("1","2","3","4","5","6");
    		monthField.setItems(monList);
    		
    		String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			this.con=con;
			
			roomTable.setItems(infoList);
			tableType.setCellValueFactory(new PropertyValueFactory<>("rtype"));
			tableOldPrice.setCellValueFactory(new PropertyValueFactory<>("rop"));
			
			roomTable.setRowFactory( tv -> {   //tableview双击事件查看房间详细信息
	            TableRow<RoomInfo> row = new TableRow<RoomInfo>();
	            row.setOnMouseClicked(event -> {
	                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	                    RoomInfo roomInfo = row.getItem();
	                    System.out.println(roomInfo.getRtype());
	                    String dbRid=roomInfo.getRtype();
	                    try
	                	{
	                    	//css！
	                		Stage stage=new Stage();
	            			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/RoomDetails.fxml"));
	            			Parent root=loader.load();
	            			Scene scene=new Scene(root);
	            			URL css=this.getClass().getClassLoader().getResource("application/RoomDetails.css");
	            			scene.getStylesheets().add(css.toExternalForm());
	            			//设置窗口图标！
	            	        Image icon = new Image("/resourcefile/duck100.png");
	            	        stage.getIcons().add(icon);
	            			stage.setScene(scene);
	            			stage.setResizable(false);
	            			stage.setTitle("房间详情");
	            			stage.show();
	            			RoomDetailsController rd=loader.getController();
	            			rd.showInfo(dbRid);
	                	}
	                	catch(Exception e)
	                	{
	                		System.out.println(e);
	                	}
	                }
	            });
	            return row ;
	        });
    	}
    	catch(Exception e)
    	{
		    System.out.println(e);
		}
    }
    
    @FXML
    void handleSearch(ActionEvent event)   //查询指定日期空房
    {
    	try
    	{
    		roomTable.getItems().clear();
    		String BookMon,BookDay;
    		int thisTime=0;
	    	if (monthField.getValue()==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请选择起始月份！");
	    		alert.show();
	    		return;
	    	}
	    	else BookMon=monthField.getValue();
	    	if (dayField.getValue()==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请选择起始日！");
	    		alert.show();
	    		return;
	    	}
	    	else BookDay=dayField.getValue();
	    	
	    	if (timeField.getValue()==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("预订天数不能为空！");
	    		alert.show();
	    		return;
	    	}
	    	else thisTime=Integer.valueOf(timeField.getValue());
    		
    		Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * from room_table");
			
			int flag;
			HashMap<String,Integer> hashmap=new HashMap<String,Integer>();  //存放是否出现过该房型
			while (rs.next()) //遍历结果集
			{  
				String thisType=rs.getString("rtype");
				System.out.println("rtype:"+thisType);
				if (hashmap.containsKey(thisType)&&hashmap.get(thisType)==1) continue;   //该房型已打印
				hashmap.put(thisType,1);
				
				String thisRid=rs.getString("rid");
				System.out.println("rid:"+thisRid);
				flag=0;
				thisRid=rs.getString("rid");
					
				int day=Integer.valueOf(BookDay);
				int mon=Integer.valueOf(BookMon);
				System.out.println("day:"+day);
				System.out.println("mon:"+mon);
				for (int i=0;i<thisTime;i++)         //从订房当日开始+i天
				{
					if (mon==2&&day==29) {mon=3;day=1;}
					else if ((mon==1||mon==3||mon==5)&&day==32) {mon++;day=1;}
					else if ((mon==4||mon==6)&&day==31) {mon++;day=1;}
							
					Statement stmt2=con.createStatement();
					String sql="SELECT * FROM book_table WHERE broomid='"+thisRid+"' AND bmon='"+String.valueOf(mon)+"' AND bday='"+String.valueOf(day)+"'";
					ResultSet rs2=stmt2.executeQuery(sql);
					while (rs2.next())   //注意无论是否搜到都不为null！！
						flag=1;          //flag=1代表已被预订
					if (flag==1) break;
					day++;
				}
				System.out.println("flag:"+flag);
				System.out.println();
				if (flag==1) continue;
					
				RoomInfo roomInfo=new RoomInfo(thisType,rs.getInt("rprice"));
				infoList.add(roomInfo);
				
			}
    	}
    	catch(Exception e)
    	{
		    System.out.println(e);
		}
    }
    
    @FXML
    void handleBook(ActionEvent event)    //处理预订
    {
    	try
    	{
    		RoomInfo selRoom=roomTable.getSelectionModel().getSelectedItem();
    		if (selRoom==null)
    		{
    			Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请选择房型！");
	    		alert.show();
	    		return;
    		}
    		//css！
    		Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/BookInfo.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/BookInfo.css");
			scene.getStylesheets().add(css.toExternalForm());
			//设置窗口图标！
	        Image icon = new Image("/resourcefile/duck100.png");
	        stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.setTitle("确认订单");
			stage.setResizable(false);
			stage.show();
			
			BookInfoController bi=loader.getController();
			bi.showInfo(selRoom.getRtype(),Integer.valueOf(monthField.getValue()),Integer.valueOf(dayField.getValue()),Integer.valueOf(timeField.getValue()));
    	}
    	catch(Exception e)
    	{
		    System.out.println(e);
		}
    }
    
    public static class RoomInfo {
		private SimpleStringProperty rtype;
		private SimpleIntegerProperty rop;
		
		public RoomInfo(String t,int o)
		{
			rtype=new SimpleStringProperty(t);
			rop=new SimpleIntegerProperty(o);
		}

		public String getRtype() {
			return rtype.get();
		}

		public void setRtype(String rtype) {
			this.rtype.set(rtype);
		}

		public int getRop() {
			return rop.get();
		}

		public void setRop(int rop) {
			this.rop.set(rop);
		}
		

    }
}
