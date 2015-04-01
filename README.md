# datebuilder
joda-time based Date-building convenience utility with a fluent-api style API.

All dates will be in UTC unless specified with inTimeZone()
```java
import static com.bradneighbors.builders.DateBuilder.*;
...
Date oneYearAgoAtMidnight = now().inYear(2009).atMidnightExactly().build();
Date yesterday = yesterday().build();
Date tomorrow = tomorrow().build();
Date birthday = MM_dd_yyyy("08_29_1974").build();
Date birthday = now().inYear(1974).inMonth(8).onDay(29).build();
```

To use from maven central:

```xml
<dependency>
  <groupId>com.bradneighbors.builders</groupId>
  <artifactId>datebuilder</artifactId>
  <version>1.0.0-RELEASE</version>
</dependency>
```
