
package ee.ut.soap.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ee.ut.soap.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Plant_QNAME = new QName("http://web.soap.ut.ee/", "plant");
    private final static QName _GetAvailablePlants_QNAME = new QName("http://web.soap.ut.ee/", "getAvailablePlants");
    private final static QName _GetAvailablePlantsResponse_QNAME = new QName("http://web.soap.ut.ee/", "getAvailablePlantsResponse");
    private final static QName _NotFoundException_QNAME = new QName("http://web.soap.ut.ee/", "NotFoundException");
    private final static QName _PurchaseOrder_QNAME = new QName("http://web.soap.ut.ee/", "purchaseOrder");
    private final static QName _GetAllPlantsResponse_QNAME = new QName("http://web.soap.ut.ee/", "getAllPlantsResponse");
    private final static QName _CreatePurchaseOrder_QNAME = new QName("http://web.soap.ut.ee/", "createPurchaseOrder");
    private final static QName _Plants_QNAME = new QName("http://web.soap.ut.ee/", "plants");
    private final static QName _GetAllPlants_QNAME = new QName("http://web.soap.ut.ee/", "getAllPlants");
    private final static QName _CreatePurchaseOrderResponse_QNAME = new QName("http://web.soap.ut.ee/", "createPurchaseOrderResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ee.ut.soap.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PlantResource }
     * 
     */
    public PlantResource createPlantResource() {
        return new PlantResource();
    }

    /**
     * Create an instance of {@link GetAvailablePlantsResponse }
     * 
     */
    public GetAvailablePlantsResponse createGetAvailablePlantsResponse() {
        return new GetAvailablePlantsResponse();
    }

    /**
     * Create an instance of {@link GetAvailablePlants }
     * 
     */
    public GetAvailablePlants createGetAvailablePlants() {
        return new GetAvailablePlants();
    }

    /**
     * Create an instance of {@link PlantResourceList }
     * 
     */
    public PlantResourceList createPlantResourceList() {
        return new PlantResourceList();
    }

    /**
     * Create an instance of {@link CreatePurchaseOrder }
     * 
     */
    public CreatePurchaseOrder createCreatePurchaseOrder() {
        return new CreatePurchaseOrder();
    }

    /**
     * Create an instance of {@link GetAllPlantsResponse }
     * 
     */
    public GetAllPlantsResponse createGetAllPlantsResponse() {
        return new GetAllPlantsResponse();
    }

    /**
     * Create an instance of {@link PurchaseOrderResource }
     * 
     */
    public PurchaseOrderResource createPurchaseOrderResource() {
        return new PurchaseOrderResource();
    }

    /**
     * Create an instance of {@link NotFoundException }
     * 
     */
    public NotFoundException createNotFoundException() {
        return new NotFoundException();
    }

    /**
     * Create an instance of {@link CreatePurchaseOrderResponse }
     * 
     */
    public CreatePurchaseOrderResponse createCreatePurchaseOrderResponse() {
        return new CreatePurchaseOrderResponse();
    }

    /**
     * Create an instance of {@link GetAllPlants }
     * 
     */
    public GetAllPlants createGetAllPlants() {
        return new GetAllPlants();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlantResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "plant")
    public JAXBElement<PlantResource> createPlant(PlantResource value) {
        return new JAXBElement<PlantResource>(_Plant_QNAME, PlantResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailablePlants }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "getAvailablePlants")
    public JAXBElement<GetAvailablePlants> createGetAvailablePlants(GetAvailablePlants value) {
        return new JAXBElement<GetAvailablePlants>(_GetAvailablePlants_QNAME, GetAvailablePlants.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailablePlantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "getAvailablePlantsResponse")
    public JAXBElement<GetAvailablePlantsResponse> createGetAvailablePlantsResponse(GetAvailablePlantsResponse value) {
        return new JAXBElement<GetAvailablePlantsResponse>(_GetAvailablePlantsResponse_QNAME, GetAvailablePlantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "NotFoundException")
    public JAXBElement<NotFoundException> createNotFoundException(NotFoundException value) {
        return new JAXBElement<NotFoundException>(_NotFoundException_QNAME, NotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseOrderResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "purchaseOrder")
    public JAXBElement<PurchaseOrderResource> createPurchaseOrder(PurchaseOrderResource value) {
        return new JAXBElement<PurchaseOrderResource>(_PurchaseOrder_QNAME, PurchaseOrderResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPlantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "getAllPlantsResponse")
    public JAXBElement<GetAllPlantsResponse> createGetAllPlantsResponse(GetAllPlantsResponse value) {
        return new JAXBElement<GetAllPlantsResponse>(_GetAllPlantsResponse_QNAME, GetAllPlantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePurchaseOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "createPurchaseOrder")
    public JAXBElement<CreatePurchaseOrder> createCreatePurchaseOrder(CreatePurchaseOrder value) {
        return new JAXBElement<CreatePurchaseOrder>(_CreatePurchaseOrder_QNAME, CreatePurchaseOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlantResourceList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "plants")
    public JAXBElement<PlantResourceList> createPlants(PlantResourceList value) {
        return new JAXBElement<PlantResourceList>(_Plants_QNAME, PlantResourceList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPlants }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "getAllPlants")
    public JAXBElement<GetAllPlants> createGetAllPlants(GetAllPlants value) {
        return new JAXBElement<GetAllPlants>(_GetAllPlants_QNAME, GetAllPlants.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePurchaseOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.ut.ee/", name = "createPurchaseOrderResponse")
    public JAXBElement<CreatePurchaseOrderResponse> createCreatePurchaseOrderResponse(CreatePurchaseOrderResponse value) {
        return new JAXBElement<CreatePurchaseOrderResponse>(_CreatePurchaseOrderResponse_QNAME, CreatePurchaseOrderResponse.class, null, value);
    }

}
