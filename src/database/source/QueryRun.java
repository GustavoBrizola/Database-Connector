package database.source;

import java.sql.SQLException;
import java.sql.Statement;

public class QueryRun
{  
    // Singleton Pattern
    private QueryRun() {}
    private static QueryRun run;
    protected static QueryRun GetInstance()
    {
        if(run == null) synchronized(QueryRun.class) {run = new QueryRun();}
        return run;
    }

    // variables
    private Statement query_statement;

    // Getters and Setters
    protected void SetQueryStatement(Statement query_statement) {this.query_statement = query_statement;}
    public Statement GetQueryStatement() {return this.query_statement;}

    public void RunQuery(boolean terminal)
    {  
        // Before everyting, checks if the currenct query presents some kind of injection
        if(QueryAntiInjection.GetInstance().CheckSQL())
        {
            System.err.println("Execution Aborted: SQL Injection detected!");
            return;
        }
        
        try
        {
            // This runs the query Allocated on variable
            SetQueryStatement(Settings.GetInstance().GetConnection().createStatement());
            GetQueryStatement().execute(Query.GetInstance().GetQuery());  

            // Checks if terminal parameter is true
            // If false, end function
            if(!terminal) return;

            // Prints rows
            // Column nane: Data, Column nane: Data, Column nane: Data, ...
            while(GetQueryStatement().getResultSet().next())
            {
                for(int i = 1; i <= GetQueryStatement().getResultSet().getMetaData().getColumnCount(); i++)
                {
                    if(i>1) System.out.print(", ");
                    System.out.print(GetQueryStatement().getResultSet().getMetaData().getCatalogName(i) + ": ");
                    System.out.print(GetQueryStatement().getResultSet().getString(i));
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