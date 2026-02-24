package database.source;

import java.sql.SQLException;
import java.sql.Statement;

public class QueryRun
{  
    // Singleton Pattern
    private static QueryRun run;
    private QueryRun(){}
    protected static QueryRun Get_Instance()
    {
        if(run == null) synchronized(QueryRun.class) {run = new QueryRun();}
        return run;
    }

    private Statement query_statement;

    public String GetQuery() {return Query.GetInstance().GetQuery();}

    protected void SetQueryStatement(Statement query_statement) {this.query_statement = query_statement;}
    public Statement GetQueryStatement() {return this.query_statement;}

    public void RunQuery(boolean terminal)
    {  
        try
        {
            SetQueryStatement(Settings.GetInstance().GetConnection().createStatement());
            GetQueryStatement().execute(Query.GetInstance().GetQuery()); 

            // Checks if terminal parameter is true
            // If false, end function
            if(!terminal) return;

            while(GetQueryStatement().getResultSet().next())
            {
                for(int i = 1; i <= GetQueryStatement().getResultSet().getMetaData().getColumnCount(); i++)
                {
                    if(i>1) System.out.print(", ");
                    System.out.print(GetQueryStatement().getResultSet().getMetaData().getCatalogName(i) + ": " + GetQueryStatement().getResultSet().getString(i));
                }   
                System.out.println(""); 
            }
        }
        catch(SQLException e)
        {
            System.err.println("Query failed: " + e.getMessage());
        }
    }
}