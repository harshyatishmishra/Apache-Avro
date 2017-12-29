# Apache-Avro
Apache Avro is a data Serialize and deserialize system.

For Serializing JSON:
java -jar avro-tools-1.7.5.jar fromjson --schema-file {avscFile} {jsonFile} > {avroFileName}

For Deserializing JSON:
java -jar avro-tools-1.7.5.jar tojson {avroFileName} 

External Jar:
1. json-simple-1.1.jar
2. avro-tools-1.8.2.jar
