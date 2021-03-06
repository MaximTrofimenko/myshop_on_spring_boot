package ru.geekbrains.paymentservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.10
 * 2020-04-01T19:01:07.211+03:00
 * Generated source version: 3.1.10
 * 
 */
@WebServiceClient(name = "PaymentPortService", 
                  wsdlLocation = "file:/D:/projects/myshop_on_spring_boot/src/main/resources/META-INF/wsdl/payment/WsPayment.wsdl",
                  targetNamespace = "http://www.geekbrains.ru/PaymentService") 
public class PaymentPortService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.geekbrains.ru/PaymentService", "PaymentPortService");
    public final static QName PaymentPortSoap11 = new QName("http://www.geekbrains.ru/PaymentService", "PaymentPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/projects/myshop_on_spring_boot/src/main/resources/META-INF/wsdl/payment/WsPayment.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(PaymentPortService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/projects/myshop_on_spring_boot/src/main/resources/META-INF/wsdl/payment/WsPayment.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public PaymentPortService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PaymentPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PaymentPortService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public PaymentPortService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public PaymentPortService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public PaymentPortService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns PaymentPort
     */
    @WebEndpoint(name = "PaymentPortSoap11")
    public PaymentPort getPaymentPortSoap11() {
        return super.getPort(PaymentPortSoap11, PaymentPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PaymentPort
     */
    @WebEndpoint(name = "PaymentPortSoap11")
    public PaymentPort getPaymentPortSoap11(WebServiceFeature... features) {
        return super.getPort(PaymentPortSoap11, PaymentPort.class, features);
    }

}
