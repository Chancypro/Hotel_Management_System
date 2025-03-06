package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class RoomDetailsController {

    @FXML private TextField showType;
    @FXML private TextArea showDetail;
    @FXML private TextField showArea;
    @FXML private ImageView pic;
    
    void showInfo(String type)
    {
    	try
    	{
	    	String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
	        String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			Statement stmt=con.createStatement();
			String sql="SELECT * FROM room_table WHERE rtype='"+type+"';";
			ResultSet rs=stmt.executeQuery(sql);
			
	    	showType.setText(type);
	    	while (rs.next())
	    	{
	    		
	    		int area=0;
	    		if (type.equals("大床房")==true)
	    		{
	    			area=20;
	    			Image image=new Image("/resourcefile/dachuang.png");
	    			pic.setImage(image);
	    		}
				else if (type.equals("双床房")==true)
					{
						area=30;
			    		Image image=new Image("/resourcefile/shuangchuang.png");
			    		pic.setImage(image);
					}
				else if (type.equals("豪华单人间")==true) 
					{
						area=25;
						Image image=new Image("/resourcefile/haohuadanren.png");
			    		pic.setImage(image);
					}
				else if (type.equals("豪华双床房")==true) 
					{
						area=35;
						Image image=new Image("/resourcefile/haohuashuangchuang.png");
			    		pic.setImage(image);
					}
				else if (type.equals("总统套房")==true)
					{
						area=120;
						Image image=new Image("/resourcefile/zongtong.png");
			    		pic.setImage(image);
					}
	    		showArea.setText(String.valueOf(area)+"平方米");
	    		break;
	    	}
    	}
    	catch (SQLException e)
    	{
			e.printStackTrace();			
		}
    }

}
