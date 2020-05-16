package nd.sched.web;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import nd.sched.job.JobTrigger;

@JsonComponent
public class JobTriggerSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JobTriggerSerializer.class);
    public static class Serializer extends JsonSerializer<JobTrigger> {
        @Override
        public void serialize(JobTrigger jt, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            logger.debug("Serialize called for: {}", jt);
            jgen.writeStartObject();
            jgen.writeStringField("status",jt.getStatus().toString());
            jgen.writeStringField("name",jt.getName());
            jgen.writeStringField("parent",jt.getParent());
            jgen.writeStringField("condition",jt.getCondition());
            jgen.writeStringField("time_condition",jt.getTimeCondition());
            jgen.writeStringField("arguments",jt.getArguments());
            jgen.writeStringField("description",jt.getDescription());
            jgen.writeStringField("target_job",jt.getTargetJob());
            jgen.writeStringField("timezone",jt.getTimezone());
            jgen.writeEndObject();            
        }
    }

    public static class Deserializer extends JsonDeserializer<JobTrigger> {

        @Override
        public JobTrigger deserialize(JsonParser arg0, DeserializationContext arg1)
                throws IOException {
            logger.info("DeSerialize called for");
            throw new UnsupportedOperationException("Deserialize on JobTrigger NOT supported");
        }
    }
    private JobTriggerSerializer(){}
}