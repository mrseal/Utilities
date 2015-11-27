import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsMessageProducer {

	private final static String JMS_SERVER_IP_PORT = "127.0.0.1:4447";
	private final static String USERNAME = "fei";
	private final static String PASSWORD = "Ericsson*1";
	private final static String CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
	private final static String JMS_DESTINATION_JNDI_NAME = "jms/queue/test";

	public static void main(String[] args) throws Exception {

		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.initialContextFactory");
		env.put(Context.PROVIDER_URL, "remote://" + JMS_SERVER_IP_PORT);
		env.put(Context.SECURITY_PRINCIPAL, USERNAME);
		env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
		Context context = new InitialContext(env);

		ConnectionFactory factory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_JNDI_NAME);
		Connection connection = factory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination dest = (Destination) context.lookup(JMS_DESTINATION_JNDI_NAME);
		MessageProducer sender = session.createProducer(dest);

		TextMessage message = session.createTextMessage("Message from JSE client");

		sender.send(message);

		session.close();
		connection.close();

	}

}
