# simple java library for parsing Ableton als projects Edit
## Usage ##

checkout with git:
```
git clone https://github.com/my5t3ry/als-parser.git
```

install to local mvn repository:
```
cd als-parser
mvn clean install
```


add mvn dependency:
```
<dependency>
    <groupId>de.my5t3ry</groupId>
    <artifactId>als-parser</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

parse als file:
```
final AbletonProjectParser alsParser = new AbletonProjectParser();
AbletonProjectParser result = alsParser.parse(new File("foo.als"));
result.printSummary();

```


