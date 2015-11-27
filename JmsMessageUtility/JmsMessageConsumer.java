import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsMessageConsumer {

    private final static String JMS_SERVER_IP_PORT = "127.0.0.1:4447";
    private final static String USERNAME = "fei";
    private final static String PASSWORD = "Ericsson*1";
    private final static String CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
    private final static String JMS_DESTINATION_JNDI_NAME = "jms/queue/testqueue";

    public static void main(String[] args) throws Exception {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "remote://" + JMS_SERVER_IP_PORT);
        env.put(Context.SECURITY_PRINCIPAL, USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        Context context = new InitialContext(env);

        ConnectionFactory factory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_JNDI_NAME);
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = (Destination) context.lookup(JMS_DESTINATION_JNDI_NAME);
        MessageConsumer receiver = session.createConsumer(dest);
        receiver.setMessageListener(new JmsMessageListener());
        connection.start();

        System.out.println("Start listening... press any key to exit");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.read();

        session.close();
        connection.close();
    }
}

class JmsMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.print("Message received: " + message + ", Payload: ");
        try {
            if (message instanceof TextMessage) {
                System.out.println(((TextMessage) message).getText());
            } else if (message instanceof ObjectMessage) {
                System.out.println(((ObjectMessage) message).getObject());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
