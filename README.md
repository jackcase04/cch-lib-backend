# CCH Academic Drive | Back-End

### Description

This is the backend for a web application that permits members of CCF to view, access, and request resources from the CCF academic drive.

### Development Instructions

Make sure you have an `.env` file in the project root, with this format:

```
SPRING_DATASOURCE_URL=url
SPRING_DATASOURCE_USERNAME=username
SPRING_DATASOURCE_PASSWORD=password
```

Then run:

```
chmod +x bash/setup_env.sh
source bash/setup_env.sh
```

Then you are ready to run the API.

Run `mvn spring-boot:run` to start the API locally. Make sure you have Maven installed and your Java version is 17.