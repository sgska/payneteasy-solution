# payneteasy-solution

## Configuration file [application.properties](src%2Fmain%2Fresources%2Fapplication.properties)
- server.port - application leastig port
- application.storage-file.path - folder for save files
- validation.file.max-size-kb - max file upload size in Kb
- validation.file.accepted-extension - valid files extensions with dot. array, separator - ",". Example: .txt,.csv

## Application start
**Application.main** - for start app from IDE.
You can use console **mvn test** for test methods.

## Interesting places in the application code
- **org.payneteasy.solution.context.ApplicationContext** - simple realization for entry app point.
- **org.payneteasy.solution.web.servlet.DispatcherServlet** - the main servlet that processes incoming requests and returns the result. it contains a mapping of controllers HttpController interface that can process requests
- **org.payneteasy.solution.context.properties.ApplicationPropertiesReader** - clas used foe get property from application.properties by property key.

  ... and more.

## UML diagram

```seq
Client->>DispatcherServlet: HttpServletRequest
DispatcherServlet->>HttpController: RequestEntity


Note right of HttpController: Implementation HttpController interface

HttpController->>DispatcherServlet: ResponseEntity
DispatcherServlet->>Client: HttpServletResponse
```

