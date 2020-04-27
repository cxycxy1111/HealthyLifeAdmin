package com.alfred.healthylife.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepxUtil {

    public RepxUtil() {

    }

    public static String repxDeal(String string) {
        string = ulDealing(string);
        string = olDealing(string);
        string = headerDealing(string);
        string = imgDealing(string);
        string = linkDealing(string);
        return string;
    }

    private static String headerDealing(String string) {

        String repx_h3 = "<p>### ([\\s\\S]*?)</p>";
        Matcher matcher_h3 = Pattern.compile(repx_h3).matcher(string);
        while (matcher_h3.find()) {
            String replace_h3 = "<h3>$1</h3>";
            string = matcher_h3.replaceAll(replace_h3);
        }

        String repx_h2 = "<p>## ([\\s\\S]*?)</p>";
        Matcher matcher_h2 = Pattern.compile(repx_h2).matcher(string);
        while (matcher_h2.find()) {
            String replace_h2 = "<h2>$1</h2>";
            string = matcher_h2.replaceAll(replace_h2);
        }

        String repx_h1 = "<p># ([\\s\\S]*?)</p>";
        Matcher matcher_h1 = Pattern.compile(repx_h1).matcher(string);
        while (matcher_h1.find()) {
            String replace_h1 = "<h1>$1</h1>";
            string = matcher_h1.replaceAll(replace_h1);
        }

        return string;
    }

    private static String imgDealing(String string) {
        String repx_img = "\\!\\[([\\s\\S]*?)\\]\\(([\\s\\S]*?) \"([\\s\\S]*?)\"\\)";
        Matcher matcher = Pattern.compile(repx_img).matcher(string);
        while (matcher.find()) {
            String replace_img = "<img alt=\"$1\" src=\"$2\" />";
            string = matcher.replaceAll(replace_img);
        }
        return string;
    }

    private static String linkDealing(String string) {
        String repx_link = "\\[([\\s\\S]*?)\\]\\(([\\s\\S]*?)\\)";
        Matcher matcher = Pattern.compile(repx_link).matcher(string);
        while (matcher.find()) {
            String replace_link = "<a href=\"$2\" target=\"_blank\">$1</a>";
            string = matcher.replaceAll(replace_link);
        }
        return string;
    }

    private static String olDealing(String string) {
        String repx_ol = "<p>[0-9]{1,}\\. ([\\S]*?)</p>";
        Matcher matcher = Pattern.compile(repx_ol).matcher(string);
        while (matcher.find()) {
            String replace_ol = "<p><ol><li>$1</li></ol></p>";
            string = matcher.replaceAll(replace_ol);
        }

        String repx_ol_2 = "</li></ol></p><p><ol><li>";
        Matcher matcher_2 = Pattern.compile(repx_ol_2).matcher(string);
        while (matcher_2.find()) {
            string = matcher_2.replaceAll("</li><li>");
        }
        string = deleteEmptyParagraph(string);
        return string;
    }

    private static String ulDealing(String string) {

        String repx = "<p>- ([\\S]*?)</p>";
        Matcher matcher = Pattern.compile(repx).matcher(string);
        while (matcher.find()) {
            String replace = "<p><ul><li>$1</li></ul></p>";
            string = matcher.replaceAll(replace);
        }

        String repx_ol_2 = "</li></ul></p><p><ul><li>";
        Matcher matcher_2 = Pattern.compile(repx_ol_2).matcher(string);
        while (matcher_2.find()) {
            string = matcher_2.replaceAll("</li><li>");
        }
        string = deleteEmptyParagraph(string);
        return string;
    }

    private static String deleteEmptyParagraph(String string) {
        String repx = "<p></p>";
        Matcher matcher = Pattern.compile(repx).matcher(string);
        while (matcher.find()) {
            string = matcher.replaceAll("");
        }
        return string;
    }


}
