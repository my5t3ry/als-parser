# simple java library for parsing Ableton als project files

works for Ableton version >= 8
## Usage ##

clone:
```
git clone https://github.com/my5t3ry/als-parser.git
```

install to local repository:
```
cd als-parser
mvn clean install
```


add dependency:
```
<dependency>
    <groupId>de.my5t3ry</groupId>
    <artifactId>als-parser</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

usage:
```
final AbletonProjectParser alsParser = new AbletonProjectParser();
AbletonProjectParser result = alsParser.parse(new File("foo.als"));
System.out.println(result.getTotalTracks());
```


