package gas.samples.knative.eventing;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.cloudevents.CloudEvent;

@Path("/")
public class HelloEventsService {
	private Jsonb jsonb = JsonbBuilder.create();

	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String info() {
		String message = "HelloEventsService running";
		return message;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processEvent(CloudEvent inputEvent) {
        //Handle the event
		System.out.println(inputEvent);
		String body = new String(inputEvent.getData());
		
		System.out.println("type: " + inputEvent.getType());
		System.out.println("source: " + inputEvent.getSource());
		
		EventBody eventBody = null;
		eventBody = jsonb.fromJson(body, EventBody.class);
		System.out.println("body: " + eventBody.getMsg());
		if(eventBody.getMsg() == null) {	
			System.out.println("unexpected event body: " + body);
		}
		

		
        return Response.ok().build();
    }
	// raw parsing not using CloudEvents
//	public String processEvent(@Context HttpHeaders headers, String body) {
//		String result = "{status:ok}";
//		
//		System.out.println(headers);
//		System.out.println(headers.getRequestHeaders());
//		System.out.println(body);
//			
//		
//		return result;
//	}
}
