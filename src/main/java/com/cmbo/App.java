package com.cmbo;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Error> scriptErrors = new ArrayList<Error>();
        StringBuilder sb=new StringBuilder("list = bsf.lookupBean (\"parameters\");\n ");
        sb.append("account = list.get(0);");
        sb.append("return account.firstName;");
        String script = sb.toString();
        Account account = new Account();
        account.setId(1);
        account.setFirstName("Ogi");
        account.setLastName("Me");
        String result = ScriptUtil.runBeanShell(script, scriptErrors, account);
        System.out.println(result);
        System.out.println( "Hello World!" );
    }
}
