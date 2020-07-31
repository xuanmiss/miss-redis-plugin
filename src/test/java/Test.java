/**
 * @project: miss-redis-plugin
 * @package: PACKAGE_NAME
 * @author: miss
 * @since: 2020/7/30 19:52
 * @history: 1.2020/7/30 created by miss
 */
public class Test {

    public static void main(String[] args) {
        String s = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" +
                "<HTML>\n" +
                " <HEAD>\n" +
                "  <TITLE> 系统登录中 </TITLE>\n" +
                "  <script>\n" +
                "   function clickbtn (){\n" +
                "     document.getElementById(\"appLogin\").submit();\n" +
                "   }\n" +
                "  </script>\n" +
                " </HEAD>\n" +
                " <BODY onload=\"clickbtn()\">\n" +
                "   <form id=\"appLogin\" method=\"post\" action=\"http://portal.definesys.com:7003/definespace/ssoservlet\">\n" +
                "     <input type=\"text\" id=\"j_username\" name=\"username\" value=\"" + 135 + "\" style=\"visibility:hidden\">\n" +
//                        "     <input type=\"password\" id=\"j_password\" name=\"j_password\" value=\"" + pwd + "\" style=\"visibility:hidden\">\n" +
                "   </form>\n" +
                " </BODY>\n" +
                "</HTML>\n";
        System.out.println(s);
    }
}
