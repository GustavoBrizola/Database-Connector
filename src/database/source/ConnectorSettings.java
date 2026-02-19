package database.source;

// Important libraries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
* @author GustavoBrizola 
*/
public class ConnectorSettings
{ 
    // Singleton Pattern
    private static ConnectorSettings settings;
    private ConnectorSettings(){}
    protected static ConnectorSettings Get_Instance()
    {
        if(settings == null)
        {
            synchronized(ConnectorSettings.class) {settings = new ConnectorSettings();}
        }
        return settings;
    }

    // Variables
    private Connection connection;
    
    private int driver;
    private String protocol;
    private String host;
    private String port;
    private String database;

    private String user;
    private String password;

    // Setters and Getters
    protected void SetConnection(Connection connection) {this.connection = connection;}
    public Connection GetConnection() {return this.connection;}

    protected void SetProtocol(String protocol) {this.protocol = protocol;}
    public String GetProtocol() {return this.protocol;}

    protected void SetDriver(int driver) {this.driver = driver;}
    public int GetDriver() {return this.driver;}

    protected void SetHost(String host) {this.host = host;}
    public String GetHost() {return this.host;}

    protected void SetPort(String port) {this.port = port;}
    public String GetPort() {return this.port;}

    protected void SetDatabase(String database) {this.database = database;}
    public String GetDatabase() {return this.database;}

    protected void SetUser(String user) {this.user = user;}
    public String GetUser() {return this.user;}

    protected void SetPassword(String password) {this.password = password;}
    public String GetPassword() {return this.password;}

    /* setting Methods */
    // Starts connection session
    public ConnectorSettings Connect()
    {
        // If there's connection, do nothing and returns
        if(CheckConnection()) return this;

        try
        {
            SetProtocol("jdbc:mysql://"+ GetHost()+":"+ GetPort() + "/"+ GetDatabase());
            SetConnection(DriverManager.getConnection(GetProtocol(), GetUser(), GetPassword()));
        } 
        catch (SQLException e) 
        {   
            // FIXME: Catchs exception on first attempt
            // Recursive ensures connection
            System.err.println("("+e.getErrorCode()+") "+"Connection Failed: "+e.getMessage());
            Connect();
        }
        return this;
    }

    // Ends the current connection
    public ConnectorSettings Disconnect()
    {
        // If there's not connection, do nothing and return
        if(!CheckConnection()) return this;
        
        try
        {
            GetConnection().close();
            SetConnection(null);
        }
        catch(SQLException e)
        {
            System.err.println("Failed: "+e.getMessage());
        }
        return this;
    }

    // Checks current connection
    public boolean CheckConnection()
    {
        if(GetConnection() != null)
        return false;
        else return true;
    }

    //
    public void ChangeConnection(int driver, String host, String port, String database, String user, String password)
    {
        Disconnect();
        if(GetDriver() != driver)   SetDriver(driver);
        if(GetHost() != null)       SetHost(host);
        if(GetPort() != null)       SetPort(port);
        if(GetDatabase() != null)   SetDatabase(database);
        if(GetUser() != null)       SetUser(user);
        if(GetPassword() != null)   SetPassword(password);
        Connect();
    }
}
