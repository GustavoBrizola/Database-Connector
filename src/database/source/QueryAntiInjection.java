package database.source;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class QueryAntiInjection 
{
    /*  
    This Class has the unique task to get the current query allocated and check
    any sign of SQL injection and return validation

        Example of Injection: 
        SELECT * FROM users WHERE id = 1 OR '1'='1'
        SELECT * FROM users WHERE username = 'admin' AND password = '' OR '1'='1'
        SELECT name, description FROM products WHERE category = '' UNION SELECT username, password FROM users--'
        SELECT title FROM articles WHERE id = 10; IF (1=1) WAITFOR DELAY '0:0:5'--

    Powered libinjection and JNA
    https://github.com/client9/libinjection.git
    https://github.com/java-native-access/jna.git
    */

    // Singleton pattern
    private QueryAntiInjection() {}
    private static QueryAntiInjection injection;
    protected static QueryAntiInjection GetInstance()
    {
        if(injection == null) synchronized(QueryAntiInjection.class) {injection = new QueryAntiInjection();}
        return injection;
    }
   
    // Define the Interface to match libinjection_sqli.h
    // Extending Library allow JNA use libinjection
    private interface LibInjection extends Library 
    {
        LibInjection INSTANCE = (LibInjection) Native.load("lib/libinjection.dylib", LibInjection.class);
        int libinjection_sqli(String input, int input_len, byte[] fingerprint);
    }

    public Boolean CheckSQL()
    {
        // libinjection uses an 8-char fingerprint
        byte[] fingerprint = new byte[8]; 

        // Call the C function
        int isSqli = LibInjection.INSTANCE.libinjection_sqli
        (
            Query.GetInstance().GetQuery(), 
            Query.GetInstance().GetQuery().length(), 
            fingerprint
        );

        // Returns boolean
        if(isSqli == 1) return true;
        else return false;
    }
}