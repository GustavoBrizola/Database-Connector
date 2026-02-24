package database.source;

public class QueryCommand
{
    // Singleton Pattern
    private static QueryCommand commands;
    private QueryCommand(){}
    protected static QueryCommand Get_Instance()
    {
        if(commands == null) synchronized(QueryCommand.class) {commands = new QueryCommand();}
        return commands;
    }

    // links the QueryRun class
    public QueryRun run = QueryRun.Get_Instance();

    // return the query in any part of the code
    public String GetQuery() {return Query.GetInstance().GetQuery();}


    public QueryCommand Prompt(String query)
    {
        Query.GetInstance().SetQuery(query);
        return this;
    }

    // Primary Syntaxes - Initialize the query
    // SELECT
     public QueryCommand SELECT(String data)
    {        
        if(data == null) return this;
        String method = BuildQuery("SELECT ? ", data);
        Query.GetInstance().SetQuery(method);
        return this;
    }

    //INSERT
    public QueryCommand INSERT(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("INSERT ? ", data);
        Query.GetInstance().SetQuery(method);
        return this;
    }

    //CREATE
    public QueryCommand CREATE(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("CREATE ? ", data);
        Query.GetInstance().SetQuery(method);
        return this;
    }

    //DELETE
    public QueryCommand DELETE(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("DELETE ? ", data);
        Query.GetInstance().SetQuery(method);
        return this;
    }

    //DROP
    public QueryCommand DROP(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("DROP ? ", data);
        Query.GetInstance().SetQuery(method);
        return this;   
    }

    //SHOW
    public QueryCommand SHOW(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("SHOW ? ", data);
        if(GetQuery() != null)
        {
            Query.GetInstance().SetQuery(GetQuery()+method);
            return this;
        }
        Query.GetInstance().SetQuery(method);
        return this;   
    }

    // Seconday Syntaxes - Determines Parameters
    // FROM
    public QueryCommand FROM(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("FROM ? ", data);
        if(GetQuery() != null)
        {
            Query.GetInstance().SetQuery(GetQuery()+method);
            return this;
        }
        Query.GetInstance().SetQuery(method);
        return this;
    }

    // WHERE
    public QueryCommand WHERE(String data)
    {   
        if(data == null) return this;
        String method = BuildQuery("WHERE ? ", data);
        if(GetQuery() != null)
        {
            Query.GetInstance().SetQuery(GetQuery()+method);
            return this;
        }
        Query.GetInstance().SetQuery(method);
        return this;
    }

    // AND
    public QueryCommand AND(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("AND ? ", data);
        if(GetQuery() != null)
        {
            Query.GetInstance().SetQuery(GetQuery()+method);
            return this;
        }
        Query.GetInstance().SetQuery(method);
        return this;
    }

    // VALUES
    public QueryCommand VALUES(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("VALUES ? ", data);
        if(GetQuery() != null)
        {
            Query.GetInstance().SetQuery(GetQuery()+method);
            return this;
        }
        Query.GetInstance().SetQuery(method);
        return this;     
    }

    private String BuildQuery(String statement, String data)
    {
        return statement.replaceFirst("\\?", data);
    }
}
