package com.contento3.module.email;

import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.contento3.module.email.model.EmailInfo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;


/**
 * Class that manages all the email 
 * related operations for all different 
 * providers.
 * 
 * @author hamakhaa
 *
 */
public class EmailService {

	/**
	 * Composes the email message
	 * @param emailInfo
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void compose(final EmailInfo emailInfo) throws MessagingException, IOException {

		// JavaMail representation of the message
//	    final Session session = Session.getInstance(new Properties(), null);
//	    final MimeMessage msg = new MimeMessage(session);
//	                    
//	    // Sender and recipient
//	    msg.setFrom(new InternetAddress(emailInfo.getFrom()));
//	    msg.setRecipient( Message.RecipientType.TO, new InternetAddress(emailInfo.getTo()));
//	 
//	    // Subject
//	    msg.setSubject(emailInfo.getSubject());
//	            		
//	    // Add a MIME part to the message
//	    final MimeMultipart mp = new MimeMultipart();
//	 		            
//	    final BodyPart part = new MimeBodyPart();
//	    part.setContent(emailInfo.getEmailText(), emailInfo.getMimeType());
//	    mp.addBodyPart(part);
//	 	msg.setContent(mp);
//	            
//	 	// Print the raw email content on the console
//	    PrintStream out = System.out;
//	    msg.writeTo(out);
	}
	
	/**
	 * Sends the email to the recipient
	 * based on provider selected.
	 * @return
	 */
	public Boolean send(final EmailInfo emailInfo){
		 // Your AWS credentials are stored in the AwsCredentials.properties file within the project.
        // You entered these AWS credentials when you created a new AWS Java project in Eclipse.
		final AWSCredentials credentials = new BasicAWSCredentials("AKIAJEGH4QG6UW4DAWWA","nkzXeeBuDII89ITYvbd70E38xR+YXu+w32pYUvi2");
        
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{emailInfo.getTo()});
        
        // Create the subject and body of the message.
        Content subject = new Content().withData(emailInfo.getSubject());
        Content textBody = new Content().withData(emailInfo.getEmailText()); 
        Body body = new Body().withHtml(textBody);

        // Create a message with the specified subject and body.
        final Message message = new Message().withSubject(subject).withBody(body);
        
        // Assemble the email.
        final SendEmailRequest request = new SendEmailRequest().withSource(emailInfo.getFrom()).withDestination(destination).withMessage(message);

        try
        {        
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
        
            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);
               
            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your production 
            // access status, sending limits, and Amazon SES identity-related settings are specific to a given 
            // AWS region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
            // the US East (N. Virginia) region. Examples of other regions that Amazon SES supports are US_WEST_2 
            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
//            Region REGION = Region.getRegion(Regions.US_EAST_1);
//            client.setRegion(REGION);
       
            // Send the email.
            client.sendEmail(request);  
            System.out.println("Email sent!");
            
            //TODO
            //Email info to send to the table
        }
        catch (final Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    
		return null;
	}
	
}
