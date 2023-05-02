package com.example.toodle_server_springboot.util;

public class MailBodyForm {

    private final String username;
    private final String content;

    public MailBodyForm(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "\t<meta charset=\"UTF-8\">\n" +
                        "\t<title>안내</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\t<h1>")
                .append(username)
                .append(" 님 안녕하세요.")
                .append("</h1>")
                .append("<p>")
                .append(content)
                .append("</p>")
                .append("</body></html>")
        ;

        return sb.toString();
    }

}
