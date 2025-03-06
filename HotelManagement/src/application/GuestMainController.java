package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class GuestMainController {
	
	private Connection con;
	private String thisMon,thisDay;
	int thisTime;
	//更改组件！
	@FXML
    private RXFillButton checkRoom;

    @FXML
    private RXFillButton saveBtn;

    @FXML
    private RXFillButton cancelBtn;

    @FXML
    private RXFillButton TDBtn;
	@FXML private TableColumn<RoomInfo,String> tablePrice;  //Column
    @FXML private TableColumn<RoomInfo,String> tableId;     //Column
    @FXML private TableColumn<RoomInfo,String> tableType;   //Column
    @FXML private ComboBox<String> dayField;
    @FXML private ComboBox<Integer> timeField;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private TableView<RoomInfo> roomTable;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private ComboBox<String> monthField;
    @FXML private Label label5;
    @FXML private Label label6;
    @FXML private TextField guestField;
    @FXML private Label showName;
    @FXML private Label showId;
    @FXML private Label showSex;
    @FXML private Label showPhone;
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField phoneField;
    @FXML private RadioButton rdBtn1;
    @FXML private RadioButton rdBtn2;
    @FXML private ToggleGroup sex;
    @FXML private Button returnBtn;
    @FXML private Label showRoomLabel;
    
    private ObservableList<RoomInfo> infoList=FXCollections.observableArrayList();
    ObservableList<String> dayList=FXCollections.observableArrayList();

    private String userName;
    
    void setUser1(String userName)
    {
    	this.userName=userName;
    }
    
    @FXML
    void handleReturn()
    {
    	try
    	{
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
			stage.show();
			
			StageController sc=loader.getController();
			sc.setUser(userName);
			
			Stage preStage=(Stage)returnBtn.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    public void showTable()
    {
    	ObservableList<Integer> timeList=FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
		timeField.setItems(timeList);
		
    	roomTable.setItems(infoList);
		
		ObservableList<String> monList=FXCollections.observableArrayList("1","2","3","4","5","6");
		monthField.setItems(monList);
    }
    
    private String []days=new String[] {"31","29","31","30","31","30"};
    @FXML
    void monthFieldChange(ActionEvent event)   //根据月份选择日期显示
    {
    	if (monthField.getValue()==null) return;  //加入这行
    	int chosenmon=Integer.parseInt(monthField.getValue());
    	String daynum=days[chosenmon-1];
    	dayList.clear();
    	for(int i=1;i<=Integer.parseInt(daynum);i++) {
    		if (chosenmon==1&&i<4) continue;
    		dayList.add(String.valueOf(i));
    	}
    	dayField.setItems(dayList);
    }
    
    @FXML
    void handleCheckRoom(ActionEvent event) //查询当日空房
    {
    	try
    	{
    		roomTable.getItems().clear();
    		String BookMon,BookDay;
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
	    	
	    	thisMon=BookMon;
	    	thisDay=BookDay;
    		
    		String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
			String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			this.con=con;
			
			int flag=0;
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM room_table");  //选出所有房间
			while (rs.next())
			{
				flag=0;
				String thisRid=rs.getString("rid");
				Statement stmt1=con.createStatement();      //每个房间对应一次订房表
				String sql="SELECT * FROM book_table WHERE broomid='"+thisRid+"'";
				ResultSet rs1=stmt1.executeQuery(sql);
				
				while (rs1.next())
				{
					int day=Integer.valueOf(BookDay);
					int mon=Integer.valueOf(BookMon);
					for (int i=0;i<thisTime;i++)         //从订房当日开始+i天
					{
						if (mon==2&&day==29) {mon=3;day=1;}
						else if ((mon==1||mon==3||mon==5||mon==7||mon==8||mon==10)&&day==32) {mon++;day=1;}
						else if (mon==12&&day==32) {mon=1;day=1;}
						else if ((mon==4||mon==6||mon==9||mon==11)&&day==31) {mon++;day=1;}
						
						Statement stmt2=con.createStatement();
						sql="SELECT * FROM book_table WHERE broomid='"+thisRid+"' AND bmon='"+String.valueOf(mon)+"' AND bday='"+String.valueOf(day)+"'";
						ResultSet rs2=stmt2.executeQuery(sql);
						while (rs2.next())   //注意无论是否搜到都不为null！！
							flag=1;
						day++;
					}
				}
				if (flag==1) continue;
				RoomInfo roomInfo=new RoomInfo(thisRid,rs.getString("rtype"),rs.getString("rprice"));
				infoList.add(roomInfo);
			}
			
			tableId.setCellValueFactory(new PropertyValueFactory<>("rid"));
			tableType.setCellValueFactory(new PropertyValueFactory<>("rtype"));
			tablePrice.setCellValueFactory(new PropertyValueFactory<>("rprice"));
			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    public static class RoomInfo {
		private SimpleStringProperty rid;
		private SimpleStringProperty rtype;
		private SimpleStringProperty rprice;
		
		public RoomInfo(String i,String t,String p)
		{
			rid=new SimpleStringProperty(i);
			rtype=new SimpleStringProperty(t);
			rprice=new SimpleStringProperty(p);
		}

		public String getRid() {
			return rid.get();
		}

		public void setRid(String rid) {
			this.rid.set(rid);
		}

		public String getRtype() {
			return rtype.get();
		}

		public void setRtype(String rtype) {
			this.rtype.set(rtype);
		}

		public String getRprice() {
			return rprice.get();
		}

		public void setRprice(String rprice) {
			this.rprice.set(rprice);
		}
	}
    
    /**
     * 判断字符串中是否全是中文
     * 
     * @param str 待校验字符串
     * @return 是否全是中文
     */
    public static boolean isAllChinese(String str)
    {
        if (str==null)
        	return false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m=p.matcher(str);
        return m.matches();
    }
    
    //判断手机号是否合法
    public boolean phoneValidate(String phoneNumber)
    {
        if (phoneNumber!=null&&!phoneNumber.isEmpty())
        {
            boolean matches = Pattern.matches(
                    "^1[3-9]\\d{9}$|" +
                            "^1[3-9]\\d{1}[-\\s]\\d{4}[-\\s]\\d{4}$|" +
                            "^\\(1[3-9]\\d{1}\\)\\d{4}-\\d{4}$|" +
                            "^(?:\\(\\+\\d{2}\\)|\\+\\d{2})(\\d{11})$|" +
                            "^0\\d{3}-\\d{7}$|" +
                            "^0\\d{2}-\\d{8}$",phoneNumber);

            return matches;
        }
        return false;
    }

    @FXML
    void handleAdd(ActionEvent event) {
    	System.out.println("执行了handleAdd");
    	
    	try
    	{
    		RadioButton selectedSex=(RadioButton) sex.getSelectedToggle();
    		
	    	String bookName=nameField.getText();
	    	String bookId=idField.getText();
	    	String bookPhone=phoneField.getText();
	    	if (selectedSex==null||bookName.length()==0||bookId.length()==0||bookPhone.length()==0)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请完整填写住客信息！");
	    		alert.show();
	    		return;
	    	}
	    	int elFlag=0;
	    	if ((bookName.charAt(0)>='A'&&bookName.charAt(0)<='Z')||(bookName.charAt(0)>='a'&&bookName.charAt(0)<='z'))
	    	{
	    		elFlag=1;
	    		//如果有英文必须只含英文/空格
	    		for (int i=1;i<bookName.length();i++)
	    		{
	    			if ((bookName.charAt(i)>='A'&&bookName.charAt(i)<='Z')||(bookName.charAt(i)>='a'&&bookName.charAt(i)<='z'))
	    		    	continue;
	    			System.out.println("bookName:"+bookName.charAt(i));
	    			if (bookName.charAt(i)==' ')
	    				continue;
	    			System.out.println("bookName:"+bookName.charAt(i));
	    			Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("温馨提示");
		    		alert.setHeaderText("");
		    		alert.setContentText("请填写合法住客姓名（纯英文或纯中文）！");
		    		alert.show();
		    		return;
	    		}
	    	}
	    	if (elFlag==0)
	    		if (isAllChinese(bookName)==false)
	    		{
	    			Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("温馨提示");
		    		alert.setHeaderText("");
		    		alert.setContentText("请填写合法住客姓名（纯英文或纯中文）！");
		    		alert.show();
		    		return;
	    		}
	    	
	    	if (phoneValidate(bookPhone)==false)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请填写11位合法手机号！");
	    		alert.show();
	    		return;
	    	}
	    	
	    	String bookSex=selectedSex.getText();
	    	
	    	System.out.println("名字："+bookName);
	    	System.out.println("身份证号："+bookId);
	    	System.out.println("性别："+bookSex);
	    	System.out.println("联系方式："+bookPhone);
	    	
	    	RoomInfo selRoom=roomTable.getSelectionModel().getSelectedItem();
	    	if (selRoom==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请选择房间！");
	    		alert.show();
	    		return;
	    	}
	    	String selRid=selRoom.getRid();  //选中的房间id
	    	
	    	//判断完终于可以正式处理了、、
	    	Statement stmt=con.createStatement();
			String sql="SELECT * from guest_table";
			ResultSet rs=stmt.executeQuery(sql);
			int flag=0;
			while (rs.next())
			    if (rs.getString("gid").equals(bookId)==true)  //增加住客信息
			    	{flag=1;break;}
			if (flag==0)
			{
				sql="INSERT INTO guest_table (gid,gname,gsex,gphonenum) VALUES ('"+bookId+"','"+bookName+"','"+bookSex+"','"+bookPhone+"');";
				System.out.println("sql语句："+sql);
				stmt.executeUpdate(sql);
			}
			
			//增加订房信息
			Statement st=con.createStatement();
			ResultSet rrs=st.executeQuery("SELECT * FROM room_table WHERE rid='"+selRid+"';");
			int price=0;
			while (rrs.next())
				price=rrs.getInt("rprice");
			System.out.println("持续时间："+thisTime+"天");
			int day=Integer.valueOf(thisDay);    
			int mon=Integer.valueOf(thisMon);
			for (int i=0;i<thisTime;i++)             //从订房当日开始+i天
			{
				System.out.println("前Mon："+mon);
				System.out.println("前Day："+day);
				
				if (mon==2&&day==29) {mon=3;day=1;}
				else if ((mon==1||mon==3||mon==5)&&day==32) {mon++;day=1;}
				else if ((mon==4||mon==6)&&day==31) {mon++;day=1;}
				
				System.out.println("后Mon："+mon);
				System.out.println("后Day："+day);
				
				sql="INSERT INTO book_table (byear,bmon,bday,broomid,bguestid,bprice,bassess) VALUES ('2024','"+String.valueOf(mon)+"','"+String.valueOf(day)+"','"+selRid+"','"+bookId+"',"+price+",0);";
				System.out.println("sql语句："+sql);
				stmt.executeUpdate(sql);
				
				day++;
			}
			
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("房间预订成功！");
    		alert.show();
    		roomTable.getItems().clear();   //刷新tableview
    		//清空其他内容
    		idField.clear();
    		nameField.clear();
    		phoneField.clear();
    		monthField.getItems().clear();
    		dayField.getItems().clear();
    		timeField.getItems().clear();
    		sex.getToggles().clear();
    		showTable();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

    @FXML
    void handleCancel(ActionEvent event) {

    	try
    	{
    		System.out.println("执行了handleCancel");
    		roomTable.getItems().clear();
    		idField.clear();
    		nameField.clear();
    		phoneField.clear();
    		monthField.getItems().clear();
    		dayField.getItems().clear();
    		timeField.getItems().clear();
    		sex.getToggles().clear();
    		showTable();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void handleTD(ActionEvent event) {    //退订
    	
    	try
    	{
    		//css！
    		Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/TDpage.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/TDpage.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setScene(scene);
			//设置窗口图标！
			Image icon = new Image("/resourcefile/duck100.png");
            stage.getIcons().add(icon);
			stage.setTitle("客房退订");
			stage.setResizable(false);
			stage.show();
			
			TDpageController tc=loader.getController();
			tc.showTable();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

}
