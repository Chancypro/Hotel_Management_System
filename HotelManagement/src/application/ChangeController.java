package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangeController implements Initializable{

    @FXML
    private Label roomidlabel;

    //组件更改！
    @FXML
    private RXFillButton savebut;

    @FXML
    private RXFillButton canclebut;

    @FXML
    private Label statusfield;

    @FXML
    private Label levellabel;

    @FXML
    private Label title;

    @FXML
    private Label statuslabel;

    @FXML
    private ComboBox<String> idcom;
    private ObservableList<String> idboxs = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> levelcom;
    private ObservableList<String> levelboxs = FXCollections.observableArrayList();
    private ObservableList<String> levelrec = FXCollections.observableArrayList();
    private ObservableList<String> statusrec = FXCollections.observableArrayList();
    
    private String urls="jdbc:mysql://localhost:3306/hoteldatabase?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private String user="root";
    private String pwd="sysu";
    private Connection connection;
    private Statement stmt;
    
    @FXML
    void savehandler(ActionEvent event) {
    	String id="",type="";
    	if (idcom.getValue()==null)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("请选择房间！");
    		alert.show();
    		return;
    	}
    	else id = idcom.getValue();
    	type = levelcom.getValue();
    	
    	try 
		{        						
			String sql = "UPDATE room_table SET rtype = ? WHERE rid = ?";
			connection= DriverManager.getConnection(urls,user,pwd);
			PreparedStatement stmt = connection.prepareStatement(sql);						
			stmt.setString(1, type);
			stmt.setString(2, id);
			stmt.executeUpdate();
			savebut.getScene().getWindow().hide();
		}		
		catch(SQLException e) {
		    System.out.println("错误：" + e);
		}
    	Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("温馨提示");
		alert.setHeaderText("");
		alert.setContentText("更改成功！");
		alert.show();
		return;
    }

    @FXML
    void canclehandler(ActionEvent event) {
    	idcom.getSelectionModel().clearSelection();
		levelcom.getSelectionModel().clearSelection();
		statusfield.setText("");
		Stage stage=(Stage)canclebut.getScene().getWindow();
		stage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			//连接数据库
			Connection connection= DriverManager.getConnection(urls,user,pwd);		
			Statement stmt=connection.createStatement();
			
			ResultSet rs=stmt.executeQuery("Select * FROM room_table");
			while(rs.next())
			{
				String a=rs.getString("rid");
				String b=rs.getString("rtype");
				
				Statement stmt1=connection.createStatement();
				ResultSet rs1=stmt1.executeQuery("Select * FROM book_table WHERE broomid='"+a+"'");
				//System.out.println("sql:"+"Select * FROM book_table WHERE broomid='"+a+"'");
				String year="",mon="",day="";
				try
				{
					Date date=new Date();   //获取系统时间
					SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    String comdate=formatter.format(date);
					year=comdate.substring(0,4);
					mon=comdate.substring(5,7);
					day=comdate.substring(8,10);
				}
				catch (Exception e)
			    {
			    	System.out.println(e);
			    }
				int flag=0;
				while (rs1.next())
				{
					if (rs1.getInt("byear")==Integer.parseInt(year)&&rs1.getInt("bmon")==Integer.parseInt(mon)&&rs1.getInt("bday")==Integer.parseInt(day))
					{
						flag=1;
						break;
					}
				}
				if (flag==0) statusrec.add("空房");  //无订房记录
				else statusrec.add("已预订");
				idboxs.add(a);
				levelrec.add(b);
			}
//			ResultSet rss=stmt.executeQuery("Select bmon,bday,broomid FROM book_table");
//			while(rss.next())
//			{
//				int a=rss.getInt("bmon");
//				int b=rss.getInt("bday");				
//			}
			levelboxs.add("双床房");
			levelboxs.add("大床房");
			levelboxs.add("总统套房");
			levelboxs.add("豪华单人间");
			levelboxs.add("豪华双床房");
			idcom.setItems(idboxs);
			levelcom.setItems(levelboxs);

			//选择房间号之后自动更新其他值
			idcom.setOnAction(event -> {
				if(idcom.getSelectionModel().getSelectedIndex()!=-1)
				{
					int idx = idcom.getSelectionModel().getSelectedIndex();
					levelcom.setValue(levelrec.get(idx));
					statusfield.setText(statusrec.get(idx));
				}
	            	  
	        });
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

