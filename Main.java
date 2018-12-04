import javax.imageio.IIOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IIOException {

        SQLiteJDBCDriverConnection obj1= new SQLiteJDBCDriverConnection();

            obj1.connect();
            //obj1.createNewDatabase("test1");
            obj1.InitDB();
            obj1.Menu();


    }
}
