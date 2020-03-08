import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * @program: lagou-spider
 * @description:
 * @author: 闲乘月
 * @create: 2020-03-08 09:43
 **/
public class MailOp {
    public String getPath(){
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return path;
    }

    public boolean send(String userAccount, String receiver, String title,String content, String filePath) {
        Session session = authorizeMail();

        try {

            // 创建一个MimeMessage类的实例对象
            Message message = new MimeMessage(session);

            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(userAccount));

            // 设置收件人邮箱地址
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiver));

            // 设置邮件主题
            message.setSubject(title);
            Multipart multipart = setContent(content, filePath);

            // 设置内容
            message.setContent(multipart);

            // 最终发送邮件
            Transport.send(message);
            System.out.println("=====邮件已经发送=====");
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public Multipart setContent(String title, String filePath) throws MessagingException {
        // 创建一个MimeBodyPart的对象，以便添加内容
        BodyPart messageBodyPart1 = new MimeBodyPart();

        // 设置邮件正文内容
        messageBodyPart1.setText(title);

        // 创建另外一个MimeBodyPart对象，以便添加其他内容
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();

        // 设置邮件中附件文件的路径
        String filename = filePath;

        // 创建一个datasource对象，并传递文件
        DataSource source = new FileDataSource(filename);

        // 设置handler
        messageBodyPart2.setDataHandler(new DataHandler(source));

        // 加载文件
        messageBodyPart2.setFileName(filename);

        // 创建一个MimeMultipart类的实例对象
        Multipart multipart = new MimeMultipart();

        // 添加正文1内容
        multipart.addBodyPart(messageBodyPart1);

        // 添加正文2内容
        multipart.addBodyPart(messageBodyPart2);
        return multipart;
    }

    public Session authorizeMail() {
        // 创建一个Property文件对象
        Properties props = new Properties();

        // 设置邮件服务器的信息，这里设置smtp主机名称
        props.put("mail.smtp.host", "smtp.qq.com");

        // 设置socket factory 的端口
        props.put("mail.smtp.socketFactory.port", "465");

        // 设置socket factory
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        // 设置需要身份验证
        props.put("mail.smtp.auth", "true");

        // 设置SMTP的端口，QQ的smtp端口是25
        props.put("mail.smtp.port", "25");

        // 身份授权
        return Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 第二个参数，就是我QQ开启smtp的授权码
                String userAccount = "2351036454@qq.com";
                String authorizationCode = "nkblibknbklrecdc";
                return new PasswordAuthentication(userAccount, authorizationCode);

            }

        });
    }

}