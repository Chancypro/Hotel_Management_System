package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PriceChangeController implements Initializable{

    @FXML
    private ComboBox<String> typeCom;

    @FXML
    private TextField priceField;

    @FXML
    private RXFillButton saveBtn;

    @FXML
    private RXFillButton cancelBtn;
    
    private ObservableList<String> typeboxs = FXCollections.observableArrayList();
    private Connection con;

    @FXML
    void handleSave(ActionEvent event) {
    	try
    	{
	    	String thisType="";
	    	if (typeCom.getValue()==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请选择房型！");
	    		alert.show();
	    		return;
	    	}
	    	else thisType=typeCom.getValue().toString();
	    	
	    	String sprice=priceField.getText();
	    	if (sprice.length()==0)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请填写价格！");
	    		alert.show();
	    		return;
	    	}
	    	for (int i=0;i<sprice.length();i++)
	    	{
	    		if (sprice.charAt(i)>='0'&&sprice.charAt(i)<='9') continue;
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请输入合法价格！");
	    		alert.show();
	    		return;
	    	}
	    	//int price=Integer.parseInt(sprice);
	    	String sql="UPDATE room_table SET rprice="+sprice+" WHERE rtype='"+thisType+"';";
	    	Statement st=con.createStatement();
	    	st.executeUpdate(sql);
	    	
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("修改成功！");
    		alert.show();
    		
    		Stage stage=(Stage)saveBtn.getScene().getWindow();
    		stage.close();
    	}
    	catch (Exception e)
		{
			System.out.println(e);
		}
    }

    @FXML
    void handleCancel(ActionEvent event) {
    	Stage stage=(Stage)cancelBtn.getScene().getWindow();
		stage.close();
    }
    
    public void initialize(java.net.URL location, ResourceBundle resources)
    {
    	typeboxs.add("双床房");
    	typeboxs.add("大床房");
    	typeboxs.add("总统套房");
    	typeboxs.add("豪华单人间");
    	typeboxs.add("豪华双床房");
		typeCom.setItems(typeboxs);
		
		typeCom.setOnAction(event ->
		{
			if(typeCom.getSelectionModel().getSelectedIndex()!=-1)
			{
				try
				{
					String type=typeCom.getValue();
					
					String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
			        String DBUser="root", DBPassword="sysu";
					Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
					this.con=con;
					Statement stmt=con.createStatement();
					String sql="SELECT * FROM room_table WHERE rtype='"+type+"';";
					ResultSet rs=stmt.executeQuery(sql);
					while (rs.next())
					{
						priceField.setText(rs.getString("rprice"));
						break;
					}
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
				
			}
		});
		
    }
}
