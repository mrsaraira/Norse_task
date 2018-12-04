import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.*;

public class SQLiteJDBCDriverConnection {

    public static Connection conn;
    public static Statement statmt;
    public static String url;

    /**
     * Connect to database
     */
    public void connect() {
        conn = null;
        try {
            //registering the jdbc driver here, your string to use
            //here depends on what driver you are using.
            DriverManager.registerDriver(new org.sqlite.JDBC());
            url = "jdbc:sqlite:/home/mrsarayra/database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NoClassDefFoundError ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Create and initialize database
     */
    public void InitDB() {
        try {
            statmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS Person (\n"
                    + "	PersonID integer PRIMARY KEY,\n"
                    + "	Name text NOT NULL,\n"
                    + "	LastName text NOT NULL,\n"
                    + " MidName text NOT NULL,\n"
                    + " Age integer, \n"
                    + " Email text, \n"
                    + " Birth_date text\n"
                    + ");";

            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            sql = "CREATE TABLE IF NOT EXISTS Faculty (\n"
                    + "FacultyID integer PRIMARY KEY,\n"
                    + "FacultyName text,\n"
                    + "Description text,\n"
                    + "Establishment text\n"
                    + ");";
            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            sql = "CREATE TABLE IF NOT EXISTS Degree (\n"
                    + "DegreeID integer PRIMARY KEY,\n"
                    + "DegreeName text \n"
                    + ");";
            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            sql = "CREATE TABLE IF NOT EXISTS Major (\n"
                    + "MajorID integer PRIMARY KEY,\n"
                    + "MajorName text \n"
                    + ");";
            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            statmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS StudyForm (\n"
                    + "FormID integer PRIMARY KEY,\n"
                    + "FormName text\n"
                    + ");";

            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            sql = "CREATE TABLE IF NOT EXISTS Groups (\n"
                    + "GroupID integer PRIMARY KEY,\n"
                    + "GroupName text NOT NULL,\n"
                    + "GFaculty integer,\n"
                    + "ClassPresident integer UNIQUE Default null,"
                    + "FOREIGN KEY (ClassPresident) REFERENCES Student(StudentID),\n"
                    + "FOREIGN KEY (GFaculty) REFERENCES Faculty(FacultyID)\n"
                    + ");";
            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            sql = "CREATE TABLE IF NOT EXISTS Student (\n"
                    + "StudentID integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "SPerson integer NOT NULL UNIQUE,\n"
                    + "SStudyForm integer,\n"
                    + "SGroup integer,\n"
                    + "GPA real,\n"
                    + "FOREIGN KEY (SPerson) REFERENCES Person(PersonID),\n"
                    + "FOREIGN KEY (SStudyForm) REFERENCES StudyForm(FormID),\n"
                    + "FOREIGN KEY (SGroup) REFERENCES Groups(GroupID)\n"
                    + ");";
            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");

            sql = "CREATE TABLE IF NOT EXISTS Teacher (\n"
                    + "TeacherID integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "TPerson integer NOT NULL UNIQUE,\n"
                    + "TMajor integer,\n"
                    + "TDegree integer,\n"
                    + "TFaculty integer,\n"
                    + "TGroups integer,"
                    + "FOREIGN KEY (TPerson) REFERENCES Person(PersonID),\n"
                    + "FOREIGN KEY (TFaculty) REFERENCES Faculty(FacultyID),\n"
                    + "FOREIGN KEY (TGroups) REFERENCES Groups(GroupID),"
                    + "FOREIGN KEY (TMajor) REFERENCES Major(MajorID),\n"
                    + "FOREIGN KEY (TDegree) REFERENCES Degree(DegreeID)\n"
                    + ");";
            statmt.execute(sql);

            sql = "INSERT OR REPLACE INTO StudyForm(FormID,FormName)\n" +
                    "Values" +
                    "(1,'Budget')," +
                    "(2,'Contract');\n";
            statmt.execute(sql);
            sql = "INSERT OR REPLACE INTO Major(MajorID,MajorName)\n" +
                    "Values" +
                    "(1,'Math')," +
                    "(2,'History')," +
                    "(3,'Computer science')";
            statmt.execute(sql);
            sql = "INSERT OR REPLACE INTO Degree(DegreeID,DegreeName)\n" +
                    "Values" +
                    "(1,'Candidate of Sciences')," + //no good translation for those ))
                    "(2,'Doctor of Sciences');\n";
            statmt.execute(sql);
            System.out.println("Таблица создана или уже существует.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insertion menu to call different methods (uncompleted, it should be submenu)
     */
    public void Menu() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader args = new BufferedReader(new InputStreamReader(System.in));
        String tempS;
        char temp;
        clearScreen();
        try {
            do {
                System.out.println("Inserting options:\n"
                        + "a)add student.\nb)add teacher\nc)add group\nd)add faculty\nz)Show all tables\ns)Show students\nt)Show teachers\n" +
                        "g)Show groups\nf)Show faculties\nr)Top 3 of each faculty\nq)cancel");
                temp = br.readLine().charAt(0);
                switch (temp) {
                    case 'a': {
                        clearScreen();
                        insertS();
                        break;
                    }
                    case 'c': {
                        clearScreen();
                        insertG();
                        break;
                    }
                    case 'd': {
                        clearScreen();
                        insertF();
                        break;
                    }
                    case 'b': {
                        clearScreen();
                        insertT();
                        break;
                    }
                    case 's': {
                        ListS();
                        break;
                    }
                    case 't': {
                        ListT();
                        break;
                    }
                    case 'g': {
                        ListG();
                        break;
                    }
                    case 'f': {
                        ListF();
                        break;
                    }
                    case 'z': {
                        ListF();
                        ListG();
                        ListS();
                        ListT();
                        ListF();
                    }
                    case 'r': {
                        TopThreeS();
                        break;
                    }
                    default:
                        break;
                }
            } while (temp != 'q');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert new student
     */
    public void insertS() {
        //Insert new student
        String sql, tempS;
        BufferedReader args = new BufferedReader(new InputStreamReader(System.in));
        try {
            ResultSet resultSet = insertP();
            sql = "INSERT INTO Student(SPerson,SStudyForm,SGroup,GPA) Values(?,?,?,?)";
            System.out.println("Please enter study form(1-budget,2-contract), group*, GPA\n" +
                    "\"\\\"HIT ENTER AFTER EVERY INSERTION\\\"\" *-skip by entering empty string");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, resultSet.getInt(1));
            pstmt.setDouble(2, Double.parseDouble(args.readLine()));
            int group = (tempS = args.readLine()).isEmpty() ? 0 : Integer.parseInt(tempS);
            if (group != 0)
                pstmt.setInt(3, group);
            else
                pstmt.setNull(3, Types.NULL);
            pstmt.setDouble(4, Double.parseDouble(args.readLine()));
            pstmt.executeUpdate();
            System.out.println("New row inserted!");
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    /**
     * Insert new Person (implicitly)
     */
    public ResultSet insertP() {
        ResultSet resultSet = null;
        BufferedReader args = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Please enter name, lastname, middilename, age, email, birth date).\n"
                    + "\"HIT ENTER AFTER EVERY INSERTION\"");

            String sql = "INSERT INTO Person" + "(Name,LastName,MidName,Age,Email,Birth_date)\n"
                    + "Values(?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, args.readLine());
            pstmt.setString(2, args.readLine());
            pstmt.setString(3, args.readLine());
            pstmt.setInt(4, Integer.parseInt(args.readLine()));
            pstmt.setString(5, args.readLine());
            pstmt.setString(6, args.readLine());
            pstmt.executeUpdate();
            String lastRow = "select MAX(PersonID) from Person;\n";
            statmt = conn.createStatement();
            resultSet = statmt.executeQuery(lastRow);
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public void insertG() {
        // Insert new group
        BufferedReader args = new BufferedReader(new InputStreamReader(System.in));
        String tempS;
        try {
            System.out.println("Please enter group's number, name, faculty*, class president*\n"
                    + "\"HIT ENTER AFTER EVERY INSERTION\"\t *-skip by entering an empty string");
            String sql = "INSERT INTO Groups Values(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(args.readLine()));
            pstmt.setString(2, args.readLine());
            int faculty = (tempS = args.readLine()).isEmpty() ? 0 : Integer.parseInt(tempS);
            if (faculty != 0)
                pstmt.setInt(3, faculty);
            else
                pstmt.setNull(3, Types.NULL);
            int classPresident = (tempS = args.readLine()).isEmpty() ? 0 : Integer.parseInt(tempS);
            if (classPresident != 0)
                pstmt.setInt(4, classPresident);
            else
                pstmt.setNull(4, Types.NULL);
            pstmt.executeUpdate();
            System.out.println("New row inserted");
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    /**
     * Insert new faculty
     */
    public void insertF() {
        BufferedReader args = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Please enter faculty's name, description, establishment\n"
                    + "\"HIT ENTER AFTER EVERY INSERT\"");
            String sql = "INSERT INTO Faculty (FacultyName,Description,Establishment) Values(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, args.readLine());
            pstmt.setString(2, args.readLine());
            pstmt.setString(3, args.readLine());
            pstmt.executeUpdate();
            System.out.println("New row inserted");
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    /**
     * Insert new teacher
     */
    public void insertT() {
        ResultSet resultSet;
        BufferedReader args = new BufferedReader(new InputStreamReader(System.in));
        String tempS;
        try {
            resultSet = insertP();
            System.out.println("Please enter teacher's major(1-3), degree(1/2), faculty*, groups*"
                    + "\"HIT ENTER AFTER EVERY INSERT\"\t *-skip by entering an empty string");

            String sql = "INSERT INTO Teacher(TPerson,TMajor,TDegree,TFaculty,TGroups) Values(?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, resultSet.getInt(1));
            pstmt.setInt(2, Integer.parseInt(args.readLine()));
            pstmt.setInt(3, Integer.parseInt(args.readLine()));
            int faculty = (tempS = args.readLine()).isEmpty() ? 0 : Integer.parseInt(tempS);
            if (faculty != 0)
                pstmt.setInt(4, faculty);
            else
                pstmt.setNull(4, Types.NULL);
            int group = (tempS = args.readLine()).isEmpty() ? 0 : Integer.parseInt(tempS);
            if (group != 0)
                pstmt.setInt(5, group);
            else
                pstmt.setNull(5, Types.NULL);
            pstmt.executeUpdate();
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Show students (not completed)
     */
    public void ListS() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT StudentID,Person.Name,Person.MidName,Person.LastName,Person.Age,StudyForm.FormName,Person.Birth_date,Person.Email,Groups.GroupName,GPA\n" +
                    " FROM Student \n" +
                    " LEFT JOIN Person ON Student.SPerson = Person.PersonID\n" +
                    " LEFT JOIN StudyForm ON Student.SStudyForm=StudyForm.FormID\n" +
                    " LEFT JOIN Groups ON Student.SGroup=Groups.GroupID;";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ID\tName\tMiddile Name \tLast Name\tAge\tForm Name\tBirth date\tEmail\tGroup\tGPA");
            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("StudentID") + "\t" +
                                rs.getString("Name") + "\t" +
                                rs.getString("MidName") + "\t" +
                                rs.getString("LastName") + "\t" +
                                rs.getString("Age") + "\t" +
                                rs.getString("FormName") + "\t" +
                                rs.getString("Birth_date") + "\t" +
                                rs.getString("Email") + "\t" +
                                rs.getString("GroupName") + "\t" +
                                rs.getString("GPA"));
            }
            System.out.println("\n\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void ListT() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT Teacher.TeacherID,Person.Name,Person.MidName,Person.LastName,Person.Age,Person.Birth_date,Person.Email," +
                    "Major.MajorName,Degree.DegreeName,Faculty.FacultyName,Groups.GroupName\n" +
                    " FROM Teacher \n" +
                    " JOIN Person ON Teacher.TPerson = Person.PersonID\n" +
                    " LEFT JOIN Major ON Teacher.TMajor=Major.MajorID\n" +
                    " LEFT JOIN Faculty ON Teacher.TFaculty=Faculty.FacultyID\n" +
                    " LEFT JOIN Degree ON Teacher.TDegree=Degree.DegreeID\n" +
                    " LEFT JOIN Groups ON Teacher.TGroups=Groups.GroupID";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ID\tName\tMiddile Name\tLast Name\tAge\tBirth date\tEmail\tMajor\tDegree\tFaculty\tGroup");
            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("TeacherID") + "\t" +
                                rs.getString("Name") + "\t" +
                                rs.getString("MidName") + "\t" +
                                rs.getString("LastName") + "\t" +
                                rs.getString("Age") + "\t" +
                                rs.getString("Birth_date") + "\t" +
                                rs.getString("Email") + "\t" +
                                rs.getString("MajorName") + "\t" +
                                rs.getString("DegreeName") + "\t" +
                                rs.getString("FacultyName") + "\t" +
                                rs.getString("GroupName"));
            }
            System.out.println("\n\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void ListG() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT GroupID,GroupName,Faculty.FacultyName,Person.Name" +
                    " FROM Groups \n" +
                    " LEFT JOIN Faculty ON Groups.GFaculty = Faculty.FacultyID\n" +
                    " LEFT JOIN Student ON Groups.ClassPresident=Student.StudentID\n" +
                    " LEFT JOIN Person ON Student.SPerson=Person.PersonID";

            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("GroupID\tName\tFaculty\tGroup");
            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("GroupID") + "\t" +
                                rs.getString("GroupName") + "\t" +
                                rs.getString("FacultyName") + "\t" +
                                rs.getString("Name"));
            }
            System.out.println("\n\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void ListF() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT FacultyID,FacultyName,Description,Establishment FROM Faculty";

            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("FacultyID\tName\tDescription\tEstablishment");
            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("FacultyID") + "\t" +
                                rs.getString("FacultyName") + "\t" +
                                rs.getString("Description") + "\t" +
                                rs.getString("Establishment"));
            }
            System.out.println("\n\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void TopThreeS() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT MAX(FacultyID) FROM Faculty;";
            ResultSet rs = stmt.executeQuery(sql);
            int max = rs.getInt(1);
            rs = null;
            boolean first_iteration = true;
            for (int i = max; i > 0; i--) {
                stmt = conn.createStatement();
                sql = "SELECT Faculty.FacultyName,Person.Name,Person.LastName,GPA FROM Student \n" +
                        "LEFT JOIN Groups ON Student.SGroup=Groups.GroupID\n" +
                        "LEFT JOIN Faculty ON Groups.GFaculty=Faculty.FacultyID\n" +
                        "LEFT JOIN Person ON Student.SPerson=Person.PersonID\n" +
                        "WHERE Faculty.FacultyID = " + i + " " +
                        "ORDER BY GPA DESC\n" +
                        "LIMIT 3;";

                rs = stmt.executeQuery(sql);

                if (first_iteration) {
                    System.out.println("FacultyID\tFaculty\tStudent\tGPA");
                    first_iteration = false;
                }
                // loop through the result set
                while (rs.next()) {
                    System.out.println(
                            rs.getString("FacultyName") + "\t" +
                                    rs.getString("Name") + "\t" +
                                    rs.getString("LastName") + "\t" +
                                    rs.getString("GPA"));
                }
            }
            System.out.println("\n\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

}
