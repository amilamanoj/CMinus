package org.amila.cm;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: amila
 * Date: 2/7/12
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class CMinusTokenPrinter {

    public static void main(String[] args) {
        CMinusParserTokenManager lexer;

        if (args.length == 1) {
            System.out.println("CMinus Parser Version 1.0:  Checking syntax of " + args[0]);
            JavaCharStream stream = null;
            try {
                stream = new JavaCharStream(new java.io.FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            lexer = new CMinusParserTokenManager(stream);
            String t;
            int i=0;
            while (!(t = lexer.getNextToken().image).equals("")) {
                i++;
                System.out.println("Token" + i +": " + t);
            }
        }
    }
}
