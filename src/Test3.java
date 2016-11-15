

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.confirmed.ConfirmedRequestService;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyMultipleRequest;
import com.serotonin.bacnet4j.service.confirmed.ReadPropertyRequest;
import com.serotonin.bacnet4j.service.confirmed.WritePropertyRequest;
import com.serotonin.bacnet4j.service.unconfirmed.WhoIsRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.PropertyReference;
import com.serotonin.bacnet4j.type.constructed.ReadAccessSpecification;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.Enumerated;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.util.RequestUtils;

public class Test3 {
    public static void main(String[] args) throws Exception {
        IpNetwork network = new IpNetwork();
        Transport transport = new Transport(network);
        LocalDevice ld = new LocalDevice(1, transport);
        System.out.print(network.toString());
        ld.initialize();
//        ld.sendGlobalBroadcast(new WhoIsRequest());
//        ld.send(new RemoteDevice(601,new Address("10.2.141.61", 0xbac0), null), new ReadPropertyRequest(new ObjectIdentifier(ObjectType.analogInput, 0), PropertyIdentifier.presentValue));
//        
        RemoteDevice rd = ld.findRemoteDevice(new Address("10.2.141.61", 0xbac0), null, 601);
//        System.out.println(rd.getInstanceNumber());
//        Map<PropertyIdentifier, Encodable> values = RequestUtils.getProperties(ld, rd, null,
//                PropertyIdentifier.objectName, PropertyIdentifier.vendorName, PropertyIdentifier.modelName,
//                PropertyIdentifier.description, PropertyIdentifier.location, PropertyIdentifier.objectList);
//
//        System.out.println(values);
        System.out.println( ld.send(rd, new ReadPropertyRequest(new ObjectIdentifier(ObjectType.analogInput, 0), PropertyIdentifier.presentValue)));
//        System.out.println( ld.send(rd, new WritePropertyRequest(new ObjectIdentifier(ObjectType.binaryOutput, 16384),
//                PropertyIdentifier.presentValue, null, new Enumerated(1), null)));
        List<ReadAccessSpecification> readAccessSpecs = new ArrayList<ReadAccessSpecification>();

        List<PropertyReference> propertyReferences = new ArrayList<PropertyReference>();
        propertyReferences.add(new PropertyReference(PropertyIdentifier.presentValue, null));
        propertyReferences.add(new PropertyReference(PropertyIdentifier.description, null));
        readAccessSpecs.add(new ReadAccessSpecification(new ObjectIdentifier(ObjectType.analogInput, 0),
                new SequenceOf<PropertyReference>(propertyReferences)));

        propertyReferences = new ArrayList<PropertyReference>();
        propertyReferences.add(new PropertyReference(PropertyIdentifier.presentValue, null));
        readAccessSpecs.add(new ReadAccessSpecification(new ObjectIdentifier(ObjectType.analogInput, 1),
                new SequenceOf<PropertyReference>(propertyReferences)));

//        propertyReferences = new ArrayList<PropertyReference>();
//        propertyReferences.add(new PropertyReference(PropertyIdentifier.presentValue, null));
//        readAccessSpecs.add(new ReadAccessSpecification(new ObjectIdentifier(ObjectType.analogInput, 35),
//                new SequenceOf<PropertyReference>(propertyReferences)));

//        ConfirmedRequestService service = new ReadPropertyMultipleRequest(new SequenceOf<ReadAccessSpecification>(
//                readAccessSpecs));
        
        
        System.out.print(ld.send(rd, new ReadPropertyMultipleRequest(new SequenceOf<ReadAccessSpecification>(
                readAccessSpecs))));
        
        ld.terminate();
    }
}
