package avroEx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

public class avroSerialize {
    public void serialize() throws IOException {

        InputStream in = new FileInputStream("resources/StudentActivity.json");

        // create a schema
        Schema schema = new Schema.Parser()
                .parse(new File("resources/StudentActivity.avsc"));
        // create a record to hold json
        GenericRecord AvroRec = new GenericData.Record(schema);
        // create a record to hold course_details
        GenericRecord CourseRec = new GenericData.Record(
                schema.getField("course_details").schema());
        // this file will have AVro output data
        File AvroFile = new File("resources/StudentActivity.avro");
        // Create a writer to serialize the record
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(
                schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(
                datumWriter);

        dataFileWriter.create(schema, AvroFile);

        // iterate over JSONs present in input file and write to Avro output
        // file
        for (Iterator it = new ObjectMapper().readValues(
                new JsonFactory().createJsonParser(in), JSONObject.class); it
                        .hasNext();) {

            JSONObject JsonRec = (JSONObject) it.next();
            AvroRec.put("id", JsonRec.get("id"));

            LinkedHashMap CourseDetails = (LinkedHashMap) JsonRec
                    .get("course_details");
            CourseRec.put("course_id", CourseDetails.get("course_id"));
            CourseRec.put("enroll_date", CourseDetails.get("enroll_date"));
            CourseRec.put("verb", CourseDetails.get("verb"));
            CourseRec.put("result_score", CourseDetails.get("result_score"));

            AvroRec.put("course_details", CourseRec);

            dataFileWriter.append(AvroRec);
        } // end of for loop

        in.close();
        dataFileWriter.close();
System.out.println("close");
    }

    public static void main(String[] args) throws IOException {
        avroSerialize AvroEx = new avroSerialize();
        AvroEx.serialize();
      //  AvroEx.deserialize();
    }

}
