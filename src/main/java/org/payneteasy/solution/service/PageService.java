package org.payneteasy.solution.service;

public class PageService {

    public String getMain() {
        return """
                <!DOCTYPE html>
                <html>
                    <head>
                        <title>Main page</title>
                    </head>
                    <body>
                        <p>Hello, payneteasy!</p>
                    </body>
                </html>
                """;
    }
}
