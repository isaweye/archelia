<p align="left">
  <img src="docs/repository/banner.png"/>
</p>

[Examples](https://isaweye.github.io/archelia)

## Installation
1. Install to local repo
```shell
   $ mvn install
```
2. Add as dependency
```xml
    <dependency>
        <groupId>uk.mqchinee</groupId>
        <artifactId>Archelia</artifactId>
        <version>{version-here}</version>
        <scope>provided</scope>
    </dependency>
```

### OR 
*(not recommended)*

1. Add as dependency
```xml
    <dependency>
        <groupId>uk.mqchinee</groupId>
        <artifactId>Archelia</artifactId>
        <version>{version-here}</version>
        <scope>system</scope>
        <systemPath>{path-to-jar}</systemPath>
    </dependency>
```
