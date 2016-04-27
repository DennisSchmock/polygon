
package Control;

import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jvnet.mock_javamail.Mailbox;

/**
 * This test uses a Mock-JavaMail jar file.
 * It redirects all the mails to in-memory mail box which can be easily used in unit tests.
 * @author Cherry   
 */
public class MailSenderBeanTest {
    
    private MailSenderBean mailSender;
    private String toEmail;
    private String subject;
    private String message;
    private String fromEmail;
    private String username;
    private String password;
    
    public MailSenderBeanTest() {
    }
    
    @Before
    public void setUp() throws MessagingException {
        mailSender = new MailSenderBean();
        toEmail = "noreply.polygonproject@gmail.com";
        subject  = "TEST";
        message = "This is just for testing";
        fromEmail = "test.polygonproject@gmail.com";
        username = "test.polygonproject";
        password = "password";
        
        Mailbox.clearAll();
        
    }
    
    //uses the Mock-JavaMail  approach to read the mails from the Mock Email Server.
    @Test
    public void testSendEmail() throws Exception {
        mailSender.sendEmail(fromEmail, username, password, toEmail, subject, message);

        List<Message> inbox = Mailbox.get(toEmail);

        assertTrue(inbox.size() == 1);
        assertEquals(subject, inbox.get(0).getSubject());
        assertEquals(message, inbox.get(0).getContent());
    }
}
